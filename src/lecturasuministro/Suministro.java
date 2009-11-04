
package lecturasuministro;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
/**
 *
 * @author utcl95
 */
public class Suministro extends MIDlet implements CommandListener, ItemCommandListener {
    private static final Command CMD_PRESS = new Command ("Press", Command.ITEM, 1);
    private static final Command CMD_EXIT = new Command ("Exit", Command.EXIT, 1);
    private Display display;
    private TextField txt1;
    private TextField txt3;
    private TextField txt4;
    private Form mainForm;
    private Form mainForm2;

    /**
     * Signals the MIDlet to start and enter the Active state.
     */
    protected void startApp () {
        
        display = Display.getDisplay (this);

        mainForm = new Form ("Suministro");
       
        txt1 = new TextField ("Buscar", "", 15, TextField.ANY);
        
        mainForm.append(txt1);

        StringItem item = new StringItem ("Button ", "Button", Item.BUTTON);
        item.setDefaultCommand (CMD_PRESS);
        item.setItemCommandListener (this);
        mainForm.append (item);
        mainForm.addCommand (CMD_EXIT);
        mainForm.setCommandListener (this);
        display.setCurrent (mainForm);

        mainForm2 = new Form ("respuesta");
        txt3 = new TextField("Suministro", "", 20, TextField.UNEDITABLE);
        txt4 = new TextField("Cuenta", "", 20, TextField.ANY);
        mainForm2.append(txt3);
        mainForm2.append(txt4);
    }

    public void commandAction (Command c, Item item) {

        if (c == CMD_PRESS) {

            String[] stringArray = {"jaqui", "carlos", "juan", "moni"};
           
            for (int i=0; i<4; i++){
               if (stringArray[i].equals(txt1.getString())){                  
                   mainForm2.setCommandListener(this);
                   display.setCurrent (mainForm2);
                   txt3.setString(txt1.getString());

               }else if(stringArray[i].equals(txt1.getString())){
                   String text = "Se edfancontro suministro";
                   Alert a = new Alert ("Action", text, null, AlertType.INFO);
                   display.setCurrent (a);
               }
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
}
