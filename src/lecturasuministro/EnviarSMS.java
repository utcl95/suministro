/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lecturasuministro;

import javax.microedition.io.Connector;
import javax.microedition.midlet.*;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;

/**
 * @author utcl95
 */
public class EnviarSMS extends MIDlet {
    public void startApp() {
        boolean exito = false;

        exito = sendSms();

    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }


    public boolean sendSms(){
    boolean result = true;
    try {
        // Numeor a enviar el SMS.
        String number = "64964630552"; // Numero del tigrillo para prueba.
        String smsMensaje = "";

        String addr = "sms://" + number;

        MessageConnection conn = (MessageConnection) Connector.open(addr);
        // Mensaje de texto.
        TextMessage msg =
        (TextMessage)conn.newMessage(MessageConnection.TEXT_MESSAGE);
        // Mensaje de texto.
        smsMensaje = "Hola Tigrillo...";
        //set text
        msg.setPayloadText(smsMensaje);
        // Enviar mensaje.
        conn.send(msg);
        conn.close();
    } catch (SecurityException se) {
        result = false;
    } catch (Exception e) {
        result = false;
    }
    return result;
  }
}
