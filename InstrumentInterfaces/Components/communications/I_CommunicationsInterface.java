package communications;

/**
 * Interface que define los métodos básicos para el control y el intercambio de información
 * con instrumentos que dispongan de un puerto de comunicaciones compatible
 * El clásico ciclo de vida de toda interface de comunicaciones será:
 * 1. inicializacion
 * 2. apertura
 * inicializacion y apertura pueden cambiar su orden
 * 3. intercambio de datos
 * 4. cierre y liberacion de recursos
 * @author DavidS
 *
 */
public interface I_CommunicationsInterface {
	
	//version 100: First version control annotation. Added initialize method.
	
	public String 	getType() throws Exception;
	public String 	getStandard() throws Exception;
	public String 	getAddress() throws Exception;
	public void 	setAddress(String address) throws Exception;
	public void 	initialize() throws Exception;
	public void 	open() throws Exception;
	public boolean 	isOpened() throws Exception;
	public void 	close() throws Exception;
	public byte[] 	read() throws Exception;
	public void 	write(String data) throws Exception;
	public byte[] 	ask(String query) throws Exception;
	
}
