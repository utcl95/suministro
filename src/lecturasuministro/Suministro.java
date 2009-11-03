
package lecturasuministro;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
/**
 *
 * @author utcl95
 */
public class Suministro extends MIDlet implements CommandListener {
    private Command exitCommand = new Command ("Salir", Command.EXIT, 1);
    private boolean firstTime;
    private Form mainForm;

    public Suministro() {
        firstTime = true;
        mainForm = new Form ("Suministro");
    }

    protected void startApp () {
        if (firstTime) {
            mainForm.append ("Número Suministro:");

            mainForm.append (new TextField ("", "", 15, TextField.NUMERIC));


            mainForm.addCommand (exitCommand);
            mainForm.setCommandListener (this);
            firstTime = false;
        }

        Display.getDisplay (this).setCurrent (mainForm);
    }

    public void commandAction (Command c, Displayable s) {
        if (c == exitCommand) {
            destroyApp (false);
            notifyDestroyed ();
        }
    }

    protected void destroyApp (boolean unconditional) {
    }

    protected void pauseApp () {
    }

}
