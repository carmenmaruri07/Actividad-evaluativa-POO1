import java.util.Scanner;

class Servidor {
    String nombre;
    String origen;
    String critico;
    int intentosTotales;
    int intentosFallidos;

    // Aqui se comprueba que todos los datos introducidos sean correctos.
    boolean datosValidos() {
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }

        if (origen == null ||
            (!origen.trim().equalsIgnoreCase("interno") &&
             !origen.trim().equalsIgnoreCase("externo"))) {
            return false;
        }

        if (critico == null ||
            (!critico.trim().equalsIgnoreCase("si") &&
             !critico.trim().equalsIgnoreCase("no"))) {
            return false;
        }

        if (intentosTotales <= 0) {
            return false;
        }

        if (intentosFallidos < 0 || intentosFallidos > intentosTotales) {
            return false;
        }

        return true;
    }

    // Luego se clasifica el estado del servidor siguiendo el orden de prioridad.
    String evaluarEstado() {
        if (!datosValidos()) {
            return "DATOS INVALIDOS";
        }

        String origenNormalizado = origen.trim();
        String criticoNormalizado = critico.trim();

        if (intentosFallidos >= 5 ||
            (criticoNormalizado.equalsIgnoreCase("si") &&
             origenNormalizado.equalsIgnoreCase("externo") &&
             intentosFallidos >= 3)) {
            return "BLOQUEADO";
        } else if (intentosFallidos == 3 || intentosFallidos == 4 ||
                   (origenNormalizado.equalsIgnoreCase("externo") &&
                    intentosFallidos >= 2)) {
            return "ALERTA";
        } else {
            return "NORMAL";
        }
    }

    // A continuación se calcula el porcentaje únicamente cuando los datos son válidos.
    double porcentajeFallos() {
        return (intentosFallidos * 100.0) / intentosTotales;
    }

    // Y luego se devuelve un número para comparar la prioridad de los servidores.
    int nivelPrioridad() {
        String estado = evaluarEstado();

        if (estado.equals("BLOQUEADO")) {
            return 3;
        } else if (estado.equals("ALERTA")) {
            return 2;
        } else if (estado.equals("NORMAL")) {
            return 1;
        } else {
            return 0;
        }
    }

    // Muestra el resumen sin calcular porcentajes para datos inválidos.
    void mostrarResumen() {
        if (datosValidos()) {
            System.out.printf("%s | %d/%d | %.1f%% | %s%n",
                    nombre.trim(), intentosFallidos, intentosTotales,
                    porcentajeFallos(), evaluarEstado());
        } else {
            String nombreMostrar = nombre;

            if (nombreMostrar == null || nombreMostrar.trim().isEmpty()) {
                nombreMostrar = "SIN NOMBRE";
            } else {
                nombreMostrar = nombreMostrar.trim();
            }
/* 
Respuestas a las preguntas del ejercicio:
1. Dos objetos de una misma clase pueden tener valores diferentes
   porque cada objeto tiene sus propios atributos.

2. Con el Servidor b = a;, las dos variables hacen referencia al mismo
   objeto. En cambio, con Servidor b = new Servidor(); se crea un
   objeto nuevo e independiente.

3. Es importante comprobar datosValidos() antes de calcular el
   porcentaje para que pueda evitarse hacer divisiones con datos incorrectos
   o con un total de intentos igual a cero.
*/
