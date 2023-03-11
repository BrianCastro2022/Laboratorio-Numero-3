/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: PanelImagen.java 1328 2008-10-11 05:30:36Z ju-cort1 $
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

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Es el panel de la imagen
 */
public class PanelImagen extends JPanel
{
    // --------------------------------------------------------
    // Atributos de la interfaz
    // --------------------------------------------------------

    /**
     * Imagen del panel
     */
    private JLabel imagen;

    // --------------------------------------------------------
    // Constructores
    // --------------------------------------------------------

    /**
     * Construye el panel imagen
     */
    public PanelImagen( )
    {
        imagen = new JLabel( );
        imagen.setIcon( new ImageIcon( "./data/imagenes/imagen.png" ) );
        add( imagen );
    }

    // --------------------------------------------------------
    // Métodos
    // --------------------------------------------------------
}
