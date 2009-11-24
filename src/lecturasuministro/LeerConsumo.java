/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lecturasuministro;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

/**
 * @author Jaqui
 */
public class LeerConsumo extends Form {
   // private static final Command CMD_PRESS = new Command ("Press", Command.ITEM, 1);
    private static final Command CMD_PRESS2 = new Command ("Press", Command.ITEM, 1);
    //private static final Command CMD_EXIT = new Command ("Exit", Command.EXIT, 1);

    private Display display2;
    //private TextField txt1;
    private TextField consumo;
    //private Form mainForm;
    private String suministro;
    private TextField obs;

    SuministroRMS sRMS = new SuministroRMS("SUMINISTROS");

    LeerConsumo(String lect) {
        super("Lectura de Consumo");
        buscarSuministro(lect);
    }
    
    public void commandAction (Command c, Item item) {
        if (c == CMD_PRESS2) {
            if (ingresarConsumo(suministro, consumo.getString(), obs.getString())){
           
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
       append(item);
    }

    public boolean ingresarConsumo(String msuministro, String mconsumo, String mobs) {
        int index = sRMS.searchSuministro(msuministro);
        sRMS.setSuministro(index, msuministro, mconsumo, mobs);
        int resta = sRMS.cuentaLista();
        sRMS.showRMS();

        String text = "Consumo ingresado. "+"Le quedan "+resta+" suministros";
        Alert b = new Alert ("Action", text, null, AlertType.INFO);
        display2.setCurrent (b);

        return false;
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable d) {
        destroyApp (false);
    }
}
