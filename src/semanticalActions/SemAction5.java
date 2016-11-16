package semanticalActions;

import lexicalAnalyzer.LexicalAnalyzer;
import structures.Token;

/**
 * 
 * 
 * reconoce los simbolos dobles != <= etc
 *
 */

public class SemAction5 extends SemanticalAction{

	@Override
	public Token execute(LexicalAnalyzer lexicalAnalyzer, char character) {
		//System.out.println("ACCION SEMANTICA 5");
		Token token = null;
		lexicalAnalyzer.setLexema(character);
		String lexema = lexicalAnalyzer.getLexema();
		if (lexema.equals("<=")) {
			//System.out.println("Agrego menor igual: "+lexicalAnalyzer.getLexema());
			token = new Token("MENORIGUAL", lexema, lexicalAnalyzer.getLineNumber());
			//lexicalAnalyzer.getSymbolTable().addToken("MENORIGUAL", token);
			return token;
		}
		if (lexema.equals(">=")) {
			token = new Token("MAYORIGUAL", lexema, lexicalAnalyzer.getLineNumber());
			//lexicalAnalyzer.getSymbolTable().addToken("MAYORIGUAL", token);
			return token;
		}
		if (lexema.equals("!=")) {
			token = new Token("DISTINTO", lexema, lexicalAnalyzer.getLineNumber());
			//lexicalAnalyzer.getSymbolTable().addToken("DISTINTO", token);
			return token;
		}
		if (lexema.equals(":=")) {
			token = new Token("ASIGNACION", lexema, lexicalAnalyzer.getLineNumber());
			//lexicalAnalyzer.getSymbolTable().addToken("ASIGNACION", token);
			return token;
		}
		return null;
	}

	@Override
	public String toString() {
		return "Semantical Action 5 - reconoce comparadores";
	}


}
