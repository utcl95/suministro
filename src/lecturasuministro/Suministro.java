
package lecturasuministro;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

/**
 *
 * @author utcl95
 */
public class Suministro extends MIDlet implements CommandListener, ItemCommandListener {
    private static final Command CMD_PRESS = new Command ("Press", Command.ITEM, 1);
    private static final Command CMD_PRESS2 = new Command ("Press", Command.ITEM, 1);
    private static final Command CMD_EXIT = new Command ("Exit", Command.EXIT, 1);
    private Display display;
    private TextField txt1;
    private TextField consumo;
    private Form mainForm;
    private Form mainForm2;
  
    // Funciones sobre Suministro:
    // addSuministro, searchSuministro, setSuministro, showSuministro
    SuministroRMS sRMS = new SuministroRMS("ELECTRO");

    protected void startApp () {
            display = Display.getDisplay(this);
       
            mainForm = new Form("Lectura Suministro");
            txt1 = new TextField("Buscar", "", 15, TextField.NUMERIC);
            mainForm.append(txt1);
            StringItem item = new StringItem("", "Buscar", Item.BUTTON);
            item.setDefaultCommand(CMD_PRESS);
            item.setItemCommandListener(this);
            mainForm.append(item);
            mainForm.addCommand(CMD_EXIT);
            mainForm.setCommandListener(this);
            display.setCurrent(mainForm);
           
    }

    public void commandAction (Command c, Item item) {
        if (c == CMD_PRESS) {
             if (buscarSuministro(txt1.getString())) {
                 
             }
        }if (c == CMD_PRESS2) {
            if (ingresarConsumo(txt1.getString(), consumo.getString())){
           
            }
        }
    }

    public void commandAction (Command c, Displayable d) {
        destroyApp (false);
        notifyDestroyed ();
    }

    /**
     * Signals the MIDlet to terminate and enter the Destroyed state.
     */
    protected void destroyApp (boolean unconditional) {
    }

    /**
     * Signals the MIDlet to stop and enter the Paused state.
     */
    protected void pauseApp () {
    }



    /**
     * Buscar Suministro
     * return True o False si se encuentra el suministro
     */
    public boolean buscarSuministro(String msuministro) {
             
        if(sRMS.searchSuministro(msuministro) == 0){

           String text = "No se encontro suministro";
           Alert a = new Alert ("Action", text, null, AlertType.INFO);
           display.setCurrent (a);
           
        }else{

           mainForm2 = new Form("Ingresar Suministro");
           consumo = new TextField("Consumo   ", "", 20, TextField.NUMERIC);
           mainForm2.append(new TextField("Suministro", txt1.getString(), 20, TextField.UNEDITABLE));
           mainForm2.append(consumo);
           StringItem item = new StringItem("", "Ingresar", Item.BUTTON);
           item.setDefaultCommand(CMD_PRESS2);
           item.setItemCommandListener(this);
           mainForm2.append(item);
           mainForm2.setCommandListener(this);
           display.setCurrent (mainForm2);

        }

        return false;
    }

    public boolean ingresarConsumo(String msuministro, String mconsumo) {

        int index = sRMS.searchSuministro(msuministro);
        sRMS.setSuministro(index, msuministro, mconsumo);
        int resta = sRMS.cuentaLista();
        sRMS.showRMS();

        String text = "Consumo ingresado. "+"Le quedan "+resta+" suministros";
        Alert b = new Alert ("Action", text, null, AlertType.INFO);
        display.setCurrent (b);

        return false;
    }

    
}
