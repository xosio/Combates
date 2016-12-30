/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package combate;

import sub.TTropas;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Rosita
 */
public class Reporte {

    private int ntropas = 6; // Podemos tener todas las tropas que se vayan definiendo...

    protected String terreno; //El terreno ya lo sabemos. No tednría que estar en el reporte
    protected GrupoTropas defiende; //Idem
    protected String coordenadas="xxx-xxx";

    protected int[] bajasatacante = new int[ntropas]; //Las bajas las implementamos con maps
    protected int[] bajasdefensor = new int[ntropas]; //Idemn

    private Map<TTropas, Integer> bajasatacanteX = new HashMap();
    private Map<TTropas, Integer> bajasdefensorX = new HashMap();

    //Condiciones varias
    //Victorias
    protected boolean victoriaatacante = false; //Las inicio todas 
    protected boolean victoriadefensor = false; //Se debería cumplir victoriaatacante=!victoriadefensor- Supongo.
    protected boolean victoriasobrecampis = false; //No sé si hará falta.

    //Aniquilaciones
    protected boolean atacanteaniquilado = false;
    protected boolean defensoraniquilado = false;

    //Moverse a posiciones más seguras
    protected boolean moveratacante;
    private boolean moverdefensor;

    //Huidas al principal o con el personaje
    private boolean huyeatacante;
    private boolean huyedefensor;

    
    private boolean exito = false;//PAra indicar si se produce enfrentamiento con las unidades defensivas
    private String mensaje1=null; //Para indicar la imposibilidad de realizar la acción
    private String mensaje2=null; //Para indicar el motivo del ataque fallido
    private String mensaje3=null; //Para indicar una derrota por los campesinos
    
    private int reservaarrasada;
    private int mansosarrasados;
  

    public Reporte() {
    }

    //Ya no hace falta
    Reporte(GrupoTropas tropas, String terreno) {
        this.terreno = terreno;
        this.defiende = tropas;
    }

    /**
     * ************* Métodos getter y
     * setter**************************************
     */
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
    public void setMensaje1(String mensajes) {
        this.mensaje1 = mensajes;
    }
    public void setMensaje2(String mensajes) {
        this.mensaje2 = mensajes;
    }
    public void setMensaje3(String mensajes) {
        this.mensaje3 = mensajes;
    }

    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }

    //Estas no deberían hacer falta:
    public int getntropas() {
        return ntropas;
    }

    void setterreno(String terreno) {
        this.terreno = terreno;
    }

    String getterreno() {
        return terreno;
    }

    void setdefiende(GrupoTropas tropas) {
        this.defiende = tropas;
    }

    GrupoTropas getdefiende() {
        return defiende;
    }
    /* *************** Bajas      ********************/

    public void anadirBajas(Reporte reporte) {
        for (int i = 0; i < bajasatacante.length; i++) {
            bajasatacante[i] = bajasatacante[i] + reporte.getbajasatacante(i);
            bajasdefensor[i] = bajasdefensor[i] + reporte.getbajasdefensor(i);
        }
    }

    //Yo no lo uso 
    public int[] getbajasatacante() {
        return bajasatacante;
    }

    public int getbajasatacante(int indice) {
        return bajasatacante[indice];
    }
