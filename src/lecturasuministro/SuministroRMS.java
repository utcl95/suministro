/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lecturasuministro;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

/**
 *
 * @author utcl95
 */
public class SuministroRMS {
    /**
     * Añadir Suministro al RMS.
     */
    public boolean addSuministro(RecordStore rs, String aSuministro) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(bout);
        try {
            dout.writeUTF(aSuministro);
            dout.writeUTF("000000");
            dout.close();
            byte[] data = bout.toByteArray();
            rs.addRecord(data, 0, data.length);
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return true;
    }

    public void showRMS(RecordStore rs) {
        try {
            rs = RecordStore.openRecordStore("myrs", false);
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }

        int nextID = this.recordCount(rs);

        for(int i=1; i<=nextID; i++) {
            this.mostrarSuministro(rs, i);
        }

        try {
            rs.closeRecordStore();
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
    }

    public void mostrarSuministro(RecordStore rs, int index) {
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
