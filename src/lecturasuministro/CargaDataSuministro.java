/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lecturasuministro;

import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.*;

/**
 * @author Juan
 */
public class CargaDataSuministro extends MIDlet {
    DataSuministros sRMS = new DataSuministros("DATA00");
    RMS_Ordenados rmsOrden = new RMS_Ordenados("ORDENADOS");
    private Display display;
    private int m_nsuministros = 0;
    private String m_namerms = "";

    public void startApp() {
        display = Display.getDisplay(this);

        // Datos del Suministro
        cargarSuministros(); 
        String msg = "Suministros Cargados : " + m_nsuministros;

        // Suministros ordenados.
        cargarSuministrosOrdenados();


        Alert al = new Alert(msg);
        al.setTimeout(Alert.FOREVER);
        display.setCurrent(al);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    /**
     * Cargar suministros a leer, 1500 aprox.
     */
    public boolean cargarSuministros() {
        FileConnection ptr_file = null;
        String m_linea = "";
        StringBuffer sb = new StringBuffer();
        String m_data[] = null;
        InputStream is = null;
        int num_linea = 1;
        int ch;
        int len  = 0;

        // Numero de Suministros.
        m_nsuministros = 0;

        // Abrir el RMS.
        sRMS.openRMS();
        try {
            ptr_file = (FileConnection) Connector.open("file:///SDCard//suministros.txt", Connector.READ);
            //ptr_file = (FileConnection) Connector.open("file:///e:/suministros.txt", Connector.READ);
            is = ptr_file.openInputStream();

            while((ch=is.read()) != -1) {
                if (ch == '\n') {
                    m_linea = sb.toString().trim(); // Linea
                    m_data = split_linea(m_linea);  // Divide la Linea en partes.
                    // Busca el nombre correcto, 100 por RMS.
                    if((num_linea%100) == 0) {
                        sRMS.closeRMS();
                        m_namerms = getNameFile(num_linea);
                        sRMS.setNameRMS(m_namerms);
                        sRMS.openRMS();
                    }
                    sRMS.addSuministro(m_data);     // Añade al RMS.
                    num_linea = num_linea + 1;
                    len = sb.length();
                    sb.delete(0, len);
                } else {
                    sb.append((char)ch);
                }
            }
            m_nsuministros = num_linea;
            ptr_file.close();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        sRMS.closeRMS();
        is = null;
        return true;
    }

    /**
     * Cargar suministros ordenados.
     */
    public boolean cargarSuministrosOrdenados() {
        FileConnection ptr_file = null;
        String m_linea = "";
        StringBuffer sb = new StringBuffer();
        String m_data[] = null;
        InputStream is = null;
        int num_linea = 1;
        int ch;
        int len  = 0;

        // Numero de Suministros.
        m_nsuministros = 0;

        // Abrir el RMS.
        rmsOrden.openRMS();
        
        try {
            ptr_file = (FileConnection) Connector.open("file:///SDCard//suministrosordenados.txt", Connector.READ);
            //ptr_file = (FileConnection) Connector.open("file:///e:/suministrosordenados.txt", Connector.READ);
            is = ptr_file.openInputStream();

            while((ch=is.read()) != -1) {
                if (ch == '\n') {
                    m_linea = sb.toString().trim(); // Linea
                    m_data = split_linea(m_linea);  // Divide la Linea en partes.
                    rmsOrden.addSuministro(m_data);     // Añade al RMS.
                    num_linea = num_linea + 1;
                    len = sb.length();
                    sb.delete(0, len);
                } else {
                    sb.append((char)ch);
                }
            }
            m_nsuministros = num_linea;
            ptr_file.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        rmsOrden.closeRMS();
        is = null;
        return true;
    }

     public String[] split_linea(String original) {
        Vector nodes = new Vector();
        String separator = ",";
        // System.out.println("split start...................");
        // Parse nodes into vector
        int index = original.indexOf(separator);
        while(index>=0) {
            nodes.addElement( original.substring(0, index) );
            original = original.substring(index+separator.length());
            index = original.indexOf(separator);
        }
        // Get the last node
        nodes.addElement( original );

        // Create splitted string array
        String[] result = new String[ nodes.size() ];
        if( nodes.size()>0 ) {
            for(int loop=0; loop<nodes.size(); loop++) {
                result[loop] = (String)nodes.elementAt(loop);
                //System.out.println(result[loop]);
            }
        }
        return result;
    }

    public String getNameFile(int i) {
        String name = "DATA";
        String sufix = "";
        int n = i / 100;
        if((n < 10) && (n >= 0)) {
            sufix = "00" + Integer.toString(n);
            sufix = sufix.substring(1,3);
        } else {
            sufix = Integer.toString(n);
        }
        return (name+sufix);
    }
}