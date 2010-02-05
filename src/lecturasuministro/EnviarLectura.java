/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lecturasuministro;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.*;

/**
 * @author utcl95
 */
public class EnviarLectura extends MIDlet {
    private Display display;

    public void startApp() {
        display = Display.getDisplay(this);
        sendData();
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void sendData() {
        String[] m_record = new String[3];
        String m_linea = "";
        char cr = 13;
        try {
            SuministroRMS sRMS = new SuministroRMS("SUMINISTROS");
            int numRMS = sRMS.recordCount();
            //System.out.println(numRMS);
            FileConnection connection = (FileConnection) Connector.open("file:///SDCard//lecturas.txt", Connector.WRITE );
            //FileConnection connection = (FileConnection) Connector.open("file:///e:/lecturas.txt", Connector.WRITE );
            connection.create();
            OutputStream out = connection.openOutputStream();
            PrintStream output = new PrintStream(out);
            
            for (int i = 1; i <= numRMS; i++) {
                m_record = sRMS.getRecord(i);
                if(!m_record[1].equals("00000000")) {
                    m_record[0] = m_record[0].substring(0, 8);
                    m_linea =  m_record[0] + "," + m_record[1] + "," +  m_record[2] + cr;
                    output.println(m_linea);
                }
            }
            out.close();
            connection.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Alert al = new Alert("Archivo de Lecturas Generado...");
        al.setTimeout(Alert.FOREVER);
        display.setCurrent(al);
        // Borrar de memoria.
        destroyApp (false);
        notifyDestroyed();
    }
}
