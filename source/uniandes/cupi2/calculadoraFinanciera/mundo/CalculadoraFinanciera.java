/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: CalculadoraFinanciera.java 1388 2008-10-28 23:28:46Z jua-gome $
 * Universidad de los Andes (Bogot� - Colombia)
 * Departamento de Ingenier�a de Sistemas y Computaci�n 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Ejercicio: n8_calculadoraFinanciera
 * Autor: Juan Camilo Cort�s Medina - 27-ago-2008
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.cupi2.calculadoraFinanciera.mundo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Es la clase que se encarga de manejar, cargar, persistir los cr�ditos. <br>
 * <b> inv: </b> <br>
 * creditos != null <br>
 * No hay dos cr�ditos iguales
 */
public class CalculadoraFinanciera
{
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Es la contenedora de cr�ditos
     */
    private ArrayList creditos;

    /**
     * Es el nombre del archivo de donde se cargan y se persisten los cr�ditos.
     */
    private String archivoCalculadora;

	private PrintWriter pluma;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Crea una nueva calculadora financiera e inicializa la contenedora de cr�ditos a partir de un archivo de persistencia
     * @param nombreArchivoCalculadora es la ruta del archivo serializado de persistencia. nombreArchivoCalculadora != null
     * @throws PersistenciaException si ocurre un error al cargar la informaci�n
     */
    public CalculadoraFinanciera( String nombreArchivoCalculadora ) throws PersistenciaException
    {
        archivoCalculadora = nombreArchivoCalculadora;
        File archivo = new File( archivoCalculadora );
        if( archivo.exists( ) )
        {
            // El archivo existe: se debe recuperar de all� el estado del modelo del mundo
            try
            {
                ObjectInputStream ois = new ObjectInputStream( new FileInputStream( archivo ) );
                creditos = ( ArrayList )ois.readObject( );
                ois.close( );
            }
            catch( Exception e )
            {
                throw new PersistenciaException( "Error fatal: imposible restaurar el estado del programa (" + e.getMessage( ) + ")" );
            }
        }
        else
        {
            creditos = new ArrayList( );
        }
        verificarInvariante( );
    }

    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------

    /**
     * Se crea un nuevo cr�dito en la calculadora financiera<br>
     * <b>pre:</b> La contenedora de cr�ditos fue inicializada. <br>
     * <b>post: </b> El cr�dito fue agregado a la contenedora si el cliente due�o de �ste no exist�a. <br>
     * @param monto es el monto del cr�dito. monto > 0
     * @param plazo es el plazo en meses para cancelar el cr�dito. plazo > 0
     * @param tasa es la tasa efectiva mensual del cr�dito. tasa > 0
     * @param nombre es el nombre del cliente. nombre != null
     * @param cedula es la c�dula del cliente. cedula != null
     * @return la posici�n del nuevo cr�dito o -1 si no se pudo agregar
     * @throws CreditoYaExisteException si se intenta agregar m�s de un cr�dito con la misma c�dula
     */
    public Credito crearCredito( double monto, int plazo, double tasa, String nombre, String cedula ) throws CreditoYaExisteException
    {
        Credito credito = null;
        Credito cre = darCreditoSegunCliente( cedula );
        if( cre == null )
        {
            credito = new Credito( monto, plazo, tasa, nombre, cedula );
            creditos.add( credito );
        }
        else
            throw new CreditoYaExisteException( "El cr�dito del cliente con c�dula: " + cedula + " ya existe" );

        verificarInvariante( );

        return credito;
    }

    /**
     * Retorna el total de dinero pagado de un cr�dito dado seg�n el a�o
     * @param credito es el cr�dito dado. credito != null
     * @param anio es el a�o. anio > 0
     * @return el total de dinero pagado
     */
    public double darTotalDineroPagado( Credito credito, int anio )
    {
        return credito.darDineroPagadoPorAnio( anio );
    }

    /**
     * Retorna el total de inter�s pagado de un cr�dito dado seg�n el a�o
     * @param credito es el cr�dito dado. credito != null
     * @param anio es el a�o. anio > 0
     * @return el total de dinero pagado.
     */
    public double darTotalInteresPagado( Credito credito, int anio )
    {
        return credito.darInteresPagadoPorAnio( anio );
    }

