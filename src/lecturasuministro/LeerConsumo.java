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
        
        vobs = Integer.parseInt(obs.getString());
        lactual = Integer.parseInt(consumo.getString());
        int lanterior = ss.lAnterior();
        System.out.println("jaqui"+lanterior);
        if (c == CMD_PRESS2) {
            if (vobs == 1){
                if(lactual > lanterior){
                    if (ingresarConsumo(suministro, consumo.getString(), obs.getString())){}
                }else{
                    ss.mostrarMensaje();
                }
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
