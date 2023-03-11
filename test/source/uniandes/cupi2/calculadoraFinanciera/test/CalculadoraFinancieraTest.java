/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: CalculadoraFinancieraTest.java 1365 2008-10-23 00:25:35Z jua-gome $
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Ejercicio: n8_calculadoraFinanciera
 * Autor: Juan Camilo Cortés Medina - 27-ago-2008
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.cupi2.calculadoraFinanciera.test;

import junit.framework.TestCase;
import uniandes.cupi2.calculadoraFinanciera.mundo.CalculadoraFinanciera;
import uniandes.cupi2.calculadoraFinanciera.mundo.Credito;
import uniandes.cupi2.calculadoraFinanciera.mundo.CreditoYaExisteException;
import uniandes.cupi2.calculadoraFinanciera.mundo.PersistenciaException;
import uniandes.cupi2.calculadoraFinanciera.mundo.ReporteException;

/**
 * Esta es la clase usada para verificar que los métodos de la clase CalculadoraFinanciera estén correctamente implementados
 */
public class CalculadoraFinancieraTest extends TestCase
{
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Es la clase donde se harán las pruebas
     */
    private CalculadoraFinanciera calculadoraFinanciera;

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Construye una nueva CalculadoraFinanciera vacía con un archivo de persistencia
     */
    private void setupEscenario1( )
    {
        try
        {
            calculadoraFinanciera = new CalculadoraFinanciera( "./test/data/persistenciaCalculadora1" );
        }
        catch( PersistenciaException e )
        {
            fail( "No debió generar error al crear una nueva calculadora" );
        }
    }

    /**
     * Construye una nueva CalculadoraFinanciera con los siguientes créditos:<br>
     * (Nombre) - (Cédula) - (Monto) - (Plazo) - (Tasa mensual)<br>
     * Ned - 543 - 100 - 24 - 10 <br>
     * Homero - 123 - 100 - 12 - 10
     */
    private void setupEscenario2( )
    {
        try
        {
            calculadoraFinanciera = new CalculadoraFinanciera( "./test/data/calculadora.dat" );
        }
        catch( PersistenciaException e )
        {
            fail( "No debió generar error al crear una nueva calculadora" );
        }
    }

    /**
     * Verifica el constructor cuando se inicializa sin datos. <br>
     * <b> Métodos a probar: </b> <br>
     * CalculadoraFinanciera (constructor). <br>
     * <b> Objetivo: </b> Probar que el constructor crea una calculadora de forma correcta. <br>
     * <b> Resultados esperados: </b> <br>
     * 1. Al crear una calculadora los atributos del objeto deben quedar con el valor correcto.
     */
    public void testCalculadoraFinanciera( )
    {
        setupEscenario1( );

        assertEquals( "Al inicio no hay créditos en la calculadora", 0, calculadoraFinanciera.darCreditos( ).size( ) );
        assertEquals( "El tamaño de la contenedora de créditos es incorrecta", 0, calculadoraFinanciera.darCreditos( ).size( ) );
    }

    /**
     * Verifica el método crearCredito. <br>
     * <b> Métodos a probar: </b> <br>
     * crearCredtio. <br>
     * <b> Objetivo: </b> Probar que el método inserta un crédito correctamente. <br>
     * <b> Resultados esperados: </b> <br>
     * 1. Al crear un crédito que no existe se inserta correctamente.
     */
    public void testCrearCredito1( )
    {
        setupEscenario1( );

        try
        {
            calculadoraFinanciera.crearCredito( 100, 12, 0.1, "Homero Simpson", "12345" );
            assertEquals( "El crédito no fue insertado en la contenedora", 1, calculadoraFinanciera.darCreditos( ).size( ) );
        }
        catch( CreditoYaExisteException e )
        {
            fail( "El crédito no fue ingresado y el cliente no existía con anterioridad" );
        }

        try
        {
            calculadoraFinanciera.crearCredito( 200, 24, 0.2, "Ned Flanders", "54321" );
            assertEquals( "El crédito no fue insertado en la contenedora", 2, calculadoraFinanciera.darCreditos( ).size( ) );
        }
        catch( CreditoYaExisteException e )
        {
            fail( "El crédito no fue ingresado y el cliente no existía con anterioridad" );
        }
    }

