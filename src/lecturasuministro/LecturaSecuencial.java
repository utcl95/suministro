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
        mainForm = new Form ("Ingreso Secuencial");
    }

    protected void startApp () {
        if (firstTime) {
            display = Display.getDisplay (this);
            objCanvas = new FormCanvas ("Suministro", Display.getDisplay (this));
            currentItem = objCanvas.siguienteSinData(currentItem);
            objCanvas.setCurrentSuministro(currentItem);
            mainForm.append (objCanvas);
            consumo = new TextField("Consumo   ", "", 12, TextField.NUMERIC);
            obs = new TextField ("Obs", "", 2, TextField.NUMERIC);
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
                lactual = Integer.parseInt(consumo.getString());
            }

            if (vobs  >= 0 && vobs <= 40){
            
                lanterior = objCanvas.getAnterior();
                promedio = objCanvas.getPromedio();
                int cons_act = lactual - lanterior;

                boolean esValido = validarSuministro.esValido(vobs, lactual, lanterior, cons_act, promedio);
                //if(vobs > 0 && vobs <= 40) esValido = true;

                if(!esValido ) {
                    mostrarMensaje("c", lactual);
                }else{
                    grabarConsumo();
                    consumo.setString("");
                    obs.setString("");
                    display.setCurrent(mainForm);
                }

            }else {
               mostrarAlerta();
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

     public void mostrarMensaje(String m, int lectura_actual){
        String mns = m;

        if(mns.equals("d")){
            String msg = "Lectura correcta";
            Alert al = new Alert(msg);
            System.out.println("jaqui4");
            display.setCurrent(al);
            System.out.println("jaqui5");
        }else{
            yesNoAlert = new Alert("Atencion");
            yesNoAlert.setString("Consumo incorrecto. Desea guardar consumo : " + lectura_actual);
            softKey1 = new Command("No", Command.STOP, 1);
            softKey2 = new Command("Yes", Command.OK, 1);
            yesNoAlert.addCommand(softKey1);
            yesNoAlert.addCommand(softKey2);
            yesNoAlert.setCommandListener(this);
            System.out.println("jaqui6");
            display.setCurrent(yesNoAlert);
            System.out.println("jaqui7");
            status = false;
        }
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
