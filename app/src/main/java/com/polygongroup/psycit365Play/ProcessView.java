package com.polygongroup.psycit365Play;

/*public*/ class ProcessView extends View//, PsyLib
{

	/*public*/ final static int PROCESS_ENTER = 1;
	/*public*/ final static int PROCESS_LEAVING = 2;
	/*public*/ final static int PROCESS_INPUT_1 = 1;
	/*public*/ final static int PROCESS_INPUT_2 = 2;

	private double Enter1Value;
	private double Enter2Value;
	private double RatioValue;
	private int Enter1Units;
	private int Enter2Units;
	private int RatioUnits;

	private double Leaving1Value;
	private double Leaving2Value;
	private int Leaving1Units;
	private int Leaving2Units;
	
	
	/*public*/ final void setInputValueUnitType(double value, int unitType, int enter_leaving, int inputNumber)
	{
		switch (inputNumber)
		{
			case DefineConstants.PROCESS_INPUT_1:
				switch (enter_leaving)
				{
					case DefineConstants.PROCESS_ENTER:
						Enter1Value = value;
						Enter1Units = unitType;
						break;
					case DefineConstants.PROCESS_LEAVING:
						Leaving1Value = value;
						Leaving1Units = unitType;
						break;
				}
				break;
    
			case DefineConstants.PROCESS_INPUT_2:
				switch (enter_leaving)
				{
					case DefineConstants.PROCESS_ENTER:
						Enter2Value = value;
						Enter2Units = unitType;
						break;
					case DefineConstants.PROCESS_LEAVING:
						Leaving2Value = value;
						Leaving2Units = unitType;
						break;
				}
				break;
		}
	}

	/*public*/ final void setRatioValueUnitType(double value, int unitType)
	{
		RatioValue = value;
		RatioUnits = unitType + 13;
	}

	public final void set_in_units(int _val)
	{
		super.set_in_units(_val);   
	}

	public final void set_out_units(int _val)
	{
		super.set_out_units(_val);
	}

	/*public*/ final int calculate(double[] psyCalcValues, String[] stringValues)
	{
		AirStream first = new AirStream();
		AirStream second = new AirStream();
		AirProcess procc = new AirProcess();
		PsyCalcUtils utl = new PsyCalcUtils();
		FieldNames fld_names = new FieldNames();
		ProccFieldNames procc_fld_names = new ProccFieldNames();
		int err_code = 0;

		first.set_units(in_units());
		second.set_units(in_units());
		first.setValueByUnitType(Enter1Value, Enter1Units); 
		//first.array[Enter1Units] = Enter1Value;
		first.setValueByUnitType(Enter2Value, Enter2Units); 
		//first.array[Enter2Units] = Enter2Value;
		first.setValueByUnitType(RatioValue, RatioUnits); 
		//first.array[RatioUnits] = RatioValue;
		
		second.setValueByUnitType(Leaving1Value, Leaving1Units);
		//second.array[Leaving1Units] = Leaving1Value;
		second.setValueByUnitType(Leaving2Value, Leaving2Units); 
		//second.array[Leaving2Units] = Leaving2Value;
		second.setValueByUnitType(RatioValue, RatioUnits); 
		//second.array[RatioUnits] = first.array[RatioUnits];
		
		first.AtmPress = alt_val();
		second.AtmPress = first.AtmPress;
		first.set_units(Units.Units_ip);
		second.set_units(Units.Units_ip);
		procc.set_units(Units.Units_ip);

		err_code = first.calc_stream(Enter1Units, Enter2Units, RatioUnits);
		if (err_code == 0)
		{
			second.vol = first.vol;
			err_code = second.calc_stream(Leaving1Units, Leaving2Units, AirStream.Fld_idx_vol);
		}
		if (err_code == 0) procc.calc(first, second);
		if (err_code == 0)
		{
			second.set_units(out_units());
			procc.set_units(out_units());
			psyCalcValues[0] = second.t;//array[0];
			psyCalcValues[1] = second.tw;//array[1];
			psyCalcValues[2] = second.RelH;//array[2];
			psyCalcValues[3] = second.h;//array[3];
			psyCalcValues[4] = second.Tdp;//array[4];
			psyCalcValues[5] = second.X;//array[5];
			psyCalcValues[6] = second.v;//array[7];
			psyCalcValues[7] = second.vp;//array[8];
			psyCalcValues[8] = second.PPMw;//array[11];
			psyCalcValues[9] = second.PPMv;//array[12];
			psyCalcValues[10] = second.ah;//array[10];
			//Process Load Values
			psyCalcValues[11] = procc.TLoad;
			psyCalcValues[12] = procc.SLoad;
			psyCalcValues[13] = procc.LLoad;
			psyCalcValues[14] = procc.MLoad;
			psyCalcValues[15] = procc.TLoad / 12000.00f;
		}
		else
		{
			for(int i=0; i<16;i++) psyCalcValues[i] = 0.0;
		}
    
		stringValues[0] = utl.mk_num_str(psyCalcValues[0], fld_names.fld_format(out_units(), 0));
		stringValues[1] = utl.mk_num_str(psyCalcValues[1], fld_names.fld_format(out_units(), 1));
		stringValues[2] = utl.mk_num_str(psyCalcValues[2], fld_names.fld_format(out_units(), 2));
		stringValues[3] = utl.mk_num_str(psyCalcValues[3], fld_names.fld_format(out_units(), 3));
		stringValues[4] = utl.mk_num_str(psyCalcValues[4], fld_names.fld_format(out_units(), 4));
		stringValues[5] = utl.mk_num_str(psyCalcValues[5], fld_names.fld_format(out_units(), 5));
		stringValues[6] = utl.mk_num_str(psyCalcValues[6], fld_names.fld_format(out_units(), 7));
		stringValues[7] = utl.mk_num_str(psyCalcValues[7], fld_names.fld_format(out_units(), 8));
		stringValues[8] = utl.mk_num_str(psyCalcValues[8], fld_names.fld_format(out_units(), 11));
		stringValues[9] = utl.mk_num_str(psyCalcValues[9], fld_names.fld_format(out_units(), 12));
		stringValues[10] = utl.mk_num_str(psyCalcValues[10], fld_names.fld_format(out_units(), 10));
		stringValues[11] = utl.mk_num_str(psyCalcValues[11], procc_fld_names.fld_format(out_units(), procc.Fld_idx_tload));
		stringValues[12] = utl.mk_num_str(psyCalcValues[12], procc_fld_names.fld_format(out_units(), procc.Fld_idx_sload));
		stringValues[13] = utl.mk_num_str(psyCalcValues[13], procc_fld_names.fld_format(out_units(), procc.Fld_idx_lload));
		stringValues[14] = utl.mk_num_str(psyCalcValues[14], procc_fld_names.fld_format(out_units(), procc.Fld_idx_mload));
		stringValues[15] = utl.mk_num_str(psyCalcValues[15], procc_fld_names.fld_format(out_units(), procc.Fld_idx_tload));
		return err_code;
	}




}