    /**
     * Verifica el método crearCredito. <br>
     * <b> Métodos a probar: </b> <br>
     * crearCredtio. <br>
     * <b> Objetivo: </b> Probar que el método no inserta créditos con cédulas repetidas. <br>
     * <b> Resultados esperados: </b> <br>
     * 1. Al crear un crédito que ya existe no es insertado.
     */
    public void testCrearCredito2( )
    {
        setupEscenario1( );

        try
        {
            calculadoraFinanciera.crearCredito( 100, 12, 0.1, "Homero Simpson", "12345" );
            assertEquals( "El crédito no fue insertado en la contenedora", 1, calculadoraFinanciera.darCreditos( ).size( ) );
        }
        catch( CreditoYaExisteException e )
        {
            fail( "El crédito no fue ingresado y el cliente no existía con anterioridad" );
        }

        try
        {
            calculadoraFinanciera.crearCredito( 100, 12, 0.1, "Homero Simpson", "12345" );
            fail( "El crédito fue agregado pese a que el cliente ya existía" );
        }
        catch( CreditoYaExisteException e )
        {
            assertEquals( "El crédito no fue insertado en la contenedora", 1, calculadoraFinanciera.darCreditos( ).size( ) );
        }

    }

    /**
     * Verifica el método darTotalDineroPagado. <br>
     * <b> Métodos a probar: </b> <br>
     * darTotalDineroPagado, crearCredito. <br>
     * <b> Objetivo: </b> Probar que el método retorna el total de dinero pagado correctamente. <br>
     * <b> Resultados esperados: </b> <br>
     * 1. El valor retornado corresponde al total de dinero pagado para el año especificado.
     */
    public void testDarTotalDineroPagado( )
    {
        setupEscenario1( );

        try
        {
            Credito credito1 = calculadoraFinanciera.crearCredito( 100, 12, 0.1, "Homero Simpson", "12345" );
            Credito credito2 = calculadoraFinanciera.crearCredito( 100, 24, 0.1, "Ned Flanders", "54321" );

            assertEquals( "El total de dinero pagado es incorrecto", 176.12, calculadoraFinanciera.darTotalDineroPagado( credito1, 1 ), 0.1 );

            assertEquals( "El total de dinero pagado es incorrecto", 133.56, calculadoraFinanciera.darTotalDineroPagado( credito2, 1 ), 0.1 );
            assertEquals( "El total de dinero pagado es incorrecto", 133.56, calculadoraFinanciera.darTotalDineroPagado( credito2, 2 ), 0.1 );
        }
        catch( CreditoYaExisteException e )
        {
            fail( "El crédito no fue ingresado y el cliente no existía con anterioridad" );
        }
    }

    /**
     * Verifica el método darTotalInteresPagado. <br>
     * <b> Métodos a probar: </b> <br>
     * darTotalInteresPagado, crearCredito. <br>
     * <b> Objetivo: </b> Probar que el método retorna el total de interés pagado correctamente. <br>
     * <b> Resultados esperados: </b> <br>
     * 1. El valor retornado corresponde al total de interés pagado para el año especificado.
     */
    public void testDarTotalInteresPagado( )
    {
        setupEscenario1( );

        try
        {
            Credito credito1 = calculadoraFinanciera.crearCredito( 100, 12, 0.1, "Homero Simpson", "12345" );
            Credito credito2 = calculadoraFinanciera.crearCredito( 100, 24, 0.1, "Ned Flanders", "54321" );

            assertEquals( "El total de interés pagado es incorrecto", 76.12, calculadoraFinanciera.darTotalInteresPagado( credito1, 1 ), 0.1 );

            assertEquals( "El total de interés pagado es incorrecto", 109.40, calculadoraFinanciera.darTotalInteresPagado( credito2, 1 ), 0.1 );
            assertEquals( "El total de interés pagado es incorrecto", 57.72, calculadoraFinanciera.darTotalInteresPagado( credito2, 2 ), 0.1 );
        }
        catch( CreditoYaExisteException e )
        {
            fail( "El crédito no fue ingresado y el cliente no existía con anterioridad" );
        }
    }

