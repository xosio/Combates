/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package combate;


import sub.TTropas;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xosio && Kordigan
 * 
 * Modificadores de los combates en función del terreno/lugar donde se producen
 * 
 */
public enum pCombateK {

    ALDEA,
    TORRE,
    CASTILLO,
    FORTALEZA,
    COSTA,
    RIO,
    LLANO,
    MONTAÑA,
    BOSQUE,
    CIUDAD,
    DESIERTO,
    PANTANO,
    MAR;

  
    
    /*
     * 
     * ¡¡¡ Con la implementación actual las tropas no tienen poder en los feudos
     * del tipo CIUDAD.(20/11/2016)
     *
     *
     */
    
   
    
    public pCombateK enFeudo(FeudoK f) {
        pCombateK p = pCombateK.valueOf(f.getTfeudo().toString());
        return p;
    }

   

   

    /**
     * *********** Arrasar
     * ******************************************************
     */
    
    //Todos estos datos hay que ponerlos en la clase correspondiente
    
    //TTropas
    private int poderarrase[] = {1, 0, 0, 3, 1, 5};

    //TTropas
    public int getpoderarrase(int indice) {
        return poderarrase[indice];
    }
    
    //TMes (este lo tengo. Pero no está en el paquete sub.
    private double[] arrasemes = {0.5, 1.0, 1.0, 1.5, 1.5, 1.5, 2.0, 2.0, 0.0, 0.0, 0.25, 0.25};

    //TMes
    public double getarrasemes(int mes) {
        return arrasemes[mes];
    }

    //FeudoK. Pero no sé a que se refiere el indice.
    //El índice se refiere al nivel de satisfacción de los campesinos
    //psrs calcular la población que atacaría
    public double getsatisfaccionarrase(int indice) {
        double satisfaccion = 1.0;
        switch (indice) {
            case 0:
                satisfaccion = 1.5 + Math.random() * 0.5;
                break;
            case 1:
                satisfaccion = 1.4 + Math.random() * 0.3;
                break;
            case 2:
                satisfaccion = 1.2 + Math.random() * 0.2;
                break;
            case 3:
                satisfaccion = 1.1 + Math.random() * 0.1;
                break;
            default:
                return 1.0;
        }
        return satisfaccion;
    }

    
    
    
    
    double getbajasvictoria() {
        switch (this) {
            case ALDEA:
            /*fuerza = 0.2;
             ;*/
            case BOSQUE:
            case MONTAÑA:
                //fuerza = 0.2;
                return 0.2;
            case MAR:
            case PANTANO:
                //fuerza = 0.1;
                return 0.1;
            case RIO:
            case COSTA:
            case LLANO:
                //fuerza = 0.3;;
                return 0.3;
            default:
                //return 0.3;
                return 0.0;
        }

    }

    //getbajasderrota() Xosio    
    double getbajasderrota() {
        switch (this) {
            case ALDEA:
            //fuerza = 0.5;
            case BOSQUE:
            case MONTAÑA:
                //fuerza = 0.5;
                return 0.5;
            case MAR:
            case PANTANO:
                //fuerza = 0.3;
                return 0.3;
            case LLANO:
                //fuerza = 0.7;
                return 0.7;
            default:
                return 0.7;
        }

    }

