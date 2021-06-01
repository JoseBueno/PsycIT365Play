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
public class PointActivity extends Activity implements TextWatcher,AdapterView.OnItemSelectedListener
{
	boolean ignoreFieldChanged = false; 
	int m_def_In_Units = Units.Units_ip;
	int m_def_Out_Units= Units.Units_ip;
	int previousInUnits = m_def_In_Units;
	
	PsyCalcView PsyCalcViewMain = new PsyCalcView();
	FieldNames PsyCalcFieldNamesFormat = new FieldNames();
	
	double input1Value;
	double input2Value;
	double psyCalcValues[] = {0,0,0,0,0, 0,0,0,0,0, 0};// [11]
	/*public*/ static String psyCalcStringValues[] = new String[11];
	
	Spinner altChoices,	input1Choices,	input2Choices,	choices4,	choices5,	choices6,	choices7;
	EditText altEntry,	input1,			input2,			custom4,	custom5,	custom6,	custom7;
	TextView inputLabel;
	 
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pointscreen);
        
        altChoices 	  = (Spinner) findViewById(R.id.altChoices);
        input1Choices = (Spinner) findViewById(R.id.spinner2);
        input2Choices = (Spinner) findViewById(R.id.spinner3);
        choices4 = (Spinner) findViewById(R.id.spinner4);
        choices5 = (Spinner) findViewById(R.id.spinner5);
        choices6 = (Spinner) findViewById(R.id.spinner6);
        choices7 = (Spinner) findViewById(R.id.spinner7);
        
        altEntry= (EditText) findViewById(R.id.altEntry);
        input1 	= (EditText) findViewById(R.id.text2);
        input2 	= (EditText) findViewById(R.id.text3);
        custom4 = (EditText) findViewById(R.id.text4);
        custom5 = (EditText) findViewById(R.id.text5);
        custom6 = (EditText) findViewById(R.id.text6);
        custom7 = (EditText) findViewById(R.id.text7);
        inputLabel = (TextView) findViewById(R.id.inputLabel);
        
        altEntry.addTextChangedListener(this);
        input1.addTextChangedListener(this);
        input2.addTextChangedListener(this);
        
        altChoices.setOnItemSelectedListener(this);
        input1Choices.setOnItemSelectedListener(this);
        input2Choices.setOnItemSelectedListener(this);
        choices4.setOnItemSelectedListener(this);
        choices5.setOnItemSelectedListener(this);
        choices6.setOnItemSelectedListener(this);
        choices7.setOnItemSelectedListener(this);     
	}
