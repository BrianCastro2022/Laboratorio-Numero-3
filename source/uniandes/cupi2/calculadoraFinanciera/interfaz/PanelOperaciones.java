/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: PanelOperaciones.java 1343 2008-10-16 16:59:39Z ju-cort1 $
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Ejercicio: n8_calculadoraFinanciera
 * Autor: Juan Camilo Cortés Medina - 27-ago-2008
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.cupi2.calculadoraFinanciera.interfaz;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 * Panel de manejo de extensiones
 */
public class PanelOperaciones extends JPanel implements ActionListener
{

    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
     * Comando generar reporte según archivo
     */
    private static final String GENERAR_REPORTE_ARCHIVO = "GENERAR_REPORTE_ARCHIVO";

    /**
     * Comando generar reporte de crédito actual
     */
    private static final String GENERAR_REPORTE_ACTUAL = "GENERAR_REPORTE_ACTUAL";

    /**
     * Comando Opción 1
     */
    private static final String OPCION_1 = "OPCION_1";

    /**
     * Comando Opción 2
     */
    private static final String OPCION_2 = "OPCION_2";

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Ventana principal de la aplicación
     */
    private InterfazCalculadoraFinanciera principal;

    // -----------------------------------------------------------------
    // Atributos de interfaz
    // -----------------------------------------------------------------

    /**
     * Botón Generar Reporte
     */
    private JButton btnGenerarReporteArchivo;

    /**
     * Botón generar reporte de crédito actual
     */
    private JButton btnGenerarReporteActual;

    /**
     * Botón Opción 1
     */
    private JButton btnOpcion1;

    /**
     * Botón Opción 2
     */
    private JButton btnOpcion2;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Constructor del panel
     * @param elPadre Ventana principal
     */
    public PanelOperaciones( InterfazCalculadoraFinanciera elPadre )
    {
        principal = elPadre;

        setBorder( new TitledBorder( "Opciones" ) );
        setLayout( new GridLayout( 1, 2 ) );

        // Botón generar reporte según archivo
        btnGenerarReporteArchivo = new JButton( "Reporte Según Archivo" );
        btnGenerarReporteArchivo.setActionCommand( GENERAR_REPORTE_ARCHIVO );
        btnGenerarReporteArchivo.addActionListener( this );
        add( btnGenerarReporteArchivo );

        // Botón generar reporte de crédito actual
        btnGenerarReporteActual = new JButton( "Reporte Crédito Actual" );
        btnGenerarReporteActual.setActionCommand( GENERAR_REPORTE_ACTUAL );
        btnGenerarReporteActual.addActionListener( this );
        add( btnGenerarReporteActual );

        // Botón opción 1
        btnOpcion1 = new JButton( "Opción 1" );
        btnOpcion1.setActionCommand( OPCION_1 );
        btnOpcion1.addActionListener( this );
        add( btnOpcion1 );

        // Botón opción 2
        btnOpcion2 = new JButton( "Opción 2" );
        btnOpcion2.setActionCommand( OPCION_2 );
        btnOpcion2.addActionListener( this );
        add( btnOpcion2 );
    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Manejo de los eventos de los botones
     * @param e Acción que generó el evento.
     */
    public void actionPerformed( ActionEvent e )
    {
        if( GENERAR_REPORTE_ARCHIVO.equals( e.getActionCommand( ) ) )
        {
            DialogoGenerarReporteArchivo dialogo = new DialogoGenerarReporteArchivo( this );
            dialogo.setVisible( true );
        }
        else if( GENERAR_REPORTE_ACTUAL.equals( e.getActionCommand( ) ) )
        {
            boolean hay = principal.hayCreditoActual( );
            if( hay )
            {
                DialogoGenerarReporteActual dialogo = new DialogoGenerarReporteActual( this );
                dialogo.setVisible( true );
            }
            else
                JOptionPane.showMessageDialog( null, "No hay ningún crédito seleccionado", "Atención", JOptionPane.INFORMATION_MESSAGE );
        }
        else if( OPCION_1.equals( e.getActionCommand( ) ) )
        {
            principal.reqFuncOpcion1( );
        }
        else if( OPCION_2.equals( e.getActionCommand( ) ) )
        {
            principal.reqFuncOpcion2( );
        }
    }

    /**
     * Genera el reporte según un archivo de entrada sobre un crédito a un archivo de salida seleccionado.
     * @param rutaArchivoEntrada es la ruta del archivo de entrada
     * @param rutaArchivoSalida es la ruta del archivo de salida
     * @return true si se pudo generar el reporte y false de lo contrario.
     */
    public boolean generarReporte( String rutaArchivoEntrada, String rutaArchivoSalida )
    {
        return principal.generarReporte( rutaArchivoEntrada, rutaArchivoSalida );
    }

    /**
     * Genera el reporte según un archivo de entrada sobre un crédito a un archivo de salida seleccionado.
     * @param rutaArchivoSalida es la ruta del archivo de salida
     * @return true si se pudo generar el reporte y false de lo contrario.
     */
    public boolean generarReporteActual( String rutaArchivoSalida )
    {
        return principal.generarReporteActual( rutaArchivoSalida );
    }

}
