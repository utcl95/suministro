package lecturasuministro;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;


public class RMS_Ordenados {
    RecordStore m_rs     = null;
    String      m_name   = "";

    RMS_Ordenados(String name) {
        m_name = name;
    }

    public void setNameRMS(String name) {
        m_name = name;
    }


    public void openRMS() {
        try {
            m_rs = RecordStore.openRecordStore(m_name, true);
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
    }

    public void closeRMS() {
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
        //openRMS();
        try {
            dout.writeUTF(dataSuministro[0]);  // Suministro
            dout.writeUTF(dataSuministro[1]);  // Orden
            dout.close();
            byte[] data = bout.toByteArray();
            m_rs.addRecord(data, 0, data.length); // Añade el registro.
            //closeRMS();
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
     * Devuelve el registro index.
     */
    public String[] getRecord(int index) {
        ByteArrayInputStream bin = null;
        DataInputStream din = null;
        String m_rms[] = new String[2];


        openRMS();
        try {
            byte[] data = m_rs.getRecord(index);

            bin = new ByteArrayInputStream(data);
            din = new DataInputStream(bin);

            m_rms[0] = din.readUTF();
            m_rms[1] = din.readUTF();

            din.close();
            closeRMS();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
        return m_rms;
    }

    public String getSuministro(int index) {
        ByteArrayInputStream bin = null;
        DataInputStream din = null;
        String m_rms[] = new String[2];

        try {
            byte[] data = m_rs.getRecord(index);

            bin = new ByteArrayInputStream(data);
            din = new DataInputStream(bin);

            m_rms[0] = din.readUTF();
            m_rms[1] = din.readUTF();
            din.close();
            return m_rms[0];
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }

        return m_rms[0];
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

    public int buscar(String dato) {
        if(dato.trim().length() < 8)
            return 0;
        openRMS();
        // Dato a buscar.
        int m_dato = Integer.parseInt(dato);
        int m_sumi = 0;
        String m_ssumi = "";
        String so[] = new String[2];
        int inicio = 1;
        int fin = 0;
        System.out.println(m_dato);
        try {
            fin = m_rs.getNumRecords();
        } catch (RecordStoreNotOpenException ex) {
            ex.printStackTrace();
        }

        int pos;
        while (inicio <= fin) {
         pos = (inicio+fin) / 2;

         so = getRecord(pos);
         m_ssumi = so[0];

         m_sumi = Integer.parseInt(m_ssumi);
         System.out.println("Suministro : " + m_sumi);
         if ( m_sumi == m_dato) {
             closeRMS();
             return Integer.parseInt(so[1].trim());
         } else if ( m_sumi < m_dato ) {
            inicio = pos+1;
         } else {
            fin = pos-1;            
         }

        }
        closeRMS();
        return 0;
    }

} // end class