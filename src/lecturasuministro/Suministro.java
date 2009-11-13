
package lecturasuministro;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

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
  
    // Funciones sobre Suministro:
    // addSuministro, searchSuministro, setSuministro, showSuministro
    SuministroRMS sRMS = new SuministroRMS("ELECTRO");

    protected void startApp () {
        try {
            display = Display.getDisplay(this);
            // Cargar Suministro.
            cargarSuministro();
            sRMS.showRMS();
            
            mainForm = new Form("Lectura Suministro");
            txt1 = new TextField("Buscar", "", 15, TextField.NUMERIC);
            mainForm.append(txt1);
            StringItem item = new StringItem("Button ", "Button", Item.BUTTON);
            item.setDefaultCommand(CMD_PRESS);
            item.setItemCommandListener(this);
            mainForm.append(item);
            mainForm.addCommand(CMD_EXIT);
            mainForm.setCommandListener(this);
            display.setCurrent(mainForm);
           
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
    }

    public void commandAction (Command c, Item item) {
        if (c == CMD_PRESS) {
             if (buscarSuministro(txt1.getString())) {
                 
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
    public boolean cargarSuministro() throws RecordStoreException {
        // Estos son los suministros que van a ser leidos.
        String[] m_suministros = {"1111", "2222", "3333", "4444"};                    

        int nElementos = 4;
        for (int i = 0; i < nElementos; ++i) {
            sRMS.addSuministro(m_suministros[i]);
            System.out.println(m_suministros[i]);
        }        
        return true;
    }

    /**
     * Buscar Suministro
     * return True o False si se encuentra el suministro
     */
    public boolean buscarSuministro(String msuministro) {
             
        if(sRMS.searchSuministro(msuministro) == 0)
               
           System.out.println("NO Encontrado");
        
        else{
            mainForm2 = new Form("Ingresar Suministro");
            consumo = new TextField("Consumo   ", "", 20, TextField.NUMERIC);
            mainForm2.append(new TextField("Suministro", txt1.getString(), 20, TextField.UNEDITABLE));
            mainForm2.append(consumo);
            mainForm2.setCommandListener(this);
            display.setCurrent (mainForm2);
           //System.out.println("Encontrado");
        }
        return false; 
    }
}
