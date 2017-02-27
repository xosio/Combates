/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package combate;

import java.util.HashMap;
import java.util.Map;
import sub.TTropas;

/**
 *
 * @author Rosita
 */


/*
*  Para que se pueda producir un asalto tengo que tener el portón abierto,
*  llevar torres de asalto, escalas o la muralla deteriorada.
*/
public class Asalto extends Acciones
{  
    
    private final int nmaxplomosTasalto=5;
    private final int capacidadescalas=50;
    private final int capacidadtasalto=200;
    private final String[] prioridad={"LEVAS","SOLDADOS","LANCEROS","BALLESTEROS","ARQUEROS"};
   //Constructor
    public Asalto(){
        operacion="ASALTAR";
    }
    /*
    * 
    */
    @Override
    public void asaltoEscalasTorres(GrupoTropas ataca, GrupoTropas defiende, FeudoK feudo)
    {
        pAsaltoK pasalto=pAsaltoK.valueOf(feudo.getTEdificio().toString());
        GrupoTropas grupotasalto=new GrupoTropas(); //Para las unidades que suben por las torres
        Map<TTropas, Double> poderTasalto =  new HashMap();
        GrupoTropas grupoescalas=new GrupoTropas();// PAra las unidades que suben por las escalas
        Map<TTropas, Double> poderEscalas =  new HashMap();
        GrupoTropas grupoadistancia;//Para las unidades que disparan desde abajo
        Map<TTropas, Double> poderAdistancia;
        
        int conservacion=feudo.getTEdificio().getConservacion();
        Map<TTropas, Double> poderGuarnicion =  defiende.getPoderAsalto("GUARNICION", pasalto, conservacion);               
                                        
        //Empezamos con las torres de asalto
        if(ataca.tiene("TASALTO")){
            int ntasalto=ataca.getCantidadTipoTropa(TTropas.TASALTO);
            
            //Enfrentamos las catapultas defensivas a las torres.
            if(feudo.getTEdificio().getCdefensivas()>0){
               int ncatdefensivas=feudo.getTEdificio().getCdefensivas();               
               ntasalto=catapultasDefATasalto(ncatdefensivas, ntasalto);
               //Actualizamos el número de torres de asalto que quedan
               ataca.getTK("TASALTO").setcantidad(ntasalto);
            }
            //Obtenemos la cantidad de tropas que suben
            grupotasalto=formacionEscalasyTasalto(ataca,true);
            
            //Enfrentamos las torres a los plomos
            if(feudo.getTEdificio().getPlomos()>0){
                //Obtenemos la cantidad de torres rotas y las bajas producidas
                plomosATasalto(feudo.getTEdificio().getPlomos(),ntasalto, grupotasalto);                        
                }
                //Obtenemos el poder de las tropas que suben por las torres
                poderTasalto=grupotasalto.getPoderAsalto("TASALTO", pasalto, conservacion);
            }
      
            //Si quedan tropas por subir y hay escalas.
            if((ataca.tiene("ESCALAS"))&&(ataca.hayUnidadesApie())){
                grupoescalas=formacionEscalasyTasalto(ataca,false);
                //Obtenemos el poder de las tropas que suben por las escalas
                poderEscalas=grupoescalas.getPoderAsalto("ESCALAS", pasalto, conservacion);
            }
       
            //Obtenemos las unidades a distancia que disparan desde abajo
            grupoadistancia=formacionAdistancia(ataca);
            poderAdistancia=grupoadistancia.getPoderAsalto("ADISTANCIA", pasalto, conservacion);
                        
            //Ejecutamos el ataque con los poderes pertinentes.
            double poderAt=sumaMap(poderTasalto)+sumaMap(poderEscalas)+
                    sumaMap(poderAdistancia);
            double defensaDe=defiende.defensaGuarnicion(pasalto, conservacion);
  
            //Obtenemos la condición de victoria para el combate
            double auxA=condicionVictoriaAsalto(poderAt,defensaDe);
            double auxD=1.0-auxA;
                
            //Obtenemos la reducción de bajas para cada bando
            double rba=pasalto.getRBajasAt(victoriataca);
            double rbd=pasalto.getRBajasGuar(victoriadefensor);
                
            //Calculamos las bajas...
            //En primer lugar escribimos en acciones las escalas rotas.
            destruyeEscalas(ataca.getCantidadTipoTropa(TTropas.ESCALAS));
            
            if(aniquilaataca){
                aniquila(grupotasalto, true);
                aniquila(grupoescalas, true);
                aniquila(grupoadistancia, true);
            }
            else{
                calculaBajas(poderTasalto, rba, auxD, pasalto, grupotasalto, "TASALTO", conservacion);            
                calculaBajas(poderEscalas, rba, auxD, pasalto, grupoescalas, "ESCALAS", conservacion); 
                calculaBajas(poderAdistancia, rba, auxD, pasalto, grupoadistancia, "ADISTANCIA", conservacion);   
            }
            if(aniquiladefensor)
            {
                aniquila(defiende, false);
            }
            else{
                calculaBajas(poderGuarnicion, rbd, auxA, pasalto, defiende, "GUARNICION", conservacion);
            } 
                        
    }
    /*
    * Dado un grupo de tropas devuelve otro formado solo por las unidades a distancia
    */
    GrupoTropas formacionAdistancia(GrupoTropas grupo){
        Map<TTropas, TropasK> grupoat = new HashMap();
        GrupoTropas gasalto=new GrupoTropas(grupoat,false,false);
        
        for (Map.Entry<TTropas, TropasK> elemento : grupo.getUnidad().entrySet()) 
        {
            TTropas tr=elemento.getKey();
            if (tr.isAdistancia()){
                grupoat.put(tr,elemento.getValue().copia());
            }
        }         
        return gasalto;
    }
    //Obtenemos la cantidad de escalas que se pierden, según la condición de victoria       
    public void destruyeEscalas(int escalas)
    {    
        int escalasrotas=0;
        if((victoriataca)&&(portonabierto)){
            //Se rompen un 10% de las escalas.
            escalasrotas=redondea(0.1*escalas);
        }
        else{
            if((huyeataca)||(mueveataca)||(aniquilaataca)){
                escalasrotas=escalas;//Se rompen todas
            }
            else{
                //Se rompen entre un 30 y un 70 %
                escalasrotas=redondea(0.3*escalas+0.4*escalas*Math.random());
            }
        }
        ponBajas(getBajasA(), TTropas.ESCALAS , escalasrotas);
    }
    /*
    * Función que calcula las condiciones de victoria para cualquier tipo de asalto
    */
    public double condicionVictoriaAsalto(double ataque, double defensa){
        double aux=ataque/(ataque+defensa);
        
        if(ataque>defensa){
            //Gana el atacante
            victoriataca=true;           
            //Como las tropas guarnecidas no pueden huir subimos la cota de aniquilación
            if(aux>=0.6){
                if(aux>=0.9){
                    //Aniquilación de los defensores
                    aniquiladefensor=true;              
                }
                else{
                    //Los atacantes cosniguen abrir el portón
                    portonabierto=true;
                }                 
            }                      
        }
        else{
            //Gana el grupo que defiende, en caso de empate le damos la ventaja al defensor
            victoriadefensor=true;
            if(aux<=0.02){
                //Aniquilación del atacante
                aniquilaataca=true;   
            }
            else{
                if(aux<=0.2){
                    //Retirada al principal o al personaje
                    huyeataca=true;
                }
                else{
                    if(aux<=0.35){
                        //Se mueve a posiciones mas seguras.
                        mueveataca=true;
                    }
                }
            }
        }
        return aux;
    }
    /*
    *  Función que calcula el GrupoTropas que sube por las escalas o torres de asalto
    */
    public GrupoTropas formacionEscalasyTasalto(GrupoTropas ataca, boolean torres)
    {
        Map<TTropas, TropasK> grupoat = new HashMap();
        GrupoTropas gasalto=new GrupoTropas(grupoat,false,false);
        int nmax;
        if(torres){
            nmax=ataca.getCantidadTipoTropa(TTropas.TASALTO)*capacidadtasalto;
        }
        else{
            nmax=ataca.getCantidadTipoTropa(TTropas.ESCALAS)*capacidadescalas;             
        }
       //Formamos el grupo de tropas que sube
        for(int i=0;i<prioridad.length;i++){
            if(nmax>0){
                 if(ataca.tiene(prioridad[i])){
                     TropasK auxt=ataca.getTK(prioridad[i]);
                     int cantidad=auxt.getcantidad();
                     if(cantidad>nmax){
                            //Creamos la unidad con nmax                      
                            auxt.setcantidad(nmax);
                            grupoat.put(ataca.getTT(prioridad[i]),auxt.copia());
                            //Quitamos del grupo inicial las que suben
                            ataca.getTK(prioridad[i]).setcantidad(cantidad-nmax);
                            nmax=0;
                         }                
                     else{
                         //Creamos la unidad con cantidad
                         grupoat.put(ataca.getTT(prioridad[i]),auxt.copia());
                         ataca.getTK(prioridad[i]).setcantidad(0);//Mejor borrarlo del Map?
                         nmax=nmax-cantidad;
                     }        
                 }
             }
        }
        return gasalto;
    }
    //
    /*
    *  Función que desarrolla el disparo de las catapultas defensivas a las
    *  torres de asalto en su aproximación al edificio.
    *  Devuelve el número de torres de asalto que quedan.
    */
    public int catapultasDefATasalto(int ncatapultas, int ntasalto)
    {
        double bajas=(0.1+Math.random()*0.3)*ncatapultas;
        int tasaltorotas=0;
            
        if(bajas>=ntasalto){
            tasaltorotas=ntasalto;
        }
        else{
            tasaltorotas=redondea(bajas);
        }
        
        ponBajas(getBajasA(), TTropas.TASALTO , tasaltorotas);    
        return  (ntasalto-tasaltorotas);
    }
    /*
    *  Función que calcula el número de torres que rompen los plomos
    *  devuelve los plomos usados
    */
    public void plomosATasalto(int plomos, int ntasalto, GrupoTropas grupo)
    {
        int nplomos=Math.min(plomos,nmaxplomosTasalto*ntasalto);
        double bajas=nplomos*(0.05+0.1*Math.random()); 
        int tasaltorotas=redondea(bajas);

        ponBajas(getBajasA(), TTropas.TASALTO , tasaltorotas); 
        
        //Obtenemos ahora el daño producido a las tropas
        bajas=capacidadtasalto*(nplomos*(0.05+0.05*Math.random())+0.3*Math.random()*tasaltorotas);
        int nbajas=redondea(bajas);
        
        double porcentaje=(double)(nbajas)/(double)(grupo.getCantidadTropas());
        
        for (Map.Entry<TTropas, TropasK> elemento : grupo.getUnidad().entrySet()) {
            TTropas tr = elemento.getKey();
            TropasK tk = elemento.getValue();
            int cantidad=tk.getcantidad();
            int mueren=redondea(cantidad*porcentaje);
            
            //Actualizamos las unidades
            tk.setcantidad(cantidad-mueren);
            //Añadimos las bajas a la lista
            ponBajas(getBajasA(), tr , mueren);
        }
    }
    
        
    //Función que dada un GrupoTropas y los plomos del enemigo devuelve el GrupoTropas con 
    //las unidades que suben por las torres en el asedio, descontando las bajas que producen los plomos
   /* 
    public void formacionTorres(ReporteAsalto reporte, GrupoTropas ataca, GrupoTropas torres, int nplomos)
    {
        double pbajas=0.0;

        //En primer lugar obtenemos los parámetros del plomo fundido
        if(nplomos>0)
        {
             //Obtenemos el número máximo de plomos que podemos usar.
             int nmax=Math.min(nplomos,plomosporTorre*ataca.getNtorres());
               
             //Obtenemos el porcentaje de daño causado a las torres.
             reporte.setdanoTorres(redondea(Math.random()*20*ataca.getNtorres()+Math.random()*10*nmax));
                
             //Obtenemos el porcentaje de daño causado a las tropas.
             pbajas=(0.05+Math.random()*0.1)*nmax/ataca.getNtorres();
             //Reportamos los plomos gastados
             reporte.setplomosGastados(nmax);
         }
               
         //Obtenemos la composición y cantidad de las unidades que van en las torres.
         int nmax=tropasenTorre*ataca.getNtorres();//Número máximo de unidades que suben por las torres.
            
         for(int i=0;i<iterador.length;i++)
         {
             if(nmax>0)
             {
                    //Obtenemos el número de unidades que suben a la torre.
                    int aux=Math.min(nmax, ataca.get(iterador[i]).getcantidad());
                    //Quitamos del GrupoTropas las unidades que suben a la torre.
                    ataca.get(iterador[i]).setbajas(aux);
                    //Calculamos el número de bajas que se producen por los plomos
                    int bajas=redondea(aux*pbajas);
                    //Actualizamos las bajas en el reporte
                    reporte.setbajasatacante(bajas,iterador[i]);
                    //Actualizamos el contador
                    nmax=nmax-aux;
                    //Obtenemos las unidades que quedan
                    aux=aux-bajas;
                    //Metemos en el GrupoTropas las unidades que entran definitivamente
                    torres.set(iterador[i],aux,ataca.get(iterador[i]).getmoral(),ataca.get(iterador[i]).getpericia());
             }
         }
    }
    private int tropasenTorre=200;
    private int plomosporTorre=5;
    private int [] iterador={5,3,0,4};
    
    
    
    
    // La variable estado es del estado de conservación de la muralla, pues se supone
    // que si hay asalto es porque el portón está roto o la muralla deteriorada.
    public void asaltar(ReporteAsalto reporte, GrupoTropas ataca, GrupoTropas guarnecido, Edificio edificio, pCombate p)
    {
        double estado=edificio.getEstado()/100.0;
        int ntropas=reporte.getnTropas();
        GrupoTropas torres=new GrupoTropas();
        
        //Para guardar el poder de las tropas asaltantes.
        double [] poderA=new double[ntropas+1];
        double [] poderTorres=new double[ntropas+1];
        //Para guardar la defensa de las tropas asaltantes
        double [] defensaA=new double[ntropas];
        double [] defensaTorres=new double[ntropas+1];
        
        //Obtenemos el poder y defensa de las unidades guarnecidas
        double [] poderG=guarnecido.getPoderGuarnicion(estado,p);
        double [] defensaG=guarnecido.getDefensaGuarnicion(estado,p);       
        
/************************* Combate con las torres ***************************************************/
/*        
//Comprobamos si el atacante posee torres de asedio 
        if(ataca.isTorres())
        {
            //Obtenemos la composición de las unidades que suben por las torres
            formacionTorres(reporte,ataca,torres,edificio.getNplomos());
            //Obtenemos los poderes de ataque y defensa con las bonificaciones de las murallas
            poderTorres=torres.getPoderGuarnicion(estado,p);
            defensaTorres=torres.getDefensaGuarnicion(estado,p);
        }
/**************************** Combate en muralla deteriorada y portón***********************************************/
/*        
//Comprobamos si entran también tropas por la puerta o la muralla y calculamos sus poderes
        if((edificio.getPortonAbierto())||(edificio.getAsaltable()))
        {
            //Obtenemos el poder y defensa de las tropas que entran por el portón o la murralla
            poderA=ataca.getPoderAsalto(p);
            defensaA=ataca.getDefensaAsalto(p);
        }
            
/*********************** Continuamos con el desarrollo del combate ******************/        
/*        
//Juntamos los poderes de las tropas que van por las torres con las demás
        poderA[ntropas]=poderA[ntropas]+poderTorres[ntropas];
        //Obtenemos la condición de victoria
        double auxA=poderA[ntropas]/(poderA[ntropas]+poderG[ntropas]);
        double auxD=poderG[ntropas]/(poderA[ntropas]+poderG[ntropas]);
        
        
        //Metemos las condiciones de victoria en el reporte
        //Enfrentamos el poder de ataque de los atacantes con el poder defensivo de las unidades guarnecidas.
        if(poderA[ntropas]>defensaG[ntropas])
        {
            //Gana el atacante
            reporte.setvictoriaatacante(true);
            
            //Como las tropas guarnecidas no pueden huir subimos la cota de aniquilación
            if(auxD<=0.1)
            {
                //Aniquilación de D
                reporte.setdefensoraniquilado(true);
                //Calculamos las bajas
                for(int i=0;i<ntropas;i++)
                {
                    if(poderA[i]>0)
                    {
                        reporte.setbajasatacante(redondea(0.2*auxD*poderA[i]/defensaA[i]),i);
                    }
                    if(poderTorres[i]>0)
                    {
                        reporte.setbajasatacante(redondea(0.2*auxD*poderTorres[i]/defensaTorres[i]),i);
                    }        
                    reporte.setbajasdefensor(guarnecido.get(i).getcantidad(),i);                 
                } 
                return;
            }           
        }
        else
        {
            //Gana el grupo que defiende, en caso de empoate le damos la ventaja al defensor
            reporte.setvictoriadefensor(true);
            if(auxA<=0.02)
            {
                //Aniquilación de A
                reporte.setatacanteaniquilado(true);
                //Calculamos las bajas
                for(int i=0;i<ntropas;i++)
                {
                    if(poderA[i]>0)
                    {
                        reporte.setbajasatacante(ataca.get(i).getcantidad(),i);
                    }
                    if(poderTorres[i]>0)
                    {
                        reporte.setbajasatacante(ataca.get(i).getcantidad(),i);
                    }        
                    if(poderG[i]>0)
                    {
                        reporte.setbajasdefensor(redondea(0.15*auxA*poderG[i]/defensaG[i]),i);
                    }                 
                } 
                return;
            }
            else
            {
                if(auxA<=0.2)
                {
                    //Retirada al principal o al personaje
                    reporte.sethuyeatacante(true);
                }
                else
                {
                    if(auxA<=0.35)
                    {
                        //Se mueve a posiciones mas seguras.
                        reporte.setmoveratacante(true);
                    }
                }
            }
        }
        
        //Obtenemos la bonificaciones por victoria
        double rba=0.0;//Reducción de bajas del atacante.
        double rbd=0.0;//Reducción de bajas del defensor.
        
        //En caso de empate técnico repartimos las bajas
        if((poderA[ntropas]>=0.47)&&(poderA[ntropas]<=0.53))
        {
            rba=0.5;
            rbd=0.5;
        }
        else
        {      
            if(reporte.isVictoriaatacante())
            {
                rba=0.3;
                rbd=0.7;
            }
            else
            {
                rba=0.7;
                rbd=0.3;
            }
        }

        //Calculamos las bajas
        for(int i=0;i<ntropas;i++)
        {
            if(poderA[i]>0)
            {
                reporte.setbajasatacante(redondea(0.5*auxD*rba*poderA[i]/defensaA[i]),i);
            }
            if(poderTorres[i]>0)
            {
                reporte.setbajasatacante(redondea(0.5*auxD*rba*poderTorres[i]/defensaTorres[i]),i);
            }
            if(poderG[i]>0)
            {
                reporte.setbajasdefensor(redondea(0.5*auxA*rbd*poderG[i]/defensaG[i]),i);
            }
        } 
    }
    

    
    //Dado un double lo redondea.   
    public int redondea(double num)
    {
        int sol=(int)(num);
        if(num-sol<0.5)
        {
              return sol;
        }
        return sol+1;
    }
   //Función que nos da el resultado de lanzar los arietes contra un edificio
    
    //Función que nos da el resultado de disparar las catapultas contra un edificio 
    /* En esta función falta:
     * Actualizar las bajas en las catapultas.
     * Aumentar la pericia de las catapultas tras los disparos.
     * Actualizar el estado del edificio.
     * Hacerlo asaltable en su caso.
     * Actualizar las bajas en las catapultas defensivas.
     * Enviar respectivos mensajes de lo ocurrido.
     * La función se podría modificar para que devolviese el entero del deterioro del edificio
     * Y luego actualizar los demás parámetros de la clase tropas y edificio para actualizar lo demás.
     * También se podría hacer una clase reporte si te es más cómodo, como tú veas.
     
    void disparaCatapultas(TropasK catapultas, Edificio edificio)
    {
        //Primero actúan las catapultas defensivas
        if(edificio.hayCatapultas())
        {
            int bajas=redondea((0.1+Math.random()*0.15)*edificio.getNcatapultas());
            catapultas.setbajas(bajas);
        }
        //Obtenemos la cantidad de impactos sobre el edificio
        double impacto=2*catapultas.getPoderUnidades()*edificio.resistencia();
        
        //Actualizamos el estado del edificio.
        if(impacto>20)
        {
            edificio.rompeEdificio(20);
            //Actualizamos las bajas en las catapultas defensivas.
            if(edificio.hayCatapultas())
            {
                edificio.setBajasCatapultas(20);
            }
        }
        else
        {
            int aux=redondea(impacto);
            edificio.rompeEdificio(aux);
            //Actualizamos las bajas en las catapultas defensivas.
            if(edificio.hayCatapultas())
            {
                edificio.setBajasCatapultas(aux);
            }
        }
        //Comprobamos si el edificio es ahora asaltable.
        if(edificio.getEstado()<50)
        {
            edificio.setAsaltable(true);
        }  
    }
    //Eta función desarrolla el lanzamiento de los arietes a un edificio.
    //El objeto TropasK se puede sustituir por un entero con el número de arietes.
    public void lanzarArietes(TropasK arietes, Edificio edificio)
    {
        if(edificio.getTipoEdificio()==0)
        {
            edificio.setPortonAbierto(true);
            //Mandamos una baja a los arietes
            arietes.setbajas(1);
            return;
        }
        //Obtenemos el número de arietes que se lanzan
        int narietes=Math.min(edificio.nmaxArietes(),arietes.getcantidad());
        int piedras=edificio.getNpiedras();
        
        if(piedras>3*narietes)
        {
            //Son repelidos todos los arietes.
            arietes.setbajas(narietes);
            edificio.setBajasPiedras(narietes);
            return;
        }
        
        //Liquidamos las piedras, pues se usan todas.
        edificio.setBajasPiedras(narietes);
        //Obtenemos los arietes destruidos por bloques de tres piedras.
        narietes=narietes-(int)(piedras/3.0);
        //Obtenemos las piedras que quedan sueltas.
        piedras=piedras%3;
        //comprobamos si las piedras restantes son capaces de romper otro ariete.
        if(piedras*Math.random()>Math.random())
        {
            narietes--;
        }
        //Comprobamos si se habre el portón
        if(narietes>=edificio.arietesNecesarios())
        {
            edificio.setPortonAbierto(true);
            arietes.setbajas(edificio.arietesNecesarios());
        }   
    }
*/
    //Función que completa el reporte con los datos necesarios
    public void completaReporte(Reporte reporte)
    {
        reporte.setVictoriaatacante(victoriataca);
        reporte.setAtacanteaniquilado(aniquilaataca);
        reporte.setDefensoraniquilado(aniquiladefensor);
        reporte.setHuyeatacante(huyeataca);
        reporte.setHuyedefensor(huyedefensor);
        reporte.setMoveratacante(mueveataca);
        reporte.setMoverdefensor(muevedefensor);
        reporte.setVictoriasobrecampis(victoriasobrecampis);
        
        reporte.setExito(exito);
        reporte.setMensaje1(mensaje1);
        reporte.setMensaje2(mensaje2);
        reporte.setMensaje3(mensaje3); 
        if(mensaje3!=null)
        {
            escribeBajas(reporte);
        }
    }
}
