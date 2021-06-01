package com.polygongroup.psycit365Play;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
public class MixingActivity extends Activity implements TextWatcher,AdapterView.OnItemSelectedListener
{
	boolean ignoreFieldChanged = false;
	int m_def_In_Units = Units.Units_ip;
	int m_def_Out_Units= Units.Units_ip;
	int previousInUnits = m_def_In_Units;
	double input1Value;
	double input2Value;
	double altitudeValue = 0.0f;
	double mixingValues[] = {0,0,0,0,0,0,0,0,0,0,0,0,0};// [13]
	/*public*/ static String[] mixingStringValues = new String[13];
//	boolean outputUnitsIsMetric = false;
//	boolean inputUnitsIsMetric = false;
	int airARatioValue=100;
	int airBRatioValue=100;
	//int airCRatioValue=airARatioValue+airBRatioValue;
	final static int sliderMax = 200;
	final static int sliderPixelWidth = 300;
	MixingView MixingViewMain = new MixingView();
	
	Spinner 	altChoices,	air1AChoices,	air2AChoices,	airARatioChoices,	air1BChoices,	air2BChoices,	airBRatioChoices;
	EditText 	altEntry,	air1AEntry,		air2AEntry,		airARatioEntry,		air1BEntry,		air2BEntry,		airBRatioEntry;
	
	Spinner	mixed1Choices,	mixed2Choices,	mixed3Choices,	mixed4Choices,	mixed5Choices;
	EditText	mixed1Label,mixed2Label,	mixed3Label,	mixed4Label,	mixed5Label;
	
	TextView mixedAirLabel;
	
