/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package combate;

import sub.TTropas;

/**
 *
 * @author Rosa
 */
public class TropasK 
{ 
   
   //Los siguientes datos se obtienen de la base de datos: 
   
   private int cantidad; //Cantidad de tropas
   private final int moral; //Moral de las tropas
   private final int pericia;//Pericia 
   private final int cargaviveres;//Capacidad de carga de viveres de las unidades
   private final int cargaoros; //Capacidad de carga de oros de las unidades
   private final boolean bloqueado;//Para la maquinaria de asedio en montaje
   
/******************************** Constructores *****************/   
  
    //Constructor 
   
    TropasK(int cantidad, int moral, int pericia, int cargaviveres, int cargaoros,
            boolean bloqueado)
   {
       this.cantidad=cantidad;
       this.moral=moral;
       this.pericia=pericia;
       this.cargaviveres=cargaviveres;
       this.cargaoros=cargaoros;
       this.bloqueado=bloqueado;
   }
   
/******************* Métodos getter y setter***************************************/
   
   public boolean getBloqueado()
   {
       return bloqueado;
   }
   public int getcantidad()
   {
       return cantidad;
   }
   void setcantidad(int cantidad)
   {
       this.cantidad=cantidad;
   }
   
   public int getmoral()
   {
       return moral;   
   } 
   
   public int getpericia()
   {
       return pericia;
   }
 
   public int getcargaviveres()
   {
       return cargaviveres;
   }
/************************ Funciones   ***************************************/   
   
 
   public TropasK copia()
   {
       return new TropasK(cantidad, moral, pericia, cargaviveres, cargaoros, bloqueado);
   }
   public double getPoder()
   {
       return 0.0001*(1+moral)*pericia;
   }
   
   public double getPoderUnidades()
    {
        return cantidad*getPoder();
    }

}