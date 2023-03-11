/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: DialogoAgregarCredito.java 1363 2008-10-22 21:50:11Z jua-gome $
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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Diálogo para agregar un crédito en el sistema
 */
public class DialogoAgregarCredito extends JDialog implements ActionListener
{
    // --------------------------------------------------------
    // Constantes
    // --------------------------------------------------------

    /**
     * Comando para agregar un crédito
     */
    private final static String ACEPTAR = "ACEPTAR";

    /**
     * Comando para cancelar la operación
     */
    private final static String CANCELAR = "CANCELAR";

    // --------------------------------------------------------
    // Atributos
    // --------------------------------------------------------

    /**
     * Panel padre de este diálogo
     */
    private PanelCredito principal;

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
     * Campo de texto para mostrar el nombre
     */
    private JTextField txtNombre;

    /**
     * Campo de texto para mostrar la cédula
     */
    private JTextField txtCedula;

    /**
     * Campo de texto para mostrar el monto
     */
    private JTextField txtMonto;

    /**
     * Campo de texto para mostrar el plazo
     */
    private JComboBox cbPlazo;

    /**
     * Campo de texto para mostrar la tasa
     */
    private JTextField txtTasa;

    /**
     * Botón aceptar
     */
    private JButton btnAceptar;

    /**
     * Botón cancelar
     */
    private JButton btnCancelar;

    // --------------------------------------------------------
    // Constructores
    // --------------------------------------------------------

    /**
     * Construye un nuevo dialogo para agregar un crédito
     * @param principalP es la referencia al panel padre del diálogo
     */
    public DialogoAgregarCredito( PanelCredito principalP )
    {
        principal = principalP;

        setLayout( new GridLayout( 6, 2 ) );
        this.setSize( 466, 200 );
        setTitle( "Agregar crédito" );

        lbNombre = new JLabel( "Nombre:" );
        txtNombre = new JTextField( );

        lbCedula = new JLabel( "Cédula:" );
        txtCedula = new JTextField( );

        lbMonto = new JLabel( "Monto:" );
        txtMonto = new JTextField( );

        lbPlazo = new JLabel( "Plazo:" );
        cbPlazo = new JComboBox( );
        cbPlazo.addItem( "12" );
        cbPlazo.addItem( "24" );
        cbPlazo.addItem( "36" );
        cbPlazo.addItem( "48" );
        cbPlazo.addItem( "60" );

        lbTasa = new JLabel( "Tasa Mensual (%):" );
        txtTasa = new JTextField( );

        btnAceptar = new JButton( "Aceptar" );
        btnAceptar.setActionCommand( ACEPTAR );
        btnAceptar.addActionListener( this );

        btnCancelar = new JButton( "Cancelar" );
        btnCancelar.setActionCommand( CANCELAR );
        btnCancelar.addActionListener( this );

        add( lbNombre );
        add( txtNombre );

        add( lbCedula );
        add( txtCedula );

        add( lbMonto );
        add( txtMonto );

        add( lbPlazo );
        add( cbPlazo );

        add( lbTasa );
        add( txtTasa );

        add( btnAceptar );
        add( btnCancelar );

        setModal( true );
        setLocationRelativeTo( null );
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
        if( e.getActionCommand( ).equals( ACEPTAR ) )
        {
            String nombre = txtNombre.getText( );
            String cedula = txtCedula.getText( );
            String montoS = txtMonto.getText( );
            String plazoS = cbPlazo.getSelectedItem( ).toString( );
            String tasaS = txtTasa.getText( );

            if( nombre != null && cedula != null && montoS != null && plazoS != null && tasaS != null && !nombre.equals( "" ) && !cedula.equals( "" ) )
            {
                try
                {
                    double monto = Double.parseDouble( montoS );
                    int plazo = Integer.parseInt( plazoS );
                    double tasa = Double.parseDouble( tasaS );
                    tasa = tasa / 100;

                    if( !cedula.matches( "[0-9]+" ) )
                    {
                        JOptionPane.showMessageDialog( null, "La cédula no puede contener letras", "Error", JOptionPane.ERROR_MESSAGE );
                    }
                    else
                    {
                        if( monto < 0 || plazo < 0 || tasa < 0 )
                        {
                            JOptionPane.showMessageDialog( null, "El monto, el plazo y la tasa deben ser valores positivos. " );
                        }
                        else
                        {
                            principal.agregarCredito( monto, plazo, tasa, nombre, cedula );
                            dispose( );
                        }
                    }
                }
                catch( NumberFormatException e1 )
                {
                    JOptionPane.showMessageDialog( null, "Los datos de monto, plazo y tasa deben ser valores numéricos", "Error", JOptionPane.ERROR_MESSAGE );
                }
            }
            else
                JOptionPane.showMessageDialog( null, "Todos los datos deben ser ingresados", "Error", JOptionPane.ERROR_MESSAGE );

        }
        else if( e.getActionCommand( ).equals( CANCELAR ) )
        {
            dispose( );
        }

    }
}
