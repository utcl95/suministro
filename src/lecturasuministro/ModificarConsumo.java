/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lecturasuministro;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

/**
 * @author Carlos
 */
public class ModificarConsumo extends MIDlet implements CommandListener, ItemCommandListener{
    private boolean firstTime;
    private Form mainForm;
    private Form mainForm2;
    private Display display2;
    private TextField consum;
    private static final Command CMD_PRESS = new Command ("Buscar", Command.ITEM, 1);
    private static final Command CMD_PRESS2 = new Command ("Ingresar", Command.ITEM, 1);
    private String suministro;
    private SetConsumo sc;
    private canvasForm canvas = null;
    private Alert yesNoAlert;
    private Command softKey1;
    private Command softKey2;
    private boolean status;

    RMS_Ordenados rms_orden = new RMS_Ordenados("ORDENADOS");
    int currentIdSuministro = 0;

    SuministroRMS sRMS = new SuministroRMS("SUMINISTROS");
    private TextField consum2;
    private TextField obs;

    public ModificarConsumo () {
        firstTime = true;
        mainForm = new Form ("Busqueda...");
    }
    
    public void startApp() {
        if (firstTime) {
            
            display2 = Display.getDisplay (this);

            mainForm.append ("BUSQUEDA POR CONSUMO");
            consum = new TextField ("Suministro", "", 15, TextField.NUMERIC);
            mainForm.append (consum);

            StringItem item = new StringItem("", "Buscar", Item.BUTTON);
            item.setDefaultCommand(CMD_PRESS);
            item.setItemCommandListener(this);

            mainForm.append(item);
            mainForm.setCommandListener (this);
            firstTime = false;

            display2.setCurrent(mainForm);
            canvas = new canvasForm ();
        }
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable d) {
    }

    public void commandAction(Command c, Item item) {
        if (c == CMD_PRESS) {
            suministro = consum.getString();
            // Id de Suministro.
            int index = rms_orden.buscar(suministro);
            currentIdSuministro = index;
            boolean data = sRMS.tieneData(index);
            if(data == true){
                sc = new SetConsumo(suministro, this);
                //sc.addCommand(CMD_CANCEL);
                sc.setCommandListener(this);
                display2.setCurrent(sc);
            }else{
                String msg = "El suministro no tiene consumo";
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
            yesNoAlert.setString("Consumo incorrecto. Desea guardar consumo : " + lectura_actual);
            softKey1 = new Command("No", Command.STOP, 1);
            softKey2 = new Command("Yes", Command.OK, 1);
            yesNoAlert.addCommand(softKey1);
            yesNoAlert.addCommand(softKey2);
            yesNoAlert.setCommandListener(this);
            display2.setCurrent(yesNoAlert);
            status = false;
        }
    }

    public int lAnterior(){
        return canvas.getAnterior();
    }

    public int lPromedio(){
        return canvas.getPromedio();
    }

    public int getIdSuministro() {
        int i = currentIdSuministro;
        return i;
    }
}
