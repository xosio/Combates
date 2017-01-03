
package sub;

/**
 *
 * @author Xosio
 * 
 * 
 */

//Las carretas: Maximo grupos de 90 carros, para poder transporar 180.000 denarios cada carreta.
//Lo cantidad de oros de lo que hay en la base de datos es directamente las monedas que lleva el grupo de carretas.

public enum TTropas {
    
    //TIPO DE TROPAS (Capcidad trasporte,unidad de consumo diaria,capacidad transporte oros, aniquilable)
    //Capacidad de transporte en libras. 
    
    CAMPESINOS(4,1,0, true, true,0),
    LEVAS(10,1,0, true, true,300),
    SOLDADOS(5,1,50, true, true,3000),
    ARQUEROS(5,1,50, true, true,1000),
    LANCEROS(5,1,50,true, true,1000),
    JINETES(20,2,50,true, false,0),
    CABALLEROS(9,3,100,true, false,0),
    CARRETAS(1800,4,1800,false, false,0);
    //A añadir: ballesteros, torres de asedio, arietes...
   
    
   //private final double poder; Poder: esto serían los parámetros de combate...
    private final int transporte;
    private final int consumo;
    private final int oros;
    private final boolean aniquilables;
    private final boolean apie;
    private final int poderarrasar;
    
    TTropas(int transporte, int consumo, int oros, boolean aniquilable, boolean apie, int poderarrasar) {
        //this.poder=poder;
        this.transporte=transporte;
        this.consumo=consumo;
        this.oros=oros;
        this.aniquilables=aniquilable;
        this.apie=apie;
        this.poderarrasar=poderarrasar;
    }
     
    /*public  double  getPoder() {
        return poder;
    }*/
    public int getPoderarrasar()
    {
        return poderarrasar;
    }
    public  int getTransporte() {
        return transporte;
    }

    public int getConsumo() {
        return consumo;
    }

    public boolean isAniquilables() {
        return aniquilables;
    }
    
    public boolean isApie()
    {
        return apie;
    }
    
    //Devuelve la carga maxima de oros: EN oros !!!!!!!!!!!!!!!!
    //Y por que no pongo directamente 180.000 oros....????
    //Porque quizás pueda transportar otras cosas que no sean oros, con un límite de carga de 1800
    //El factor 0,01 es el peso de las monedas...
    //...así que de momento lo dejo así...
    //En su momento el argumento podrá ser el tipo de objeto, y en función del peso del objeto devolverá la carga máximia
    public int getOros(TTropas t) {
        if ("CARRETAS".equals(t.toString())) {
            return (int) Math.floor(oros/0.01);
        }
        return oros;
    }
       
    public int getOros() {
        if ("CARRETAS".equals(this.toString())) {
            return (int) Math.floor(oros/0.01);
        }
        return oros;
    }
    
    
    
    
}
