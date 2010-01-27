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
 *
 * getRecord, devuelve el registro index, como un array de 3 elementos.
 *
 * public String[] getRecord(int index)
 * 
 */
public class SuministroRMS {
    RecordStore m_rs     = null;
    String      m_name   = "";

    SuministroRMS(String name) {
        m_name = name;
    }

    /**
     * El suministro index, tiene su lectura con datos?
     */
    public boolean tieneData(int index) {
        boolean bReturn = false;
        String data[] = new String[3];
        data = getRecord(index);
        bReturn = (data[1].equals("00000000")) ? false : true;
        // System.out.println("Tiene Data : " + bReturn);
        return bReturn;
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
     * Añadir Suministro al RMS.
     */
    public boolean addSuministro(String asuministro) {
        ByteArrayOutputStream   bout = new ByteArrayOutputStream();
        DataOutputStream        dout = new DataOutputStream(bout);
        //openRMS();
        try {
            dout.writeUTF(asuministro); // Suministro
            dout.writeUTF("00000000");  // Consumo a cero al inicio.
            dout.writeUTF("00");        // Observacion
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

    public int searchSuministro(String ssuministro) {
        openRMS();
        int nextID = recordCount();
        int i=1;
        // Variables de Compare
        ByteArrayInputStream bin = null;
        DataInputStream din = null;
        String msuministro = "";

        // getRecord
        byte[] data = null;
        String m_rms[] = new String[3];

        //System.out.println(nextID+"JAQUI");
        //showRMS();
        while(i<=nextID) {
             // Inicio Compare
            try {
                // Inicio getRecord
                data = m_rs.getRecord(i);
                bin = new ByteArrayInputStream(data);
                din = new DataInputStream(bin);

                m_rms[0] = din.readUTF();
                m_rms[1] = din.readUTF();
                m_rms[2] = din.readUTF();
                din.close();
                // Fin getRecord

                msuministro  = m_rms[0];
                if(ssuministro.equals(msuministro)) {
                    closeRMS();
                    return i;
                }
                bin = null; // Para evitar errores.
                din = null; // Para evitar errores.
                msuministro = "";
                data = null;
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (RecordStoreException ex) {
                ex.printStackTrace();
            }
             // Fin Compare
            ++i;
        } // end while
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
            // System.out.println("RMS");
            // System.out.println(msuministro);
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
        // Añadir "0" al inicio de numeros menores a 9.
        String s = "";
        if(sobs.trim().length() > 0) {
            int n = Integer.parseInt(sobs);
            if( n < 10 && n > 0) {
                s = "00" + Integer.toString(n);
                s = s.substring(1,3);
            }
            sobs = s;
        }
        openRMS();
        try {
            dout.writeUTF(ssuministro); // Suministro
            dout.writeUTF(sconsumo);
            dout.writeUTF(sobs);

            dout.close();
            byte[] data = bout.toByteArray();
            // m_rs.setRecord(index, data, index, index);
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
            System.out.println(msuministro + " -- " + mlectura + " -- " + mobs );
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Devuelve el registro index, como un array de 3 elementos:
     * suministro, consumo, obs.
     */
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