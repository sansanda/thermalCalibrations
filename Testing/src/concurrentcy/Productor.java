package concurrentcy;

public class Productor extends Thread {

    private Tuberia tuberia;
    private String name;
    private String alfabeto;

    public Productor(Tuberia t, String _name, String _alfabeto) {           // constructor

            // Mantiene una copia propia del objeto compartido t
            tuberia = t;
            name = _name;
            alfabeto = _alfabeto;
    }

    public void run() {

        char c; // almacenará una letra individual


        // Mete 10 letras en la tubería
        for (int i = 0; i < 10; i++) {

            c = alfabeto.charAt((int) (Math.random() * alfabeto.length())); // letra del alfabeto al azar

            tuberia.introducir(c);

// Imprime un registro con lo añadido

            System.out.println("Introducida la letra " + c + " a la tuberia " + "por "+ name);

// Espera un poco antes de añadir más letras

            try {
                    sleep((int) (Math.random() * 100));

            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }

    }


}