//Yo no lo uso 

    int[] getbajasdefensor() {
        return bajasdefensor;
    }

    public int getbajasdefensor(int indice) {
        return bajasdefensor[indice];
    }

    void setbajasatacante(int[] bajas) {
        for (int i = 0; i < bajasatacante.length; i++) {
            bajasatacante[i] = bajasatacante[i] + bajas[i];
        }
    }

    void setbajasdefensor(int[] bajas) {
        for (int i = 0; i < bajasdefensor.length; i++) {
            bajasdefensor[i] = bajasdefensor[i] + bajas[i];
        }
    }

    void ponBajasatacante(TTropas tr, int bajas) {
        if (bajasatacanteX.containsKey(tr)) {
            int bj = bajasatacanteX.get(tr);
            bajas = bj + bajas;
        }
        bajasatacanteX.put(tr, bajas);
    }

    void ponBajasDefensor(TTropas tr, int bajas) {
        if (bajasdefensorX.containsKey(tr)) {
            int bj = bajasdefensorX.get(tr);
            bajas = bj + bajas;
        }
        bajasdefensorX.put(tr, bajas);
    }

    public void setbajasatacante(int bajas, int indice) {
        bajasatacante[indice] = bajasatacante[indice] + bajas;
    }

    public void setbajasdefensor(int bajas, int indice) {
        bajasdefensor[indice] = bajasdefensor[indice] + bajas;
    }

    //Yo no lo uso 
    public int bajasAtacante() {
        int aux = 0;

        for (int i = 0; i < ntropas; i++) {
            aux = aux + bajasatacante[i];
        }
        return aux;
    }
