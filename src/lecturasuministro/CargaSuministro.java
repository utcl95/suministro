/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lecturasuministro;

import java.io.IOException;
import java.io.InputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
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
        FileConnection ptr_file = null;
        String m_temp = "";
        StringBuffer sb = new StringBuffer();
        InputStream is = null;
        int num = 0;
        int ch;
        int len  = 0;

        // Numero de Suministros.
        m_nsuministros = 0;
        sRMS.openRMS();
        // Abrir el archivo.
        try {
            ptr_file = (FileConnection) Connector.open("file:///SDCard//solosuministros.txt", Connector.READ);
            //ptr_file = (FileConnection) Connector.open("file:///e:/solosuministros.txt", Connector.READ);
            is = ptr_file.openInputStream();            
            
            while((ch=is.read()) != -1) {
                if (ch == '\n') {
                    m_temp = sb.toString().trim();
                    sRMS.addSuministro(m_temp);
                    num = num + 1;
                    //System.out.println(num + " : " + m_temp);
                    len = sb.length();
                    sb.delete(0, len);
                } else {                    
                    sb.append((char)ch);
                }
            }
            
            m_nsuministros = num;

            // Cierra el archivo.
            ptr_file.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        is = null;
        sRMS.closeRMS();
        return true;
    }
}
