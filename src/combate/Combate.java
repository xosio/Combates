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
 * @author Rosa
 */
public class Combate {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        /**
         * ****************** PARÁMETROS *************************************
         */
        //Acción a ejecutar..
        //SAQUEAR
        //ATACAR
        //ARRASAR
        //CONQUISTAR
        //ASALTAR
        //ASEDIAR
        String accion = "ASALTAR";

        //Mes en el que se produce el ataque. Solo se emplea con la acción arrsar
        int mes = 6;

        //Datos feudo. Poner en FeudosK
        int poblacion = 400;
        int campesinos = 80;
        int satisfaccion = 2 ;
        int producidomansos = 84000;
        int producidoreserva = 16800;
        int arados = 0;
        TCultura tcultura = TCultura.CRISTIANOS;
        TFeudo tfeudo = TFeudo.LLANO;
        boolean conpropietario = true;
        TEdificio niveledificio = TEdificio.TORRE;
        int conservacion=80;
        niveledificio.setConservacion(conservacion);

        //Los ponemos todo en un objeto FeudosK
        FeudoK feudo = new FeudoK(poblacion, campesinos, satisfaccion, producidomansos, producidoreserva, arados, tcultura, tfeudo, conpropietario, niveledificio,mes);

        //Cultura del agresor. Objeto de tipo TTCultura
        TCultura culturaagresor = TCultura.SARRACENOS;

       
        //Para cada tipo que haya en un grupo, ponemos los datos del tipo: cantidad, pericia y moral y lo de la carga...
        
        /** GRUPO DE ATAQUE**/
        Map<TTropas, TropasK> grupoat = new HashMap();
       //Arqueros
        grupoat.put(TTropas.ARQUEROS, new TropasK(100, 100, 100,0,0));
        //Ballesteros
        //grupoat.put(TTropas.BALLESTEROS, new TropasK(800, 100, 100,0,0));
        //Caballeros
        //Para deputación: Si no queremos que hayan tropas de un tipo NO ponerlas el Map...!!!!!!!!!!!!!!
        grupoat.put(TTropas.CABALLEROS, new TropasK(80, 100, 100,0,0));
        
        //Lanceros
        //grupoat.put(TTropas.LANCEROS, new TropasK(8, 100, 100,0,0));
       
        //Levass
        grupoat.put(TTropas.LEVAS, new TropasK(50, 100, 100,0,0));
        //Soldados
        grupoat.put(TTropas.SOLDADOS, new TropasK(50, 100, 100,0,0));

        //Escalas
        grupoat.put(TTropas.ESCALAS, new TropasK(10, 100, 100,0,0));
    //Los parámetros en retirada y en movimiento. Para que sirven????
        GrupoTropas at = new GrupoTropas(grupoat, false, false);

        /** GRUPO DE DEFENSA **/
        Map<TTropas, TropasK> grupodef = new HashMap();
        
        //Arqueros
        grupodef.put(TTropas.ARQUEROS, new TropasK(80, 100, 100,0,0));
        //Caballeros
        //Para depuración: Si no hay tropas de un tipo NO ponerlas el Map...!!!!!!!!!!!!!!
        //grupodef.put(TTropas.CABALLEROS, new TropasK(80, 100, 100,0,0););
        
        //Jinetes
        //grupodef.put(TTropas.JINETES, new TropasK(1000, 100, 100,0,0));
        
        //Lanceros 
        //grupodef.put(TTropas.LANCEROS, new TropasK(10, 100, 100,0,0));
        
        //Carretas
        //grupodef.put(TTropas.CARRETAS, new TropasK(10, 100, 100,0,0));
        
        //Exploradores
        //grupodef.put(TTropas.EXPLORADORES, new TropasK(5, 100, 100,0,0));
        
        GrupoTropas def = new GrupoTropas(grupodef, false, false);
        
        //Ejecutamos la contienda...
        //Acciones as=new Asalto();
        
        //System.out.println("grupo ataque");at.print();
        //System.out.println("grupo defensa");def.print();
        
        Contienda c = new Contienda(at, def, feudo, accion, culturaagresor, mes);
        c.resumen();
       
        //Y cojemos el informe de resultados...
        Reporte reporte = c.reporte();
       
        reporte.print();
       

        
        //as.escalas(at,def, feudo);
        System.out.println("Hay que actualizar las tropas tras el combate cuando pasan luego con los campesinos.");
    }

}