    //getbajasempate() Xosio
    double getbajasempate() {
        switch (this) {
            case ALDEA:
            // fuerza = 0.4
            case BOSQUE:
            case MONTAÑA:
                //fuerza = 0.4;
                return 0.4;
            case MAR:
            case PANTANO:
                //fuerza = 0.3;
                return 0.3;
            case LLANO:
                // fuerza = 0.5;
                return 0.5;
            default:
                return 0.5;
        }
    }
    //Nos da el tipo de unidades que bonifica a @param tr según el tipo de terreno.
    List<TTropas> getubonificadora(TTropas tr) {
        List<TTropas> ubonificadora;
        ubonificadora = new ArrayList();
        //Arquero----------->0
        //Caballero---------->1
        //Jinete------------->2
        //Lancero------------>3
        //Leva-------------->4
        //Soldado------------>5
        switch (this) {
            case BOSQUE:
                //ubonificadora = new int[][]{{0}, {0}, {2}, {1, 2}, {4}, {0, 3}};
                switch (tr) {
                    //case ARQUEROS:
                    //ubonificadora.add(TTropas.ARQUEROS);
                    //return ubonificadora;
                    case CABALLEROS:
                        ubonificadora.add(TTropas.ARQUEROS);
                        return ubonificadora;
                    //case JINETES:
                    //ubonificadora.add(TTropas.JINETES);
                    //return ubonificadora;
                    case LANCEROS:
                        ubonificadora.add(TTropas.CABALLEROS);
                        ubonificadora.add(TTropas.JINETES);
                        return ubonificadora;
                    //case LEVAS:
                    //ubonificadora.add(TTropas.LEVAS);
                    //return ubonificadora;
                    case SOLDADOS:
                        ubonificadora.add(TTropas.ARQUEROS);
                        ubonificadora.add(TTropas.LANCEROS);
                        return ubonificadora;
                }
                break;
            case MONTAÑA:
                //ubonificadora = new int[][]{{3, 4}, {0}, {2}, {1, 2}, {4}, {0, 3}};
                switch (tr) {
                    case ARQUEROS:
                        ubonificadora.add(TTropas.LANCEROS);
                        ubonificadora.add(TTropas.LEVAS);
                        return ubonificadora;
                    case CABALLEROS:
                        ubonificadora.add(TTropas.ARQUEROS);
                        return ubonificadora;
                    //case JINETES:
                    //ubonificadora.add(TTropas.JINETES);
                    //return ubonificadora;
                    case LANCEROS:
                        ubonificadora.add(TTropas.JINETES);
                        ubonificadora.add(TTropas.CABALLEROS);
                        return ubonificadora;
                    //case LEVAS:
                    //ubonificadora.add(TTropas.LEVAS);
                    //return ubonificadora;
                    case SOLDADOS:
                        ubonificadora.add(TTropas.ARQUEROS);
                        ubonificadora.add(TTropas.LANCEROS);
                        return ubonificadora;

                }

                break;
            case RIO:
            case COSTA:
            case LLANO:
                //ubonificadora = new int[][]{{3, 4}, {0, 5}, {0}, {2}, {4}, {0, 3}};
                switch (tr) {
                    case ARQUEROS:
                        ubonificadora.add(TTropas.LANCEROS);
                        ubonificadora.add(TTropas.LEVAS);
                        return ubonificadora;
                    case CABALLEROS:
                        ubonificadora.add(TTropas.ARQUEROS);
                        ubonificadora.add(TTropas.SOLDADOS);
                        return ubonificadora;
                    //case JINETES:
                    //ubonificadora.add(TTropas.JINETES);
                    //return ubonificadora;
                    case LANCEROS:
                        ubonificadora.add(TTropas.JINETES);
                        ubonificadora.add(TTropas.CABALLEROS);
                        return ubonificadora;
                    //case LEVAS:
                    //ubonificadora.add(TTropas.LEVAS);
                    //return ubonificadora;
                    case SOLDADOS:
                        ubonificadora.add(TTropas.ARQUEROS);
                        ubonificadora.add(TTropas.LANCEROS);
                        return ubonificadora;
                }
                break;
            case MAR:
                //ubonificadora = new int[][]{{3, 4}, {0}, {2}, {3}, {4}, {0, 3}};
                switch (tr) {
                    case ARQUEROS:
                        ubonificadora.add(TTropas.LANCEROS);
                        ubonificadora.add(TTropas.LEVAS);
                        return ubonificadora;
                    case CABALLEROS:
                        ubonificadora.add(TTropas.ARQUEROS);
                        return ubonificadora;
                    //case JINETES:
                    //ubonificadora.add(TTropas.JINETES);
                    //return ubonificadora;
                    //case LANCEROS:
                    //ubonificadora.add(TTropas.JINETES);
                    //ubonificadora.add(TTropas.CABALLEROS);
                    //return ubonificadora;
                    //case LEVAS:
                    //ubonificadora.add(TTropas.LEVAS);
                    //return ubonificadora;
                    case SOLDADOS:
                        ubonificadora.add(TTropas.ARQUEROS);
                        ubonificadora.add(TTropas.LANCEROS);
                        return ubonificadora;
                }

                break;
            case PANTANO:
                //ubonificadora = new int[][]{{3, 4}, {0}, {2}, {1, 2}, {4}, {0, 3}};
                switch (tr) {
                    case ARQUEROS:
                        ubonificadora.add(TTropas.LANCEROS);
                        ubonificadora.add(TTropas.LEVAS);
                        return ubonificadora;
                    case CABALLEROS:
                        ubonificadora.add(TTropas.ARQUEROS);
                        return ubonificadora;
                    //case JINETES:
                    //ubonificadora.add(TTropas.JINETES);
                    //return ubonificadora;
                    case LANCEROS:
                        ubonificadora.add(TTropas.JINETES);
                        ubonificadora.add(TTropas.CABALLEROS);
                        return ubonificadora;
                    //case LEVAS:
                    //ubonificadora.add(TTropas.LEVAS);
                    //return ubonificadora;
                    case SOLDADOS:
                        ubonificadora.add(TTropas.ARQUEROS);
                        ubonificadora.add(TTropas.LANCEROS);
                        return ubonificadora;
                }
                break;
            default:
                //ubonificadora.add(TTropas.LEVAS);
                return null; //??????

        }
        return ubonificadora; //No sé si está todo controlado con esto: comprobar.
    }

  
    //bonifica Xosio
    public boolean bonifica(TTropas tr) {

        switch (this) {
            case BOSQUE:
                //bonif = new boolean[]{false, true, false, false, false, true};
                if (tr.equals(TTropas.CABALLEROS)
                        || tr.equals(TTropas.SOLDADOS)) {
                    return true;
                } else {
                    return false;
                }
            case MONTAÑA:
                //bonif = new boolean[]{true, true, false, true, false, true};
                if (tr.equals(TTropas.ARQUEROS)
                        || tr.equals(TTropas.CABALLEROS)
                        || tr.equals(TTropas.LANCEROS)
                        || tr.equals(TTropas.SOLDADOS)) {
                    return true;
                } else {
                    return false;
                }

            case MAR:
                //bonif = new boolean[]{true, true, false, true, false, true};
                if (tr.equals(TTropas.ARQUEROS)
                        || tr.equals(TTropas.CABALLEROS)
                        || tr.equals(TTropas.LANCEROS)
                        || tr.equals(TTropas.SOLDADOS)) {
                    return true;
                } else {
                    return false;
                }

            case PANTANO:
                //bonif = new boolean[]{true, true, false, false, false, true};
                if (tr.equals(TTropas.ARQUEROS)
                        || tr.equals(TTropas.CABALLEROS)
                        || tr.equals(TTropas.SOLDADOS)) {
                    return true;
                } else {
                    return false;
                }
            default:
                return false;
        }
    }

