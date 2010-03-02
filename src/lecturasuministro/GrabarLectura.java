/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lecturasuministro;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;

/**
 *
 * @author Juan
 */
public class GrabarLectura {
    //
    private FormCanvas m_objCanvas;
    
    private Display m_display;

    private Object m_parentObject = null;
    
    String m_suministro     = "";
    String m_consumoActual  = "";
    String m_observacion    = "";
    private Alert yesNoAlert;
    
    private final Command CMD_EXIT = new Command ("Salir", Command.EXIT, 1);
    
    private Command softKey1;
    private Command softKey2;
    private Command softKey3;

    RMS_Suministro sRMS = new RMS_Suministro("SUMINISTROS");

    public GrabarLectura(FormCanvas objCanvas) {
        m_objCanvas = objCanvas;
    }

    public void setCurrentDisplay(Display display) {
        m_display = display;
    }

    public void setParentObject(Object obj) {}

    private void Validar() {
        int lanterior = 0;
        int promedio = 0;
        Validacion validarSuministro = new Validacion();
        lanterior = m_objCanvas.getAnterior();
        promedio = m_objCanvas.getPromedio();
    }

    public void grabarConsumo(String suministro, int lactual, int vobs){
        int index = 0;

        index = m_objCanvas.getCurrentSuministroPosition();
        String lactual1 = String.valueOf(lactual);
        String vobs1 = String.valueOf(vobs);
        sRMS.setSuministro(index, suministro, lactual1, vobs1);
        repaintCanvasAfterSave();
    }

     public void mostrarAlerta(){
        Alert al1 = new Alert("Atención");
        al1.setString("Observación Incorrecta.");
        m_display.setCurrent(al1);
     }

     public void mostrarMensaje(int num_mensaje, int lectura_actual){

        switch(num_mensaje) {
            case 1:
                String msg = "Lectura Correcta: "+lectura_actual+
                        " .Obs: "+m_observacion;
                Alert al = new Alert(msg);
                softKey3 = new Command("Salir", Command.HELP, 1);
                al.addCommand(softKey3);
                al.setCommandListener((CommandListener) m_parentObject);
                m_display.setCurrent(al);
                break;
            case 2:
                String msg1 = "Consumo/Observacion Incorrecto";
                Alert al1 = new Alert(msg1);
                m_display.setCurrent(al1);
                break;
            case 3:
                yesNoAlert = new Alert("Atencion");
                yesNoAlert.setString("Consumo Incorrecto: " + lectura_actual+". Desea guardar?");
                softKey1 = new Command("No", Command.STOP, 1);
                softKey2 = new Command("Yes", Command.OK, 1);
                yesNoAlert.addCommand(softKey1);
                yesNoAlert.addCommand(softKey2);
                yesNoAlert.setCommandListener((CommandListener) m_parentObject);
                m_display.setCurrent(yesNoAlert);
                break;
             case 4:
                String msg2 = "Observacion incorrecta";
                Alert al2 = new Alert(msg2);
                m_display.setCurrent(al2);
                break;
        } // end case
     } // end Mostrar Mensaje.

     public void repaintCanvasAfterSave() {
        m_objCanvas.doNext();
    }

}
