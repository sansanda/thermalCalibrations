package statistics;

public interface IStatsFilter 
{
	boolean satisfies(Object samples) throws Exception;
}

