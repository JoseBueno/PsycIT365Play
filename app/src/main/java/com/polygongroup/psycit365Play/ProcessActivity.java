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

public class ProcessActivity extends Activity implements TextWatcher, AdapterView.OnItemSelectedListener 
{
	
	boolean ignoreFieldChanged = false;
	int m_def_In_Units = Units.Units_ip;
	int m_def_Out_Units= Units.Units_ip;
	int previousInUnits = m_def_In_Units;
	double enteringInput1Value;
	double enteringInput2Value;
	double leavingInput1Value;
	double leavingInput2Value;
	double altitudeValue = 0.0f;
	double processValues[] = {
			0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
			0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};// [16]
	/*public*/ static String processStringValues[] = new String[16];
	ProcessView ProcessViewMain = new ProcessView();
	
	Spinner altChoices, leftChoice1, leftChoice2, rightChoice1, rightChoice2, bottomChoice;
	EditText altEntry, leftEntry1,leftEntry2, rightEntry1, rightEntry2, bottomEntry;
	EditText
		textLine1A,textLine1B,textLine1C,
		textLine2A,textLine2B,textLine2C,
		textLine3A,textLine3B,textLine3C,
		textLine4A,textLine4B,textLine4C,
		textLine5A,textLine5B,textLine5C;
	
	TextView processLoadsLabel;
	 
	  public void onCreate(Bundle savedInstanceState) 
	  {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.processscreen);
	        
	        altChoices = (Spinner) findViewById(R.id.altChoices);
	        leftChoice1 = (Spinner) findViewById(R.id.leftChoice1);
	        leftChoice2 = (Spinner) findViewById(R.id.leftChoice2);
	        rightChoice1 = (Spinner) findViewById(R.id.rightChoice1);
	        rightChoice2 = (Spinner) findViewById(R.id.rightChoice2);
	        bottomChoice = (Spinner) findViewById(R.id.bottomChoice);
	        
	        altEntry = (EditText) findViewById(R.id.altEntry);
	        leftEntry1 = (EditText) findViewById(R.id.leftEntry1);
	        leftEntry2 = (EditText) findViewById(R.id.leftEntry2);
	        rightEntry1 = (EditText) findViewById(R.id.rightEntry1);
	        rightEntry2 = (EditText) findViewById(R.id.rightEntry2);
	        bottomEntry = (EditText) findViewById(R.id.bottomEntry);
	        
	        altChoices.setOnItemSelectedListener(this);
	        leftChoice1.setOnItemSelectedListener(this);
	        leftChoice2.setOnItemSelectedListener(this);
	        rightChoice1.setOnItemSelectedListener(this);
	        rightChoice2.setOnItemSelectedListener(this);
	        bottomChoice.setOnItemSelectedListener(this);	
	        
	        altEntry.addTextChangedListener(this);
	        leftEntry1.addTextChangedListener(this);
	        leftEntry2.addTextChangedListener(this);
	        rightEntry1.addTextChangedListener(this);
	        rightEntry2.addTextChangedListener(this);
	        bottomEntry.addTextChangedListener(this);	
	        
	        //altChoices.setBackgroundColor(Color.rgb(0x00, 0xcc, 0xff));
	        
	        processLoadsLabel = (TextView)findViewById(R.id.processLoadsLabel);
	        
	        textLine1A = (EditText)findViewById(R.id.line1Label);
	        textLine1B = (EditText)findViewById(R.id.line1Value);
	        textLine1C = (EditText)findViewById(R.id.line1Units);
	        
	        textLine2A = (EditText)findViewById(R.id.line2Label);
	        textLine2B = (EditText)findViewById(R.id.line2Value);
	        textLine2C = (EditText)findViewById(R.id.line2Units);
	        
	        textLine3A = (EditText)findViewById(R.id.line3Label);
	        textLine3B = (EditText)findViewById(R.id.line3Value);
	        textLine3C = (EditText)findViewById(R.id.line3Units);
	        
	        textLine4A = (EditText)findViewById(R.id.line4Label);
	        textLine4B = (EditText)findViewById(R.id.line4Value);
	        textLine4C = (EditText)findViewById(R.id.line4Units);
	        
