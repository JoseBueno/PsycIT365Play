package com.polygongroup.psycit365Play;
/*public*/ class FieldNames
/*		These are sets of AirStream filed's names.
*/
{
	/*public*/ static final char[] fti_lb = { 'f', 't', 0x00b3, '/', 'l', 'b', '\00' };
	/*public*/ static final char[] lb_fti = { 'l', 'b', '/', 'f', 't', 0x00b3, '\00' };
	/*public*/ static final char[] gr_fti = { 'g', 'r', '/', 'f', 't', 0x00b3, '\00' };
	/*public*/ static final char[] mi_kg = { 'm', 0x00b3, '/', 'k', 'g', '\00' };
	/*public*/ static final char[] kg_mi = { 'k', 'g', '/', 'm', 0x00b3, '\00' };
	/*public*/ static final char[] g_mi = { 'g', '/', 'm', 0x00b3, '\00' };


	protected static final NumFormat[][] ms_fld_formats = { 
		{ new NumFormat(true, 3, 2), new NumFormat(true, 3, 2) }, 
		{ new NumFormat(true, 3, 2), new NumFormat(true, 3, 2) }, 
		{ new NumFormat(true, 3, 2), new NumFormat(true, 3, 2) }, 
		{ new NumFormat(true, 3, 2), new NumFormat(true, 3, 2) }, 
		{ new NumFormat(true, 3, 2), new NumFormat(true, 3, 2) }, 
		{ new NumFormat(true, 3, 2), new NumFormat(true, 3, 2) }, 
		{ new NumFormat(true, 5, 3), new NumFormat(true, 3, 3) }, 
		{ new NumFormat(true, 3, 2), new NumFormat(true, 3, 2) }, 
		{ new NumFormat(true, 3, 5), new NumFormat(true, 3, 6) }, 
		{ new NumFormat(true, 3, 5), new NumFormat(true, 3, 5) }, 
		{ new NumFormat(true, 3, 2), new NumFormat(true, 3, 2) }, 
		{ new NumFormat(true, 3, 0), new NumFormat(true, 3, 0) }, 
		{ new NumFormat(true, 3, 0), new NumFormat(true, 3, 0) }, 
		{ new NumFormat(true, 3, 0), new NumFormat(true, 3, 0) }, 
		{ new NumFormat(true, 3, 0), new NumFormat(true, 3, 0) } };


		/*public*/ final NumFormat fld_format(int _units, int _idx)
		{
				return ms_fld_formats[_idx][_units - 1];
		}
		/*
		*/



//private static final NumFormat[][] ms_fld_formats = new NumFormat[AirStream.Fields_num][2];
}