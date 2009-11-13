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
 * addSuministro(rs, "123456");
 *
 * searchSuministro, devuelve un int, indicando la posicion del suministro o
 * 0 en caso no sea encontrado.
 * searchSuministro(rs, "123456");
 *
 * showSuministro, muestra un suministro ubicado en la posicion index.
 * showSuministro(rs, 4)
 *
 * setSuministro, cambia el consumo de acuerdo al suministro y al index
 * Ejemplo:
 * 
 * ssuministro = "12345678";
 * consumo    = "563251"
 * index = searchSuministro(rs, ssuministro)
 * if(setSuministro(rs, index, ssuministro, consumo))
 *     // suministro cambiado ok.
 * 
 * setSuministro(rs, index, ssuministro, sconsumo)
 */
public class SuministroRMS {
    /**
     * Añadir Suministro al RMS.
     */
    public boolean addSuministro(RecordStore rs, String asuministro) {
        ByteArrayOutputStream   bout = new ByteArrayOutputStream();
        DataOutputStream        dout = new DataOutputStream(bout);
        try {
            dout.writeUTF(asuministro); // Suministro
            dout.writeUTF("000000");    // Consumo a cero al inicio.
            dout.close();
            byte[] data = bout.toByteArray();
            rs.addRecord(data, 0, data.length); // Añade el registro.
            return true;
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            return false;
        }
    }

    public int searchSuministro(RecordStore rs, String ssuministro) {
        int nextID = SuministroRMS.recordCount(rs);
        int i=1;
        
        while(i<=nextID) {
            if(compareSuministro(rs, ssuministro, i))
                return i;
            ++i;
        }
        return 0;
    }

    private boolean compareSuministro(RecordStore rs, String csuministro, int index) {
        ByteArrayInputStream bin = null;
        DataInputStream din = null;
        try {
            byte[] data = rs.getRecord(index);

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

    public boolean setSuministro(RecordStore rs, int index, String ssuministro, String sconsumo) {
        ByteArrayOutputStream   bout = new ByteArrayOutputStream();
        DataOutputStream        dout = new DataOutputStream(bout);
        try {
            dout.writeUTF(ssuministro); // Suministro
            dout.writeUTF(sconsumo);    // Consumo a cero al inicio.
            dout.close();
            byte[] data = bout.toByteArray();
            rs.setRecord(index, data, index, index);
            rs.setRecord(index, data, 0, data.length);
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            return false;
        }
    }

    public void showRMS(RecordStore rs) {
        int nextID = SuministroRMS.recordCount(rs);

        for(int i=1; i<=nextID; i++) {
            this.showSuministro(rs, i);
        }

        try {
            rs.closeRecordStore();
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
    }

    public void showSuministro(RecordStore rs, int index) {
        ByteArrayInputStream bin = null;
        DataInputStream din = null;
        try {
            byte[] data = rs.getRecord(index);

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

    public static int recordCount(RecordStore rs)  {
      int count = 0;
      try  {
         count = rs.getNumRecords();
      }
      catch (Exception e) {
         System.out.println(e);
         e.printStackTrace();
      }
      return count;
   }
    
} // end class
