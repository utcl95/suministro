/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lecturasuministro;

import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.StringItem;

/**
 *
 * @author winxp
 */
public class FormSuministro extends Form  {
    String m_title = "";
    private StringItem m_suministro;
    private StringItem m_zona;
    private StringItem m_nombre;
    private StringItem m_direccion;
    private StringItem m_serie;
    
    FormSuministro(String title) {
        super(title);
        // Iniciar todas las etiquetas.
        m_suministro   = new StringItem("Suministro ", "None", Item.PLAIN);
        m_zona         = new StringItem("", "None", Item.PLAIN);
        m_nombre       = new StringItem("", "None", Item.PLAIN);
        m_direccion    = new StringItem("", "None", Item.PLAIN);
        m_serie        = new StringItem("", "None", Item.PLAIN);

        append(m_suministro);
        append(m_zona);
        append(m_nombre);
        append(m_direccion);
        append(m_serie);
        m_title = title;
    }

    public void setSuministro(String ss) {
        m_suministro.setText(ss);
    }

    public void setZona(String sz) {
        m_zona.setText(sz);
    }

    public void setNombre(String sn) {
        m_zona.setText(sn);
    }

    public void setDireccion(String sd) {
        m_zona.setText(sd);
    }

    public void setSerie(String ss) {
        m_zona.setText(ss);
    }



}
