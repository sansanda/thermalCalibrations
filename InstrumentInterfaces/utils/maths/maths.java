package maths;

import java.util.Iterator;
import java.util.List;

public abstract class maths 
{
	
	private maths() {super();}

	public static double mean(List<Double> numbers, int numbersCount) throws Exception
	{
		double sum=0; 
		if (numbersCount<=0) throw new Exception("Numbers list must contain at least one value");
		Iterator<Double> numbersIt = numbers.iterator();
		
		while(numbersIt.hasNext())
		{
			sum = sum + numbersIt.next();
		}

		return sum/numbersCount;
	}
	
	public static double stDev(List<Double> numbers) throws Exception
	{
		double sum=0; 
		double n = numbers.size();
		double mean = maths.mean(numbers, numbers.size());
		
		if (n<=1) throw new Exception("For St Dev Calculation Numbers list must contain at least two values");
		
		Iterator<Double> numbersIt = numbers.iterator();
		
		while(numbersIt.hasNext())
		{
			sum+=Math.pow((numbersIt.next()-mean),2);
		}
		
		mean=sum/(n-1);
		return Math.sqrt(mean);
	}
}