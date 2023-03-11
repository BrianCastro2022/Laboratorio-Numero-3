/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: InterfazCalculadoraFinanciera.java 1365 2008-10-23 00:25:35Z jua-gome $
 * Universidad de los Andes (Bogot� - Colombia)
 * Departamento de Ingenier�a de Sistemas y Computaci�n 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Ejercicio: n8_calculadoraFinanciera
 * Autor: Juan Camilo Cort�s Medina - 27-ago-2008
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.cupi2.calculadoraFinanciera.interfaz;

import java.awt.BorderLayout;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import uniandes.cupi2.calculadoraFinanciera.mundo.CalculadoraFinanciera;
import uniandes.cupi2.calculadoraFinanciera.mundo.Credito;
import uniandes.cupi2.calculadoraFinanciera.mundo.CreditoYaExisteException;
import uniandes.cupi2.calculadoraFinanciera.mundo.MesAmortizacion;
import uniandes.cupi2.calculadoraFinanciera.mundo.PersistenciaException;
import uniandes.cupi2.calculadoraFinanciera.mundo.ReporteException;

/**
 * Esta es la ventana principal de la aplicaci�n.
 */
public class InterfazCalculadoraFinanciera extends JFrame
{
    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
     * Indicador de cantidad de decimales luego de la coma
     */
    private static final int NUM_DECIMALES = 2;

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Clase principal del mundo
     */
    private CalculadoraFinanciera calculadoraFinanciera;

    // -----------------------------------------------------------------
    // Atributos de la interfaz
    // -----------------------------------------------------------------

    /**
     * Panel imagen
     */
    private PanelImagen panelImagen;

    /**
     * Panel central
     */
    private PanelCentral panelCentral;

    /**
     * Panel con las extensiones
     */
    private PanelOperaciones panelOperaciones;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Crea la ventana principal
     * @param archivoPersistencia es el archivo donde se persiste la informaci�n de la aplicaci�n
     */
    public InterfazCalculadoraFinanciera( String archivoPersistencia )
    {
        // Crea la clase principal
        try
        {
            calculadoraFinanciera = new CalculadoraFinanciera( archivoPersistencia );
            setTitle( "Calculadora Financiera" );
            setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
            setResizable( false );

            // Construye la forma
            setLayout( new BorderLayout( ) );
            setSize( 810, 620 );

            // Creaci�n de los paneles aqu�
            panelImagen = new PanelImagen( );
            add( panelImagen, BorderLayout.NORTH );

            panelCentral = new PanelCentral( this );
            add( panelCentral, BorderLayout.CENTER );

            panelOperaciones = new PanelOperaciones( this );
            add( panelOperaciones, BorderLayout.SOUTH );

            ArrayList creditos = calculadoraFinanciera.darCreditos( );
            panelCentral.actualizarCedulas( calculadoraFinanciera.darCreditos( ) );

            ArrayList losCreditos = calculadoraFinanciera.darCreditos( );

            if( losCreditos.size( ) > 0 )
            {
                Credito credito = ( Credito )losCreditos.get( 0 );

                panelCentral.actualizarDatosCredito( credito.darMonto( ), credito.darPlazo( ), credito.darTasa( ), credito.darCuota( ), credito.darNombre( ) );

                double totalInteres = calculadoraFinanciera.darTotalInteresPagado( credito, 1 );
                double totalAbonos = calculadoraFinanciera.darTotalAbonoPagado( credito, 1 );
                double total = calculadoraFinanciera.darTotalDineroPagado( credito, 1 );

                panelCentral.actualizarDatosTotales( totalInteres, totalAbonos, total );

                String[][] tabla = darTablaCredito( credito, 1 );
                panelCentral.actualizarDatos( 1, tabla );

                setLocationRelativeTo( null );
            }
        }
        catch( PersistenciaException e )
        {
            JOptionPane.showMessageDialog( null, e.getMessage( ), "Error", JOptionPane.ERROR_MESSAGE );
        }
    }

    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------

    /**
     * Genera el reporte de un cr�dito seg�n los datos del archivo de entrada al archivo de salida
     * @param rutaArchivoEntrada es la ruta del archivo de entrada
     * @param rutaArchivoSalida es la ruta del archivo de salida
     * @return true si se pudo generar el reporte y false de lo contrario.
     */
    public boolean generarReporte( String rutaArchivoEntrada, String rutaArchivoSalida )
    {
        boolean exito = false;
        try
        {
            calculadoraFinanciera.generarReporteAmortizacionSegunAnio( rutaArchivoEntrada, rutaArchivoSalida );
            exito = true;
        }
        catch( ReporteException e )
        {
            JOptionPane.showMessageDialog( null, e.getMessage( ), "Error", JOptionPane.ERROR_MESSAGE );
        }
        return exito;
    }

