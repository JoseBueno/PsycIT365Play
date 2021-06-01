package com.polygongroup.psycit365Play;
//ORIGINAL LINE: class AltEncoding : protected PsyLib
//C++ TO JAVA CONVERTER TODO TASK: Java has no concept of 'protected' inheritance:

/*public*/ class AltEncoding extends PsyLib
{

		/*public*/ final double to_atm_press(int _units, int _idx, double _val)
		{
			double _result_ = 0;
        
			if (_idx == 0)
			{
				if (_units == Units.Units_si)
					_val = _val * 3.2808333;
				_result_ = AlttoPSI(_val);
        
			}
			else if (_idx == 1)
			{
				if (_units == Units.Units_si)
					_val = _val / 25.4;
				_result_ = InH2OtoPSI(_val);
        
			}
			else if (_idx == 2)
			{
				if (_units == Units.Units_si)
					_val = _val / 25.4;
				_result_ = HgtoPSI(_val);
        
			}
			else if (_idx == 3)
			{
				if (_units == Units.Units_si)
					_val = _val * (OneAtmPSI / OneAtmkPa);
				_result_ = _val;
        
			}
        
			if (_units == Units.Units_si)
			{
				_result_ = _result_ * (OneAtmkPa / OneAtmPSI);
			}
        
			return _result_;
		}
		/*		Converts val expressed in { idx, units }
				to atmospheric pressure expressed in units.
		*/
		/*public*/ final double from_atm_press(int _units, int _idx, double _val)
		{
        
			double _result_ = 0;
        
        
			if (_units == Units.Units_si)
			{
				_val = _val * (OneAtmPSI / OneAtmkPa);
			}
        
			 if (_idx == 0)
			{
				_result_ = PSItoALT(_val);
				if (_units == Units.Units_si)
					_result_ = _result_ / 3.2808333;
        
			}
			else if (_idx == 1)
			{
				_result_ = PSItoInH20(_val);
				if (_units == Units.Units_si)
					_result_ = _result_ * 25.4;
        
			}
			else if (_idx == 2)
			{
				_result_ = PSItoHg(_val);
				if (_units == Units.Units_si)
					_result_ = _result_ * 25.4;
        
			}
			else if (_idx == 3)
			{
				_result_ = _val;
				if (_units == Units.Units_si)
					_result_ = _result_ * (OneAtmkPa / OneAtmPSI);
        
			}
        
			return _result_;
		}
		/*		Converts atmospheric pressure val expressed in
				units to the value expressed in { idx,
				units }.
		*/

}