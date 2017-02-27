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
    
   private Map<TTropas, TropasK> unidad; //Realmente, esto es la clase.
    
   //Para la clase ataque ???
    private boolean enretirada;
    private boolean enmovimiento;
    

    /**
     * *****************
     * Constructores********************************************
     */
    GrupoTropas(){}

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

   public boolean tiene(String nombre)
    {
        for (Map.Entry<TTropas, TropasK> elemento : unidad.entrySet()) 
        {
            if (elemento.getKey().toString().equals(nombre)){
                return true;
            }
        }
        return false;
    }
   public TropasK getTK(String nombre)
    {
        for (Map.Entry<TTropas, TropasK> elemento : unidad.entrySet()) 
        {
            if (elemento.getKey().toString().equals(nombre)){
                return elemento.getValue();
            }
        }
        return null;
    }
   //Función que nos dice si una unidad es a distancia
   public boolean adistancia(String nombre)
    {
        if ((nombre=="ARQUEROS")||(nombre=="BALLESTEROS")){
                return true;
            }
        return false;
    }
   
   public TTropas getTT(String nombre)
    {
        for (Map.Entry<TTropas, TropasK> elemento : unidad.entrySet()) 
        {
            if (elemento.getKey().toString().equals(nombre)){
                return elemento.getKey();
            }
        }
        return null;
    }
   /**
    * ****************Asaltos*****************************************
    */
   /*
   *  Da el poder de las unidades cuando asaltan un edificio.
   *  El poder no depende del tipo de edificio ni del terreno.
   */
    Map<TTropas, Double> getPoderAsalto(String caso, pAsaltoK p, int conservacionEd) {
        
        Map<TTropas, Double> sol = new HashMap();
        double conservacion=0.01*conservacionEd;
        
        for (Map.Entry<TTropas, TropasK> elemento : unidad.entrySet()) {
            
            TTropas tipotropa = elemento.getKey();
            TropasK u = elemento.getValue();
            double poder;
            poder = u.getPoderUnidades();
            double fuerzaAtaque;
            switch(caso){ 
                case "ASALTO":
                    fuerzaAtaque=p.poderAsalto(tipotropa);
                    break;
                case "ADISTANCIA":
                    fuerzaAtaque=p.poderAdistancia(tipotropa);
                    break;
                case "ESCALAS":
                    fuerzaAtaque=p.poderEscalas(tipotropa);
                    break;
                case "TASALTO":
                    fuerzaAtaque = p.poderTAsalto(tipotropa);
                    break;
                case "GUARNICION":
                    fuerzaAtaque=p.poderGuarnicion(tipotropa, conservacion);
                    break;
                default:
                        fuerzaAtaque=0.0;
            }
                poder = poder * fuerzaAtaque;
                sol.put(tipotropa, poder);   
        }
        return sol;
    }
    
    public double defensaGuarnicion(pAsaltoK p, int conservacion){
        double defensa=0.0;
        double conservacionEd=0.01*conservacion; //ya que conservación es sobre 100
        for (Map.Entry<TTropas, TropasK> elemento : unidad.entrySet()) {
            TTropas tr=elemento.getKey();
            TropasK tk=elemento.getValue();
            defensa=defensa+tk.getcantidad()*getDefensaAsalto(tr, conservacionEd, p, "GUARNICION");
        }
        return defensa;
    }
    
    double getDefensaAsalto(TTropas tipotropa, double conservacion, pAsaltoK p, String caso) {
        //Multiplicamos la pericia, la moral y el poder de defensa en el terreno
        
         if (unidad.containsKey(tipotropa)) {
            TropasK tr=unidad.get(tipotropa);
            switch(caso){
                case "ASALTO":
                    return tr.getPoder()*p.defensaAsalto(tipotropa);
                case "ADISTANCIA":
                    return tr.getPoder()*p.defensaAdistancia(tipotropa);
                case "ESCALAS":
                    return tr.getPoder()*p.defensaEscalas(tipotropa);
                case "TASALTO":
                    return tr.getPoder()*p.defensaTAsalto(tipotropa);
                case "GUARNICION":
                    return tr.getPoder()*p.defensaGuarnicion(tipotropa, conservacion);
                default:
                    return 0.0;
            }     
        }
        else {
            return 0.0;
        }   
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

   /**
    * Calculo si el grupo tiene tropas de combate
    * @return 
    */
    public boolean conTropasCombate() {
        pCombateK p = pCombateK.LLANO;
       
        for (Map.Entry<TTropas, TropasK> elemento : unidad.entrySet()) {
            TTropas tipotropa = elemento.getKey();
            double poder = getDefensaD(tipotropa, p) * getCantidadTipoTropa(tipotropa);
            if (poder > 0.0) {
                return true;
            }
        }
        return false;
    }
        
           
   public Map<TTropas, Integer> capturaTropas(){
       Map<TTropas, Integer> resultado=new HashMap();
        for (Map.Entry<TTropas, TropasK> elemento : unidad.entrySet()) {
            TTropas tipotropas=elemento.getKey();
            if (!tipotropas.isAniquilables()) {
                TropasK u = elemento.getValue();
                int cantidad=u.getcantidad();
                resultado.put(tipotropas, cantidad);
            }
        }
        return resultado;
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
            int podertipo=tipotropa.getPoderarrasar();
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
    
   
    //Para depuración...
    
    void print() {
        System.out.println("Unidades: cantindad, pericia, moral");
          for (Map.Entry<TTropas, TropasK> elemento : unidad.entrySet()) {
            TTropas tipotropa = elemento.getKey();
            TropasK u = elemento.getValue();
            int cantidad=u.getcantidad();
            System.out.println(tipotropa+" :" + cantidad+", "+u.getpericia()+", "+u.getmoral());   
        }
    }
}

