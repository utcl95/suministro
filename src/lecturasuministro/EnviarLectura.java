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
import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

/**
 * @author utcl95
 */
public class EnviarLectura extends MIDlet implements CommandListener {
    private final Command CMD_YES   = new Command("Aceptar",   Command.OK,   1);

    private Display m_display;
    private Alert exitAlert;


    public void startApp() {
        m_display = Display.getDisplay(this);
        sendData();
        
        exitAlert = new Alert("Aviso");
        exitAlert.setString("Archivo de Lecturas Generado...");
        exitAlert.setTimeout(Alert.FOREVER);
        exitAlert.addCommand(CMD_YES);

        exitAlert.setCommandListener(this);
        m_display.setCurrent(exitAlert);
    }



    public void sendData() {
        String[] m_record = new String[3];
        String m_linea = "";
        //
        RecordStore m_rs            = null;
        ByteArrayInputStream bin    = null;
        DataInputStream din         = null;
        byte[] data = null;
        String m_rms[] = new String[3];
        //
        char cr = 13;
        try {
            m_rs = RecordStore.openRecordStore("SUMINISTROS", true);
            int numRMS = m_rs.getNumRecords();
            //System.out.println(numRMS);
            //FileConnection connection = (FileConnection) Connector.open("file:///SDCard//lecturas.txt", Connector.WRITE );
            FileConnection connection = (FileConnection) Connector.open("file:///e:/lecturas.txt", Connector.WRITE );
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
                    data = m_rs.getRecord(i);

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
                
                if(!m_rms[1].equals("00000000") || !m_rms[2].equals("00")) {
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

    }

    public void commandAction(Command c, Displayable d) {
        if ((c.getCommandType() == Command.OK) ) {
            destroyApp (false);
            notifyDestroyed ();
        }
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }
}