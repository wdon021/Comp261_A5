import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.*;
import javax.swing.JFileChooser;

/**
 * The parser and interpreter. The top level parse function, a main method for
 * testing, and several utility methods are provided. You need to implement
 * parseProgram and all the rest of the parser.
 */
public class Parser {
	//private static HashMap<String, SensorNode> varMap = new HashMap<String, SensorNode>();
	/**
	 * Top level parse method, called by the World
	 */
	static RobotProgramNode parseFile(File code) {
		Scanner scan = null;
		try {
			scan = new Scanner(code);

			// the only time tokens can be next to each other is
			// when one of them is one of (){},;
			scan.useDelimiter("\\s+|(?=[{}(),;])|(?<=[{}(),;])");

			RobotProgramNode n = parseProgram(scan); // You need to implement this!!!

			scan.close();
			return n;
		} catch (FileNotFoundException e) {
			System.out.println("Robot program source file not found");
		} catch (ParserFailureException e) {
			System.out.println("Parser error:");
			System.out.println(e.getMessage());
			scan.close();
		}
		return null;
	}

	/** For testing the parser without requiring the world */

	public static void main(String[] args) {
		if (args.length > 0) {
			for (String arg : args) {
				File f = new File(arg);
				if (f.exists()) {
					System.out.println("Parsing '" + f + "'");
					RobotProgramNode prog = parseFile(f);
					System.out.println("Parsing completed ");
					if (prog != null) {
						System.out.println("================\nProgram:");
						System.out.println(prog);
					}
					System.out.println("=================");
				} else {
					System.out.println("Can't find file '" + f + "'");
				}
			}
		} else {
			while (true) {
				JFileChooser chooser = new JFileChooser(".");// System.getProperty("user.dir"));
				int res = chooser.showOpenDialog(null);
				if (res != JFileChooser.APPROVE_OPTION) {
					break;
				}
				RobotProgramNode prog = parseFile(chooser.getSelectedFile());
				System.out.println("Parsing completed");
				if (prog != null) {
					System.out.println("Program: \n" + prog);
				}
				System.out.println("=================");
			}
		}
		System.out.println("Done");
	}

	// Useful Patterns

	static Pattern NUMPAT = Pattern.compile("-?\\d+"); // ("-?(0|[1-9][0-9]*)");
	static Pattern OPENPAREN = Pattern.compile("\\(");
	static Pattern CLOSEPAREN = Pattern.compile("\\)");
	static Pattern OPENBRACE = Pattern.compile("\\{");
	static Pattern CLOSEBRACE = Pattern.compile("\\}");
	static Pattern OPR = Pattern.compile("\\+|-|\\*|\\/|&&|\\|\\||<|<=|>|>=|==|!=");
	/**
	 * PROG ::= STMT+
	 */
	public static RobotProgramNode parseProgram(Scanner s) {
		// THE PARSER GOES HERE
		ArrayList<RobotProgramNode> children = new ArrayList<RobotProgramNode>();
		int number = 0;
		if(!s.hasNext()) {fail("empty String", s);}
		//		else if(s.hasNext("[a-z]+")) {
		//		while(s.hasNext()) {
		//			System.out.println("parsPROG ran"+number);
		//			children.add(parseSTMT(s));
		//			number++;
		//		}}
		//		else{ fail("not an statement", s);}	
		while(s.hasNext()) {
			//System.out.println("parsPROG ran"+number);
			children.add(parseSTMT(s));
			number++;
		}
		System.out.println("parsPROG ran"+number);
		return new PROGNode(children);
	}

	public static RobotProgramNode parseSTMT(Scanner s) {
		RobotProgramNode child = null;
		System.out.println("parseSTMT running");
		if(!s.hasNext()) {fail("empty String", s);}
		else if(s.hasNext("loop")) {child = parseLoop(s);}
		else if(s.hasNext("if")) {child = parseIf(s);}
		else if(s.hasNext("while")) {child = parseWhile(s);}
		else if(s.hasNext("\\$[A-Za-z][A-Za-z0-9]*")) {child = parseAssgn(s); 
			}// or $[A-Za-z][A-Za-z0-9]*
		else{
			child = parseAct(s);
			//s.hasNext(); Dont know whether I need it or not
			//System.out.println("what is my next"+s.next());
			if(!s.hasNext(";")) {
				fail("not a good Act statments, missing ';' at the end", s);
			}
			s.next();
		}
		
		return new STMSNode(child);
	} 
	
