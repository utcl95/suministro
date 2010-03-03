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
    private static final Command CMD_BACK   = new Command ("Regresar", Command.BACK, 1);
    private static final Command CMD_EXIT   = new Command ("Salir", Command.EXIT, 1);
    private static final Command CMD_GRABAR = new Command ("Press", Command.ITEM, 1);
    private static final Command CMD_BUSCAR = new Command ("Buscar", Command.ITEM, 1);
    private static final Command CMD_CANCEL = new Command ("Cancelar", Command.CANCEL, 1);
    private static final Command CMD_SAVE   = new Command ("Grabar", Command.OK, 1);

    private boolean firstTime;
    private Form mainForm;
    private Form mainForm2;
    private Display display;

    private TextField txtsum;
    private TextField consumo;
    private TextField obs;
    
    private String suministro;

    private FormCanvas objCanvas;

    GrabarLectura objGrabarLectura = null;

    RMS_Ordenados rms_orden = new RMS_Ordenados("ORDENADOS");
    RMS_Suministro sRMS = new RMS_Suministro("SUMINISTROS");

    public BusquedaSuministro () {
        firstTime = true;
        mainForm = new Form ("Ingreso Suministro");
    }

    protected void startApp () {

            display = Display.getDisplay (this);
            mainForm = new Form ("");
            mainForm.append ("");
            txtsum = new TextField ("Suministro", "", 8, TextField.NUMERIC);
            mainForm.append (txtsum);

            StringItem item = new StringItem("", "Buscar", Item.BUTTON);
            item.setDefaultCommand(CMD_BUSCAR);
            item.setItemCommandListener(this);
            mainForm.append(item);

            mainForm.addCommand (CMD_CANCEL);
            mainForm.setCommandListener (this);
            display.setCurrent(mainForm);

            mainForm2 = new Form ("");
            mainForm2.append ("");
            objCanvas = new FormCanvas ("", Display.getDisplay (this));
            mainForm2.append (objCanvas);
            consumo = new TextField("Consumo/Observacion", "", 6, TextField.NUMERIC);
            obs = new TextField ("", "", 2, TextField.NUMERIC);
            mainForm2.append(consumo);
            mainForm2.append (obs);
            StringItem item2 = new StringItem("", "Ingresar", Item.BUTTON);
            item2.setDefaultCommand(CMD_GRABAR);
            item2.setItemCommandListener(this);
            mainForm2.append(item2);
            mainForm2.addCommand (CMD_EXIT);
            mainForm2.addCommand(CMD_SAVE);
            mainForm2.setCommandListener (this);

            objGrabarLectura = new GrabarLectura(this, objCanvas, display);
    }

    public void commandAction (Command c, Displayable s) {
            suministro = objCanvas.getSuministro();
            objGrabarLectura.setLectura(suministro, consumo.getString(), obs.getString());            

            if ((c.getCommandType() == Command.OK) && (c != CMD_SAVE)) {
                objGrabarLectura.grabarLectura();
                resetConsumoObservacion();
                display.setCurrent(mainForm);

            }if (c.getCommandType() == Command.STOP){
                display.setCurrent(mainForm2);

            }if (c.getCommandType() == Command.HELP) {
                objGrabarLectura.grabarLectura();
                resetConsumoObservacion();
                display.setCurrent(mainForm);

            }if (c.getCommandType() == Command.EXIT){
                txtsum.setString("");
                display.setCurrent(mainForm);
            }if (c == CMD_CANCEL) {
                destroyApp (false);
                notifyDestroyed ();
            } if (c == CMD_SAVE) {
                objGrabarLectura.consultaGrabar();
            } // if save
    }

    protected void destroyApp (boolean unconditional) {
    }

    protected void pauseApp () {
    }

    public void commandAction(Command c, Item item) {        
        if (c == CMD_BUSCAR) {
            // No realizar la busqueda.
            if( suministro.trim().length() < 8 )
                return;

            // Busqueda
            int index = rms_orden.buscar(suministro);
            // Suministro No encontrado
            if (index == 0) {
                String msg = "No existe suministro";
                Alert al = new Alert("Atencion");
                al.setString(msg);
                display.setCurrent(al);
            } else { // Suministro Encontrado
                boolean suministroConData = sRMS.tieneData(index);                
                if(!suministroConData) { // Suministro Sin Data
                    display.setCurrent (mainForm2);
                    objCanvas.setCurrentSuministro(index);
                } else {
                    String msg1 = "El suministro ya tiene Lectura";
                    Alert al1 = new Alert("Atencion");
                    al1.setString(msg1);
                    display.setCurrent(al1);
                }
            }
            return;

        } if (c == CMD_GRABAR){
            suministro = objCanvas.getSuministro();
            objGrabarLectura.setLectura(suministro, consumo.getString(), obs.getString());
            objGrabarLectura.consultaGrabar();

         } // end if
   } // End CommandAction

    public void resetConsumoObservacion() {
        consumo.setString("");
        obs.setString("");
    }

    public String getObservacion(int i) {
        String[] observacion = {"OO", "AL", "MI", "MO", "MT", "SM", "MS", "TI", "LC", "PI", "NU",
                                      "MM", "BY", "CM", "ST", "DL", "FR", "NS", "CY", "DI", "DC",
                                      "EX", "ES", "FC", "MA", "MD", "ME", "EA", "MF", "TR", "SE",
                                      "CV", "RI", "LR", "CE", "AY", "PR", "PC", "LD", "MC", "TD"};
        return observacion[i];
    }

}
