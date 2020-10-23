package concurrentcy;

import java.util.Arrays;

class Tuberia {

    private char buffer[] = new char[6];

    private int siguiente = 0;

    // banderas para saber el estado del buffer
    private boolean estaLlena = false;
    private boolean estaVacia = true;

    // M�todo SINCRONIZADO para retirar letras del buffer
    public synchronized char recuperar() {

        // IMPORTANTE: NO se puede consumir si el buffer est� vac�o
        while (estaVacia == true) {

            try {
                    wait(); // Se sale cuando estaVacia cambia a false

            } catch (InterruptedException e) {

                e.printStackTrace();

            }

        }

        // Decrementa la cuenta, ya que va a consumir una letra

        siguiente--;

        // Comprueba si se retir� la �ltima letra

        if (siguiente == 0)     estaVacia = true;

        // El buffer no puede estar lleno, porque acabamos de consumir
        estaLlena = false;

        notify();  //se notifica al monitor de sincronizaci�n para ejecute el siguiente hilo en espera (que est� en WAIT), productor o consumidor


        // Devuelve la letra al thread consumidor
        return (buffer[siguiente]);
    }


	// M�todo SINCRONIZADO para meter letras por la tuber�a
    public synchronized void introducir(char c) {

        // Espera hasta que haya sitio para otra letra

        while (estaLlena == true) {

            try {
                    wait(); // Se sale cuando estaLlena cambia a false

            } catch (InterruptedException e) {

                e.printStackTrace();

            }

        }

        // A�ade una letra en el primer lugar disponible
        buffer[siguiente] = c;

        // Cambia al siguiente lugar disponible
        siguiente++;

        // Comprueba si el buffer est� lleno
        if (siguiente == 6)     estaLlena = true;

        estaVacia = false; // no puede estar vac�a porque se acaba de meter una letra

        notify();  //se notifica al monitor de sincronizaci�n para ejecute el siguiente hilo en espera (que est� en WAIT), productor o consumidor

        System.out.println(this.toString());
    }
    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Tuberia buffer=" + Arrays.toString(buffer);
	}


}
