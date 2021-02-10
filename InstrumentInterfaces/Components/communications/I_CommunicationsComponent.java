/**
 * 
 */
package communications;

import common.I_InstrumentComponent;

/**
 * @author david
 * Esta interface es la interface que deben implementar todos las clases que quieran
 * modelar el comportamiento de un tipico radioButtonGroup pero utilizando components 
 * en lugar de radiobuttons. Puede ser util en el caso de, por ejemplo, un componente 
 * formado por un Array de varios que cumplen el mismo objetivo pero donde solo uno
 * de ellos puede ser habilitado. Ejemplo claro, el módulo de comunicaciones de un keithley 
 * que tiene varios componentes para la comunicacion con una computadora: RS232, GPIB, LAN y donde
 * solo uno de ellos es habilitado para dicha comunicacion.
 */
public interface I_CommunicationsComponent{
	
	void addPort(String type);
	void deletePort(String type);
	I_InstrumentComponent getPort(String type);
	I_InstrumentComponent getSelectedPort();
}
