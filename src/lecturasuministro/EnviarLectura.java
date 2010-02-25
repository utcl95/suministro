/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lecturasuministro;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.*;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

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
        //
        RecordStore m_rs            = null;
        ByteArrayInputStream bin    = null;
        DataInputStream din         = null;
        String m_rms[] = new String[3];
        //
        char cr = 13;
        try {
            m_rs = RecordStore.openRecordStore("SUMINISTROS", true);
            int numRMS = m_rs.getNumRecords();
            //System.out.println(numRMS);
            FileConnection connection = (FileConnection) Connector.open("file:///SDCard//lecturas.txt", Connector.WRITE );
            //FileConnection connection = (FileConnection) Connector.open("file:///e:/lecturas.txt", Connector.WRITE );
            connection.create();
            OutputStream out = connection.openOutputStream();
            PrintStream output = new PrintStream(out);
            
            for (int i = 1; i <= numRMS; i++) {
                //
                bin = null;
                din = null;
                m_rms[0] = "";
                m_rms[1] = "";
                m_rms[2] = "";
                try {
                    byte[] data = m_rs.getRecord(i);

                    bin = new ByteArrayInputStream(data);
                    din = new DataInputStream(bin);

                    m_rms[0] = din.readUTF();
                    m_rms[1] = din.readUTF();
                    m_rms[2] = din.readUTF();
                    din.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (RecordStoreException ex) {
                    ex.printStackTrace();
                }
                //
                
                if(!m_rms[1].equals("00000000")) {
                    m_rms[0] = m_rms[0].substring(0, 8);
                    m_linea =  m_rms[0] + "," + m_rms[1] + "," +  m_rms[2] + cr;
                    output.println(m_linea);
                }
            }
            m_rs.closeRecordStore();
            connection.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
        System.out.println("Echo");
        Alert al = new Alert("Archivo de Lecturas Generado...");
        //al.setTimeout(Alert.FOREVER);
        display.setCurrent(al);
        // Borrar de memoria.
        destroyApp (false);
        notifyDestroyed();
    }
}