	        textLine5A = (EditText)findViewById(R.id.line5Label);
	        textLine5B = (EditText)findViewById(R.id.line5Value);
	        textLine5C = (EditText)findViewById(R.id.line5Units);
	    }

	public void setup()
    {
		ignoreFieldChanged = true;
		
    	int savedAltIndex = altChoices.getSelectedItemPosition();
    	
    	int leftChoice1Index = leftChoice1.getSelectedItemPosition();
    	int leftChoice2Index = leftChoice2.getSelectedItemPosition();
    	int rightChoice1Index = rightChoice1.getSelectedItemPosition();
    	int rightChoice2Index = rightChoice2.getSelectedItemPosition();
    	int bottomChoiceIndex = bottomChoice.getSelectedItemPosition();
    	
    	if(SettingsActivity.processMainSetDefaults)
    	{
	    	//m_def_In_Units =  SettingsActivity.inputIsMetric?Units.Units_si:Units.Units_ip;
	    	//m_def_Out_Units = SettingsActivity.outputIsMetric?Units.Units_si:Units.Units_ip;
	    	
	    	if(SettingsActivity.inputIsMetric)
	    	{
	    		m_def_In_Units = Units.Units_si;
	    		 altChoices.setAdapter(SettingsActivity.adapterMetricInput3);
	    		 leftChoice1.setAdapter(SettingsActivity.adapterMetricInput12);
	    		 leftChoice2.setAdapter(SettingsActivity.adapterMetricInput12);
	    		 rightChoice1.setAdapter(SettingsActivity.adapterMetricInput12);
	    		 rightChoice2.setAdapter(SettingsActivity.adapterMetricInput12);
	    		 bottomChoice.setAdapter(SettingsActivity.adapterMetricAirFlowRate);
	    	}
	    	
	    	else
	    	{
	    		m_def_In_Units = Units.Units_ip;
	    		 altChoices.setAdapter(SettingsActivity.adapterUSInput3);
	       		 leftChoice1.setAdapter(SettingsActivity.adapterUSInput12);
	    		 leftChoice2.setAdapter(SettingsActivity.adapterUSInput12);
	    		 rightChoice1.setAdapter(SettingsActivity.adapterUSInput12);
	    		 rightChoice2.setAdapter(SettingsActivity.adapterUSInput12);
	    		 bottomChoice.setAdapter(SettingsActivity.adapterUSAirFlowRate);
	    	}
	    	
	    	if(SettingsActivity.outputIsMetric)
	     	{
	     		m_def_Out_Units = Units.Units_si; 
	     	}
	    	else
	    	{
	    		m_def_Out_Units = Units.Units_ip;
	    	}
	    	
	    	altChoices.setSelection(savedAltIndex);
	    	leftChoice1.setSelection(SettingsActivity.spinner1LeftIndex);
	    	leftChoice2.setSelection(SettingsActivity.spinner2LeftIndex);
	    	rightChoice1.setSelection(SettingsActivity.spinner1LeftIndex);
	    	rightChoice2.setSelection(SettingsActivity.spinner2LeftIndex);
	    	bottomChoice.setSelection(SettingsActivity.spinner3LeftIndex);
	    	
	    	if(previousInUnits != m_def_In_Units)// get input defaults
	        {
	    		leftEntry1.setText(Integer.toString((int)PsyCalcUtils.fld_init_vals[leftChoice1.getSelectedItemPosition()][m_def_In_Units-1]));
	    		leftEntry2.setText(Integer.toString((int)PsyCalcUtils.fld_init_vals[leftChoice2.getSelectedItemPosition()][m_def_In_Units-1]));
	    		rightEntry1.setText(Integer.toString((int)PsyCalcUtils.fld_init_vals[rightChoice1.getSelectedItemPosition()][m_def_In_Units-1]));
	    		rightEntry2.setText(Integer.toString((int)PsyCalcUtils.fld_init_vals[rightChoice2.getSelectedItemPosition()][m_def_In_Units-1]));
	    		bottomEntry.setText(Integer.toString((int)100));
	        }
	    	
	    	SettingsActivity.processMainSetDefaults = false;
	        previousInUnits = m_def_In_Units;
	    	ignoreFieldChanged = false;
	    	this.fieldChanged(null,0);
    	}
    	
    	else
    	{
    		altChoices.setSelection(savedAltIndex);
    		
    		leftChoice1.setSelection(leftChoice1Index);
	    	leftChoice2.setSelection(leftChoice2Index);
	    	rightChoice1.setSelection(rightChoice1Index);
	    	rightChoice2.setSelection(rightChoice2Index);
	    	bottomChoice.setSelection(bottomChoiceIndex);
	    	
    		ignoreFieldChanged = false;
	    	this.fieldChanged(null,0);
    	}
    }
	  
	  public void fieldChanged(Object field, int context)
	    {	
			if(ignoreFieldChanged)return;
			ProcessViewMain.set_in_units(m_def_In_Units);
			ProcessViewMain.set_out_units(m_def_Out_Units);
			if(field == altChoices)
	    	{
	    		altEntry.setText(ProcessViewMain.on_alt_unit_changed(altChoices.getSelectedItemPosition()));
	  //  		Dialog.alert("After: "+altEntry.getValue());    	
	    	}
	    	else
	    	{
	    		if (field == altEntry) ProcessViewMain.setAltitudeValue(fieldValue(altEntry));
		    	ProcessViewMain.setInputValueUnitType(fieldValue(leftEntry1), leftChoice1.getSelectedItemPosition(),DefineConstants.PROCESS_ENTER,DefineConstants.PROCESS_INPUT_1);
		    	ProcessViewMain.setInputValueUnitType(fieldValue(leftEntry2), leftChoice2.getSelectedItemPosition(),DefineConstants.PROCESS_ENTER,DefineConstants.PROCESS_INPUT_2);
		    	ProcessViewMain.setInputValueUnitType(fieldValue(rightEntry1), rightChoice1.getSelectedItemPosition(),DefineConstants.PROCESS_LEAVING,DefineConstants.PROCESS_INPUT_1);
		    	ProcessViewMain.setInputValueUnitType(fieldValue(rightEntry2), rightChoice2.getSelectedItemPosition(),DefineConstants.PROCESS_LEAVING,DefineConstants.PROCESS_INPUT_2);
		    	ProcessViewMain.setRatioValueUnitType(fieldValue(bottomEntry), bottomChoice.getSelectedItemPosition());
		
		    	int error_code = ProcessViewMain.calculate(processValues, processStringValues);
		    	if(error_code != 0) 
		    	{
		    		processLoadsLabel.setText(getString(R.string.exceedslimits));//""*** Exceeds Limits ***");
		    		processLoadsLabel.setTextColor(Color.RED);
		    	}
		    	else 
		    	{
		    		processLoadsLabel.setText(getString(R.string.processloads));//"" Process Loads...");
		    		processLoadsLabel.setTextColor(Color.WHITE);
		    	}
		    	// *** text below will change with metric
		    	if(m_def_Out_Units == Units.Units_si)
		    	{
		    		//textLine1.setText(processStringValues[11]+" kW Total");
		    		textLine1A.setText(getString(R.string.total));//""Total");
		    		textLine1B.setText(processStringValues[11]);
		    		textLine1C.setText(getString(R.string.kw));//""kW");
		    		
		    
		    		//textLine2.setText(processStringValues[12]+" kW Sensible");
		    		textLine2A.setText(getString(R.string.sensible));//""Sensible");
		    		textLine2B.setText(processStringValues[12]);
		    		textLine2C.setText(getString(R.string.kw));//""kW");
		    		
		    		//textLine3.setText(processStringValues[13]+" kW Latent");
		    		textLine3A.setText(getString(R.string.latent));//""Latent");
		    		textLine3B.setText(processStringValues[13]);
		    		textLine3C.setText(getString(R.string.kw));//""kW");
		    		
		    		//textLine4.setText(processStringValues[14]+" kg/hr Moisture");
		    		textLine4A.setText(getString(R.string.moisture));//""Moisture");
		    		textLine4B.setText(processStringValues[14]);
		    		textLine4C.setText(getString(R.string.kghr));//""kg/hr");
		    		
		    		textLine5A.setText(" ");
		    		textLine5B.setText(" ");
		    		textLine5C.setText(" ");
		    		
		    	}
		    	else
		    	{
		    		textLine1A.setText(getString(R.string.total));//""Total");
		    		textLine1B.setText(processStringValues[11]);
		    		textLine1C.setText(getString(R.string.btuh));//""Btuh");
		    		
		    		textLine2A.setText(" ");
		    		textLine2B.setText(processStringValues[15]);
		    		textLine2C.setText(getString(R.string.tons));//""Tons");
		    		
		    		textLine3A.setText(getString(R.string.sensible));//""Sensible");
		    		textLine3B.setText(processStringValues[12]);
		    		textLine3C.setText(getString(R.string.btuh));//"Btuh");
		    		
		    		textLine4A.setText(getString(R.string.latent));//"Latent");
		    		textLine4B.setText(processStringValues[13]);
		    		textLine4C.setText(getString(R.string.btuh));//"Btuh");
		    		
		    		textLine5A.setText(getString(R.string.moisture));//"Moisture");	
		    		textLine5B.setText(processStringValues[14]);
		    		textLine5C.setText(getString(R.string.lbhr));//lb/hr
		    	}
	    	}
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
	  
	  public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) 
		{
			this.fieldChanged(parent,0);	
		//	      Toast.makeText(parent.getContext(), "The selection id:" + view.getId()+ " = "+ id, Toast.LENGTH_LONG).show();
		}
	    public void onNothingSelected(AdapterView<?> parent) {} // Do nothing.
		    
/////////////////////////////////////////////////////////////////////////
//EditText callbacks 
//// called every time for a single keystroke within the input fields

public void afterTextChanged(Editable s) { this.fieldChanged(null,0);}// do computations every keystroke
public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) {}
public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}	 
protected void onResume () { super.onResume(); setup(); }
	
}