	private static RobotProgramNode parseAssgn(Scanner s) {
		System.out.println("Parsing assignment");
		
		String varname = null;
		EveryThingInterface ssn = null;
		//SensorNode ssn2 = null;
		if(s.hasNext("\\$[A-Za-z][A-Za-z0-9]*")){
			varname = s.next();
			if(s.hasNext("=")) { 
			System.out.println("next in Assgn"+s.next());
			ssn = parseEVERY(s);
			//varMap.put(varname, ssn);
			if(!s.hasNext(";")) {fail("Not a good Assign statement", s);}
			s.next();
			}
			
		}else {
			fail("Not belong to Assign", s);
		}
			// TODO Auto-generated method stub
			return new AssignNode(varname, ssn);
	}

	public static RobotProgramNode parseWhile(Scanner s) {
		RobotProgramNode RPNChild = null;
		EveryThingInterface CNChild = null;
		System.out.println("parsewhile running");
		if(!s.hasNext("while")) {fail("Not a while statement", s);}
		s.next();
		if(!s.hasNext("\\(")) {fail("missing '(' in parseWhile", s);}
		s.next();
		CNChild = parseEVERY(s);
		if(!s.hasNext("\\)")) {fail("missing ')' in parseWhile", s);}
		s.next();
		RPNChild = parseBlock(s);
		return new WhileNode(RPNChild, CNChild);
	}
	
	public static RobotProgramNode parseIf(Scanner s) {
		RobotProgramNode ifblock = null;
		RobotProgramNode elseblock = null;
		ArrayList<EveryThingInterface> cond = new ArrayList<EveryThingInterface>();
		ArrayList<RobotProgramNode> block = new ArrayList<RobotProgramNode>();
		EveryThingInterface CNChild = null;
		System.out.println("parseIf running");
		if(!s.hasNext("if")) {fail("Not an if statement", s);}
		s.next();
		if(!s.hasNext("\\(")) {fail("missing '(' in parseIf if", s);}
		s.next();
		CNChild = parseEVERY(s);
		if(!s.hasNext("\\)")) {fail("missing ')' parseIf if", s);}
		s.next();
		ifblock = parseBlock(s);
		while(s.hasNext("elif")) {
			System.out.println("my stage: "+ s.next());
			if(!s.hasNext("\\(")) {fail("missing '(' parseIf elif", s);}
			s.next();
			cond.add(parseEVERY(s));
			if(!s.hasNext("\\)")) {fail("missing ')' parseIf elif", s);}
			s.next();
			block.add(parseBlock(s));
		}
		if(s.hasNext("else")) {
			s.next();
			elseblock = parseBlock(s);
		}
		
		return new IfNode(ifblock, CNChild, elseblock, cond, block);
	}
	public static EveryThingInterface parseEVERY(Scanner s) {
		System.out.println("parse Everything");
//		ArrayList<SensorNode> ASSN = new ArrayList<SensorNode>();
//		ArrayList<String> operator = new ArrayList<String>();
		EveryThingInterface left, right;
		//ASSN.add(parseK(s));
		left = parseK(s);
		while(s.hasNext(OPR)) {
			//operator.add(s.next());
			//ASSN.add(parseK(s));
			
			if(s.hasNext("\\+")) {
				s.next();
				right = parseK(s);
				left = new AddNode(left, right);
			}else if(s.hasNext("-")) {
				s.next();
				right = parseK(s);
				left = new MinusNode(left, right);
			}else if(s.hasNext("\\*")) {
				s.next();
				right = parseK(s);
				left = new MultipNode(left, right);
			}else if(s.hasNext("\\/")) {
				s.next();
				right = parseK(s);
				left = new DivNode(left, right);
			}else if(s.hasNext("&&")) {
				s.next();
				right = parseK(s);
				left = new AndNode(left, right);
			}else if(s.hasNext("\\|\\|")) {
				s.next();
				right = parseK(s);
				left = new OrNode(left, right);
			}else if(s.hasNext(">")) {
				s.next();
				right = parseK(s);
				left = new LargerNode(left, right);
			}else if(s.hasNext("<")) {
				s.next();
				right = parseK(s);
				left = new SmallerNode(left, right);
			}else if(s.hasNext(">=")) {
				s.next();
				right = parseK(s);
				left = new LargeEqualNode(left, right);
			}else if(s.hasNext("<=")) {
				s.next();
				right = parseK(s);
				left = new smallEqualNode(left, right);
			}else if(s.hasNext("==")) {
				s.next();
				right = parseK(s);
				left = new EqualNode(left, right);
			}else if(s.hasNext("!=")) {
				s.next();
				right = parseK(s);
				left = new NotEqualNode(left, right);
			}
			
			
		}	
		return left;
		//return new ALLIMP(left);
	}
	
