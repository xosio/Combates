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
     public AccionArrasar() {
        operacion = "ARRASAR";
    }

    //Desarrolla un combate donde no hay tropas defensivas y devuelve la acción realizada
    @Override
    public void combateCampesinos(GrupoTropas ataca, FeudoK feudo, TCultura culturaagresor) {

        //Calcular el poder de arrase del grupo ataca
        int arrasado = ataca.poderArrase();
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
                //Los campesinos huyen despaVoridos :)
                exito = true;
                return;
            } else {
                return;
            }
        }
    }

    @Override
    public void operacionSinTropas(GrupoTropas ataca, FeudoK feudo, TCultura culturaagresor) {
        //Primero ejecutamos la operación en las propiedades del señor
        double porcentaje = operacion(ataca, feudo, false);

        if (porcentaje == 0) //Las unidades cumplen su misión con las propiedades del señor
        {
            return;
        } else //Las unidades pueden seguir actuando
        {
            //Actualizamos la cantidad de tropas que siguen con la operación
            GrupoTropas aux = siguenTropas(ataca, porcentaje);
            //aux.print();
            //Intervienen los campesinos en el proceso para defender sus posesiones

            //Propuesta.... ???? Las tropas que atacan a los campesinos para seguir el saqueo ¿son solo una parte del total?
            //Que solo intervengan el el ataca las tropas que pueden saquar
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

        //Calculamos la capacidad de carga de las tropas ataca.
        //Realmente, tenemos dos capacidades: oros y viveres...
        int capacidad = ataca.carga();
        double porcentaje = 0.0;

        if (aldea == true) {
            /*
             * 
             * 1º- Reducimos la capacidad de carga de las tropas a la cuarta parte,
             * ya que no es lo mismo ir a una armería o granero y cargar, que tener 
             * que ir rebuscando casa a casa.
             * 2º- Solo pueden saquear hasta un 10% de las propiedades de los campesinos
             * Aquí el orden sería:
             * - Tesoro de la aldea.
             * - Propiedades de los campesinos, que pueden provenir de comercio
             * y demás, al 100% de su valor
             * - Víveres de la aldea.
             */
            return 0.0;
        } else {
            /*Creo que el criterio más acertado para los saqueos es:
             * - Tesoro del señor
             * - Equipos, las unidades cargan el 80% del valor de cada equipo.
             * - Arados, igual que con los equipos.
             * - Granero del señor
             */
        }

        //Realmente, más que porcentajes, seria más adecuado devolver la cantidad realmente saqueada.
        return porcentaje;
    }

    //Da la cantidad de tropas correspondiente a un porcentaje dado
    //Otra vez, mas que porcentaje, bastaría con actualizar la carga que puede soportar el grupo de tropas...
    public GrupoTropas siguenTropas(GrupoTropas grupo, double porcentaje) {

        return grupo;
    }

 //Bueno. Veremos las funciones para saquear los feudos y donde las escribimos...  Aquí o en FeudoK..
}
