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
        String m_linea = "";
        TextFile txt = new TextFile("file:///SDCard//solosuministros.txt");
        int lineas = txt.numLineas();
        for(int i=1; i<=lineas; i++){
            m_linea = txt.readLine(i);
            System.out.println(i + " : " + m_linea);
            sRMS.addSuministro(m_linea);
        }
        return true;
    }
}
