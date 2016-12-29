/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package combate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import sub.TCultura;
import sub.TTropas;

/**
 *
 * @author Xosio
 */
public class Acciones {

    /*ARRASAR,
     ASALTAR,
     ASEDIAR,
     ATACAR,
     CONQUISTAR-->OVERRIDE AccionConquista,
     SAQUEAR;*/
// Sobre quien se ejecuta la acción. Para los casos de arrasar, y arrasar.
    private boolean acampesinos = false;
    private boolean apropietario = false;

    private Map<TTropas, Integer> bajasA = new HashMap(); //Bajas de los atacantes para cada tipo de tropas
    private Map<TTropas, Integer> bajasD = new HashMap(); //Bajas de los defensores para cada tipo de tropas

    //Resultados de la accion sobre las tropas
    protected boolean victoriataca = false;
    protected boolean victoriadefensor = false;
    protected boolean victoriasobrecampis = false;

    protected boolean aniquilaataca = false;
    protected boolean aniquiladefensor = false;

    protected boolean huyeataca = false;
    protected boolean huyedefensor = false;

    protected boolean mueveataca = false;
    protected boolean muevedefensor = false;
   
    //Resultado de la accion
    protected boolean exito = false;
    //True si hay lucha contra las unidades defensivas.
    //False si no hay lucha contra las unidades defensivas
    protected String mensaje1=null;
    protected  String mensaje2=null;
    protected  String mensaje3=null;
    //Operacion
    protected String operacion;

    public boolean isAniquiladefensor() {
        return aniquiladefensor;
    }

    public boolean isHuyedefensor() {
        return huyedefensor;
    }

