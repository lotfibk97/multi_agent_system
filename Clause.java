import java.util.*;
//import java.io.*;

public class Clause {

  Vector ruleRefs ;
  RuleVariable lhs ;
  String rhs ;
  Condition cond ;
  Boolean consequent ; // true or false
  Boolean truth;      // states = null(unknown), true or false

  Clause(RuleVariable Lhs, Condition Cond, String Rhs) {

    lhs = Lhs ;	cond = Cond ; rhs = Rhs ;
    lhs.addClauseRef(this) ;
    ruleRefs = new Vector() ;
    truth = null ;
    consequent = new Boolean(false) ;
  }

  void addRuleRef(Rule ref) {
    ruleRefs.addElement(ref) ;
  }

  Boolean check() {

    if (consequent.booleanValue() == true)
      return null ;
    if (lhs.value == null)
      return truth = null ;    // Var value is undefined
    else
      switch(cond.index) {
        case 1:
          truth = new Boolean(lhs.value.equals(rhs)) ;
          break ;
        case 2:
          truth = new Boolean(lhs.value.compareTo(rhs) > 0) ;
          break ;
        case 3:
          truth = new Boolean(lhs.value.compareTo(rhs) < 0) ;
          break ;
        case 4:
          truth = new Boolean(lhs.value.compareTo(rhs) != 0) ;
          break ;
      }

    return truth ;
  }

  void isConsequent() {
    consequent = new Boolean(true);
  }

  Rule getRule() {
    if (consequent.booleanValue() == true)
      return (Rule)ruleRefs.firstElement() ;
    else
      return null ;
  }

};