    //Función que nos da la fuerza de ataque de cada unidad que ataca.
    // fuerzaAtaca xosio
    public double fuerzaAtaca(TTropas tr) {

        switch (this) {
            case ALDEA:
            //fuerza = {3.0, 12.0, 6.0, 4.0, 1.0, 2.0};

            case BOSQUE:
            case MONTAÑA:
                //fuerza = new double[]{3.0, 12.0, 6.0, 4.0, 1.0, 2.0};
                //fuerza = {3.0, 12.0, 6.0, 4.0, 1.0, 2.0};
                switch (tr) {
                    case ARQUEROS:
                        return 3.0;
                    case CABALLEROS:
                        return 12.0;
                    case JINETES:
                        return 6.0;
                    case LANCEROS:
                        return 4.0;
                    case CAMPESINOS:
                    case LEVAS:
                        return 1.0;
                    case SOLDADOS:
                        return 2.0;
                    default:
                        return 0.0;
                }
            case RIO:
            case COSTA:
            case LLANO:
                //fuerza = new double[]{4.0, 20.0, 8.0, 4.0, 1.0, 2.0};
                switch (tr) {
                    case ARQUEROS:
                        return 4.0;
                    case CABALLEROS:
                        return 20.0;
                    case JINETES:
                        return 8.0;
                    case LANCEROS:
                        return 4.0;
                    case CAMPESINOS:
                    case LEVAS:
                        return 1.0;
                    case SOLDADOS:
                        return 2.0;
                    default:
                        return 0.0;
                }
            case MAR:
                //fuerza = new double[]{3.0, 10.0, 6.0, 4.0, 1.0, 2.0};
                switch (tr) {
                    case ARQUEROS:
                        return 3.0;
                    case CABALLEROS:
                        return 10.0;
                    case JINETES:
                        return 6.0;
                    case LANCEROS:
                        return 4.0;
                    case CAMPESINOS:
                    case LEVAS:
                        return 1.0;
                    case SOLDADOS:
                        return 2.0;
                    default:
                        return 0.0;
                }

            case PANTANO:
                // fuerza = new double[]{5.0, 7.0, 5.0, 3.0, 1.0, 2.0};
                switch (tr) {
                    case ARQUEROS:
                        return 5.0;
                    case CABALLEROS:
                        return 7.0;
                    case JINETES:
                        return 5.0;
                    case LANCEROS:
                        return 3.0;
                    case CAMPESINOS:
                    case LEVAS:
                        return 1.0;
                    case SOLDADOS:
                        return 2.0;
                    default:
                        return 0.0;
                }
            default:
                return 0;
        }

    }