	public static EveryThingInterface parseK(Scanner s) {
		System.out.println("parse parsek");
		EveryThingInterface k = null;
		SensorNode child = null;
		if(s.hasNext("true|false")){
			k = parseBool(s);
		}else if(s.hasNext("!")) {
			s.next();
			String not = "not";
			k = parseNot(s);
		}else if(s.hasNext("-?[1-9][0-9]*|0")) {System.out.println("parse Number"); child = parseNum(s);
		}else if(s.hasNext("fuelLeft")) {child = parsefuelLeft(s);}
		else if(s.hasNext("oppLR")) {child = parseOppLR(s);}
		else if(s.hasNext("oppFB")) {child = parseOppFB(s);}
		else if(s.hasNext("numBarrels")) {child = parseNumBrls(s);}
		else if(s.hasNext("barrelLR")) {child = parsebrlLR(s);}
		else if(s.hasNext("barrelFB")) {child = parsebrlFB(s);}
		else if(s.hasNext("wallDist")) {child = parsewallDist(s);}
		else if (s.hasNext("\\$[A-Za-z][A-Za-z0-9]*")) {
			child = parseVar(s);
		}else if(s.hasNext(OPENPAREN)) {
			s.next();
			k = parseEVERY(s);
			if(!s.hasNext("\\)")) {fail("missing ')' in parseK", s);}
			s.next();
		}
		if(child !=null) {
			return child;
		}
		return k;

	}
	
	
	
	
	private static EveryThingInterface parseNot(Scanner s) {
		// TODO Auto-generated method stub
		EveryThingInterface not;
		not = parseEVERY(s);
		return new NotNode(not);
	}

	private static EveryThingInterface parseBool(Scanner s) {
		// TODO Auto-generated method stub
		String bools = s.next();
		
		return new BoolNode(bools);
	}

	

	public static String parseRelop(Scanner s) {
		System.out.println("parse Relop runing");
		String st = null;
		if(s.hasNext("lt")) { st = s.next();}
		else if(s.hasNext("gt")) {st = s.next();}
		else if(s.hasNext("eq")) {st = s.next();}
		else{fail("Not a condition", s);}
		return st;
		//return new RelopNode(child);
	}

	private static SensorNode parseNum(Scanner s) {
		// TODO Auto-generated method stub
		System.out.println("parseNum running");
		if(!s.hasNext("-?[1-9][0-9]*|0")){fail("not a right number", s);}
		String st = s.next();
		System.out.println("parseNum what is in you"+ st);
		return new NumNode(st);

	}

	private static SensorNode parseSen(Scanner s) {
		System.out.println("parseSensor running");
		SensorNode child = null;
		if(s.hasNext("fuelLeft")) {child = parsefuelLeft(s);}
		else if(s.hasNext("oppLR")) {child = parseOppLR(s);}
		else if(s.hasNext("oppFB")) {child = parseOppFB(s);}
		else if(s.hasNext("numBarrels")) {child = parseNumBrls(s);}
		else if(s.hasNext("barrelLR")) {child = parsebrlLR(s);}
		else if(s.hasNext("barrelFB")) {child = parsebrlFB(s);}
		else if(s.hasNext("wallDist")) {child = parsewallDist(s);}
		else {fail("Not a Experession + Sensor Node", s);}
		return new SenNode(child);
	}

	private static SensorNode parsewallDist(Scanner s) {
		System.out.println("parsewallDist running");
		if(!s.hasNext("wallDist")) {fail("Not a wallDist", s);}
		s.next();
		// TODO Auto-generated method stub
		return new wallDistNode();
	}

