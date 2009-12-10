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
    private static final Command CMD_PRESS2 = new Command ("Ingresar", Command.ITEM, 1);

    private boolean firstTime;
    private Form mainForm;
    private Form mainForm3;
    private Display display2;
    private TextField consumo;
    private TextField txtsum;
    private String suministro;
    private TextField obs;
    

    SuministroRMS sRMS = new SuministroRMS("SUMINISTROS");
    
    public IngresoSum () {
        firstTime = true;
        mainForm = new Form ("Ingreso Suministro");
    }

    protected void startApp () {
        
        
        if (firstTime) {

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

    public void busqedaSum(){
         
        mainForm3 = new Form("Ingresar Lectura");
        consumo = new TextField("Consumo   ", "", 20, TextField.NUMERIC);
        mainForm3.append(new TextField("Suministro", suministro, 20, TextField.UNEDITABLE));
        mainForm3.append(consumo);
        obs = new TextField("Obs", "", 2, TextField.NUMERIC);
        mainForm3.append(obs);
        StringItem item = new StringItem("", "Ingresar", Item.BUTTON);
        item.setDefaultCommand(CMD_PRESS2);
        item.setItemCommandListener(this);
        mainForm3.append(item);
        mainForm3.setCommandListener(this);
        display2.setCurrent (mainForm3);
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
          
           System.out.println("jaqui"+ index + suministro);
           if(index != 0)
            {
               busqedaSum();
            }else{
                String msg = "No existe suministro";
                Alert al = new Alert(msg);
                display2.setCurrent(al);
            }

        }if (c == CMD_PRESS2) {
             if (ingresarConsumo(suministro, consumo.getString(), obs.getString())){

            }

        }
    }

     public boolean ingresarConsumo(String msuministro, String mconsumo, String mobs) {

        int index = sRMS.searchSuministro(msuministro);
        sRMS.setSuministro(index, msuministro, mconsumo, mobs);
        
        return false;
    }


}
