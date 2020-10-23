package concurrentcy;

public class ProductorConsumidorTuberia {

	public static void main(String args[]) {

        Tuberia t = new Tuberia();          // creaci�n de la tuber�a
        Productor p = new Productor(t,"p","ABCDEFGHIJKLMNOPQRSTUVWXYZ");     // creaci�n del productor, recibe la tuber�a como par�metro
        Productor p2 = new Productor(t,"p2","1234567890");     // creaci�n del productor2, recibe la tuber�a como par�metro
        Consumidor c = new Consumidor(t,"c");   // creaci�n del consumidor, recibe la tuber�a como par�metro
        Consumidor c2 = new Consumidor(t,"c2");   // creaci�n del consumidor 2, recibe la tuber�a como par�metro

        p.start();      // se lanza el hilo productor
        p2.start();      // se lanza el hilo productor2
        c.start();      // se lanza el hilo consumidor
        c2.start();      // se lanza el hilo consumidor2
    }

}
