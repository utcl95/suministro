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
import javax.microedition.midlet.*;

/**
 * @author utcl95
 */
public class EnviarLectura extends MIDlet {
    public void startApp() {
        sendData();
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void sendData() {
        String[] m_record = new String[3];
        String m_linea = "";
        try {
            SuministroRMS sRMS = new SuministroRMS("SUMINISTROS");
            int numRMS = sRMS.recordCount();
            //System.out.println(numRMS);
            FileConnection connection = (FileConnection) Connector.open("file:///SDCard//lecturas.txt", Connector.WRITE );
            connection.create();
            OutputStream out = connection.openOutputStream();
            PrintStream output = new PrintStream(out);
            
            for (int i = 1; i <= numRMS; i++) {
                m_record = sRMS.getRecord(i);
                m_record[0] = m_record[0].substring(0, 8);
                m_linea =  m_record[0] + ";" + m_record[1] + ";" +  m_record[2];
                output.println(m_linea);
            }
            out.close();
            connection.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}