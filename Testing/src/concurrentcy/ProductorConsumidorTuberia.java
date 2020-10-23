package concurrentcy;

public class ProductorConsumidorTuberia {

	public static void main(String args[]) {

        Tuberia t = new Tuberia();          // creación de la tubería
        Productor p = new Productor(t,"p","ABCDEFGHIJKLMNOPQRSTUVWXYZ");     // creación del productor, recibe la tubería como parámetro
        Productor p2 = new Productor(t,"p2","1234567890");     // creación del productor2, recibe la tubería como parámetro
        Consumidor c = new Consumidor(t,"c");   // creación del consumidor, recibe la tubería como parámetro
        Consumidor c2 = new Consumidor(t,"c2");   // creación del consumidor 2, recibe la tubería como parámetro

        p.start();      // se lanza el hilo productor
        p2.start();      // se lanza el hilo productor2
        c.start();      // se lanza el hilo consumidor
        c2.start();      // se lanza el hilo consumidor2
    }

}