    public boolean isMuevedefensor() {
        return muevedefensor;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public String getOperacion() {
        return operacion;
    }
    //Creo que no hace falta
    /*public boolean getExito()
    {
        return exito;
    }

    /**
     * Ataque de tropas "ataca" sobre tropas "defiende" en el terreno/lugar p.
     *
     *
     * @param ataca GrupoTropas que atacan
     * @param defiende GrupoTropas que dedienden
     * @param p Modificadores de poder en función del terreno/lugar donde se
     * @param auxiliar nos indica si la acción atacar es el cuerpo del programa o es una acción auxiliar
     * realiza la accion de ataca. pCombateK
     */
    public void atacar(GrupoTropas ataca, GrupoTropas defiende, pCombateK p) {
        /**
         * ******* Variables e
         * inicializaciones**********************************
         */
        //Necesito reiniciarlas, porque se puede llamar varias veces a la función
        // atacar e ir sobreescribiendo datos ficticios.
         bajasA = new HashMap(); 
         bajasD = new HashMap();
         aniquiladefensor = false;
         muevedefensor = false;
         huyedefensor = false;
         
        double penalizacionDe = 1.0;//Guarda las penalizaciones correspondientes al grupo que defiende
        double penalizacionAt = 1.0;//Guarda las penalizaciones correspondientes al grupo que ataca

        //Variables que guardan la suma de los poderes de las unidades
        double poderAt = 0.0;
        double poderDe = 0.0;

        double rba = 0.0;//Reducción de bajas del atacante.
        double rbd = 0.0;//Reducción de bajas del defensor.

        Map<TTropas, Double> poderBajasA = new HashMap();
        Map<TTropas, Double> poderBajasD = new HashMap();

        /**
         * ******************** Obtenemos el poder de las tropas
         * ********************************
         */
        //Obtenemos los poderes de las tropas unidad por unidad
        //Obtenemos los poderes de las tropas para cada unidad, en función del tipo de feudo donde se realiza la acción
        //Tropas de ataque: 
        Map<TTropas, Double> poderA = ataca.getPoderUnidadesAX(p);

        //Tropas de defensa:
        Map<TTropas, Double> poderD = ataca.getPoderUnidadesDX(p);

        /*Obtenemos el poder de las bonificaciones correspondientes a cada tipo de unidad  
         * El poder obtenido lo guardamos de forma proporcional al número de unidades
         * en el lugar donde causan las bajas, en poderBajas. Es decir, si enfrento caballeros en ataque
         * contra soldados en defensa, el poder de la bonificación lo guardo en poderBajas[5] ya que es 
         * donde se causan las bajas.
         */
        //Bonificaciones de las Tropas que atacan
        Map<TTropas, TropasK> atacantes = ataca.getUnidad();
        for (Map.Entry<TTropas, TropasK> elemento : atacantes.entrySet()) {
            TTropas tipotropa = elemento.getKey();
            TropasK u = elemento.getValue();

            if (p.bonifica(tipotropa)) { //Si el tipo de tropas tiene bonificacion en el terreno/lugar p

                List<TTropas> aux1 = p.getubonificadora(tipotropa); //Obtenemos una lista aux con el tipo de tropas sobre las que tiene bonificación
                if (poderA.containsKey(tipotropa)) {  //Si el tipo tropa tiene poder ???
                    if ((poderA.get(tipotropa) > 0.0) && (bonificable(aux1, poderD))) {
                        bonificacion(tipotropa, u, aux1, defiende, poderBajasD); //??? bonificamos y ponemos los datos en poderBajasD...
                    }
                }
            }
        }

        //Bonificaciones de las tropas que defienden 
        Map<TTropas, TropasK> defensores = defiende.getUnidad();
        for (Map.Entry<TTropas, TropasK> elemento : defensores.entrySet()) {
            TTropas tipotropa = elemento.getKey();
            TropasK u = elemento.getValue();

            if (p.bonifica(tipotropa)) {
                List<TTropas> aux1 = p.getubonificadora(tipotropa);
                if (poderA.containsKey(tipotropa)) {
                    if ((poderD.get(tipotropa) > 0.0) && (bonificable(aux1, poderA))) {
                        bonificacion(tipotropa, u, aux1, ataca, poderBajasA);
                    }
                }
            }
        }

        //Sumamos el poder de ataque de las unidades 
        //Del atacante:
        for (Map.Entry<TTropas, Double> elemento : poderA.entrySet()) {
            double poderAi = elemento.getValue();
            poderAt = poderAt + poderAi;
            System.out.println("Poder ataque "+poderAi);
        }
System.out.println("Poder ataque "+poderAt);
        //Del defensor:
        for (Map.Entry<TTropas, Double> elemento : poderD.entrySet()) {
            //TTropas tipotropa = elemento.getKey(); poderD puede ser un Lista ???
            double poderDi = elemento.getValue();
            poderDe = poderDe + poderDi;
            System.out.println("Poder defiende "+ poderDi);
        }

System.out.println("Poder defiende "+ poderDe);
        //Obtenemos las condiciones de victoria
        double auxA = poderAt / (poderAt + poderDe);
        double auxD = poderDe / (poderAt + poderDe);

        //Guardamos las condiciones de victoria en el reporte
        if (poderAt > poderDe) {
            //Gana el atacante
            victoriataca = true;

            if (auxD <= 0.02) {
                aniquiladefensor = true;

                for (Map.Entry<TTropas, Double> elemento : poderA.entrySet()) {
                    double poderAi = elemento.getValue();
                    /*
                     Original:
                     if(poderA[i]>0)
                     {
                     reporte.setbajasatacante(redondea(0.2*auxD*ataca.get(i).getcantidad()*p.fuerzaAtaca(i) /ataca.getDefensaA(i,p)),i);
                     } 
                     */
                    if (poderAi > 0) {
                        TTropas tipotropa = elemento.getKey();
                        int cantidad = ataca.getCantidadTipoTropa(tipotropa); //ataca.get(i).getcantidad()
                        double fa = p.fuerzaAtaca(tipotropa); //p.fuerzaAtaca(i)
                        double da = ataca.getPoderTipoTropa(tipotropa) * p.defensaAtaca(tipotropa); //ataca.getDefensaA(i,p)
                        int bajas = redondea(0.2 * auxD * cantidad * fa / da);
                        
                        ponBajas(bajasA, tipotropa, bajas);
                        
                        
                    }
                }
                //Y aniquilamos al defensor. 
                //Se escribiria así. Pero en este caso no lo hago, porque ya esta puesto que se aniquilan 
                //en la variable aniquiladefensor
                /*
                 for (Map.Entry<TTropas, TropasK> u : defiende.getUnidad().entrySet()) {
                 ponBajasDefensor(u.getKey(), u.getValue().getcantidad());
                 }
                 */
                return;

            } else {
                if (auxD <= 0.2) {
                    //Retirada al principal o con el personaje
                    huyedefensor = true;
                } else {
                    if (auxD <= 0.3) {
                        //Se mueve a posiciones mas seguras.
                        muevedefensor = true;
                    }
                }
            }
        } else {
            //Gana el grupo que defiende, en caso de empoate le damos la ventaja al defensor
            victoriadefensor = true;
            if (auxA <= 0.02) {
                //Aniquilación de Atacante
                aniquilaataca = true;

                for (Map.Entry<TTropas, Double> elemento : poderD.entrySet()) {
                    double poderDi = elemento.getValue();
 System.out.println(poderDi);
                    if (poderDi > 0) {
                        TTropas tipotropa = elemento.getKey();
                        int cantidad = defiende.getCantidadTipoTropa(tipotropa);
                        double fa = p.fuerzaDefiende(tipotropa);
                        double da = defiende.getPoderTipoTropa(tipotropa) * p.fuerzaDefiende(tipotropa);
                        int bajas = redondea(0.2 * auxD * cantidad * fa / da);
                        
                        ponBajas(bajasD, tipotropa, bajas);
System.out.println(bajasD);
                        
                    }
                }
                //Idem. No escribo las bajas del atacante. Ya se sabe que son todas...
                return;
            } else {
                if (auxA <= 0.2) {
                    //Retirada al principal o al personaje
                    huyeataca = true;
                } else {
                    if (auxA <= 0.3) {
                        //Se mueve a posiciones mas seguras.
                        mueveataca = true;
                    }
                }
            }
        }

        /**
         * ********************* Obtenemos la reduccion de bajas por
         * victoria***************************************************
         */
        //En caso de combate más o menos igualado repartimos las bajas
        if ((auxD >= 0.4) && (auxD <= 0.6)) {
            rba = p.getbajasempate();
            rbd = p.getbajasempate();
        } else {
            if (victoriataca) {
                rba = p.getbajasvictoria();
                rbd = p.getbajasderrota();
            } else {
                rba = p.getbajasderrota();
                rbd = p.getbajasvictoria();
            }
        }

        /**
         * ************************* Calculamos las bajas
         * *******************************
         */
        //Calculamos el poder de las bajas de cada bando
        //Atacantes...
        for (Map.Entry<TTropas, Double> elemento : poderBajasA.entrySet()) {
            TTropas tipotropas = elemento.getKey();
            double poderAi = elemento.getValue();
            double poderBajas = poderA.get(tipotropas) + poderAi / (poderAi + poderA.get(tipotropas) + auxD) * rba;
            poderBajasA.put(tipotropas, poderBajas);

        }

        //Defensores...
        for (Map.Entry<TTropas, Double> elemento : poderBajasD.entrySet()) {
            TTropas tipotropas = elemento.getKey();
            double poderDi = elemento.getValue();
            double poderBajas = poderD.get(tipotropas) + poderDi / (poderDi + poderD.get(tipotropas) + auxA) * rbd;
            poderBajasD.put(tipotropas, poderBajas);
        }

        //Atacantes...
        for (Map.Entry<TTropas, Double> elemento : poderBajasA.entrySet()) {
            TTropas tipotropas = elemento.getKey();
            double poderBajasAi = elemento.getValue() / ataca.getDefensaA(tipotropas, p);
            int bajasAi = redondea(poderBajasAi);
            
            ponBajas(bajasA, tipotropas, bajasAi);
            
        }

        //Defensores...
        for (Map.Entry<TTropas, Double> elemento : poderBajasD.entrySet()) {
            TTropas tipotropas = elemento.getKey();
            double poderBajasDi = elemento.getValue() / ataca.getDefensaD(tipotropas, p);
            int bajasDi = redondea(poderBajasDi);
            
            ponBajas(bajasD, tipotropas, bajasDi);          
        }
    }

    /**
     * Redondea las bajas obtenidas en el combate, cuando es mayor al 30%.
     *
     * @param num
     * @return numero entero redondeado
     */
    private int redondea(double num) {
        int sol = (int) (num);
        if (num - sol < 0.3) {
            return sol;
        }
        return sol + 1;
    }
    //Función que completa el reporte con los datos necesarios
    public void completaReporte(Reporte reporte)
    {
        reporte.setVictoriaatacante(victoriataca);
        reporte.setVictoriadefensor(victoriadefensor);
        
        reporte.setMoverdefensor(muevedefensor);
        reporte.setMoveratacante(mueveataca);

        reporte.setHuyedefensor(huyedefensor);
        reporte.setHuyeatacante(huyeataca);
        
        reporte.setAtacanteaniquilado(aniquilaataca);
        reporte.setDefensoraniquilado(aniquiladefensor);
    
        escribeBajas(reporte);
    }
    //Función que escribe las bajas en el reporte y resetea las ya existentes
    public void escribeBajas(Reporte reporte) {/*
        //Atacantes...
        for (Map.Entry<TTropas, Double> elemento : poderBajasA.entrySet()) {
        TTropas tipotropas = elemento.getKey();
        double poderBajasAi = elemento.getValue() / ataca.getDefensaA(tipotropas, p);
        int bajasAi = redondea(poderBajasAi);
        reporte.ponBajasatacante(tipotropas, bajasAi);
        }

        //Defensores...
        for (Map.Entry<TTropas, Double> elemento : poderBajasD.entrySet()) {
        TTropas tipotropas = elemento.getKey();
        double poderBajasDi = elemento.getValue() / ataca.getDefensaD(tipotropas, p);
        int bajasDi = redondea(poderBajasDi);
        reporte.ponBajasDefensor(tipotropas, bajasDi);
        }*/
     }
    /**
     *
     * Pone en el parámetro MAP poderBajas la bonificación para cada tipo de
     * tropas tipotropa.. ???
     *
     * @param tipotropa: tipo de tropa que se quiere bonificar
     * @param unidad: Datos de las tropas de tipo tropa
     * @param uBonificadoras: Lista de tropas sobre las que tipotropa puede
     * obtener bonificación
     * @param enemigas: Map con las tropas sobre las que se va a calcular la
     * bonificación
     * @param poderBajas Map donde se van a guardar los resultados...
     *
     */
    private void bonificacion(TTropas tipotropa, TropasK unidad, List<TTropas> uBonificadoras, GrupoTropas enemigas, Map<TTropas, Double> poderBajas) {
        //Obtenemos el número de unidades bonificadoras

        int n = 0;
        for (TTropas tr : uBonificadoras) {
            int cantidad = 0;
            if (enemigas.getUnidad().get(tr) != null) {
                cantidad = enemigas.getUnidad().get(tr).getcantidad();
            }
            n = n + cantidad;
        }

        //Obtenemos la proporción de la unidad bonificada sobre las bonificadoras.
        double proporcion = (double) unidad.getcantidad() / (double) (n);
        double bonificacion = 0.0;

        switch (tipotropa) {
            case ARQUEROS: //Bonificación para los arqueros contra lanceros y levas
                bonificacion = proporcion * (-16.0 * proporcion + 8.0);
                break;
            case CABALLEROS://Bonificación para los caballeros contra arqueros, soldados y levas  
                bonificacion = proporcion * (-18.0 * proporcion + 12.0);
                break;
            case JINETES://Bonificación para los jinetes contra arqueros y levas 
                bonificacion = 8.0 * proporcion * (-proporcion + 1.0);
                break;
            case LANCEROS://Bonificación para los lanceros contra caballeros y jinetes
                bonificacion = 0.66 * (proporcion - 1.0) * (-proporcion / 8.0 + 1.0);
                break;
            case SOLDADOS://Bonificación para los soldados contra arqueros, lanceros y levas
                bonificacion = (proporcion - 0.75) * (-2.0 * proporcion / 9 + 4.0 / 3.0);
                break;
            default:
                break;
        }

        double poder = unidad.getPoder();

        System.out.println("Proporcion= " + proporcion);
        System.out.println("Poder antes bon");
        System.out.println(poder);

        if (bonificacion > 0.0) {
            //Obtenemos el poder de la bonificación
            poder = poder * bonificacion;

            //Obtenemos ahora la proporción de poder que causa bajas en cada tipo de unidad
            for (TTropas tr : uBonificadoras) {
                if (enemigas.getUnidad().get(tr) != null) {
                    proporcion = (double) (enemigas.getUnidad().get(tr).getcantidad()) / (double) (n);
                    poderBajas.put(tr, poder * proporcion);
                }

            }
        }

        System.out.println("Poder con bon");
        System.out.println(poder);
        System.out.println("Poder extra por unidad=" + poder / unidad.getcantidad());
    }

    /*
     * Devuelve true si hay tropas enemigas que ofrecen bonificación y false en caso contrario
     * uBonificadoras: es un vector que contiene los índices de las unidades que bonifican.
     * poder: es un vector con los poderes de las unidades enemigas.
     */
//Realmente esta hace falta??. 
    //Si es bonificable, estará en la lista. Si no es bonificable, pues no lo estará.   
    private boolean bonificable(List<TTropas> uBonificadoras, Map<TTropas, Double> poder) {
        for (TTropas tipotropa : uBonificadoras) {
            if (poder.containsKey(tipotropa)) {
                if (poder.get(tipotropa) > 0.0) {
                    return true;
                }
            }
        }
        return false;
    }
    //Igual pero genérico.
    private void ponBajas(Map<TTropas, Integer> bajas, TTropas tr, int cantidad)
    {
        if (bajas.containsKey(tr)) {
            int bj = bajas.get(tr);
            cantidad = bj + cantidad;
        }
        bajas.put(tr, cantidad);
    }
    private void ponBajasAtacante(TTropas tr, int bajas) {
        if (bajasA.containsKey(tr)) {
            int bj = bajasA.get(tr);
            bajas = bj + bajas;
        }
        bajasA.put(tr, bajas);
    }

    private void ponBajasDefensor(TTropas tr, int bajas) {
        if (bajasD.containsKey(tr)) {
            int bj = bajasD.get(tr);
            bajas = bj + bajas;
        }
        bajasD.put(tr, bajas);
    }

    ////*****
    //Desarrolla un combate donde no hay tropas defensiva (militares):
    //CombateConCampesinos: Implementado aquí. Para conquistas...
    //El método se sobresscribe para Arrases y saqueos...
    /**
     * Desarrolla un combate donde no hay tropas defensivas (de combate):
     *
     * @param ataca Tropas que atacan
     * @param feudo Datos del feudo donde se realiza el ataque
     * @param culturaagresor
     *
     * Se comprueba si la acción molesta a los campesinos. En caso afirmativo se
     * obtiene el grupo de campesinos defensor. Se calcula el combate. Con
     * victoria simple del atacante se conquista y con retirada para saquear y
     * arrasar. Si se produce retirada de los campesinos no se ejecuta el
     * combate. Las tropas actúan. Si no molesta a los campesinos las tropas
     * actúan.
     *
     *
     */
    public void combateCampesinos(GrupoTropas ataca, FeudoK feudo, TCultura culturaagresor) {
        //*Se comprueba si la acción molesta a los campesinos del feudoK para esta acción

        //Una cuestión de extender la clase ACCIONES está en la función molestia y si el 
        //número de campesinos que defienden es diferente en funcion de la accion.
        //moletiaCampesinos(operacion)
        //Se pueden escribir en feudosK.
        //En lugar de pasar el parámetro operacion se pueden escribir tres funciones diferentes:
        //molestiaConquista(parámetros necesarios..)
        //molestiaArrase(parámetros necesarios..)
        //molestiaSaqueo(parámetros necesarios..)
        //Funciones que podrán devolver el número de campesinos que actuarían
        //En combateCampesinos de las clases extendidas se especifica la llamada a cada una de ellas, con sus parámetros... 
        //Case general en clase Acciones. Para conquistas...
        //Obtenemos en núnero de campis que se que se organizan para la defensa
        int campisresistentes = feudo.molestiaConquista(culturaagresor);

        if (campisresistentes > 0) {

            //Creamos el grupo de tropas....ncampesions a 100 de pericia y moral.
            TropasK campis = new TropasK(campisresistentes, 100, 100);
            Map<TTropas, TropasK> campesinos = new HashMap();
            campesinos.put(TTropas.CAMPESINOS, campis);
            GrupoTropas defensores = new GrupoTropas(campesinos, false, false);

            //Y realizamos el ataque en la aldea
            atacar(ataca, defensores, pCombateK.ALDEA);

            /*
             * La conquista de feudos es especial, pues damos la propiedad de las tierras
             * si se produce una victoria simple, mientras que para saquear y arrasar se necesita
             * una victoria con retirada del defensor.
             */
            if (victoriadefensor) {
                        //Los campesinos repelen la conquista
                //aux.print();
                //reporte.setExito(false);
                return;
            } else {
                exito = true;
                return;
            }
        }
    }

    /*
     * Esta función se desarrolla cuando ya no hay tropas en el feudo, bien porque
     * no las hubiera bien porque han sido destruidas.
     */
    /**
     * OperacionSinTropas: Se ejecuta la misión de la contienda contra el señor
     * de las tierras. Se actualiza la capacidad de ataque de las unidades
     * militares. Si todavía hay capacidad de ataque se ejecuta
     * CombateConCampesinos.
     */
    public void operacionSinTropas(GrupoTropas ataca, FeudoK feudo, TCultura culturaagresor) {
        //Esta implentacion es para CONQUSITAS (en feudos con propietario).
        //Sobreescribir para SAQUEOS Y ARRASES
        exito = true;
    }

}
