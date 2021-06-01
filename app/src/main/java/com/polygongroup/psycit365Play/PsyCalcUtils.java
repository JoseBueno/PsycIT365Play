package com.polygongroup.psycit365Play;

/*public*/ class PsyCalcUtils extends PsyLib
{
	/*public*/ static final double[][] fld_init_vals = 
	{ { 70, 20 }, { 58, 14 }, { 50, 50 }, { 25, 38 }, { 50, 9 }, { 55, 7 } };
	/*public*/ static final double[][] prc_init_vals1 = 
	{ { 95, 35 }, { 78, 25 }, { 48, 100 }, { 41, 78 }, { 72, 22 }, { 120, 17 } };
	/*public*/ static final double[][] prc_init_vals2 = 
	{ { 55, 13 }, { 55, 13 }, { 100, 100 }, { 23, 36 }, { 55, 13 }, { 64, 9 } };

	/*public*/ final String mk_num_str(double _val, NumFormat _fmt)
	{
		double tmp = 0.0f;
		String numString;
		tmp = round(_val,_fmt.after_dec);
		if (tmp == 0)
		{
			_val = 0;
		}
		String fmt = String.format("%%%d.%df", _fmt.before_dec, _fmt.after_dec);
		numString = String.format(fmt, _val);
		return numString;
	}
}