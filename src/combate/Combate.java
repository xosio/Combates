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
        String accion = "ARRASAR";

        //Mes en el que se produce el ataque. Solo se emplea con la acción arrsar
        int mes = 3;

        //Datos feudo. Poner en FeudosK
        int poblacion = 400;
        int campesinos = 80;
        int satisfaccion = 2;
        int producidomansos = 84000;
        int producidoreserva = 0;
        int arados = 0;
        TCultura tcultura = TCultura.CRISTIANOS;
        TFeudo tfeudo = TFeudo.LLANO;
        boolean conpropietario = true;
        TEdificio niveledificio = TEdificio.CASTILLO;

        //Los ponemos todo en un objeto FeudosK
        FeudoK feudo = new FeudoK(poblacion, campesinos, satisfaccion, producidomansos, producidoreserva, arados, tcultura, tfeudo, conpropietario, niveledificio);

        //Cultura del agresor. Objeto de tipo TTCultura
        TCultura culturaagresor = TCultura.CRISTIANOS;

        /**
         * *********************GRUPO DE
         * ATAQUE**********************************
         */
        //TTropas:
        //Arquero------------>0--->ARQUEROS
        //Caballero---------->1--->CABALLEROS
        //Jinete------------->2--->JINETES
        //Lancero------------>3--->LANCEROS
        //Levas-------------->4--->LEVAS
        //Soldado------------>5--->SOLDADOS
        //CAMPESINOS
        //Se puedn añadir en TTropas lo que queramos:
        //EXPLORADORES
        //TORRESASALTO
        //...
        //Para cada tipo que haya en un grupo, ponemos los datos del tipo: cantidad, pericia y moral y lo de la carga...
        Map<TTropas, TropasK> grupoat = new HashMap();
        //at.set(0,00,100,100);
        //unidades1.add(attr1);
        //at.set(1,0,100,100);
        //at.set(2,00,100,100);
        //at.set(3,100, 100, 100);

        //TropasK no necesita tener la propiedad TTropas. Pero de momento está
        TropasK trat3A = new TropasK(TTropas.LANCEROS, 100, 100, 100);

        grupoat.put(TTropas.LANCEROS, trat3A);
        //at.set(4,000,100,100);
        //at.set(5, 00, 100, 100);

        //Los parámetros en retirada y en movimiento. Para que sirven????
        GrupoTropas at = new GrupoTropas(grupoat, false, false);

        /**
         * *********************GRUPO DE
         * DEFENSA**********************************
         */
        Map<TTropas, TropasK> grupodef = new HashMap();

        //at1.set(0,00,100,100);
        //def.set(1,50, 100, 100);
        TropasK tr1def = new TropasK(TTropas.CABALLEROS, 100, 100, 100);
        grupodef.put(TTropas.CABALLEROS, tr1def);
        //at.set(2,100,100,100);
        TropasK tr2def = new TropasK(TTropas.JINETES, 100, 100, 100);
        grupodef.put(TTropas.JINETES, tr2def);
        //at.set(3, 0, 100, 100);
        //at.set(4,000,100,100);
        //at.set(5, 0, 100, 100);

        GrupoTropas def = new GrupoTropas(grupodef, false, false);

       
        //Ejecutamos la contienda...
        Contienda c = new Contienda(at, def, feudo, accion, culturaagresor, mes);

        //Y cojemos el informe de resultados...
        Reporte reporte = c.reporte();

        /**
         * *************** RESULTADO
         * *******************************************************
         */
        System.out.println("Tropas que atacan");
        at.print();
        
         System.out.println("Tropas que defienden");
        def.print();
        
        System.out.println("Resultado de la acción " + accion);
        reporte.print();

    }

}