    /**
     * Genera el reporte del cr�dito actual seg�n los datos del archivo de salida
     * @param rutaArchivoSalida es la ruta del archivo de salida
     * @return true si se pudo generar el reporte y false de lo contrario.
     */
    public boolean generarReporteActual( String rutaArchivoSalida )
    {
        boolean exito = false;
        try
        {
            String cedula = panelCentral.darCedulaClienteActual( );
            calculadoraFinanciera.generarReporteCreditoActual( cedula, rutaArchivoSalida );
            exito = true;
        }
        catch( ReporteException e )
        {
            JOptionPane.showMessageDialog( null, e.getMessage( ), "Error", JOptionPane.ERROR_MESSAGE );
        }
        return exito;
    }

    /**
     * Actualiza la informaci�n presentada al usuario del cr�dito seleccionado
     * @param credito es el nombre del cliente
     */
    public void actualizarCredito( Credito credito )
    {
        if( credito != null )
        {
            panelCentral.actualizarDatosCredito( credito.darMonto( ), credito.darPlazo( ), credito.darTasa( ), credito.darCuota( ), credito.darNombre( ) );

            double totalInteres = calculadoraFinanciera.darTotalInteresPagado( credito, 1 );
            double totalAbonos = calculadoraFinanciera.darTotalAbonoPagado( credito, 1 );
            double total = calculadoraFinanciera.darTotalDineroPagado( credito, 1 );

            panelCentral.actualizarDatosTotales( totalInteres, totalAbonos, total );

            String[][] tabla = darTablaCredito( credito, 1 );
            panelCentral.actualizarDatos( 1, tabla );
        }
    }

    /**
     * Agrega un cr�dito al sistema
     * @param monto es el monto del cr�dito
     * @param plazo es el plazo del cr�dito
     * @param tasa es la tasa del cr�dito
     * @param nombre es el nombre del cliente
     * @param cedula es la c�dula del cliente
     */
    public void agregarCredito( double monto, int plazo, double tasa, String nombre, String cedula )
    {
        try
        {
            Credito credito = calculadoraFinanciera.crearCredito( monto, plazo, tasa, nombre, cedula );

            panelCentral.actualizarCedulas( calculadoraFinanciera.darCreditos( ) );
            panelCentral.seleccionarUltimoCredito( );
            panelCentral.actualizarDatosCredito( credito.darMonto( ), credito.darPlazo( ), credito.darTasa( ), credito.darCuota( ), credito.darNombre( ) );

            double totalInteres = calculadoraFinanciera.darTotalInteresPagado( credito, 1 );
            double totalAbonos = calculadoraFinanciera.darTotalAbonoPagado( credito, 1 );
            double total = calculadoraFinanciera.darTotalDineroPagado( credito, 1 );

            panelCentral.actualizarDatosTotales( totalInteres, totalAbonos, total );

            String[][] tabla = darTablaCredito( credito, 1 );
            panelCentral.actualizarDatos( 1, tabla );
        }
        catch( CreditoYaExisteException e )
        {
            JOptionPane.showMessageDialog( null, e.getMessage( ), "Error", JOptionPane.ERROR_MESSAGE );
        }
    }

    /**
     * Actualiza los datos del cr�dito del a�o anterior del cr�dito actual
     * @param credito es el cr�dito actual presentado en la interfaz
     * @param anioActual es el a�o actual
     */
    public void actualizarAnterioresDatosCredito( Credito credito, int anioActual )
    {
        if( anioActual != 1 )
        {

            double totalInteres = calculadoraFinanciera.darTotalInteresPagado( credito, anioActual - 1 );
            double totalAbonos = calculadoraFinanciera.darTotalAbonoPagado( credito, anioActual - 1 );
            double total = calculadoraFinanciera.darTotalDineroPagado( credito, anioActual - 1 );

            panelCentral.actualizarDatosTotales( totalInteres, totalAbonos, total );

            String[][] tabla = darTablaCredito( credito, anioActual - 1 );
            panelCentral.actualizarDatos( anioActual - 1, tabla );
        }
        else
            JOptionPane.showMessageDialog( null, "No hay ning�n a�o anterior", "Atenci�n", JOptionPane.INFORMATION_MESSAGE );

    }

