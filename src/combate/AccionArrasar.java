/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package combate;

import java.util.HashMap;
import java.util.Map;
import sub.TCultura;
import sub.TTropas;

/**
 *
 * @author Xosio
 */
public class AccionArrasar extends Acciones{
    private int reservaarrasada;
    private int mansosarrasados;
    private String mensaje;
    
     public AccionArrasar() {
        operacion = "ARRASAR";
    }

    //Desarrolla un combate donde no hay tropas defensivas y devuelve la acción realizada
    @Override
    public void combateCampesinos(GrupoTropas ataca, FeudoK feudo, TCultura culturaagresor) {

        //Calcular el poder de arrase del grupo ataca
        int arrasado = Math.min(ataca.poderArrase(feudo.getMes()),feudo.getProducidomansos());
        int campisresistentes = feudo.molestiaArrase(arrasado);
System.out.println("Campesinos "+campisresistentes);
        if (campisresistentes > 0) {
            //Creamos el grupo de tropas....ncampesions a 100 de pericia y moral.
            TropasK campis = new TropasK(campisresistentes, 100, 100);
            Map<TTropas, TropasK> campesinos = new HashMap();
            campesinos.put(TTropas.CAMPESINOS, campis);
            GrupoTropas defensores = new GrupoTropas(campesinos, false, false);

            //Y realizamos el ataque en la aldea
            /*
             */ atacar(ataca, defensores, pCombateK.ALDEA);
             /* lo cambio por
             *
            Contienda caux=new Contienda(ataca,defensores,feudo,"ATACAR",null,0);
            Reporte reporteAux=new Reporte();
            reporteAux=caux.reporte();
            */
            //if (reporteAux.isMoverdefensor() || reporteAux.isHuyedefensor() || reporteAux.getdefensoraniquilado()) {
              if (muevedefensor || huyedefensor || aniquiladefensor) { 
             //Los campesinos huyen despaVoridos :), realizamos la operación
                operacion(ataca,feudo,true);
                System.out.println("Arrasan los mansos");
                return;
            } else {
                //Metemos las bajas en ambos bandos producidas en el combate
                //reporte.anadirBajas(reporteAux);
                  System.out.println("No pueden con los campesinos");
                return;
            }
        }
    }

    @Override
    public void operacionSinTropas(GrupoTropas ataca, FeudoK feudo, TCultura culturaagresor) {
        //Comprobamos si hay algo que arrasar
        if((feudo.getMes()==9)||(feudo.getMes()==10))
        {
            mensaje="No hay nada que arrasar, los campos están totalmente desiertos";
            return;
        }
        //Comprobamos que hay unidades con poder de arrasar (unidades a pie)
        if(!ataca.hayUnidadesApie())
        {
            mensaje="Nuestras unidades consideran humillante realizar esta acción";
            return;
        }
        //Primero ejecutamos la operación en las propiedades del señor
        double porcentaje = operacion(ataca, feudo, false);
        System.out.println("Arrasan las reservas");
        
        if (porcentaje == 0) //Las unidades cumplen su misión con las propiedades del señor
        {           
            return;
        } else //Las unidades pueden seguir actuando
        {System.out.println("Continuan con los mansos");
            //Actualizamos la cantidad de tropas que siguen con la operación
            GrupoTropas aux = siguenTropas(ataca, porcentaje);
            //Intervienen los campesinos en el proceso para defender sus posesiones
            combateCampesinos(aux, feudo, culturaagresor);
        }
    }

    private double operacion(GrupoTropas ataca, FeudoK feudo, boolean aldea) {
        /*Esta variable nos dice que porcentaje de las unidades pueden seguir con 
         * la operación. Es decir, si cargo el 60% de mi capacidad, devuelvo 0.4
         * que es la cantidad que seguiría en la aldea, después de las 
         * propiedades del señor.
         * En la aldea el valor es 0, ya que no pueden seguir.
         */
        double porcentaje = 0.0;
        //Obtenemos la cantidad que pueden arrasar.
        int capacidad = ataca.poderArrase(feudo.getMes());
 
        if(capacidad==0){
            return 0.0;
        }
        if(aldea)
        {
            mansosarrasados=Math.min(capacidad, feudo.getProducidomansos());
            return 0.0;
        }
        else {
            reservaarrasada=Math.min(capacidad, feudo.getProducidoreserva());
            porcentaje=(capacidad-(double)(reservaarrasada))/capacidad;
        }
        return porcentaje;
    }

    //Da la cantidad de tropas correspondiente a un porcentaje dado
    public GrupoTropas siguenTropas(GrupoTropas grupo, double porcentaje) {

        return grupo;
    }

    public void setMansosarrasados(int cantidad)
    {
        this.mansosarrasados=cantidad;
    }
    public int getMansosarrasados()
    {
        return mansosarrasados;
    }
    
    public void setReservaarrasada(int cantidad)
    {
        this.reservaarrasada=cantidad;
    }
    public int getReservaarrasada()
    {
        return reservaarrasada;
    }
    
    public String getMensaje()
    {
        return mensaje;
    }
    public void setMensaje(String mensaje)
    {
        this.mensaje=mensaje;
    }
 //Bueno. Veremos las funciones para saquear los feudos y donde las escribimos...  Aquí o en FeudoK..
}
