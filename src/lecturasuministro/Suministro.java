
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
        
        // Verifiar q el actual este sin data, sino avanza al siguiente(s)
        currentItem = siguienteSinData();
        fs.setCurrentSuministro(currentItem);

        StringItem item = new StringItem("", "Atras", Item.BUTTON);
        item.setDefaultCommand(CMD_PRESS3);
        item.setItemCommandListener(this);
        fs.append(item);

        StringItem item1 = new StringItem("", "Siguiente", Item.BUTTON);
        item1.setDefaultCommand(CMD_PRESS4);
        item1.setItemCommandListener(this);
        fs.append(item1);

        StringItem item2 = new StringItem("", "Lectura", Item.BUTTON);
        item2.setDefaultCommand(CMD_PRESS5);
        item2.setItemCommandListener(this);
        fs.append(item2);
        //fs.addCommand(CMD_BACK);
        //fs.addCommand(CMD_NEXT);
        fs.setCommandListener(this);
        display.setCurrent(fs);

    }

    public void commandAction (Command c, Item item) {
        String lect = fs.getSuministro();
        if (c == CMD_PRESS3) {
            doBack();
        }if (c == CMD_PRESS4) {
           //System.out.println("jaqui");
           doNext();
        }if (c == CMD_PRESS5) {
            lectura = new LeerConsumo(lect, this);
            display.setCurrent(lectura);
            
        }
    }

    public void commandAction (Command c, Displayable d) {

    }

    public void mostrarMensaje(String m){
        String mns = m;

        if(mns.equals("a")){
            String msg = "Error lectura actual";
            Alert al = new Alert(msg);
            display.setCurrent(al);
        }else if(mns.equals("b")){
            String msg = "Error, no tiene lectura anterior";
            Alert al = new Alert(msg);
            display.setCurrent(al);
        }else if(mns.equals("d")){
            String msg = "Lectura correcta";
            Alert al = new Alert(msg);
            display.setCurrent(al);
        }else{
            String msg = "Fuera de rango";
            Alert al = new Alert(msg);
            display.setCurrent(al);
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

    public void doBack() {
        SuministroRMS m_rms = new SuministroRMS("SUMINISTROS");
        int i = currentItem;
        if(i == 1) {
        } else {
            currentItem = currentItem - 1;
            while(m_rms.tieneData(currentItem) && i > 1) {
                currentItem = currentItem - 1;
            }
            fs.setCurrentSuministro(currentItem);

        }
        m_rms = null;
    }

    public void doNext() {
        SuministroRMS m_rms = new SuministroRMS("SUMINISTROS");
        int numeroSuministros = m_rms.recordCount();
        int i = currentItem;
        if(i == numeroSuministros) {
        } else {
            currentItem = currentItem + 1;
            while(m_rms.tieneData(currentItem) && i <= numeroSuministros) {
                currentItem = currentItem + 1;
            }
            fs.setCurrentSuministro(currentItem);
        }
        m_rms = null;
    }

    public int siguienteSinData() {
        SuministroRMS m_rms = new SuministroRMS("SUMINISTROS");
        int i = currentItem;
        if(i == 1000) {
        } else {            
            while(m_rms.tieneData(currentItem) && i < 1000) {
                System.out.println("No entra");
                currentItem = currentItem + 1;
            }
        }
        m_rms = null;
        return currentItem;
    }

}
