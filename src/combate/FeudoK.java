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

    public FeudoK(int poblacion, int campesinos, int satisfaccion, int producidomansos, int producidoreserva, int arados, TCultura tcultura, TFeudo tfeudo, boolean conpropietario, TEdificio edificio) {
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

        double ajuste = tcultura.factorCultura(culturaagresor) * satisfaccionConquista(1);

        int ncampesinos = (int) (poblacion * ajuste);

        return ncampesinos;
    }

     //No sé que hace ni a que se refiere el parámetro indice... 
    private double satisfaccionConquista(int indice) {
        double satisfaccion = 1.0;
        switch (indice) {
            case 0:
                satisfaccion = Math.random() * 0.1;
                break;
            case 1:
                satisfaccion = 0.1 + Math.random() * 0.2;
                break;
            case 2:
                satisfaccion = 0.3 + Math.random() * 0.3;
                break;
            case 3:
                satisfaccion = 0.4 + Math.random() * 0.3;
                break;
            default:
                return 1.0;
        }
        return satisfaccion;

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
        
        //Falta concretar e implementar. Lo que está por tener algo...
        //Creo que ya tengo algunas funciones escritas sobre todo esto. Pero las tengo que buscar...
        
        if(arrasado>producidomansos)
        {
            arrasado=producidomansos;
        }
        
        int produccion=producidomansos;
        //Obtenemos las sobras de los campesinos hasta las siguientes cosechas
        int consumo=poblacion;
        int sobras=produccion-consumo-arrasado;
        //Comprobamos si la carga excede las sobras de los campesinos.
        if(sobras>=0)
        {
            return 0;
        }   
       
        return (int) Math.round(campesinos*(1.0-((double)(produccion-arrasado))/(double)(consumo)));
    }
    
    
}
