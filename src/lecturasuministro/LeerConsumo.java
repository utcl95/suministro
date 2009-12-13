/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lecturasuministro;

import javax.microedition.lcdui.*;

/**
 * @author Jaqui
 */
public class LeerConsumo extends Form implements CommandListener, ItemCommandListener{

    private static final Command CMD_PRESS2 = new Command ("Press", Command.ITEM, 1);
    private static final Command CMD_PRESS3 = new Command ("Press", Command.ITEM, 1);
    private Suministro ss;
    private TextField consumo;
    private String suministro;
    private TextField obs;
    private int vobs;
    private int lactual;
    

    SuministroRMS sRMS = new SuministroRMS("SUMINISTROS");



    LeerConsumo(String lect, Suministro ss) {
        super("Lectura de Consumo");
        this.ss = ss;
        buscarSuministro(lect);
    }

    public void commandAction (Command c, Item item) {
        Validacion validarSuministro = new Validacion();
        
        if(obs.getString().equals(""))
            vobs = 0;
        else
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
                // Todos los casos: 3-16 son semejantes.
                default:
                    if(!validarSuministro.esValido(vobs, lactual, lanterior, cons_act, promedio) ) {
                      ss.mostrarMensaje("c");
                    }else{
                      if (ingresarConsumo(suministro, consumo.getString(), obs.getString())){}
                    }
                    break;
            }   // end case
            if(!validarSuministro.esValido(vobs, lactual, lanterior, cons_act, promedio) ) {
              ss.mostrarMensaje("c");
            }else{
              if (ingresarConsumo(suministro, consumo.getString(), obs.getString())){}
            }
        }       // end if
        if (c == CMD_PRESS3){
         if (ingresarConsumo(suministro, consumo.getString(), obs.getString())){}
        }
        validarSuministro = null;
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

       StringItem item = new StringItem("", "Validar", Item.BUTTON);
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
