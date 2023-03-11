/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: PanelTotales.java 1363 2008-10-22 21:50:11Z jua-gome $
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
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Es el panel que muestra los totales según el año del crédito actual
 */
public class PanelTotales extends JPanel
{

    // --------------------------------------------------------
    // Constantes
    // --------------------------------------------------------

    /**
     * Indicador de cantidad de decimales luego de la coma
     */
    private static final int NUM_DECIMALES = 2;

    // --------------------------------------------------------
    // Atributos de la interfaz
    // --------------------------------------------------------

    /**
     * Etiqueta pago interés
     */
    private JLabel lbPagoInteres;

    /**
     * Etiqueta pago abonos
     */
    private JLabel lbPagoAbonos;

    /**
     * Etiqueta pago total
     */
    private JLabel lbPagoTotal;

    /**
     * Campo de texto pago de interés
     */
    private JTextField txtPagoInteres;

    /**
     * Campo de texto pago de abonos
     */
    private JTextField txtPagoAbonos;

    /**
     * Campo de texto pago total
     */
    private JTextField txtPagoTotal;

    // --------------------------------------------------------
    // Constructores
    // --------------------------------------------------------

    /**
     * Construye un nuevo panel totales
     */
    public PanelTotales( )
    {
        setBorder( BorderFactory.createTitledBorder( "Totales para el año actual" ) );
        setLayout( new GridLayout( 3, 2 ) );

        lbPagoInteres = new JLabel( "Pago interés: $" );
        txtPagoInteres = new JTextField( );
        txtPagoInteres.setEditable( false );

        lbPagoAbonos = new JLabel( "Pago abonos: $" );
        txtPagoAbonos = new JTextField( );
        txtPagoAbonos.setEditable( false );

        lbPagoTotal = new JLabel( "Pago total: $" );
        txtPagoTotal = new JTextField( );
        txtPagoTotal.setEditable( false );

        add( lbPagoInteres );
        add( txtPagoInteres );

        add( lbPagoAbonos );
        add( txtPagoAbonos );

        add( lbPagoTotal );
        add( txtPagoTotal );
    }

    // --------------------------------------------------------
    // Métodos
    // --------------------------------------------------------

    /**
     * Cambia el valor mostrado de pago de interés
     * @param interes es el nuevo valor de pago de interés
     */
    public void cambiarPagoInteres( double interes )
    {
        DecimalFormat df = ( DecimalFormat )NumberFormat.getInstance( );
        df.applyPattern( " ###,###,###.##" );
        String interesS = df.format( redondear( interes ) );

        txtPagoInteres.setText( interesS );
    }

    /**
     * Cambia el valor mostrado de pago por abonos
     * @param abono es el nuevo valor de pago de abonos
     */
    public void cambiarPagoAbonos( double abono )
    {
        DecimalFormat df = ( DecimalFormat )NumberFormat.getInstance( );
        df.applyPattern( " ###,###,###.##" );
        String abonoS = df.format( redondear( abono ) );

        txtPagoAbonos.setText( abonoS );
    }

    /**
     * Cambia el valor mostrado de pago total
     * @param total es el nuevo valor total de pagos
     */
    public void cambiarPagoTotal( double total )
    {
        DecimalFormat df = ( DecimalFormat )NumberFormat.getInstance( );
        df.applyPattern( " ###,###,###.##" );
        String totalS = df.format( redondear( total ) );

        txtPagoTotal.setText( totalS );
    }

    // ------------------------------------------------------
    // Métodos auxiliares
    // ------------------------------------------------------

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
        tmp++;

        return ( double ) ( tmp / aux0 );
    }
}
