package semanticalActions;

import lexicalAnalyzer.LexicalAnalyzer;
import structures.Token;

/*
 * 
 *	Agrega el caracter al lexema del token, el lexema ya esta inicializado en vacio ("")
 *
 */
public class SemAction1 extends SemanticalAction{

	@Override
	public Token execute(LexicalAnalyzer lexicalAnalyzer, char character){
		//System.out.println("ACCION SEMANTICA 1");
		lexicalAnalyzer.setLexema(character);
		return null;

	}

	@Override
	public String toString() {
		return "Semantical Action 1 - Agrega el caracter leido al lexema";
	}

}
