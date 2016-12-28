/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package combate;

import java.util.HashMap;
import java.util.Map;
import sub.TCultura;
import sub.TEdificio;
import sub.TFeudo;
import sub.TTropas;

/**
 *
 * @author Xosio
 */
public class FeudoK {

    private int poblacion; //=400;
    private int campesinos; //=80;
    private int satisfaccion; //=2;
    private int producidomansos; //=84000;
    private int producidoreserva; //=0;
    private int arados; //=0;
    private TCultura tcultura; //=2; //Será un objeto del tipo TCultura
    private TFeudo tfeudo;
    private boolean conpropietario; //=true;
    private TEdificio edificio; //=2;
    private int mes;

    public FeudoK(int poblacion, int campesinos, int satisfaccion, int producidomansos, int producidoreserva, int arados, TCultura tcultura, TFeudo tfeudo, boolean conpropietario, TEdificio edificio, int mes) {
        this.poblacion = poblacion;
        this.campesinos = campesinos;
        this.satisfaccion = satisfaccion; ///?????
        this.producidomansos = producidomansos;
        this.producidoreserva = producidoreserva;
        this.arados = arados;
        this.tcultura = tcultura;
        this.tfeudo = tfeudo;
        this.conpropietario = conpropietario;
        this.edificio = edificio;
        this.mes=mes;
    }

    public boolean isConpropietario() {
        return conpropietario;
    }

    public TFeudo getTfeudo() {
        return tfeudo;
    }

    public int getSatisfaccion() {
        return satisfaccion;
    }

    public int getPoblacion() {
        return poblacion;
    }

    public TEdificio getEdificio() {
        return edificio;
    }

    public int getProducidoreserva()
    {
        return producidoreserva;
    }
    public int getProducidomansos()
    {
        return producidomansos;
    }
    
    public int getMes()
    {
        return mes;
    }
    
    public void setMes(int mes)
    {
        this.mes=mes;
    }
    /**
     * Devuelve si la accion causa molestia sobre los campesinos en Caso de conquista.
     *
     * @param accion
     * @return
     */
    public int molestiaConquista(TCultura culturaagresor) {

        return campesinosConquista(culturaagresor);
    }

    public int campesinosConquista(TCultura culturaagresor) {

        double ajuste = tcultura.factorCultura(culturaagresor) * satisfaccionConquista();

        int ncampesinos = (int) (poblacion * ajuste);

        return ncampesinos;
    }

     //No sé que hace ni a que se refiere el parámetro indice... 
    private double satisfaccionConquista() {
        double variacion = 1.0;
        switch (satisfaccion) {
            case 0:
                variacion = Math.random() * 0.1;
                break;
            case 1:
                variacion = 0.1 + Math.random() * 0.2;
                break;
            case 2:
                variacion = 0.3 + Math.random() * 0.3;
                break;
            case 3:
                variacion = 0.4 + Math.random() * 0.3;
                break;
            default:
                return 1.0;
        }
        return satisfaccion;

    }
    private double satisfaccionArrase() {
        double variacion = 1.0;
        switch (satisfaccion) {
            case 0:
                variacion =3.0+ Math.random() * 0.5;
                break;
            case 1:
                variacion = 2.0 + Math.random() * 0.5;
                break;
            case 2:
                variacion = 1.0 + Math.random() * 0.3;
                break;
            case 3:
                variacion = 0.5 + Math.random() * 0.2;
                break;
            default:
                return 1.0;
        }
        return variacion;
    }
    
    //Falta concretar e implementar. Lo que hay por tener algo...
    public int molestiaSaqueo(int capacidadsaqueo)
    {
        boolean molesta=true;
        double porcentaje=1.0;//Nos da en tanto por uno la molestia de los aldeanos por la accón
        return campesinos;
    }
    /*Genera el grupo de levas formadas por campesinos que se enfrentan a los enemigos
     * Tanto en el saqueo como en el arrase participa toda la población.
     * La cantidad de aldeanos que participa depende de Molesta y Nivel de satisfacción.
     * Para el nivel de satisfacción se puede usar la misma función que para arrasar,
     * situada en pCombate. Y para moles, se puede usar la que ya tienes o la misma
     * que uso para arrasar.
    */

     public int molestiaArrase(int arrasado)
    {             
        //Calculamos los meses en los que los campesinos se tienen que alimentar con la producción arrasada.
        int acosechas=mesesEntreCosechas();
        //Obtenemos el consumo correspondiente
        int consumo=poblacion*acosechas*31;
        
        //Calculamos los campesinos molestos
        int campis=(int)(Math.round(satisfaccionArrase()*campesinos*((double)(consumo-arrasado))/(double)(consumo)));
       
        return  Math.min(campesinos, campis);
    }
    //Nos da el periodo entre las próximas cosechas y las siguientes
     public int mesesEntreCosechas()
     {
         int periodo=0;
         if((mes>=4)&&(mes<=8))
         {
             periodo=8;
         }else
         {
             periodo=4;
         }
         return periodo;
     }
     //Nos da los meses que faltan hasta las próximas cosechas.
    public int mesesAcosechas()
    {
        int meses=0;
        if(mes>=8){
            meses=16-mes;
        }else
        {
            if(mes<4)
            {
                meses=4-mes;
            }else
            {
                meses=8-mes;
            }
        }
        return meses;
    }
}
