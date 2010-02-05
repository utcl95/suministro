/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lecturasuministro;

import javax.microedition.lcdui.*;

/**
 *
 * @author Carlos
 */
public class SetConsumo extends Form implements CommandListener, ItemCommandListener{

    private static final Command CMD_PRESS2 = new Command ("Press", Command.ITEM, 1);
    private ModificarConsumo ss;
    private TextField consumo;
    private String suministro;
    private TextField obs;
    private int vobs;
    private int lactual;
    private String sumanterior;

    RMS_Suministro sRMS = new RMS_Suministro("SUMINISTROS");
    private String lect;

    SetConsumo(String lect, ModificarConsumo ss) {
        super("Lectura de Consumo");
        this.ss = ss;
        this.lect = lect;
        leerConsumoSuministro(lect);
    }

    public void commandAction (Command c, Item item) {
        Validacion validarSuministro = new Validacion();

        if(obs.getString().equals(""))
            vobs = 0;
        else
            vobs = Integer.parseInt(obs.getString());

        lactual = Integer.parseInt(consumo.getString());
        int lanterior = ss.lAnterior();
        int promedio = ss.lPromedio();
        int cons_act = lactual - lanterior;

        if (c == CMD_PRESS2){

            if(!validarSuministro.esValido(vobs, lactual, lanterior, cons_act, promedio) ) {
              ss.mostrarMensaje("c", lactual);
            }else{
              datosConsumo();
            }
        }       // end if

        validarSuministro = null;
    }

     public void datosConsumo(){

        if (ingresarConsumo(suministro, consumo.getString(), obs.getString())){}

    }

    public void leerConsumoSuministro(String msuministro) {
       suministro = msuministro;
       String lectura = new String(ss.lActual());
       consumo = new TextField("Consumo   ", "", 20, TextField.NUMERIC);
       append(new TextField("Suministro", suministro, 20, TextField.UNEDITABLE));
       consumo.setString(lectura);
       append(consumo);
       obs = new TextField("Obs", "", 2, TextField.NUMERIC);
       append(obs);

       StringItem item = new StringItem("", "Corregir", Item.BUTTON);
       item.setDefaultCommand(CMD_PRESS2);
       item.setItemCommandListener(this);
       append(item);

    }

    public boolean ingresarConsumo(String msuministro, String mconsumo, String mobs) {
        int index = ss.getIdSuministro();
        sRMS.setSuministro(index, msuministro, mconsumo, mobs);
        return false;
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable d) {
    }
}
