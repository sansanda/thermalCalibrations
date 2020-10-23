package data;


import java.io.IOException;




public class DataReqUtility {
	DataRequest dataRequest;

	public DataReqUtility(DataRequest _dataRequest) throws IOException
	{
		dataRequest = _dataRequest;
	}
	public DataInterface getData()throws Exception
	{
		/* Use factory to create action based on request parms */
		String data = dataRequest.getData();
		return DataFactory.createData(data);
	}
}