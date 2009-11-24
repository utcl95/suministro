
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
    //private static final Command CMD_BACK = new Command ("Atras", Command.ITEM, 1);
    //private static final Command CMD_NEXT = new Command ("Sigte", Command.ITEM, 1);

    private static final Command CMD_PRESS3 = new Command ("Atras", Command.ITEM, 1);
    private static final Command CMD_PRESS4 = new Command ("Sigte", Command.ITEM, 1);
    private static final Command CMD_PRESS5 = new Command ("Consumo", Command.ITEM, 1);

    // Actual Elemento en pantalla (Suministro)
    private int currentItem = 1;

    private Display display;
  

    private FormSuministro fs = null;
  
    // Funciones sobre Suministro:
    // addSuministro, searchSuministro, setSuministro, showSuministro
    SuministroRMS sRMS = new SuministroRMS("SUMINISTROS");
    LeerConsumo lectura = new LeerConsumo("INGRESAR LECTURA");

    protected void startApp () {

        display = Display.getDisplay(this);
        // Leer Suministro.

        fs = new FormSuministro("Lectura x Zona");
        fs.setCurrentSuministro(currentItem);

        StringItem item = new StringItem("", "Atras", Item.BUTTON);
        item.setDefaultCommand(CMD_PRESS3);
        item.setItemCommandListener(this);
        fs.append(item);

        StringItem item1 = new StringItem("", "Siguiente", Item.BUTTON);
        item1.setDefaultCommand(CMD_PRESS4);
        item1.setItemCommandListener(this);
        fs.append(item1);

        StringItem item2 = new StringItem("", "Siguiente", Item.BUTTON);
        item2.setDefaultCommand(CMD_PRESS5);
        item2.setItemCommandListener(this);
        fs.append(item2);
        //fs.addCommand(CMD_BACK);
        //fs.addCommand(CMD_NEXT);
        fs.setCommandListener(this);
        display.setCurrent(fs);        
                                  
    }

    public void commandAction (Command c, Item item) {
        if (c == CMD_PRESS3) {
            doBack();
        }if (c == CMD_PRESS4) {
            doNext();
        }if (c == CMD_PRESS5) {
            lectura.buscarSuministro("suministro");
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
