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
    private Display display2;
    private TextField consum;
    private static final Command CMD_BACK = new Command ("Back", Command.BACK, 1);
    private static final Command CMD_PRESS = new Command ("Buscar", Command.ITEM, 1);
    private static final Command CMD_PRESS2 = new Command ("Ingresar", Command.ITEM, 1);
    private static final Command CMD_EXIT = new Command ("Exit", Command.EXIT, 1);
    private String suministro;
    private SetConsumo sc;
    private Alert yesNoAlert;
    private Command softKey1;
    private Command softKey2;
    private boolean status;
    private int index;

    RMS_Ordenados rms_orden = new RMS_Ordenados("ORDENADOS");
    private RMS_DataSuministros dataRMS = new RMS_DataSuministros("DATA00");
    int currentIdSuministro = 0;

    RMS_Suministro sRMS = new RMS_Suministro("SUMINISTROS");
    
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
            mainForm.addCommand (CMD_EXIT);
            firstTime = false;

            display2.setCurrent(mainForm);
            
        }
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable d) {
         status = c.getCommandType() == Command.OK;

        if (c.getCommandType() == Command.OK) {
                sc.datosConsumo();
                display2.setCurrent(mainForm);

            } else if (c.getCommandType() == Command.STOP){
                display2.setCurrent(sc);
            }else if (c.getCommandType() == Command.BACK) {
                        display2.setCurrent(mainForm);
            } else if (c.getCommandType() == Command.EXIT) {
                        destroyApp (false);
                        notifyDestroyed ();
            }
    }

    public void commandAction(Command c, Item item) {
        if (c == CMD_PRESS) {
            suministro = consum.getString();
            // Id de Suministro.
            index = rms_orden.buscar(suministro);
            currentIdSuministro = index;
            boolean data = sRMS.tieneData(index);
            if(data == true){
                consum.setString("");
                sc = new SetConsumo(suministro, this);
                sc.addCommand(CMD_BACK);
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
        String data[] = dataRMS.getRecord(index);
        int anterior = Integer.parseInt(data[5].trim());
        return anterior;
    }

    public int lPromedio(){
        String data[] = dataRMS.getRecord(index);
        int promedio = Integer.parseInt(data[6]);
        return promedio;
    }

    public String lActual(){
        String data[] = sRMS.getRecord(index);
        String actual = data[1].trim();
        return actual;
    }
    
    public int getIdSuministro() {
        int i = currentIdSuministro;
        return i;
    }
}
