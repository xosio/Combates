/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sub;

/**
 *
 * @author Xosio
 */
public enum TEdificio {

    //Tipos de edificios
    NADA,
    TORRE,
    CASTILLO,
    FORTALEZA,
    CIUDAD;
    
    private int conservacion; //Estado de conservación del edificio sobre 100
    private boolean portonabierto; //Indica si el porton está abierto o cerrado
    private int piedras;//Contra arietes
    private int plomos;//Contra arietes y torres de asalto
    private int cdefensivas;//Contra torres de asalto y catapultas
    public static TEdificio setEdificio(short tipo) {

        TEdificio e = null;

        switch (tipo) {
            case 0:
            case 10:
            case 20:
            case 30: //No hay nada
                e = TEdificio.NADA;
                //Se puede edificar de todo
                break;
            case 1:
            case 21:
            case 31:
                
                e = TEdificio.TORRE;
                break;
            case 2:
            case 32: //Hay una torre. 
                e = TEdificio.CASTILLO;
                break;
            case 3:
                e = TEdificio.FORTALEZA;
        }
        return e;
    }
    
     
    public static int getTiempo(TEdificio e) {

        int tiempo = 0;

        switch (e) {
            case TORRE:
                tiempo = 40;
                break;
            case CASTILLO:
                tiempo = 140;
                break;
            case FORTALEZA:
                tiempo = 240;
                break;
        }
        return tiempo;
    }

    public static int getCoste(TEdificio e) {
        int r = 0;
        switch (e) {
            case TORRE:
                r = 10000;
                break;
            case CASTILLO:
                r = 100000;
                break;
            case FORTALEZA:
                r = 1000000;
        }
        return r;
    }
    
     public static int getCapacidad(TEdificio e) {
        int r = 0;
        switch (e) {
            case TORRE:
                r = 18000;
                break;
            case CASTILLO:
                r = 180000;
                break;
            case FORTALEZA:
                r = 1800000;
        }
        return r;
    }  
     
     public int getConservacion()
     {
         return conservacion;
     }
     public void setConservacion(int conservacion)
     {
         this.conservacion=conservacion;
     }
     public boolean getPortonabierto()
     {
         return portonabierto;
     }
     public void setPortonabierto(boolean portonabierto)
     {
         this.portonabierto=portonabierto;
     }
     public int getPiedras()
     {
         return piedras;
     }
     public void setPiedras(int piedras)
     {
         this.piedras=piedras;
     }
     public int getPlomos()
     {
         return plomos;
     }
     public void setPlomos(int plomos)
     {
         this.plomos=plomos;
     }
     public int getCdefensivas()
     {
         return cdefensivas;
     }
     public void setCdefensivas(int cdefensivas)
     {
         this.cdefensivas=cdefensivas;
     }
}
