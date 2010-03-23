/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lecturasuministro;

import java.io.IOException;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;


/**
 * Lectura Secuencial de Suministro.
 */
public class LecturaSecuencial extends MIDlet implements CommandListener, ItemCommandListener {
    private static final Command CMD_EXIT   = new Command ("Exit", Command.EXIT, 1);
    private static final Command CMD_GRABAR = new Command ("Press", Command.ITEM, 1);
    private static final Command CMD_SAVE   = new Command ("Grabar", Command.OK, 1);
    
    private Display display;
    private Form mainForm;

    private boolean firstTime;

    private String suministro;
    
    private TextField consumo;
    private TextField obs;
    
    private int currentItem = 1;    

    private GrabarLectura objGrabarLectura = null;
    
    private FormCanvas objCanvas;

    public LecturaSecuencial () {
        firstTime = true;
        mainForm = new Form ("");
    }

    protected void startApp () {
        if (firstTime) {
            display = Display.getDisplay(this);

            objCanvas   = new FormCanvas ("", Display.getDisplay (this));

            currentItem = objCanvas.siguienteSinData(currentItem);
            objCanvas.setCurrentSuministro(currentItem);

            mainForm.append(objCanvas);
            consumo     = new TextField("Consumo/Observacion", "", 6, TextField.NUMERIC);
            obs         = new TextField ("", "", 2, TextField.NUMERIC);

            mainForm.append(consumo);
            mainForm.append (obs);

            //StringItem item = new StringItem("", "Ingresar", Item.BUTTON);
            //item.setDefaultCommand(CMD_GRABAR);
            //item.setItemCommandListener(this);

            //mainForm.append(item);
            mainForm.addCommand (CMD_EXIT);
            mainForm.addCommand(CMD_SAVE);
            mainForm.setCommandListener (this);

            objGrabarLectura = new GrabarLectura(this, objCanvas, display);
            firstTime = false;
        }

        display.setCurrent (mainForm);

    }

    public void commandAction (Command c, Displayable d) {
        suministro = objCanvas.getSuministro();
        objGrabarLectura.setLectura(suministro, consumo.getString(), obs.getString());
            
        if (c == CMD_EXIT) {
            destroyApp (false);
            notifyDestroyed ();
        } else if ((c.getCommandType() == Command.OK) && (c != CMD_SAVE)) {
            objGrabarLectura.grabarLectura();
            resetConsumoObservacion();
            display.setCurrent(mainForm);
        } else if (c.getCommandType() == Command.STOP){
            display.setCurrent(mainForm);
        } else if (c == CMD_SAVE) {
            objGrabarLectura.consultaGrabar();
        } // if save
    }

    public void commandAction(Command c, Item item) {        
        if (c == CMD_GRABAR) {
            suministro = objCanvas.getSuministro();
            objGrabarLectura.setLectura(suministro, consumo.getString(), obs.getString());
            objGrabarLectura.consultaGrabar();
        }
    }

    public void resetConsumoObservacion() {
        consumo.setString("");
        obs.setString("");
    }

    public void repaintCanvasAfterSave() {
            objCanvas.doNext();
        if (objCanvas.esElUltimoRegistro()) {
            destroyApp (false);
            notifyDestroyed();
        }
    }

    protected void destroyApp (boolean unconditional) {
    }

    protected void pauseApp () {
    }
}
