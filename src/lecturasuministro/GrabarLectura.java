/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lecturasuministro;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;

/**
 *
 * @author Juan
 */
public class GrabarLectura {
    // Para ser usado por la clase Lectura Secuencial
    private LecturaSecuencial m_lecturaSecuencial = null;

    // Canvas de la Clase actual.
    private FormCanvas m_objCanvas = null;

    // Display para mostrar los mensajes.
    private Display m_display;

    // Clase validacion, para todas las validaciones a realizar.
    private Validacion validarLectura = new Validacion();
    
    // Variables a ser usadas para grabar (suministro, consumo, observacion).
    private String m_suministro  = "";
    private int m_consumoActual  = 0;
    private int m_observacion    = 0;

    private Alert yesNoAlert;
    
    private final Command CMD_EXIT  = new Command("Salir", Command.EXIT, 1);
    private final Command CMD_NOT   = new Command("No",    Command.STOP, 1);
    private final Command CMD_YES   = new Command("Yes",   Command.OK,   1);
    private final Command CMD_QUIT  = new Command("Salir", Command.HELP, 1);


    RMS_Suministro sRMS = new RMS_Suministro("SUMINISTROS");

    public GrabarLectura(LecturaSecuencial lecturasecuencial, FormCanvas objCanvas, Display display) {
        m_objCanvas         = objCanvas;
        m_lecturaSecuencial = lecturasecuencial;
        m_display           = display;
    }

    public void setLectura(String suministro, String lectura, String observacion) {
        m_suministro    = suministro;
        m_consumoActual = (lectura.equals("")) ? 0 : (Integer.parseInt(lectura));
        m_observacion   = (observacion.equals("")) ? 0 : (Integer.parseInt(observacion));
    }

    public int getConsumoActual() {
        return m_consumoActual;
    }

    public int getObservacion() {
        return m_observacion;
    }

    public String getSuministro() {
        return m_suministro;
    }

    public void grabarLectura(){
        int index = 0;

        index = m_objCanvas.getCurrentSuministroPosition();
        String consumoActual = Integer.toString(m_consumoActual);
        String mobservacion  = Integer.toString(m_observacion);    

        sRMS.setSuministro(index, m_suministro, consumoActual, mobservacion);
        repaintCanvasAfterSave();
    }

    public void mostrarMensaje(int num_mensaje, int lectura_actual){

        switch(num_mensaje) {
            case 1:
                String msg = "Lectura Correcta: "+lectura_actual+
                        " .Obs: "+m_observacion;
                Alert al = new Alert(msg);
                
                al.addCommand(CMD_QUIT);
                al.setCommandListener(m_lecturaSecuencial);
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

                yesNoAlert.addCommand(CMD_NOT);
                yesNoAlert.addCommand(CMD_YES);
                yesNoAlert.setCommandListener(m_lecturaSecuencial);
                m_display.setCurrent(yesNoAlert);
                break;
             case 4:
                String msg2 = "Observacion incorrecta";
                Alert al2 = new Alert(msg2);
                m_display.setCurrent(al2);
                break;
        } // end case
     } // end Mostrar Mensaje.

    public void consultaGrabar() {
        int lanterior = 0;
        int promedio = 0;

        lanterior = m_objCanvas.getAnterior();
        promedio = m_objCanvas.getPromedio();
        int cons_act = m_consumoActual - lanterior;

        int codigoValidarLectura = validarLectura.validarConsumoObservacion(m_observacion, m_consumoActual, lanterior, cons_act, promedio);

        switch(codigoValidarLectura) {
        case 1:
            grabarLectura();
            m_lecturaSecuencial.resetConsumoObservacion();
            return;
        case 2:
            mostrarMensaje(2, m_consumoActual);
            return;
        case 3:
            mostrarMensaje(3, m_consumoActual);
            return;
        case 4:
            mostrarMensaje(4, m_consumoActual);
            return;
        }   // end case.
    } // End Consulta Grabar

     public void repaintCanvasAfterSave() {
        m_objCanvas.doNext();
    }
}