    /**
     * Retorna el total de abono pagado de un cr�dito dado seg�n el a�o
     * @param credito es el cr�dito dado. credito != null
     * @param anio es el a�o. anio > 0
     * @return el total de dinero pagado.
     */
    public double darTotalAbonoPagado( Credito credito, int anio )
    {
        return credito.darAbonoPagadoPorAnio( anio );
    }

    /**
     * Genera un reporte de amortizaci�n para los a�os seleccionado de una serie de cr�dito <br>
     * El archivo de entrada debe especificar los cr�ditos y los a�os para los que se quiere generar el reporte.
     * @param rutaArchivoEntrada es la ruta del archivo de entrada para la generaci�n del reporte. rutaArchivoEntrada != null
     * @param rutaArchivoSalida es la ruta del archivo de salida para la generaci�n del reporte. rutaArchivoSalida != null
     * @throws ReporteException si ocurre un error al generar el reporte
     */
    public void generarReporteAmortizacionSegunAnio( String rutaArchivoEntrada, String rutaArchivoSalida ) throws ReporteException
    {
        File archivoE = new File( rutaArchivoEntrada );
        File archivoS = new File( rutaArchivoSalida );

        if( archivoE.exists( ) )
        {
            String cedula = "";
            int anio = 0;

            try
            {
                BufferedReader lector = new BufferedReader( new FileReader( archivoE ) );
                BufferedWriter escritor = new BufferedWriter( new FileWriter( archivoS ) );

                String linea = lector.readLine( );
                while( linea != null )
                {
                    if( linea.contains( "#" ) )
                    {
                        linea = lector.readLine( );

                        // Extraemos la c�dula
                        String[] partes = linea.split( ":" );
                        if( partes[ 1 ] != null )
                        {
                            partes[ 1 ] = partes[ 1 ].trim( );
                            cedula = partes[ 1 ];
                        }
                        else
                        {
                            lector.close( );
                            escritor.close( );
                            throw new ReporteException( "La c�dula del cliente no est� especificada" );
                        }

                        linea = lector.readLine( );

                        partes = linea.split( ":" );
                        if( partes[ 1 ] != null )
                        {
                            partes[ 1 ] = partes[ 1 ].trim( );
                            try
                            {
                                anio = Integer.parseInt( partes[ 1 ] );

                                Credito credito = darCreditoSegunCliente( cedula );
                                if( credito != null )
                                {
                                    credito.generarSalidaReporte( escritor, anio );
                                    escritor.newLine( );
                                }
                                else
                                {
                                    lector.close( );
                                    escritor.close( );
                                    throw new ReporteException( "Uno de los cr�ditos especificados en el archivo no existe" );
                                }
                            }
                            catch( NumberFormatException e )
                            {
                                lector.close( );
                                escritor.close( );
                                throw new ReporteException( "El a�o de amortizaci�n debe ser un valor num�rico" );
                            }
                        }
                        else
                        {
                            lector.close( );
                            escritor.close( );
                            throw new ReporteException( "El a�o de amortizaci�n no est� especificado" );
                        }
                    }

                    linea = lector.readLine( );
                }
                lector.close( );
                escritor.close( );
            }
            catch( FileNotFoundException e )
            {
                throw new ReporteException( "Error Fatal: El archivo de entrada con los datos del cr�dito no existe. No es posible generar el reporte" );
            }
            catch( IOException e )
            {
                throw new ReporteException( "Error Fatal: Ocurri� un error inesperado al escribir el reporte. Verifique el formato del archivo e int�ntelo nuevamente." );
            }
        }
        else
        {
            throw new ReporteException( "Error Fatal: El archivo de entrada con los datos del cr�dito no existe. No es posible generar el reporte" );
        }
    }

    /**
     * Genera un reporte con la informaci�n total (mes a mes) de un cr�dito <br>
     * @param cedula es la c�dula del cliente due�o del cr�dito sobre el cual se quiere generar el reporte. cedula != null
     * @param anio es el a�o del cual se quiere obtener el reporte. anio > 0
     * @param rutaArchivoSalida ruta al archivo donde se va a generar el reporte. rutaArchivoSalida != null
     * @throws ReporteException si ocurre un error al generar el reporte.
     */
    public void generarSalidaReporte( String cedula, int anio, String rutaArchivoSalida ) throws ReporteException
    {
        Credito credito = darCreditoSegunCliente( cedula );

        if( credito != null )
        {
            File archivo = new File( rutaArchivoSalida );
            try
            {
                BufferedWriter escritor = new BufferedWriter( new FileWriter( archivo ) );

                credito.generarSalidaReporte( escritor, anio );
                escritor.close( );
            }
            catch( IOException e )
            {
                throw new ReporteException( "Error Fatal: Ocurri� un error inesperado al escribir el reporte. Int�ntelo nuevamente." );
            }
        }
        else
            throw new ReporteException( "Error: El cr�dito del cliente con c�dula: " + cedula + ", para el a�o: " + anio + "es inv�lido. Operaci�n cancelada" );
    }

