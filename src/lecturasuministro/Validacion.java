
package lecturasuministro;

/**
 *
 * @author utcl95
 */
public class Validacion {
    private final int LECTURA_VALIDA            = 1;
    private final int LECTURA_NO_VALIDA         = 2;
    private final int PREGUNTAR_GRABAR_LECTURA  = 3;
    private final int OBSERVACION_INCORRECTA    = 4;

    public Validacion() {
    }

    public boolean esValido(int lactual, int lanterior, int cons_act, int promedio) {
        boolean bReturn = false;

        bReturn =   (lactual < lanterior) ||
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

    public int validarConsumoObservacion(int observacion, int lecturaActual, int lecturaAnterior,
                                         int consumoActual, int promedio) {
        // Verificar valores o setearlos adecuadamente.

        System.out.println("Observacion     : " + observacion);
        System.out.println("Lectura Actual  : " + lecturaActual);
        System.out.println("Lectura Anterior: " + lecturaAnterior);
        System.out.println("Consumo Actual  : " + consumoActual);
        System.out.println("Promedio        : " + promedio);
        // Validaciones
        boolean esValido = false;
        boolean consumoEsValido  = esValido(lecturaActual, lecturaAnterior, consumoActual, promedio);
        boolean obsEsValido = (observacion > 0 && observacion <= 40);
        boolean obsEsCero = (observacion == 0);

        if((consumoEsValido && (obsEsValido || obsEsCero)) || (obsEsValido && (lecturaActual == 0) )){
            return LECTURA_VALIDA;
        }

        if((!consumoEsValido && obsEsValido) || (!consumoEsValido && obsEsCero && (lecturaActual != 0))){
             return PREGUNTAR_GRABAR_LECTURA;
        }

        if((lecturaActual==0 && !obsEsValido) || (!consumoEsValido && !obsEsValido) || (lecturaActual == 0 && obsEsCero) ){
             return LECTURA_NO_VALIDA;
        }

        if(consumoEsValido && !obsEsValido && !obsEsCero){
            return OBSERVACION_INCORRECTA;
        }

        return 0;
    }
}