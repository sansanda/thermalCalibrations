package devicesToCalibrate;

import java.util.Iterator;
import java.util.List;

import org.jdom.Element;

import device.Device;
import device.TTC;

public abstract class DevicesToCalibrate {
	private int nDevicesToCalibrate;
    private Device[] devicesToCalibrate;

    public DevicesToCalibrate()throws Exception{
    }
    public DevicesToCalibrate(Device[] _devicesToCalibrate)throws Exception{
        devicesToCalibrate = _devicesToCalibrate;
        nDevicesToCalibrate = calculeNumberOfDevicesToCalibrate(_devicesToCalibrate);
    }
    public Device[] getDevicesToCalibrate(){
        return devicesToCalibrate;
    }
    public int getNDevicesToCalibrate(){
        return nDevicesToCalibrate;
    }
    private int calculeNumberOfDevicesToCalibrate(Device[] _devicesToCalibrate){
        int devicesToCalibrate = 0;
        for (int i=0;i<_devicesToCalibrate.length;i++){
            if (_devicesToCalibrate[i].isSelected())devicesToCalibrate++;
        }
        return devicesToCalibrate;
    }
    public String toString(){
        String res="";
        res = res + "*********DEVICES TO CALIBRATE*************";
        res = res + "\n\n";
        res = res + "Number of devices to calibrate = " + getNDevicesToCalibrate();
        res = res + "\n\n";
        for (int i=0;i<nDevicesToCalibrate;i++){
        	res = res + devicesToCalibrate[i].toString();
        	res = res +"\n";
        }
        res = res + "*********END TTCs TO CALIBRATE*************";
        return res;
    }
}
