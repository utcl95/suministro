/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lecturasuministro;

import javax.microedition.midlet.*;

/**
 * @author Carlos
 */
public class CargaSuministro extends MIDlet {
    SuministroRMS sRMS = new SuministroRMS("ELECTRO");

    public void startApp() {
        cargarSuministros();
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    /**
     * Cargar suministros a leer, 1000 aprox.
     */
    public boolean cargarSuministros() {
        // Estos son los suministros que van a ser leidos.
        String[] m_suministros = {"5555", "6666", "7777", "8888"};

        int nElementos = 4;
        for (int i = 0; i < nElementos; ++i) {
            sRMS.addSuministro(m_suministros[i]);
            //System.out.println(m_suministros[i]);
        }
        return true;
    }
}
