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
    private static final Command CMD_BACK = new Command ("Regresar", Command.BACK, 1);
    private static final Command CMD_EXIT = new Command ("Salir", Command.EXIT, 1);
    private static final Command CMD_GRABAR = new Command ("Press", Command.ITEM, 1);
    private static final Command CMD_BUSCAR = new Command ("Buscar", Command.ITEM, 1);
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
            mainForm = new Form ("");
            mainForm.append ("BUSQUEDA POR SUMINISTRO");
            txtsum = new TextField ("Suministro", "", 15, TextField.NUMERIC);
            mainForm.append (txtsum);

            StringItem item = new StringItem("", "Buscar", Item.BUTTON);
            item.setDefaultCommand(CMD_BUSCAR);
            item.setItemCommandListener(this);
            mainForm.append(item);

            mainForm.addCommand (CMD_CANCEL);
            mainForm.setCommandListener (this);
            display2.setCurrent(mainForm);

            mainForm2 = new Form ("");
            mainForm2.append ("INGRESO CONSUMO");
            objCanvas = new FormCanvas ("Suministro", Display.getDisplay (this));
            mainForm2.append (objCanvas);
            consumo = new TextField("Consumo   ", "", 12, TextField.NUMERIC);
            obs = new TextField ("Obs", "", 2, TextField.NUMERIC);
            mainForm2.append(consumo);
            mainForm2.append (obs);
            StringItem item2 = new StringItem("", "Ingresar", Item.BUTTON);
            item2.setDefaultCommand(CMD_GRABAR);
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

    protected void pauseApp () {
    }

    public void commandAction(Command c, Item item) {

        suministro = txtsum.getString();
        Validacion validarSuministro = new Validacion();
        
        if (c == CMD_BUSCAR) {
            // No realizar la busqueda.
            if( suministro.trim().length() < 6 )
                return;

            // Busqueda
            int index = rms_orden.buscar(suministro);
            if (index == 0) {
                String msg = "No existe suministro"+"\n"+
                             "o Ya tiene Lectura";
                Alert al = new Alert("Atencion");
                al.setString(msg);
                display2.setCurrent(al);
            } else {
                boolean suministroConData = sRMS.tieneData(index);
                if(index != 0 && !suministroConData) {
                    display2.setCurrent (mainForm2);
                    objCanvas.setCurrentSuministro(index);
                }
            }

        }if (c == CMD_GRABAR){

            suministro = objCanvas.getSuministro();

            int lanterior = 0;
            int promedio = 0;

            if(obs.getString().equals("")) {
                vobs = 0;
            } else {
                vobs = Integer.parseInt(obs.getString());                
            }

            // Consumo puede ser 0, cuando se presenta algun problema.
            if(consumo.getString().equals("")) {
                lactual = 0;
            } else {
                lactual = Integer.parseInt(consumo.getString());
            }

            if (vobs >= 0 && vobs <= 40){
           
                lanterior = objCanvas.getAnterior();
                promedio = objCanvas.getPromedio();
                int cons_act = lactual - lanterior;

                // Validaciones
                boolean esValido = false;
                boolean suministroEsValido  = validarSuministro.esValido(vobs, lactual, lanterior, cons_act, promedio);
                boolean obsEsValido = (vobs > 0 && vobs <= 40);
                boolean obsEsCero = (vobs == 0);
                //TODO: Si consumo=0 y obs tiene valor añadir mensaje.
                if((suministroEsValido && (obsEsValido || obsEsCero)) || (obsEsValido && (lactual == 0) ))
                    esValido = true;

                if(!esValido ) {
                    mostrarMensaje("c", lactual);
                }else{
                    grabarConsumo();
                    consumo.setString("");
                    obs.setString("");
                    display2.setCurrent(mainForm2);
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
//        int nObs = Integer.parseInt(obs.getString());
//        String msg = "Suministro : " + suministro + "\n" +
//                    "Consumo : " + consumo.getString() + "\n" +
//                    "Observacion : " + getObservacion(nObs);
//        Alert recordSaved = new Alert(msg);
//        recordSaved.setTimeout(100);
//        display2.setCurrent(recordSaved);
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

    public String getObservacion(int i) {
        String[] observacion = {"OO", "AL", "MI", "MO", "MT", "SM", "MS", "TI", "LC", "PI", "NU",
                                      "MM", "BY", "CM", "ST", "DL", "FR", "NS", "CY", "DI", "DC",
                                      "EX", "ES", "FC", "MA", "MD", "ME", "EA", "MF", "TR", "SE",
                                      "CV", "RI", "LR", "CE", "AY", "PR", "PC", "LD", "MC", "TD"};
        return observacion[i];
    }

}