    /**
     * Actualiza los siguientes datos del cr�dito
     * @param credito es el cr�dito actual presentado en la interfaz
     * @param anioActual es el a�o actual
     */
    public void actualizarSiguientesDatosCredito( Credito credito, int anioActual )
    {
        int anio = anioActual + 1;
        if( credito.darPlazo( ) >= anio * Credito.CANT_MESES )
        {
            double totalInteres = calculadoraFinanciera.darTotalInteresPagado( credito, anio );
            double totalAbonos = calculadoraFinanciera.darTotalAbonoPagado( credito, anio );
            double total = calculadoraFinanciera.darTotalDineroPagado( credito, anio );

            panelCentral.actualizarDatosTotales( totalInteres, totalAbonos, total );

            String[][] tabla = darTablaCredito( credito, anio );
            panelCentral.actualizarDatos( anio, tabla );
        }
        else
            JOptionPane.showMessageDialog( null, "No hay ning�n a�o siguiente", "Atenci�n", JOptionPane.INFORMATION_MESSAGE );
    }

    /**
     * Retorna una tabla con los datos de pagos del cr�dito seg�n el a�o especificado
     * @param credito es el cr�dito del cual se quiere saber sus datos
     * @param anio es el a�o especificado
     * @return la tabla con los datos de pago
     */
    public String[][] darTablaCredito( Credito credito, int anio )
    {
        DecimalFormat df = ( DecimalFormat )NumberFormat.getInstance( );
        df.applyPattern( " ###,###,###.##" );

        String tabla[][] = new String[Credito.CANT_MESES][MesAmortizacion.CANT_DATOS];

        int indice = darMesIniAnio( anio );
        MesAmortizacion[] arreglo = credito.darMeses( anio );
        for( int i = 0; i < Credito.CANT_MESES; i++ )
        {
            MesAmortizacion mes = arreglo[ i ];
            tabla[ i ][ 0 ] = "" + mes.darNumeroMes( );
            tabla[ i ][ 1 ] = df.format( redondear( mes.darInteresAPagar( ) ) );
            tabla[ i ][ 2 ] = df.format( redondear( mes.darAbonoACapital( ) ) );
            tabla[ i ][ 3 ] = df.format( redondear( mes.darSaldoObligacion( ) ) );

            indice++;
        }
        return tabla;
    }

    /**
     * Determina si hay al menos un cr�dito ingresado en la aplicaci�n
     * @return true si hay al menos un cr�dito y false de lo contrario.
     */
    public boolean hayCreditoActual( )
    {
        return calculadoraFinanciera.darCreditos( ).size( ) > 0;
    }

    /**
     * M�todo que se ejecuta de cerrar la aplicaci�n. Antes de hacer esto debe salvar los datos de la calculadora.
     */
    public void dispose( )
    {
        try
        {
            calculadoraFinanciera.saveCalculadora( );
        }
        catch( PersistenciaException e )
        {
            JOptionPane.showMessageDialog( null, e.getMessage( ), "Error", JOptionPane.ERROR_MESSAGE );
        }
        super.dispose( );
    }

    // -----------------------------------------------------------------
    // Puntos de Extensi�n
    // -----------------------------------------------------------------

    /**
     * M�todo para la extensi�n 1
     */
    public void reqFuncOpcion1( )
    {
        String resultado = calculadoraFinanciera.metodo1( );
        JOptionPane.showMessageDialog( null, resultado, "Respuesta", JOptionPane.INFORMATION_MESSAGE );
    }

    /**
     * M�todo para la extensi�n 2
     */
    public void reqFuncOpcion2( )
    {
        String resultado = calculadoraFinanciera.metodo2( );
        JOptionPane.showMessageDialog( null, resultado, "Respuesta", JOptionPane.INFORMATION_MESSAGE );
    }

    // -----------------------------------------------------------------
    // M�todos Auxiliares
    // -----------------------------------------------------------------

    /**
     * Retorna el �ndice del mes en el marco del cr�dito en el que inicia el a�o.
     * @param anio es el a�o
     * @return n�mero del mes
     */
    private int darMesIniAnio( int anio )
    {
        return ( ( anio - 1 ) * 12 );
    }

    /**
     * Redondea un n�mero real con el n�mero de decimales expl�cito de la clase
     * @param numero es el n�mero real
     * @return el n�mero redondeado
     */
    private double redondear( double numero )
    {
        double aux0 = Math.pow( 10, NUM_DECIMALES );
        double aux = numero * aux0;
        int tmp = ( int )aux;

        return ( double ) ( tmp / aux0 );
    }

    // -----------------------------------------------------------------
    // Main
    // -----------------------------------------------------------------

    /**
     * Este m�todo ejecuta la aplicaci�n, creando una nueva interfaz
     * @param args
     */
    public static void main( String[] args )
    {

        InterfazCalculadoraFinanciera interfaz = new InterfazCalculadoraFinanciera( "./data/calculadora.dat" );
        interfaz.setVisible( true );
    }
}