	private static SensorNode parsebrlFB(Scanner s) {
		System.out.println("parsebrlFB running");
		EveryThingInterface exp = null;
		if(!s.hasNext("barrelFB")) {fail("Not a BarrelLR", s);}
		s.next();
		if(s.hasNext("\\(")) {
			s.next();
			exp = parseEVERY(s);
			if(!s.hasNext("\\)")) {
				fail("Not a good parsebrlFB( int), missing')'", s);
			}
			s.next();
			
		}
		if(exp!=null) {
			return new brlFBNode(exp);
		}
		// TODO Auto-generated method stub
		return new brlFBNode();
	}

	private static SensorNode parsebrlLR(Scanner s) {
		EveryThingInterface exp = null;
		System.out.println("parsebrlLR running");
		if(!s.hasNext("barrelLR")) {fail("Not a BarrelLR", s);}
		// TODO Auto-generated method stub
		s.next();
		if(s.hasNext("\\(")) {
			s.next();
			exp = parseEVERY(s);
			if(!s.hasNext("\\)")) {
				fail("Not a good parsebrlLR(int), missing')'", s);
			}
			s.next();
		}
		if(exp!=null) {
			return new BarlLRNode(exp);
		}
		return new BarlLRNode();
	}

	private static SensorNode parseNumBrls(Scanner s) {
		System.out.println("parseNumBrls running");
		if(!s.hasNext("numBarrels")) {fail("Not a numBarrels", s);}
		s.next();
		// TODO Auto-generated method stub
		return new NumBrlsNode();
	}

	private static SensorNode parseOppFB(Scanner s) {
		System.out.println("parseOppFB running");
		// TODO Auto-generated method stub
		if(!s.hasNext("oppFB")) {fail("Not a oppFB", s);}
		s.next();
		return new OppFBNode();
	}

	private static SensorNode parsefuelLeft(Scanner s) {
		System.out.println("parsefuelLeft running");
		// TODO Auto-generated method stub
		if(!s.hasNext("fuelLeft")) {fail("Not a fuel left", s);}
		s.next();
		return new FielLeftNode();
	}

	private static SensorNode parseOppLR(Scanner s) {
		System.out.println("parseOppLR running");
		// TODO Auto-generated method stub
		if(!s.hasNext("oppLR")) {fail("Not an opp LR", s);}
		s.next();
		return new OppLRNode();
	}


	public static RobotProgramNode parseAct(Scanner s) {
		RobotProgramNode child = null;
		EveryThingInterface senChild = null;
		System.out.println("parseACT ran");
		if(!s.hasNext()) {fail("empty String", s);}
		else if(s.hasNext("move")) {child = parseMove(s);
		if(s.hasNext("\\(")) {s.next();
		senChild = parseEVERY(s);
		if(!s.hasNext("\\)")) {fail("Not a good Expression missing ')' in ParseAct", s);}
		s.next();}
		}
		else if(s.hasNext("turnL")) {child = parseTurnL(s);}
		else if(s.hasNext("wait")) {child = parseWait(s);
		if(s.hasNext("\\(")) {s.next();
		senChild = parseEVERY(s);
		if(!s.hasNext("\\)")) {fail("Not a good Expression missing ')' in ParseAct", s);}
		s.next(); 
		}
		}
		else if(s.hasNext("turnR")) {child = parseTurnR(s);}
		else if(s.hasNext("takeFuel")) {child = parseTakeFuel(s);} 
		else if(s.hasNext("turnAround")) {child = parseturnA(s);}
		else if(s.hasNext("shieldOn")) {child = parseShieldOn(s);} 
		else if(s.hasNext("shieldOff")) {child = parseShieldOff(s);}
		else {fail("Not an action", s);}
		if(senChild!=null) {
			return new ActNode(child, senChild);
		}
		return new ActNode(child);
	}
	
	static SensorNode parseVar(Scanner s) {
		return new VarNode(s.next());
	}

	private static RobotProgramNode parseShieldOff(Scanner s) {
		if(!s.hasNext("shieldOff")) {fail("this is not a shield off node", s);}
		s.next();
		System.out.println("parseShieldOff ran");
		// TODO Auto-generated method stub
		return new ShieldOffNode();
	}

	private static RobotProgramNode parseShieldOn(Scanner s) {
		if(!s.hasNext("shieldOn")) {fail("this is not a shield on node", s);}
		s.next();
		System.out.println("parseShieldOn ran");
		// TODO Auto-generated method stub
		return new ShieldOnNode();
	}

