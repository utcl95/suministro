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
    private static final Command CMD_EXIT = new Command ("Exit", Command.EXIT, 1);
    private static final Command CMD_PRESS2 = new Command ("Press", Command.ITEM, 1);
    private static final Command CMD_PRESS = new Command ("Buscar", Command.ITEM, 1);
    private static final Command CMD_CANCEL = new Command ("Cancelar", Command.CANCEL, 1);

    private boolean firstTime;
    private Form mainForm;
    private Form mainForm2;
    private Display display2;
    private TextField txtsum;
    private TextField consumo;
    private TextField obs;
    private String suministro;
    private Alert yesNoAlert;
    private Command softKey1;
    private Command softKey2;
    //private canvasForm canvas = null;
    private String sumCanvas;
    private FormCanvas objCanvas;
    private int vobs;
    private int lactual;

    RMS_Ordenados rms_orden = new RMS_Ordenados("ORDENADOS");
    RMS_Suministro sRMS = new RMS_Suministro("SUMINISTROS");

    public BusquedaSuministro () {
        firstTime = true;
        mainForm = new Form ("Ingreso Suministro");
    }

    protected void startApp () {

            display2 = Display.getDisplay (this);
            mainForm = new Form ("String Item Demo");
            mainForm.append ("BUSQUEDA POR SUMINISTRO");
            txtsum = new TextField ("Suministro", "", 15, TextField.NUMERIC);
            mainForm.append (txtsum);

            StringItem item = new StringItem("", "Buscar", Item.BUTTON);
            item.setDefaultCommand(CMD_PRESS);
            item.setItemCommandListener(this);
            mainForm.append(item);

            mainForm.addCommand (CMD_CANCEL);
            mainForm.setCommandListener (this);
            display2.setCurrent(mainForm);

            mainForm2 = new Form ("respuesta");
            mainForm2.append ("INGRESO CONSUMO");
            objCanvas = new FormCanvas ("Suministro", Display.getDisplay (this));
            mainForm2.append (objCanvas);
            consumo = new TextField("Consumo   ", "", 12, TextField.NUMERIC);
            obs = new TextField ("Obs", "", 2, TextField.NUMERIC);
            mainForm2.append(consumo);
            mainForm2.append (obs);
            StringItem item2 = new StringItem("", "Ingresar", Item.BUTTON);
            item2.setDefaultCommand(CMD_PRESS2);
            item2.setItemCommandListener(this);
            mainForm2.append(item2);
            mainForm2.addCommand (CMD_EXIT);
            mainForm2.setCommandListener (this);
    }

    public void commandAction (Command c, Displayable s) {

            if (c.getCommandType() == Command.OK) {
                grabarConsumo();
                consumo.setString("");
                obs.setString("");
                display2.setCurrent(mainForm2);

            }if (c.getCommandType() == Command.STOP){
                display2.setCurrent(mainForm2);

            }if (c.getCommandType() == Command.EXIT){
                txtsum.setString("");
                display2.setCurrent(mainForm);

            }if (c == CMD_CANCEL) {
                destroyApp (false);
                notifyDestroyed ();

        }
    }

    protected void destroyApp (boolean unconditional) {
    }

//    public void buscarSuministro(String sumActual){
//        sumCanvas  = sumActual;
//
//        lectura = new LeerConsumo(sumCanvas, this);
//        txtsum.setString("");
//        //lectura.addCommand(CMD_CANCEL);
//        lectura.setCommandListener(this);
//        display2.setCurrent(lectura);
// }

    protected void pauseApp () {
    }

    public void commandAction(Command c, Item item) {

        suministro = txtsum.getString();
        Validacion validarSuministro = new Validacion();
        
        if (c == CMD_PRESS) {
            // Busqueda
            //int index = sRMS.searchSuministro(suministro);
            int index = rms_orden.buscar(suministro);
            boolean suministroConData = sRMS.tieneData(index);
            if(index != 0 && !suministroConData){
                display2.setCurrent (mainForm2);
                objCanvas.setCurrentSuministro(index);
            }else{
                String msg = "No existe suministro";
                Alert al = new Alert(msg);
                display2.setCurrent(al);
            }

        }if (c == CMD_PRESS2){

            suministro = objCanvas.getSuministro();

            int lanterior = 0;
            int promedio = 0;

            if(obs.getString().equals("")){
                vobs = 0;
            }else{
                vobs = Integer.parseInt(obs.getString());
                lactual = Integer.parseInt(consumo.getString());
            }

            if (vobs  > 0 && vobs <= 40){

            }else {
                 mostrarAlerta();
            }

            lanterior = objCanvas.getAnterior();
            promedio = objCanvas.getPromedio();
            int cons_act = lactual - lanterior;


            if(!validarSuministro.esValido(vobs, lactual, lanterior, cons_act, promedio) ) {
                mostrarMensaje("c", lactual);
            }else{
                grabarConsumo();
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
        return objCanvas.getAnterior();
    }

    public int lPromedio(){
        return objCanvas.getPromedio();
    }

    // Dibuja el Canvas despues de grabar.
    public void repaintCanvasAfterSave() {
        objCanvas.doNext();
        //display2.setCurrent(canvas);
    }

    public void mostrarAlerta(){
        System.out.println("jaqui1");
        Alert al1 = new Alert("Atención");
        al1.setString("Observación Incorrecta.");
        display2.setCurrent(al1);
     }

     public int getIdSuministro() {
        int i = objCanvas.getCurrentSuministroPosition();
        return i;
    }

}

