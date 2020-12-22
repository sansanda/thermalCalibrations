package statistics;

import java.util.Vector;

public interface ISamplesBuffer<T,V> {
	/**
	 * Clears the buffer. 
	 */
	public void clear();
	/**
	 * @param sample to add to the buffer. 
	 * @param sampleMultipleNumber. Param that helps to know if the number of samples of the buffer is multiple of sampleMultipleNumber. 
	 * @return true if the number of samples of the buffer is multiple of sampleMultiple. false otherwise
	 * @throws Exception if the buffer is empty 
	 */
	public boolean addSample(V sample, int sampleMultipleNumber) throws Exception ;
	/**
	 * @return the number of samples of the buffer
	 */
	public int getSamplesCount();
	/**
	 * @return Vector<S> with all the samples
	 */
	public Vector<V> getBuffer();
	/**
	 * @param n indicates the number of samples to retrieve
	 * @return Vector<S> with the last N samples or less if the buffer has less samples
	 * @throws Exception if the buffer size is empty
	 */
	public Vector<V> getLastNSamples(int n) throws Exception;
	/**
	 * @return the sampling period
	 */
	public T getSamplingPeriod();
}
