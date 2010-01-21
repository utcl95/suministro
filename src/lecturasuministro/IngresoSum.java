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
    private Form mainForm2;
    private Display display2;
    private TextField txtsum;
    private String suministro;
    private EnviarLecturaSum lectura = null;
    private Alert yesNoAlert;
    private Command softKey1;
    private Command softKey2;
    private boolean status;
    private FormSuministro fs = null;
    private boolean grabar;
    private canvasForm canvas = null;
    
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
        status = c.getCommandType() == Command.OK;

            if (c.getCommandType() == Command.OK) {
                display2.setCurrent(mainForm);
                lectura.datosConsumo();

            } else if (c.getCommandType() == Command.BACK) {
                display2.setCurrent(lectura);
            }
    }

    protected void destroyApp (boolean unconditional) {
    }

    public void buscarSuministro(){

        display2.setCurrent(lectura);

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
           System.out.println(index);
             if(index != 0){
                fs.setCurrentSuministro(index);
                lectura = new EnviarLecturaSum(suministro, this);
                txtsum.setString("");
                //display2.setCurrent(lectura);
                canvas =  new canvasForm(this);
                canvas.setCurrentSuministro(index);
                display2.setCurrent(canvas);
                //consum = new ingresarConsumo(this);
               
             }else{
                String msg = "No existe suministro";
                Alert al = new Alert(msg);
                display2.setCurrent(al);
             }
        }
    }

   public void mostrarMensaje(String m){
        String mns = m;

        if(mns.equals("d")){
            String msg = "Lectura correcta";
            Alert al = new Alert(msg);
            display2.setCurrent(al);
        }else{
            yesNoAlert = new Alert("Atencion");
            yesNoAlert.setString("Consumo incorrecto. Desea guardar consumo?");
            softKey1 = new Command("No", Command.BACK, 1);
            softKey2 = new Command("Yes", Command.OK, 1);
            yesNoAlert.addCommand(softKey1);
            yesNoAlert.addCommand(softKey2);
            yesNoAlert.setCommandListener(this);
            display2.setCurrent(yesNoAlert);
            status = false;
        }
        
    }
    
    public int lAnterior(){
        return fs.getAnterior();
    }

    public int lPromedio(){
        return fs.getPromedio();
    }
}
