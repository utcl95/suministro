
package lecturasuministro;


import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

import javax.microedition.pim.*;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

/**
 *
 * @author utcl95
 */
public class Suministro extends MIDlet implements CommandListener, ItemCommandListener {
    private static final Command CMD_PRESS = new Command ("Press", Command.ITEM, 1);
    private static final Command CMD_EXIT = new Command ("Exit", Command.EXIT, 1);
    private Display display;
    private TextField txt1;
    private TextField consumo;
    private Form mainForm;
    private Form mainForm2;

    protected void startApp () {
        
        display = Display.getDisplay (this);

        // Cargar Suministro.
        cargarSuministro();

        mainForm = new Form ("Suministro");
       
        txt1 = new TextField ("Buscar", "", 15, TextField.NUMERIC);
        
        mainForm.append(txt1);

        StringItem item = new StringItem ("Button ", "Button", Item.BUTTON);
        item.setDefaultCommand (CMD_PRESS);
        item.setItemCommandListener (this);
        mainForm.append (item);
        mainForm.addCommand (CMD_EXIT);
        mainForm.setCommandListener (this);
        display.setCurrent (mainForm);

        mainForm2 = new Form ("Lectura");
        consumo = new TextField("Consumo   ", "", 20, TextField.NUMERIC);
        mainForm2.append(new TextField("Suministro", txt1.getString(), 20, TextField.UNEDITABLE));
        mainForm2.append(consumo);
    }

    public void commandAction (Command c, Item item) {
        if (c == CMD_PRESS) {
             if (buscarSuministro(txt1.getString())) {
                 mainForm2.setCommandListener(this);
                 display.setCurrent (mainForm2);
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
     * Cargar suministros a leer, 1000 aprox.
     */
    public boolean cargarSuministro() {
        // Estos son los suministros que van a ser leidos.
        String m_suministros[] = {"2","4","6","8","10","12","14","16","18","20"};
        boolean retVal;
        
        RecordStore rs = null;
        int     authMode = RecordStore.AUTHMODE_ANY;
        boolean writable = true;

        try {
            rs = RecordStore.openRecordStore( "myrs", true, authMode, writable );
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
        return true;
    }

    /**
     * Buscar Suministro
     * return True o False si se encuentra el suministro
     */
    public boolean buscarSuministro(String msuministro) {
        String[] stringArray = {"1234", "2341", "2312", "4123"};
        int i=0;
        while(i<=4){
            if (stringArray[i].equals(msuministro))
                return true;
            ++i;
        }
        return false;
    }
}
