/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lecturasuministro;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;


/**
 * CustomItem demo
 */
public class BusqedaSuministro extends MIDlet implements CommandListener, ItemCommandListener {
    private static final Command CMD_EXIT = new Command ("Exit", Command.EXIT, 1);
    private static final Command CMD_PRESS2 = new Command ("Press", Command.ITEM, 1);
    private Display display;
    private boolean firstTime;
    private Form mainForm;

    public BusqedaSuministro () {
        firstTime = true;
        mainForm = new Form ("Custom Item");
    }

    protected void startApp () {
        if (firstTime) {
            display = Display.getDisplay (this);
            mainForm.append (new FormCanvas ("Suministro", Display.getDisplay (this)));
            mainForm.append (new TextField ("Consumo   ", "", 12, TextField.NUMERIC));
            mainForm.append (new TextField ("Obs", "", 2, TextField.NUMERIC));

            StringItem item = new StringItem("", "Ingresar", Item.BUTTON);
            item.setDefaultCommand(CMD_PRESS2);
            item.setItemCommandListener(this);
            mainForm.append(item);
            mainForm.addCommand (CMD_EXIT);
            mainForm.setCommandListener (this);
            firstTime = false;
        }

        display.setCurrent (mainForm);
    }

    public void commandAction (Command c, Displayable d) {
        if (c == CMD_EXIT) {
            destroyApp (false);
            notifyDestroyed ();
        }
    }

    protected void destroyApp (boolean unconditional) {
    }

    protected void pauseApp () {
    }

    public void commandAction(Command c, Item item) {
    }
}
