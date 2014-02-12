/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lecturasuministro;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Calendar;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

/**
 *
 * @author Juan
 */
public class GrabarLectura {
    // Para ser usado por la clase Lectura Secuencial
    private LecturaSecuencial m_lecturaSecuencial = null;

    // Para ser usado por la clase Lectura Secuencial
    private BusquedaSuministro m_busquedaSuministro = null;

    // Para ser usado por la clase Lectura Secuencial
    private ModificarConsumo m_modificarConsumo = null;

    // Canvas de la Clase actual.
    private FormCanvas m_objCanvas = null;
    private boolean m_ObjSinCanvas = false;

    // Display para mostrar los mensajes.
    private Display m_display;

    // Clase validacion, para todas las validaciones a realizar.
    private Validacion validarLectura = new Validacion();
    
    // Variables a ser usadas para grabar (suministro, consumo, observacion).
    private String m_suministro  = "";
    private int m_consumoActual  = 0;
    private int m_observacion    = 0;

    private int m_consumoAnterior = 0;
    private int m_consumoPromedio = 0;

    // Posicion dentro de toda el RMS.
    private int m_indexSuministro = 0;

    private Alert yesNoAlert;
    
    private final Command CMD_NOT   = new Command("No",    Command.STOP, 1);
    private final Command CMD_YES   = new Command("Yes",   Command.OK,   1);
    
    RMS_Suministro objRmsSuministro = new RMS_Suministro("SUMINISTROS");

    public GrabarLectura() {}
    
    public GrabarLectura(LecturaSecuencial lecturasecuencial, FormCanvas objCanvas, Display display) {
        m_objCanvas         = objCanvas;
        m_lecturaSecuencial = lecturasecuencial;
        m_display           = display;
    }

    public GrabarLectura(BusquedaSuministro busquedasuministro, FormCanvas objCanvas, Display display) {
        m_objCanvas         = objCanvas;
        m_busquedaSuministro= busquedasuministro;
        m_display           = display;
    }

    public GrabarLectura(ModificarConsumo modificarconsumo, Display display) {
        m_modificarConsumo  = modificarconsumo;
        m_display           = display;
        m_ObjSinCanvas = true;
    }

    public void setLectura(String suministro, String lectura, String observacion) {
        m_suministro    = suministro;
        m_consumoActual = (lectura.equals("")) ? 0 : (Integer.parseInt(lectura));
        m_observacion   = (observacion.equals("")) ? 0 : (Integer.parseInt(observacion));
    }

    public void setIndexSuministro(int index) {
        m_indexSuministro = index;
    }

    public void setConsumoAnterior(int consumoanterior) {
        m_consumoAnterior = consumoanterior;
    }

    public void setConsumoPromedio(int consumopromedio) {
        m_consumoPromedio = consumopromedio;
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
        m_indexSuministro = (m_ObjSinCanvas) ? m_indexSuministro : m_objCanvas.getCurrentSuministroPosition();
        String consumoActual = Integer.toString(m_consumoActual);
        String mobservacion  = Integer.toString(m_observacion);    

        objRmsSuministro.setSuministro(m_indexSuministro, m_suministro, consumoActual, mobservacion);
        if(!m_ObjSinCanvas) {
            if(m_lecturaSecuencial == null) {                
                m_busquedaSuministro.repaintCanvasAfterSave();
            } else {
               m_lecturaSecuencial.repaintCanvasAfterSave();               
            }
        }
    }

    public void mostrarMensaje(int num_mensaje, int lectura_actual){
        String m_msg = "";
        switch(num_mensaje) {
            case 1:
                break;
            case 2:
                Alert al1 = new Alert("Consumo/Observacion Incorrecto");
                m_display.setCurrent(al1);
                break;
            case 3:
                yesNoAlert = new Alert("Atencion");
                int delta = m_consumoActual - m_consumoAnterior;
                if( (delta - m_consumoPromedio) > 500 )
                    m_msg = "Consumo Incorrecto: " + lectura_actual+ ". Diferencia : " + (delta-m_consumoPromedio) + ". Desea guardar?";
                else
                    m_msg = "Consumo Incorrecto: " + lectura_actual+". Desea guardar?";

                yesNoAlert.setString(m_msg);

                yesNoAlert.addCommand(CMD_NOT);
                yesNoAlert.addCommand(CMD_YES);

                boolean ingresarModificar = (m_busquedaSuministro == null && m_lecturaSecuencial == null);
                if(ingresarModificar) {
                    yesNoAlert.setCommandListener(m_modificarConsumo);
                } else {
                    if(m_busquedaSuministro == null)
                        yesNoAlert.setCommandListener(m_lecturaSecuencial);
                    else
                        yesNoAlert.setCommandListener(m_busquedaSuministro);
                }
                m_display.setCurrent(yesNoAlert);
                break;
             case 4:
                Alert msgAlert = new Alert("Observacion incorrecta");
                m_display.setCurrent(msgAlert);
                break;
        } // end case
     } // end Mostrar Mensaje.

    public void consultaGrabar() {
        m_consumoAnterior   = (m_ObjSinCanvas)? m_consumoAnterior:m_objCanvas.getAnterior();
        m_consumoPromedio   = (m_ObjSinCanvas)? m_consumoPromedio:m_objCanvas.getPromedio();
        int cons_act = m_consumoActual - m_consumoAnterior;

        int codigoValidarLectura = validarLectura.validarConsumoObservacion(m_observacion, m_consumoActual, m_consumoAnterior, cons_act, m_consumoPromedio);
        switch(codigoValidarLectura) {
        case 1:
            grabarLectura();
            if(m_busquedaSuministro == null)
                m_lecturaSecuencial.resetConsumoObservacion();
            else
                m_busquedaSuministro.resetConsumoObservacion();
            
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
}