    /**
     * Genera un reporte con la informaci�n total (mes a mes) de un cr�dito <br>
     * @param cedula es la c�dula del cliente due�o del cr�dito sobre el cual se quiere generar el reporte. cedula != null
     * @param rutaArchivoSalida ruta al archivo donde se va a generar el reporte. rutaArchivoSalida != null
     * @throws ReporteException si ocurre un error al generar el reporte.
     */
    public void generarReporteCreditoActual( String cedula, String rutaArchivoSalida ) throws ReporteException
    {
        Credito credito = darCreditoSegunCliente( cedula );

        if( credito != null )
        {
            File archivo = new File( rutaArchivoSalida );
            try
            {
                BufferedWriter escritor = new BufferedWriter( new FileWriter( archivo ) );

                int cantMeses = credito.darMeses( ).length;
                int cantAnios = cantMeses / 12;

                escritor.write( "Cliente: " + credito.darNombre( ) + "\n" );
                escritor.write( "CC: " + cedula + "\n" );
                escritor.write( "Monto: " + redondear( credito.darMonto( ) ) + "\n" );
                escritor.write( "Plazo: " + redondear( credito.darPlazo( ) ) + "\n" );
                escritor.write( "Tasa mensual: " + ( credito.darTasa( ) * 100 ) + "(%)\n" );

                int i = 0;
                while( i < cantAnios )
                {
                    credito.generarReporteActual( escritor, ( i + 1 ) );

                    i++;
                }
                escritor.close( );
            }
            catch( IOException e )
            {
                throw new ReporteException( "Error Fatal: Ocurri� un error inesperado al escribir el reporte. Int�ntelo nuevamente." );
            }
        }
        else
            throw new ReporteException( "Error: El cr�dito del cliente con c�dula: " + cedula + ". Operaci�n cancelada" );
    }

    /**
     * Retorna el cr�dito del cliente.
     * @param cedula es la c�dula del cliente. cedula != null
     * @return el cr�dito del cliente o null si no existe
     */
    public Credito darCreditoSegunCliente( String cedula )
    {
        Credito retorno = null;
        boolean encontrado = false;

        for( int i = 0; i < creditos.size( ) && !encontrado; i++ )
        {
            Credito temp = ( Credito )creditos.get( i );
            if( temp.darCedula( ).equals( cedula ) )
            {
                retorno = temp;
                encontrado = true;
            }
        }
        return retorno;
    }

    /**
     * Retorna los cr�ditos ingresados
     * @return los cr�ditos ingresados
     */
    public ArrayList darCreditos( )
    {
        return creditos;
    }

    // ----------------------------------------------------------------
    // M�todos auxiliares
    // ----------------------------------------------------------------

    /**
     * Redondea un n�mero real con el n�mero de decimales expl�cito de la clase
     * @param numero es el n�mero real
     * @return el n�mero redondeado
     */
    private double redondear( double numero )
    {
        double aux0 = Math.pow( 10, Credito.NUM_DECIMALES );
        double aux = numero * aux0;
        int tmp = ( int )aux;

        return ( double ) ( tmp / aux0 );
    }

    // -----------------------------------------------------------------
    // Persistencia
    // -----------------------------------------------------------------

