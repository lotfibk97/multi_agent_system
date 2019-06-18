import java.util.*;
import java.io.*;
import java.awt.* ;
import java.io.*;
import java.applet.*;

public class RuleBase {

  String name ;
  Hashtable variableList ; // all variables in the rulebase
  Clause clauseVarList[];
  Vector ruleList ; // list of all rules
  Vector conclusionVarList ; // queue of variables
  Rule rulePtr ; // working pointer to current rule
  Clause clausePtr ; // working pointer to current clause
  Stack goalClauseStack; // for goals (cons clauses ) and subgoals
  static TextArea textArea1;

  public void setDisplay(TextArea txtArea) {
    textArea1 = txtArea;
  }

  RuleBase(String Name) {
    name = Name;
  }

  public static void appendText(String text) {
    textArea1.appendText(text);
  }

  // for trace purposes  - display all variables and their value
  public void displayVariables(TextArea textArea) {
    Enumeration enume = variableList.elements();

    while(enume.hasMoreElements()) {
      RuleVariable temp = (RuleVariable)enume.nextElement() ;
      textArea.appendText("\n" + temp.name + " value = " + temp.value) ;
    }
  }

  // for trace purposes - display all rules in text format
  public void displayRules(TextArea textArea) {

    textArea.appendText("\n" + name + " Rule Base: " + "\n");
    Enumeration enume = ruleList.elements() ;

    while(enume.hasMoreElements()) {
      Rule temp = (Rule)enume.nextElement() ;
      temp.display(textArea) ;
    }
  }

  // for trace purposes - display all rules in the conflict set
  public void displayConflictSet(Vector ruleSet) {

    textArea1.appendText("\n" + " -- Rules in conflict set:\n");
    Enumeration enume = ruleSet.elements();

    while(enume.hasMoreElements()) {
      Rule temp = (Rule)enume.nextElement() ;
      textArea1.appendText(temp.name + "(" + temp.numAntecedents()+ "), ");
    }
  }

  // reset the rule base for another round of inferencing
  // by setting all variable values to null

  public void reset() {

    textArea1.appendText("\n Setting all " + name + " variables to null");
    Enumeration enume = variableList.elements() ;

    while(enume.hasMoreElements()) {
      RuleVariable temp = (RuleVariable)enume.nextElement() ;
      temp.setValue(null) ;
    }
  }

  public void forwardChain() {

    Vector conflictRuleSet = new Vector() ;

    // first test all rules, based on initial data
	  conflictRuleSet = match(true); // see which rules can fire

    while(conflictRuleSet.size() > 0) {
	    Rule selected = selectRule(conflictRuleSet); // select the "best" rule
	    selected.fire() ; // fire the rule
	    // do the consequent action/assignment
	    // update all clauses and rules
	    conflictRuleSet = match(false); // see which rules can fire
	    // displayVariables("Forward Chaining") ; // display variable bindings
	   }
	}

  //used for forward chaining only
  //determine which rules can fire, return a Vector
  public Vector match(boolean test) {

    Vector matchList = new Vector() ;
	  Enumeration enume = ruleList.elements() ;

    while (enume.hasMoreElements()) {

      Rule testRule = (Rule)enume.nextElement() ;
	    if (test)
        testRule.check() ; // test the rule antecedents
	    if (testRule.truth == null)
        continue ;
      // fire the rule only once for now
	    if ((testRule.truth.booleanValue() == true) &&
	         (testRule.fired == false))
        matchList.addElement(testRule);
	  }

    displayConflictSet(matchList) ;
	  return matchList ;
	}

  //used for forward chaining only
  //select a rule to fire based on specificity
  public Rule selectRule(Vector ruleSet) {

    Enumeration enume = ruleSet.elements() ;
    long numClauses ;
    Rule nextRule ;
    Rule bestRule = (Rule)enume.nextElement() ;
    long max = bestRule.numAntecedents() ;

    while (enume.hasMoreElements()) {

      nextRule = (Rule)enume.nextElement() ;
      if ((numClauses = nextRule.numAntecedents()) > max) {
        max = numClauses ;
        bestRule = nextRule ;
      }
    }

    return bestRule ;
  }

  public void backwardChain(String goalVarName) {

    RuleVariable goalVar = (RuleVariable)variableList.get(goalVarName);
    Enumeration goalClauses = goalVar.clauseRefs.elements() ;

    while (goalClauses.hasMoreElements()) {

      Clause goalClause = (Clause)goalClauses.nextElement() ;
      if (goalClause.consequent.booleanValue() == false) continue ;
      goalClauseStack.push(goalClause) ;

      Rule goalRule = goalClause.getRule();
      Boolean ruleTruth = goalRule.backChain() ; // find rule truth value

      if (ruleTruth == null)
        textArea1.appendText("\nRule " + goalRule.name + " is null, can't determine truth value.");
      else if (ruleTruth.booleanValue() == true) {
        // rule is OK, assign consequent value to variable
        goalVar.setValue(goalClause.rhs) ;
        goalVar.setRuleName(goalRule.name) ;
        goalClauseStack.pop() ; // clear item from subgoal stack
        textArea1.appendText("\nRule " + goalRule.name + " is true, setting " + goalVar.name + ": =" + goalVar.value);
        if (goalClauseStack.empty() == true) {
          textArea1.appendText("\n +++ Found Solution for goal: " + goalVar.name);
          break ; // for now, only find first solution, then stop
        }
      } else {
        goalClauseStack.pop() ; // clear item from subgoal stack
        textArea1.appendText("\nRule " + goalRule.name + " is false, can't set " + goalVar.name);
      }
    }

    if (goalVar.value == null)
      textArea1.appendText("\n +++ Could Not Find Solution for goal: " + goalVar.name);

    }
}
