package com.polygongroup.psycit365Play;
import java.lang.Math;
//import net.rim.device.api.util.MathUtilities.pow;

/*public*/ class PsyLib
{
	/*public*/ static final double OneAtmPSI = 14.695948775513;
	/*public*/ static final double OneAtminHg = 29.921299597519;
	/*public*/ static final double OneAtminH20 = 406.782461732239;
	/*public*/ static final double OneAtmmmHg = 760.001009776983;
	/*public*/ static final double OneAtmkPa = 101.325;
	/*public*/ static final double MaxDB = 1500;
	/*public*/ static final double Min_atm_press = 0.7;
	/*public*/ static final double Max_atm_press = 400;

		/*public*/ static final double round(double _num, int _pos)
		{
      
			double _value_ = 0;
			double tmp = 0;
			tmp = Math.pow(10, _pos);
      
			_value_ = (long)(_num * tmp + 0.5);
//			modf(_value_, _value_);
			_value_ = _value_ / tmp;
      
			return _value_;
		}
		/*public*/ final double Z_Factor(double _tdb, double _p)
		{
			double result;
      
			result = (-2.2128E-15 * Math.pow(_tdb, 4) + 3.2829E-12 * Math.pow(_tdb, 3) + -1.9732E-9 * Math.pow(_tdb, 2) + 6.3605E-7 * _tdb - 8.6533E-5) * _p + 0.999375;
      
			return result;
		}
		/*public*/ final double Enthalpy(double _t, double _gr)
		{
			double result;
			result = cairCp(_t) * _t + (_gr / 7000) * (1061 + 0.444 * _t);
			return result;
		}
		/*public*/ final double Xpws(double _temp)
		{
			double result;
			double c1;
			double c2;
			double c3;
			double c4;
			double c5;
			double c6;
			double c7;
			double tr;
      
			if (_temp < -148 || _temp > MaxDB)
			{
				result = -9999;
      
			}
			else
			{
				tr = _temp + 459.67;
				if (_temp < 32.018)
				{
					c1 = -10214.165;
					c2 = -4.8932428;
					c3 = -0.0053765794;
					c4 = 0.00000019202377;
					c5 = 3.5575832E-10;
					c6 = -9.0344688E-14;
					c7 = 4.1635019;
      
				}
				else
				{
					c1 = -10440.397;
					c2 = -11.29465;
					c3 = -0.027022355;
					c4 = 0.00001289036;
					c5 = -2.4780681E-09;
					c6 = 0;
					c7 = 6.5459673;
				}
				result = (((c6 * tr + c5) * tr + c4) * tr + c3) * tr + c2 + c1 / tr;
				result = Math.exp(result + c7 * Math.log(tr));
			}
      
			return result;
		}
		/*public*/ final double XFs(double _atm, double _t)
		{
			double result;
			double ps;
			double Baa;
			double Caa;
			double r;
			double Bprime;
			double Cprime;
			double Caaw;
			double Cwww;
			double Bww;
			double Baw;
			double Caww;
			double kappa;
			double x;
			double ag;
			double bg;
			double cg;
			double dg;
			double eg;
			double a;
			double b;
			double c;
			double log10KO;
			double lnKO;
			double KO;
			double log10KN;
			double lnKN;
			double KN;
			double KA;
			double K;
			double vc;
			double Rcon;
			double xas;
			double lnFs;
			double Fs;
			double p;
			double tk;
			double Fsnew;
			double y;
			double[] j = new double[10];
			double[] f = new double[10];
      
      
			//	check for boiling and set XFs to 1.000000 if > boiling point
			//	y is saturation pressure in psi
			y = (((0.000000019578 * _t - 0.0000061977) * _t + 0.0010557) * _t - 0.069597) * _t + 1.5286;
      
			y = 0.000000019578 * Math.pow(_t, 4) - 0.0000061977 * Math.pow(_t, 3) + 0.0010557 * Math.pow(_t, 2) - 0.069597 * _t + 1.5286;
      
			if (_atm < (0.98 * y))
			//	almost boiling so set XFs to 1.0
			{
				result = 1;
				return result;
			}
			p = _atm * 101325 / OneAtmPSI; // ASHRAE shows 6894.8 but actually
											//	101325/OneAtmPSI0 = 6894.733261
			tk = (_t - 32) * 5 / 9 + 273.15;
      
			//	this function is troublesome at low temp
			if (tk < 173)
				tk = 173;
			//	its limits are 173 Kelvin here
      
			ps = Xpws(_t) * 101325 / OneAtmPSI;
      
			//	Find constants as function of Tk
			Baa = 34.9568 - 6687.72 / tk - 2101410 / tk / tk + 92474600 / tk / tk / tk;
			Caa = 1259.75 - 190905 / tk + 63246700 / tk / tk;
			r = 8314410; // Pa cm3/mol/k
			Bprime = 0.000000007 - 0.00000000147184 * Math.exp(1734.29 / tk);
			Cprime = 1.04E-15 - 3.35297E-18 * Math.exp(3645.09 / tk);
			Bww = r * tk * Bprime;
			Cwww = r * r * tk * tk * (Cprime + (Bprime * Bprime));
			Baw = 32.366097 - 14113.8 / tk - 1244535.0f / tk / tk - 2348789000.0f / Math.pow(tk, 4);
			Caaw = 482.737 + 105678 / tk - 65639400 / tk / tk + 29444200000.0f / Math.pow(tk, 3) - 3193170000000.0f / Math.pow(tk, 4); // corrected by JJ 5/3/97
			Caww = -1000000.0f * Math.exp(-10.728876 + 3478.02 / tk - 383383 / tk / tk + 33406000.0f / Math.pow(tk, 3));
			kappa = (8.875E-11 + 0.0165E-11 * tk);
      
			j[0] = 0;
			if (273.16 <= tk && tk <= 373.16)
			{
				j[0] = 50.88496;
				j[1] = 0.6163813;
				j[2] = 0.001459187;
				j[3] = 0.00002008438;
				j[4] = -0.5847727E-7;
				j[5] = 0.410411E-9;
				j[6] = 0.01967348;
			}
      
			if (373.16 < tk)
			{
				j[0] = 50.884917;
				j[1] = 0.62590623;
				j[2] = 0.0013848668;
				j[3] = 0.21603427E-4;
				j[4] = -0.72087667E-7;
				j[5] = 0.46545054E-9;
				j[6] = 0.019859983;
			}
      
			if (j[0] > 0)
			{
				int i;
      
				x = j[0];
      
				for (i = 1; i <= 5; ++i)
					x = x + j[i] * Math.pow(tk, i);
      
				kappa = (x / (1 + j[6] * tk)) * 1E-11;
			}
      
			K = 0;
			if (tk > 273.16)
			{
				ag = -0.0005943;
				bg = -0.147;
				cg = -0.0512;
				dg = -0.1076;
				eg = 0.8447;
				a = ag;
				b = cg * 1000 / tk + dg;
//#if 1
				c = (bg * (1000 / tk) + eg) * 1000 / tk - 1;
//#else
				//c = bg * Math.pow(1000/tk,2) + eg + 1000 / tk -1;
//#endif
				log10KO = (-b + Math.sqrt(b * b - 4 * a * c)) / 2 * a;
				lnKO = log10KO * Math.log(10);
				KO = Math.exp(lnKO);
      
				ag = -0.1021;
				bg = -0.1482;
				cg = -0.019;
				dg = -0.03741;
				eg = 0.851;
				a = ag;
				b = cg * 1000 / tk + dg;
//#if 1
				c = (bg * (1000 / tk) + eg) * 1000 / tk - 1;
//#else
				//c = bg * Math.pow(1000/tk,2) + eg + 1000 / tk -1;
//#endif
				log10KN = (-b + Math.sqrt(b * b - 4 * a * c)) / 2 * a;
				lnKN = log10KN * Math.log(10);
				KN = Math.exp(lnKN);
      
				KA = 1 / (0.22 / KO + 0.78 / KN);
				K = 0.0001 / KA / 101325;
			}
      
			if (tk < 273.16)
			{
//#if 1
				vc = (0.371611E-9 * tk - 0.249936E-7) * tk + 0.001070003;
//#else
				//vc = 0.001070003 - 0.249936E-7 * tk + 0.371611E-9 * tk * tk;
//#endif
      
			}
			else
			{
				int i;
      
				f[0] = -2403.360201;
				f[1] = -1.40758895;
				f[2] = 0.1068287657;
				f[3] = -0.0002914492351;
				f[4] = 0.373497936E-6;
				f[5] = -0.21203787E-9;
				f[6] = -3.424442728;
				f[7] = 0.01619785;
      
				vc = f[0];
				for (i = 1; i <= 5; ++i)
					vc = vc + f[i] * Math.pow(tk, i);
				vc = (f[6] + f[7] * tk) / vc; // inverts density
			}
			vc = vc * 18015.28;
      
			Rcon = r * r * tk * tk;
      
			Fsnew = 1; // first try
			do
			{
				Fs = Fsnew;
				xas = (p - Fsnew * ps) / p;
				lnFs = ((1 + kappa * ps) * (p - ps) - 0.5 * kappa * (p * p - ps * ps)) * vc * r * tk;
				lnFs = lnFs + Math.log(1 - K * xas * p) * Rcon;
				lnFs = lnFs + (xas * xas) * p * Baa * r * tk;
				lnFs = lnFs - 2 * (xas * xas) * p * Baw * r * tk;
				lnFs = lnFs - (p - ps - xas * xas * p) * Bww * r * tk;
				lnFs = lnFs + (xas * xas * xas) * p * p * Caa;
				lnFs = lnFs + (3 * xas * xas * (1 - 2 * xas) * p * p) * 0.5 * Caaw;
				lnFs = lnFs - 3 * xas * xas * (1 - xas) * p * p * Caww;
				lnFs = lnFs - ((1 + 2 * xas) * ((1 - xas) * (1 - xas)) * p * p - ps * ps) * 0.5 * Cwww;
				lnFs = lnFs - xas * xas * (1 - 3 * xas) * (1 - xas) * p * p * Baa * Bww;
				lnFs = lnFs - 2 * (xas * xas * xas) * (2 - 3 * xas) * p * p * Baa * Baw;
				lnFs = lnFs + 6 * xas * xas * ((1 - xas) * (1 - xas)) * p * p * Bww * Baw;
				lnFs = lnFs - 3 * Math.pow(xas, 4) * p * p * 0.5 * Baa * Baa;
				lnFs = lnFs - 2 * xas * xas * (1 - xas) * (1 - 3 * xas) * p * p * Baw * Baw;
				lnFs = lnFs - (ps * ps - (1 + 3 * xas) * Math.pow(1 - xas, 3) * p * p) * 0.5 * Bww * Bww;
      
				lnFs = lnFs / r / r / tk / tk;
      
				Fsnew = Math.exp(lnFs);
      
			} while (Math.abs((Fs - Fsnew)) >= 0.000001);
      
			result = (((Fsnew) > (1)) ? (Fsnew) : (1));
      
			return result;
		}
		/*public*/ final double Xpwsapp(double _atm, double _tf)
		{
			double result;
      
			result = XFs(_atm, _tf) * Xpws(_tf);
			return result;
		}
		/*public*/ final double SatGrains(double _temp, double _atm)
		{
			double result;
			double pwsapp;
			double wsat=0F;
      
			if (_temp < -148 || _temp > MaxDB)
			{
				result = 20000;
      
			}
			else
			{
				pwsapp = Xpwsapp(_atm, _temp);
				if (pwsapp < _atm && _temp <= 200)
				{
					wsat = 0.62198 * pwsapp * 7000 / (_atm - pwsapp);
      
				}
				else
					wsat = 20000; // otherwise wsat will go negative
			}
      
			result = wsat;
			return result;
		}
		/*public*/ final double SatVP(double _temp)
		{
			double result;
      
			if (_temp < -148 || _temp > MaxDB)
			{
				result = 1;
      
			}
			else
			{
				result = Xpws(_temp);
				result = result / 0.491154;
			}
			return result;
		}
		/*public*/ final double VDW_Volume(double t, double TrialV, double p)
		{
			double Z;
			double a;
			double b;
			double MW;
			double Ru;
			double m;
			double p1;
			double Vn;
			int counter;
      
			a = 727488;
			b = 0.587;
			MW = 28;
			Ru = 1544;
			m = 0.075;
      
			counter = 0;
			Vn = TrialV * MW / m;
			p1 = (Ru * t / (Vn - b) - a / Math.pow(Vn, 2)) / 144;
			while (Math.abs((1 - p1 / p)) >= 0.0005)
			{
				Vn = TrialV * MW / m;
				p1 = (Ru * t / (Vn - b) - a / Math.pow(Vn, 2)) / 144;
				counter = counter + 1;
				if(Math.abs((1 - p1 / p)) >= 0.0005)
				{
					TrialV = TrialV * (1 - 0.5 * (1 - p1 / p));
      
				}
				else
					break;
				if (counter == 40)
				{
					break;
				}
			}
			Z = p * 144 * TrialV * MW / (m * Ru * t);
      
			return Z;
		}
		/*public*/ final double WBtoGR(double _temp_db, double _temp_wb, double _atm)
		{
			double result;
			double Ws;
			double W;
      
			if (_atm < 0.7 || _atm > 400 || _temp_db < -80 || _temp_db > MaxDB || _temp_wb > _temp_db)
			{
				result = -9999;
      
			}
			else
			{
				if (_temp_db < _temp_wb)
				{
					result = -9999;
      
				}
				else
				{
					Ws = SatGrains(_temp_wb, _atm);
					Ws = Ws / 7000;
					W = ((1093 - 0.556 * _temp_wb) * Ws - 0.24 * (_temp_db - _temp_wb)) / (1093 + 0.444 * _temp_db - _temp_wb);
					if (W <= 0)
					{
						result = -9999;
      
					}
					else
						result = W * 7000;
					//	Gr/Lb
				}
			}
      
			return result;
		}
		/*public*/ final double XTdp(double _pws)
		{
			double result;
			double LowTdpF;
			double HiTdpF;
			double HiXpws;
			double Rconst;
			double alpha;
			double Tdpf;
			double Tdpr;
			double DPold;
			double Pwsold;
			double DPnew;
			double Pwsnew;
			double del;
			double t;
			double x;
			double LowXpws;
			int i;
      
			if (_pws < 0)
			{
				result = -9999;
      
			}
			else
			{
				Rconst = 459.67;
				HiTdpF = 392;
				LowTdpF = -148;
      
				LowXpws = Xpws(LowTdpF);
      
				if (_pws < LowXpws)
				{
					result = (LowTdpF + Rconst) / (LowXpws - _pws) * _pws - Rconst;
					return result;
				}
      
				HiXpws = Xpws(HiTdpF);
				if (_pws > HiXpws)
				{
					result = HiTdpF;
					return result;
				}
      
				alpha = Math.log(_pws * 2.036);
				if (_pws >= 0.08865)
				{
					Tdpf = 79.047 + 30.579 * alpha + 1.8893 * alpha * alpha;
      
				}
				else
					Tdpf = 71.98 + 24.873 * alpha + 0.87 * alpha * alpha;
      
				Tdpr = Tdpf + Rconst;
				result = Tdpf;
      
				//	newton search for dp
				DPold = Tdpr;
				t = Tdpr;
				Pwsold = Xpws(t - Rconst);
      
				del = -1;
				i = 0;
				while (true)
				{
					i = i + 1;
					DPnew = DPold + del;
					t = DPnew;
					Pwsnew = Xpws(t - Rconst);
					x = Pwsnew - Pwsold;
					if ((Pwsnew - Pwsold) == 0)
					{
						Tdpr = DPnew;
						break;
					}
      
					del = (DPnew - DPold) / (Pwsnew - Pwsold) * (_pws - Pwsnew);
					if (Math.abs(del) < 0.00001)
					{
						Tdpr = DPnew;
						break;
					}
      
					DPold = DPnew;
					Pwsold = Pwsnew;
      
					if (i > 1000)
					{
						Tdpr = DPnew;
						break;
					}
				}
				result = Tdpr - Rconst;
			}
      
			return result;
		}
		/*public*/ final double Wetbulb(double _temp, double _grains, double _atm)
		{
			double result;
			double W;
			double wetold;
			double wetnew;
			double Wnew;
			double wsstar;
			double pwsapp;
			double tboil;
			double del;
			double atmd;
			double wold;
			int i;
      
			//	Newton search for wet-bulb by solving eq 33 HoF 93
			W = _grains / 7000;
			atmd = _atm * 1;
			//	first guess
			wetold = _temp + 10;
      
			//	***boiling point check removed to avoid negative value at ~200 psia
			//	find boiling point
			tboil = XTdp(atmd);
			tboil = tboil - 5;
      
			//	limit first guess t wet bulbs at least 5 deg F below boiling point
			if (wetold > tboil && atmd < 150)
				wetold = tboil - 5;
      
			while (true)
			{
				wetold = wetold - 10;
				pwsapp = Xpwsapp(_atm, wetold);
				wsstar = 0.62198 * pwsapp / (_atm - pwsapp);
				wold = ((1093 - 0.556 * wetold) * wsstar - 0.24 * (_temp - wetold)) / (1093 + 0.444 * _temp - wetold);
				if (wold < W)
					break;
			}
			wetold = wetold + 10; // went to far by 10 deg F-reset
      
			while (true)
			{
				wetold = wetold - 1;
				pwsapp = Xpwsapp(_atm, wetold);
				wsstar = 0.62198 * pwsapp / (_atm - pwsapp);
				wold = ((1093 - 0.556 * wetold) * wsstar - 0.24 * (_temp - wetold)) / (1093 + 0.444 * _temp - wetold);
				if (wold < W)
					break;
			}
      
			wetold = wetold + 1; // again went to far-reset
			pwsapp = Xpwsapp(_atm, wetold);
			wsstar = 0.62198 * pwsapp / (_atm - pwsapp);
			wold = ((1093 - 0.556 * wetold) * wsstar - 0.24 * (_temp - wetold)) / (1093 + 0.444 * _temp - wetold);
      
			del = 0.1; // sets second guess iteration
			i = 0;
			while (true)
			{
				i = i + 1;
				wetnew = wetold + del;
				pwsapp = Xpwsapp(_atm, wetnew);
				wsstar = 0.62198 * pwsapp / (_atm - pwsapp);
				Wnew = ((1093 - 0.556 * wetnew) * wsstar - 0.24 * (_temp - wetnew)) / (1093 + 0.444 * _temp - wetnew);
				if ((Wnew - wold) == 0) // Or Abs(Wnew - wold) < .0001
					break; // complete convergence
      
				del = (wetnew - wetold) / (Wnew - wold) * (W - Wnew);
				if (Math.abs(del) < 0.00001)
					break; // normal convergence
      
				if (Math.abs((Wnew - wold)) < 0.00001)
					break; // low humidity convergence
      
				wetold = wetnew;
				wold = Wnew;
				if (i > 1000)
					break;
			}
      
			//	This approach can solve equations in the fog zone
			//	and now limits the calculated wet bulb
			//	If wetnew + 100 * 0.00001 > _temp Then Xwetbulb = "#N/A": Exit Function
			//	set to drybulb if calculated slightly high
      
			if (wetnew > _temp)
				wetnew = _temp;
      
			result = wetnew;
			return result;
		}
		/*public*/ final double Mod_Ideal_V(double _tdb, double _p, double _miv_a)
		{
			double result = _miv_a * (_tdb + 459.67) / (_p * 144);
			return result;
		}
		/*public*/ final double rh(double _temp, double _g, double _atm)
		{
			double result;
			double pwsapp;
			double wsat;
			double pwsatm;
			double mu;
      
			pwsapp = Xpwsapp(_atm, _temp);
			wsat = 0.62198 * pwsapp * 7000 / (_atm - pwsapp);
			pwsatm = pwsapp / _atm;
			mu = _g / wsat;
			result = (mu / (1 - (1 - mu) * pwsatm)) * 100;
      
			return result;
		}
		/*public*/ final double RHtoGR(double _temp, double _rh, double _atm)
		{
			double result;
			double pwsapp;
			double pws;
			double W;
			double Adjuster;
			double c0;
			double c1;
			double c2;
			double c3;
			double c4;
			double c5;
			double c6;
			double c7;
			double c8;
      
			if (_temp < -80 || _temp > MaxDB || _rh < 0 || _rh > 1)
			{
				result = -9999;
				return result;
			}
      
			pwsapp = Xpwsapp(_atm, _temp);
			pws = pwsapp * _rh;
			W = 0.62198 * pws * 7000 / (_atm - pws);
			result = W;
      
			Adjuster = 0;
			if (_temp > 32)
			{
				c8 = 4.4062538 * 0.000000000000001;
				c7 = -1.4225633 * 0.000000000001;
				c6 = 5.4244443 * 0.00000000001;
				c5 = 1.98191 * 0.00000001;
				c4 = -1.2608929 * 0.000001;
				c3 = -8.0519039 * 0.00001;
				c2 = 0.0047687314;
				c1 = 0.10440528;
				c0 = -1.9960984;
      
				Adjuster = c8 * Math.pow(_temp, 8) + c7 * Math.pow(_temp, 7) + c6 * Math.pow(_temp, 6) + c5 * Math.pow(_temp, 5) + c4 * Math.pow(_temp, 4) + c3 * Math.pow(_temp, 3) + c2 * Math.pow(_temp, 2) + c1 * _temp + c0;
      
				result = result + Adjuster * _rh * 7000 / 1000000;
			}
      
			return result;
		}
		/*public*/ final double Density(double _temp, double _grains, double _press)
		{
			double result;
      
			result = 0;
			if (_grains >= 0)
			{
				result = (144 * _press / (53.352 * (_temp + 459.67) * (1 + 1.6078 * _grains / 7000))) * (1 + _grains / 7000);
			}
      
			return result;
		}
		/*public*/ final double XTdpapp(double _atm, double _pwsapp)
		{
			double result;
			double tdp;
			double pws;
			double Fs;
			double e;
			double Pwsnew;
			double errfac;
      
			result = -9999;
			if (_pwsapp >= 0)
			{
				pws = _pwsapp;
				tdp = XTdp(pws);
				Fs = XFs(_atm, tdp);
				pws = _pwsapp / Fs;
      
				e = 1; // initialize e to allow Do to continue.
				while (!(e < 0.000001))
				{
					tdp = XTdp(pws);
					Fs = XFs(_atm, tdp);
					Pwsnew = _pwsapp / Fs;
					errfac = 1;
					if (pws > errfac)
						errfac = pws; // permits search at very low vapor pressure
					e = Math.abs(((pws - Pwsnew) / errfac));
					pws = Pwsnew;
				}
      
				result = tdp;
			}
			return result;
		}
		/*public*/ final double Mod_Ideal_V_a(double _z, double _r, double _m)
		{
			double result = _z * _m * _r;
			return result;
		}
		/*public*/ final double Dewpoint(double _x, double _atm)
		{
			double result;
			double W;
			double pwsapp;
      
			if(_x < 0 || _x > 16300)
			{
				result = 201;
      
			}
			else
			{
				W = _x / 7000;
				pwsapp = _atm * W / (0.62198 + W);
				result = XTdpapp(_atm, pwsapp);
			}
      
			return result;
		}
		/*public*/ final double ModIdealV(double Tdb, double p, double Z, double r, double m)
		{
			double _value_ = 0;
			_value_ = Z * m * r * (Tdb + 459.67) / (p * 144);
			return _value_;
		}

		/*public*/ final double HgtoPSI(double _val)
		{
			final double result = _val * (OneAtmPSI / OneAtminHg);
			return result;
		}
		/*public*/ final double PSItoHg(double _val)
		{
			final double result = _val * (OneAtminHg / OneAtmPSI);
			return result;
		}
		/*public*/ final double InH2OtoPSI(double _val)
		{
			final double result = _val * (OneAtmPSI / OneAtminH20);
			return result;
		}
		/*public*/ final double PSItoInH20(double _val)
		{
			final double result = _val * (OneAtminH20 / OneAtmPSI);
			return result;
		}
		/*public*/ final double PSItoALT(double _val)
		{
			double _result_ = 0;
      
			_result_ = 0.00000048709 * Math.pow(_val, 9) + (-0.000014404) * Math.pow(_val, 8) + (-0.0012929) * Math.pow(_val, 7) + 0.091085 * Math.pow(_val, 6) + (-2.5239) * Math.pow(_val, 5) + 38.829 * Math.pow(_val, 4) + (-361.75) * Math.pow(_val, 3) + 2107.1 * Math.pow(_val, 2) + (-8396.1) * _val + 25022.9856;
			_result_ = _result_ * 39.37 / 12.0;
      
			return _result_;
		}
		/*public*/ final double AlttoHg(double _val)
		{
			final double result = AlttoPSI(_val) * (OneAtminHg / OneAtmPSI);
			return result;
		}
		/*public*/ final double AlttoPSI(double _val)
		{
			double result;
			double m;
      
			m = _val * 12 / 39.37;
			result = (-8.9107e-12 * Math.pow(m, 3) + 0.00000053401 * Math.pow(m, 2) - 0.011938 * m + OneAtmkPa);
			result = result * (OneAtmPSI / OneAtmkPa);
      
			return result;
		}

		private double cairCp(double temp)
		{
      
			double cAirValue;
			double Temp_Rdb;
			double C0;
			double C1;
			double C2;
			double C3;
			double C4;
      
			Temp_Rdb = temp + 459.67; // degrees R
      
			C0 = .25521;
			C1 = -7.25734347049141E-05;
			C2 = 1.0337677149363E-07;
			C3 = -4.35628286204278E-11;
			C4 = 6.41212378545722E-15;
      
			cAirValue = C4 * Temp_Rdb * Temp_Rdb * Temp_Rdb * Temp_Rdb + C3 * Temp_Rdb * Temp_Rdb * Temp_Rdb + C2 * Temp_Rdb * Temp_Rdb + C1 * Temp_Rdb + C0;
      
			return cAirValue;
      
		}

}
//#endif

//C++ TO JAVA CONVERTER NOTE: The following #define macro was replaced in-line:
//#define max(a,b) (((a) > (b)) ? (a) : (b))

