/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lecturasuministro;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.*;

/**
 * @author Carlos
 */
public class CargaSuministro extends MIDlet {
    SuministroRMS sRMS = new SuministroRMS("SUMINISTROS");
    private Display display;
    private int m_nsuministros = 0;

    public void startApp() {
        display = Display.getDisplay(this);

        cargarSuministros();

        String msg = "Suministros Cargados : " + m_nsuministros;
        Alert al = new Alert(msg);
        al.setTimeout(Alert.FOREVER);
        display.setCurrent(al); 
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    /**
     * Cargar suministros a leer, 1000 aprox.
     */
    public boolean cargarSuministros() {
        // Estos son los suministros que van a ser leidos.
        
        String m_linea = "";
        String m_temp = "";
        int len = 0;
        TextFile txt = new TextFile("file:///SDCard//solosuministros.txt");
        int lineas = txt.numLineas() - 1;
        // Numero de Suministros.
        m_nsuministros = lineas;
        for(int i=1; i<=lineas; i++){
            m_linea = txt.readLine(i);
            // System.out.println(i + " : " + m_linea.substring(0, 10));
            m_temp = m_linea.trim();
            len = m_temp.length();
            if(len < 10) {
            } else {
                sRMS.addSuministro(m_temp);
            }
        }
        			// Alerta hasta que oprima boton
        
        return true;
    }
}
