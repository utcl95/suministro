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


public class canvasForm2 extends Canvas {
    public int number = 0;
    String m_title = "";
    private String m_suministro;
    private String m_zona;
    private String m_nombre;
    private String m_direccion;
    private String m_serie;
    // Suministro Actual
    private String currentSuministro = "";

    // Actual "registro", 1..1000
    private int m_current = 0;


    // Promedio de lectura del suministro
    public int m_promedioLectura = 0;
    public int m_anteriorLectura = 0;
    private Suministro ss;

    canvasForm2(){
    }

    canvasForm2(Suministro ss){
        this.ss = ss;
    }

    protected void keyPressed(int keyCode) {
        if (keyCode > 0) {
          System.out.println("keyPressed " + ((char) keyCode));
        } else {
          System.out.println("keyPressed action " + getGameAction(keyCode));
        }
        // Derecha +
        if(getGameAction(keyCode) == 5) m_current++;
        // Izquierda -
        if(getGameAction(keyCode) == 2) m_current--;
        setCurrentSuministro(m_current);
        repaint();
        // Ingresar consumo
        if(getGameAction(keyCode) == FIRE){
            ss.buscarSuministro(m_suministro);}
      }

      public void paint(Graphics g) {
        int w = getWidth();
        int h = getHeight();

        g.setColor(0xffffff);
        g.fillRect(0, 0, w, h);
        g.setColor(0x000000);

        // Suministro y Zona
        g.drawString(m_suministro + " " + m_zona,   0, 0, Graphics.TOP | Graphics.LEFT);
        // Cliente
        g.drawString(m_nombre,          0, 25, Graphics.TOP | Graphics.LEFT);
        // Direccion
        g.drawString(m_direccion,    0, 50, Graphics.TOP | Graphics.LEFT);
        // Serie Suministro
        g.drawString(m_serie,        0, 75, Graphics.TOP | Graphics.LEFT);

      }

      public void setSuministro(String ss) {
            m_suministro = ss;
      }

    public String getSuministro() {
        return currentSuministro;
    }

        public void setZona(String sz) {
            m_zona = sz;
        }

        public void setNombre(String sn) {
            m_nombre = sn;
        }

        public void setDireccion(String sd) {
            int nl = sd.length();
            sd = sd.substring(0, nl-2);
            m_direccion = sd;
        }

        public void setSerie(String ss) {
            m_serie = ss;
        }

        public void setCurrentSuministro(int cs) {
            TextFile txt = new TextFile("file:///SDCard//suministros.txt");
            //TextFile txt = new TextFile("file:///e:/suministros.txt");
            String m_linea = txt.readLine(cs);
            String data[] = txt.split(m_linea);
            System.out.println("jaqui:"+cs);
            setSuministro(data[0]);
            m_current = cs;
            // System.out.println(data[0]);

            // Setea el suministro actual
            currentSuministro = data[0];

            setZona(data[1]);
            setNombre(data[2]);
            setDireccion(data[3]);
            setSerie(data[4]);
            // Mostrar los datos.
            repaint();

            // Promedio de Lectura
            m_promedioLectura = Integer.parseInt(data[7]);
            // Lectura anterior
            m_anteriorLectura = Integer.parseInt(data[6].trim());

            txt = null;
        }
}
