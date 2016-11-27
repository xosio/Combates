/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sub;

/**
 *
 * @author Xosio
 */
public enum TCultura {
    //TIPOS DE CULTURA
    SARRACENOS,
    CRISTIANOS,
    BÁRBAROS,
    RELIGIOSOS,
    JUDIOS,
    CAMPESINOS;
    
     public static TCultura Cultura(String cultura) {
         TCultura c;
         c=TCultura.valueOf(cultura.toUpperCase());
         return c;
     }
    public static String getCultura(TCultura c,boolean sexo) {     
        String rango = "";
        switch (c) {
            case SARRACENOS:
                if (sexo) {
                    rango = "Sarraceno";
                } else {
                    rango = "Sarracena";
                }
                break;
            case CRISTIANOS:
                if (sexo) {
                    rango = "Cristiano";
                } else {
                    rango = "Cristiana";
                }
                break;
            case BÁRBAROS:
                if (sexo) {
                    rango = "Barbaro";
                } else {
                    rango = "Barbara";
                }
                break;
            case JUDIOS:
                if (sexo) {
                    rango = "Judio";
                } else {
                    rango = "Judia";
                }
                break;
            case RELIGIOSOS:
                if (sexo) {
                    rango = "Religioso";
                } else {
                    rango = "Religiosa";
                }
                break;
        }
        return rango;
    }
    
     public static String setImagenPj(TCultura c,boolean sexo) {
        String imgpj = "";
        switch (c) {
            case SARRACENOS:
                if (sexo) {
                    imgpj = "sarraceno.gif";
                } else {
                    imgpj = "sarracena.gif";
                }
                break;
            case CRISTIANOS:
                if (sexo) {
                    imgpj = "cristiano.gif";
                } else {
                    imgpj = "dama.gif";
                }
                break;
            case BÁRBAROS:
                if (sexo) {
                    imgpj = "barbaro.gif";
                } else {
                    imgpj = "barbara.gif";
                }
                break;
            case JUDIOS:
                if (sexo) {
                    imgpj = "judio.gif";
                } else {
                    imgpj = "judia.gif";
                }
                break;
            case RELIGIOSOS:
                if (sexo) {
                    imgpj = "judio.gif";
                } else {
                    imgpj = "judia.gif";
                }
                break;
        }
        return imgpj;
    }
     
    /**
     * Devuelve el factor de resistencia al cambio de cultura ante una agresión del atacante.
     * @param atacante
     * @return 
     */ 
    public double factorCultura(TCultura atacante) {
        /* 0-> Sin cultura-->CAMPESINOS
         * 1-> Bárbaro--> BÁRBAROS
         * 2-> Cristiano--> CRISTIANOS
         * 3-> Religioso--> RELIGIOSOS
         * 4-> Sarraceno-->SARRACENOS
         JUDIOS,
         */
        switch (this) {
            case CAMPESINOS: //0
                //{1.0, 1.0, 1.0, 1.0}
                return 1.0;
            case BÁRBAROS:
                //{1.0, 1.25, 1.5, 1.25},
                switch (atacante) {
                    case BÁRBAROS:
                        return 1.0;
                    case CRISTIANOS:
                        return 1.00;
                    case RELIGIOSOS:
                        return 1.00;
                    case SARRACENOS:
                        return 1.25;
                    default:
                        return 1.00; //??
                }
            case CRISTIANOS:
                //{1.5, 1.0, 0.8, 1.25},
                switch (atacante) {
                    case BÁRBAROS:
                        return 1.5;
                    case CRISTIANOS:
                        return 1.00;
                    case RELIGIOSOS:
                        return 0.80;
                    case SARRACENOS:
                        return 1.25;
                    default:
                        return 1.00; //??

                }
            case RELIGIOSOS:
                //2.0, 1.0, 0.5, 2.0},
                switch (atacante) {
                    case BÁRBAROS:
                        return 2.00;
                    case CRISTIANOS:
                        return 1.00;
                    case RELIGIOSOS:
                        return 0.50;
                    case SARRACENOS:
                        return 2.00;
                    default:
                        return 1.00; //??

                }
            case SARRACENOS:
                //{1.5, 1.25, 1.25, 1.0}
                switch (atacante) {
                    case BÁRBAROS:
                        return 1.50;
                    case CRISTIANOS:
                        return 1.25;
                    case RELIGIOSOS:
                        return 1.25;
                    case SARRACENOS:
                        return 1.00;
                    default:
                        return 1.00; //??

                }
            default:
                return 1.00; //??

        }

    }
    
}
