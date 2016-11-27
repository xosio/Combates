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
   
    private TTropas tr; 



    //En el combate los exploradores se unen a los jinetes.
       
    private int tipotropasId;
   //Arquero------------>0
   //Caballero---------->1
   //Jinete------------->2
   //Lancero------------>3
   //Levas-------------->4
   //Soldado------------>5
   private int cantidad; //Cantidad de tropas
   private int moral; //Moral de las tropas
   private int pericia;//Pericia 
   private int cargaviveres;//Capacidad de carga de viveres de las unidades
   
   
  
/******************************** Constructores *****************/   
   //Constructor nulo
   TropasK()
   {
       tipotropasId=4;
       cantidad=0;
       moral=0;
       pericia=0;
   }
    //Constructor 
   TropasK(int idtropas, int cantidad, int moral, int pericia)
   {
       this.cantidad=cantidad;
       this.moral=moral;
       this.pericia=pericia;
       this.tipotropasId=idtropas;
   }
   
     //Constructor Xosio
   TropasK(TTropas tr, int cantidad, int moral, int pericia)
   {
       this.cantidad=cantidad;
       this.moral=moral;
       this.pericia=pericia;
       this.tr=tr;
   }
   
    TropasK(int cantidad, int moral, int pericia)
   {
       this.cantidad=cantidad;
       this.moral=moral;
       this.pericia=pericia;
       //this.tr=tr;
   }
   
/******************* Métodos getter y setter***************************************/
    public TTropas getTr() {
        return tr;
    }

   
   
   
   
   int getcantidad()
   {
       return cantidad;
   }
   
   
   void setcantidad(int c)
   {
       if(c<0)
       {
           cantidad=0;
       }
       else
       {
           cantidad=c;
       }   
   }
   int getmoral()
   {
       return moral;   
   } 
   
   void setmoral(int m)
   {
       moral=m;   
   }
   int getpericia()
   {
       return pericia;
   }
   void setpericia(int p)
   {
       pericia=p;
   }
   int gettipotropasId()
   {
       return  tipotropasId;
   }
   void settipotropasId(int tipo)
   {
       tipotropasId=tipo;
   }
   int getcargaviveres()
   {
       return cargaviveres;
   }
/************************ Funciones   ***************************************/   
   
 
  //Introduce el número de bajas que se produce para un valor introducido 
   void setbajas(int a)
   {
       if(cantidad<=a)
       {
           cantidad=0;
       }
       else
       {
            cantidad=cantidad-a;
       }
   }
   
   double getPoder()
   {
       return 0.0001*(1+moral)*pericia;
   }
   
   double getPoderUnidades()
    {
        return cantidad*getPoder();
    }
//Para borrar
   void print(String tipo)
   {
       System.out.println(tipo+" "+cantidad+" moral "+moral+" pericia " + pericia);
   }
}
