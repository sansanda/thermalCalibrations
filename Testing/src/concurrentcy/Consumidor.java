package concurrentcy;

class Consumidor extends Thread {

    private Tuberia tuberia;
    private String name;

    public Consumidor(Tuberia t, String _name) {

        tuberia = t;        // Mantiene una copia propia del objeto compartido
        name = _name;
    }

    public void run() {

        char c;

// Consume 10 letras de la tubería

        for (int i = 0; i < 10; i++) 
        {

            c = tuberia.recuperar();  // recupera una letra desde la tubería a través del método sincronizado RECUPERAR

            // Imprime la letra recuperada
            System.out.println("Recuperada la letra " + c + " por " + name);

            // Espera un poco antes de seguir cogiendo más letras (al azar, como máximo 2000ms )
            try {
                        sleep((int) (Math.random() * 2000));

            } catch (InterruptedException e) {

                e.printStackTrace();
            }

        }

    }

}
