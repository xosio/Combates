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

        if (campisresistentes > 0) {
            //Creamos el grupo de tropas....ncampesions a 100 de pericia y moral.
            TropasK campis = new TropasK(campisresistentes, 100, 100);
            Map<TTropas, TropasK> campesinos = new HashMap();
            campesinos.put(TTropas.CAMPESINOS, campis);
            GrupoTropas defensores = new GrupoTropas(campesinos, false, false);

            //Y realizamos el ataque en la aldea
            atacar(ataca, defensores, pCombateK.ALDEA);
            
            if (muevedefensor || huyedefensor || aniquiladefensor) { 
             //Los campesinos huyen despaVoridos :), realizamos la operación
                huyecampesinos=true;
                exito=true;
                operacion(ataca,feudo,true);
                System.out.println("Arrasan los mansos");
                return;
            } 
            System.out.println("No pueden con los campesinos");
            
        }
    }

    @Override
    public void operacionSinTropas(GrupoTropas ataca, FeudoK feudo, TCultura culturaagresor) {
        //Comprobamos si hay algo que arrasar
        if(pCombateK.ALDEA.getarrasemes(feudo.getMes())==0)
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
        
        //Comprobamos si las unidades pueden seguir actuando
        if (porcentaje != 0){System.out.println("Continuan con los mansos");
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
        double porcentaje;
        //Obtenemos la cantidad que pueden arrasar.
        double capacidad = ataca.poderArrase(feudo.getMes());
 
        if(capacidad==0){
            return 0.0;
        }
        if(aldea)
        {
            mansosarrasados=Math.min((int)capacidad, feudo.getProducidomansos());
            return 0.0;
        }
        else {
            reservaarrasada=Math.min((int)capacidad, feudo.getProducidoreserva());
            porcentaje=(capacidad-(double)(reservaarrasada))/capacidad;
        }
        return porcentaje;
    }

    //Función que completa el reporte con los datos necesarios
    public void completaReporte(Reporte reporte)
    {
        reporte.setReservaarrasada(reservaarrasada);
        if(exito)//Las tropas llegan a las propiedades de los campesinos
        {
            reporte.setMansosarrasados(mansosarrasados);
            reporte.setVictoriasobrecampis(true);
        }
        else{//Las tropas acaban en las propiedades del señor o son derrotadas por los campesinos
            if(!huyecampesinos)
            {
                escribeBajas(reporte);
            }
        }
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
}
