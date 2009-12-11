/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lecturasuministro;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;

/**
 * @author Jaqui
 */
public class IngresoSum extends MIDlet implements CommandListener, ItemCommandListener {
    private Command exitCommand = new Command ("Exit", Command.EXIT, 1);

    private static final Command CMD_PRESS = new Command ("Buscar", Command.ITEM, 1);
    
    private boolean firstTime;
    private Form mainForm;
   
    private Display display2;
 
    private TextField txtsum;
    private String suministro;
    private EnviarLecturaSum lectura = null;

private FormSuministro fs = null;
    
private int currentItem = 1;
    SuministroRMS sRMS = new SuministroRMS("SUMINISTROS");
    
    public IngresoSum () {
        firstTime = true;
        mainForm = new Form ("Ingreso Suministro");
    }

    protected void startApp () {
        
        
        if (firstTime) {
            fs = new FormSuministro("Lectura x Zona");

            
            display2 = Display.getDisplay (this);

            mainForm.append ("BUSQUEDA POR SUMINISTRO");
            txtsum = new TextField ("Suministro", "", 15, TextField.NUMERIC);
            mainForm.append (txtsum);

            StringItem item = new StringItem("", "Buscar", Item.BUTTON);
            item.setDefaultCommand(CMD_PRESS);
            item.setItemCommandListener(this);
            mainForm.append(item);
            
            mainForm.addCommand (exitCommand);
            mainForm.setCommandListener (this);
            firstTime = false;

            display2.setCurrent(mainForm);
        }

        
    }

    public void commandAction (Command c, Displayable s) {
    }

    protected void destroyApp (boolean unconditional) {
    }

    protected void pauseApp () {
    }

    public void commandAction(Command c, Item item) {

        suministro = txtsum.getString();

        if (c == exitCommand) {
            destroyApp (false);
            notifyDestroyed ();
        }if (c == CMD_PRESS) {
            int index = sRMS.searchSuministro(suministro);
          
            fs.setCurrentSuministro(index);
            lectura = new EnviarLecturaSum(suministro, this);
            display2.setCurrent(lectura);

        }

    }

    public int lAnterior(){
        return fs.getAnterior();
    }

    public int lPromedio(){
        return fs.getPromedio();
    }

    public void mostrarMensaje(String m){
        String mns = m;

        if(mns.equals("a")){
            String msg = "Error lectura actual";
            Alert al = new Alert(msg);
            display2.setCurrent(al);
        }else if(mns.equals("b")){
            String msg = "Error, no tiene lectura anterior";
            Alert al = new Alert(msg);
            display2.setCurrent(al);
        }else{
            String msg = "Fuera de rango";
            Alert al = new Alert(msg);
            display2.setCurrent(al);
        }

    }

     public boolean ingresarConsumo(String msuministro, String mconsumo, String mobs) {

        int index = sRMS.searchSuministro(msuministro);
        sRMS.setSuministro(index, msuministro, mconsumo, mobs);        
        return false;
    }


}