    //función que nos da la fuerza de ataque de cada unidad que defiende.
    //fuerzaDefiende Xosio
    public double fuerzaDefiende(TTropas tr) {
        switch (this) {
            case ALDEA:
                //fuerza = new double[]{3.0, 12.0, 6.0, 4.0, 2.0, 3.0};
                switch (tr) {
                    case ARQUEROS:
                        return 3.0;
                    case CABALLEROS:
                        return 12.0;
                    case JINETES:
                        return 6.0;
                    case LANCEROS:
                        return 4.0;
                    case CAMPESINOS:
                    case LEVAS:
                        return 2.0;
                    case SOLDADOS:
                        return 3.0;
                    default:
                        return 0.0;
                }
            case BOSQUE:
            case MONTAÑA:
                //fuerza = new double[]{3.0, 12.0, 6.0, 4.0, 1.0, 3.0};
                switch (tr) {
                    case ARQUEROS:
                        return 3.0;
                    case CABALLEROS:
                        return 12.0;
                    case JINETES:
                        return 6.0;
                    case LANCEROS:
                        return 4.0;
                    case CAMPESINOS:
                    case LEVAS:
                        return 1.0;
                    case SOLDADOS:
                        return 3.0;
                    default:
                        return 0.0;
                }

            case COSTA:
            case RIO:
            case LLANO:
                //fuerza = new double[]{4.0, 20.0, 8.0, 4.0, 1.0, 2.0};
                switch (tr) {
                    case ARQUEROS:
                        return 4.0;
                    case CABALLEROS:
                        return 20.0;
                    case JINETES:
                        return 8.0;
                    case LANCEROS:
                        return 4.0;
                    case CAMPESINOS:
                    case LEVAS:
                        return 1.0;
                    case SOLDADOS:
                        return 2.0;
                    default:
                        return 0.0;
                }

            case MAR:
                //fuerza = new double[]{3.0, 10.0, 6.0, 4.0, 1.0, 2.0};
                switch (tr) {
                    case ARQUEROS:
                        return 3.0;
                    case CABALLEROS:
                        return 10.0;
                    case JINETES:
                        return 6.0;
                    case LANCEROS:
                        return 4.0;
                    case CAMPESINOS:
                    case LEVAS:
                        return 1.0;
                    case SOLDADOS:
                        return 2.0;
                    default:
                        return 0.0;
                }

            case PANTANO:
                //  fuerza = new double[]{5.0, 7.0, 5.0, 3.0, 1.0, 2.0};
                switch (tr) {
                    case ARQUEROS:
                        return 5.0;
                    case CABALLEROS:
                        return 7.0;
                    case JINETES:
                        return 5.0;
                    case LANCEROS:
                        return 3.0;
                    case CAMPESINOS:
                    case LEVAS:
                        return 1.0;
                    case SOLDADOS:
                        return 2.0;
                    default:
                        return 0.0;
                }
            default:
                return 0;
        }

    }