	private static RobotProgramNode parseturnA(Scanner s) {
		if(!s.hasNext("turnAround")) {fail("this is not a turn Around node", s);}
		s.next();
		System.out.println("parseTurnAround ran");
		return new TurnAroundNode();
	}

	public static RobotProgramNode parseMove(Scanner s) {
		if(!s.hasNext("move")) {fail("this is not a move node", s);}
		s.next();
		System.out.println("parseMove ran");
		return new MoveNode();
	}

	public static RobotProgramNode parseTurnL(Scanner s) {
		if(!s.hasNext("turnL")) {fail("this is not a turn left node", s);}
		s.next();
		System.out.println("parseTurnL ran");
		return new turnLNode();
	}

	public static RobotProgramNode parseWait(Scanner s) {
		if(!s.hasNext("wait")) {fail("this is not a wait node", s);}
		s.next();
		System.out.println("parseWait ran");
		return new waitNode();
	}

	public static RobotProgramNode parseTurnR(Scanner s) {
		if(!s.hasNext("turnR")) {fail("this is not a turn right node", s);}
		s.next();
		System.out.println("parseTurnR ran");
		return new turnRNode();
	}

	public static RobotProgramNode parseTakeFuel(Scanner s) {
		if(!s.hasNext("takeFuel")) {fail("this is not a Take fuel node", s);}
		s.next();
		System.out.println("parseTakeFuel ran");
		return new takeFuelNode();
	}

	public static RobotProgramNode parseLoop(Scanner s) {
		RobotProgramNode child = null;
		System.out.println("parseLoop ran");
		if(s.hasNext("loop")) {s.next(); child = parseBlock(s);}
		else {fail("Not a loop", s);}
		return new loopNode(child);
	}

	public static RobotProgramNode parseBlock(Scanner s) {
		ArrayList<RobotProgramNode> children = new ArrayList<RobotProgramNode>();
		require("\\{", "Missing '{' in parseBlock", s);
		if(s.hasNext("\\}")) {fail("empty BLOCK", s);}
		while(s.hasNext() & !s.hasNext("\\}")) {
			children.add(parseSTMT(s));
		}
		require("\\}", "Missing '}' in parseBlock", s);
		return new BlockNode(children);
	}
	// utility methods for the parser

	/**
	 * Report a failure in the parser.
	 */
	static void fail(String message, Scanner s) {
		String msg = message + "\n   @ ...";
		for (int i = 0; i < 5 && s.hasNext(); i++) {
			msg += " " + s.next();
		}
		throw new ParserFailureException(msg + "...");
	}

	/**
	 * Requires that the next token matches a pattern if it matches, it consumes
	 * and returns the token, if not, it throws an exception with an error
	 * message
	 */
	static String require(String p, String message, Scanner s) {
		if (s.hasNext(p)) {
			return s.next();
		}
		fail(message, s);
		return null;
	}

	static String require(Pattern p, String message, Scanner s) {
		if (s.hasNext(p)) {
			return s.next();
		}
		fail(message, s);
		return null;
	}

	/**
	 * Requires that the next token matches a pattern (which should only match a
	 * number) if it matches, it consumes and returns the token as an integer if
	 * not, it throws an exception with an error message
	 */
	static int requireInt(String p, String message, Scanner s) {
		if (s.hasNext(p) && s.hasNextInt()) {
			return s.nextInt();
		}
		fail(message, s);
		return -1;
	}

	static int requireInt(Pattern p, String message, Scanner s) {
		if (s.hasNext(p) && s.hasNextInt()) {
			return s.nextInt();
		}
		fail(message, s);
		return -1;
	}

	/**
	 * Checks whether the next token in the scanner matches the specified
	 * pattern, if so, consumes the token and return true. Otherwise returns
	 * false without consuming anything.
	 */
	static boolean checkFor(String p, Scanner s) {
		if (s.hasNext(p)) {
			s.next();
			return true;
		} else {
			return false;
		}
	}

	static boolean checkFor(Pattern p, Scanner s) {
		if (s.hasNext(p)) {
			s.next();
			return true;
		} else {
			return false;
		}
	}

}

// You could add the node classes here, as long as they are not declared public (or private)
