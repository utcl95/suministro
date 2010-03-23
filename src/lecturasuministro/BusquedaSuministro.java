/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lecturasuministro;

import java.io.IOException;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;

/**
 * @author Jaqui
 */
public class BusquedaSuministro extends MIDlet implements CommandListener, ItemCommandListener {
    private static final Command CMD_EXIT   = new Command ("Salir", Command.EXIT, 1);
    //private static final Command CMD_GRABAR = new Command ("Press", Command.ITEM, 1);
    private static final Command CMD_BUSCAR = new Command ("Buscar", Command.ITEM, 1);
    private static final Command CMD_CANCEL = new Command ("Cancelar", Command.CANCEL, 1);
    private static final Command CMD_SAVE   = new Command ("Grabar", Command.OK, 1);

    private Form FormBuscar;
    private Form FormCanvas;
    private Display display;

    private TextField txtSuministro;
    private TextField txtConsumo;
    private TextField txtObservacion;
    
    private String m_suministro;

    private FormCanvas objCanvas;

    GrabarLectura objGrabarLectura = null;

    RMS_Ordenados rms_orden = new RMS_Ordenados("ORDENADOS");
    RMS_Suministro sRMS = new RMS_Suministro("SUMINISTROS");

    public BusquedaSuministro () {
        FormBuscar = new Form ("Ingreso Suministro");
    }

    protected void startApp () {
        display = Display.getDisplay (this);
        FormBuscar = new Form ("Busqueda");
        txtSuministro = new TextField ("Suministro", "", 8, TextField.NUMERIC);
        FormBuscar.append (txtSuministro);

        /*
        StringItem btnBuscar = new StringItem("", "Buscar", Item.BUTTON);
        btnBuscar.setDefaultCommand(CMD_BUSCAR);
        btnBuscar.setItemCommandListener(this);
        FormBuscar.append(btnBuscar);
        */
        FormBuscar.addCommand (CMD_BUSCAR);
        FormBuscar.addCommand (CMD_CANCEL);

        FormBuscar.setCommandListener (this);
        display.setCurrent(FormBuscar);

        FormCanvas = new Form ("");
        objCanvas = new FormCanvas ("", Display.getDisplay (this));
        FormCanvas.append (objCanvas);

        txtConsumo      = new TextField("Consumo/Observacion", "", 6, TextField.NUMERIC);
        txtObservacion  = new TextField ("", "", 2, TextField.NUMERIC);

        FormCanvas.append(txtConsumo);
        FormCanvas.append(txtObservacion);

        //StringItem btnIngresar = new StringItem("", "Ingresar", Item.BUTTON);
        //btnIngresar.setDefaultCommand(CMD_GRABAR);
        //btnIngresar.setItemCommandListener(this);
        //FormCanvas.append(btnIngresar);
        
        FormCanvas.addCommand(CMD_EXIT);
        FormCanvas.addCommand(CMD_SAVE);
        FormCanvas.setCommandListener (this);

        objGrabarLectura = new GrabarLectura(this, objCanvas, display);
    }

    public void commandAction (Command c, Displayable s) {
        m_suministro = objCanvas.getSuministro();
        objGrabarLectura.setLectura(m_suministro, txtConsumo.getString(), txtObservacion.getString());

        if ((c.getCommandType() == Command.OK) && (c != CMD_SAVE)) {
            objGrabarLectura.grabarLectura();
            resetConsumoObservacion();
            display.setCurrent(FormCanvas);
        } else if (c.getCommandType() == Command.STOP){
            display.setCurrent(FormCanvas);

        } else if (c.getCommandType() == Command.EXIT){
            txtSuministro.setString("");
            display.setCurrent(FormBuscar);
        } else if (c == CMD_CANCEL) {
            destroyApp (false);
            notifyDestroyed ();
        } else if (c == CMD_SAVE) {
            objGrabarLectura.consultaGrabar();
        } else {
            String msgAlert = "";
            m_suministro = txtSuministro.getString();
            objGrabarLectura.setLectura(m_suministro, txtConsumo.getString(), txtObservacion.getString());

            if (c == CMD_BUSCAR) {
                // No realizar la busqueda.
                if( m_suministro.trim().length() < 8 )
                    return;

                // Busqueda
                int index = rms_orden.buscar(m_suministro);
                // Suministro No encontrado
                if (index == 0) {
                    msgAlert = "No existe suministro";
                    Alert al = new Alert("Atencion");
                    al.setString(msgAlert);
                    display.setCurrent(al);
                } else { // Suministro Encontrado
                    boolean suministroConData = sRMS.tieneData(index);
                    if(!suministroConData) { // Suministro Sin Data
                        display.setCurrent (FormCanvas);
                        objCanvas.setCurrentSuministro(index);
                    } else {
                        msgAlert = "El suministro ya tiene Lectura";
                        Alert al1 = new Alert("Atencion");
                        al1.setString(msgAlert);
                        display.setCurrent(al1);
                    }
                }
                return;

            } // end buscar
        }
    } // end commandAction



    public void commandAction(Command c, Item item) {
        /*
        String msgAlert = "";
        m_suministro = txtSuministro.getString();
        objGrabarLectura.setLectura(m_suministro, txtConsumo.getString(), txtObservacion.getString());
        
        if (c == CMD_BUSCAR) {
            // No realizar la busqueda.
            if( m_suministro.trim().length() < 8 )
                return;

            // Busqueda
            int index = rms_orden.buscar(m_suministro);
            // Suministro No encontrado
            if (index == 0) {
                msgAlert = "No existe suministro";
                Alert al = new Alert("Atencion");
                al.setString(msgAlert);
                display.setCurrent(al);
            } else { // Suministro Encontrado
                boolean suministroConData = sRMS.tieneData(index);                
                if(!suministroConData) { // Suministro Sin Data
                    display.setCurrent (FormCanvas);
                    objCanvas.setCurrentSuministro(index);
                } else {
                    msgAlert = "El suministro ya tiene Lectura";
                    Alert al1 = new Alert("Atencion");
                    al1.setString(msgAlert);
                    display.setCurrent(al1);
                }
            }
            return;

        }
        */
        //if (c == CMD_GRABAR){
          //  m_suministro = objCanvas.getSuministro();
          //  objGrabarLectura.setLectura(m_suministro, txtConsumo.getString(), txtObservacion.getString());
          //  objGrabarLectura.consultaGrabar();

         // } // end if
         
   } // End CommandAction

    public void resetConsumoObservacion() {
        txtConsumo.setString("");
        txtObservacion.setString("");
    }

    public void repaintCanvasAfterSave() {
            objCanvas.doNext();
        if (objCanvas.esElUltimoRegistro()) {
            destroyApp (false);
            notifyDestroyed();
        }
    }

    public String getObservacion(int i) {
        String[] observacion = {"OO", "AL", "MI", "MO", "MT", "SM", "MS", "TI", "LC", "PI", "NU",
                                      "MM", "BY", "CM", "ST", "DL", "FR", "NS", "CY", "DI", "DC",
                                      "EX", "ES", "FC", "MA", "MD", "ME", "EA", "MF", "TR", "SE",
                                      "CV", "RI", "LR", "CE", "AY", "PR", "PC", "LD", "MC", "TD"};
        return observacion[i];
    }

    protected void destroyApp (boolean unconditional) {
    }

    protected void pauseApp () {
    }

}
