/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: Credito.java 1365 2008-10-23 00:25:35Z jua-gome $
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Ejercicio: n8_calculadoraFinanciera
 * Autor: Juan Camilo Cortés Medina - 05-ago-2008
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package uniandes.cupi2.calculadoraFinanciera.mundo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Serializable;

/**
 * Esta clase representa un crédito. Dos créditos son iguales si la cédula del cliente es igual<br>
 * <b> inv: </b> <br>
 * monto > 0 <br>
 * plazo > 0 <br>
 * tasa > 0 <br>
 * cuota > 0 <br>
 * nombre != null && nombre != ""<br>
 * cedula != null && cedula != ""<br>
 * meses != null <br>
 * La cuota del crédito debe ser siempre igual al interés a pagar más el abono a capital de cada mes de amortización.
 */
public class Credito implements Serializable
{
    // --------------------------------------------------------
    // Constantes
    // --------------------------------------------------------

    /**
     * Indicador de versión para la serialización
     */
    private static final long serialVersionUID = 100L;

    /**
     * Indicador de cantidad de decimales luego de la coma
     */
    public static final int NUM_DECIMALES = 2;

    /**
     * Indicador de meses en un año
     */
    public static final int CANT_MESES = 12;

    /**
     * Etiqueta Mes para el reporte
     */
    public static final String MES = "Mes";

    /**
     * Etiqueta Interés para el reporte
     */
    public static final String INTERES = "Interés a pagar";

    /**
     * Etiqueta abono para el reporte
     */
    public static final String ABONO = "Abono a capital";

    /**
     * Etiqueta saldo para el reporte
     */
    public static final String SALDO = "Saldo Obligación";

    // --------------------------------------------------------
    // Atributos
    // --------------------------------------------------------

    /**
     * Es el monto total del crédito
     */
    private double monto;

    /**
     * Es el plazo en meses que se tiene para cancelar el crédito
     */
    private int plazo;

    /**
     * Es la tasa efectiva mensual del crédito.
     */
    private double tasa;

    /**
     * Es la cuota fija a pagar durante los meses del plazo del crédito.
     */
    private double cuota;

    /**
     * Contenedora para los meses de amortización del crédito
     */
    private MesAmortizacion[] meses;

    /**
     * Nombre del cliente
     */
    private String nombre;

    /**
     * Cédula del cliente
     */
    private String cedula;

    // --------------------------------------------------------
    // Constructores
    // --------------------------------------------------------

    /**
     * Crea un nuevo crédito con los datos básicos
     * @param montoP es el monto del crédito. montoP > 0
     * @param plazoP es el plazo en meses para cancelar el crédito. plazoP > 0
     * @param tasaP es la tasa efectiva mensual del crédito. tasaP > 0
     * @param nombreP es el nombre del cliente. nombreP != null
     * @param cedulaP es la cédula del cliente. cedulaP != null
     */
    public Credito( double montoP, int plazoP, double tasaP, String nombreP, String cedulaP )
    {
        monto = montoP;
        plazo = plazoP;
        tasa = tasaP;
        nombre = nombreP;
        cedula = cedulaP;

        cuota = calcularCuota( );
        meses = inicializarMeses( );

        verificarInvariante( );
    }

    // --------------------------------------------------------
    // Métodos
    // --------------------------------------------------------

    /**
     * Retorna la cantidad de dinero pagado durante un año dado. Corresponde a la cantidad de cuotas pagadas durante el año.
     * @param anio es el año del cual se quiere conocer cuanto se pago
     * @return cantidad pagada por el crédito.
     */
    public double darDineroPagadoPorAnio( int anio )
    {
        return cuota * CANT_MESES;
    }

    /**
     * Retorna la cantidad de dinero pagado por conceptos de interés durante el año dado
     * @param anio es el número del año.
     * @return cantidad de dinero pagado.
     */
    public double darInteresPagadoPorAnio( int anio )
    {
        double dineroPagado = 0.0;
        int indice = darMesIniAnio( anio );

        for( int i = 0; i < CANT_MESES; i++ )
        {
            MesAmortizacion elMes = meses[ indice ];
            dineroPagado += elMes.darInteresAPagar( );
            indice++;
        }
        return dineroPagado;
    }

    /**
     * Retorna la cantidad de dinero pagado por abonos durante el año dado
     * @param anio es el número del año.
     * @return cantidad de dinero pagado.
     */
    public double darAbonoPagadoPorAnio( int anio )
    {
        double dineroPagado = 0.0;
        int indice = darMesIniAnio( anio );

        for( int i = 0; i < CANT_MESES; i++ )
        {
            MesAmortizacion elMes = meses[ indice ];
            dineroPagado += elMes.darAbonoACapital( );
            indice++;
        }
        return dineroPagado;
    }

