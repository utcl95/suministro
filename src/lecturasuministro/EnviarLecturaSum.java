
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lecturasuministro;

import javax.microedition.lcdui.*;

/**
 * @author Jaqui
 */
public class EnviarLecturaSum extends Form implements CommandListener, ItemCommandListener{

    private static final Command CMD_PRESS2 = new Command ("Press", Command.ITEM, 1);

    private IngresoSum ss;
    private TextField consumo;
    private String suministro;
    private TextField obs;
    private int vobs;
    private int lactual;


    SuministroRMS sRMS = new SuministroRMS("SUMINISTROS");



    EnviarLecturaSum(String lect, IngresoSum ss) {
        super("Lectura de Consumo");
        this.ss = ss;
        buscarSuministro(lect);
    }

    public void commandAction (Command c, Item item) {
       
        if(obs.getString().equals("")){
            vobs = 0;
        
        }else
            vobs = Integer.parseInt(obs.getString());
        lactual = Integer.parseInt(consumo.getString());
        int lanterior = ss.lAnterior();
        int promedio = ss.lPromedio();
        int cons_act = lactual - lanterior;
        
        if (c == CMD_PRESS2) {
            switch(vobs){
                case 0:
                    if(lactual > lanterior){
                      if (ingresarConsumo(suministro, consumo.getString(), obs.getString())){}
                    }else{
                      ss.mostrarMensaje("a");
                    }
                    break;
                case 1:
                    if(lactual > lanterior){
                      if (ingresarConsumo(suministro, consumo.getString(), obs.getString())){}
                    }else{
                      ss.mostrarMensaje("a");
                    }
                    break;
                case 2:
                    if(lanterior != 0){
                      if (ingresarConsumo(suministro, consumo.getString(), obs.getString())){}
                    }else{
                      ss.mostrarMensaje("b");
                    }
                    break;
                case 3:
                    if( (cons_act <= 10) && (promedio > 4 * cons_act) && (promedio > 20) ){
                      ss.mostrarMensaje("c");
                    }else{
                      if (ingresarConsumo(suministro, consumo.getString(), obs.getString())){}
                    }
                    break;
                case 4:
                    if( (cons_act > 10) && (cons_act <= 30) && ( promedio >= 2.75 * cons_act || promedio <= 0.4 * cons_act ) ){
                      ss.mostrarMensaje("c");
                    }else{
                      if (ingresarConsumo(suministro, consumo.getString(), obs.getString())){}
                    }
                    break;
                case 5:
                    if( (cons_act > 30) && (cons_act <= 50) && ( promedio >= 2.30 * cons_act || promedio <= 0.5 * cons_act ) ){
                      ss.mostrarMensaje("c");
                    }else{
                      if (ingresarConsumo(suministro, consumo.getString(), obs.getString())){}
                    }
                    break;
                case 6:
                    if( (cons_act > 50) && (cons_act <= 70) && ( promedio >= 2 * cons_act || promedio <= 0.6 * cons_act ) ){
                      ss.mostrarMensaje("c");
                    }else{
                      if (ingresarConsumo(suministro, consumo.getString(), obs.getString())){}
                    }
                    break;
                case 7:
                    if( (cons_act > 70) && (cons_act <= 100) && ( promedio >= 1.8 * cons_act || promedio <= 0.6 * cons_act ) ){
                      ss.mostrarMensaje("c");
                    }else{
                      if (ingresarConsumo(suministro, consumo.getString(), obs.getString())){}
                    }
                    break;
                case 8:
                    if( (cons_act > 100) && (cons_act <= 150) && ( promedio >= 1.7 * cons_act || promedio <= 0.6 * cons_act ) ){
                      ss.mostrarMensaje("c");
                    }else{
                      if (ingresarConsumo(suministro, consumo.getString(), obs.getString())){}
                    }
                    break;
                case 9:
                    if( (cons_act > 150) && (cons_act <= 200) && ( promedio >= 1.5 * cons_act || promedio <= 0.6 * cons_act ) ){
                      ss.mostrarMensaje("c");
                    }else{
                      if (ingresarConsumo(suministro, consumo.getString(), obs.getString())){}
                    }
                    break;
                case 10:
                    if( (cons_act > 200) && (cons_act <= 300) && ( promedio >= 1.5 * cons_act || promedio <= 0.65 * cons_act ) ){
                      ss.mostrarMensaje("c");
                    }else{
                      if (ingresarConsumo(suministro, consumo.getString(), obs.getString())){}
                    }
                    break;
                case 11:
                    if( (cons_act > 300) && (cons_act <= 400) && ( promedio >= 1.6 * cons_act || promedio <= 0.7 * cons_act ) ){
                      ss.mostrarMensaje("c");
                    }else{
                      if (ingresarConsumo(suministro, consumo.getString(), obs.getString())){}
                    }
                    break;
                case 12:
                    if( (cons_act > 400) && (cons_act <= 500) && ( promedio >= 1.5 * cons_act || promedio <= 0.7 * cons_act ) ){
                      ss.mostrarMensaje("c");
                    }else{
                      if (ingresarConsumo(suministro, consumo.getString(), obs.getString())){}
                    }
                    break;
                case 13:
                    if( (cons_act > 500) && (cons_act <= 700) && ( promedio >= 1.4 * cons_act || promedio <= 0.7 * cons_act ) ){
                      ss.mostrarMensaje("c");
                    }else{
                      if (ingresarConsumo(suministro, consumo.getString(), obs.getString())){}
                    }
                    break;
                case 14:
                    if( (cons_act > 700) && (cons_act <= 1500) && ( promedio >= 1.3 * cons_act || promedio <= 0.7 * cons_act ) ){
                      ss.mostrarMensaje("c");
                    }else{
                      if (ingresarConsumo(suministro, consumo.getString(), obs.getString())){}
                    }
                    break;
                 case 15:
                    if( (cons_act > 1500) && (cons_act <= 2500) && ( promedio >= 1.2 * cons_act || promedio <= 0.7 * cons_act ) ){
                      ss.mostrarMensaje("c");
                    }else{
                      if (ingresarConsumo(suministro, consumo.getString(), obs.getString())){}
                    }
                    break;
                 case 16:
                    if( cons_act > 2500 && ( cons_act < 1.2 * promedio || promedio <= 0.6 * cons_act ) ) {
                      ss.mostrarMensaje("c");
                    }else{
                      if (ingresarConsumo(suministro, consumo.getString(), obs.getString())){}
                    }
                    break;
            }
        }
    }

     /**
     * Buscar Suministro
     * return True o False si se encuentra el suministro
     */
    public void buscarSuministro(String msuministro) {
       suministro = msuministro;
       consumo = new TextField("Consumo   ", "", 20, TextField.NUMERIC);
       append(new TextField("Suministro", suministro, 20, TextField.UNEDITABLE));
       append(consumo);
       obs = new TextField("Obs", "", 2, TextField.NUMERIC);
       append(obs);

       StringItem item = new StringItem("", "Ingresar", Item.BUTTON);
       item.setDefaultCommand(CMD_PRESS2);
       item.setItemCommandListener(this);
       append(item);

    }

    public boolean ingresarConsumo(String msuministro, String mconsumo, String mobs) {
        int index = sRMS.searchSuministro(msuministro);
        sRMS.setSuministro(index, msuministro, mconsumo, mobs);

        sRMS.showRMS();
        ss.startApp();

        return false;
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {

    }

    public void commandAction(Command c, Displayable d) {

    }
}

