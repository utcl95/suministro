
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

    // Comandos para Adelante, Atras de informacion de Suministro.
    private static final Command CMD_BACK = new Command ("Atras", Command.ITEM, 1);
    private static final Command CMD_NEXT = new Command ("Sigte", Command.ITEM, 1);

    // Actual Elemento en pantalla (Suministro)
    private int currentItem = 1;

    private Display display;
    private TextField txt1;
    private TextField consumo;
    private Form mainForm;
    private Form mainForm2;

    private FormSuministro fs = null;
  
    // Funciones sobre Suministro:
    // addSuministro, searchSuministro, setSuministro, showSuministro
    SuministroRMS sRMS = new SuministroRMS("SUMINISTROS");

    protected void startApp () {

        display = Display.getDisplay(this);
        // Leer Suministro.

        fs = new FormSuministro("Lectura x Zona");
        fs.setCurrentSuministro(currentItem);
        fs.addCommand(CMD_BACK);
        fs.addCommand(CMD_NEXT);
        fs.setCommandListener(this);
        display.setCurrent(fs);        
                                  
    }

    public void leerSuministro() {
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
            if (ingresarConsumo(txt1.getString(), consumo.getString(), "00")){
           
            }
        } if (c == CMD_BACK) {
            doBack();
        } if(c == CMD_NEXT) {
            doNext();
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

    public boolean ingresarConsumo(String msuministro, String mconsumo, String mobs) {

        int index = sRMS.searchSuministro(msuministro);
        sRMS.setSuministro(index, msuministro, mconsumo, mobs);
        int resta = sRMS.cuentaLista();
        sRMS.showRMS();

        String text = "Consumo ingresado. "+"Le quedan "+resta+" suministros";
        Alert b = new Alert ("Action", text, null, AlertType.INFO);
        display.setCurrent (b);

        return false;
    }


    public void doBack() {
        int i = currentItem;
        if(i == 1) {
        } else {
            currentItem = currentItem - 1;
            fs.setCurrentSuministro(currentItem);

        }

    }

    public void doNext() {
        int i = currentItem;
        if(i == 1000) {
        } else {
            currentItem = currentItem + 1;
            fs.setCurrentSuministro(currentItem);
        }
    }

    
}
