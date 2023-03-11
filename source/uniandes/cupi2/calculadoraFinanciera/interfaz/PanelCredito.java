/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: PanelCredito.java 1388 2008-10-28 23:28:46Z jua-gome $
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

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import uniandes.cupi2.calculadoraFinanciera.mundo.Credito;

/**
 * Es el panel que muestra y agrega créditos al sistema
 */
public class PanelCredito extends JPanel implements ActionListener
{

    // --------------------------------------------------------
    // Constantes
    // --------------------------------------------------------

    /**
     * Comando para agregar un nuevo crédito
     */
    private static final String AGREGAR = "AGREGAR";

    /**
     * Comando para modificar un combo
     */
    private static final String COMBO = "COMBO";

    /**
     * Indicador de cantidad de decimales luego de la coma
     */
    private static final int NUM_DECIMALES = 2;

    // --------------------------------------------------------
    // Atributos
    // --------------------------------------------------------

    /**
     * Panel principal de este panel
     */
    private PanelCentral principal;

    // --------------------------------------------------------
    // Atributos de la interfaz
    // --------------------------------------------------------

    /**
     * Etiqueta nombre
     */
    private JLabel lbNombre;

    /**
     * Etiqueta cédula
     */
    private JLabel lbCedula;

    /**
     * Etiqueta monto
     */
    private JLabel lbMonto;

    /**
     * Etiqueta plazo
     */
    private JLabel lbPlazo;

    /**
     * Etiqueta tasa
     */
    private JLabel lbTasa;

    /**
     * Etiqueta cuota
     */
    private JLabel lbCuota;

    /**
     * Combo box para mostrar la cédula
     */
    private JComboBox cbxCedula;

    /**
     * Campo de texto para mostrar el nombre
     */
    private JTextField txtNombre;

    /**
     * Campo de texto para mostrar el monto
     */
    private JTextField txtMonto;

    /**
     * Campo de texto para mostrar el plazo
     */
    private JTextField txtPlazo;

    /**
     * Campo de texto para mostrar la tasa
     */
    private JTextField txtTasa;

    /**
     * Campo de texto para mostrar la cuota
     */
    private JTextField txtCuota;

    /**
     * Botón para agregar un crédito
     */
    private JButton btnAgregar;

    // --------------------------------------------------------
    // Constructores
    // --------------------------------------------------------

    /**
     * Construye un nuevo panel crédito
     * @param principalP es el panel dueño de este
     */
    public PanelCredito( PanelCentral principalP )
    {
        principal = principalP;

        setLayout( new GridLayout( 7, 2 ) );
        setBorder( BorderFactory.createTitledBorder( "Crédito" ) );

        lbCedula = new JLabel( "Cédula:" );
        cbxCedula = new JComboBox( );
        cbxCedula.addActionListener( this );
        cbxCedula.setActionCommand( COMBO );

        lbNombre = new JLabel( "Nombre:" );
        txtNombre = new JTextField( );
        txtNombre.setEditable( false );

        lbMonto = new JLabel( "Monto:" );
        txtMonto = new JTextField( );
        txtMonto.setEditable( false );

        lbPlazo = new JLabel( "Plazo:" );
        txtPlazo = new JTextField( );
        txtPlazo.setEditable( false );

        lbTasa = new JLabel( "Tasa Mensual (%):" );
        txtTasa = new JTextField( );
        txtTasa.setEditable( false );

        lbCuota = new JLabel( "Cuota" );
        txtCuota = new JTextField( );
        txtCuota.setEditable( false );

        btnAgregar = new JButton( "Agregar" );
        btnAgregar.setActionCommand( AGREGAR );
        btnAgregar.addActionListener( this );

        add( lbCedula );
        add( cbxCedula );

        add( lbNombre );
        add( txtNombre );

        add( lbMonto );
        add( txtMonto );

        add( lbPlazo );
        add( txtPlazo );

        add( lbTasa );
        add( txtTasa );

        add( lbCuota );
        add( txtCuota );

        add( new JLabel( "" ) );
        add( btnAgregar );
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
        if( e.getActionCommand( ).equals( AGREGAR ) )
        {
            DialogoAgregarCredito dialogo = new DialogoAgregarCredito( this );
            dialogo.setVisible( true );
        }
        else if( e.getActionCommand( ).equals( COMBO ) )
        {
            JComboBox cb = ( JComboBox )e.getSource( );
            Credito credito = ( Credito )cb.getSelectedItem( );
            principal.actualizarCredito( credito );
        }

    }

    /**
     * Agrega un crédito al sistema
     * @param monto es el monto del crédito
     * @param plazo es el plazo del crédito
     * @param tasa es la tasa del crédito
     * @param nombre es el nombre del cliente
     * @param cedula es la cédula del cliente
     */
    public void agregarCredito( double monto, int plazo, double tasa, String nombre, String cedula )
    {
        principal.agregarCredito( monto, plazo, tasa, nombre, cedula );
    }

    /**
     * Retorna el crédito seleccionado en el combo box
     * @return el crédito seleccionado en el combo box
     */
    public Credito darCreditoActual( )
    {
        return ( Credito )cbxCedula.getSelectedItem( );
    }

    /**
     * Actualiza los datos del crédito
     * @param monto es el monto del crédito
     * @param plazo es el plazo del crédito
     * @param tasa es la tasa del crédito
     * @param cuota es la cuota mensual del crédito
     * @param nombre es el nombre del cliente
     */
    public void actualizarDatosCredito( double monto, int plazo, double tasa, double cuota, String nombre )
    {
        DecimalFormat df = ( DecimalFormat )NumberFormat.getInstance( );
        df.applyPattern( " ###,###,###.##" );

        String montoS = df.format( monto );
        String tasaS = df.format( ( tasa * 100 ) );
        String cuotaS = df.format( redondear( cuota ) );

        txtNombre.setText( nombre );
        txtMonto.setText( montoS );
        txtPlazo.setText( "" + plazo );
        txtTasa.setText( tasaS );
        txtCuota.setText( cuotaS );
    }

    /**
     * Actualiza las cédulas mostradas en el combo box
     * @param creditos Lista de créditos que se quiere mostrar en el combo box
     */
    public void actualizarCedulas( ArrayList creditos )
    {
        cbxCedula.removeAllItems( );
        for( int i = 0; i < creditos.size( ); i++ )
        {
            cbxCedula.addItem( creditos.get( i ) );
        }
    }

    /**
     * Selecciona el último crédito de la lista.
     */
    public void seleccionarUltimoCredito( )
    {
        cbxCedula.setSelectedIndex( cbxCedula.getItemCount( ) - 1 );
    }

    // ------------------------------------------------
    // Métodos auxiliares
    // ------------------------------------------------

    /**
     * Redondea un número real con el número de decimales explícito de la clase
     * @param numero es el número real
     * @return el número redondeado
     */
    private double redondear( double numero )
    {
        double aux0 = Math.pow( 10, NUM_DECIMALES );
        double aux = numero * aux0;
        int tmp = ( int )aux;

        return ( double ) ( tmp / aux0 );
    }

}
