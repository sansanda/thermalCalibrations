package old;

import java.util.List;

import javax.measure.Measurable;
import javax.measure.quantity.Temperature;
import javax.measure.unit.SI;

import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;

public class TemperatureVSNumberSamplerBuffer {
	
	private XYSeries allTemperatureSamples = null;
	private XYSeries lastNTemperatureSamples = null;
	//Last N Temperatures (The window under we can make statistics)
	private XYSeries lastNTemperatureSamplesWindow = null;
	private int nTemperatureSamplesPerWindow = 120; 
	
	
	public TemperatureVSNumberSamplerBuffer(XYSeries _allTemperatureSamples, int _nTemperatureSamples, String bufferName) 
	{
		
		nTemperatureSamplesPerWindow = _nTemperatureSamples;
		allTemperatureSamples = _allTemperatureSamples;
		lastNTemperatureSamples = new XYSeries(bufferName+" last-"+nTemperatureSamplesPerWindow+"-TemperaturesSamples");
		lastNTemperatureSamples.setMaximumItemCount(nTemperatureSamplesPerWindow);
		lastNTemperatureSamplesWindow = new XYSeries(bufferName+" last-"+nTemperatureSamplesPerWindow+"-TemperaturesSamplesWindow");	
		lastNTemperatureSamplesWindow.setMaximumItemCount(nTemperatureSamplesPerWindow);
	}
	/**
	 * Clear the buffer
	 */
	public void clear() {
		allTemperatureSamples.clear();
		lastNTemperatureSamples.clear();
		lastNTemperatureSamplesWindow.clear();
	}
	/**
	 * Get the number of samples of the buffer
	 * @return int with the number of the samples that contains the buffer
	 */
	public int getTemperatureSamplesCount() {
		return allTemperatureSamples.getItemCount();
	}
	/**
	 * Adds a temperature sample to the buffer.
	 * @param timeStamp
	 * @param temperature
	 * @return true if , as a consequence of the temperature sample added, the buffer reaches an itemcount multiple of  nTemperatureSamplesPerWindow. false otherwise. 
	 * @throws CloneNotSupportedException
	 */
	public boolean addTemperatureSample(long timeStamp, Measurable<Temperature> temperature) throws CloneNotSupportedException {
		Double d = new Double(temperature.doubleValue(SI.CELSIUS));
		allTemperatureSamples.add(timeStamp, d);
		lastNTemperatureSamples.add(timeStamp,d);
		if ((allTemperatureSamples.getItemCount()%nTemperatureSamplesPerWindow)==0 && allTemperatureSamples.getItemCount()>0)
		{
			//El buffer contiene un numero de samples igual a un multiplo de nTemperatureSamplesPerWindow y no está vacio
			//Valcamos los ultimos nTemperatureSamplesPerWindow de lastNTemperatureSamples a lastNTemperatureSamplesWindow
			lastNTemperatureSamplesWindow = lastNTemperatureSamples.createCopy(lastNTemperatureSamples.getItemCount()-nTemperatureSamplesPerWindow, lastNTemperatureSamples.getItemCount()-1);
			System.out.println("----------------------------------------->" + lastNTemperatureSamplesWindow.getItemCount());
			return true;
		}
		return false;
	}
	
	
	/**
	 * 
	 * @param axis is the axis where we want to perform the analisys.
	 * @return the mean of the axis values of the allTemperatureSamples.
	 * @throws Exception if the lastNTemperatureSamplesWindow serie is empty
	 */
	public double calculateMeanOfAllTemperatureSamples(String axis) throws Exception {
		return getMeanOfSeriesAxis(allTemperatureSamples,axis);
	}
	/**
	 * 
	 * @param axis is the axis where we want to perform the analisys.
	 * @return the standard deviation of the axis values of the allTemperatureSamples.
	 * @throws Exception if the lastNTemperatureSamplesWindow serie is empty
	 */
	public double calculateStDevOfAllTemperatureSamples(String axis) throws Exception {
		return getStDevOfSeriesAxis(allTemperatureSamples,axis);
	}
	/**
	 * 
	 * @param axis is the axis where we want to perform the analisys.
	 * @return the mean of the axis values of the lastNTemperatureSamples.
	 * @throws Exception if the lastNTemperatureSamplesWindow serie is empty
	 */
	public double calculateMeanOfLastNTemperatureSamples(String axis) throws Exception {
		return getMeanOfSeriesAxis(lastNTemperatureSamples,axis);
	}
	/**
	 * 
	 * @param axis is the axis where we want to perform the analisys.
	 * @return the standard deviation of the axis values of the lastNTemperatureSamples.
	 * @throws Exception if the lastNTemperatureSamplesWindow serie is empty
	 */
	public double calculateStDevOfLastNTemperatureSamples(String axis) throws Exception {
		return getStDevOfSeriesAxis(lastNTemperatureSamples,axis);
	}
	/**
	 * 
	 * @param axis is the axis where we want to perform the analisys.
	 * @return the mean of the axis values of the lastNTemperatureSamplesWindow.
	 * @throws Exception if the lastNTemperatureSamplesWindow serie is empty
	 */
	public double calculateMeanOfLastNTemperatureSamplesWindow(String axis) throws Exception {
		return getMeanOfSeriesAxis(lastNTemperatureSamplesWindow,axis);
	}
	/**
	 * 
	 * @param axis is the axis where we want to perform the analisys.
	 * @return the standard deviation of the axis values of the lastNTemperatureSamplesWindow.
	 * @throws Exception if the lastNTemperatureSamplesWindow serie is empty
	 */
	public double calculateStDevOfLastNTemperatureSamplesWindow(String axis) throws Exception {
		return getStDevOfSeriesAxis(lastNTemperatureSamplesWindow,axis);
	}
	/**
	 * 
	 * @param serie containg the xy values.
	 * @param axis is the axis where we want to perform the analisys.
	 * @return the standard deviation of the axis values of the xy serie.
	 * @throws Exception if the serie is empty
	 */
	private double getStDevOfSeriesAxis(XYSeries serie, String axis) throws Exception{
		return org.jfree.data.statistics.Statistics.getStdDev(getXYSeries_AxisValues(serie,axis));	 
	}
	/**
	 * 
	 * @param serie containg the xy values.
	 * @param axis is the axis where we want to perform the analisys.
	 * @return the mean of the axis values of the xy serie.
	 * @throws Exception if the serie is empty
	 */
	private double getMeanOfSeriesAxis(XYSeries serie, String axis) throws Exception{
		return org.jfree.data.statistics.Statistics.calculateMean(getXYSeries_AxisValues(serie,axis));	 
	}
	/**
	 * 
	 * @param serie containg the xy values.
	 * @param axis is the axis where we want to extract the values.
	 * @return the axis values as Number[]
	 * @throws Exception if the serie is empty
	 */
	private Number[] getXYSeries_AxisValues(XYSeries serie, String axis) throws Exception
	{
		if (serie.isEmpty()) throw new Exception();
		List<XYDataItem> serieXYItems = serie.getItems();
		Number[] serieAxisValues = new Number[serie.getItemCount()];
		int i=0;
		for (XYDataItem t:serieXYItems)
		{
			if (axis.toLowerCase().equals("x")) serieAxisValues[i] = t.getXValue();
			else if (axis.toLowerCase().equals("y")) serieAxisValues[i] = t.getYValue();
			else break;
			i++;
		}
		return serieAxisValues;
	}
	
}
