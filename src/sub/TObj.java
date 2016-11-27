/*
 * Tipos de objetos que pueden haber en los feudos y/o en los pedidos de compra
 */
package bayren.sub;

/**
 *
 * @author Xosio
 */
public enum TObj {

    ARADOS,
    SOLDADOS,
    CARRETAS,
    CABALLEROS,
    ARQUEROS,
    JINETES,
    LANCEROS,
    TORRES;

    public static short getTipo(TObj o) {
        short tipo = 0;
        switch (o) {
            case ARADOS:
                tipo = 1;
                break;
            case SOLDADOS:
                tipo = 2;
                break;
            case CARRETAS:
                tipo = 3;
                break;
            case CABALLEROS:
                tipo = 4;
                break;
            case LANCEROS:
                tipo = 5;
                break;
            case ARQUEROS:
                tipo = 6;
                break;
            case JINETES:
                tipo = 7;
                break;
            case TORRES:
                tipo = 8;
                break;    
        }
        return tipo;
    }
    
    
    public static double getTiempo(TObj o) {
        double tipo = 1000000;
        
        //Los que se puede hacer en un día:
        switch (o) {
            case ARADOS:
                tipo = 5.0;
                break;
            case SOLDADOS:
                tipo = 10.0;
                break;
            case CARRETAS:
                tipo = 0.3; //5 días una
                break;
            case CABALLEROS:
                tipo = 2.0; 
                break;
            case LANCEROS:
                tipo = 10.0;
                break;
            case ARQUEROS:
                tipo = 8.0;
                break;
            case JINETES:
                tipo = 2.0;
                break;
            case TORRES:
                tipo = 0.5;
                break;    
        }
        return tipo;
    }
    
    public static int getPrecio(TObj o) {
        int tipo = 1000000000;
        switch (o) {
            case ARADOS:
                tipo = 584;
                break;
            case SOLDADOS:
                tipo = 146;
                break;
            case CARRETAS:
                tipo = 1898;
                break;
            case CABALLEROS:
                tipo = 1168;
                break;
            case LANCEROS:
                tipo = 438;
                break;
            case ARQUEROS:
                tipo = 584;
                break;
            case JINETES:
                tipo = 730;
                break;
            case TORRES:
                tipo = 3796;
                break;    
        }
        return tipo;
    }
    
      public static TObj Obj(String objeto) {
         TObj o;
         o=TObj.valueOf(objeto.toUpperCase());
         return o;
     }
      
      
       public static TObj getObj(short tipo) {
        
        TObj o=null;
        //Los que se puede hacer en un día:
        switch (tipo) {
            case 1:
                o = TObj.ARADOS;
                break;
            case 2:
                o = TObj.SOLDADOS;
                break;
            case 3:
               o = TObj.CARRETAS;
                break;
            case 4:
                o = TObj.CABALLEROS;
                break;
            case 5:
                o = TObj.LANCEROS;
                break;
            case 6:
                   o=TObj.ARQUEROS;
                break;
            case 7:
                o=TObj.JINETES;
                break;
            case 8:
                o=TObj.TORRES;
                break;    
        }
        return o;
    }
       
       //*Devuelve el tipo de tropa de la tabla tipos de tropa que general el objeto de acuerdo a lo que hay en la base de datos.
       
       
       public static int getTipoTropa(TObj o) {
        int tipo = 0;
        switch (o) {
            
            case SOLDADOS:
                tipo = 2;
                break;
            case CABALLEROS:
                tipo = 1;
                break;
            case LANCEROS:
                tipo = 5;
                break;
            case ARQUEROS:
                tipo = 6;
                break;
            case JINETES:
                tipo = 7;
                break;
            case CARRETAS:
                tipo = 3;
                break;    
            
        }
        return tipo;
    }
    
     


}
