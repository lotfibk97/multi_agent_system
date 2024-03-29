import java.awt.*;
import java.applet.*;
import java.util.* ;

public class RuleApplet extends Applet {

	// user selected a rule base
	void choice1_Clicked() {

		String rbName = choice1.getSelectedItem() ;

		if (rbName.equals("Vehicles"))
			currentRuleBase = vehicles ;

		//if (rbName.equals("Bugs")) currentRuleBase = bugs ;
		//if (rbName.equals("Plants")) currentRuleBase = plants ;
		currentRuleBase.reset() ; // reset the rule base
		Enumeration vars = currentRuleBase.variableList.elements() ;
		while (vars.hasMoreElements())
			choice2.addItem(((RuleVariable)vars.nextElement()).name) ;

		currentRuleBase.displayVariables(textArea3) ;
	}

	// user selected a variable
	void choice2_Clicked(Event event) {

		String varName = choice2.getSelectedItem() ;
		choice3.removeAll() ;
		RuleVariable rvar =
		(RuleVariable)currentRuleBase.variableList.get(varName);
		Enumeration labels = rvar.labels.elements();

		while (labels.hasMoreElements())
			choice3.addItem(((String)labels.nextElement())) ;
	}

	// user selected a value for a variable
	//void choice3_Clicked(Event event)
	void choice3_Clicked(Event event) {

		String varName = choice2.getSelectedItem() ;
		String varValue = choice3.getSelectedItem() ;
		RuleVariable rvar = (RuleVariable)currentRuleBase.variableList.get(varName);
		rvar.setValue(varValue) ;
		textArea3.appendText("\n"+ rvar.name + " set to "+ varValue) ;
	}

	// user pressed Find button -- do an infernece cycle
	void button1_Clicked(Event event) {

		String goal = textField1.getText() ;
		textArea2.appendText("\n --- Starting Inferencing Cycle --- \n");
		currentRuleBase.displayVariables(textArea2) ;
		if (radioButton1.getState() == true)
			currentRuleBase.forwardChain();
		if (radioButton2.getState() == true)
			currentRuleBase.backwardChain(goal);
		currentRuleBase.displayVariables(textArea2) ;
		textArea2.appendText("\n --- Ending Inferencing Cycle --- \n");
	}

	// user pressed Demo button -- do inference with pre-set values
	void button2_Clicked(Event event) {

		String rbName = choice1.getSelectedItem() ;

		if (rbName.equals("Vehicles")) {

			if (radioButton1.getState() == true)
				demoVehiclesFC(vehicles);
			if (radioButton2.getState() == true)
				demoVehiclesBC(vehicles);
		}

		//else if (rbName.equals("Bugs")) {
		//if (radioButton1.getState() == true) demoBugsFC(bugs);
		//if (radioButton2.getState() == true) demoBugsBC(bugs);
		//} else {
		//if (radioButton1.getState() == true) demoPlantsFC(plants);
		//if (radioButton2.getState() == true) demoPlantsBC(plants);
		//}
	}

	// User press the Reset button
	void button3_Clicked(Event event) {

		//{{CONNECTION
		// Clear the text for TextArea
		textArea1.setText("");
		textArea2.setText("");
		textArea3.setText("");
		//}}
		currentRuleBase.reset() ;
		currentRuleBase.displayRules(textArea1);
		currentRuleBase.displayVariables(textArea3) ;
	}

	public void init() {

		super.init();
		// Note, this code is generated by Visual Cafe
		//{{INIT_CONTROLS
		setLayout(null);
		addNotify();
		resize(624,527);
		button1 = new java.awt.Button("Find Goal");
		button1.reshape(36,468,108,30);
		add(button1);
		button2 = new java.awt.Button("Run Demo");
		button2.reshape(228,468,108,30);
		add(button2);
		button3 = new java.awt.Button("Reset");
		button3.reshape(444,468,108,30);
		add(button3);
		textArea1 = new java.awt.TextArea();
		textArea1.reshape(12,48,312,144);
		add(textArea1);
		textArea2 = new java.awt.TextArea();
		textArea2.reshape(12,216,600,168);
		add(textArea2);
		label2 = new java.awt.Label("Trace Log");
		label2.reshape(24,192,168,24);
		add(label2);
		label1 = new java.awt.Label("Rule Base");
		label1.reshape(24,12,96,24);
		add(label1);
		choice1 = new java.awt.Choice();
		add(choice1);
		choice1.reshape(132,12,192,24);
		Group1 = new CheckboxGroup();
		radioButton1 = new java.awt.Checkbox("Forward Chain", Group1, false);
		radioButton1.reshape(36,396,156,21);
		add(radioButton1);
		choice3 = new java.awt.Choice();
		add(choice3);
		choice3.reshape(480,36,135,24);
		label5 = new java.awt.Label("Value");
		label5.reshape(480,12,95,24);
		add(label5);
		choice2 = new java.awt.Choice();
		add(choice2);
		choice2.reshape(336,36,137,24);
		textArea3 = new java.awt.TextArea();
		textArea3.reshape(336,72,276,122);
		add(textArea3);
		label4 = new java.awt.Label("Variable");
		label4.reshape(336,12,109,24);
		add(label4);
		radioButton2 = new java.awt.Checkbox("Backward Chain", Group1, false);
		radioButton2.reshape(36,420,156,24);
		add(radioButton2);
		textField1 = new java.awt.TextField();
		textField1.reshape(324,420,142,27);
		add(textField1);
		label3 = new java.awt.Label("Goal");
		label3.reshape(324,384,80,30);
		add(label3);
		//}}
		// initialize the rule applet
		frame = new Frame("Ask User") ;
		frame.resize(50,50) ;
		frame.setLocation(100,100) ;
		choice1.addItem("Vehicles") ;
		//choice1.addItem("Bugs"") ;
		//choice1.addItem("Plants") ;
		vehicles = new RuleBase("Vehicles Rule Base" ) ;
		vehicles.setDisplay(textArea2) ;
		initVehiclesRuleBase(vehicles) ;
		currentRuleBase = vehicles ;
		//bugs = new RuleBase("Bugs Rule Base") ;
		//bugs.setDisplay(textArea2) ;
		//initBugsRuleBase(bugs) ;
		//plants = new RuleBase("Plants Rule Base") ;
		//plants.setDisplay(textArea2) ;
		//initPlantsRuleBase(plants) ;
		// initialize textAreas and list controls
		currentRuleBase.displayRules(textArea1) ;
		currentRuleBase.displayVariables(textArea3) ;
		radioButton1.setState(true) ;
		choice1_Clicked() ; // fill variable list
	}

