/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lecturasuministro;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import javax.microedition.lcdui.*;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;


public class FormCanvas extends CustomItem implements ItemCommandListener {
    private static final Command CMD_EDIT = new Command ("Edit", Command.ITEM, 1);
    private static final int UPPER = 0;
    private static final int IN = 1;
    private static final int LOWER = 2;
    private Display display;
    private int rows = 5;
    private int cols = 3;
    private int dx = 80;
    private int dy = 18;
    private int location = UPPER;
    private int currentX = 0;
    private int currentY = 0;
    private boolean isLastRecord = false;
    private String[][] data = new String[rows][cols];

    // Traversal stuff
    // indicating support of horizontal traversal internal to the CustomItem
    boolean horz;

    // indicating support for vertical traversal internal to the CustomItem.
    boolean vert;


    public int number = 0;
    String m_title = "";
    private String m_suministro;
    private String m_zona;
    private String m_direccion1;
    private String m_direccion2;
    private String m_serie;
    // Suministro Actual
    private String currentSuministro = "";

    // Actual "registro", 1..1000
    private int m_current = 0;


    // Promedio de lectura del suministro
    public int m_promedioLectura = 0;
    public int m_anteriorLectura = 0;

    private int cs = 1;
    private RMS_DataSuministros dataRMS = new RMS_DataSuministros("DATA00");



    public FormCanvas (String title, Display d) {
        super (title);
        display = d;
        setDefaultCommand (CMD_EDIT);
        setItemCommandListener (this);
        int interactionMode = getInteractionModes ();
        horz = ((interactionMode & CustomItem.TRAVERSE_HORIZONTAL) != 0);
        vert = ((interactionMode & CustomItem.TRAVERSE_VERTICAL) != 0);
        setCurrentSuministro(cs);
    }

    protected int getMinContentHeight () {
        return (rows * dy) + 1;
    }

    protected int getMinContentWidth () {
        return (cols * dx) + 1;
    }

    protected int getPrefContentHeight (int width) {
        return (rows * dy) + 1;
    }

    protected int getPrefContentWidth (int height) {
        return (cols * dx) + 1;
    }

    protected void paint (Graphics g, int w, int h) {
        g.setColor(0xffffff);
        g.fillRect(0, 0, w, h);
        g.setColor(0x000000);

        // Suministro y Zona
        g.drawString(m_suministro + "--" + m_zona,   0, 0, Graphics.TOP | Graphics.LEFT);
        // Cliente
        g.drawString(m_direccion1,       0, 20, Graphics.TOP | Graphics.LEFT);
        // Direccion
        g.drawString(m_direccion2,    0, 40, Graphics.TOP | Graphics.LEFT);
        // Serie Suministro
        g.drawString(m_serie,        0, 60, Graphics.TOP | Graphics.LEFT);

   }

