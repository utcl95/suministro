/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lecturasuministro;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;

/**
 * @author Jaqui
 */
public class BusquedaSuministro extends MIDlet implements CommandListener, ItemCommandListener {
    private static final Command exitCommand = new Command ("Salir", Command.EXIT, 1);

    private static final Command CMD_BACK = new Command ("Regresar", Command.BACK, 1);

    private static final Command CMD_PRESS = new Command ("Buscar", Command.ITEM, 1);
    private static final Command CMD_CANCEL = new Command ("Cancelar", Command.CANCEL, 1);

    private boolean firstTime;
    private Form mainForm;
    private Display display2;
    private TextField txtsum;
    private String suministro;
    private LeerConsumo lectura = null;
    private Alert yesNoAlert;
    private Command softKey1;
    private Command softKey2;
    private canvasForm canvas = null;
    private String sumCanvas;

    RMS_Ordenados rms_orden = new RMS_Ordenados("ORDENADOS");
    RMS_Suministro sRMS = new RMS_Suministro("SUMINISTROS");

    public BusquedaSuministro () {
        firstTime = true;
        mainForm = new Form ("Ingreso Suministro");
    }

    protected void startApp () {

        if (firstTime) {
            //fs = new FormSuministro("Lectura x Zona");

            display2 = Display.getDisplay (this);

            mainForm.append ("BUSQUEDA POR SUMINISTRO");
            txtsum = new TextField ("Suministro", "", 15, TextField.NUMERIC);
            mainForm.append (txtsum);

            StringItem item = new StringItem("", "Buscar", Item.BUTTON);
            item.setDefaultCommand(CMD_PRESS);
            item.setItemCommandListener(this);
            mainForm.append(item);

            mainForm.addCommand (exitCommand);
            mainForm.setCommandListener (this);
            firstTime = false;

            canvas =  new canvasForm(this);
            canvas.addCommand(CMD_BACK);
            canvas.setCommandListener(this);



            display2.setCurrent(mainForm);
        }

    }

    public void commandAction (Command c, Displayable s) {

            if (c.getCommandType() == Command.OK) {
                lectura.datosConsumo();
                //canvas.doNext();
                display2.setCurrent(canvas);


            } else if (c.getCommandType() == Command.BACK) {
                        display2.setCurrent(mainForm);
            } else if (c.getCommandType() == Command.EXIT) {
                        destroyApp (false);
                        notifyDestroyed ();
            } else if (c.getCommandType() == Command.CANCEL){
                        display2.setCurrent(canvas);
            }else if (c.getCommandType() == Command.STOP){
                        display2.setCurrent(lectura);
            }
    }

    protected void destroyApp (boolean unconditional) {
    }

    public void buscarSuministro(String sumActual){
        sumCanvas  = sumActual;

        lectura = new LeerConsumo(sumCanvas, this);
        txtsum.setString("");
        lectura.addCommand(CMD_CANCEL);
        lectura.setCommandListener(this);
        display2.setCurrent(lectura);
 }

    protected void pauseApp () {
    }

    public void commandAction(Command c, Item item) {

        suministro = txtsum.getString();

        if (c == exitCommand) {
            destroyApp (false);
            notifyDestroyed ();
        }if (c == CMD_PRESS) {
            // Busqueda
            //int index = sRMS.searchSuministro(suministro);
            int index = rms_orden.buscar(suministro);

             if(index != 0){
                canvas.setCurrentSuministro(index);
                display2.setCurrent(canvas);

             }else{
                String msg = "No existe suministro";
                Alert al = new Alert(msg);
                display2.setCurrent(al);
             }
        }
    }

   public void mostrarMensaje(String m, int lectura_actual){
        String mns = m;

        if(mns.equals("d")){
            String msg = "Lectura correcta";
            Alert al = new Alert(msg);
            display2.setCurrent(al);
        }else{
            yesNoAlert = new Alert("Atencion");
            yesNoAlert.setString("Consumo incorrecto. Desea guardar consumo? " + lectura_actual);
            softKey1 = new Command("No", Command.STOP, 1);
            softKey2 = new Command("Yes", Command.OK, 1);
            yesNoAlert.addCommand(softKey1);
            yesNoAlert.addCommand(softKey2);
            yesNoAlert.setCommandListener(this);
            display2.setCurrent(yesNoAlert);
        }

    }

    public int lAnterior(){
        return canvas.getAnterior();
    }

    public int lPromedio(){
        return canvas.getPromedio();
    }

    // Dibuja el Canvas despues de grabar.
    public void repaintCanvasAfterSave() {
        canvas.doNext();
        display2.setCurrent(canvas);
    }

    public int getIdSuministro() {
        int i = canvas.getCurrentSuministroPosition();
        return i;
    }

}

