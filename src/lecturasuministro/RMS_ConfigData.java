
package lecturasuministro;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

/**
 * @author utcl95
 *
 */

public class RMS_ConfigData {
    private RecordStore m_rs     = null;
    private String      m_name   = "";
    private int         m_ts     = 0; // Total de Suministros a Leer

    RMS_ConfigData() {
        m_name = "config_data";
    }

    /**
     * Almacenar la cantidad de suministros a leer y cuantos suministros hemos leido
     * hasta el momento en un RMS y no tener q leer el numero de suministros en forma
     * secuencial del archivo txt.
     */
    public boolean setConfigData(int totalSuministros) {
        ByteArrayOutputStream   bout = new ByteArrayOutputStream();
        DataOutputStream        dout = new DataOutputStream(bout);
        int currentSuministro = 0;
        openRMS();

        try {
            dout.writeInt(currentSuministro);
            dout.writeInt(totalSuministros);
            dout.close();
            byte[] data = bout.toByteArray();
            m_rs.addRecord(data, 0, data.length);

            // Numero de Suministros
            m_ts = totalSuministros;

            closeRMS();
            return true;
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            return false;
        }

    }

    private void openRMS() {
        try {
            m_rs = RecordStore.openRecordStore(m_name, true);
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
    }

    private void closeRMS() {
        try {
            m_rs.closeRecordStore();
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
    }


    public int getTotalSuministros() {
        ByteArrayInputStream bin = null;
        DataInputStream din = null;
        int totalSuministros  = 0;
        int currentSuministro = 0;
        openRMS();
        try {
            byte[] data = m_rs.getRecord(1); // Solo existe un registro.

            bin = new ByteArrayInputStream(data);
            din = new DataInputStream(bin);

            currentSuministro  = din.readInt();
            totalSuministros   = din.readInt();
            din.close();
            closeRMS();

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
        return totalSuministros;
    }

    public int getSuministrosLeidos() {
        ByteArrayInputStream bin = null;
        DataInputStream din = null;
        int totalSuministros  = 0;
        int suministrosLeidos = 0;
        openRMS();
        try {
            byte[] data = m_rs.getRecord(1); // Solo existe un registro.

            bin = new ByteArrayInputStream(data);
            din = new DataInputStream(bin);

            suministrosLeidos  = din.readInt();
            totalSuministros   = din.readInt();
            din.close();
            closeRMS();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
        return suministrosLeidos;
    }

    public boolean addLeidos() {
        ByteArrayOutputStream   bout = new ByteArrayOutputStream();
        DataOutputStream        dout = new DataOutputStream(bout);

        // Suministros Leidos hasta el momento y total a leer.
        int currentLeidos = getSuministrosLeidos();
        int totalLeer     = getTotalSuministros();

        openRMS();
        try {
            // Añade en 1, los leidos.
            dout.writeInt((currentLeidos+1));
            dout.writeInt(totalLeer);
            dout.close();
            byte[] data = bout.toByteArray();
            // Grabar
            m_rs.setRecord(1, data, 0, data.length);
            closeRMS();
            return true;
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            return false;
        }
    }

    public void showRMS() {
        int nextID = 1;
        openRMS();
        for(int i=1; i<=nextID; i++) {
            this.showSuministro(i);
        }
        closeRMS();
    }

    private void showSuministro(int index) {
        ByteArrayInputStream bin = null;
        DataInputStream din = null;
        try {
            byte[] data = m_rs.getRecord(index);

            bin = new ByteArrayInputStream(data);
            din = new DataInputStream(bin);

            int leidos = din.readInt();
            int total  = din.readInt();
            din.close();
            System.out.println(leidos + " -- " + total);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
    }

}