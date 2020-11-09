package composites;

import java.util.ArrayList;

import javax.measure.Measurable;
import javax.measure.Measure;
import javax.measure.quantity.ElectricPotential;
import javax.measure.quantity.ElectricCurrent;
import javax.measure.unit.SI;

import SDM.IDelay;
import SDM.IMeasure;
import SDM.ISource;
import basics.DelayCommand;
import basics.MeasureCommand;
import basics.SourceCommand;

public class SDMLinearCommandListFactory{
	
	public SDMLinearCommandListFactory() {}

	public static SDMCommandList createSDMBiasCurrentCommandList(	ISource _source,
												IDelay _delayer,
												IMeasure _meter,
												Measurable<ElectricCurrent> _currentSourceLevel,
												Measurable<ElectricPotential> _complianceLevel,
												Measurable<ElectricPotential> _range,
												double _nplc, 
												int _repeatFilterN, 
												boolean _enableFourMeasure) 
	{
		ArrayList<SDMCommand> sdmCommandList = new ArrayList<SDMCommand>();
		sdmCommandList.add(new SDMCommand(new SourceCommand(_source, ElectricCurrent.UNIT,_currentSourceLevel, _complianceLevel,ElectricPotential.UNIT,null),new DelayCommand(_delayer),new MeasureCommand(_meter, ElectricPotential.UNIT,_range, _nplc, _repeatFilterN, _enableFourMeasure)));
		SDMCommandList sdmBiasCommandList = new SDMCommandList(sdmCommandList,0);
		return sdmBiasCommandList;
	}
	
	public static SDMCommandList createSDMBiasVoltageCommandList(	ISource _source,
															IDelay _delayer,
															IMeasure _meter,
															Measurable<ElectricPotential> _voltageSourceLevel,
															Measurable<ElectricCurrent> _complianceLevel,
															Measurable<ElectricCurrent> _range,
															double _nplc, 
															int _repeatFilterN, 
															boolean _enableFourMeasure) 
	{
		ArrayList<SDMCommand> sdmCommandList = new ArrayList<SDMCommand>();
		sdmCommandList.add(new SDMCommand(new SourceCommand(_source, ElectricPotential.UNIT,_voltageSourceLevel, _complianceLevel,ElectricCurrent.UNIT,null),new DelayCommand(_delayer),new MeasureCommand(_meter, ElectricCurrent.UNIT,_range, _nplc, _repeatFilterN, _enableFourMeasure)));
		SDMCommandList sdmBiasCommandList = new SDMCommandList(sdmCommandList,0);
		return sdmBiasCommandList;
	}
	
	public static SDMCommandList createStepVoltageCommandList(	ISource _source,
																IDelay _delayer,
																IMeasure _meter,
																Measurable<ElectricPotential> _startVoltageSourceLevel,
																Measurable<ElectricPotential> _stopVoltageSourceLevel,
																int _nSteps,
																Measurable<ElectricCurrent> _complianceLevel,
																Measurable<ElectricCurrent> _range,
																double _nplc, 
																int _repeatFilterN, 
																boolean _enableFourMeasure)  
	{
		SDMCommandList sdmStepCommandList = new SDMCommandList(null,1);
		ArrayList<SDMCommand> sdmCommandList = new ArrayList<SDMCommand>();	
		double start = _startVoltageSourceLevel.doubleValue(SI.VOLT);
		double stop = _stopVoltageSourceLevel.doubleValue(SI.VOLT);
		double voltageStepValue = (stop-start)/(_nSteps-1);
		for (int i=0;i<_nSteps;i++)
		{
			double c = start + i*voltageStepValue;
			SourceCommand sc = new SourceCommand(_source, ElectricPotential.UNIT, Measure.valueOf(c, SI.VOLT), _complianceLevel, ElectricCurrent.UNIT,null);
			DelayCommand dc = new DelayCommand(_delayer);
			MeasureCommand mc = new MeasureCommand(_meter, ElectricCurrent.UNIT,_range, _nplc, _repeatFilterN, _enableFourMeasure);
			SDMCommand sdmC = new SDMCommand(sc, dc, mc);
			sdmCommandList.add(sdmC);
		}
		
		sdmStepCommandList.setSDMCommands(sdmCommandList);
		return sdmStepCommandList;
	}
	
	
	public static SDMCommandList createStepCurrentCommandList(	ISource _source,
																IDelay _delayer,
																IMeasure _meter,
																Measurable<ElectricCurrent> _startCurrentSourceLevel,
																Measurable<ElectricCurrent> _stopCurrentSourceLevel,
																int _nSteps,
																Measurable<ElectricPotential> _complianceLevel,
																Measurable<ElectricPotential> _range,
																double _nplc, 
																int _repeatFilterN, 
																boolean _enableFourMeasure)  
	{
		SDMCommandList sdmStepCommandList = new SDMCommandList(null,1);
		ArrayList<SDMCommand> sdmCommandList = new ArrayList<SDMCommand>();	
		double start = _startCurrentSourceLevel.doubleValue(SI.AMPERE);
		double stop = _stopCurrentSourceLevel.doubleValue(SI.AMPERE);
		double currentStepValue = (stop-start)/(_nSteps-1);
		for (int i=0;i<_nSteps;i++)
		{
			double c = start + i*currentStepValue;
			SourceCommand sc = new SourceCommand(_source, ElectricCurrent.UNIT, Measure.valueOf(c, SI.AMPERE), _complianceLevel, ElectricPotential.UNIT,null);
			DelayCommand dc = new DelayCommand(_delayer);
			MeasureCommand mc = new MeasureCommand(_meter, ElectricPotential.UNIT,_range, _nplc, _repeatFilterN, _enableFourMeasure);
			SDMCommand sdmC = new SDMCommand(sc, dc, mc);
			sdmCommandList.add(sdmC);
		}
		
		sdmStepCommandList.setSDMCommands(sdmCommandList);
		return sdmStepCommandList;
	}

