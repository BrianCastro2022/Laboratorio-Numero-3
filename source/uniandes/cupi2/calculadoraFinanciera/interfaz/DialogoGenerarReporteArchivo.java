/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: DialogoGenerarReporteArchivo.java 1412 2008-12-12 16:39:06Z jua-gome $
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Ejercicio: n8_calculadoraFinanciera
 * Autor: Juan Camilo Cortés Medina - 05-ago-2008
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package uniandes.cupi2.calculadoraFinanciera.interfaz;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Diálogo para generar el reporte según archivo de uno o varios créditos. El archivo de entrada para generar el reporte debe existir. En cada sección del reporte se
 * especifican los datos del crédito y un resumen de los pagos según el año.
 */
public class DialogoGenerarReporteArchivo extends JDialog implements ActionListener
{
    // --------------------------------------------------------
    // Constantes
    // --------------------------------------------------------

    /**
     * Comando para buscar la ruta del archivo de entrada
     */
    private final static String BUSCAR_ENTRADA = "BUSCAR_ENTRADA";

    /**
     * Comando para buscar la ruta del archivo de salida
     */
    private final static String BUSCAR_SALIDA = "BUSCAR_SALIDA";

    /**
     * Comando para generar el reporte
     */
    private final static String GENENAR = "GENERAR";

    /**
     * Comando para cancelar la operación
     */
    private final static String CANCELAR = "CANCELAR";

    // -------------------------------------------------------
    // Atributos
    // -------------------------------------------------------

    /**
     * Panel padre de este diálogo
     */
    private PanelOperaciones principal;

    // --------------------------------------------------------
    // Atributos de la interfaz
    // --------------------------------------------------------

    /**
     * Etiqueta del archivo de entrada
     */
    private JLabel lbArchivoEntrada;

    /**
     * Campo de texto del archivo de entrada
     */
    private JTextField txtArchivoEntrada;

    /**
     * Botón para buscar el archivo de entrada
     */
    private JButton btnBuscarEntrada;

    /**
     * Etiqueta del archivo de salida
     */
    private JLabel lbArchivoSalida;

    /**
     * Campo de texto del archivo de salida
     */
    private JTextField txtArchivoSalida;

    /**
     * Botón para buscar el archivo de salida
     */
    private JButton btnBuscarSalida;

    /**
     * Botón para generar el reporte
     */
    private JButton btnGenerar;

    /**
     * Botón para cancelar la operación
     */
    private JButton btnCancelar;

    // --------------------------------------------------------
    // Constructores
    // --------------------------------------------------------

    /**
     * Construye un nuevo diálogo para generar el reporte de un crédito
     * @param principalP es el Panel padre de este diálogo.
     */
    public DialogoGenerarReporteArchivo( PanelOperaciones principalP )
    {
        principal = principalP;
        this.setSize( 466, 200 );
        this.setTitle( "Generar reporte según archivo" );
        setLayout( new GridBagLayout( ) );

        GridBagConstraints gridBagConstraints7 = new GridBagConstraints( );
        gridBagConstraints7.gridx = 1;
        gridBagConstraints7.gridy = 4;

        GridBagConstraints gridBagConstraints6 = new GridBagConstraints( );
        gridBagConstraints6.gridx = 0;
        gridBagConstraints6.gridy = 4;

        GridBagConstraints gridBagConstraints5 = new GridBagConstraints( );
        gridBagConstraints5.gridx = 2;
        gridBagConstraints5.insets = new Insets( 5, 5, 5, 5 );
        gridBagConstraints5.gridy = 3;

        GridBagConstraints gridBagConstraints4 = new GridBagConstraints( );
        gridBagConstraints4.fill = GridBagConstraints.BOTH;
        gridBagConstraints4.gridy = 3;
        gridBagConstraints4.weightx = 1.0;
        gridBagConstraints4.insets = new Insets( 5, 5, 5, 5 );
        gridBagConstraints4.gridwidth = 2;
        gridBagConstraints4.gridx = 0;

        GridBagConstraints gridBagConstraints3 = new GridBagConstraints( );
        gridBagConstraints3.gridx = 0;
        gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints3.gridy = 2;
        gridBagConstraints3.insets = new Insets( 5, 5, 0, 0 );

        GridBagConstraints gridBagConstraints2 = new GridBagConstraints( );
        gridBagConstraints2.gridx = 2;
        gridBagConstraints2.insets = new Insets( 5, 5, 5, 5 );
        gridBagConstraints2.gridy = 1;

        GridBagConstraints gridBagConstraints1 = new GridBagConstraints( );
        gridBagConstraints1.fill = GridBagConstraints.BOTH;
        gridBagConstraints1.gridy = 1;
        gridBagConstraints1.weightx = 1.0;
        gridBagConstraints1.gridwidth = 2;
        gridBagConstraints1.insets = new Insets( 5, 5, 5, 5 );
        gridBagConstraints1.gridx = 0;

        GridBagConstraints gridBagConstraints = new GridBagConstraints( );
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets( 5, 5, 0, 0 );

        lbArchivoSalida = new JLabel( );
        lbArchivoSalida.setText( "Ruta archivo de salida" );
        lbArchivoSalida.setHorizontalAlignment( SwingConstants.LEFT );

        lbArchivoEntrada = new JLabel( );
        lbArchivoEntrada.setText( "Ruta archivo de entrada" );
        lbArchivoEntrada.setHorizontalAlignment( SwingConstants.LEFT );
        lbArchivoEntrada.setHorizontalTextPosition( SwingConstants.TRAILING );

        add( lbArchivoEntrada, gridBagConstraints );
        add( darTxtArchivoEntrada( ), gridBagConstraints1 );
        add( darBtnBuscarEntrada( ), gridBagConstraints2 );
        add( lbArchivoSalida, gridBagConstraints3 );
        add( darTxtArchivoSalida( ), gridBagConstraints4 );
        add( darBtnBuscarSalida( ), gridBagConstraints5 );
        add( darBtnGenerar( ), gridBagConstraints6 );
        add( darBtnCancelar( ), gridBagConstraints7 );

        setModal( true );
        setLocationRelativeTo( null );
    }