    //Función que da la resistencia de cada unidad que ataca.
    //defensaAtaca Kordi
    /*
     double defensaAtaca(int indice) {
     double[] fuerza;
     switch (this) {
     case ALDEA:
     case BOSQUE:
     case MONTE:
     fuerza = new double[]{4.0, 20.0, 6.0, 4.0, 2.0, 5.0};
     break;
     case LLANO:
     fuerza = new double[]{3.0, 18.0, 6.0, 3.0, 1.0, 4.0};
     break;
     case MAR:
     fuerza = new double[]{4.0, 20.0, 5.0, 4.0, 1.0, 4.0};
     break;
     case PANTANO:
     fuerza = new double[]{3.0, 20.0, 5.0, 3.0, 2.0, 5.0};
     break;
     default:
     return 0;
     }
     return fuerza[indice];
     }
     */
    //Función que da la resistencia de cada unidad al ser atacada.
    //defensaAtaca Xosio
    public double defensaAtaca(TTropas tr) {
        switch (this) {
            case ALDEA:
            //fuerza = new double[]{4.0, 20.0, 6.0, 4.0, 2.0, 5.0};

            case BOSQUE:
            case MONTAÑA:
                //fuerza = new double[]{4.0, 20.0, 6.0, 4.0, 2.0, 5.0};
                switch (tr) {
                    case ARQUEROS:
                        return 4.0;
                    case CABALLEROS:
                        return 20.0;
                    case JINETES:
                        return 6.0;
                    case LANCEROS:
                        return 4.0;
                    case CAMPESINOS:
                    case LEVAS:
                        return 2.0;
                    case SOLDADOS:
                        return 5.0;
                    default:
                        return 0.0;
                }

            case COSTA:
            case RIO:
            case LLANO:
                //fuerza = new double[]{3.0, 18.0, 6.0, 3.0, 1.0, 4.0};
                switch (tr) {
                    case ARQUEROS:
                        return 3.0;
                    case CABALLEROS:
                        return 18.0;
                    case JINETES:
                        return 6.0;
                    case LANCEROS:
                        return 3.0;
                    case CAMPESINOS:
                    case LEVAS:
                        return 1.0;
                    case SOLDADOS:
                        return 4.0;
                    default:
                        return 0.0;
                }

            case MAR:
                //fuerza = new double[]{4.0, 20.0, 5.0, 4.0, 1.0, 4.0};
                switch (tr) {
                    case ARQUEROS:
                        return 4.0;
                    case CABALLEROS:
                        return 20.0;
                    case JINETES:
                        return 5.0;
                    case LANCEROS:
                        return 4.0;
                    case CAMPESINOS:
                    case LEVAS:
                        return 1.0;
                    case SOLDADOS:
                        return 4.0;
                    default:
                        return 0.0;
                }

            case PANTANO:
                //fuerza = new double[]{3.0, 20.0, 5.0, 3.0, 2.0, 5.0};
                switch (tr) {
                    case ARQUEROS:
                        return 3.0;
                    case CABALLEROS:
                        return 20.0;
                    case JINETES:
                        return 5.0;
                    case LANCEROS:
                        return 3.0;
                    case CAMPESINOS:
                    case LEVAS:
                        return 2.0;
                    case SOLDADOS:
                        return 5.0;
                    default:
                        return 0.0;
                }
            default:
                return 0;
        }
    }