	public static SDMCommandList createSweepVoltageCommandList(	ISource _source,
			IDelay _delayer,
			IMeasure _meter,
			Measurable<ElectricPotential> _startVoltageSourceLevel,
			Measurable<ElectricPotential> _stopVoltageSourceLevel,
			int _nSteps,
			Measurable<ElectricCurrent> _complianceLevel,
			Measurable<ElectricCurrent> _range,
			double _nplc, 
			int _repeatFilterN, 
			boolean _enableFourMeasure)  
	{
		SDMCommandList sdmStepCommandList = new SDMCommandList(null,2);
		ArrayList<SDMCommand> sdmCommandList = new ArrayList<SDMCommand>();	
		double start = _startVoltageSourceLevel.doubleValue(SI.VOLT);
		double stop = _stopVoltageSourceLevel.doubleValue(SI.VOLT);
		double voltageStepValue = (stop-start)/(_nSteps-1);
		
		for (int i=0;i<_nSteps;i++)
		{
			double c = start + i*voltageStepValue;
			SourceCommand sc = new SourceCommand(_source, ElectricPotential.UNIT, Measure.valueOf(c, SI.VOLT), _complianceLevel, ElectricCurrent.UNIT,null);
			DelayCommand dc = new DelayCommand(_delayer);
			MeasureCommand mc = new MeasureCommand(_meter, ElectricCurrent.UNIT,_range, _nplc, _repeatFilterN, _enableFourMeasure);
			SDMCommand sdmC = new SDMCommand(sc, dc, mc);
			sdmCommandList.add(sdmC);
		}

		sdmStepCommandList.setSDMCommands(sdmCommandList);
		return sdmStepCommandList;
	}


	public static SDMCommandList createSweepCurrentCommandList(	ISource _source,
															IDelay _delayer,
															IMeasure _meter,
															Measurable<ElectricCurrent> _startCurrentSourceLevel,
															Measurable<ElectricCurrent> _stopCurrentSourceLevel,
															int _nSteps,
															Measurable<ElectricPotential> _complianceLevel,
															Measurable<ElectricPotential> _range,
															double _nplc, 
															int _repeatFilterN, 
															boolean _enableFourMeasure)  
	{
		SDMCommandList sdmStepCommandList = new SDMCommandList(null,2);
		ArrayList<SDMCommand> sdmCommandList = new ArrayList<SDMCommand>();	
		double start = _startCurrentSourceLevel.doubleValue(SI.AMPERE);
		double stop = _stopCurrentSourceLevel.doubleValue(SI.AMPERE);
		double currentStepValue = (stop-start)/(_nSteps-1);
		for (int i=0;i<_nSteps;i++)
		{
			double c = start + i*currentStepValue;
			SourceCommand sc = new SourceCommand(_source, ElectricCurrent.UNIT, Measure.valueOf(c, SI.AMPERE), _complianceLevel, ElectricPotential.UNIT,null);
			DelayCommand dc = new DelayCommand(_delayer);
			MeasureCommand mc = new MeasureCommand(_meter, ElectricPotential.UNIT,_range, _nplc, _repeatFilterN, _enableFourMeasure);
			SDMCommand sdmC = new SDMCommand(sc, dc, mc);
			sdmCommandList.add(sdmC);
		}
		
		sdmStepCommandList.setSDMCommands(sdmCommandList);
		return sdmStepCommandList;
	}

	
}
