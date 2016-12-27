/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package combate;


import sub.TTropas;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Rosa
 */
public class GrupoTropas {
    
    //Arquero----------->0
    //Caballero---------->1
    //Jinete------------->2
    //Lancero------------>3
    //Leva-------------->4
    //Soldado------------>5

    //Número de unidades que combaten ya que los exploradores van con los jinetes
    //private int ntropas = 6;
    //private List<TropasK> unidades = new ArrayList();

    private Map<TTropas, TropasK> unidad; //Realmente, esto es la clase.
    
   //Para la clase ataque ???
    private boolean enretirada;
    private boolean enmovimiento;
    //int nexploradores;

    /**
     * *****************
     * Constructores********************************************
     */
   

    GrupoTropas(Map<TTropas, TropasK> unidad, boolean enretirada, boolean enmovimiento)
    {
        //En retirada y en movimiento. ¿Se utilizan?
        
        this.unidad = unidad;
        this.enretirada = enretirada;
        this.enmovimiento = enmovimiento;
        //this.nexploradores = nexploradores; En su caso, número de exploradores estará en TropasK.cantidad para  TTropas.EXPLORADOR
    }

  


    /**
     * *************** Métodos getter y
     * setter**************************************
     */
   

    public Map<TTropas, TropasK> getUnidad() {
        return unidad;
    }
    
   

   
    void setenmovimiento(boolean enmovimiento) {
        this.enmovimiento = enmovimiento;
    }

    boolean getenmovimiento() {
        return enmovimiento;
    }

    void setenretirada(boolean enretirada) {
        this.enretirada = enretirada;
    }

    boolean getenretirada() {
        return enretirada;
    }

   

    /**
     * ************************************* Combates *********************
     */
    
    /**
     * Devuelve la cantiad total de tropas del grupo
     * @return 
     */
    
    public int getCantidadTropas() {
        int cantidad = 0;
        for (Map.Entry<TTropas, TropasK> elemento : unidad.entrySet()) {
            TropasK u = elemento.getValue();
            cantidad=cantidad+u.getcantidad();
        }
        return cantidad;
    }
    
    /**
     * Devuelve la cantidad total de tropas del tipo tr qye hay en el grupo
     * @param tr
     * @return 
     */
    public int getCantidadTipoTropa(TTropas tr) {
        int cantidad = 0;
        if (unidad.containsKey(tr)) {
            cantidad=unidad.get(tr).getcantidad();
        }
        return cantidad;
    }
    
    /**
     * Devuelve el poder sin modificar de las tropas de tipo tr del grupo
     * @param tr
     * @return 
     */
    
     public double getPoderTipoTropa(TTropas tr) {
        double poder = 0;
        if (unidad.containsKey(tr)) {
            poder=unidad.get(tr).getPoder();
        }
        return poder;
    }

    //Esta función devueve, para cada tipo de tropa del Grupo de Tropas, el poder de ataque modificado en función del tipo de feudo
    Map<TTropas, Double> getPoderUnidadesAX(pCombateK p) {

        Map<TTropas, Double> sol = new HashMap();

        for (Map.Entry<TTropas, TropasK> elemento : unidad.entrySet()) {
            TTropas tipotropa = elemento.getKey();
            TropasK u = elemento.getValue();
            double poder;
            poder = u.getPoderUnidades();
            double fuerzaAtaque = p.fuerzaAtaca(tipotropa);
            poder = poder * fuerzaAtaque;
            sol.put(tipotropa, poder);
        }
        return sol;
    }

    Map<TTropas, Double> getPoderUnidadesDX(pCombateK p) {

        Map<TTropas, Double> sol = new HashMap();
        
         for (Map.Entry<TTropas, TropasK> elemento : unidad.entrySet()) {
            TTropas tipotropa = elemento.getKey();
            TropasK u = elemento.getValue();
            double poder;
            poder = u.getPoderUnidades();
            double fuerzaAtaque = p.fuerzaDefiende(tipotropa);
            poder = poder * fuerzaAtaque;
            sol.put(tipotropa, poder);
        }
        return sol;
    }

   
        
           
    
    
    //Función que da la resistencia para cada uno de los tipos de tropa del Grupo cuando atacan.
    //Eliminar esta función: 
    //Hacer la operacion tr.getPoder*p.defensaDefiende en la Clase Contienda:

  
    //Esta deberia estar en Contienda..
    
