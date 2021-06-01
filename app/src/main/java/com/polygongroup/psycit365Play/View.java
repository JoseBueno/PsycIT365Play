package com.polygongroup.psycit365Play;

/*public*/ class View
{
	private double altitudeValue;
	private int m_in_units;
	private int m_out_units;
	private int m_last_alt_sel;
	
	/*public*/ View()
	{
		m_in_units = Units.Units_ip;
		m_out_units = Units.Units_ip;
	}

	/*public*/ double alt_val()
	{
	//	double _result_ = 0.0f;
		AltEncoding alt_enc = new AltEncoding();
    
		double _result_ = alt_enc.to_atm_press(in_units(), m_last_alt_sel, altitudeValue);
		return _result_;
	}

	/*public*/ double set_alt_val(double altValue)
	{
   		AltEncoding alt_enc = new AltEncoding();
		double altNewValue = 0.0;
		altNewValue = alt_enc.from_atm_press(in_units(), m_last_alt_sel, altValue);
		setAltitudeValue(altNewValue);
		return altNewValue;
	}

	/*public*/ void set_in_units(int _val)
	{
		AirStream stream = new AirStream();
		stream.set_units(in_units());
		stream.AtmPress = alt_val();
		m_in_units = _val;
	}

	/*public*/ void init_start_vals()
	{
	}

	/*public*/ int in_units()
	{
		return m_in_units;
	}

	/*public*/ int out_units()
	{
		return m_out_units;
	}

	/*public*/ void set_out_units(int _val)
	{
		m_out_units = _val;
	}

	/*public*/ String on_alt_unit_changed(int altSelectIndex)
	{
		NumFormat altFormat = new NumFormat();
		FieldNames fld_names = new FieldNames();
		PsyCalcUtils utl = new PsyCalcUtils();
		// double val = 0.0;
//		double tmp = altitudeValue;
		double val = alt_val();
		setAltIndex(altSelectIndex);
		val = set_alt_val(val);
		altFormat = fld_names.fld_format(in_units(), AirStream.Fld_idx_atm_press);
		int afterDecSave = altFormat.after_dec; // this is stupid but necessary
		if(m_last_alt_sel == 0)
		{
			altFormat.after_dec = 0;
			val = (int)val;
		}
//		altDoubleValue = val;
		String returnString = utl.mk_num_str(val, altFormat);
		altFormat.after_dec = afterDecSave;
		return (returnString);
	}

	/*public*/ void setAltIndex(int altIndex)
	{
		m_last_alt_sel = altIndex;
	}

	/*public*/ void setAltitudeValue(double altitude)
	{
		altitudeValue = altitude;
	}
}
//#endif


