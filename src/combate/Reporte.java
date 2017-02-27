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

    /**
     * **
     */
    //Propiedades a efectos de depuración
    private String accion;
    protected String coordenadas = "xxx-xxx";
    private boolean conpropietario;
    /**
     * **
     */

    private Map<TTropas, Integer> bajasatacanteX = new HashMap();
    private Map<TTropas, Integer> bajasdefensorX = new HashMap();

    private Map<TTropas, Integer> capturasX = new HashMap();

    //Campesinos que han intervenido en la acción
    private int campesinos;

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

    private boolean exito = false;//Para indicar si se produce enfrentamiento con las unidades defensivas
    private String mensaje1 = null; //Para indicar la imposibilidad de realizar la acción
    private String mensaje2 = null; //Para indicar el motivo del ataque fallido
    private String mensaje3 = null; //Para indicar una derrota por los campesinos

    private int reservaarrasada;
    private int mansosarrasados;
    
    private boolean abrirporton;

    public Reporte(String accion, boolean conpropietario) {
        this.accion = accion;
        this.conpropietario = conpropietario;
    }

    /**
     * ************* Métodos getter y
     * setter**************************************
     */
    public void setMansosarrasados(int cantidad) {
        this.mansosarrasados = cantidad;
    }

    public int getMansosarrasados() {
        return mansosarrasados;
    }

    public void setReservaarrasada(int cantidad) {
        this.reservaarrasada = cantidad;
    }

    public int getReservaarrasada() {
        return reservaarrasada;
    }
    
    public boolean getAbrirporton(){
        return abrirporton;
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

    public void setCampesinos(int campesinos) {
        this.campesinos = campesinos;
    }
    
    public void setAbrirporton(boolean abrir){
        abrirporton=abrir;
    }

    /* *************** Bajas      ********************/
    void ponBajasAtacante(TTropas tr, int bajas) {
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

    //Otra forma:
    public void escribeBajas(Map<TTropas, Integer> bajasA, Map<TTropas, Integer> bajasD) {
        //Atacantes...
        for (Map.Entry<TTropas, Integer> elemento : bajasA.entrySet()) {
            ponBajasAtacante(elemento.getKey(), elemento.getValue());
        }
        //Defensores...
        for (Map.Entry<TTropas, Integer> elemento : bajasD.entrySet()) {
            ponBajasDefensor(elemento.getKey(), elemento.getValue());
        }
    }

    //Y lo más rápido..
    public void setBajasatacanteX(Map<TTropas, Integer> bajasatacanteX) {
        this.bajasatacanteX = bajasatacanteX;
    }

    public void setBajasdefensorX(Map<TTropas, Integer> bajasdefensorX) {
        this.bajasdefensorX = bajasdefensorX;
    }
    
    
    //Y lo más rápido y corto..
    public void setBajasX(Acciones accion) {
        bajasatacanteX = accion.getBajasA();
        bajasdefensorX = accion.getBajasD();
        
    }

   

    /* *************** Capturas     ********************/
    public void setCapturasX(Map<TTropas, Integer> capturasX) {
        this.capturasX = capturasX;
    }

    //Yo no lo uso 
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

    public void completaReporte(Acciones accion) {
        //Creo que sería interesante meter aquí 
        //accion.escribeBajas(reporte);
        victoriaatacante = accion.victoriataca;
        victoriadefensor = accion.victoriadefensor;

        moverdefensor = accion.muevedefensor;
        moveratacante = accion.mueveataca;

        huyedefensor = accion.huyedefensor;
        huyeatacante = accion.huyeataca;

        atacanteaniquilado = accion.aniquilaataca;
        defensoraniquilado = accion.aniquiladefensor;

        campesinos = accion.campesinos;
        abrirporton=accion.portonabierto;

    }

    /**
     * ************Para Depuracion***************************
     */
    //Mensaje para el atacante
    void printAsalto(){
        if (!exito)//No se puede realizar la acción
        {
            System.out.println("Mi señor, no hemos podido ASALTAR el edificio del feudo " + coordenadas + " porque " + mensaje1);
            return;
        }
        System.out.print("Mi señor, siguiendo vuestras órdenes hemos ASALTADO el edificio del feudo " + coordenadas + " ");
        printAtaque();
        if(abrirporton){
            System.out.println("Mi señor, hemos conseguido abrir el portón");
        }
    }
    void printArrasar() {

        if (mensaje1 != null)//No se puede realizar la acción
        {
            System.out.println("Mi señor, no hemos podido ARRASAR el feudo " + coordenadas + " porque " + mensaje1);
        } else {
            if (mensaje2 != null)//La acción ha fracasado o no se puede realizar en las propiedades del señor
            {
                System.out.print("Mi señor, siguiendo vuestras órdenes hemos ARRASADO el feudo " + coordenadas + " ");
                if (exito)//Las unidades defensivas repelen el ataque
                {
                    System.out.println(mensaje2);
                    print();
                    return;
                } else//Las tropas defensivas se refugian en el edifcio
                {
                    System.out.println(mensaje2);
                    if (victoriasobrecampis) {
                        System.out.println("Pero hemos arrasado las siguientes cantidades:");
                        System.out.println(reservaarrasada + " víveres de la reserva.");
                        System.out.println(mansosarrasados + " víveres de los mansos.");
                        return;
                    } else {
                        if (mensaje3 != null) {
                            System.out.println("Además" + mensaje3);
                            print();
                        }
                        System.out.println("Pero hemos arrasado:");
                        System.out.println(reservaarrasada + " víveres de la reserva.");
                        return;
                    }
                }
            }
            //No hay edificio
            System.out.println("Mi señor, siguiendo vuestras órdenes hemos ARRASADO el feudo " + coordenadas + " :");
            if (exito)//Hay lucha con las unidades defensivas
            {
                if (mensaje2 != null)//Las unidades defensivas repelen el ataque
                {
                    System.out.println(mensaje2);
                    print();
                    return;
                }
                if (mensaje3 != null)//Los campesinos repelen el ataque
                {
                    System.out.println("Pero " + mensaje3);
                }
                //Imprimo el combate con las bajas conjuntas tanto si han luchado
                // solo contra las tropas defensivas como si han luchado también contra los campis.
                print();
                //Informe de arrasado
                System.out.println("Pero hemos arrasado las siguientes cantidades:");
                System.out.println(reservaarrasada + " víveres de la reserva.");
                System.out.println(mansosarrasados + " víveres de los mansos.");
            } else//No hay lucha contra las unidades defensivas
            {
                if (mensaje3 != null)//Los campesinos repelen el ataque
                {
                    System.out.println(mensaje3);
                    print();
                    System.out.println("Pero hemos arrasado las siguientes cantidades:");
                    System.out.println(reservaarrasada + " víveres de la reserva.");
                    return;
                }
                //Informe de arrasado
                System.out.println("Hemos arrasado las siguientes cantidades:");
                System.out.println(reservaarrasada + " víveres de la reserva.");
                System.out.println(mansosarrasados + " víveres de los mansos.");
            }
        }
    }

    void printSaquear() {

    }

    void printAtaque() {
        System.out.println(mensaje1);
        if (exito) {

            //Se ha realizado el ataque:
            //Poner victoria:
            if (victoriaatacante) {
                System.out.println("Victoria: atacantes");

            } else {
                System.out.println("Victoria: defensores");
            }

            System.out.println("Bajas atancantes:");
            printBajas(bajasatacanteX);

            if (atacanteaniquilado) {
                System.out.println("Las unidades atacantes han sido completamente aniquiladas.");
            }
            if (moveratacante) {
                System.out.println("Las unidades atacantes se retiran hacia posiciones más seguras.");
            }
            if (huyeatacante) {
                System.out.println("Las unidades atacantes huyen en desbandada.");
            }

            System.out.println("Bajas defensores:");
            printBajas(bajasdefensorX);
            if (defensoraniquilado) {
                System.out.println("Las unidades defensoras han sido completamente aniquiladas.");
            }
            if (moverdefensor) {
                System.out.println("Las unidades defensoras se retiran hacia posiciones más seguras.");
            }
            if (huyedefensor) {
                System.out.println("Las unidades defensoras huyen en desbandada.");
            }
            if (capturasX != null) {
                if (victoriaatacante) {
                    System.out.println("Capturas realizadas por el atacante:");
                } else {
                    System.out.println("Capturas realizadas por el defensor:");
                }
                printCapturas();
            }
        }

    }

    void printConquista() {
        //Sobre feudo libre 
        if (!conpropietario) {
            System.out.print("Conquista de feudo libre ");
            if (victoriaatacante) {
                System.out.println("LOGRADA");
            } else {
                System.out.println("FALLIDA");
            }
            if (campesinos > 0) {
                System.out.println("Campesinos resistentes: " + campesinos);
                System.out.println("Bajas atancantes:");
                printBajas(bajasatacanteX);
                if (atacanteaniquilado) {
                    System.out.println("Las unidades atacantes han sido completamente aniquiladas.");
                }
                if (moveratacante) {
                    System.out.println("Las unidades atacantes se retiran hacia posiciones más seguras.");
                }
                if (huyeatacante) {
                    System.out.println("Las unidades atacantes huyen en desbandada.");
                }
                
                if (atacanteaniquilado||huyeatacante) {
                    if (capturasX.size()>0) {
                        System.out.println("Los atacantes han abondonado en la aldea lo siguiente:");
                        printCapturas();
                        System.out.println("Los campesinos harán buen uso de todo ello...");
                    }
                    
                }
                
                System.out.println("Bajas campesinos:");
                
                if (defensoraniquilado) {
                    System.out.println("Todos los campesinos resistentes han sido exterminados");
                }
                else {
                     printBajas(bajasdefensorX);
                }
            } else {
                System.out.println("No ha habido resistencia por parte de los campesinos.");
            }
        }
        else {
            //feudo con propietario  
        }
    }

    public void print() {
        switch (accion) {
            case "ATACAR":
                printAtaque();
                return;
            case "ASALTAR":
                printAsalto();
                return;
            case "ARRASAR":
                printArrasar();
                return;
            case "CONQUISTAR":
                printConquista();
                return;
            case "SAQUEAR":
                printSaquear();
                return;
            default:
                printAtaque();
        }

        /*
         if (mensaje1 != null) {
         System.out.println(mensaje1);
         return;
         }

         if (exito) {
         if (victoriaatacante) {
         System.out.println("Al enfrentarnos a nuestros enemigos hemos obtenido la victoria.");
         } else {
         if (atacanteaniquilado) {
         System.out.println("Nuestras unidades han sido completamente aniquiladas.");
         } else {
         if (moveratacante) {
         System.out.println("Nuestras unidades se retiran hacia posiciones más seguras.");
         } else {
         if (huyeatacante) {
         System.out.println("Nuestras unidades huyen en desbandada.");
         } else {
         System.out.println("Hemos sido derrotados.");
         }
         }
         }
         }
         System.out.println("Teniendo las siguientes bajas: ");
         printBajas(bajasatacanteX);

         //
         // **************************Pasamos al
         // defensor********************************************
         //
         System.out.println("Causando las siguientes bajas entre las filas enemigas: ");
         printBajas(bajasdefensorX);

         if (defensoraniquilado) {
         System.out.println("Las unidades enemigas han sido completamente aniquiladas.");
         }
         if (moverdefensor) {
         System.out.println("Las unidades enemigas se retiran hacia posiciones más seguras.");
         }
         if (huyedefensor) {
         System.out.println("Las unidades enemigas huyen en desbandada.");
         }
         }*/
    }

    private void printBajas(Map<TTropas, Integer> mapabajas) {
        if (mapabajas.size() > 0) {
            for (Map.Entry<TTropas, Integer> elemento : mapabajas.entrySet()) {
                TTropas tipotropa = elemento.getKey();
                int cantidad = elemento.getValue();
                if (cantidad > 0) {
                    System.out.println("Se han producido " + cantidad + " bajas de " + tipotropa);
                }
            }
        } else {
            System.out.println("Sin bajas");
        }
    }

    private void printCapturas() {

        if (capturasX.size() > 0) {
            for (Map.Entry<TTropas, Integer> elemento : capturasX.entrySet()) {
                TTropas tipotropa = elemento.getKey();
                int cantidad = elemento.getValue();
                if (cantidad > 0) {
                    System.out.println(cantidad + " " + tipotropa);
                }
            }
        } else {
            System.out.println("Ninguna");
        }

    }
}
