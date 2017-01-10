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
                this.accion = new AccionSaqueo();
                break;
            case "ATACAR":
                this.accion = new Acciones();
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
        this.reporte = new Reporte(operacion, feudo.isConpropietario());
        this.culturaagresor = culturaagresor;
    }

    //Metodos...
    /*
     Esta clase desarrolla las contiendas: ataques, conquistas, saqueos,  arrases, asedios y asaltos de feudos con todas sus casuísticas y opciones. Requiere de un ReporteConquista, ReporteSaqueo o ReporteArrase que son las que definen las funciones particulares de cada opción.

     Conquistas:
    
     *Feudo libre-> CombateconCampesinos
     *Feudo con propietario:
     --Tiene edificio
     ---Tiene tropas de combate
     ----Calcular ataque campo abierto(atacante contra defensores+capiresistentes)
     ----Si gana el defensor+campisresistentes, se produce el ataque y se repele la ofensiva.
     ----Si pierden defensor+campisresistentes:--> No se puede conquistar, los campesinos se refugian.
     ---No tiene tropas de combate--> Conquista+captura
    
     --No tiene edificio
     ---Tiene tropas de combate
     ----Calcular ataque campo abierto(atacante contra defensores+campiresistentes)
     ----Si victoria atacante
     -----Si huye o aniquila defensor:
     ------Calcular ataque campo abierto(atacante contra defensores sin campis)
     ------Ejectuar atacaque y ejecutar accion-->Conquista
     -----Si mueve o victoria simple
     ------Ejectuar atacaque y ejecutar accion-->Conquista
     ----Si vitoria defensor:
     ------Ejectuar atacaque
     ---No tiene tropas:
     ----CombateCampesinos
    
    
    
    
     Ataques:
     Se calcula el ataque a campo abierto.
     -Si feudo no es del que defiene-->Se realiza el Ataque
     -Si feudo es propiedad del defensor:
     --Si tiene edificio 
     ---Si Tiene tropas de combate:
     ----Si gana el defensor, se produce el ataque.
     ----Si pierde el defensor se resguardan en el edificio y no hay ataque.
     ---No tiene tropas de combate:
     ----Se produce el ataque
     --No tiene edificio:
     ---Se produce el ataque
    
    
     Saqueos,  arrases:
    
     *Feudo libre-> CombateconCampesinos
     *Feudo con propietario:
     --Tiene tropas 
     ----Tiene edificio:
     ----* combate con las tropas.
     ----**Si gana el defensor, se produce el ataque y se repele la ofensiva.
     ----**Si pierde no salen del edificio.
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
                //Comprobaciones previas para permitir el ataque:
                //El atacante tiene tropas en ese feudo (cualquier tipo)
                //El defensor tiene tropas en ese feudo (cualquier tipo)

                //Realmente sería muy sencillo no permitir que se puedan atacar a tropas en feudo propio con edificios...
                //... en este caso, la opción debería ser asediar o asaltar...
                //... pero por varios motivos, no lo hacemos así. Aunque resultaría una programación mucho más sencilla...
                p = p.enFeudo(feudo);
                //Calculamos el ataque...
                accion.atacar(ataca, defiende, p);

                //Lo que sigue supone que las tropas que se defienden del ataque son las del propietario del feudo...
                if (feudo.isConpropietario()) {
                    if (!feudo.getEdificio().equals(TEdificio.NADA)) {
                        //Hay edificio...
                        if (accion.victoriadefensor) {
                            //Hay edificio y las tropas defensoras lograrían la victoria
                            reporte.setExito(true);
                            reporte.setMensaje1("Se ha realizado el ataque;");
                        } else {
                            //Hay edificio y las tropas defensoras perderían
                            if (defiende.conTropasCombate()) {
                                //Las tropas defensivas pueden defender el edificio (una sola basta)
                                reporte.setExito(false);
                                reporte.setMensaje1("Los cobardes defensores se han refugiado en su castillo");
                            } else {
                                //Hay edificio, pero no hay tropas defensivas que lo puedan defender
                                reporte.setExito(true);
                                reporte.setMensaje1("Hay edificio, pero el grupo defensor no tiene tropas capaces de defenderlo;");
                            }
                        }
                    } else {
                        //No hay edificio
                        reporte.setExito(true);
                        reporte.setMensaje1("Se ha realizado el ataque;");
                    }
                } else {
                    //Ataque normal
                    reporte.setExito(true);
                    reporte.setMensaje1("Se ha realizado el ataque;");
                }

                //Completar reporte...
                reporte.completaReporte(accion);
                //Completamos reporte con bajas y capturas... 
                if (reporte.isExito()) {
                    //Escribir las bajas
                    //Ojo. Con el uso en accion de BajasA y BajasD y la reinicialización que se hace en atacar()
                    reporte.setBajasatacanteX(accion.getBajasA());
                    reporte.setBajasdefensorX(accion.getBajasD());

                    //Escribir las capturas...
                    if (accion.victoriataca) {
                        if (accion.huyedefensor || accion.aniquiladefensor) {
                            reporte.setCapturasX(defiende.capturaTropas());
                        }
                    }

                    if (accion.victoriadefensor) {
                        if (accion.huyeataca || accion.aniquilaataca) {
                            reporte.setCapturasX(defiende.capturaTropas());
                        }
                    }

                }
                break;

            case "ASALTAR":

                //Sin implementar
                break;

            case "ASEDIAR":

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
                    //Con una función conTropasAsedio() como la que ya hay conTropasCombate().
                    //Si las tiene pueden poner en asedio el feudo.
                }
                break;

            case "CONQUSITAR":
                if (!feudo.isConpropietario()) {
                    accion.combateCampesinos(ataca, feudo, culturaagresor);
                    if (accion.campesinos > 0) {
                        accion.escribeBajas(reporte);
                        //Escribir las capturas...
                        if (accion.victoriadefensor) {
                            if (accion.huyeataca || accion.aniquilaataca) {
                                reporte.setCapturasX(ataca.capturaTropas());
                            }
                        }
                    }
                } else {
                    //Feudo con propietario
                    if (!feudo.getEdificio().equals(TEdificio.NADA)) {
                        //Feudo con propietario y con edificio
                        if (defiende.conTropasCombate()) {
                            //Feudo con propietario,  con edificio y con tropas de combate
                            p = p.enFeudo(feudo);
                            accion.atacarConCampis(ataca, defiende, p, feudo, culturaagresor);
                            if (accion.victoriadefensor) {
                                //Victoria del defensor-->se produce el ataque y se repele la ofensiva.
                                accion.escribeBajas(reporte);
                                if (accion.huyeataca || accion.aniquilaataca) {
                                    reporte.setCapturasX(ataca.capturaTropas());
                                }
                                reporte.setExito(true);
                            } else {
                                //Victoria del atacante-->No se puede conquistar, los campesinos se refugian.
                                reporte.setExito(false);
                            }
                        } else {
                            //Feudo con propietario,  con edificio pero sin tropas de combate
                            reporte.setVictoriaatacante(true);
                            reporte.setCapturasX(ataca.capturaTropas());
                        }

                    } else {
                        //Feudo con propietario y sin edificio
                        if (defiende.conTropasCombate()) {
                            //Feudo con propietario,  sin edificio y con tropas de combate
                            p = pCombateK.ALDEA;
                            accion.atacarConCampis(ataca, defiende, p, feudo, culturaagresor);
                            if (accion.victoriadefensor) {
                                //Feudo con propietario,  sin edificio y con tropas de combate
                                //Victoria del defensor-->se produce el ataque y se repele la ofensiva.
                                reporte.escribeBajas(accion.getBajasA(), accion.getBajasA());
                                if (accion.huyeataca || accion.aniquilaataca) {
                                    reporte.setCapturasX(ataca.capturaTropas());
                                }
                                reporte.setExito(true);
                            } else {
                                //Feudo con propietario,  sin edificio y con tropas de combate
                                //Victoria del atacante sobre campis y tropas en aldea
                                if (accion.huyedefensor || accion.aniquiladefensor) {
                                    //p = p.enFeudo(feudo);
                                    accion.atacar(ataca, defiende, p);
                                    reporte.setBajasX(accion);
                                    if (accion.victoriataca) {
                                        if (accion.huyedefensor || accion.aniquiladefensor) {
                                            reporte.setCapturasX(defiende.capturaTropas());
                                        }
                                    } else {
                                        //Esto no creo que se pueda dar.. pero por si acaso
                                        if (accion.huyeataca || accion.aniquilaataca) {
                                            reporte.setCapturasX(ataca.capturaTropas());
                                        }
                                    }
                                }
                            }
                            reporte.setExito(false);
                        } else {
                            //Feudo con propietario,  sin edificio y sin tropas de combate
                            accion.combateCampesinos(ataca, feudo, culturaagresor);
                            if (accion.campesinos > 0) {
                             reporte.setCampesinos(accion.campesinos);    
                                
                                if (accion.victoriadefensor) {
                                    reporte.setBajasX(accion);
                                    if (accion.huyeataca || accion.aniquilaataca) {
                                        reporte.setCapturasX(ataca.capturaTropas());
                                    }
                                }
                                if (accion.victoriataca) {
                                    if (accion.huyedefensor||accion.aniquiladefensor) {
                                        reporte.setCampesinos(0);
                                    }
                                    reporte.setCapturasX(defiende.capturaTropas());
                                }
                            } else {
                                //Los campesinos no se molestan
                                reporte.setCapturasX(defiende.capturaTropas());
                            }
                        }
                    }
                }

            //ARRASAR y SAQUEAR...
            default:
                if (!feudo.isConpropietario()) {
                    //Feudo libre-> CombateconCampesinos;
                    //Opciones disponibles. En caso de conquistar, arrasar o saquear
                    //Se debería poder arrasar un feudo libre. ¿Por qué no? ¿Y si es ciudad?
                    accion.combateCampesinos(ataca, feudo, culturaagresor);
                    //Si ha habido resistencia por parte de los campsionos escribimos las bajas:
                    if (accion.campesinos > 0) {
                        accion.escribeBajas(reporte);
                        //Escribir las capturas...
                        if (accion.victoriadefensor) {
                            if (accion.huyeataca || accion.aniquilaataca) {
                                reporte.setCapturasX(ataca.capturaTropas());
                            }
                        }
                    }
                } else {
                    //Feudo con propietario...
                    if (defiende.conTropasCombate()) {
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

                            accion.setExito(true); //Aquí aun no sabemos si hay exito en la acción: tienen que atacar a los defensores....
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
                    } else {
                        System.out.println("No hay tropas");
                        //No hay tropas
                        //Feudo con propietario sin tropas.
                        //Igual que antes. Las tropas que no hay son de combate...

                        accion.operacionSinTropas(ataca, feudo, culturaagresor);
                        //¿Sublebar feudo?   
                    }
                }
                reporte.completaReporte(accion);
                break;
        }
        //Terminar de escribir el reporte con los datos de accion..

        return reporte;
    }

    public void resumen() {
        System.out.println("/************************************/");
        System.out.println("Resumen de las condiciones de la acción:");
        System.out.println("Accion: " + accion.getOperacion());
        System.out.println("Tipo de feudo: " + feudo.getTfeudo().toString());
        System.out.println("Edificio: " + feudo.getEdificio().toString());
        if (feudo.isConpropietario()) {
            System.out.println("Las tropas que defienen (si las hay) son propietarias del feudo.");
        } else {
            System.out.println("Feudo sin propietario.");
        }
        System.out.println("/************************************/");
        System.out.println("Tropas atacantes (" + culturaagresor.toString() + "):");
        ataca.print();
        System.out.println("Tropas defensivas: (" + feudo.getTcultura().toString() + "):");
        defiende.print();
        System.out.println("/************************************/");
        System.out.println("Resultados:");
    }

}
