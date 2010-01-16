/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lecturasuministro;

/**
 *
 * @author Juan
 */
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.StringItem;


public class canvasForm {

class TextCanvas extends Canvas {

    public int number = 0;
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

    protected void keyPressed(int keyCode) {
        if (keyCode > 0) {
          System.out.println("keyPressed " + ((char) keyCode));
        } else {
          System.out.println("keyPressed action " + getGameAction(keyCode));
        }

        if(getGameAction(keyCode) == 5) number = number + 1;
        if(getGameAction(keyCode) == 2) number = number - 1;
        repaint();
      }

  public void paint(Graphics g) {
    int w = getWidth();
    int h = getHeight();

    g.setColor(0xffffff);
    g.fillRect(0, 0, w, h);
    g.setColor(0x000000);

    // Suministro y Zona
    g.drawString("Suministro : ",   0, 0, Graphics.TOP | Graphics.LEFT);
    // Cliente
    g.drawString("Nombre",          0, 25, Graphics.TOP | Graphics.LEFT);
    // Direccion
    g.drawString("Direccion : ",    0, 50, Graphics.TOP | Graphics.LEFT);
    // Serie Suministro
    g.drawString("Serie : ",        0, 75, Graphics.TOP | Graphics.LEFT);

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
}
}