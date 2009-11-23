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
    RecordStore m_rs     = null;
    String      m_name   = "";

    SuministroRMS(String name) {
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
     * Añadir Suministro al RMS.
     */
    public boolean addSuministro(String asuministro) {
        ByteArrayOutputStream   bout = new ByteArrayOutputStream();
        DataOutputStream        dout = new DataOutputStream(bout);
        openRMS();
        try {
            dout.writeUTF(asuministro); // Suministro
            dout.writeUTF("00000000");  // Consumo a cero al inicio.
            dout.writeUTF("00");        // Observacon
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

    public int searchSuministro(String ssuministro) {
        openRMS();
        int nextID = recordCount();
        int i=1;
        
        while(i<=nextID) {
            if(compareSuministro(ssuministro, i)) {
                closeRMS();
                return i;
            }
            ++i;
        }
        closeRMS();
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
            if(csuministro.equals(msuministro)) {
                return true;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean setSuministro(int index, String ssuministro, String sconsumo, String sobs) {
        ByteArrayOutputStream   bout = new ByteArrayOutputStream();
        DataOutputStream        dout = new DataOutputStream(bout);
        openRMS();
        try {
            dout.writeUTF(ssuministro); // Suministro
            dout.writeUTF(sconsumo);    // Consumo a cero al inicio.
            dout.writeUTF(sobs);    // Consumo a cero al inicio.

            dout.close();
            byte[] data = bout.toByteArray();
            m_rs.setRecord(index, data, index, index);
            m_rs.setRecord(index, data, 0, data.length);
            closeRMS();
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            return false;
        }
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
            System.out.println(msuministro + " -- " + mlectura);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
    }

    public String[] getRecord(int index) {
        ByteArrayInputStream bin = null;
        DataInputStream din = null;
        String m_rms[] = new String[3];
        openRMS();
        try {
            byte[] data = m_rs.getRecord(index);

            bin = new ByteArrayInputStream(data);
            din = new DataInputStream(bin);

            m_rms[0] = din.readUTF();
            m_rms[1] = din.readUTF();
            m_rms[2] = din.readUTF();
            din.close();
            closeRMS();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }        
        return m_rms;
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

    public int cuentaLista()  {
      int numero = 0;
      int compare = 0;
      ByteArrayInputStream bin = null;
      DataInputStream din = null;

      try  {
        numero = recordCount();
        openRMS();
        for(int i=1; i<=numero; i++) {
            byte[] data = m_rs.getRecord(i);

            bin = new ByteArrayInputStream(data);
            din = new DataInputStream(bin);

            String msuministro  = din.readUTF();
            String mlectura     = din.readUTF();

            din.close();
            if(mlectura.equals("00000000"))
                compare = compare+1;            
        }
        closeRMS();
      }
      catch (Exception e) {
         System.out.println(e);
         e.printStackTrace();
      }
      return compare;
   }



    
} // end class
