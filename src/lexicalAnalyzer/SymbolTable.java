package lexicalAnalyzer;

import java.util.HashMap;

import structures.Token;
import structures.TokenMatrix;


public class SymbolTable {

	private HashMap<String, Token> symbolsTable;

	public SymbolTable() {
		this.symbolsTable = new HashMap<String, Token>();
	}

	public Token getToken(String key) {
		return symbolsTable.get(key);
	}
	

	public int size() {
		return this.symbolsTable.keySet().size();
	}

	public boolean containsSymbol(String lexema) {
		return this.symbolsTable.containsKey(lexema);
	}

	public void addToken(String key, Token token) {
		symbolsTable.put(key, token);
	}

	public void addToken(String key, TokenMatrix token) {
		symbolsTable.put(key, token);
	}
	
	public String toString() {
		String values = "";        
		for(String key : symbolsTable.keySet()){
			values+=symbolsTable.get(key)+"\n";
		}
		return values;	
	}
}
