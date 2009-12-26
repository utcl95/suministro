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
    // Suministro Actual
    private String currentSuministro = "";
    
    // Promedio de lectura del suministro
    public int m_promedioLectura = 0;
    public int m_anteriorLectura = 0;
    
    
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

    public String getSuministro() {
        return currentSuministro;
    }

    public void setZona(String sz) {
        m_zona.setText(sz);
    }

    public void setNombre(String sn) {
        m_nombre.setText(sn);
    }

    public void setDireccion(String sd) {
        int nl = sd.length();
        sd = sd.substring(0, nl-2);
        m_direccion.setText(sd);
    }

    public void setSerie(String ss) {
        m_serie.setText(ss);
    }

    public void setCurrentSuministro(int cs) {
        TextFile txt = new TextFile("file:///SDCard//suministros.txt");
        //TextFile txt = new TextFile("file:///e:/suministros.txt");
        String m_linea = txt.readLine(cs);
        String data[] = txt.split(m_linea);

        setSuministro(data[0]);
        // System.out.println(data[0]);
        
        // Setea el suministro actual
        currentSuministro = data[0];

        setZona(data[1]);
        setNombre(data[2]);
        setDireccion(data[3]);
        setSerie(data[4]);
        
        // Promedio de Lectura
        m_promedioLectura = Integer.parseInt(data[7]);
        // Lectura anterior
        m_anteriorLectura = Integer.parseInt(data[6].trim());
        
        txt = null;
    }

    public int getPromedio() {        
        return this.m_promedioLectura;
    }

    public int getAnterior() {
        return m_anteriorLectura;
    }
}
