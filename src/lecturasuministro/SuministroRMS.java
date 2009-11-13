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
 * Uso:
 *
 * addSuministro, devuelve true o false, indicando el exito al ser añadido.
 * addSuministro("123456");
 *
 * searchSuministro, devuelve un int, indicando la posicion del suministro o
 * 0 en caso no sea encontrado.
 * searchSuministro("123456");
 *
 * showSuministro, muestra un suministro ubicado en la posicion index.
 * showSuministro(4)
 *
 * setSuministro, cambia el consumo de acuerdo al suministro y al index
 * Ejemplo:
 * 
 * ssuministro = "12345678";
 * consumo    = "563251"
 * index = searchSuministro(ssuministro)
 * if(setSuministro(index, ssuministro, consumo))
 *     // suministro cambiado ok.
 * 
 * setSuministro(index, ssuministro, sconsumo)
 */
public class SuministroRMS {
    static RecordStore m_rs     = null;
    static String      m_name   = "";

    SuministroRMS(String name) {
        m_name = name;
        try {
            m_rs = RecordStore.openRecordStore(m_name, true);
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Añadir Suministro al RMS.
     */
    public boolean addSuministro(String asuministro) {
        ByteArrayOutputStream   bout = new ByteArrayOutputStream();
        DataOutputStream        dout = new DataOutputStream(bout);
        try {
            dout.writeUTF(asuministro); // Suministro
            dout.writeUTF("000000");    // Consumo a cero al inicio.
            dout.close();
            byte[] data = bout.toByteArray();
            m_rs.addRecord(data, 0, data.length); // Añade el registro.
            return true;
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            return false;
        }
    }

    public int searchSuministro(String ssuministro) {
        int nextID = SuministroRMS.recordCount();
        int i=1;
        
        while(i<=nextID) {
            if(compareSuministro(ssuministro, i))
                return i;
            ++i;
        }
        return 0;
    }

    private boolean compareSuministro(String csuministro, int index) {
        ByteArrayInputStream bin = null;
        DataInputStream din = null;
        try {
            byte[] data = m_rs.getRecord(index);

            bin = new ByteArrayInputStream(data);
            din = new DataInputStream(bin);

            String msuministro  = din.readUTF();
            // String mlectura     = din.readUTF();
            din.close();
            if(csuministro.equals(msuministro))
                return true;
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean setSuministro(int index, String ssuministro, String sconsumo) {
        ByteArrayOutputStream   bout = new ByteArrayOutputStream();
        DataOutputStream        dout = new DataOutputStream(bout);
        try {
            dout.writeUTF(ssuministro); // Suministro
            dout.writeUTF(sconsumo);    // Consumo a cero al inicio.
            dout.close();
            byte[] data = bout.toByteArray();
            m_rs.setRecord(index, data, index, index);
            m_rs.setRecord(index, data, 0, data.length);
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            return false;
        }
    }

    public void showRMS() {
        int nextID = SuministroRMS.recordCount();

        for(int i=1; i<=nextID; i++) {
            this.showSuministro(i);
        }

        try {
            m_rs.closeRecordStore();
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
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
            din.close();
            System.out.println(msuministro);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
    }

    public static int recordCount()  {
      int count = 0;
      try  {
         count = m_rs.getNumRecords();
      }
      catch (Exception e) {
         System.out.println(e);
         e.printStackTrace();
      }
      return count;
   }
    
} // end class
