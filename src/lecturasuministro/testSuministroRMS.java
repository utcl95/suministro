
package lecturasuministro;

/**
 *
 * @author utcl95
 */
public class testSuministroRMS {

    SuministroRMS sRMS = new SuministroRMS("ELECTRO_TEST");

    public testSuministroRMS() {
    }

    public void doTest() {
        // Carga los suministros al Record Management System
        cargarSuministro();
        // Muestra los suministros en el RMS, TODOS.
        sRMS.showRMS();

        // Editar un Consumo de un Suministro.
        String m_suministro     = "2222";
        String m_consumo        = "234234";
        // Busqueda de consumo, devuelve su UBICACION, index.
        int index = sRMS.searchSuministro(m_suministro);
        // Cambia el consumo del suministro "m_suministro".
        sRMS.setSuministro(index, m_suministro, m_consumo);
        // Muestra todos los consumos.
        sRMS.showRMS();
    }

    public void cargarSuministro() {
        // Suministros de prueba.
        String[] m_suministros = {"1111", "2222", "3333", "4444"};

        int nElementos = 4;
        for (int i = 0; i < nElementos; ++i) {
            sRMS.addSuministro(m_suministros[i]);
            System.out.println(m_suministros[i]);
        }
    }



}
