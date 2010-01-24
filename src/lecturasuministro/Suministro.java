
package lecturasuministro;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

/**
 *
 * @author utcl95
 */
public class Suministro extends MIDlet implements CommandListener, ItemCommandListener {
    // Constante que HABILITA, DESHABILITA los test, solo permite realizar los test, no
    // ejecuta parte alguna del programa.
    private static final boolean U_TEST = false;
    private static final Command CMD_BACK = new Command ("Back", Command.BACK, 1);
    private static final Command CMD_PRESS = new Command ("Buscar", Command.ITEM, 1);
    private static final Command CMD_CANCEL = new Command ("Cancelar", Command.CANCEL, 1);

    // Actual Elemento en pantalla (Suministro)
    private int currentItem = 1;
    private Display display;
    //private FormSuministro fs = null;
    private LeerConsumo lectura = null;
    //variables para alerta
    private Alert yesNoAlert;
    private Command softKey1;
    private Command softKey2;
    private boolean status;
    private String sumCanvas;
    private canvasForm2 cf;

    protected void startApp () {
        // Modificar su valor en la declaracion para la realizacion de test.
        if(U_TEST) {
            testSuministroRMS testRMS = new testSuministroRMS();
            testRMS.doTest();

            testConfigData testCD = new testConfigData();
            testCD.doTest();
        }

        display = Display.getDisplay(this);
        // Leer Suministro.

        //fs = new FormSuministro("Lectura x Zona");
        
        // Probar con un formulario en un canvas.
        cf = new canvasForm2(this);
        cf.addCommand(CMD_BACK);
        // Verificar q el actual este sin data, sino avanza al siguiente(s)
        //currentItem = siguienteSinData();
        //fs.setCurrentSuministro(currentItem);
        cf.setCommandListener(this);
        cf.setCurrentSuministro(currentItem);
        
//        StringItem item = new StringItem("", "Atras", Item.BUTTON);
//        item.setDefaultCommand(CMD_PRESS3);
//        item.setItemCommandListener(this);
//        fs.append(item);
//
//        StringItem item1 = new StringItem("", "Siguiente", Item.BUTTON);
//        item1.setDefaultCommand(CMD_PRESS4);
//        item1.setItemCommandListener(this);
//        fs.append(item1);
//
//        StringItem item2 = new StringItem("", "Lectura", Item.BUTTON);
//        item2.setDefaultCommand(CMD_PRESS5);
//        item2.setItemCommandListener(this);
//        fs.append(item2);
//
//        fs.setCommandListener(this);
        display.setCurrent(cf);

    }

    public void buscarSuministro(String sumActual){
        sumCanvas  = sumActual;

        lectura = new LeerConsumo(sumCanvas, this);
        lectura.addCommand(CMD_CANCEL);
        lectura.setCommandListener(this);
        display.setCurrent(lectura);
    }

    public void commandAction (Command c, Item item) {
//        String lect = cf.getSuministro();
//        if (c == CMD_PRESS) {
//            lectura = new LeerConsumo(lect, this);
//            display.setCurrent(lectura);
//
//        }
    }

    public void commandAction (Command c, Displayable d) {
    status = c.getCommandType() == Command.OK;

            if (c.getCommandType() == Command.OK) {
                cf.doNext();
                display.setCurrent(cf);
                //lectura.datosConsumo();
                }else if (c.getCommandType() == Command.BACK) {
                        destroyApp (false);
                        notifyDestroyed ();


            }  else if (c.getCommandType() == Command.CANCEL){
                        display.setCurrent(cf);
            }else if (c.getCommandType() == Command.STOP){
                        display.setCurrent(lectura);
            }
    }

    public void mostrarMensaje(String m, int lectura_actual){
        String mns = m;

        if(mns.equals("d")){
            String msg = "Lectura correcta";
            Alert al = new Alert(msg);
            display.setCurrent(al);
        }else{
            yesNoAlert = new Alert("Atencion");
            yesNoAlert.setString("Consumo incorrecto. Desea guardar consumo : " + lectura_actual);
            softKey1 = new Command("No", Command.STOP, 1);
            softKey2 = new Command("Yes", Command.OK, 1);
            yesNoAlert.addCommand(softKey1);
            yesNoAlert.addCommand(softKey2);
            yesNoAlert.setCommandListener(this);
            display.setCurrent(yesNoAlert);
            status = false;
        }
    }

    public int lAnterior(){
        return cf.getAnterior();
    }

    public int lPromedio(){
        return cf.getPromedio();
    }
    
    // Dibuja el Canvas despues de grabar.
    public void repaintCanvasAfterSave() {
        cf.doNext();
        display.setCurrent(cf);
    }

    /**
     * Signals the MIDlet to terminate and enter the Destroyed state.
     */
    protected void destroyApp (boolean unconditional) {
    }

    /**
     * Signals the MIDlet to stop and enter the Paused state.
     */
    protected void pauseApp () {
    }

    

}
