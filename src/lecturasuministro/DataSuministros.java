package lecturasuministro;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;


public class DataSuministros {
    RecordStore m_rs     = null;
    String      m_name   = "";

    DataSuministros(String name) {
        m_name = name;
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

    /**
     * Añadir data de suministro al RMS.
     */
    public boolean addSuministro(String[] dataSuministro) {
        ByteArrayOutputStream   bout = new ByteArrayOutputStream();
        DataOutputStream        dout = new DataOutputStream(bout);
        openRMS();
        try {
            dout.writeUTF(dataSuministro[0]);  // Suministro
            dout.writeUTF(dataSuministro[1]);  // Zona
            dout.writeUTF(dataSuministro[2]);  // Nombre
            dout.writeUTF(dataSuministro[3]);  // Direccion
            dout.writeUTF(dataSuministro[4]);  // Serie
            dout.writeUTF(dataSuministro[6]);  // Anterior
            dout.writeUTF(dataSuministro[7]);  // Promedio
            dout.close();
            byte[] data = bout.toByteArray();
            m_rs.addRecord(data, 0, data.length); // Añade el registro.
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

    /**
     * Devuelve el registro index, como un array de 3 elementos:
     * suministro, consumo, obs.
     */
    public String[] getRecord(int index) {
        ByteArrayInputStream bin = null;
        DataInputStream din = null;
        String m_rms[] = new String[7];
        openRMS();
        try {
            byte[] data = m_rs.getRecord(index);

            bin = new ByteArrayInputStream(data);
            din = new DataInputStream(bin);

            m_rms[0] = din.readUTF();
            m_rms[1] = din.readUTF();
            m_rms[2] = din.readUTF();
            m_rms[3] = din.readUTF();
            m_rms[4] = din.readUTF();
            m_rms[5] = din.readUTF();
            m_rms[6] = din.readUTF();
            din.close();
            closeRMS();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
        return m_rms;
    }

    public void showRMS() {
        int nextID = recordCount();
        openRMS();
        for(int i=1; i<=nextID; i++) {
            this.showSuministro(i);
        }
        closeRMS();
    }

    public void showSuministro(int index) {
        ByteArrayInputStream bin = null;
        DataInputStream din = null;
        try {
            byte[] data = m_rs.getRecord(index);

            bin = new ByteArrayInputStream(data);
            din = new DataInputStream(bin);

            String msuministro  = din.readUTF();
            String mlectura     = din.readUTF();
            String mobs         = din.readUTF();
            din.close();
            System.out.println(msuministro + " -- " + mlectura + " -- " + mobs );
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
    }

    public int recordCount()  {
      int count = 0;
      try  {
         openRMS();
         count = m_rs.getNumRecords();
         closeRMS();
      }
      catch (Exception e) {
         System.out.println(e);
         e.printStackTrace();
      }
      return count;
   }

} // end class