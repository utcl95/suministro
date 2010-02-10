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
public class CustomItemDemo extends MIDlet implements CommandListener {
    private static final Command CMD_EXIT = new Command ("Exit", Command.EXIT, 1);
    private Display display;
    private boolean firstTime;
    private Form mainForm;

    public CustomItemDemo () {
        firstTime = true;
        mainForm = new Form ("Custom Item");
    }

    protected void startApp () {
        if (firstTime) {
            display = Display.getDisplay (this);
            mainForm.append (new Table ("Suministro", Display.getDisplay (this)));
            mainForm.append (new TextField ("Consumo", null, 10, 0));
            mainForm.append (new TextField ("Observación", null, 10, 0));
            
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
}
