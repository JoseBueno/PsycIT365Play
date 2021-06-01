package com.polygongroup.psycit365Play;

public final class NumFormat
{
	public static final int Max_before_dec=15;
	public static final int Max_after_dec=15;
	public boolean allow_neg = true;
	public  int before_dec = 15;
	public  int after_dec = 15;
	
	public NumFormat()
	{
		this(true, Max_before_dec, Max_after_dec);
	}
	
	public NumFormat(boolean _allow_neg, int _before_dec)
	{
		this(_allow_neg, _before_dec, Max_after_dec);
	}
	public NumFormat(boolean _allow_neg)
	{
		this(_allow_neg, Max_before_dec, Max_after_dec);
	}

	public NumFormat(boolean _allow_neg, int _before_dec, int _after_dec)
	{
		allow_neg = _allow_neg;
		before_dec = _before_dec;
		after_dec = _after_dec;
	}
}
