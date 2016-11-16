package lexicalAnalyzer;
/**
 * 
 * clase encargada de realizar el análisis lexico reconocer los tokens y armar la tabla de símbolos
 * 
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import interfaz.UI2;
import matrix.Matrix;
import matrix.SemanticalActionMatrix;
import matrix.StateTransitionMatrix;
import semanticalActions.SemanticalAction;
import structures.Token;

public class LexicalAnalyzer {

	public static final int MAX_LENGTH = 20;
	public static final int FINAL_STATE = -1;
	public static boolean fin = false;
	private SymbolTable symbolTable; 
	private BufferedReader sourceCode;
	private Matrix semanticalActions = new SemanticalActionMatrix();
	private Matrix stateTransitionMatrix =new StateTransitionMatrix();
	private ArrayList<Token> tokens;
	private ArrayList<String> reservedWords;
	private ArrayList<String> errors = new ArrayList<String>();

	private int indexLine;
	private int lineNumber;
	private String line;


	private String lexema;
	private Token token;
	private int currentState;

	private void inicializeReservedWords() {
		this.reservedWords.add("if");
		this.reservedWords.add("else");
		this.reservedWords.add("endif");
		this.reservedWords.add("for");
		this.reservedWords.add("integer");
		this.reservedWords.add("float");
		this.reservedWords.add("matrix");
		this.reservedWords.add("print");
		this.reservedWords.add("allow");
		this.reservedWords.add("to");
	}

	public void addToken(Token token) {
		tokens.add(token);
	}

	private int findColumn (char c) {
		if ((c >= 'a') && (c <= 'z') && (c!='f') && (c!='i') || (c >= 'A' && c <= 'Z' && c != 'E'))  return 0;
		if ((c >= '2') && (c <= '9') )                         	return 1;
		if ( c == '_' )                                         return 2;
		if ( c == '-' )                                         return 3;
		if ( c == '+' )                                         return 4;
		/*se juntaron el * y / para agregar el control decaracteres invalidos,
		se cambio el codigo de retorno del espacio en blanco de 28 a 6 
		y se deja al 28 como codigo para cualquier caracter invalido
		esto desencadena cambios en la matriz de transicion de estados y de acciones semanticas en las columnas indicadas*/
		if (( c == '*' ) || ( c == '/' ) )                      return 5;
		if ( (String.valueOf(c)).equals( " " ) || (String.valueOf(c)).equals("	"))  return 28;
		if ( c == '=' )                                         return 7;
		if ( c == '<' )                                         return 8;
		if ( c == '>' )                                         return 9;
		if ( c == '!' )                         				return 10;
		if ( c == '@')                                          return 11;
		if ( c == '&' )                                         return 12;
		if ( c == ':' )                                         return 13;
		if ( c == '\n')                                         return 14; 
		if ( c == '(' )                                         return 15;
		if ( c == ')' )                                         return 16;
		if ( c == '{' )                                         return 17;
		if ( c == '}' )                                         return 18;
		if ( c == ';' || c == ',')                              return 19;
		if ( c == '.' )                                         return 20;
		if ( c == '"' )                                         return 21;   
		if ( c == 'E' )                                         return 22;   
		if ( c == 'f' )                                         return 23;   
		if ( c == 'i' )                                         return 24;   
		if ( c == '[' ) 										return 25;
		if ( c == ']' ) 										return 26;
		if ( c == '1' || c == '0' )								return 27;
		return 6;
	}

	public void loadFile(String Path) throws FileNotFoundException {
		FileReader file = new FileReader(Path);
		sourceCode = new BufferedReader(file);
	}


	public LexicalAnalyzer(String sourceCodePath) {		
		this.semanticalActions.inicialize();
		this.stateTransitionMatrix.inicialize();
		this.symbolTable = new SymbolTable();
		this.tokens = new ArrayList<Token>();
		this.reservedWords = new ArrayList<String>();
		this.inicializeReservedWords();
		this.showReservedWords();
		try {
			this.loadFile(sourceCodePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.lexema = "";
		this.indexLine = 0;
		this.lineNumber = 0;
		this.line = "";
	}

	private void showReservedWords (){
		for (String word : reservedWords) {
			UI2.addText(UI2.txtReservedWords, word+"\n");

		}
	}

	public Token getToken()  {
		currentState = 0;
		while (indexLine <= line.length()) {
			/*
			 * En el caso de la primera linea, arranca con 0 el length de la misma, por lo que al while entra seguro y leeasi la primera linea
			 * 
			 * Si estamos en el final de la linea pregunto si quedan mas lineas para leer, en caso de que aun queden 
			 * se lee la siguiente linea. De lo contrario se retorna null.
			 * 
			 */
			if (indexLine == line.length()) {
				try {
					if ( sourceCode.ready() )  {
						line = sourceCode.readLine()+"\n";
						indexLine = 0;
						lineNumber++;
						//System.out.println("linea de codigo: "+line);
					}
					else {
						return null;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (currentState==FINAL_STATE || currentState == 0) {
				currentState=0;
				this.lexema = "";
			}
			char character = line.charAt(indexLine);
			int column = findColumn(character);
//			System.out.println("columna: "+column);
//			System.out.println("character: "+character);
			//llamado a ACCION SEMANTICA indicada en la matriz de acciones semanticas
			token = ((SemanticalAction)semanticalActions.getElem(currentState, column)).execute(this, character);
			indexLine++;
			if (token != null){
				tokens.add(token);					
				currentState = (Integer) stateTransitionMatrix.getElem(currentState, column);
				return token;
			}
			currentState = (Integer) stateTransitionMatrix.getElem(currentState, column);
		}
		return null;
	}


	public ArrayList<String> getReservedWords() {
		return reservedWords;
	}	

	public int getIndexLine() {
		return indexLine;
	}

	public void setIndexLine(int indexLine) {
		this.indexLine = indexLine;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}


	public String getLexema() {
		return lexema;
	}


	public void setLexema(char character) {
		this.lexema += character;

	}

	public void setLexema(String lexema) {
		this.lexema = lexema;

	}

	public SymbolTable getSymbolTable (){
		return this.symbolTable;
	}

	public String viewErrors() {
		String values ="";
		for (int i = 0; i < errors.size(); i++) {
			values+=errors.get(i);
		}
		return values;
	}

	public void addErrors(String error) {
		this.errors.add(error);
	}

	public int getCurrentState() {
		return currentState;
	}

	public void setCurrentState(int currentState) {
		this.currentState = currentState;
	}
	
	

	public ArrayList<Token> getTokenList() {
		return tokens;
	}
//
//	public void setTokens(ArrayList<Token> tokens) {
//		this.tokens = tokens;
//	}

	public String viewTokenList() {
		String values ="";
		for (int i = 0; i < tokens.size(); i++) {
			values+=tokens.get(i).toString()+"\n";
		}
		return values;
	}
	

	}


