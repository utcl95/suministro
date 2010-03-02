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
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.midlet.*;

/**
 * @author Carlos
 */
public class CargaSuministro extends MIDlet implements CommandListener {
    RMS_Suministro sRMS      = new RMS_Suministro("SUMINISTROS");
    RMS_Ordenados rmsOrden  = new RMS_Ordenados("ORDENADOS");
    RMS_DataSuministros dataRMS = new RMS_DataSuministros("DATA00");
    

    private Display display;
    private Form screen = new Form("Error en Carga...");    
    
    private int m_nsuministros = 0;
    private String m_namerms = "";

    private String urlToFile = "";

    private static final Command exitCommand = new Command ("Salir", Command.EXIT, 1);

    public void startApp() {
        display = Display.getDisplay(this);

        // Un solo lugar para modificar el path a los archivos en el emulador y en el celular.
        //urlToFile = "file:///e:/";
        urlToFile = "file:///SDCard//";

        sRMS.openRMS();
        int numDatos = sRMS.recordCount();
        sRMS.closeRMS();
        

        if(numDatos > 0) {
            String str = "No se puede realizar esta acción, ya existen suministros con lectura...";
            screen.append(str);
            screen.addCommand (exitCommand);
            screen.setCommandListener(this);
            display.setCurrent(screen);
        } else {
            // Carga de Datos de Suministros, Suministros, Suministros Ordenados.
            cargarSuministros();
            cargarDataSuministros();
            cargarSuministrosOrdenados();

            String msg = "Suministros Cargados : " + m_nsuministros;

            Alert al = new Alert(msg);
            al.setTimeout(Alert.FOREVER);
            display.setCurrent(al);
        }
    }

    public void pauseApp() {
    }

    public void commandAction (Command c, Displayable s) {
        if (c.getCommandType() == Command.EXIT) {
            destroyApp (false);
            notifyDestroyed();
        }
    }

    public void destroyApp(boolean unconditional) {
    }
    
    /**
     * Cargar suministros a leer.
     */
    public boolean cargarSuministros() {
        FileConnection ptr_file = null;
        String m_temp = "";

        String urlToSoloSuministros = "";

        StringBuffer sb = new StringBuffer();
        InputStream is = null;
        int num = 0;
        int ch;
        int len  = 0;
        // Numero de Suministros.
        m_nsuministros = 0;
        sRMS.openRMS();
        // Abrir el archivo.
        try {
            urlToSoloSuministros = urlToFile + "solosuministros.txt";
            ptr_file = (FileConnection) Connector.open(urlToSoloSuministros, Connector.READ);
            is = ptr_file.openInputStream();            
            
            while((ch=is.read()) != -1) {
                if (ch == '\n') {
                    m_temp = sb.toString().trim();
                    sRMS.addSuministro(m_temp);
                    num = num + 1;
                    //System.out.println(num + " : " + m_temp);
                    len = sb.length();
                    sb.delete(0, len);
                } else {                    
                    sb.append((char)ch);
                }
            }
            m_temp = sb.toString().trim();
            if(m_temp.length() == 8) {
                num = num + 1;
                sRMS.addSuministro(m_temp);
            }
                        
            m_nsuministros = num;

            // Cierra el archivo.
            ptr_file.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        is = null;
        sRMS.closeRMS();
        sRMS = null;
        return true;
    }

    /**
     * Cargar Datos de Suministros
     */
    public boolean cargarDataSuministros() {
        FileConnection ptr_file = null;
        String m_linea = "";
        StringBuffer sb = new StringBuffer();
        String m_data[] = null;

        String urlToSuministros = "";

        InputStream is = null;
        int num_linea = 1; // Para sincronizar con la busqueda.
        int ch;
        int len  = 0;

        // Numero de Suministros.
        m_nsuministros = 0;

        // Abrir el RMS.
        dataRMS.openRMS();
        try {
            urlToSuministros = urlToFile + "suministros.txt";
            ptr_file = (FileConnection) Connector.open(urlToSuministros, Connector.READ);
            is = ptr_file.openInputStream();

            while((ch=is.read()) != -1) {
                if (ch == '\n') {
                    m_linea = sb.toString().trim(); // Linea
                    m_data = split_linea(m_linea);  // Divide la Linea en partes.
                    // Busca el nombre correcto, 100 por RMS.
                    if((num_linea%100) == 0) {
                        dataRMS.closeRMS();
                        m_namerms = getNameFile(num_linea);
                        dataRMS.setNameRMS(m_namerms);
                        dataRMS.openRMS();
                    }
                    dataRMS.addSuministro(m_data);     // Añade al RMS.
                    num_linea = num_linea + 1;
                    len = sb.length();
                    sb.delete(0, len);
                } else {
                    sb.append((char)ch);
                }
            }
            
            m_linea = sb.toString().trim();
            if(m_linea.length() > 8) {
                num_linea = num_linea + 1;
                m_data = split_linea(m_linea);
                dataRMS.addSuministro(m_data);
            }

            m_nsuministros = num_linea;
            ptr_file.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        dataRMS.closeRMS();
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

        String urlToSuministrosOrdenados = "";

        InputStream is = null;
        int num_linea = 0;
        int ch;
        int len  = 0;

        // Numero de Suministros.
        m_nsuministros = 0;

        // Abrir el RMS.
        rmsOrden.openRMS();

        try {
            urlToSuministrosOrdenados = urlToFile + "suministrosordenados.txt";
            ptr_file = (FileConnection) Connector.open(urlToSuministrosOrdenados, Connector.READ);
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

            m_linea = sb.toString().trim();
            if(m_linea.length() > 8) {
                num_linea = num_linea + 1;
                m_data = split_linea(m_linea);
                rmsOrden.addSuministro(m_data);
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
