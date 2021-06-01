package com.polygongroup.psycit365Play;
import java.lang.Math;

/*public*/ class AirStream extends PsyLib
{
	/*public*/ static final int Fld_idx_t=0;
	/*public*/ static final int Fld_idx_tw=1;
	/*public*/ static final int Fld_idx_RelH=2;
	/*public*/ static final int Fld_idx_h=3;
	/*public*/ static final int Fld_idx_tdp=4;
	/*public*/ static final int Fld_idx_x=5;
	/*public*/ static final int Fld_idx_atm_press=6;
	/*public*/ static final int Fld_idx_v=7;
	/*public*/ static final int Fld_idx_vp=8;
	/*public*/ static final int Fld_idx_d=9;
	/*public*/ static final int Fld_idx_ah=10;
	/*public*/ static final int Fld_idx_ppmw=11;
	/*public*/ static final int Fld_idx_ppmv=12;
	/*public*/ static final int Fld_idx_vol=13;
	/*public*/ static final int Fld_idx_avol=14;
	
	
	/*public*/ static final int Fields_num = 15;
	/*public*/ static final int Input_fields_num = 15;
	
	/*public*/ int m_units;
	
	/*public*/ double t; // 1, Fdb, Cdb
	/*public*/ double tw; // 2, Fwb, Cwb
	/*public*/ double RelH; // 3, %RH, %RH
	/*public*/ double h; // 4, Btu/lb, kJ/kg
	/*public*/ double Tdp; // 5, Fdp, Cdp
	/*public*/ double X; // 6, dr/lb, g/kg
	/*public*/ double AtmPress; // 7, psia, kPa
	/*public*/ double v; // 8, ft/lb, m/kg
	/*public*/ double vp; // 9, ''Hg VP, kPa VP
	/*public*/ double d; // 11, lb/ft, kg/m
	/*public*/ double ah; // 12, gr/ft, g/m
	/*public*/ double PPMw; // 13, PPMw, PPMw
	/*public*/ double PPMv; // 14, PPMv, PPMv
	/*public*/ double vol; // 15,
	/*public*/ double avol; // 16,
	

	
	/*public*/ static final int[] ms_out_fld_idxs = { 
		Fld_idx_vol, Fld_idx_avol, Fld_idx_t, Fld_idx_tw, 
		Fld_idx_RelH, Fld_idx_h, Fld_idx_tdp,Fld_idx_x, 
		Fld_idx_atm_press, Fld_idx_v, Fld_idx_vp, Fld_idx_d, 
		Fld_idx_ah, Fld_idx_ppmw, Fld_idx_ppmv };

	/*public*/ static final int[] ms_out_end_pts_fld_idxs = { 
		Fld_idx_vol, Fld_idx_avol, Fld_idx_t, Fld_idx_tw, 
		Fld_idx_RelH, Fld_idx_h, Fld_idx_tdp, Fld_idx_x, 
		Fld_idx_atm_press, Fld_idx_v, Fld_idx_vp, Fld_idx_d, 
		Fld_idx_ah, Fld_idx_ppmw, Fld_idx_ppmv };
//	/*public*/ double[] array;
	/*public*/ double Z;

	/*public*/ AirStream()
	{
//		array = new double[Fields_num];
		m_units = Units.Units_ip;
	}
	/*public*/ final void setValueByUnitType(double _value, int unitType)
	{
		switch(unitType)
		{
			case Fld_idx_t: 	t=_value;break;
			case Fld_idx_tw: 	tw=_value;break;
			case Fld_idx_RelH: 	RelH=_value;break;
			case Fld_idx_h: 	h=_value;break;
			case Fld_idx_tdp: 	Tdp=_value;break;
			case Fld_idx_x: 	X=_value;break;
			case Fld_idx_atm_press: AtmPress=_value;break;
			case Fld_idx_v: 	v=_value;break;
			case Fld_idx_vp: 	vp=_value;break;
			case Fld_idx_d: 	d=_value;break;
			case Fld_idx_ah: 	ah=_value;break;
			case Fld_idx_ppmw: 	PPMw=_value;break;
			case Fld_idx_ppmv: 	PPMv=_value;break;
			case Fld_idx_vol: 	vol=_value;break;
			case Fld_idx_avol: 	avol=_value;break;
		} 
	}
	/*public*/ final void set_units(int _val)
	{
		if (_val == m_units)
		{
			//	Do nothing.
		}
		else if (m_units == Units.Units_ip && _val == Units.Units_si)
		{
			t = (t - 32) * 5/9;
			tw = (tw - 32) * 5/9;
			h = (h - 7.712) * 2.326;
			Tdp = (Tdp - 32) * 5/9;
			X = X / 7;
			AtmPress = AtmPress * (OneAtmkPa / OneAtmPSI);
			v = v * .062428;
			vp = vp * 101.325 / OneAtminHg;
			d = d * Math.pow((39.37 / 12), 3) / 2.20462;
			ah = ah * 35.31 / 7 / 2.20462;
			vol = vol / .5885;
			avol = avol / .5885;
		}
		else if (m_units == Units.Units_si && _val == Units.Units_ip)
		{
			t = t * 9/5 + 32;
			tw = tw * 9/5 + 32;
			h = h / 2.326 + 7.712;
			Tdp = Tdp * 9/5 + 32;
			X = X * 7;
			AtmPress = AtmPress * (OneAtmPSI / OneAtmkPa);
			v = v / .062428;
			vp = vp / (101.325 / OneAtminHg);
			d = d / (Math.pow((39.37 / 12), 3) / 2.20462);
			ah = ah / (35.31 / 7 / 2.20462);
			vol = vol * .5885;
			avol = avol * .5885;
		}
		m_units = _val;
	}

		/*public*/ final int calc_stream(int _first_param, int _second_param, int _vol_param)
		{
			int err_code = 0;
			err_code = rangeCheck(_first_param, _second_param);
			if (err_code == 0)
			{
				err_code = calc_pcalc_params(_first_param, _second_param);
			}
			if (err_code == 0)
			{
				//calculate specific volume
				v = (53.352 * (t + 459.67) * (1 + 1.6078 * X / 7000)) / (144 * AtmPress);
				//calculate vapor pressure
				vp = SatVP(Tdp);
				//calculate specific volume of air using compressibility factor Z
				Z = 1;
				if (AtmPress > 19.969)
				{
					Z = Z_Factor(t, AtmPress);
					v = ModIdealV(t, AtmPress, Z, 53.35, 1);
				}
				//calculate density
				d = Density(t, X, AtmPress);
				//calculate absolute humidity
				ah = (d - (144 * AtmPress) / (53.352 * (t + 459.67) * (1 + 1.6078 * X / 7000))) * 7000;
				PPMw = ((X/7000) /(1 + (X/7000))) * 1000000;
				PPMv = (vp * .4912 / AtmPress) * 1000000;
				//PPMw =  ((ah / 7000) / d) * 1000000;
				//PPMv = PPMw / 0.62198;
				if (_vol_param == Fld_idx_vol)
				{
					//calculate actual volumetric flow rate
					avol = vol * v / ((53.352 * (68 + 459.67) * (1 + 1.6078 * 0 / 7000)) / (144 * OneAtmPSI));
				}
				else if (_vol_param == Fld_idx_avol)
				{
					vol = avol * ((53.352 * (68 + 459.67) * (1 + 1.6078 * 0 / 7000)) / (144 * OneAtmPSI)) / v;
				}
				else
				{
					vol = 0;
					avol = 0;
				}
			}
	
			return(err_code);
		}
		/*public*/ final int calc_mixing(AirStream _second)
		{
			final double mix_vol = (vol + _second.vol);
        
			h = (h * vol + _second.h * _second.vol) / mix_vol;
			X = (X * vol + _second.X * _second.vol) / mix_vol;
			vol = mix_vol;
			int ret = calc_stream(Fld_idx_h, Fld_idx_x, Fld_idx_vol);

			return ret;
		}

		protected final int calc_pcalc_params(int _first_param, int _second_param)
		{
			int op_code = 0;
			int err_code = 0;
        
			double RelHc;
			double xc;
			double lb;
			double pws;
			double wts;
			double i;
        
			++_first_param;
			++_second_param;
			if (_first_param > _second_param)
			{
				op_code = 100 * _second_param + _first_param;
			}
			else
				op_code = 100 * _first_param + _second_param;
        
			if (AtmPress < Min_atm_press)
			{
				err_code = 52;
			}
			else if (AtmPress > Max_atm_press)
			{
				err_code = 51;
			}
			else
			{
				while (true)
				{
					if (op_code == 102)
					//	DB/WB
					{
						if (tw > t)
						{
							err_code = 20;
							break;
						}
						X = WBtoGR(t, tw, AtmPress);
						if (X <= 0)
						{
							err_code = 8;
							break;
						}
						if (X > SatGrains(t, AtmPress))
						{
							err_code = 7;
							break;
						}
						Tdp = Dewpoint(X, AtmPress);
						h = Enthalpy(t, X);
						RelH = rh(t, X, AtmPress);
        
					}
					else if (op_code == 103)
					//  dry bulb          %RH
					{
						if (RelH <= 0)
						{
							err_code = 3;
							break;
						}
						X = RHtoGR(t, RelH / 100, AtmPress);
						Tdp = Dewpoint(X, AtmPress);
						h = Enthalpy(t, X);
						if (round(Tdp, 2) > 200)
						{
							err_code = 9;
							break;
						}
						tw = Wetbulb(t, X, AtmPress);
					}
					else if (op_code == 104)
					//	dry bulb          Btu/lb
					{
						X = (h - 0.24 * t) / (1061 + 0.444 * t) * 7000;
						if (X <= 0)
						{
							err_code = 8;
							break;
						}
						if (X > SatGrains(t, AtmPress))
						{
							err_code = 7;
							break;
						}
						Tdp = Dewpoint(X, AtmPress);
						tw = Wetbulb(t, X, AtmPress);
						RelH = rh(t, X, AtmPress);
					}
					else if (op_code == 105)
					//  dry bulb          dewpoint
					{
						if (Tdp > t)
						//	stop from going below 0 gr/lb
						{
							err_code = 21;
							break;
						}
						X = SatGrains(Tdp, AtmPress);
						tw = Wetbulb(t, X, AtmPress);
						h = Enthalpy(t, X);
						if (t == Tdp)
						{
							RelH = 100;
						}
						else
						{
							RelH = rh(t, X, AtmPress);
						}
					}
					else if (op_code == 106 || op_code == 601)
					//  dry bulb          gr/lb
					{
						if (X <= 0)
						{
							err_code = 8;
							break;
						}
        
						Tdp = Dewpoint(X, AtmPress);
						/// fix from JJ added 11/16/2011//////
						if ( Tdp > t )
						{
							err_code  = 7;
							break;
						}
						///// end fix ///////
						h = Enthalpy(t, X);
						RelH = rh(t, X, AtmPress);
						tw = Wetbulb(t, X, AtmPress);
        
					}
					else if (op_code == 203 || op_code == 302)
					//	wet bulb          RH
					{
						if (RelH <= 0)
						{
							err_code = 6;
							break;
						}
						t = tw;
						while (true)
						{
							t = t + 1;
							X = WBtoGR(t, tw, AtmPress);
							RelHc = rh(t, X, AtmPress);
							if (RelHc < RelH)
							{
								t = t - 1;
								break;
							}
						}
						while (true)
						{
							t = t + 0.1;
							X = WBtoGR(t, tw, AtmPress);
							RelHc = rh(t, X, AtmPress);
							if (RelHc < RelH)
							{
								t = t - 0.1;
								break;
							}
						}
						while (true)
						{
							t = t + 0.01;
							X = WBtoGR(t, tw, AtmPress);
							RelHc = rh(t, X, AtmPress);
							if (RelHc < RelH)
							{
								t = t - 0.01;
								break;
							}
						}
						while (true)
						{
							t = t + 0.001;
							X = WBtoGR(t, tw, AtmPress);
							RelHc = rh(t, X, AtmPress);
							if (RelHc < RelH)
							{
								t = t - 0.001;
								break;
							}
						}
						if (RelH == 100)
						{
							t = tw;
							X = SatGrains(t, AtmPress);
							Tdp = t;
						}
						else
						{
							X = WBtoGR(t, tw, AtmPress);
							Tdp = Dewpoint(X, AtmPress);
						}
						RelHc = rh(t, X, AtmPress);
						h = Enthalpy(t, X);
					}
					else if (op_code == 205 || op_code == 502)
					//	wet bulb          Dewpoint
					{
						if (Tdp > tw)
						{
							err_code = 6;
							break;
						}
						X = SatGrains(Tdp, AtmPress);
						if (X <= 0)
						{
							err_code = 8;
							break;
						}
						if (X > SatGrains(tw, AtmPress))
						{
							err_code = 7;
							break;
						}
						t = tw;
						while (true)
						{
							t = t + 10;
							xc = WBtoGR(t, tw, AtmPress);
							if (xc < X)
							{
								t = t - 10;
								break;
							}
						}
						while (true)
						{
							t = t + 1;
							xc = WBtoGR(t, tw, AtmPress);
							if (xc < X)
							{
								t = t - 1;
								break;
							}
						}
						while (true)
						{
							t = t + 0.1;
							xc = WBtoGR(t, tw, AtmPress);
							if (xc < X)
							{
								t = t - 0.1;
								break;
							}
						}
						h = Enthalpy(t, X);
						RelH = rh(t, X, AtmPress);
        
					}
					else if (op_code == 206 || op_code == 602)
					//  wet bulb          gr/lb
					{
						if (X > SatGrains(tw, AtmPress))
						{
							err_code = 7;
							break;
						}
						t = tw;
						while (true)
						{
							t = t + 10;
							xc = WBtoGR(t, tw, AtmPress);
							if (xc < X)
							{
								t = t - 10;
								break;
							}
						}
						while (true)
						{
							t = t + 1;
							xc = WBtoGR(t, tw, AtmPress);
							if (xc < X)
							{
								t = t - 1;
								break;
							}
						}
						while (true)
						{
							t = t + 0.1;
							xc = WBtoGR(t, tw, AtmPress);
							if (xc < X)
							{
								t = t - 0.1;
								break;
							}
						}
						h = Enthalpy(t, X);
						RelH = rh(t, X, AtmPress);
						Tdp = Dewpoint(X, AtmPress);
					}
					else if (op_code == 304 || op_code == 403)
					//  RH          Btu/lb
					{
						if (RelH <= 0)
						{
							err_code = 6;
							break;
						}
						t = -80;
						while (true)
						{
							lb = (h - 0.24 * t) / (1061 + 0.444 * t);
							X = lb * 7000;
							RelHc = rh(t, X, AtmPress);
							if (RelHc != -9999)
							{
								if (round(RelHc, 1) <= RelH)
								{
									break;
								}
							}
							t = t + 10;
						}
						t = t - 10;
						while (true)
						{
							lb = (h - 0.24 * t) / (1061 + 0.444 * t);
							X = lb * 7000;
							RelHc = rh(t, X, AtmPress);
							if (RelHc != -9999)
							{
								if (round(RelHc, 1) <= RelH)
								{
									break;
								}
							}
							t = t + 1;
						}
						t = t - 1;
						while (true)
						{
							lb = (h - 0.24 * t) / (1061 + 0.444 * t);
							X = lb * 7000;
							RelHc = rh(t, X, AtmPress);
							if (RelHc != -9999)
							{
								if (round(RelHc, 1) <= RelH)
								{
									break;
								}
							}
							t = t + 0.1;
						}
						if (X <= 0)
						{
							err_code = 2;
							break;
						}
						Tdp = Dewpoint(X, AtmPress);
						tw = Wetbulb(t, X, AtmPress);
        
					}
					else if (op_code == 305 || op_code == 503)
					//  RH          Dewpoint
					{
						if (RelH <= 0)
						{
							err_code = 6;
							break;
						}
        
						X = SatGrains(Tdp, AtmPress);
        
						if (RelH == 100)
						{
							t = Tdp;
        
						}
						else
						{
							pws = SatVP(Tdp);
							wts = pws * (OneAtmPSI / OneAtminHg) / (RelH / 100);
							t = XTdp(wts);
						}
        
						tw = Wetbulb(t, X, AtmPress);
						h = Enthalpy(t, X);
        
					}
					else if (op_code == 306 || op_code == 603)
					//  RH          gr/lb
					{
						if (X <= 0)
						{
							err_code = 8;
							break;
						}
						if (RelH <= 0)
						{
							err_code = 6;
							break;
						}
						Tdp = Dewpoint(X, AtmPress);
						pws = SatVP(Tdp);
						wts = pws * (OneAtmPSI / OneAtminHg) / (RelH / 100);
						t = XTdp(wts);
						tw = Wetbulb(t, X, AtmPress);
						h = Enthalpy(t, X);
        
					}
					else if (op_code == 405 || op_code == 504)
					//  Btu/lb          Dewpoint
					{
						X = SatGrains(Tdp, AtmPress);
						if (X <= 0)
						{
							err_code = 8;
							break;
						}
        
						if (Enthalpy(Tdp, X) > h)
						{
							err_code = 23;
							break;
						}
						for (i = -80; i <= MaxDB; i += 10)
						{
							if (Enthalpy(i, X) > h)
							{
								t = i - 10;
								break;
							}
						}
						for (i = t; i <= (t + 10); i += 1)
						{
							if (Enthalpy(i, X) > h)
							{
								t = i - 1;
								break;
							}
						}
						for (i = t; i <= (t + 1); i += 0.1)
						{
							if (Enthalpy(i, X) > h)
							{
								t = i - 0.1;
								break;
							}
						}
						for (i = t; i <= (t + 0.1); i += 0.01)
						{
							if (Enthalpy(i, X) > h)
							{
								t = i - 0.01;
								break;
							}
						}
						RelH = rh(t, X, AtmPress);
						if (RelH > 100)
						{
							err_code = 5;
							break;
						}
						if (RelH <= 0)
						{
							err_code = 6;
							break;
						}
						tw = Wetbulb(t, X, AtmPress);
					}
					else if (op_code == 406 || op_code == 604)
					//  Btu/lb          Gr/lb
					{
						if (X <= 0)
						{
							err_code = 8;
							break;
						}
						Tdp = Dewpoint(X, AtmPress);
						if (Enthalpy(Tdp, X) > h)
						{
							err_code = 24;
							break;
						}
						for (i = -80; i <= MaxDB; i += 10)
						{
							if (Enthalpy(i, X) > h)
							{
								t = i - 10;
								break;
							}
						}
						for (i = t; i <= (t + 10); i += 1)
						{
							if (Enthalpy(i, X) > h)
							{
								t = i - 1;
								break;
							}
						}
						for (i = t; i <= (t + 1); i += 0.1)
						{
							if (Enthalpy(i, X) > h)
							{
								t = i - 0.1;
								break;
							}
						}
						for (i = t; i <= (t + 0.1); i += 0.01)
						{
							if (Enthalpy(i, X) > h)
							{
								t = i - 0.01;
								break;
							}
						}
						RelH = rh(t, X, AtmPress);
						tw = Wetbulb(t, X, AtmPress);
					}
					else
					{
						err_code = 30;
						break;
					}
        
					//keep dewpoint equal to or below drybulb
					Tdp = round(Tdp, 4);
        
					if (Tdp > t)
					{
					   Tdp = t;
					}
					break;
				}
			}
			return(err_code);
		}
		protected final int rangeCheck(int _first_param, int _second_param)
		{
			int err_code = 0;
			int op_code = 0;
        
			if ((_first_param == Fld_idx_t) || (_second_param == Fld_idx_t))
			{
				if (t > 1500.00f)
				{
					err_code = 1;
				}
			}
        
			if ((_first_param == Fld_idx_tw) || (_second_param == Fld_idx_tw))
			{
				if (tw >200.00f)
				{
					err_code = 2;
				}
			}
        
			if ((_first_param == Fld_idx_RelH) || (_second_param == Fld_idx_RelH))
			{
				if (RelH >100.00f)
				{
					err_code = 3;
				}
			}
        
			if ((_first_param == Fld_idx_tdp) || (_second_param == Fld_idx_tdp))
			{
				if (Tdp >200.00f)
				{
					err_code = 4;
				}
			}
			++_first_param;
			++_second_param;
			if (_first_param > _second_param)
			{
				op_code = 100 * _second_param + _first_param;
			}
			else
			{
				op_code = 100 * _first_param + _second_param;
			}
        
			if ((_first_param - 1 == Fld_idx_t) || (_second_param-1 == Fld_idx_t))
			{
				if ((t < -80.00f) && (op_code < 200))
				{
					err_code = 5;
				}
			}
			return(err_code);
		}
		
}