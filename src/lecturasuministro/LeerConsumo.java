/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lecturasuministro;

import javax.microedition.lcdui.*;

/**
 * @author Jaqui
 */
public class LeerConsumo extends Form implements CommandListener, ItemCommandListener{

    private static final Command CMD_PRESS2 = new Command ("Press", Command.ITEM, 1);
    private Suministro obj_suministro = null;
    private BusquedaSuministro obj_busquedasuministro = null;

    private TextField consumo;
    private String suministro;
    private TextField obs;
    private int vobs;
    private int lactual;

    RMS_Suministro sRMS = new RMS_Suministro("SUMINISTROS");
    private String lect;
    
    LeerConsumo(String lect, Suministro sumin) {
        super("Lectura de Consumo");
        this.obj_suministro = sumin;
        this.lect = lect;
        leerConsumoSuministro(lect);
    }

    LeerConsumo(String lect, BusquedaSuministro bs) {
        super("Lectura de Consumo");
        this.obj_busquedasuministro = bs;
        this.lect = lect;
        leerConsumoSuministro(lect);
    }

    public void commandAction (Command c, Item item) {
        Validacion validarSuministro = new Validacion();
        int lanterior = 0;
        int promedio = 0;
        
        if(obs.getString().equals(""))
            vobs = 0;
        else
            vobs = Integer.parseInt(obs.getString());
        
        lactual = Integer.parseInt(consumo.getString());

        if(obj_busquedasuministro == null) {
            lanterior = obj_suministro.lAnterior();
            promedio = obj_suministro.lPromedio();

        } else {
            lanterior = obj_busquedasuministro.lAnterior();
            promedio = obj_busquedasuministro.lPromedio();
        }
        int cons_act = lactual - lanterior;
        if (c == CMD_PRESS2){
            
            if(!validarSuministro.esValido(vobs, lactual, lanterior, cons_act, promedio) ) {
                if(obj_busquedasuministro == null)
                    obj_suministro.mostrarMensaje("c", lactual);
                else
                    obj_busquedasuministro.mostrarMensaje("c", lactual);
            }else{
                grabarConsumo();
            }
        }       // end if
        
        validarSuministro = null;
    }

     public void grabarConsumo(){
        int index = 0;
        if(obj_busquedasuministro == null)
            index = obj_suministro.getIdSuministro();
        else
            index = obj_busquedasuministro.getIdSuministro();

        sRMS.setSuministro(index, suministro, consumo.getString(), obs.getString());
        if(obj_busquedasuministro == null)
            obj_suministro.repaintCanvasAfterSave();
        else
            obj_busquedasuministro.repaintCanvasAfterSave();
    }

    public void leerConsumoSuministro(String msuministro) {
       suministro = msuministro;
       consumo = new TextField("Consumo   ", "", 20, TextField.NUMERIC);
       append(new TextField("Suministro", suministro, 20, TextField.UNEDITABLE));
       append(consumo);

       obs = new TextField("Obs", "", 2, TextField.NUMERIC);
       append(obs);
       
       StringItem item = new StringItem("", "Validar", Item.BUTTON);
       item.setDefaultCommand(CMD_PRESS2);
       item.setItemCommandListener(this);
       append(item);
       
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable d) {
    }
}
