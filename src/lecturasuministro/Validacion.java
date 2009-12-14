
package lecturasuministro;

/**
 *
 * @author utcl95
 */
public class Validacion {
    public Validacion() {
    }

    public boolean esValido(int cod_validacion, int lactual, int lanterior, int cons_act, int promedio) {
        boolean bReturn = false;
//        System.out.println("ES VALIDO : " );
//        System.out.println("lactual : " + lactual);
//        System.out.println("lanterior : " + lanterior);
//        System.out.println("cons_actt : " + cons_act);
//        System.out.println("promedio : " + promedio);

        bReturn =   (lactual <= lanterior) ||
                    (lanterior == 0) ||
                    ((cons_act <= 10) && (promedio > 4 * cons_act) && (promedio > 20)) ||
                    ((cons_act > 10) && (cons_act <= 30) && ( promedio >= 2.75 * cons_act || promedio <= 0.4 * cons_act )) ||
                    ((cons_act > 30) && (cons_act <= 50) && ( promedio >= 2.30 * cons_act || promedio <= 0.5 * cons_act )) ||
                    ((cons_act > 50) && (cons_act <= 70) && ( promedio >= 2 * cons_act || promedio <= 0.6 * cons_act )) ||
                    ((cons_act > 70) && (cons_act <= 100) && ( promedio >= 1.8 * cons_act || promedio <= 0.6 * cons_act )) ||
                    ((cons_act > 100) && (cons_act <= 150) && ( promedio >= 1.7 * cons_act || promedio <= 0.6 * cons_act )) ||
                    ((cons_act > 150) && (cons_act <= 200) && ( promedio >= 1.5 * cons_act || promedio <= 0.6 * cons_act )) ||
                    ((cons_act > 200) && (cons_act <= 300) && ( promedio >= 1.5 * cons_act || promedio <= 0.65 * cons_act )) ||
                    ((cons_act > 300) && (cons_act <= 400) && ( promedio >= 1.6 * cons_act || promedio <= 0.7 * cons_act )) ||
                    ((cons_act > 400) && (cons_act <= 500) && ( promedio >= 1.5 * cons_act || promedio <= 0.7 * cons_act )) ||
                    ((cons_act > 500) && (cons_act <= 700) && ( promedio >= 1.4 * cons_act || promedio <= 0.7 * cons_act )) ||
                    ((cons_act > 700) && (cons_act <= 1500) && ( promedio >= 1.3 * cons_act || promedio <= 0.7 * cons_act )) ||
                    ((cons_act > 1500) && (cons_act <= 2500) && ( promedio >= 1.2 * cons_act || promedio <= 0.7 * cons_act )) ||
                    (cons_act > 2500 && ( cons_act < 1.2 * promedio || promedio <= 0.6 * cons_act ));
                
        return !bReturn;
    }
}