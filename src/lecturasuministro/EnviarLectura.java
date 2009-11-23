/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lecturasuministro;

import javax.microedition.midlet.*;

/**
 * @author utcl95
 */
public class EnviarLectura extends MIDlet {
    public void startApp() {
        sendData();
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void sendData() {
        String m_record[] = new String[3];
        // Variables a exportar.
        String t_suministro = "";
        String t_consumo    = "";
        String t_obs        = "";

        SuministroRMS sRMS = new SuministroRMS("SUMINISTROS");
        int numRMS = sRMS.recordCount();
        
        //System.out.println(numRMS);

        for(int i=1; i<=numRMS; i++) {
            m_record = sRMS.getRecord(i);
            System.out.println(m_record[2]);
        }
    }
}
