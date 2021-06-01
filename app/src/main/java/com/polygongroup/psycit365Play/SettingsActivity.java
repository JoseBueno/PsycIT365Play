package com.polygongroup.psycit365Play;
// this activity controls the global settings for metric/english and spinner selections
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ToggleButton;

public class SettingsActivity extends Activity implements AdapterView.OnItemSelectedListener, OnClickListener
{
	//static PersistentObject persist;
	public static SharedPreferences persist;

	public PointActivity psyCalcScreen;
	public MixingActivity mixingScreen;
	public ProcessActivity processScreen;
	
	public static boolean pointMainSetDefaults = false;
	public static boolean mixingMainSetDefaults = false;
	public static boolean processMainSetDefaults = false;
	
	// english/metric mode flags
	public static boolean inputIsMetric = false;
	public static boolean outputIsMetric = false;
	
	public static boolean currentinputIsMetric = false;
	public static boolean currentoutputIsMetric = false;
	
	//measurement unit selection choices
	public static int spinner1LeftIndex = 0;
	public static int spinner2LeftIndex = 1;
	public static int spinner3LeftIndex = 0;
	public static int spinner1RightIndex = 0;
	public static int spinner2RightIndex = 1;
	public static int spinner3RightIndex = 2;
	public static int spinner4RightIndex = 3;
	public static int spinner5RightIndex = 0;
	
	// menu choices measurement units in english
	public static String USInput12[], USInput3[], USOutput[], USAirFlowRate[];
	// menu choices measurement units in metric
	public static String MetricInput12[],MetricInput3[], MetricOutput[], MetricAirFlowRate[];

	Spinner spinner1Left ,spinner2Left,spinner3Left;
	Spinner spinner1Right,spinner2Right , spinner3Right,spinner4Right ,spinner5Right;
	ToggleButton inputButton, outputButton;
	Button setDefaultsButton;
	
