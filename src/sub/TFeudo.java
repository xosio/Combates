/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sub;

import bayren.utiles.RangoAleatorio;

/**
 *
 * @author Xosio
 */
public enum TFeudo {

    //TIPOFEUDO 
    COSTA,
    RIO,
    LLANO,
    MONTAÑA,
    BOSQUE,
    CIUDAD,
    DESIERTO,
    PANTANO,
    MAR;

    public int limiteProduccion(boolean mejora) {
        int limite = 0;
        switch (this) {
            //costa, llano, bosque, rio, pantano, montaña, ciudad, desierto
            case COSTA:
                limite = 450;
                break;
            case LLANO:
                limite = 400;
                break;

            case RIO:
                if (mejora) {
                    limite = 450;
                } else {
                    limite = 400;
                }
            break;
            case MONTAÑA:
                limite = 300;
                break;
            case BOSQUE:
                if (mejora) {
                    limite = 400;
                } else {
                    limite = 300;
                }
                break;
            case DESIERTO:
            case PANTANO:
                if (mejora) {
                    limite = 350;
                } else {
                    limite = 200;
                }
                break;
        }
        return limite;
    }

    public int poblacionInicio() {
        RangoAleatorio suerte = new RangoAleatorio();
        int almas = 0;
        switch (this) {
            //costa, llano, bosque, rio, pantano, montaña, ciudad, desierto
            case COSTA:
                almas = suerte.nextIncInc(400, 500);
                break;
            case RIO:
            case LLANO:
                almas = suerte.nextIncInc(350, 450);
                break;
            case CIUDAD:
                almas = 400;
                break;
            case MONTAÑA:
            case BOSQUE:
                almas = suerte.nextIncInc(265, 335);
                break;
            case DESIERTO:
            case PANTANO:
                almas = suerte.nextIncInc(175, 225);
                break;
        }
        return almas;
    }

    public int Produccion(boolean mejora, int campis) {
        int limite;
        int pr = 0;
        limite = this.limiteProduccion(mejora);
        if (campis > limite) {
            campis = limite;
        }
        switch (this) {
            //costa, llano, bosque, rio, pantano, montaña, ciudad, desierto
            case COSTA:
                pr = prRio(campis);
                break;
            case RIO:
                if (mejora) {
                    pr = prRio(campis);
                } else {
                    pr = prLlano(campis);
                }
                break;
            case LLANO:
                pr = prLlano(campis);
                break;
            case MONTAÑA:
                pr = prMont(campis);
                break;
            case BOSQUE:
                if (mejora) {
                    pr = prLlano(campis);
                } else {
                    pr = prMont(campis);
                }
                break;
            case DESIERTO:
            case PANTANO:
                if (mejora) {
                    pr = prDesMej(campis);
                } else {
                    pr = prDes(campis);
                }
                break;
        }
        return pr;
    }

    public int prLlano(int campis) {
        if (campis < 35) {
            return campis * 7;
        } else {
            if (campis < 70) {
                return (campis - 35) * 3 + 245;
            } else {
                if (campis < 90) {
                    return (campis - 70) * 5 + 350;
                } else {
                    if (campis < 200) {
                        return (campis - 90) * 2 + 450;
                    } else {
                        return (campis - 200) + 650;
                    }
                }
            }
        }
    }

    public int prRio(int campis) {
        if (campis < 40) {
            return campis * 7;
        } else {
            if (campis < 80) {
                return (campis - 40) * 3 + 280;
            } else {
                if (campis < 105) {
                    return (campis - 80) * 5 + 400;
                } else {
                    if (campis < 230) {
                        return (campis - 105) * 2 + 525;
                    } else {
                        return (campis - 230) + 1150;
                    }
                }
            }
        }
    }

    public int prMont(int campis) {
        if (campis < 27) {
            return campis * 7;
        } else {
            if (campis < 54) {
                return (campis - 27) * 3 + 189;
            } else {
                if (campis < 66) {
                    return (campis - 54) * 5 + 270;
                } else {
                    if (campis < 125) {
                        return (campis - 66) * 2 + 330;
                    } else {
                        return (campis - 125) + 448;
                    }
                }
            }
        }
    }

    public int prDesMej(int campis) {
        if (campis < 30) {
            return campis * 7;
        } else {
            if (campis < 60) {
                return (campis - 30) * 3 + 210;
            } else {
                if (campis < 80) {
                    return (campis - 60) * 5 + 300;
                } else {
                    if (campis < 175) {
                        return (campis - 80) * 2 + 400;
                    } else {
                        return (campis - 175) + 590;
                    }
                }
            }
        }
    }

    public int prDes(int campis) {
        if (campis < 18) {
            return campis * 7;
        } else {
            if (campis < 36) {
                return (campis - 18) * 3 + 126;
            } else {
                if (campis < 45) {
                    return (campis - 36) * 5 + 180;
                } else {
                    if (campis < 90) {
                        return (campis - 35) * 2 + 225;
                    } else {
                        return (campis - 90) + 315;
                    }
                }
            }
        }
    }

}