	// Note: this is Java 1.0.2 event model
	public boolean handleEvent(Event event) {

		if (event.target == button1 && event.id == Event.ACTION_EVENT) {
			button1_Clicked(event);
			return true;
		}

		if (event.target == button2 && event.id == Event.ACTION_EVENT) {
			button2_Clicked(event);
			return true;
		}

		if (event.target == button3 && event.id == Event.ACTION_EVENT) {
			button3_Clicked(event);
			return true;
		}

		if (event.target == dlg && event.id == Event.ACTION_EVENT) {
			return dlg.handleEvent(event);
		}

		if (event.target == choice1 && event.id == Event.ACTION_EVENT) {
			choice1_Clicked();
			return true;
		}

		if (event.target == choice2 && event.id == Event.ACTION_EVENT) {
			choice2_Clicked(event);
			return true;
		}

		if (event.target == choice3 && event.id == Event.ACTION_EVENT) {
			choice3_Clicked(event);
			return true;
		}

		return super.handleEvent(event);
	}

	// Note this code is generated by Visual Cafe
	//{{DECLARE_CONTROLS
	java.awt.Button button1;
	java.awt.Button button2;
	java.awt.Button button3;
	java.awt.TextArea textArea1;
	java.awt.TextArea textArea2;
	java.awt.Label label2;
	java.awt.Label label1;
	java.awt.Choice choice1;
	java.awt.Checkbox radioButton1;
	CheckboxGroup Group1;
	java.awt.Choice choice3;
	java.awt.Label label5;
	java.awt.Choice choice2;
	java.awt.TextArea textArea3;
	java.awt.Label label4;
	java.awt.Checkbox radioButton2;
	java.awt.TextField textField1;
	java.awt.Label label3;
	//}}
	static Frame frame ;
	static RuleVarDialog dlg ;
	//static RuleBase bugs ;
	//static RuleBase plants ;
	static RuleBase vehicles ;
	static RuleBase currentRuleBase ;
	//....
	// Rule base definitions

	//display dialog to get user value for a variable
	static public String waitForAnswer(String prompt, String labels) {

		// position dialog over parent dialog
		Point p = frame.getLocation() ;
		dlg = new RuleVarDialog(frame, true) ;
		dlg.label1.setText(" "  + prompt + "\n (" + labels + ")");
		dlg.setLocation(400, 250) ;
		dlg.show() ;
		String ans = dlg.getText() ;
		return ans ;
	}

