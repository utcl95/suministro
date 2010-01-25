/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lecturasuministro;

/**
 *
 * @author Juan
 */
public class tools {
    
    public String getNameFile(int i) {
        String name = "DATA";
        String sufix = "";
        int n = i / 100;
        if((n < 10) && (n >= 0)) {
            sufix = "00" + Integer.toString(n);
            sufix = sufix.substring(1,3);
        } else {
            sufix = Integer.toString(n);
        }
        return (name+sufix);
    }

    public int getDelta(int i) {
        int dd = i % 100;
        if(i<100) return dd;
        else return (dd+1);
    }

}
