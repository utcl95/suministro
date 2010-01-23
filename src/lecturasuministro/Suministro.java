
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

    private static final Command CMD_PRESS3 = new Command ("Atras", Command.ITEM, 1);
    private static final Command CMD_PRESS4 = new Command ("Sigte", Command.ITEM, 1);
    private static final Command CMD_PRESS5 = new Command ("Consumo", Command.ITEM, 1);

    // Actual Elemento en pantalla (Suministro)
    private int currentItem = 1;
    private Display display;
    private FormSuministro fs = null;
    private LeerConsumo lectura = null;
    //variables para alerta
    private Alert yesNoAlert;
    private Command softKey1;
    private Command softKey2;
    private boolean status;
    private String sumCanvas;

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

        fs = new FormSuministro("Lectura x Zona");
        
        // Probar con un formulario en un canvas.
        canvasForm2 cf = new canvasForm2(this);
        
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
        lectura.setCommandListener(this);
        display.setCurrent(lectura);
    }

    public void commandAction (Command c, Item item) {
        String lect = fs.getSuministro();
        if (c == CMD_PRESS5) {
            lectura = new LeerConsumo(lect, this);
            display.setCurrent(lectura);
            
        }
    }

    public void commandAction (Command c, Displayable d) {
    status = c.getCommandType() == Command.OK;

        if (c.getCommandType() == Command.OK) {
            display.setCurrent(fs);
            lectura.datosConsumo();

        } else if (c.getCommandType() == Command.BACK) {
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
            softKey1 = new Command("No", Command.BACK, 1);
            softKey2 = new Command("Yes", Command.OK, 1);
            yesNoAlert.addCommand(softKey1);
            yesNoAlert.addCommand(softKey2);
            yesNoAlert.setCommandListener(this);
            display.setCurrent(yesNoAlert);
            status = false;
        }
    }

    public int lAnterior(){
        return fs.getAnterior();
    }

    public int lPromedio(){
        return fs.getPromedio();
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