	public static ArrayAdapter<CharSequence> adapterUSInput12;
	public static ArrayAdapter<CharSequence> adapterMetricInput12;
	public static ArrayAdapter<CharSequence> adapterUSInput3;
	public static ArrayAdapter<CharSequence> adapterMetricInput3;	
	public static ArrayAdapter<CharSequence> adapterUSOutput;
	public static ArrayAdapter<CharSequence> adapterMetricOutput;
	public static ArrayAdapter<CharSequence> adapterUSAirFlowRate;
	public static ArrayAdapter<CharSequence> adapterMetricAirFlowRate;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingsscreen);

        USInput12 		= getResources().getStringArray(R.array.USInput12);
    	USInput3		= getResources().getStringArray(R.array.USInput3);
    	USOutput		= getResources().getStringArray(R.array.USOutput);
    	USAirFlowRate	= getResources().getStringArray(R.array.USAirFlowRate);
    	
    	// menu choices measurement units in metric
    	MetricInput12	= getResources().getStringArray(R.array.MetricInput12);
    	MetricInput3	= getResources().getStringArray(R.array.MetricInput3);
    	MetricOutput	= getResources().getStringArray(R.array.MetricOutput);
    	MetricAirFlowRate= getResources().getStringArray(R.array.MetricAirFlowRate);

    	spinner1Left = (Spinner) findViewById(R.id.spinner1Left);
        spinner2Left = (Spinner) findViewById(R.id.spinner2Left);
        spinner3Left = (Spinner) findViewById(R.id.spinner3Left);
        spinner1Right = (Spinner) findViewById(R.id.spinner1Right);
        spinner2Right = (Spinner) findViewById(R.id.spinner2Right);
        spinner3Right = (Spinner) findViewById(R.id.spinner3Right);
        spinner4Right = (Spinner) findViewById(R.id.spinner4Right);
        spinner5Right = (Spinner) findViewById(R.id.spinner5Right);
		inputButton = (ToggleButton) findViewById(R.id.toggleButtonLeft);
		outputButton = (ToggleButton) findViewById(R.id.toggleButtonRight);
		setDefaultsButton = (Button) findViewById(R.id.buttonSetAsDefaults);

		adapterUSInput12 	= new ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_item, USInput12);
		adapterMetricInput12= new ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_item, MetricInput12);	
		adapterUSInput3 	= new ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_item, USInput3);
		adapterMetricInput3 = new ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_item, MetricInput3);	
		adapterUSOutput 	= new ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_item, USOutput);
		adapterMetricOutput = new ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_item, MetricOutput);	
		adapterUSAirFlowRate= new ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_item, USAirFlowRate);	
		adapterMetricAirFlowRate = new ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_item, MetricAirFlowRate);	
		
		adapterUSInput12.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapterMetricInput12.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapterUSInput3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapterMetricInput3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapterUSOutput.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapterMetricOutput.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapterUSAirFlowRate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapterMetricAirFlowRate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		   // set buttons and selection choices here
		persist = getSharedPreferences("SettingsPreferences",0);
		getPersistentStore();
		restoreChoicesIndex();
		setChoices();
		
		spinner1Left.setOnItemSelectedListener(this);
		spinner2Left.setOnItemSelectedListener(this);
		spinner3Left.setOnItemSelectedListener(this);
		spinner1Right.setOnItemSelectedListener(this);
		spinner2Right.setOnItemSelectedListener(this);
		spinner3Right.setOnItemSelectedListener(this);
		spinner4Right.setOnItemSelectedListener(this);
		spinner5Right.setOnItemSelectedListener(this);
		
		inputButton.setOnClickListener(this);
		outputButton.setOnClickListener(this);
		setDefaultsButton.setOnClickListener(this);
		
		pointMainSetDefaults = true;
		mixingMainSetDefaults = true;
		processMainSetDefaults = true;
	}
	
	void setChoices()
	{
//		ignoreFieldChanged = true;
	// have to save and restore current choice settings cause setChoices wipes out setting
//		saveChoicesIndex();
		// set selections based on metric/english
		if(currentinputIsMetric)
		{
			//inputButton.setChecked(inputIsMetric);
			spinner1Left.setAdapter(adapterMetricInput12);
			spinner2Left.setAdapter(adapterMetricInput12);
			spinner3Left.setAdapter(adapterMetricAirFlowRate);
		}
		else// it's english
		{
			//inputButton.setChecked(inputIsMetric);
			spinner1Left.setAdapter(adapterUSInput12);
			spinner2Left.setAdapter(adapterUSInput12);
			spinner3Left.setAdapter(adapterUSAirFlowRate);
		} 
		if(currentoutputIsMetric)
		{
			//outputButton.setChecked(outputIsMetric);
			spinner1Right.setAdapter(adapterMetricOutput);
			spinner2Right.setAdapter(adapterMetricOutput);
			spinner3Right.setAdapter(adapterMetricOutput);
			spinner4Right.setAdapter(adapterMetricOutput);
			spinner5Right.setAdapter(adapterMetricAirFlowRate);
		}
		else // it's english
		{
			//outputButton.setChecked(outputIsMetric);
			spinner1Right.setAdapter(adapterUSOutput);
			spinner2Right.setAdapter(adapterUSOutput);
			spinner3Right.setAdapter(adapterUSOutput);
			spinner4Right.setAdapter(adapterUSOutput);
			spinner5Right.setAdapter(adapterUSAirFlowRate);
		} 
		restoreChoicesIndex();
//		ignoreFieldChanged = false;
	}
	// get saved values from persistence
	void getPersistentStore()
	{
		spinner1LeftIndex = persist.getInt("spinner1LeftIndex",0);
		spinner2LeftIndex = persist.getInt("spinner2LeftIndex",1);
		spinner3LeftIndex = persist.getInt("spinner3LeftIndex",0);
		spinner1RightIndex = persist.getInt("spinner1RightIndex",0);
		spinner2RightIndex = persist.getInt("spinner2RightIndex",1);
		spinner3RightIndex = persist.getInt("spinner3RightIndex",2);
		spinner4RightIndex = persist.getInt("spinner4RightIndex",3);
		spinner5RightIndex = persist.getInt("spinner5RightIndex",0);
		inputIsMetric = persist.getBoolean("InputIsMetric",false);
		outputIsMetric = persist.getBoolean("OutputIsMetric",false);
		currentinputIsMetric = inputIsMetric;
		currentoutputIsMetric = outputIsMetric;
	}
	// put current values into persistence
	void setPersistentStore()
	{
		inputIsMetric = currentinputIsMetric;
		outputIsMetric = currentoutputIsMetric;
		SharedPreferences.Editor edit = persist.edit();
		edit.putInt("spinner1LeftIndex",spinner1LeftIndex);
		edit.putInt("spinner2LeftIndex",spinner2LeftIndex);
		edit.putInt("spinner3LeftIndex",spinner3LeftIndex);
		edit.putInt("spinner1RightIndex",spinner1RightIndex);
		edit.putInt("spinner2RightIndex",spinner2RightIndex);
		edit.putInt("spinner3RightIndex",spinner3RightIndex);
		edit.putInt("spinner4RightIndex",spinner4RightIndex);
		edit.putInt("spinner5RightIndex",spinner5RightIndex);
		edit.putBoolean("InputIsMetric",inputIsMetric);
		edit.putBoolean("OutputIsMetric",outputIsMetric);
		edit.commit();
		pointMainSetDefaults = true;
		mixingMainSetDefaults = true;
		processMainSetDefaults = true;
	}
	// put current spinner values into local variables
	void saveChoicesIndex()
	{
		spinner1LeftIndex = spinner1Left.getSelectedItemPosition();
		spinner2LeftIndex = spinner2Left.getSelectedItemPosition();
		spinner3LeftIndex = spinner3Left.getSelectedItemPosition();
		spinner1RightIndex = spinner1Right.getSelectedItemPosition();
		spinner2RightIndex = spinner2Right.getSelectedItemPosition();
		spinner3RightIndex = spinner3Right.getSelectedItemPosition();
		spinner4RightIndex = spinner4Right.getSelectedItemPosition();
		spinner5RightIndex = spinner5Right.getSelectedItemPosition();
		inputIsMetric = inputButton.isChecked();
		outputIsMetric = outputButton.isChecked();
	}
	// set spinner positions from local variables
	void restoreChoicesIndex()
	{
		spinner1Left.setSelection(spinner1LeftIndex);
		spinner2Left.setSelection(spinner2LeftIndex);
		spinner3Left.setSelection(spinner3LeftIndex);
		spinner1Right.setSelection(spinner1RightIndex);
		spinner2Right.setSelection(spinner2RightIndex);
		spinner3Right.setSelection(spinner3RightIndex);
		spinner4Right.setSelection(spinner4RightIndex);
		spinner5Right.setSelection(spinner5RightIndex);
		inputButton.setChecked(currentinputIsMetric);
		outputButton.setChecked(currentoutputIsMetric);
	}
	// a spinner has changed, figure out which one and save the value into the associated local variable
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
    	if 		(parent == spinner1Left) spinner1LeftIndex = pos;
    	else if (parent == spinner2Left) spinner2LeftIndex = pos;
    	else if (parent == spinner3Left) spinner3LeftIndex = pos;

    	else if (parent == spinner1Right) spinner1RightIndex = pos;
    	else if (parent == spinner2Right) spinner2RightIndex = pos;
    	else if (parent == spinner3Right) spinner3RightIndex = pos;
    	else if (parent == spinner4Right) spinner4RightIndex = pos;
    	else if (parent == spinner5Right) spinner5RightIndex = pos;
    	
