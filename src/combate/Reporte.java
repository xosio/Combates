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

    //Informa acerca de si la accion ha tendio exito
    private boolean exito = false;
    private String mensajes; //Para poner, en su caso, explicaciones de lo que ha pasado....
    
    private int reservaarrasada;
    private int mansosarrasados;

    public Reporte() {
    }

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
    public void setMensajes(String mensajes) {
        this.mensajes = mensajes;
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
    public void setmoveratacante(boolean mover) {
        moveratacante = mover;
    }

    public void setMoverdefensor(boolean moverdefensor) {
        this.moverdefensor = moverdefensor;
    }

    public boolean isMoverdefensor() {
        return moverdefensor;
    }

    public boolean getmoveratacante() {
        return moveratacante;
    }

    public void sethuyeatacante(boolean mover) {
        huyeatacante = mover;
    }

    public boolean gethuyeatacante() {
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
    public void setatacanteaniquilado(boolean aniquilados) {
        atacanteaniquilado = aniquilados;
    }

    public boolean getatacanteaniquilado() {
        return atacanteaniquilado;
    }

    public void setdefensoraniquilado(boolean aniquilados) {
        defensoraniquilado = aniquilados;
    }

    public boolean getdefensoraniquilado() {
        return defensoraniquilado;
    }

    /**
     * *****************Victoria**************************
     */
    public void setvictoriaatacante(boolean victoriaatacante) {
        this.victoriaatacante = victoriaatacante;
    }

    public boolean getvictoriaatacante() {
        return victoriaatacante;
    }

    public void setvictoriadefensor(boolean victoriadefensor) {
        this.victoriadefensor = victoriadefensor;
    }

    public boolean getvictoriadefensor() {
        return victoriadefensor;
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
    void print() {

        System.out.println("Victoria actacante: " + victoriaatacante);
        System.out.println("Mueve atacante: " + moveratacante);
        System.out.println("Aniquila atacante: " + atacanteaniquilado);
        
        System.out.println("*******");
        System.out.println("Victoria defensor: " + victoriadefensor);
        System.out.println("Mueve defensor: " + moverdefensor);
        System.out.println("Aniquila defensor: " + defensoraniquilado);

        System.out.println("*******");
        
        System.out.println("Bajas del grupo atacante");

        for (Map.Entry<TTropas, Integer> elemento : bajasatacanteX.entrySet()) {
            TTropas tipotropa = elemento.getKey();
            int cantidad = elemento.getValue();
            System.out.println("Se han producido " + cantidad + " bajas de " + tipotropa);
        }

        System.out.println("Bajas del grupo defensor");
        
        for (Map.Entry<TTropas, Integer> elemento : bajasdefensorX.entrySet()) {
            TTropas tipotropa = elemento.getKey();
            int cantidad = elemento.getValue();
            System.out.println("Se han producido " + cantidad + " bajas de " + tipotropa);
        }

    }

}
