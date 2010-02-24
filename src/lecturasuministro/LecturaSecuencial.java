/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lecturasuministro;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;


/**
 * Lectura Secuencial de Suministro.
 */
public class LecturaSecuencial extends MIDlet implements CommandListener, ItemCommandListener {
    private static final Command CMD_EXIT = new Command ("Exit", Command.EXIT, 1);
    private static final Command CMD_PRESS2 = new Command ("Press", Command.ITEM, 1);
    private Display display;
    private boolean firstTime;
    private Form mainForm;
    private Alert yesNoAlert;
    private Command softKey1;
    private Command softKey2;
    private Command softKey3;
    private boolean status;
    private TextField consumo;
    private TextField obs;
    private int vobs;
    private int lactual;
    private String suministro;
    private int currentItem = 1;
    RMS_Suministro sRMS = new RMS_Suministro("SUMINISTROS");
    private FormCanvas objCanvas;

    public LecturaSecuencial () {
        firstTime = true;
        mainForm = new Form ("");
    }

    protected void startApp () {
        if (firstTime) {
            display = Display.getDisplay (this);
            objCanvas = new FormCanvas ("", Display.getDisplay (this));
            currentItem = objCanvas.siguienteSinData(currentItem);
            objCanvas.setCurrentSuministro(currentItem);
            mainForm.append (objCanvas);
            consumo = new TextField("Consumo/Observacion", "", 12, TextField.NUMERIC);
            obs = new TextField ("", "", 2, TextField.NUMERIC);
            mainForm.append(consumo);
            mainForm.append (obs);
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

        if (c.getCommandType() == Command.OK) {
            grabarConsumo();            
            consumo.setString("");
            obs.setString("");
            display.setCurrent(mainForm);

        }if (c.getCommandType() == Command.HELP) {
            grabarConsumo();
            consumo.setString("");
            obs.setString("");
            display.setCurrent(mainForm);

        }else if (c.getCommandType() == Command.STOP){
            display.setCurrent(mainForm);
        }
    }

    protected void destroyApp (boolean unconditional) {
    }

    protected void pauseApp () {
    }

    public void commandAction(Command c, Item item) {
    Validacion validarSuministro = new Validacion();

    
        if (c == CMD_PRESS2){

            suministro = objCanvas.getSuministro();

            int lanterior = 0;
            int promedio = 0;

            if(obs.getString().equals("")){
                vobs = 0;
            }else{
                vobs = Integer.parseInt(obs.getString());                
            }
            
            // Consumo puede ser 0, cuando se presenta algun problema.
            if(consumo.getString().equals("")) {
                lactual = 0;
            } else {
                lactual = Integer.parseInt(consumo.getString());
            }
            
            lanterior = objCanvas.getAnterior();
            promedio = objCanvas.getPromedio();
            int cons_act = lactual - lanterior;

            boolean esValido = false;
            boolean consumoEsValido  = validarSuministro.esValido(vobs, lactual, lanterior, cons_act, promedio);
            boolean obsEsValido = (vobs > 0 && vobs <= 40);
            boolean obsEsCero = (vobs == 0);

            if((consumoEsValido && (obsEsValido || obsEsCero)) || (obsEsValido && (lactual == 0) )){
               grabarConsumo();
               consumo.setString("");
               obs.setString("");
               display.setCurrent(mainForm);
            }

            if((!consumoEsValido && obsEsValido) || (!consumoEsValido && obsEsCero && (lactual != 0))){
                 mostrarMensaje(3, lactual);
                 return;
            }

            if((lactual==0 && !obsEsValido) || (!consumoEsValido && !obsEsValido) || (lactual == 0 && obsEsCero) ){
                 mostrarMensaje(2, lactual);
                 return;
            }

            if(consumoEsValido && !obsEsValido){
                mostrarMensaje(4, lactual);
                return;
            }

       }       // end if

        validarSuministro = null;
    }

     public void grabarConsumo(){
        int index = 0;

            index = getIdSuministro();

        sRMS.setSuministro(index, suministro, consumo.getString(), obs.getString());
        repaintCanvasAfterSave();

    }

     public void mostrarAlerta(){
        Alert al1 = new Alert("Atención");
        al1.setString("Observación Incorrecta.");
        display.setCurrent(al1);
     }

     public void mostrarMensaje(int num_mensaje, int lectura_actual){
        switch(num_mensaje) {
            case 1:
                String msg = "Lectura correcta: "+lectura_actual+
                        " .Obs: "+vobs;
                Alert al = new Alert(msg);
                softKey3 = new Command("Salir", Command.HELP, 1);
                al.addCommand(softKey3);
                al.setCommandListener(this);
                display.setCurrent(al);
                break;
            case 2:
                String msg1 = "Consumo y observacion incorrectos";
                Alert al1 = new Alert(msg1);
                display.setCurrent(al1);
                break;
            case 3:
                yesNoAlert = new Alert("Atencion");
                yesNoAlert.setString("Consumo Incorrecto: " + lectura_actual+". Desea guardar?");
                softKey1 = new Command("No", Command.STOP, 1);
                softKey2 = new Command("Yes", Command.OK, 1);
                yesNoAlert.addCommand(softKey1);
                yesNoAlert.addCommand(softKey2);
                yesNoAlert.setCommandListener(this);
                display.setCurrent(yesNoAlert);
                break;
             case 4:
                String msg2 = "Observacion incorrecta";
                Alert al2 = new Alert(msg2);
                display.setCurrent(al2);
                break;
        } // end case
    }

     public int getIdSuministro() {
        int i = objCanvas.getCurrentSuministroPosition();
        return i;
    }

        // Dibuja el Canvas despues de grabar.
    public void repaintCanvasAfterSave() {
        objCanvas.doNext();
        //display.setCurrent(mainForm);
    }
}
