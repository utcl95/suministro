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
          
        } else {
          
        }
        // Derecha +
        if(getGameAction(keyCode) == 5) doNext();
        // Izquierda -
        if(getGameAction(keyCode) == 2) doBack();
        //setCurrentSuministro(m_current);
        //repaint();
        // Ingresar consumo
        if(getGameAction(keyCode) == 6){
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
            
            setSuministro(data[0]);
            m_current = cs;
            

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

        public void doBack() {
        SuministroRMS m_rms = new SuministroRMS("SUMINISTROS");
        int i = m_current;
        if(i == 1) {
        } else {
            m_current = m_current - 1;
            while(m_rms.tieneData(m_current) && i > 1) {
                m_current = m_current - 1;
            }
            m_rms = null;
            setCurrentSuministro(m_current);
        }

    }

    public void doNext() {
        SuministroRMS m_rms = new SuministroRMS("SUMINISTROS");
        int numeroSuministros = m_rms.recordCount();
        int i = m_current;
        if(i == numeroSuministros) {
        } else {
            m_current = m_current + 1;
            while(m_rms.tieneData(m_current) && i <= numeroSuministros) {
                m_current = m_current + 1;
            }
            m_rms = null;
            setCurrentSuministro(m_current);
        }
    }

    public void siguienteSinData() {
        SuministroRMS m_rms = new SuministroRMS("SUMINISTROS");
        int i = m_current;
        if(i == 1000) {
        } else {
            while(m_rms.tieneData(m_current) && i < 1000) {
               
                m_current = m_current + 1;
                
            }
        }
        m_rms = null;
        setCurrentSuministro(1);
        //repaint();
    }

    public int getPromedio() {
        return this.m_promedioLectura;
    }

    public int getAnterior() {
        return m_anteriorLectura;
    }
}