//Yo no lo uso 

    public int bajasDefensor() {
        int aux = 0;

        for (int i = 0; i < ntropas; i++) {
            aux = aux + bajasdefensor[i];
        }
        return aux;
    }

    /**
     * *******************Huidas*************************
     */
    public void setMoveratacante(boolean mover) {
        moveratacante = mover;
    }

    public void setMoverdefensor(boolean moverdefensor) {
        this.moverdefensor = moverdefensor;
    }

    public boolean isMoverdefensor() {
        return moverdefensor;
    }

    public boolean getMoveratacante() {
        return moveratacante;
    }

    public void setHuyeatacante(boolean mover) {
        huyeatacante = mover;
    }

    public boolean getHuyeatacante() {
        return huyeatacante;
    }

    public void setHuyedefensor(boolean huyedefensor) {
        this.huyedefensor = huyedefensor;
    }

    public boolean isHuyedefensor() {
        return huyedefensor;
    }

    /**
     * *************** Aniquilaciones *******************
     */
    public void setAtacanteaniquilado(boolean aniquilados) {
        atacanteaniquilado = aniquilados;
    }

    public boolean getAtacanteaniquilado() {
        return atacanteaniquilado;
    }

    public void setDefensoraniquilado(boolean aniquilados) {
        defensoraniquilado = aniquilados;
    }

    public boolean getDefensoraniquilado() {
        return defensoraniquilado;
    }

    /**
     * *****************Victoria**************************
     */
    public void setVictoriaatacante(boolean victoriaatacante) {
        this.victoriaatacante = victoriaatacante;
    }

    public boolean getVictoriaatacante() {
        return victoriaatacante;
    }

    public void setVictoriadefensor(boolean victoriadefensor) {
        this.victoriadefensor = victoriadefensor;
    }

    public boolean getVictoriadefensor() {
        return victoriadefensor;
    }
    
    public void setVictoriasobrecampis(boolean victoriasobrecampis) {
        this.victoriasobrecampis = victoriasobrecampis;
    }
    public boolean getVictoriasobrecampis() {
        return victoriasobrecampis;
    }

    /**
     * ************* Funciones *****************
     */
    public boolean isMoveratacante() {
        if (atacanteaniquilado) {
            return false;
        } else {
            return moveratacante;
        }
    }

    /**
     * ************Para Borrar***************************
     */
    //Mensaje para el atacante
    void printArrasar() {
        
        if(mensaje1!=null)//No se puede realizar la acción
        {
            System.out.println("Mi señor, no hemos podido ARRASAR el feudo "+coordenadas+" porque "+mensaje1);
        }
        else{
            if(mensaje2!=null)//La acción ha fracasado o no se puede realizar en las propiedades del señor
            {
                System.out.print("Mi señor, siguiendo vuestras órdenes hemos ARRASADO el feudo "+coordenadas+" ");
                if(exito)//Las unidades defensivas repelen el ataque
                {
                    System.out.println(mensaje2);
                    print();
                    return;     
                }else//Las tropas defensivas se refugian en el edifcio
                {
                    System.out.println(mensaje2);
                    if(victoriasobrecampis)
                    {
                        System.out.println("Pero hemos arrasado las siguientes cantidades:");
                        System.out.println(reservaarrasada+" víveres de la reserva.");
                        System.out.println(mansosarrasados+" víveres de los mansos.");
                        return;
                    }
                    else{
                        if(mensaje3!=null)
                        {
                            System.out.println("Además" +mensaje3);
                            print();
                        }
                        System.out.println("Pero hemos arrasado:");
                        System.out.println(reservaarrasada+" víveres de la reserva.");                       
                        return;   
                    }
                }    
            }
            //No hay edificio
            System.out.println("Mi señor, siguiendo vuestras órdenes hemos ARRASADO el feudo "+coordenadas+" :");
            if(exito)//Hay lucha con las unidades defensivas
            {
                if(mensaje2!=null)//Las unidades defensivas repelen el ataque
                {
                    System.out.println(mensaje2);
                    print();
                    return;
                }
                if(mensaje3!=null)//Los campesinos repelen el ataque
                {
                    System.out.println("Pero "+mensaje3);
                }
                //Imprimo el combate con las bajas conjuntas tanto si han luchado
                // solo contra las tropas defensivas como si han luchado también contra los campis.
                print();
                //Informe de arrasado
                System.out.println("Pero hemos arrasado las siguientes cantidades:");
                System.out.println(reservaarrasada+" víveres de la reserva.");
                System.out.println(mansosarrasados+" víveres de los mansos.");
            }else//No hay lucha contra las unidades defensivas
            {
                if(mensaje3!=null)//Los campesinos repelen el ataque
                {
                    System.out.println(mensaje3);
                    print();
                    System.out.println("Pero hemos arrasado las siguientes cantidades:");
                    System.out.println(reservaarrasada+" víveres de la reserva.");
                    return;
                }
                //Informe de arrasado
                System.out.println("Hemos arrasado las siguientes cantidades:");
                System.out.println(reservaarrasada+" víveres de la reserva.");
                System.out.println(mansosarrasados+" víveres de los mansos.");
            }   
        }
    }
    void printSaquear()
    {
        
    }
    void print()
    {
        if(mensaje1!=null)
        {
            System.out.println(mensaje1);
            return;
        }
        if(victoriaatacante){
            System.out.println("Al enfrentarnos a nuestros enemigos hemos obtenido la victoria.");
        }
        else
        {
            if(atacanteaniquilado){
                System.out.println("Nuestras unidades han sido completamente aniquiladas.");
            }
            else{
                if(moveratacante){
                System.out.println("Nuestras unidades se retiran hacia posiciones más seguras.");
                }
                else{
                    if(huyeatacante){
                        System.out.println("Nuestras unidades huyen en desbandada.");
                    }
                    else{
                        System.out.println("Hemos sido derrotados.");
                    }
                }
            }       
        }
        System.out.println("Teniendo las siguientes bajas: ");

        for (Map.Entry<TTropas, Integer> elemento : bajasatacanteX.entrySet()) {
            TTropas tipotropa = elemento.getKey();
            int cantidad = elemento.getValue();
            if(cantidad>0){
            System.out.println("Se han producido " + cantidad + " bajas de " + tipotropa);
            }
        }
/****************************Pasamos al defensor*********************************************/
        
        System.out.println("Causando las siguientes bajas entre las filas enemigas: ");
  
        for (Map.Entry<TTropas, Integer> elemento : bajasdefensorX.entrySet()) {
            TTropas tipotropa = elemento.getKey();
            int cantidad = elemento.getValue();
            if(cantidad>0){
            System.out.println("Se han producido " + cantidad + " bajas de " + tipotropa);
            }
        }
        
         if(defensoraniquilado){
                System.out.println("Las unidades enemigas han sido completamente aniquiladas.");
         }
         if(moverdefensor){
                System.out.println("Las unidades enemigas se retiran hacia posiciones más seguras.");
         }
         if(huyedefensor){
                System.out.println("Las unidades enemigas huyen en desbandada.");
         }    
    }

}
