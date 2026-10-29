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

        if (intentosFallidos < 0 ||
            intentosFallidos > intentosTotales) {
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
        } else if (intentosFallidos == 3 ||
                   intentosFallidos == 4 ||
                   (origenNormalizado.equalsIgnoreCase("externo") &&
                    intentosFallidos >= 2)) {
            return "ALERTA";
        } else {
            return "NORMAL";
        }
    }

    // Entonces se calcula el porcentaje de intentos fallidos.
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

    // Seguidamente se muestra el resumen y evita calcular el porcentaje con datos inválidos.
    void mostrarResumen() {
        String nombreMostrar = nombre;

        if (nombreMostrar == null || nombreMostrar.trim().isEmpty()) {
            nombreMostrar = "SIN NOMBRE";
        } else {
            nombreMostrar = nombreMostrar.trim();
        }

        if (datosValidos()) {
            System.out.printf("%s | %d/%d | %.1f%% | %s%n",
                    nombreMostrar,
                    intentosFallidos,
                    intentosTotales,
                    porcentajeFallos(),
                    evaluarEstado());
        } else {
            System.out.printf("%s | %d/%d | N/D | %s%n",
                    nombreMostrar,
                    intentosFallidos,
                    intentosTotales,
                    evaluarEstado());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        // Se crea el primer objeto Servidor.
        Servidor servidor1 = new Servidor();

        System.out.println("DATOS DEL PRIMER SERVIDOR");

        System.out.print("Nombre: ");
        servidor1.nombre = teclado.nextLine();

        System.out.print("Origen (interno/externo): ");
        servidor1.origen = teclado.nextLine();

        System.out.print("Critico (si/no): ");
        servidor1.critico = teclado.nextLine();

        System.out.print("Intentos totales: ");
        servidor1.intentosTotales = teclado.nextInt();
        teclado.nextLine();

        System.out.print("Intentos fallidos: ");
        servidor1.intentosFallidos = teclado.nextInt();
        teclado.nextLine();

        // Se crea un segundo objeto independiente.
        Servidor servidor2 = new Servidor();

        System.out.println("\nDATOS DEL SEGUNDO SERVIDOR");

        System.out.print("Nombre: ");
        servidor2.nombre = teclado.nextLine();

        System.out.print("Origen (interno/externo): ");
        servidor2.origen = teclado.nextLine();

        System.out.print("Critico (si/no): ");
        servidor2.critico = teclado.nextLine();

        System.out.print("Intentos totales: ");
        servidor2.intentosTotales = teclado.nextInt();
        teclado.nextLine();

        System.out.print("Intentos fallidos: ");
        servidor2.intentosFallidos = teclado.nextInt();
        teclado.nextLine();

        // Se muestra el reporte de seguridad de ambos servidores.
        System.out.println("\n========== REPORTE DE SEGURIDAD ==========");
        servidor1.mostrarResumen