    /**
     * Retorna los meses del año dado
     * @param anio es el año especificado
     * @return un arreglo con los meses del año
     */
    public MesAmortizacion[] darMeses( int anio )
    {
        int indice = darMesIniAnio( anio );
        MesAmortizacion[] arreglo = new MesAmortizacion[12];
        for( int i = 0; i < CANT_MESES; i++ )
        {
            MesAmortizacion mes = meses[ indice ];
            arreglo[ i ] = mes;
            indice++;
        }
        return arreglo;
    }

    /**
     * Verifica si dos créditos son iguales. Dos créditos son iguales si tienen la misma cédula.
     * @param credito el crédito con el cual comparar. credito != null
     * @return true si son iguales y false de lo contrario
     */
    public boolean comprarCreditos( Credito credito )
    {
        return cedula.equals( credito.darCedula( ) );
    }

    /**
     * Imprime en un archivo los datos básicos del crédito y los movimientos, mes a mes, del mismo. <br>
     * <b> post: </b> Los datos del crédito fueron escritos en el reporte de salida <br>
     * @param escritor es el escritor del reporte de salida
     * @param anio es el número del año del cual se quiere escribir el reporte
     * @throws IOException si ocurre una excepción en la escritura del reporte
     */
    public void generarSalidaReporte( BufferedWriter escritor, int anio ) throws IOException
    {
        // Escribimos el encabezado
        escritor.write( "Cliente: " + nombre + "\n" );
        escritor.write( "CC: " + cedula + "\n" );
        escritor.write( "Monto: " + redondear( monto ) + "\n" );
        escritor.write( "Plazo: " + plazo + "\n" );
        escritor.write( "Tasa mensual: " + ( tasa * 100 ) + "(%)\n" );
        escritor.write( "Año amortización: " + anio + "\n" );
        escritor.newLine( );

        // Escribimos las columnas de la tabla
        String mes = MES;
        escritor.write( mes );
        escritor.write( "\t" );

        String interes = INTERES;
        escritor.write( interes );
        escritor.write( "\t" );

        String abono = ABONO;
        escritor.write( abono );
        escritor.write( "\t" );

        String saldo = SALDO;
        escritor.write( saldo );
        escritor.newLine( );

        int indice = ( anio - 1 ) * CANT_MESES;
        for( int i = 0; i < CANT_MESES; i++ )
        {
            meses[ indice ].generarReporteSalida( escritor );
            escritor.newLine( );
            indice++;
        }
    }

    /**
     * Imprime en un archivo los movimientos, mes a mes del crédito. <br>
     * <b> post: </b> Los datos del crédito fueron escritos en el reporte de salida <br>
     * @param escritor es el escritor del reporte de salida
     * @param anio es el número del año del cual se quiere escribir el reporte
     * @throws IOException si ocurre una excepción en la escritura del reporte
     */
    public void generarReporteActual( BufferedWriter escritor, int anio ) throws IOException
    {
        // Escribimos el encabezado
        escritor.newLine( );
        escritor.write( "Año amortización: " + anio + "\n" );
        escritor.newLine( );

        // Escribimos las columnas de la tabla
        String mes = MES;
        escritor.write( mes );
        escritor.write( "\t" );

        String interes = INTERES;
        escritor.write( interes );
        escritor.write( "\t" );

        String abono = ABONO;
        escritor.write( abono );
        escritor.write( "\t" );

        String saldo = SALDO;
        escritor.write( saldo );
        escritor.newLine( );

        int indice = ( anio - 1 ) * CANT_MESES;
        for( int i = 0; i < CANT_MESES; i++ )
        {
            meses[ indice ].generarReporteSalida( escritor );
            escritor.newLine( );
            indice++;
        }
    }

    /**
     * Retorna el monto del crédito
     * @return el monto del crédito
     */
    public double darMonto( )
    {
        return monto;
    }

    /**
     * Retorna el plazo en meses del crédito
     * @return el plazo en meses del crédito
     */
    public int darPlazo( )
    {
        return plazo;
    }

    /**
     * Retorna la tasa de interés del crédito
     * @return la tasa de interés del crédito
     */
    public double darTasa( )
    {
        return tasa;
    }

    /**
     * Retorna la cuota del crédito
     * @return la cuota del crédito
     */
    public double darCuota( )
    {
        return cuota;
    }

