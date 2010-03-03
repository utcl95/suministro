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
            consum = new TextField ("Suministro", "", 8, TextField.NUMERIC);
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
                // Volver al menu principal
                destroyApp (false);
                notifyDestroyed();
                //display2.setCurrent(mainForm);

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
            if(index == 0){
                String msg1 = "No existe suministro";
                Alert al1 = new Alert(msg1);
                display2.setCurrent(al1);
            }else{
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

    public String obsActual(){
        String data[] = sRMS.getRecord(index);
        String actual = data[2].trim();
        return actual;
    }

    public int getIdSuministro() {
        int i = currentIdSuministro;
        return i;
    }
}


class SetConsumo extends Form implements CommandListener, ItemCommandListener{

    private static final Command CMD_PRESS2 = new Command ("Press", Command.ITEM, 1);
    private ModificarConsumo ss;
    private TextField consumo;
    private String suministro;
    private TextField obs;
    private int vobs;
    private int lactual;
    private String sumanterior;

    RMS_Suministro sRMS = new RMS_Suministro("SUMINISTROS");
    private String lect;

    SetConsumo(String lect, ModificarConsumo ss) {
        super("Lectura de Consumo");
        this.ss = ss;
        this.lect = lect;
        leerConsumoSuministro(lect);
    }

    public void commandAction (Command c, Item item) {
        Validacion validarSuministro = new Validacion();

        if(obs.getString().equals(""))
            vobs = 0;
        else
            vobs = Integer.parseInt(obs.getString());

        if(consumo.getString().equals(""))
            lactual = 0;
        else
           lactual = Integer.parseInt(consumo.getString());
        int lanterior = ss.lAnterior();
        int promedio = ss.lPromedio();
        int cons_act = lactual - lanterior;

        if (c == CMD_PRESS2){

            if(!validarSuministro.esValido(lactual, lanterior, cons_act, promedio) ) {
              ss.mostrarMensaje("c", lactual);
            }else{
              datosConsumo();
            }
        }       // end if

        validarSuministro = null;
    }

     public void datosConsumo(){

        if (ingresarConsumo(suministro, consumo.getString(), obs.getString())){}

    }

    public void leerConsumoSuministro(String msuministro) {
       suministro = msuministro;
       String lectura = new String(ss.lActual());
       consumo = new TextField("Consumo   ", "", 6, TextField.NUMERIC);
       append(new TextField("Suministro", suministro, 8, TextField.UNEDITABLE));
       consumo.setString(lectura);
       append(consumo);
       String observacion = new String(ss.obsActual());
       obs = new TextField("Obs", "", 2, TextField.NUMERIC);
       obs.setString(observacion);
       append(obs);

       StringItem item = new StringItem("", "Corregir", Item.BUTTON);
       item.setDefaultCommand(CMD_PRESS2);
       item.setItemCommandListener(this);
       append(item);

    }

    public boolean ingresarConsumo(String msuministro, String mconsumo, String mobs) {
        int index = ss.getIdSuministro();
        sRMS.setSuministro(index, msuministro, mconsumo, mobs);
        return false;
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable d) {
    }
}
