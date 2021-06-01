package com.polygongroup.psycit365Play;
/*public*/ class ProccFieldNames
/*		These are sets of AirStream filed's names.
*/
{
	/*public*/ static NumFormat ms_fld_formats[][] = { 
		{ new NumFormat(true, 3, 0), new NumFormat(true, 3, 2) }, 
		{ new NumFormat(true, 3, 0), new NumFormat(true, 3, 2) },
		{ new NumFormat(true, 3, 0), new NumFormat(true, 3, 2) }, 
		{ new NumFormat(true, 3, 2), new NumFormat(true, 3, 2) } };
	

		/*public*/ final NumFormat fld_format(int _units, int _idx)
		{
			return ms_fld_formats[_idx][_units - 1];
		}
		/*
		*/



//private static final PsyCalcUtils.NumFormat[][] ms_fld_formats = new PsyCalcUtils.NumFormat[AirProcess.Fields_num][2];
}