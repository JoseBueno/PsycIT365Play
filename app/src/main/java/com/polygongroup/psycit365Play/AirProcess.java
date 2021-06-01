package com.polygongroup.psycit365Play;
/*public*/ class AirProcess
{
	/*public*/ final int Fld_idx_tload=0;//static 
	/*public*/ final int Fld_idx_sload=1;
	/*public*/ final int Fld_idx_lload=2;
	/*public*/ final int Fld_idx_mload=3;
	/*public*/ double[] array;

	/*public*/ double TLoad;
	/*public*/ double SLoad;
	/*public*/ double LLoad;
	/*public*/ double MLoad;
	
	/*public*/ int m_units;
	/*public*/ final int[] ms_out_prc_lds_fld_idxs = { Fld_idx_tload, Fld_idx_sload, Fld_idx_lload, Fld_idx_mload };

	/*public*/ AirProcess()
	{
		m_units = Units.Units_ip;
		array = new double[4];
	}

	/*public*/ final void set_units(int _val)
	{
		if (_val == m_units)
		{
			//	Do nothing
		}
		else if (m_units == Units.Units_si && _val == Units.Units_ip)
		{
			TLoad = TLoad * 3413;
			SLoad = SLoad * 3413;
			LLoad = LLoad * 3413;
			MLoad = MLoad * 2.205;
		}
		else if (m_units == Units.Units_ip && _val == Units.Units_si)
		{
			TLoad = TLoad / 3413;
			SLoad = SLoad / 3413;
			LLoad = LLoad / 3413;
			MLoad = MLoad / 2.205;
		}
		m_units = _val;
		toArray();
	}

	/*public*/ final void calc(AirStream _start, AirStream _end)
	{
		final double vol = _start.vol;
		//total load
		TLoad = (_end.h - _start.h) * 4.5 * vol;
		//sensible load
		SLoad = (_end.t - _start.t) * 1.08 * vol;
		//latent load
		LLoad = TLoad - SLoad;
		//moisture load
		MLoad = ((_end.X - _start.X) * 4.5 * vol) / 7000;
		toArray();
	}
	private void toArray()
	{
		array[0] = TLoad;
        array[1] = SLoad;
        array[2] = LLoad;
        array[3] = MLoad;	
	}
}