//////////////////////////////////////////////////////////////////
	public void setup()
	{
    	ignoreFieldChanged = true;
        
    	int savedAltIndex = altChoices.getSelectedItemPosition();
    	int input1ChoicesIndex = input1Choices.getSelectedItemPosition();
    	int input2ChoicesIndex = input2Choices.getSelectedItemPosition();
    	int choices4Index = choices4.getSelectedItemPosition();
    	int choices5Index = choices5.getSelectedItemPosition();
    	int choices6Index = choices6.getSelectedItemPosition();
    	int choices7Index = choices7.getSelectedItemPosition();
    	
    	if(SettingsActivity.pointMainSetDefaults)
    	{
	    	if(SettingsActivity.inputIsMetric)
	    	{
	    		m_def_In_Units = Units.Units_si; 
	    		altChoices.setAdapter(SettingsActivity.adapterMetricInput3);
	 	        input1Choices.setAdapter(SettingsActivity.adapterMetricInput12);
	 	        input2Choices.setAdapter(SettingsActivity.adapterMetricInput12);
	    	}
	    	else 
	    	{
	    		m_def_In_Units = Units.Units_ip; 
		    	altChoices.setAdapter(SettingsActivity.adapterUSInput3);
		        input1Choices.setAdapter(SettingsActivity.adapterUSInput12);
		        input2Choices.setAdapter(SettingsActivity.adapterUSInput12);
	    	}
	    	
	    	if(SettingsActivity.outputIsMetric)
	     	{
	     		m_def_Out_Units = Units.Units_si; 
	  	        choices4.setAdapter(SettingsActivity.adapterMetricOutput);
	  	        choices5.setAdapter(SettingsActivity.adapterMetricOutput);
	  	        choices6.setAdapter(SettingsActivity.adapterMetricOutput);
	  	        choices7.setAdapter(SettingsActivity.adapterMetricOutput);
	     	}
	     	else
	     	{
	     		m_def_Out_Units = Units.Units_ip;
	 	        choices4.setAdapter(SettingsActivity.adapterUSOutput);
	 	        choices5.setAdapter(SettingsActivity.adapterUSOutput);
	 	        choices6.setAdapter(SettingsActivity.adapterUSOutput);
	 	        choices7.setAdapter(SettingsActivity.adapterUSOutput);
	     	}
	    	
	    	altChoices.setSelection(savedAltIndex);
	        input1Choices.setSelection(SettingsActivity.spinner1LeftIndex);
	        input2Choices.setSelection(SettingsActivity.spinner2LeftIndex);
	        choices4.setSelection(SettingsActivity.spinner1RightIndex);
	        choices5.setSelection(SettingsActivity.spinner2RightIndex);
	        choices6.setSelection(SettingsActivity.spinner3RightIndex);
	        choices7.setSelection(SettingsActivity.spinner4RightIndex);
	        
	        if(previousInUnits != m_def_In_Units)// get input defaults
	        {
	        	input1.setText(Integer.toString((int)PsyCalcUtils.fld_init_vals[input1Choices.getSelectedItemPosition()][m_def_In_Units-1]));
	        	input2.setText(Integer.toString((int)PsyCalcUtils.fld_init_vals[input2Choices.getSelectedItemPosition()][m_def_In_Units-1]));
	 //       	altEntry.setText(Integer.toString((int)PsyCalcUtils.fld_init_vals[altChoices.getSelectedIndex()][m_def_In_Units-1]));
	        }
	        
	        SettingsActivity.pointMainSetDefaults = false;
	        previousInUnits 	= m_def_In_Units;
	        ignoreFieldChanged 	= false;
	        this.fieldChanged(null,0);
    	}
    	else
    	{
    		altChoices.setSelection(savedAltIndex);
    		
	        input1Choices.setSelection(input1ChoicesIndex);
	        input2Choices.setSelection(input2ChoicesIndex);
	        
	        choices4.setSelection(choices4Index);
	        choices5.setSelection(choices5Index);
	        choices6.setSelection(choices6Index);
	        choices7.setSelection(choices7Index);
	        
	        ignoreFieldChanged 	= false;
	        this.fieldChanged(null,0);
	        
    	}
	}

	public void fieldChanged(Object field, int context)
    {	
    	if(ignoreFieldChanged) return;
    	PsyCalcViewMain.set_in_units(m_def_In_Units);
    	PsyCalcViewMain.set_out_units(m_def_Out_Units);
    	if(field == altChoices)
    	{
   		     altEntry.setText(PsyCalcViewMain.on_alt_unit_changed(altChoices.getSelectedItemPosition()));//, altEntry.getValue()));  	
    	}
    	else
    	{
    		//if (field == altEntry) 
    			PsyCalcViewMain.setAltitudeValue(fieldValue(altEntry));
	    	PsyCalcViewMain.setInput1ValueUnitTypes(fieldValue(input1), input1Choices.getSelectedItemPosition());
	    	PsyCalcViewMain.setInput2ValueUnitTypes(fieldValue(input2), input2Choices.getSelectedItemPosition());
	    	int error_code = PsyCalcViewMain.calculate(psyCalcValues, psyCalcStringValues);
	    	if(error_code != 0) 
	    	{
	    		inputLabel.setText(getString(R.string.exceedslimits));
	    		inputLabel.setTextColor(Color.RED);
	    	}
	    	else 
	    	{
	    		inputLabel.setText(getString(R.string.input));
	    		inputLabel.setTextColor(Color.WHITE);
	    	}
	    	custom4.setText(psyCalcStringValues[choices4.getSelectedItemPosition()]);
	    	custom5.setText(psyCalcStringValues[choices5.getSelectedItemPosition()]);
	    	custom6.setText(psyCalcStringValues[choices6.getSelectedItemPosition()]);
	    	custom7.setText(psyCalcStringValues[choices7.getSelectedItemPosition()]);
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
////////////////////////////////////////////////////////////////////////////
	// a spinner has changed, go reprocess everything
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) 
    {
    	this.fieldChanged(parent,0);	
//	      Toast.makeText(parent.getContext(), "The selection id:" + view.getId()+ " = "+ id, Toast.LENGTH_LONG).show();
	}
    /////////////////////////////////////////////////////////////////////////
    //EditText callbacks 
    //// called every time for a single keystroke within the input fields
    public void onNothingSelected(AdapterView<?> parent) {} // Do nothing.
	

	 public void afterTextChanged(Editable s) { this.fieldChanged(null,0);}// do computations every keystroke

	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) {}

	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}	 

	protected void onResume () { super.onResume(); setup(); }
}
