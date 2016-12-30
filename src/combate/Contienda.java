/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
// modificación en linea
package combate;

import sub.TTropas;
import java.util.Map;
import sub.TCultura;
import sub.TEdificio;

/**
 *
 * @author Rosita
 */
public class Contienda {

    //Propiedades
    private GrupoTropas ataca;
    private GrupoTropas defiende;

    private FeudoK feudo;

    //private String accion;
    private int mes;

    private TCultura culturaagresor;

    //Resultado-->un reporte...
    private Reporte reporte;

    //Accion
    private Acciones accion;

    //Constructor
    public Contienda(GrupoTropas ataca, GrupoTropas defiende, FeudoK feudo, String operacion, TCultura culturaagresor, int mes) {
        this.ataca = ataca;
        this.defiende = defiende;
        this.feudo = feudo;
        switch (operacion) {
            case "CONQUISTAR":
                this.accion = new Acciones();
                accion.setOperacion("CONQUISTAR");
                break;
            case "SAQUEAR":
                this.accion = new Acciones();
                break;
            case "ATACAR":
                this.accion = new AccionSaqueo();
                accion.setOperacion("ATACAR");
                break;
            case "ARRASAR":
                this.accion = new AccionArrasar();
                break;
            case "ASEDIAR":
                //No implementado
                this.accion = null;
                break;
            case "ASALTAR":
                //No implementado
                this.accion = new Acciones();
                accion.setOperacion("ASALTAR");
                break;               
        }

        //Mes....
        this.mes = mes;

        //A ver como tratamos esto...
        this.reporte = new Reporte();
        this.culturaagresor = culturaagresor;
    }

