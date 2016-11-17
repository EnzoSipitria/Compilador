package semanticalActions;

import lexicalAnalyzer.LexicalAnalyzer;
import structures.Token;

/**
 *
 * avanza el indice unicamente para consumir caracteres sin agregalos al lexema
 *
 */
public class SemAction7 extends SemanticalAction{

	@Override
	public Token execute(LexicalAnalyzer lexicalAnalyzer, char character) {
		//System.out.println("ACCION SEMANTICA 7");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		return "Semantical Action 7 - consume caracteres sin agregarlos al lexema";
	}

}
