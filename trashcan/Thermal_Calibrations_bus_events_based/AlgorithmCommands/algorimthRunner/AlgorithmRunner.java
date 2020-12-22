package algorimthRunner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import javax.measure.Measure;
import javax.measure.unit.SI;

import SDM.IDelay;
import SDM.ISource;
import clasesFicticias.SMU;
import composites.SDMCommand;
import composites.SDMCommandList;
import composites.SDMLinearCommandListFactory;
import model.TimeDelayer;


public class AlgorithmRunner implements Runnable, IAlgorithmRunner {

	private ArrayList<ArrayList<SDMCommandList>> algorithmSteps = null;
	
	public AlgorithmRunner() {
		super();
		algorithmSteps = new ArrayList<ArrayList<SDMCommandList>>();
		algorithmSteps.add(0, new ArrayList<SDMCommandList>()); //Algorithm Steps of the Execution Level 0 (BIAS)
		algorithmSteps.add(1, new ArrayList<SDMCommandList>()); //Algorithm Steps of the Execution Level 1 (STEPS)
		algorithmSteps.add(2, new ArrayList<SDMCommandList>()); //Algorithm Steps of the Execution Level 2 (SWEEPS)
	}


	
	@Override
	public void run() {
		
		System.out.println("Executing Algorithm Runner");
		
		// TODO Auto-generated method stub
		ArrayList<SDMCommandList> biasList = algorithmSteps.get(0);
		ArrayList<SDMCommandList> stepsList = algorithmSteps.get(1);
		ArrayList<SDMCommandList> sweepList = algorithmSteps.get(2);
		
//		System.out.println(this.toString());
		
//		System.out.println("************************************************************************************************************************************************");
//		System.out.println("************************************************************************************************************************************************");
//		System.out.println("************************************************************************************************************************************************");
//		System.out.println("************************************************************************************************************************************************");
//		System.out.println("************************************************************************************************************************************************");
//		System.out.println("************************************************************************************************************************************************");
//		System.out.println("************************************************************************************************************************************************");
//		System.out.println("************************************************************************************************************************************************");

		
		try {
			if (biasList.isEmpty()) {
				if (stepsList.isEmpty()) {
					//biasList and stepsList are empty
					Iterator<SDMCommandList> sweepListIterator = sweepList.iterator();
					while (sweepListIterator.hasNext()) {
						sweepListIterator.next().execute();
					}
				} else {
					//biasList is empty
					//stepsList is not empty
					copyAlgorithmSteps(sweepList, stepsList.get(stepsList.size()-1));
					rankAlgorithmSteps(stepsList);
					stepsList.get(0).execute();
				}
			} else {
				if (stepsList.isEmpty()) {
					//stepsList is empty
					//biasList is not empty
					copyAlgorithmSteps(sweepList, biasList.get(biasList.size()-1));
					rankAlgorithmSteps(biasList);
					biasList.get(0).execute();
				} else {
					//stepsList is not empty
					//biasList is not empty			
					
					copyAlgorithmSteps(sweepList, stepsList.get(stepsList.size()-1));
					rankAlgorithmSteps(stepsList);
					copyAlgorithmStep(stepsList.get(0), biasList.get(biasList.size()-1));
					rankAlgorithmSteps(biasList);
					
//					System.out.println(this.toString());
					
					
					biasList.get(0).execute();
				}
			} 
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	private void copyAlgorithmStep(SDMCommandList source, SDMCommandList destination) {

		//put all source SDMCommandList inside the destination (Source Command of every item of the SDMCommandList)
		
		Iterator<SDMCommand> destinationSDMCommandsIterator = destination.getSDMCommands().iterator();
		while (destinationSDMCommandsIterator.hasNext())
		{
			destinationSDMCommandsIterator.next().getSourceCommand().addCommand(source);
		}
	}

	
	private void copyAlgorithmSteps(ArrayList<SDMCommandList> source, SDMCommandList destination) {

		//put all source SDMCommandList inside the destination (Source Command of every item of the SDMCommandList)
		
		Iterator<SDMCommand> destinationSDMCommandsIterator = destination.getSDMCommands().iterator();
		while (destinationSDMCommandsIterator.hasNext())
		{
			ListIterator<SDMCommandList> sourcetIterator = source.listIterator();
			while (sourcetIterator.hasNext())
			{
				SDMCommandList sweep = sourcetIterator.next();
				destinationSDMCommandsIterator.next().getSourceCommand().addCommand(sweep);
			}
		}
	}

	private void rankAlgorithmSteps(ArrayList<SDMCommandList> source) {
		if (source.size()<2) return;
		
		//last step algorithm inside the previous step algorithm and so on until the first
		ListIterator<SDMCommandList> sourceIterator = source.listIterator();
		while (sourceIterator.hasNext()) sourceIterator.next(); //We will situate the iterator to the end of the list
		
		if (sourceIterator.hasPrevious())
		{
			SDMCommandList previousStep = sourceIterator.previous();
			while (sourceIterator.hasPrevious()) {
				SDMCommandList prePreviousStep = sourceIterator.previous();
				Iterator<SDMCommand> prePreviousStepSDMCommandsIterator = prePreviousStep.getSDMCommands().iterator();
				while (prePreviousStepSDMCommandsIterator.hasNext())
				{
					prePreviousStepSDMCommandsIterator.next().getSourceCommand().addCommand(previousStep);
				}
				previousStep = prePreviousStep;
			}
		}
	}
	
	@Override
	public void addAlgorithmStep(SDMCommandList _algorithmStep) 
	{		
		algorithmSteps.get(_algorithmStep.getExecutionLevel()).add(_algorithmStep);
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AlgorithmRunner [\n " +
					"ALGORITHM STEPS DE NIVEL 0 = [ \n " +
						algorithmSteps.get(0) + " ] \n" +
					"ALGORITHM STEPS DE NIVEL 1 = [ \n " +
						algorithmSteps.get(1) + " ] \n" +
					"ALGORITHM STEPS DE NIVEL 2 = [ \n " +
						algorithmSteps.get(2) + " ] \n" ;
						
				
	}



	public static void main(String[] args) {
		
		try {
			AlgorithmRunner algorithmRunner = new AlgorithmRunner();
			
			SMU smu1 = new SMU("SMU1");
			SMU smu2 = new SMU("SMU2");
			SMU smu3 = new SMU("SMU3");
			SMU smu4 = new SMU("SMU4");
			
			TimeDelayer td = new TimeDelayer(Measure.valueOf(1000, SI.MILLI(SI.SECOND)));
			IDelay td2 = new TimeDelayer(Measure.valueOf(1000, SI.MILLI(SI.SECOND)));
			
			SDMCommandList sdmBiasVoltage = SDMLinearCommandListFactory.createSDMBiasVoltageCommandList
			(	
				smu1, 
				td, 
				smu1, 
				Measure.valueOf(10.0, SI.MILLI(SI.VOLT)), 
				Measure.valueOf(0.001, SI.MILLI(SI.AMPERE)),
				Measure.valueOf(0.001, SI.MILLI(SI.AMPERE)), 
				0.01,
				0,
				false
			);
			
			algorithmRunner.addAlgorithmStep(sdmBiasVoltage);
			
			SDMCommandList sdmStepVoltage2 = SDMLinearCommandListFactory.createStepVoltageCommandList
					(
						smu2, 
						td, 
						smu2, 
						Measure.valueOf(0.0, SI.MILLI(SI.VOLT)), 
						Measure.valueOf(10.0, SI.MILLI(SI.VOLT)), 
						11, 
						Measure.valueOf(0.001, SI.MILLI(SI.AMPERE)), 
						Measure.valueOf(0.001, SI.MILLI(SI.AMPERE)), 
						0.01, 
						0, false
					);
			
			algorithmRunner.addAlgorithmStep(sdmStepVoltage2);
			
			SDMCommandList sdmStepVoltage = SDMLinearCommandListFactory.createStepVoltageCommandList
			(
				smu3, 
				td, 
				smu3, 
				Measure.valueOf(0.0, SI.MILLI(SI.VOLT)), 
				Measure.valueOf(10.0, SI.MILLI(SI.VOLT)), 
				11, 
				Measure.valueOf(0.001, SI.MILLI(SI.AMPERE)), 
				Measure.valueOf(0.001, SI.MILLI(SI.AMPERE)), 
				0.01, 
				0, false
			);
			
			algorithmRunner.addAlgorithmStep(sdmStepVoltage);
			
			SDMCommandList sdmSweepVoltage = SDMLinearCommandListFactory.createSweepVoltageCommandList
			(
				smu4, 
				td2, 
				smu4, 
				Measure.valueOf(0.0, SI.MILLI(SI.VOLT)), 
				Measure.valueOf(10.0, SI.MILLI(SI.VOLT)), 
				2, 
				Measure.valueOf(0.001, SI.MILLI(SI.AMPERE)), 
				Measure.valueOf(0.001, SI.MILLI(SI.AMPERE)), 
				0.01, 
				0, false
			);
			
			algorithmRunner.addAlgorithmStep(sdmSweepVoltage);
			
			//algorithmRunner.algorithmSteps.get(2).get(0).execute();
			
			algorithmRunner.run();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println("Delay Complete");
	}
}