    /**
     * Verifica el método darTotalAbonoPagado. <br>
     * <b> Métodos a probar: </b> <br>
     * darTotalAbonoPagado, crearCredito. <br>
     * <b> Objetivo: </b> Probar que el método retorna el total de abono pagado correctamente. <br>
     * <b> Resultados esperados: </b> <br>
     * 1. El valor retornado corresponde al total de abono pagado para el año especificado.
     */
    public void testDarTotalAbonoPagado( )
    {
        setupEscenario1( );

        try
        {
            Credito credito1 = calculadoraFinanciera.crearCredito( 100, 12, 0.1, "Homero Simpson", "12345" );
            Credito credito2 = calculadoraFinanciera.crearCredito( 100, 24, 0.1, "Ned Flanders", "54321" );

            assertEquals( "El total de abono pagado es incorrecto", 100.02, calculadoraFinanciera.darTotalAbonoPagado( credito1, 1 ), 0.1 );

            assertEquals( "El total de abono pagado es incorrecto", 24.16, calculadoraFinanciera.darTotalAbonoPagado( credito2, 1 ), 0.1 );
            assertEquals( "El total de abono pagado es incorrecto", 75.84, calculadoraFinanciera.darTotalAbonoPagado( credito2, 2 ), 0.1 );
        }
        catch( CreditoYaExisteException e )
        {
            fail( "El crédito no fue ingresado y el cliente no existía con anterioridad" );
        }
    }

    /**
     * Verifica el método generarReporteAmortizacionSegunAnio. <br>
     * <b> Métodos a probar: </b> <br>
     * generarReporteAmortizacionSegunAnio, crearCredito. <br>
     * <b> Objetivo: </b> Probar que el método genera el reporte sin problemas. <br>
     * <b> Resultados esperados: </b> <br>
     * 1. El reporte se generó sin problemas.
     */
    public void testGenerarReporteAmortizacionSegunAnio1( )
    {
        setupEscenario1( );

        try
        {
            calculadoraFinanciera.crearCredito( 100, 12, 0.1, "Homero Simpson", "12345" );
            calculadoraFinanciera.crearCredito( 100, 24, 0.1, "Ned Flanders", "54321" );
        }
        catch( CreditoYaExisteException e )
        {
            fail( "El crédito no fue ingresado y el cliente no existía con anterioridad" );
        }

        try
        {
            calculadoraFinanciera.generarReporteAmortizacionSegunAnio( "./test/data/datosCredito.txt", "./test/data/reporte.txt" );
        }
        catch( ReporteException e )
        {
            fail( "No debió ocurrir error al generar el reporte" );
        }
    }

    /**
     * Verifica el método generarReporteAmortizacionSegunAnio. <br>
     * <b> Métodos a probar: </b> <br>
     * generarReporteAmortizacionSegunAnio, crearCredito. <br>
     * <b> Objetivo: </b> Probar que el método no genera el reporte si el archivo de entrada no existe. <br>
     * <b> Resultados esperados: </b> <br>
     * 1. Excepción lanzada
     */
    public void testGenerarReporteAmortizacionSegunAnio2( )
    {
        setupEscenario1( );

        try
        {
            calculadoraFinanciera.generarReporteAmortizacionSegunAnio( "no existe", "no existe" );
            fail( "Debió fallar al generar el reporte" );
        }
        catch( ReporteException e )
        {
        }
    }

}