    //Metodos...
    /*
     Esta clase desarrolla las contiendas: ataques, conquistas, saqueos,  arrases, asedios y asaltos de feudos con todas sus casuísticas y opciones. Requiere de un ReporteConquista, ReporteSaqueo o ReporteArrase que son las que definen las funciones particulares de cada opción.

     Conquistas, saqueos,  arrases:
    
     * Feudo libre-> CombateconCampesinos
     *Feudo con propietario:
     --Tiene tropas
     ----Tiene edificio:
     ----* combate con las tropas.
     ----**Si gana el defensor, se produce el ataque y se repele la ofensiva.
     ----**Si pierde no salen del edificio.
     ----**** Si es conquista--> No se puede conquistar, los campesinos se refugian.
     ----**** Si es saqueo-----> Si el edificio es castillo o más no se puede saquear.
     ----**** En caso contrario-> OperacionSinTropas
     ---- No tiene edificio:
     ----* Combate con las tropas.
     ----* Si los defensores huyen-> OperaciónSinTropas
     --No tiene tropas:
     --*OperaciónsinTropas


     _______________________________________________________________________________
     OperacionSinTropas:
     * Se ejecuta la misión de la contienda contra el señor de las tierras (Si accion es ARRASAR y SAQUEAR)
     * Se actualiza la capacidad de ataque de las unidades militares. (Si accion es ARRASAR y SAQUEAR)
     * Si todavía hay capacidad de ataque se ejecuta CombateConCampesinos.(Si accion es ARRASAR y SAQUEAR)

     _______________________________________________________________________________
     CombateConCampesinos:
     *Se comprueba si la acción molesta a los campesinos.
     * En caso afirmativo se obtiene el grupo de campesinos defensor.
     * Se calcula el combate.
     * Con victoria simple del atacante se conquista y con retirada para saquear y arrasar.
     * Si se produce retirada de los campesinos no se ejecuta el combate. Las tropas actúan.
     * Si no molesta a los campesinos las tropas actúan.
    
     */
    public Reporte reporte() {
        pCombateK p = pCombateK.ALDEA;

        switch (accion.getOperacion()) {
            case "ATACAR":
                //Ataque
                System.out.println("Atacar");
                //Comprobamos que los enemigos tienen tropas aniquilables
                if(conTropasCombate(defiende))
                {
                    p = p.enFeudo(feudo);
                    accion.atacar(ataca, defiende, p);
                }else
                {
                    reporte.setMensaje1("El grupo enemigo no tiene tropas que ofrezcan resistencia");
                }
                break;

            case "ASALTAR":
                System.out.println("Asaltar");
                //Sin implementar
                break;

            case "ASEDIAR":
                System.out.println("Asediar");
                //Sin implementar
                //Boceto
                //Solo se podrá asediar en feudos con propietario y edificio...
                p = p.enFeudo(feudo);
                accion.atacar(ataca, defiende, p);
                if (accion.isMuevedefensor() || accion.isHuyedefensor() || accion.isAniquiladefensor()) {
                    //Los defensores ganan sobradamente a las tropas que quieren realizar el asedio.
                    //Salen y las machacan y se acabo el asedio...
                    //Como para asediar hay que hacercarse al edicio, se puede hacer una pCombateK especifico para aseedios.
                } else {
                   //Si los defensores no ganan sobradamente, hay que comprobar que el atacante tiene maquinaria de ASALTO (o de asedio):
                   //Con una función conTropasAsedio() como la que ya hay con TropasCombate().
                   //Si las tiene pueden poner en asedio el feudo.
                }
                break;
            //CONQUISTAR, ARRASAR y SAQUEAR...
            default:
                if (!feudo.isConpropietario()) {
                    //Feudo libre-> CombateconCampesinos;
                    //Opciones disponibles. En caso de atacar, arrasar o saquear
                    //Se debería poder arrasar un feudo libre. ¿Por qué no? ¿Y si es ciudad?
                    accion.combateCampesinos(ataca, feudo, culturaagresor);
                    

                } else {
                    //Feudo con propietario...
                    if (conTropasCombate(defiende)) {
                        //Hay tropas defensivas (con capacidad de combatir)...
                        TEdificio edificio = feudo.getEdificio();
                        if (!edificio.equals(TEdificio.NADA)) {
                            //Hay edificio
                            p = p.enFeudo(feudo);
                            accion.atacar(ataca, defiende, p);
                            
                            //Comprobamos si las unidades guarnecidas salen a luchar
                            if (accion.isMuevedefensor() || accion.isHuyedefensor() || accion.isAniquiladefensor()) {
                                //Las unidades guarnecidas no salen a luchar/defender
                                if (accion.getOperacion().equals("CONQUISTAR")) {
                                    accion.setMensaje1("Las cobardes tropas enemigas se han refugiado en el edificio junto con los campesinos");
                                    return reporte;
                                }
                                //Si el edificio es castillo, fortaleza o ciudad no se puede saquear
                                //Falta añadir la condición de ciudad.
                                if ((accion.getOperacion().equals("SAQUEAR")) && ((edificio == TEdificio.CASTILLO) || (edificio == TEdificio.FORTALEZA))) {
                                    accion.setMensaje1("Las cobardes tropas enemigas se han refugiado en el edificio junto con los campesinos y sus propiedades");
                                    return reporte;
                                }                               
                                //Enfrentamiento con los campesinos. Arrasar o saquear en feudo con TORRE
                                accion.setMensaje2("mientras las cobardes tropas enemigas se han refugiado en el edificio");
                                accion.operacionSinTropas(ataca, feudo, culturaagresor);

                            } else {
                                //Las unidades guarnecidas salen a luchar, pues repelen el ataque
                                accion.escribeBajas(reporte);
                                accion.setExito(true);
                                accion.setMensaje2("Las tropas enemigas han impedido que cumplamos nuestra misión");
                            }
                        } else {
                            //No hay edificio:
                            //Feudo con propietario y con tropas defensivas. 
//¿Que pasa si hay tropas, pero no defensivas. Carretas, torres de asalto....?
//Pues si es conquistar o arrasar las ignoran y si es saqueo las capturan.
                            accion.setExito(true);
                            p = p.enFeudo(feudo);
                            accion.atacar(ataca, defiende, p);
                            accion.escribeBajas(reporte);
                            //Comprobamos si las tropas agresoras continúan
                            if (accion.isMuevedefensor() || accion.isHuyedefensor() || accion.isAniquiladefensor()) {
                                    accion.operacionSinTropas(ataca, feudo, culturaagresor);
                            } else {
                                    //Las unidades repelen a los enemigos
                                    accion.setMensaje2("Las tropas enemigas han impedido que cumplamos nuestra orden");
                                    return reporte;
                            }
                       }              
                    } else {System.out.println("No hay tropas");
                        //No hay tropas
                        //Feudo con propietario sin tropas.
                        //Igual que antes. Las tropas que no hay son de combate...

                        accion.operacionSinTropas(ataca, feudo, culturaagresor);
                        //¿Sublebar feudo?   
                    }
                }
                break;
        }
        //Terminar de escribir el reporte con los datos de accion..
        accion.completaReporte(reporte);
        return reporte;
    }

    /**
     * Comprueba si el grupo de tropas grupo tiene unidades DE COMBATE para defender.
     *
     * @param grupo
     * @return
     */
    public boolean conTropasCombate(GrupoTropas grupo) {
        pCombateK p = pCombateK.LLANO;
        Map<TTropas, TropasK> unidad = grupo.getUnidad();
        for (Map.Entry<TTropas, TropasK> elemento : unidad.entrySet()) {
            TTropas tipotropa = elemento.getKey();
            TropasK u = elemento.getValue();
            
            double poder= grupo.getDefensaD(tipotropa, p)*grupo.getCantidadTipoTropa(tipotropa);

            if (poder > 0.0) {
                return true;
            }
        }
        return false;
    }

    //Mejor en acciones ¿no?
    //Pone en el reporte las bajas que se producen como resultado de los combates.
    //Lo dicho con las aniquilaciones.
    /*
     public void escribeBajas(Reporte reporte) {
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
        }
     }*/
     
}