	//initialize the Vehicles rule base
	public void initVehiclesRuleBase(RuleBase rb) {

		rb.goalClauseStack = new Stack() ; // goals and subgoals
		rb.variableList = new Hashtable() ;
		RuleVariable vehicle = new RuleVariable("vehicle") ;
		vehicle.setLabels("Bicycle Tricycle MotorCycle Sports_Car Sedan MiniVan Sports_Utility_Vehicle") ;
		vehicle.setPromptText("What kind of vehicle is it?");
		rb.variableList.put(vehicle.name,vehicle) ;
		RuleVariable vehicleType = new RuleVariable("vehicleType") ;
		vehicleType.setLabels("cycle automobile") ;
		vehicleType.setPromptText("What type of vehicle is it?") ;
		rb.variableList.put(vehicleType.name, vehicleType) ;
		RuleVariable size = new RuleVariable("size") ;
		size.setLabels("small medium large") ;
		size.setPromptText("What size is the vehicle?") ;
		rb.variableList.put(size.name,size) ;
		RuleVariable motor = new RuleVariable("motor") ;
		motor.setLabels("yes no") ;
		motor.setPromptText("Does the vehicle have a motor?") ;
		rb.variableList.put(motor.name,motor) ;
		RuleVariable num_wheels = new RuleVariable("num_wheels") ;
		num_wheels.setLabels("2 3 4") ;
		num_wheels.setPromptText("How many wheels does it have?");
		rb.variableList.put(num_wheels.name,num_wheels) ;
		RuleVariable num_doors = new RuleVariable("num_doors") ;
		num_doors.setLabels("2 3 4") ;
		num_doors.setPromptText("How many doors does it have?") ;
		rb.variableList.put(num_doors.name,num_doors) ;
		// Note: at this point all variables values are NULL
		Condition cEquals = new Condition("=") ;
		Condition cNotEquals = new Condition("!=") ;
		Condition cLessThan = new Condition("<") ;

		// define rules
		rb.ruleList = new Vector() ;
		Rule Bicycle = new Rule(rb, "bicycle",
		new Clause(vehicleType,cEquals, "cycle") ,
		new Clause(num_wheels,cEquals, "2"),
		new Clause(motor, cEquals, "no"),
		new Clause(vehicle, cEquals, "Bicycle")) ;
		Rule Tricycle = new Rule(rb, "tricycle",
		new Clause(vehicleType,cEquals, "cycle") ,
		new Clause(num_wheels,cEquals, "3"),
		new Clause(motor, cEquals, "no"),
		new Clause(vehicle, cEquals, "Tricycle")) ;
		Rule Motorcycle = new Rule(rb, "motorcycle",
		new Clause(vehicleType,cEquals, "cycle") ,
		new Clause(num_wheels,cEquals, "2"),
		new Clause(motor,cEquals, "yes"),
		new Clause(vehicle,cEquals, "Motorcycle")) ;
		Rule SportsCar = new Rule(rb, "sportsCar",
		new Clause(vehicleType,cEquals, "automobile") ,
		new Clause(size,cEquals, "small"),
		new Clause(num_doors,cEquals, "2"),
		new Clause(vehicle,cEquals, "Sports_Car")) ;
		Rule Sedan = new Rule(rb, "sedan",
		new Clause(vehicleType,cEquals, "automobile") ,
		new Clause(size,cEquals, "medium"),
		new Clause(num_doors,cEquals, "4"),
		new Clause(vehicle,cEquals, "Sedan")) ;
		Rule MiniVan = new Rule(rb, "miniVan",
		new Clause(vehicleType,cEquals, "automobile") ,
		new Clause(size,cEquals, "medium"),
		new Clause(num_doors,cEquals, "3"),
		new Clause(vehicle,cEquals, "MiniVan")) ;
		Rule SUV = new Rule(rb, "SUV",
		new Clause(vehicleType,cEquals, "automobile") ,
		new Clause(size,cEquals, "large"),
		new Clause(num_doors,cEquals, "4"),
		new Clause(vehicle,cEquals, "Sports_Utility_Vehicle")) ;
		Rule Cycle = new Rule(rb, "Cycle",
		new Clause(num_wheels,cLessThan, "4") ,
		new Clause(vehicleType,cEquals, "cycle")) ;
		Rule Automobile = new Rule(rb, "Automobile",
		new Clause(num_wheels,cEquals, "4") ,
		new Clause(motor,cEquals, "yes"),
		new Clause(vehicleType,cEquals, "automobile")) ;
	}

	public void demoVehiclesFC(RuleBase rb) {

		textArea2.appendText("\n --- Starting Demo ForwardChain ---\n ") ;
		// should be a Mini-Van
		((RuleVariable)rb.variableList.get("vehicle")).setValue(null) ;
		((RuleVariable)rb.variableList.get("vehicleType")).setValue(null) ;
		((RuleVariable)rb.variableList.get("size")).setValue("medium") ;
		((RuleVariable)rb.variableList.get("num_wheels")).setValue("4") ;
		((RuleVariable)rb.variableList.get("num_doors")).setValue("3") ;
		((RuleVariable)rb.variableList.get("motor")).setValue("yes") ;
		rb.displayVariables(textArea2) ;
		rb.forwardChain() ; // chain until quiescence...
		textArea2.appendText("\n --- Stopping Demo ForwardChain! ---\n") ;
		rb.displayVariables(textArea2);
	}

	public void demoVehiclesBC(RuleBase rb) {

		textArea2.appendText("\n --- Starting Demo BackwardChain ---\n ") ;
		// should be a minivan
		((RuleVariable)rb.variableList.get("vehicle")).setValue(null) ;
		((RuleVariable)rb.variableList.get("vehicleType")).setValue(null) ;
		((RuleVariable)rb.variableList.get("size")).setValue("medium") ;
		((RuleVariable)rb.variableList.get("num_wheels")).setValue("4") ;
		((RuleVariable)rb.variableList.get("num_doors")).setValue("3") ;
		((RuleVariable)rb.variableList.get("motor")).setValue("yes") ;
		rb.displayVariables(textArea2) ;
		rb.backwardChain("vehicle") ; // chain until quiescence...
		textArea2.appendText("\n --- Stopping Demo BackwardChain! ---\n ") ;
		rb.displayVariables(textArea2) ;
	}
}