    protected boolean traverse (int dir, int viewportWidth, int viewportHeight, int[] visRect_inout) {
        if (horz && vert) {            
          switch (dir) {

               case Canvas.DOWN:
                if (location == UPPER) {
                    location = IN;
                }
                else {
                    if (currentY < (rows - 1)) {
                        location = LOWER;
                        return false;
                    }
                }
                break;

            case Canvas.UP:
                if (location == LOWER) {
                    location = IN;
                }
                else {
                    if (currentY > 0) {
                        currentY--;
                        repaint (currentX * dx, (currentY + 1) * dy, dx, dy);
                        repaint (currentX * dx, currentY * dy, dx, dy);
                    }
                    else {
                        location = UPPER;
                        return false;
                    }
                }
                break;

            case Canvas.LEFT:
                doBack();
                break;

            case Canvas.RIGHT:
            try {
                doNext();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
                break;
            } // end switch
        } // end if
       
        visRect_inout[0] = currentX;
        visRect_inout[1] = currentY;
        visRect_inout[2] = dx;
        visRect_inout[3] = dy;

        return true;
    }

    public void setText (String text) {
        data[currentY][currentX] = text;
        repaint (currentY * dx, currentX * dy, dx, dy);
    }

    public void commandAction (Command c, Item i) {
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

    public void setDireccion1(String sd1) {
        m_direccion1 = sd1;
    }
    
    public void setDireccion2(String sd2) {
        m_direccion2 = sd2;
    }

    public void setSerie(String ss) {
        m_serie = ss;
    }

    public void setCurrentSuministro(int cs) {
            String m_url = "file:///SDCard//suministros.txt";
            //String m_url = "file:///e:/suministros.txt";

            String data1[] = dataRMS.getRecord(cs);
            String dir1;
            String dir2;

            setSuministro(data1[0]);
            // Numero de suministro, como id.
            m_current = cs;

            // Setea el suministro actual
            currentSuministro = data1[0];

            setZona(data1[1]);
            dir2 = data1[3].substring(16, 32).trim();
            dir1 = data1[3].substring(0, 16);

            setDireccion1(dir1);
            setDireccion2(dir2);
            setSerie(data1[4]);
          
            // Promedio de Lectura
            m_promedioLectura = Integer.parseInt(data1[6]);
            // Lectura anterior
            m_anteriorLectura = Integer.parseInt(data1[5].trim());

            // Mostrar los datos.
            repaint();
        }

    public int getPromedio() {
        return this.m_promedioLectura;
    }

    public int getAnterior() {
        return m_anteriorLectura;
    }

    public int getCurrentSuministroPosition() {
        return m_current;
    }

    public void doNext() throws IOException {
        //RMS_Suministro m_rms = new RMS_Suministro("SUMINISTROS");
        //int numeroSuministros = m_rms.recordCount();
        int i = m_current;
        // Agregado
        boolean btieneData = false;
        ByteArrayInputStream bin = null;
        DataInputStream din = null;
        RecordStore rsSuministro     = null;
        byte[] data_binary = null;
        int numeroSuministros = 0;

        try {
            rsSuministro = RecordStore.openRecordStore("SUMINISTROS", true);
            numeroSuministros = rsSuministro.getNumRecords();
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
        

        if(i == numeroSuministros) {
            isLastRecord = true;
        } else {
            m_current = m_current + 1;
            // Begin Tiene data
            bin = null;
            din = null;
            String m_datarms[] = new String[3];
            try {
                data_binary = rsSuministro.getRecord(m_current);

                bin = new ByteArrayInputStream(data_binary);
                din = new DataInputStream(bin);

                m_datarms[0] = din.readUTF();
                m_datarms[1] = din.readUTF();
                m_datarms[2] = din.readUTF();
                din.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (RecordStoreException ex) {
                ex.printStackTrace();
            }

            btieneData = (m_datarms[1].equals("00000000") || m_datarms[2].equals("00")) ? false : true;
            // End
            while(m_current <= numeroSuministros && btieneData) {
                m_current = m_current + 1;
            }
            if(m_current > numeroSuministros) {
                isLastRecord = true;
                return;
            }
            try {
                rsSuministro.closeRecordStore();
            } catch (RecordStoreException ex) {
                ex.printStackTrace();
            }
            rsSuministro = null;
            setCurrentSuministro(m_current);
        }
        
    }

    public void doBack() {
        RMS_Suministro m_rms = new RMS_Suministro("SUMINISTROS");
        int i = m_current;
        if(i == 1) {
        } else {
            m_current = m_current - 1;
            while(m_rms.tieneData(m_current) && m_current > 1) {
                m_current = m_current - 1;
            }
            m_rms = null;
            setCurrentSuministro(m_current);
        }
    }

    public boolean esElUltimoRegistro() {
        return isLastRecord;
    }

    public int siguienteSinData(int i) {
        RMS_Suministro m_rms = new RMS_Suministro("SUMINISTROS");
        int numeroSuministros = m_rms.recordCount();
        int l_siguiente = i;
        if(i == numeroSuministros) {
        } else {
            while(m_rms.tieneData(l_siguiente) && i < numeroSuministros) {
                l_siguiente = l_siguiente + 1;
            }
        }
        m_rms = null;
        return l_siguiente;
    }
}