    /**
     * Salva los datos de la calculadora financiera serializando la lista de cr�ditos en el archivo cuya ruta est� definida en archivoCalculadora
     * @throws PersistenciaException si ocurre un error al salvar
     */
    public void saveCalculadora( ) throws PersistenciaException
    {
        verificarInvariante( );

        try
        {
            ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream( archivoCalculadora ) );
            oos.writeObject( creditos );
            oos.close( );
        }
        catch( IOException e )
        {
            throw new PersistenciaException( "Error al salvar: " + e.getMessage( ) );
        }
    }

    // ------------------------------------------------------
    // Invariantes
    // --------------------------------------------------------

    /**
     * Verifica el invariante de la clase. <br>
     * <b> inv: </b> <br>
     * creditos != null <br>
     * No hay dos cr�ditos iguales
     */
    private void verificarInvariante( )
    {
        assert ( creditos != null ) : "Los cr�ditos no pueden ser nulos";
        assert ( !hayDosCreditosIguales( ) ) : "No puede haber dos cr�ditos iguales";
    }

    /**
     * Determina si hay dos cr�ditos iguales
     * @return true si hay dos cr�ditos iguales y false de lo contrario.
     */
    private boolean hayDosCreditosIguales( )
    {
        boolean repetidos = false;
        for( int i = 0; i < creditos.size( ) - 1 && !repetidos; i++ )
        {
            Credito credito1 = ( Credito )creditos.get( i );
            for( int j = ( i + 1 ); j < creditos.size( ) && !repetidos; j++ )
            {
                Credito credito2 = ( Credito )creditos.get( j );
                if( credito1.comprarCreditos( credito2 ) )
                {
                    repetidos = true;
                }
            }
        }
        return repetidos;
    }
    //------------------------------------------------------------------
    // punto 2
    
    public void cargueDeCreditos()throws FileNotFoundException, IOException, CreditoYaExisteException {
    	File archivo = new File("./data/CreditosCargar.txt");
    	
    	if(archivo.exists()){
    		FileReader fr = new FileReader(archivo);
    		BufferedReader lector = new BufferedReader(fr);
    		String linea = lector.readLine();
    		
    		while(linea!=null){
    			String[] datos = linea.split(",");
    			
    			String monto = datos[0];
    			double monto2 = Double.parseDouble(monto);
    			
    			String plazo = datos [1];
    			int plazo2 = Integer.parseInt(plazo);
    			
    			String tasa = datos[2];
    			double tasa2 = Double.parseDouble(tasa);
    			
    			String nombre = datos[3];
    			String cedula = datos [4];
    			
    			crearCredito(monto2, plazo2, tasa2, nombre, cedula);
    			linea = lector.readLine();
    			
    			
    		}
    		fr.close();
    		lector.close();
    	}
    }
    
    //------------------------------------------------------------------
    // Punto 3
    
    public void generarReporte()throws FileNotFoundException, IOException, CreditoYaExisteException {
    	boolean reporteCreditos = false;
    	
    	for (int i=0; i<creditos.size();i++) {
    		Credito miCredito = (Credito) creditos.get(i);
    		if ( miCredito.darPlazo()>24 && miCredito.darTasa()<0.1) {
    			reporteCreditos = true;
    			
    		}
    		
    	}
    	if (reporteCreditos==false) {
    		throw new IOException ("No existen creditos con baja tasa de interes");
    	}
    	
    	
    	File archivo = new File ("./data/reporteCreditos.txt");
    	pluma = new PrintWriter(archivo);
    	
    	pluma.println("------------Creditos a largo plazo con tasa baja------------------");
    	pluma.println("-----------------------------------------------------------------");
    	pluma.println("");
    	
    	
    	for (int i=0; i<creditos.size();i++) {
    		Credito miCredito = (Credito) creditos.get(i);
    		if ( miCredito.darPlazo()>24 && miCredito.darTasa()<0.1) {
    			
    			pluma.println("");
    			pluma.println("Cedula" +miCredito.darCedula()+"Nombre" + miCredito.darNombre());
    			pluma.println("=====================================================");
    			pluma.println("Monto:" + miCredito.darMonto()+ "pesos");
    			pluma.println("Plazo:" + miCredito.darPlazo()+ "meses");
    			pluma.println("Cuota:" + miCredito.darCuota()+ "pesos");
    			pluma.println("Tasa:" + miCredito.darTasa()+ "%");
    		}
    	}
    	
    pluma.close();
    		
    			
    			
    		
    	
    	
    	
    	
    	
    	
    	
    }
   
        
        


    // -----------------------------------------------------------------
    // Puntos de Extensi�n
    // -----------------------------------------------------------------

    /**
     * M�todo para la extensi�n 1
     * @return respuesta1
     */
     public String metodo1( )
     {
    	 try {
    		 cargueDeCreditos();
    		 return "Se a cargado exitosamente";
    	 }catch(Exception e) {
    		 return "ERROR" +e.getMessage();
    	 }
     }
     
        

    /**
     * M�todo para la extensi�n2
     * @return respuesta2
     */
    public String metodo2( )
    {
        return "Respuesta 2";
    }
}