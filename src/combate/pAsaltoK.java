/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package combate;

import sub.TTropas;

/**
 *
 * @author Juanse
 */
public enum pAsaltoK {
    TORRE,
    CASTILLO,
    FORTALEZA,
    CIUDAD;
    
    public double poderTAsalto(TTropas tr) {
        switch (tr) {
            case ARQUEROS:
                return 4.0;
            case CABALLEROS:
                return 0.0;
            case JINETES:
                return 0.0;
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
    }
    public double defensaTAsalto(TTropas tr) {
        switch (tr) {
            case ARQUEROS:
                return 3.0;
            case CABALLEROS:
                return 0.0;
            case JINETES:
                return 0.0;
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
    }
     public double poderEscalas(TTropas tr) {
        switch (tr) {
            case ARQUEROS:
                return 1.0;
            case CABALLEROS:
                return 0.0;
            case JINETES:
                return 0.0;
            case LANCEROS:
                return 1.0;
            case CAMPESINOS:
            case LEVAS:
                return 1.0;
            case SOLDADOS:
                return 1.0;
            default:
                return 0.0;
        }
    }
     public double defensaEscalas(TTropas tr) {
        switch (tr) {
            case ARQUEROS:
                return 1.0;
            case CABALLEROS:
                return 0.0;
            case JINETES:
                return 0.0;
            case LANCEROS:
                return 1.0;
            case CAMPESINOS:
            case LEVAS:
                return 1.0;
            case SOLDADOS:
                return 1.0;
            default:
                return 0.0;
        }
    }
    public double defensaGuarnicion(TTropas tr, double conservacion) {
        switch (this) {
            case TORRE:
                switch (tr) {
                    case ARQUEROS:
                        return 6.0+8.0*conservacion;
                    case CABALLEROS:
                        return 8.0+2.0*conservacion;
                    case JINETES:
                        return 4.0+2.0*conservacion;
                    case LANCEROS:
                        return 4.0+2.0*conservacion;
                    case CAMPESINOS:
                    case LEVAS:
                        return 2.0+2.0*conservacion;
                    case SOLDADOS:
                        return 5.0+2.0*conservacion;
                    default:
                        return 0.0;
                }

            case CASTILLO:
                switch (tr) {
                    case ARQUEROS:
                        return 8.0+8.0*conservacion;
                    case CABALLEROS:
                        return 12.0+2.0*conservacion;
                    case JINETES:
                        return 6.0+2.0*conservacion;
                    case LANCEROS:
                        return 6.0+2.0*conservacion;
                    case CAMPESINOS:
                    case LEVAS:
                        return 2.0+2.0*conservacion;
                    case SOLDADOS:
                        return 6.0+4.0*conservacion;
                    default:
                        return 0.0;
                }
            case FORTALEZA:
                //fuerza = new double[]{4.0, 20.0, 6.0, 4.0, 2.0, 5.0};
                switch (tr) {
                    case ARQUEROS:
                        return 10.0+12.0*conservacion;
                    case CABALLEROS:
                        return 20.0;
                    case JINETES:
                        return 6.0;
                    case LANCEROS:
                        return 6.0+2.0*conservacion;
                    case CAMPESINOS:
                    case LEVAS:
                        return 2.0+2.0*conservacion;
                    case SOLDADOS:
                        return 6.0+4.0*conservacion;
                    default:
                        return 0.0;
                }
            case CIUDAD:
                switch (tr) {
                    case ARQUEROS:
                        return 12.0+10.0*conservacion;
                    case CABALLEROS:
                        return 20.0;
                    case JINETES:
                        return 6.0;
                    case LANCEROS:
                        return 6.0+2.0*conservacion;
                    case CAMPESINOS:
                    case LEVAS:
                        return 2.0+2.0*conservacion;
                    case SOLDADOS:
                        return 6.0+4.0*conservacion;
                    default:
                        return 0.0;
                }
            default:
                return 0.0;
        }
    }
}
