package semanticalActions;

import lexicalAnalyzer.LexicalAnalyzer;
import structures.Token;

/*
 * 
 *	Controlo que este en presencia de un --, DEVOLVIENDO EL ULTIMO CARACTER LEIDO AL BUFFER (SE DECREMENTA EN 1 EL INDICE DEL ARCHIVO, INDEXFILE)
 *
 */

public class SemAction4 extends SemanticalAction{

	@Override
	public Token execute(LexicalAnalyzer lexicalAnalyzer, char character) {
		System.out.println("ACCION SEMANTICA 4");
		lexicalAnalyzer.setLexema(character);
		String lexema = lexicalAnalyzer.getLexema();
		int lineNumber = lexicalAnalyzer.getLineNumber();
		return new Token("DECREMENTO",lexema, lineNumber);
	}

	@Override
	public String toString() {
		return "Semantical Action 4 - reconoce operador --";
	}


}
