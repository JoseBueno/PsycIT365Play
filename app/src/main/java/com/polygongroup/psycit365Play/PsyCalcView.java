package com.polygongroup.psycit365Play;
/*public*/ class PsyCalcView extends View//, PsyLib
{
//	protected final int[] ms_out_fld_idxs;
	private double input1Value;
	private double input2Value;
	private int inpt1UnitsType;
	private int inpt2UnitsType;

	/*public*/ final void setInput1ValueUnitTypes(double input1_Value, int input1_UnitTypes)
	{
		input1Value = input1_Value;
		inpt1UnitsType = input1_UnitTypes;
	}

	/*public*/ final void setInput2ValueUnitTypes(double input2_Value, int input2_UnitTypes)
	{
		input2Value = input2_Value;
		inpt2UnitsType = input2_UnitTypes;
	}

	/*public*/ final int calculate(double[] psyCalcValues, String[] pysCalcStringValues)
	{
		AirStream stream = new AirStream();
		FieldNames fld_names = new FieldNames();
		PsyCalcUtils utl = new PsyCalcUtils();
		int err_code = 0;
		
		stream.set_units(in_units());
		stream.setValueByUnitType(input1Value, inpt1UnitsType); //stream.array[inpt1UnitsType] = input1Value;
		stream.setValueByUnitType(input2Value, inpt2UnitsType);//stream.array[inpt2UnitsType] = input2Value;

		stream.AtmPress = alt_val();
		stream.set_units(Units.Units_ip);
		err_code = stream.calc_stream(inpt1UnitsType, inpt2UnitsType, -1);
		if (err_code == 0)
		{
			stream.set_units(out_units());
			psyCalcValues[0] = stream.t;//array[0];
			psyCalcValues[1] = stream.tw;//array[1];
			psyCalcValues[2] = stream.RelH;//array[2];
			psyCalcValues[3] = stream.h;//array[3];
			psyCalcValues[4] = stream.Tdp;//array[4];
			psyCalcValues[5] = stream.X;//array[5];
			psyCalcValues[6] = stream.v;//array[7];
			psyCalcValues[7] = stream.vp;//array[8];
			psyCalcValues[8] = stream.PPMw;//array[11];
			psyCalcValues[9] = stream.PPMv;//array[12];
			psyCalcValues[10] = stream.ah;//array[10];
		}
   
		else for(int i=0;i<11;i++) psyCalcValues[i] = 0.0;
    
		pysCalcStringValues[0] = utl.mk_num_str(psyCalcValues[0], fld_names.fld_format(out_units(), 0));
		pysCalcStringValues[1] = utl.mk_num_str(psyCalcValues[1], fld_names.fld_format(out_units(), 1));
		pysCalcStringValues[2] = utl.mk_num_str(psyCalcValues[2], fld_names.fld_format(out_units(), 2));
		pysCalcStringValues[3] = utl.mk_num_str(psyCalcValues[3], fld_names.fld_format(out_units(), 3));
		pysCalcStringValues[4] = utl.mk_num_str(psyCalcValues[4], fld_names.fld_format(out_units(), 4));
		pysCalcStringValues[5] = utl.mk_num_str(psyCalcValues[5], fld_names.fld_format(out_units(), 5));
		pysCalcStringValues[6] = utl.mk_num_str(psyCalcValues[6], fld_names.fld_format(out_units(), 7));
		pysCalcStringValues[7] = utl.mk_num_str(psyCalcValues[7], fld_names.fld_format(out_units(), 8));
		pysCalcStringValues[8] = utl.mk_num_str(psyCalcValues[8], fld_names.fld_format(out_units(), 11));
		pysCalcStringValues[9] = utl.mk_num_str(psyCalcValues[9], fld_names.fld_format(out_units(), 12));
		pysCalcStringValues[10]= utl.mk_num_str(psyCalcValues[10],fld_names.fld_format(out_units(), 10));
    
		return err_code;
	}
}