	  public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.mixingscreen);
	        
	        altEntry = (EditText) findViewById(R.id.altEntry);
	        air1AEntry= (EditText) findViewById(R.id.air1AEntry);
	        air2AEntry= (EditText) findViewById(R.id.air2AEntry);
	        airARatioEntry= (EditText) findViewById(R.id.airARatioEntry);
	        air1BEntry= (EditText) findViewById(R.id.air1BEntry);
	        air2BEntry= (EditText) findViewById(R.id.air2BEntry);
	        airBRatioEntry= (EditText) findViewById(R.id.airBRatioEntry);
	        mixed1Label= (EditText) findViewById(R.id.mixed1Label);
	        mixed2Label= (EditText) findViewById(R.id.mixed2Label);
	        mixed3Label= (EditText) findViewById(R.id.mixed3Label);
	        mixed4Label= (EditText) findViewById(R.id.mixed4Label);
	        mixed5Label= (EditText) findViewById(R.id.mixed5Label);
	        mixedAirLabel = (TextView) findViewById(R.id.mixedAirLabel);
	        
	        altChoices 	 = (Spinner) findViewById(R.id.altChoices);
	        air1AChoices = (Spinner) findViewById(R.id.air1AChoices);
	        air2AChoices = (Spinner) findViewById(R.id.air2AChoices);
	        airARatioChoices = (Spinner) findViewById(R.id.airARatioChoices);
	        air1BChoices = (Spinner) findViewById(R.id.air1BChoices);
	        air2BChoices = (Spinner) findViewById(R.id.air2BChoices);
	        airBRatioChoices = (Spinner) findViewById(R.id.airBRatioChoices);
	        mixed1Choices= (Spinner) findViewById(R.id.mixed1Choices);
	        mixed2Choices= (Spinner) findViewById(R.id.mixed2Choices);
	        mixed3Choices= (Spinner) findViewById(R.id.mixed3Choices);
	        mixed4Choices= (Spinner) findViewById(R.id.mixed4Choices);
	        mixed5Choices= (Spinner) findViewById(R.id.mixed5Choices);
	        
	        altChoices.setOnItemSelectedListener(this);
	        air1AChoices.setOnItemSelectedListener(this);
	        air2AChoices.setOnItemSelectedListener(this);
	        airARatioChoices.setOnItemSelectedListener(this);
	        air1BChoices.setOnItemSelectedListener(this);
	        air2BChoices.setOnItemSelectedListener(this);
	        airBRatioChoices.setOnItemSelectedListener(this);
	        mixed1Choices.setOnItemSelectedListener(this);
	        mixed2Choices.setOnItemSelectedListener(this);
	        mixed3Choices.setOnItemSelectedListener(this);
	        mixed4Choices.setOnItemSelectedListener(this);
	        mixed5Choices.setOnItemSelectedListener(this);
	        
	        altEntry.addTextChangedListener(this);
	        air1AEntry.addTextChangedListener(this);
	        air2AEntry.addTextChangedListener(this);
	        airARatioEntry.addTextChangedListener(this);
	        air1BEntry.addTextChangedListener(this);
	        air2BEntry.addTextChangedListener(this);
	        airBRatioEntry.addTextChangedListener(this);
	        
	    	airARatioValue = (int)fieldValue(airARatioEntry);
			airBRatioValue = (int)fieldValue(airBRatioEntry);
	        
	    }
	  
	  
	//************************************************************************
		public void setup()
	    {
			ignoreFieldChanged = true;
			
	    	int savedAltIndex = altChoices.getSelectedItemPosition();
	    	
	    	int air1AChoicesIndex = air1AChoices.getSelectedItemPosition();
	    	int air2AChoicesIndex = air2AChoices.getSelectedItemPosition();
	    	int airARatioChoicesIndex = airARatioChoices.getSelectedItemPosition();
	    	int air1BChoicesIndex = air1BChoices.getSelectedItemPosition();
	    	int air2BChoicesIndex = air2BChoices.getSelectedItemPosition();
	    	int airBRatioChoicesIndex = airBRatioChoices.getSelectedItemPosition();
	    	
	    	int mixed1ChoicesIndex = mixed1Choices.getSelectedItemPosition();
	    	int mixed2ChoicesIndex = mixed2Choices.getSelectedItemPosition();
	    	int mixed3ChoicesIndex = mixed3Choices.getSelectedItemPosition();
	    	int mixed4ChoicesIndex = mixed4Choices.getSelectedItemPosition();
	    	int mixed5ChoicesIndex = mixed5Choices.getSelectedItemPosition();
	    	
	    	
	    	if (SettingsActivity.mixingMainSetDefaults)
	    	{
		    	//m_def_In_Units =  SettingsActivity.inputIsMetric?Units.Units_si:Units.Units_ip;
		    	//m_def_Out_Units = SettingsActivity.outputIsMetric?Units.Units_si:Units.Units_ip;
		    	
	    		if(SettingsActivity.inputIsMetric)
		    	{
		    		m_def_In_Units = Units.Units_si;
		    		altChoices.setAdapter(SettingsActivity.adapterMetricInput3);
		    		air1AChoices.setAdapter(SettingsActivity.adapterMetricInput12);
		    		air2AChoices.setAdapter(SettingsActivity.adapterMetricInput12);
		    		airARatioChoices.setAdapter(SettingsActivity.adapterMetricAirFlowRate);
		    		air1BChoices.setAdapter(SettingsActivity.adapterMetricInput12);
		    		air2BChoices.setAdapter(SettingsActivity.adapterMetricInput12);
		    		airBRatioChoices.setAdapter(SettingsActivity.adapterMetricAirFlowRate);
		    	}
		    	else
		    	{
		    		m_def_In_Units = Units.Units_ip;
		    		altChoices.setAdapter(SettingsActivity.adapterUSInput3);
		    		air1AChoices.setAdapter(SettingsActivity.adapterUSInput12);
		    		air2AChoices.setAdapter(SettingsActivity.adapterUSInput12);
		    		airARatioChoices.setAdapter(SettingsActivity.adapterUSAirFlowRate);
		    		air1BChoices.setAdapter(SettingsActivity.adapterUSInput12);
		    		air2BChoices.setAdapter(SettingsActivity.adapterUSInput12);
		    		airBRatioChoices.setAdapter(SettingsActivity.adapterUSAirFlowRate);
		    	}
		    	 
		    	altChoices.setSelection(savedAltIndex);
		    	air1AChoices.setSelection(SettingsActivity.spinner1LeftIndex);
		    	air2AChoices.setSelection(SettingsActivity.spinner2LeftIndex);
		    	airARatioChoices.setSelection(SettingsActivity.spinner3LeftIndex);
		    	air1BChoices.setSelection(SettingsActivity.spinner1LeftIndex);
		    	air2BChoices.setSelection(SettingsActivity.spinner2LeftIndex);
		    	airBRatioChoices.setSelection(SettingsActivity.spinner3LeftIndex);
		    	
		    	if(SettingsActivity.outputIsMetric)
		    	{
		    		m_def_Out_Units = Units.Units_si;
		    		mixed1Choices.setAdapter(SettingsActivity.adapterMetricAirFlowRate);
		    		mixed2Choices.setAdapter(SettingsActivity.adapterMetricOutput);
		    		mixed3Choices.setAdapter(SettingsActivity.adapterMetricOutput);
		    		mixed4Choices.setAdapter(SettingsActivity.adapterMetricOutput);
		    		mixed5Choices.setAdapter(SettingsActivity.adapterMetricOutput);
		    	}

		    	else
		    	{
		    		m_def_Out_Units = Units.Units_ip;
		    		mixed1Choices.setAdapter(SettingsActivity.adapterUSAirFlowRate);
		    		mixed2Choices.setAdapter(SettingsActivity.adapterUSOutput);
		    		mixed3Choices.setAdapter(SettingsActivity.adapterUSOutput);
		    		mixed4Choices.setAdapter(SettingsActivity.adapterUSOutput);
		    		mixed5Choices.setAdapter(SettingsActivity.adapterUSOutput);
		    	}
		    	
		    	mixed1Choices.setSelection(SettingsActivity.spinner5RightIndex);
		    	mixed2Choices.setSelection(SettingsActivity.spinner1RightIndex);
		    	mixed3Choices.setSelection(SettingsActivity.spinner2RightIndex);
		    	mixed4Choices.setSelection(SettingsActivity.spinner3RightIndex);
		    	mixed5Choices.setSelection(SettingsActivity.spinner4RightIndex);
		    	
		    	if(previousInUnits != m_def_In_Units)// get input defaults
		        {
		    		air1AEntry.setText(Integer.toString((int)PsyCalcUtils.fld_init_vals[air1AChoices.getSelectedItemPosition()][m_def_In_Units-1]));
		    		air2AEntry.setText(Integer.toString((int)PsyCalcUtils.fld_init_vals[air2AChoices.getSelectedItemPosition()][m_def_In_Units-1]));
		    		air1BEntry.setText(Integer.toString((int)PsyCalcUtils.fld_init_vals[air1BChoices.getSelectedItemPosition()][m_def_In_Units-1]));
		    		air2BEntry.setText(Integer.toString((int)PsyCalcUtils.fld_init_vals[air2BChoices.getSelectedItemPosition()][m_def_In_Units-1]));
		        }
		    	
		    	SettingsActivity.mixingMainSetDefaults = false;
		        previousInUnits = m_def_In_Units;
		    	ignoreFieldChanged = false;
		    	this.fieldChanged(null,0);
		    	
	    	}
	    	
	    	else
	    	{
	    		altChoices.setSelection(savedAltIndex);
	    		
	    		air1AChoices.setSelection(air1AChoicesIndex);
		    	air2AChoices.setSelection(air2AChoicesIndex);
		    	airARatioChoices.setSelection(airARatioChoicesIndex);
		    	air1BChoices.setSelection(air1BChoicesIndex);
		    	air2BChoices.setSelection(air2BChoicesIndex);
		    	airBRatioChoices.setSelection(airBRatioChoicesIndex);
		    	
		    	mixed1Choices.setSelection(mixed1ChoicesIndex);
		    	mixed2Choices.setSelection(mixed2ChoicesIndex);
		    	mixed3Choices.setSelection(mixed3ChoicesIndex);
		    	mixed4Choices.setSelection(mixed4ChoicesIndex);
		    	mixed5Choices.setSelection(mixed5ChoicesIndex);
		    	
		    	ignoreFieldChanged 	= false;
		        this.fieldChanged(null,0);
	    	}
	    }
		//******************************************************************************************
		public void fieldChanged(Object field, int context)
	    {	
			if(ignoreFieldChanged) return;
			ignoreFieldChanged = true;// avoid conflict with field changes in this function
//			boolean fieldChanged = false;
			MixingViewMain.set_in_units(m_def_In_Units);
			MixingViewMain.set_out_units(m_def_Out_Units);
			if(field==altChoices)
			{
				altEntry.setText(MixingViewMain.on_alt_unit_changed(altChoices.getSelectedItemPosition()));
			}
			else 
			{
				airARatioValue = (int)fieldValue(airARatioEntry);
				airBRatioValue = (int)fieldValue(airBRatioEntry);
				MixingViewMain.setAltitudeValue(fieldValue(altEntry));
			}

			displayCalculations();
	
	    	ignoreFieldChanged = false;
	    }
		void displayCalculations()
		{
			MixingViewMain.set_in_units(m_def_In_Units);
			MixingViewMain.set_out_units(m_def_Out_Units);
			
	    	MixingViewMain.setInput1ValueUnitType(fieldValue(air1AEntry), air1AChoices.getSelectedItemPosition(),MixingViewMain.MIXINGSTREAMA);
	    	MixingViewMain.setInput2ValueUnitType(fieldValue(air2AEntry), air2AChoices.getSelectedItemPosition(),MixingViewMain.MIXINGSTREAMA);
	    	MixingViewMain.setRatioValueUnitType(fieldValue(airARatioEntry), airARatioChoices.getSelectedItemPosition(),MixingViewMain.MIXINGSTREAMA);    	
	    	MixingViewMain.setInput1ValueUnitType(fieldValue(air1BEntry), air1BChoices.getSelectedItemPosition(),MixingViewMain.MIXINGSTREAMB);
	    	MixingViewMain.setInput2ValueUnitType(fieldValue(air2BEntry), air2BChoices.getSelectedItemPosition(),MixingViewMain.MIXINGSTREAMB);
	    	MixingViewMain.setRatioValueUnitType(fieldValue(airBRatioEntry), airBRatioChoices.getSelectedItemPosition(),MixingViewMain.MIXINGSTREAMB);
	 	
			// assume something changed and recalculate
			int error_code = MixingViewMain.calculate(mixingValues, mixingStringValues);
			if(error_code != 0) 
	    	{
				mixedAirLabel.setText(getString(R.string.exceedslimits));
				mixedAirLabel.setTextColor(Color.RED);
	    	}
	    	else 
	    	{
	    		mixedAirLabel.setText(getString(R.string.mixedair));
	    		mixedAirLabel.setTextColor(Color.WHITE);
	    	}

			this.mixed1Label.setText(mixingStringValues[Math.abs(mixed1Choices.getSelectedItemPosition()+11)]);
			this.mixed2Label.setText(mixingStringValues[Math.abs(mixed2Choices.getSelectedItemPosition())]);
			this.mixed3Label.setText(mixingStringValues[Math.abs(mixed3Choices.getSelectedItemPosition())]);
			this.mixed4Label.setText(mixingStringValues[Math.abs(mixed4Choices.getSelectedItemPosition())]);
			this.mixed5Label.setText(mixingStringValues[Math.abs(mixed5Choices.getSelectedItemPosition())]);
		}
	  
	  
	// prevent numeric conversion exception on empty field
	  public double fieldValue(EditText field)
	  {
		  double val;
		  try 
		  {
			  val = Double.valueOf(field.getText().toString());
		  }
		  catch(Exception e)
		  {
			  val = 0.0;
		  }
		  return val;
	  }
////////////////////////////////////////////////////////////////////////////
	// a spinner has changed, go reprocess everything
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) 
	{
		this.fieldChanged(parent,0);	
	//	      Toast.makeText(parent.getContext(), "The selection id:" + view.getId()+ " = "+ id, Toast.LENGTH_LONG).show();
	}
	public void onNothingSelected(AdapterView<?> parent) {} // Do nothing.
	/////////////////////////////////////////////////////////////////////////
	//EditText callbacks 
	//// called every time for a single keystroke within the input fields

	

	 public void afterTextChanged(Editable s) { 
		this.fieldChanged(null,0);}// do computations every keystroke

		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) {}

		public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}	 

		protected void onResume () { super.onResume(); setup(); }
}
