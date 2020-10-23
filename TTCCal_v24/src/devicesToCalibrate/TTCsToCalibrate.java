package devicesToCalibrate;

import java.util.Iterator;
import java.util.List;

import org.jdom.Element;

import device.Device;
import device.TTC;

public class TTCsToCalibrate extends DevicesToCalibrate{

    public TTCsToCalibrate(Element _measuresConfigurationElement)throws Exception{
        initializeFromElement(_measuresConfigurationElement);
    }
    public TTCsToCalibrate(Device[] _ttcsToCalibrate)throws Exception{
        super(_ttcsToCalibrate);
    }
    /**
     * Title: convertMeasuresConfigurationDataToJDOMElement
     * Description: Método que convierte una instancia de la classe TMeasuresConfiguration
     * 				en un objeto Element de la librería JDOM
     * Data: 09-06-2008
     * @author David Sánchez Sánchez
     * @param 	String _name con el nombre del elemento
     * @return Element
     * @throws Exception
     */
    public Element convertToJDOMElement(String _name){
            org.jdom.Element measuresConfigurationDataDataElement = new org.jdom.Element(_name);
            org.jdom.Element devicesToMeasureDataElement = new org.jdom.Element("TTCsToCalibrate");
            for (int i=0;i<getNDevicesToCalibrate();i++){
                devicesToMeasureDataElement.addContent(((TTC)getDevicesToCalibrate()[i]).convertToJDOMElement("TTC"+i+"Data"));
            }
            measuresConfigurationDataDataElement.addContent(devicesToMeasureDataElement);
            return measuresConfigurationDataDataElement;
    }
    /**
     * Title: initializeFromElement
     * Description: Método que inicializa esta instancia de la classe GeneralProgramDataElement
     * 				a partir de un objeto de la classe Element de JDOM
     * Data: 09-06-2008
     * @author David Sánchez Sánchez
     * @param 	Element _generalProgramDataElement
     * @return none
     * @throws Exception
     */
    public void initializeFromElement(Element _measuresConfigurationElement) throws Exception{
        Element devicesToMeasureDataElement = _measuresConfigurationElement.getChild("TTCsToCalibrate");
        List<Element> DevicesToMeasureDataChildrenList = devicesToMeasureDataElement.getChildren();
        ttcsToCalibrate = new TTC[DevicesToMeasureDataChildrenList.size()];
        Iterator<Element> DevicesToMeasureDataChildrenListIterator = DevicesToMeasureDataChildrenList.iterator();
        for (int i=0;DevicesToMeasureDataChildrenListIterator.hasNext();i++){
            ttcsToCalibrate[i] = new TTC(DevicesToMeasureDataChildrenListIterator.next());
        }
        nTTCsToCalibrate = calculeNumberOfTTCsToCalibrate(ttcsToCalibrate);
    }
    public String toString(){
        return super.toString();
    }
    /**
     * For Test
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
        	TTC[] ttcs = new TTC[10];
        	TTC[] ttcs2;
            for (int i=0;i<10;i++){
            	ttcs[i] = new TTC("ttc"+i,"ttc_d","ttc with b.colombo diode",true,i);;
            }
            TTCsToCalibrate ttcsToCalibrate = new TTCsToCalibrate(ttcs);
            System.out.println(ttcsToCalibrate.toString());

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
