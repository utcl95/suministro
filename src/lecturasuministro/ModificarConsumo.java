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
    private static final Command CMD_BACK   = new Command ("Regresar", Command.BACK, 1);
    private static final Command CMD_BUSCAR = new Command ("Buscar", Command.ITEM, 1);
    private static final Command CMD_EXIT   = new Command ("Salir", Command.EXIT, 1);
    private static final Command CMD_GRABAR = new Command ("Grabar", Command.ITEM, 1);

    private RMS_Ordenados rms_orden     = new RMS_Ordenados("ORDENADOS");
    private RMS_DataSuministros dataRMS = new RMS_DataSuministros("DATA00");
    private RMS_Suministro sRMS         = new RMS_Suministro("SUMINISTROS");

    private GrabarLectura objGrabarLectura = null;
    
    private Form formBuscar;
    private Form formModificar;

    private TextField txtSuministro;
    private TextField txtConsumo;
    private TextField txtObservacion;

    private Display displayModificar;

    private String suministro;

    int m_lecturaAnterior = 0;
    int m_lecturaPromedio = 0;

    String m_lecturaModificar = "";
    String m_observacionModificar = "";

    private int index;
    private int currentIdSuministro = 0;
    private boolean firstTime;
    
    public ModificarConsumo () {
        firstTime = true;
        formBuscar = new Form ("Modificacion...");
    }
    
    public void startApp() {
        if (firstTime) {
            
            displayModificar = Display.getDisplay(this);

            formBuscar.append ("");
            txtSuministro = new TextField ("Suministro", "", 8, TextField.NUMERIC);
            formBuscar.append (txtSuministro);

            StringItem item = new StringItem("", "Buscar", Item.BUTTON);
            item.setDefaultCommand(CMD_BUSCAR);
            item.setItemCommandListener(this);

            formBuscar.append(item);
            formBuscar.setCommandListener (this);
            formBuscar.addCommand (CMD_EXIT);
            firstTime = false;
            
            objGrabarLectura = new GrabarLectura(this, displayModificar);

            displayModificar.setCurrent(formBuscar);
            
        }
    }

    public void commandAction(Command c, Displayable d) {        
        //suministro = txtSuministro.getString();
        if (c.getCommandType() == Command.OK) {
            objGrabarLectura.setLectura(suministro, txtConsumo.getString(), txtObservacion.getString());
            objGrabarLectura.setIndexSuministro(index);
            getLecturaAnteriorPromedio();
            objGrabarLectura.setConsumoAnterior(m_lecturaAnterior);
            objGrabarLectura.setConsumoPromedio(m_lecturaPromedio);
            objGrabarLectura.grabarLectura();
            resetConsumoObservacion();
            // Volver al menu principal
            destroyApp (false);
            notifyDestroyed();
        } else if (c.getCommandType() == Command.STOP) {
            displayModificar.setCurrent(formModificar);
        } else if (c.getCommandType() == Command.BACK) {
            displayModificar.setCurrent(formBuscar);
        } else if (c.getCommandType() == Command.EXIT) {
            destroyApp (false);
            notifyDestroyed ();
        }
    }

    public void commandAction(Command c, Item item) {
        String msgAlert = "";
        if (c == CMD_GRABAR){
            //suministro = txtSuministro.getString();
            objGrabarLectura.setLectura(suministro, txtConsumo.getString(), txtObservacion.getString());
            objGrabarLectura.setIndexSuministro(index);
            getLecturaAnteriorPromedio();
            objGrabarLectura.setConsumoAnterior(m_lecturaAnterior);
            objGrabarLectura.setConsumoPromedio(m_lecturaPromedio);
            objGrabarLectura.consultaGrabar();
            //destroyApp (false);
            //notifyDestroyed ();
        } // end if
        if (c == CMD_BUSCAR) {
            suministro = txtSuministro.getString();
            // Id de Suministro.
            index = rms_orden.buscar(suministro);
            if(index == 0){
                msgAlert = "No existe suministro";
                Alert al1 = new Alert(msgAlert);
                displayModificar.setCurrent(al1);
            } else {
                currentIdSuministro = index;
                boolean data = sRMS.tieneData(index);
                if(data == true){
                    txtSuministro.setString("");
                    getLecturaActualObservacion();
                    createFormModicar(suministro);
                    
                    displayModificar.setCurrent(formModificar);
                } else {
                    msgAlert = "El suministro no tiene consumo";
                    Alert al = new Alert(msgAlert);
                    displayModificar.setCurrent(al);
                }
            }
        } // end CMD_BUSCAR
    }

    public void createFormModicar(String msuministro) {
        formModificar = new Form("Modificar...");
        suministro = msuministro;

        String lectura = new String(m_lecturaModificar);
        txtConsumo = new TextField("Consumo   ", "", 6, TextField.NUMERIC);
        formModificar.append(new TextField("Suministro", suministro, 8, TextField.UNEDITABLE));
        txtConsumo.setString(lectura);
        formModificar.append(txtConsumo);

        String observacion = new String(m_observacionModificar);
        txtObservacion = new TextField("Obs", "", 2, TextField.NUMERIC);
        txtObservacion.setString(observacion);
        formModificar.append(txtObservacion);

        StringItem item = new StringItem("", "Corregir", Item.BUTTON);
        item.setDefaultCommand(CMD_GRABAR);
        item.setItemCommandListener(this);

        formModificar.addCommand(CMD_BACK);
        formModificar.setCommandListener(this);
        
        formModificar.append(item);
    }

    public void getLecturaAnteriorPromedio(){
        String data[] = dataRMS.getRecord(index);
        m_lecturaAnterior = Integer.parseInt(data[5].trim());
        m_lecturaPromedio = Integer.parseInt(data[6]);

    }

    public void getLecturaActualObservacion(){
        String data[] = sRMS.getRecord(index);
        m_lecturaModificar      = data[1].trim();
        m_observacionModificar  = data[2].trim();
    }

    public void resetConsumoObservacion() {
        txtConsumo.setString("");
        txtObservacion.setString("");
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }
}