    /**
     * Inicializa el campo de texto txtArchivoEntrada
     * @return el campo de texto txtArchivoEntrada
     */
    private JTextField darTxtArchivoEntrada( )
    {
        if( txtArchivoEntrada == null )
        {
            txtArchivoEntrada = new JTextField( );
            txtArchivoEntrada.setEditable( false );
            txtArchivoEntrada.setEnabled( false );
        }
        return txtArchivoEntrada;
    }

    /**
     * Inicializa el botón btnBuscarEntrada
     * @return el botón btnBuscarEntrada
     */
    private JButton darBtnBuscarEntrada( )
    {
        if( btnBuscarEntrada == null )
        {
            btnBuscarEntrada = new JButton( );
            btnBuscarEntrada.setText( "Buscar" );
            btnBuscarEntrada.setActionCommand( BUSCAR_ENTRADA );
            btnBuscarEntrada.addActionListener( this );
        }
        return btnBuscarEntrada;
    }

    /**
     * Inicializa el campo de texto txtArchivoSalida
     * @return el campo de texto txtArchivoSalida
     */
    private JTextField darTxtArchivoSalida( )
    {
        if( txtArchivoSalida == null )
        {
            txtArchivoSalida = new JTextField( );
            txtArchivoSalida.setEditable( false );
            txtArchivoSalida.setEnabled( false );
        }
        return txtArchivoSalida;
    }

    /**
     * Inicializa el botón btnBuscarSalida
     * @return el botón btnBuscarSalida
     */
    private JButton darBtnBuscarSalida( )
    {
        if( btnBuscarSalida == null )
        {
            btnBuscarSalida = new JButton( );
            btnBuscarSalida.setText( "Buscar" );
            btnBuscarSalida.setActionCommand( BUSCAR_SALIDA );
            btnBuscarSalida.addActionListener( this );
        }
        return btnBuscarSalida;
    }

    /**
     * Inicializa el botón btnGenerar
     * @return el botón btnGenerar
     */
    private JButton darBtnGenerar( )
    {
        if( btnGenerar == null )
        {
            btnGenerar = new JButton( );
            btnGenerar.setText( "Generar" );
            btnGenerar.setActionCommand( GENENAR );
            btnGenerar.addActionListener( this );
        }
        return btnGenerar;
    }

    /**
     * Inicializa el botón btnCancelar
     * @return el botón btnCancelar
     */
    private JButton darBtnCancelar( )
    {
        if( btnCancelar == null )
        {
            btnCancelar = new JButton( );
            btnCancelar.setText( "Cancelar" );
            btnCancelar.setActionCommand( CANCELAR );
            btnCancelar.addActionListener( this );
        }
        return btnCancelar;
    }

    // --------------------------------------------------------
    // Métodos
    // --------------------------------------------------------

    /**
     * Manejo de los eventos de los botones
     * @param e Acción que generó el evento.
     */
    public void actionPerformed( ActionEvent e )
    {
        if( e.getActionCommand( ).equals( GENENAR ) )
        {
            if( txtArchivoEntrada.getText( ) != null && !txtArchivoEntrada.getText( ).equals( "" ) && txtArchivoSalida.getText( ) != null && !txtArchivoSalida.getText( ).equals( "" ) )
            {
                if( principal.generarReporte( txtArchivoEntrada.getText( ), txtArchivoSalida.getText( ) ) )
                    JOptionPane.showMessageDialog( null, "El reporte fue generado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE );

                dispose( );
            }
            else
                JOptionPane.showMessageDialog( null, "Debe especificar las rutas de los archivos", "Error", JOptionPane.ERROR_MESSAGE );
        }
        else if( e.getActionCommand( ).equals( CANCELAR ) )
        {
            dispose( );
        }
        else if( e.getActionCommand( ).equals( BUSCAR_ENTRADA ) )
        {
            File archivoEntrada = null;
            JFileChooser fc = new JFileChooser( "./data" );
            fc.setDialogTitle( "Seleccionar archivo de entrada" );

            int resultado = fc.showOpenDialog( this );

            if( resultado == JFileChooser.APPROVE_OPTION )
            {
                archivoEntrada = fc.getSelectedFile( );
                txtArchivoEntrada.setText( archivoEntrada.getAbsolutePath( ) );
            }
        }
        else if( e.getActionCommand( ).equals( BUSCAR_SALIDA ) )
        {
            File archivoSalida = null;
            JFileChooser fc = new JFileChooser( "./data" );
            fc.setDialogTitle( "Seleccionar carpeta de salida" );
            fc.setSelectedFile( new File( "./reporte.txt" ) );

            int resultado = fc.showOpenDialog( this );

            if( resultado == JFileChooser.APPROVE_OPTION )
            {
                archivoSalida = fc.getSelectedFile( );
                txtArchivoSalida.setText( archivoSalida.getAbsolutePath( ) );
            }

        }
    }
}