//	      Toast.makeText(parent.getContext(), "The selection id:" + view.getId()+ " = "+ id, Toast.LENGTH_LONG).show();
	    }
    // we need onNothingSelected to compile correctly
    public void onNothingSelected(AdapterView<?> parent) {
	      // Do nothing.
	    }
    // handle button clicks
    public void onClick( View v )
	{ 
		int id = v.getId();
		int butn1id = R.id.toggleButtonLeft;
		int butn2id = R.id.toggleButtonRight;
		int butn3id = R.id.buttonSetAsDefaults;
		if 		(id == butn1id) { currentinputIsMetric  = inputButton.isChecked(); setChoices(); }
		else if (id == butn2id) { currentoutputIsMetric = outputButton.isChecked();setChoices(); }
		else if (id == butn3id) { 
//			psyCalcScreen.setup(this);
//    		mixingScreen.setup(this);
//    		processScreen.setup(this);
			
			setPersistentStore(); }
	}
  /*  
	public void fieldChanged(Field field, int context)
    {	
		if(ignoreFieldChanged) return;
    	if(field == inputButton) {inputIsMetric = !inputIsMetric;setChoices();} // flip english/metric
    	else if(field == outputButton){outputIsMetric= !outputIsMetric;setChoices();}
    	 //setChoices();
    	else saveChoicesIndex();
    	if(field == setDefaultsButton) 
    	{
    		psyCalcScreen.setup(this);
    		mixingScreen.setup(this);
    		processScreen.setup(this);
    		setPersistentStore();
    	}
    }
    */
	
}
