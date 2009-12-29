
package lecturasuministro;


/**
 *
 * @author utcl95
 */
public class testConfigData {

    public testConfigData() {}

    public boolean doTest() {
        ConfigData cd = new ConfigData();
        // Seteado el total de suministros a leer en 10.
        cd.setConfigData(10);

        cd.addLeidos();
        cd.showRMS();

        cd.addLeidos();
        cd.showRMS();

        return true;
    }

}
