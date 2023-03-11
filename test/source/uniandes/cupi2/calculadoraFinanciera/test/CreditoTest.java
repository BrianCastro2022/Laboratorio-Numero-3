/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: CreditoTest.java 1333 2008-10-15 00:17:33Z jua-gome $
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Ejercicio: n8_calculadoraFinanciera
 * Autor: Juan Camilo Cortés Medina - 05-ago-2008
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package uniandes.cupi2.calculadoraFinanciera.test;

import junit.framework.TestCase;
import uniandes.cupi2.calculadoraFinanciera.mundo.Credito;

/**
 * Es la clase que verifica el funcionamiento de la clase Credito
 */
public class CreditoTest extends TestCase
{
    // --------------------------------------------------------
    // Atributos
    // --------------------------------------------------------

    /**
     * Crédito usado para las clases de prueba
     */
    private Credito credito1;

    /**
     * Crédito usado para las clases de prueba
     */
    private Credito credito2;

    // --------------------------------------------------------
    // Métodos
    // --------------------------------------------------------

    /**
     * Se construye un crédito nuevo
     */
    private void setupEscenario1( )
    {
        credito1 = new Credito( 100, 12, 0.1, "Homero Simpson", "12345" );
    }

    /**
     * Se construyen dos créditos nuevos
     */
    private void setupEscenario2( )
    {
        setupEscenario1( );

        credito2 = new Credito( 100, 24, 0.1, "Ned Flanders", "54321" );
    }

    /**
     * Verifica el constructor. <br>
     * <b> Métodos a probar: </b> <br>
     * Credito (constructor). <br>
     * <b> Objetivo: </b> Probar que el constructor crea un crédito de forma correcta. <br>
     * <b> Resultados esperados: </b> <br>
     * 1. Al crear un crédito los atributos del objeto deben quedar con el valor correcto.
     */
    public void testCredito( )
    {
        setupEscenario1( );

        assertEquals( "El monto no fue inicializado", 100.0, credito1.darMonto( ) );
        assertEquals( "El plazo no fue inicializado", 12, credito1.darPlazo( ) );
        assertEquals( "La tasa no fue inicializada", 0.1, credito1.darTasa( ) );
        assertEquals( "El nombre no fue inicializado", "Homero Simpson", credito1.darNombre( ) );
        assertEquals( "La cédula no fue inicializada", "12345", credito1.darCedula( ) );

        assertEquals( "La cuota no fue inicializada correctamente", 14.68, credito1.darCuota( ), 0.1 );
    }

    /**
     * Verifica el método darDineroPagadoPorAnio. <br>
     * <b> Métodos a probar: </b> <br>
     * darDineroPagadoPorAnio. <br>
     * <b> Objetivo: </b> Probar que el método retorna el dinero pagado por año correcto. <br>
     * <b> Resultados esperados: </b> <br>
     * 1. El dinero pagado debe ser igual a la cuota multiplicada por 12.
     */
    public void testDarDineroPagadoPorAnio( )
    {
        setupEscenario1( );

        assertEquals( "El total del dinero pagado por año es incorrecto", 176.12, credito1.darDineroPagadoPorAnio( 1 ), 0.1 );
    }

    /**
     * Verifica el método darInteresPagadoPorAnio. <br>
     * <b> Métodos a probar: </b> <br>
     * darInteresPagadoPorAnio. <br>
     * <b> Objetivo: </b> Probar que el método retorna el interés pagado por año correcto. <br>
     * <b> Resultados esperados: </b> <br>
     * 1. El interés pagado debe ser igual a la suma de los interés de los meses.
     */
    public void testDarInteresPagadoPorAnio( )
    {
        setupEscenario2( );

        assertEquals( "El interés pagado por año es incorrecto", 76.12, credito1.darInteresPagadoPorAnio( 1 ), 0.1 );

        assertEquals( "El interés pagado por año es incorrecto", 109.40, credito2.darInteresPagadoPorAnio( 1 ), 0.1 );
        assertEquals( "El interés pagado por año es incorrecto", 57.72, credito2.darInteresPagadoPorAnio( 2 ), 0.1 );
    }

    /**
     * Verifica el método darAbonoPagadoPorAnio. <br>
     * <b> Métodos a probar: </b> <br>
     * darAbonoPagadoPorAnio. <br>
     * <b> Objetivo: </b> Probar que el método retorna el abono pagado por año correcto. <br>
     * <b> Resultados esperados: </b> <br>
     * 1. El abono pagado debe ser igual a la suma de los abonos de los meses.
     */
    public void testDarAbonoPagadoPorAnio( )
    {
        setupEscenario2( );

        assertEquals( "El abono pagado por año es incorrecto", 100.0, credito1.darAbonoPagadoPorAnio( 1 ), 0.1 );

        assertEquals( "El abono pagado por año es incorrecto", 24.16, credito2.darAbonoPagadoPorAnio( 1 ), 0.1 );
        assertEquals( "El abono pagado por año es incorrecto", 75.84, credito2.darAbonoPagadoPorAnio( 2 ), 0.1 );
    }

    /**
     * Verifica el método equals. <br>
     * <b> Métodos a probar: </b> <br>
     * equals. <br>
     * <b> Objetivo: </b> Probar que el método verifica que dos créditos son iguales.<br>
     * <b> Resultados esperados: </b> <br>
     * 1. Debe retornar verdadero
     */
    public void testEquals1( )
    {
        setupEscenario1( );

        Credito credito2 = new Credito( 100, 12, 0.1, "Homero Simpson", "12345" );
        assertTrue( "Los créditos son iguales", credito1.comprarCreditos( credito2 ) );
    }

    /**
     * Verifica el método equals. <br>
     * <b> Métodos a probar: </b> <br>
     * equals. <br>
     * <b> Objetivo: </b> Probar que el método verifica que dos créditos son iguales. <br>
     * <b> Resultados esperados: </b> <br>
     * 1. Debe retornar falso
     */
    public void testEquals2( )
    {
        setupEscenario1( );

        Credito credito2 = new Credito( 200, 12, 1.1, "No existe", "No existe" );
        assertFalse( "Los créditos son iguales", credito1.comprarCreditos( credito2 ) );
    }
}
