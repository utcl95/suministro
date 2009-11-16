/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lecturasuministro;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.midlet.*;

/**
 * @author utcl95
 */
public class EnviarLectura extends MIDlet {
    public void startApp() {

    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void sendData() {
        byte[] data = null;
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            DataOutputStream dout = new DataOutputStream(bout);
            // Reemplazar por Suministros de RMS.
            dout.writeUTF("1111111111");
            dout.writeUTF("2222222222");
            dout.close();
            data = bout.toByteArray();

            // Conexion al Servidor.
            HttpConnection c = (HttpConnection) Connector.open("http://www.utcl95.com/elc/sumi.php");
            c.setRequestMethod(HttpConnection.POST);
            c.setRequestProperty("Content-Length", Integer.toString(data.length));
            OutputStream sending = c.openOutputStream();
            sending.write(data);
            sending.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