    //Función que da la resistencia de cada unidad que defiende.
    //defensaDefiende Kordi
    /*
     double defensaDefiende(int indice) {
     double[] fuerza;
     switch (this) {
     case ALDEA:
     fuerza = new double[]{4.0, 20.0, 6.0, 4.0, 2.0, 5.0};
     break;
     case BOSQUE:
     case MONTE:
     fuerza = new double[]{4.0, 20.0, 6.0, 4.0, 2.0, 5.0};
     break;
     case LLANO:
     fuerza = new double[]{3.0, 18.0, 6.0, 3.0, 1.0, 4.0};
     break;
     case MAR:
     fuerza = new double[]{4.0, 20.0, 5.0, 4.0, 1.0, 4.0};
     break;
     case PANTANO:
     fuerza = new double[]{3.0, 20.0, 5.0, 3.0, 2.0, 5.0};
     break;
     default:
     return 0;
     }
     return fuerza[indice];
     }
    
     */
    public double defensaDefiende(TTropas tr) {
        switch (this) {
            case ALDEA:
            //fuerza = new double[]{4.0, 20.0, 6.0, 4.0, 2.0, 5.0};

            case BOSQUE:
            case MONTAÑA:
                //fuerza = new double[]{4.0, 20.0, 6.0, 4.0, 2.0, 5.0};
                switch (tr) {
                    case ARQUEROS:
                        return 4.0;
                    case CABALLEROS:
                        return 20.0;
                    case JINETES:
                        return 6.0;
                    case LANCEROS:
                        return 4.0;
                    case CAMPESINOS:
                    case LEVAS:
                        return 2.0;
                    case SOLDADOS:
                        return 5.0;
                    default:
                        return 0;
                }

            case COSTA:
            case RIO:
            case LLANO:
                //fuerza = new double[]{3.0, 18.0, 6.0, 3.0, 1.0, 4.0};
                switch (tr) {
                    case ARQUEROS:
                        return 3.0;
                    case CABALLEROS:
                        return 18.0;
                    case JINETES:
                        return 6.0;
                    case LANCEROS:
                        return 3.0;
                    case CAMPESINOS:
                    case LEVAS:
                        return 1.0;
                    case SOLDADOS:
                        return 4.0;
                    default:
                        return 0;
                }

            case MAR:
                //fuerza = new double[]{4.0, 20.0, 5.0, 4.0, 1.0, 4.0};
                switch (tr) {
                    case ARQUEROS:
                        return 4.0;
                    case CABALLEROS:
                        return 20.0;
                    case JINETES:
                        return 5.0;
                    case LANCEROS:
                        return 4.0;
                    case CAMPESINOS:
                    case LEVAS:
                        return 1.0;
                    case SOLDADOS:
                        return 4.0;
                    default:
                        return 0.0;
                }

            case PANTANO:
                //  fuerza = new double[]{3.0, 20.0, 5.0, 3.0, 2.0, 5.0};
                switch (tr) {
                    case ARQUEROS:
                        return 3.0;
                    case CABALLEROS:
                        return 20.0;
                    case JINETES:
                        return 5.0;
                    case LANCEROS:
                        return 3.0;
                    case CAMPESINOS:
                    case LEVAS:
                        return 2.0;
                    case SOLDADOS:
                        return 5.0;
                    default:
                        return 0.0;

                }
            default:
                return 0;
        }
    }

    //Función que nos da la penalización de iniciciativa del grupo defensor
    //Donde se ha de superar una tirada con el número de exploradores para no sufrir penalización
    double penalizacionIniciativa(int nexploradores) {
        double penalizacion = 1.0;
        switch (this) {
            case COSTA:
            case RIO:
            case LLANO:
                if (nexploradores < (5.0 * Math.random() - 3.0)) {
                    penalizacion = 0.9;
                }
                break;
            case BOSQUE:
                if (nexploradores < (40.0 * Math.random())) {
                    penalizacion = 0.25;
                }
                break;
            case MONTAÑA:
                if (nexploradores < (20.0 * Math.random())) {
                    penalizacion = 0.70;
                }
                break;
            case PANTANO:
                if (nexploradores < (30.0 * Math.random())) {
                    penalizacion = 0.8;
                }
                break;
            default:
                return 1.0;
        }
        return penalizacion;
    }

    double penalizacionIniciativaM(int nexploradores) {
        double penalizacion = 1.0;
        switch (this) {
            case COSTA:
            case RIO:
            case LLANO:
                if (nexploradores < (5.0 * Math.random() - 1.0)) {
                    penalizacion = 0.8;
                }
                break;
            case BOSQUE:
                if (nexploradores < (60.0 * Math.random())) {
                    penalizacion = 0.25;
                }
                break;
            case MONTAÑA:
                if (nexploradores < (40.0 * Math.random())) {
                    penalizacion = 0.5;
                }
                break;
            case PANTANO:
                if (nexploradores < (50.0 * Math.random())) {
                    penalizacion = 0.8;
                }
                break;
            default:
                return 1.0;
        }
        return penalizacion;
    }
    
}
