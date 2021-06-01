package com.polygongroup.psycit365Play;


/*public*/ class MixingView extends View//, PsyLib
{
	/*public*/ final int MIXINGSTREAMA=1;
	/*public*/ final int MIXINGSTREAMB=2;
	
	private double mixingStreamAInput1Value;
	private double mixingStreamAInput2Value;
	private double mixingStreamARatioValue;
	private int mixingStreamAInput1Units;
	private int mixingStreamAInput2Units;
	private int mixingStreamARatioUnits;

	private double mixingStreamBInput1Value;
	private double mixingStreamBInput2Value;
	private double mixingStreamBRatioValue;
	private int mixingStreamBInput1Units;
	private int mixingStreamBInput2Units;
	private int mixingStreamBRatioUnits;
	//private final int[] ms_out_fld_idxs;
	
	/*public*/ final void setInput1ValueUnitType(double value, int unitType, int streamA_B)
	{
		switch (streamA_B)
		{
			case MIXINGSTREAMA:
				mixingStreamAInput1Value = value;
				mixingStreamAInput1Units = unitType;
				break;
			case MIXINGSTREAMB:
				mixingStreamBInput1Value = value;
				mixingStreamBInput1Units = unitType;
				break;
		}
	}

	/*public*/ final void setInput2ValueUnitType(double value, int unitType, int streamA_B)
	{
		switch (streamA_B)
		{
			case MIXINGSTREAMA:
				mixingStreamAInput2Value = value;
				mixingStreamAInput2Units = unitType;
				break;
			case MIXINGSTREAMB:
				mixingStreamBInput2Value = value;
				mixingStreamBInput2Units = unitType;
				break;
		}
	}

	/*public*/ final void setRatioValueUnitType(double value, int unitType, int streamA_B)
	{
		switch (streamA_B)
		{
			case MIXINGSTREAMA:
				mixingStreamARatioValue = value;
				mixingStreamARatioUnits = unitType + 13;
				break;
			case MIXINGSTREAMB:
				mixingStreamBRatioValue = value;
				mixingStreamBRatioUnits = unitType + 13;
				break;
		}
	}

	/*public*/ final int calculate(double[] psyCalcValues, String[] pysCalcStringValues)
	{
		AirStream first = new AirStream();
		AirStream second = new AirStream();
		PsyCalcUtils utl = new PsyCalcUtils();
		FieldNames fld_names = new FieldNames();
		int err_code = 0;
		
		first.set_units(in_units());
		// first.toArray(); set_units above did toArray() already
		first.setValueByUnitType(mixingStreamAInput1Value, mixingStreamAInput1Units);
		//first.array[mixingStreamAInput1Units] = mixingStreamAInput1Value;
		first.setValueByUnitType(mixingStreamAInput2Value, mixingStreamAInput2Units);
		//first.array[mixingStreamAInput2Units] = mixingStreamAInput2Value;
		first.setValueByUnitType(mixingStreamARatioValue, mixingStreamARatioUnits);
		//first.array[mixingStreamARatioUnits] = mixingStreamARatioValue;

		second.set_units(in_units());
		second.setValueByUnitType(mixingStreamBInput1Value, mixingStreamBInput1Units);
		//second.array[mixingStreamBInput1Units] = mixingStreamBInput1Value;
		second.setValueByUnitType(mixingStreamBInput2Value, mixingStreamBInput2Units);
		//second.array[mixingStreamBInput2Units] = mixingStreamBInput2Value;
		second.setValueByUnitType(mixingStreamBRatioValue, mixingStreamBRatioUnits);
		//second.array[mixingStreamBRatioUnits] = mixingStreamBRatioValue;
	
		first.AtmPress = alt_val();
		second.AtmPress = first.AtmPress;
		first.set_units(Units.Units_ip);
		second.set_units(Units.Units_ip);

		err_code = first.calc_stream(
				mixingStreamAInput1Units, 
				mixingStreamAInput2Units, 
				mixingStreamARatioUnits);
    
		if (err_code == 0)
			err_code = second.calc_stream(
					mixingStreamBInput1Units, 
					mixingStreamBInput2Units, 
					mixingStreamBRatioUnits);
    
		if (err_code == 0)
			err_code = first.calc_mixing(second);
    
		if (err_code == 0)
		{
			first.set_units(out_units());
 
			psyCalcValues[0] = first.t;//array[0];
			psyCalcValues[1] = first.tw;//array[1];
			psyCalcValues[2] = first.RelH;//array[2];
			psyCalcValues[3] = first.h;//array[3];
			psyCalcValues[4] = first.Tdp;//array[4];
			psyCalcValues[5] = first.X;//array[5];
			psyCalcValues[6] = first.v;//array[7];
			psyCalcValues[7] = first.vp;//array[8];
			psyCalcValues[8] = first.PPMw;//array[11];
			psyCalcValues[9] = first.PPMv;//array[12];
			psyCalcValues[10] = first.ah;//array[10];
			psyCalcValues[11] = first.vol;//array[13];
			psyCalcValues[12] = first.avol;//array[14];
			
			if ((Units.Units_si == in_units()) && (Units.Units_ip == out_units()))
			{
				psyCalcValues[11] = psyCalcValues[11] / 0.5885f;
				psyCalcValues[12] = psyCalcValues[12] / 0.5885f;
			}
			else if ((Units.Units_ip == in_units()) && (Units.Units_si == out_units()))
			{
				psyCalcValues[11] = psyCalcValues[11] * 0.5885f;
				psyCalcValues[12] = psyCalcValues[12] * 0.5885f;
			}
		}
		else for(int i=0;i<13;i++) psyCalcValues[i] = 0.0;
    
		pysCalcStringValues[0] = utl.mk_num_str(psyCalcValues[0], fld_names.fld_format(out_units(), 0) );
		pysCalcStringValues[1] = utl.mk_num_str(psyCalcValues[1], fld_names.fld_format(out_units(), 1));
		pysCalcStringValues[2] = utl.mk_num_str(psyCalcValues[2], fld_names.fld_format(out_units(), 2));
		pysCalcStringValues[3] = utl.mk_num_str(psyCalcValues[3], fld_names.fld_format(out_units(), 3));
		pysCalcStringValues[4] = utl.mk_num_str(psyCalcValues[4], fld_names.fld_format(out_units(), 4));
		pysCalcStringValues[5] = utl.mk_num_str(psyCalcValues[5], fld_names.fld_format(out_units(), 5));
		pysCalcStringValues[6] = utl.mk_num_str(psyCalcValues[6], fld_names.fld_format(out_units(), 7));
		pysCalcStringValues[7] = utl.mk_num_str(psyCalcValues[7], fld_names.fld_format(out_units(), 8));
		pysCalcStringValues[8] = utl.mk_num_str(psyCalcValues[8], fld_names.fld_format(out_units(), 11));
		pysCalcStringValues[9] = utl.mk_num_str(psyCalcValues[9], fld_names.fld_format(out_units(), 12));
		pysCalcStringValues[10] = utl.mk_num_str(psyCalcValues[10], fld_names.fld_format(out_units(), 10));
    
		pysCalcStringValues[11] = utl.mk_num_str(psyCalcValues[11], fld_names.fld_format(out_units(), 13));
		pysCalcStringValues[12] = utl.mk_num_str(psyCalcValues[12], fld_names.fld_format(out_units(), 13));
    
		return err_code;
	}
}