    /**
     * Retorna el nombre del cliente
     * @return el nombre del cliente
     */
    public String darNombre( )
    {
        return nombre;
    }

    /**
     * Retorna la cédula del cliente
     * @return la cédula del cliente
     */
    public String darCedula( )
    {
        return cedula;
    }

    /**
     * Retorna los meses de plazo del crédito
     * @return los meses de plazo del crédito
     */
    public MesAmortizacion[] darMeses( )
    {
        return meses;
    }

    /**
     * Representación del crédito en string.
     * @return el nombre del cliente.
     */
    public String toString( )
    {
        return cedula;
    }

    // --------------------------------------------------------
    // Métodos auxiliares
    // --------------------------------------------------------

    /**
     * Inicializa los meses de amortizaciones del crédito. <b> pre: </b> <br>
     * El monto ya fue inicializado. monto > 0. <br>
     * El plazo ya fue inicializado. plazo > 0. <br>
     * La tasa ya fue inicializada. tasa > 0. <br>
     * @return la lista de los meses de amortización del crédito.
     */
    private MesAmortizacion[] inicializarMeses( )
    {
        MesAmortizacion[] meses = new MesAmortizacion[plazo];
        double tempPrestamo = monto;
        for( int i = 0; i < plazo; i++ )
        {
            double interes = ( tempPrestamo * tasa );
            double abono = cuota - interes;
            double saldo = tempPrestamo - abono;
            tempPrestamo = tempPrestamo - abono;
            if( i == plazo - 1 && ( saldo < 0 && saldo > -0.001 ) )
                saldo = 0;
            meses[ i ] = new MesAmortizacion( ( i + 1 ), interes, abono, saldo );
        }
        return meses;
    }

    /**
     * Calcula la cuota mensual de un crédito. <b> pre: </b> <br>
     * El monto ya fue inicializado. monto > 0. <br>
     * El plazo ya fue inicializado. plazo > 0. <br>
     * La tasa ya fue inicializada. tasa > 0. <br>
     * @return la cuota mensual del crédito.
     */
    private double calcularCuota( )
    {
        return monto * ( ( Math.pow( 1 + tasa, plazo ) * tasa ) / ( Math.pow( 1 + tasa, plazo ) - 1 ) );
    }

    /**
     * Retorna el índice del mes en el marco del crédito en el que inicia el año.
     * @param anio es el año
     * @return número del mes
     */
    private int darMesIniAnio( int anio )
    {
        return ( ( anio - 1 ) * CANT_MESES );
    }

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

    // ------------------------------------------------------
    // Invariantes
    // --------------------------------------------------------

    /**
     * Verifica el invariante de la clase. <br>
     * <b> inv: </b> <br>
     * monto > 0 <br>
     * plazo > 0 <br>
     * tasa > 0 <br>
     * cuota > 0 <br>
     * nombre != null && nombre != ""<br>
     * cedula != null && cedula != ""<br>
     * meses != null <br>
     * La cuota del crédito debe ser siempre igual al interés a pagar más el abono a capital de cada mes de amortización.
     */
    private void verificarInvariante( )
    {
        assert ( monto > 0 ) : "El monto debe ser positivo";
        assert ( plazo > 0 ) : "El plazo debe ser positivo";
        assert ( tasa > 0 ) : "La tasa debe de ser positiva";
        assert ( cuota > 0 ) : "La cuota debe de ser positiva";
        assert ( meses != null ) : "Los meses no pueden ser nulos";
        assert ( nombre != null && !nombre.equals( "" ) ) : "El nombre del cliente no debe ser nulo";
        assert ( cedula != null && !cedula.equals( "" ) ) : "La cédula del cliente no debe ser nula";
        assert ( verificarCuotaMesesAmortizacion( ) ) : "La cuota del crédito debe ser igual al abono a capital sumado a los intereses a pagar de cada mes de amortización.";
    }

    /**
     * Verifica que para cada mes de amortización, la suma del abono a capital más los intereses a pagar debe de ser igual a la cuota del crédito.
     * @return true si se verifica para cada mes de amortización o false de lo contrario.
     */
    private boolean verificarCuotaMesesAmortizacion( )
    {
        boolean esCorrecta = true;
        for( int i = 0; i < meses.length && esCorrecta; i++ )
        {
            MesAmortizacion mes = meses[ i ];
            if( cuota - mes.darAbonoACapital( ) + mes.darInteresAPagar( ) < 0.0001 )
                esCorrecta = false;
        }
        return esCorrecta;
    }
}