    double getDefensaA(TTropas tipotropa, pCombateK p) {
        //Multiplicamos la pericia, la moral y el poder de defensa en el terreno
        
         if (unidad.containsKey(tipotropa)) {
            TropasK tr=unidad.get(tipotropa);
            return tr.getPoder()*p.defensaDefiende(tipotropa);
        }
        else {
            return 0.0;
        }
        
    
        
    }

     //Función que da la resistencia para cada uno de los tipos de tropa del Grupo cuando defienden.
    
    /*
    double getDefensaD(int indice, pCombate p) {
        //Multiplicamos la pericia, la moral y el poder de defensa en el terreno
                return get(indice).getPoder() * p.defensaDefiende(indice);
    }
    */

    double getDefensaD(TTropas tipotropa, pCombateK p) {
        //Multiplicamos la pericia, la moral y el poder de defensa en el terreno
        if (unidad.containsKey(tipotropa)) {
            TropasK tr=unidad.get(tipotropa);
            return tr.getPoder()*p.defensaDefiende(tipotropa);
        }
        else {
            return 0.0;
        }
        
    

    }

   
    /**
     * ******************* Saqueos ****************************
     */
    //Nos da la cantidad de viveres que pueden cargar como máximo el grupo de tropas.
    public int carga() {
        int carga = 0;
        
         for (Map.Entry<TTropas, TropasK> elemento : unidad.entrySet()) {
            TropasK u = elemento.getValue();
            carga=carga+u.getcargaviveres();
        } 
        return carga;
    
    
    }
    
    
    public int poderSaqueo(){
        
        int poder=0;
        
        for (Map.Entry<TTropas, TropasK> elemento : unidad.entrySet()) {
            TTropas tipotropa = elemento.getKey();
            TropasK u = elemento.getValue();
            //El poder de arrase no está implementado en TTropas. De momento esto para pruebas..
            int podertipo=tipotropa.getTransporte();
            int cantidad=u.getcantidad();
            poder=poder+podertipo*cantidad;
        }  
        return poder;
    }
//___________________________Arrasar____________________________________________________
    
    /*
        public int carga() {
        int carga = 0;
        
         for (Map.Entry<TTropas, TropasK> elemento : unidad.entrySet()) {
            TropasK u = elemento.getValue();
            carga=carga+u.getcargaviveres();
        } 
        return carga;
    
    
    }
    */
    
    public int poderArrase(int mes){
        
        int poder=0;
        
        for (Map.Entry<TTropas, TropasK> elemento : unidad.entrySet()) {
            TTropas tipotropa = elemento.getKey();
            TropasK u = elemento.getValue();
            //El poder de arrase no está implementado en TTropas. De momento esto para pruebas..
            int podertipo=tipotropa.getTransporte();
            int cantidad=u.getcantidad();
            poder=poder+podertipo*cantidad;
        }  
        return poder;
    }
    
    public boolean hayUnidadesApie()
    {
        for (Map.Entry<TTropas, TropasK> elemento : unidad.entrySet()) {
            TropasK u = elemento.getValue();
            if(u.getcantidad()>0){
                TTropas tipotropa = elemento.getKey();
                if(tipotropa.isApie()){
                    return true;
                } 
            }
        }  
        return false;
    }
    
    
    void print() {
        System.out.println("Unidades");
          for (Map.Entry<TTropas, TropasK> elemento : unidad.entrySet()) {
            TTropas tipotropa = elemento.getKey();
            TropasK u = elemento.getValue();
            int cantidad=u.getcantidad();
            System.out.println(tipotropa+" :" + cantidad);   
        }
    }
}
