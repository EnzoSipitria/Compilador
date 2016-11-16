package semanticalActions;

import lexicalAnalyzer.LexicalAnalyzer;
import structures.Token;

/***
 *
 * reconoce las cadenas multinlinea y las agrega
 *
 */

public class SemAction9 extends SemanticalAction{

	@Override
	public Token execute(LexicalAnalyzer lexicalAnalyzer, char character) {
		//System.out.println("ACCION SEMANTICA 9");
		Token token = null;
		lexicalAnalyzer.setLexema(character);
		if (lexicalAnalyzer.getSymbolTable().containsSymbol(lexicalAnalyzer.getLexema())) {
			return new Token("CADENA",lexicalAnalyzer.getLexema(),lexicalAnalyzer.getLineNumber());

		} else {
			int numberLine = lexicalAnalyzer.getTokenList().get(lexicalAnalyzer.getTokenList().size()-1).getLineNumber();
			token = new Token("CADENA",lexicalAnalyzer.getLexema(),numberLine);
			//System.out.println("=AS9 "+lexicalAnalyzer.getLexema()+" token generado"+token);
			lexicalAnalyzer.getSymbolTable().addToken(lexicalAnalyzer.getLexema(),token); 
			//System.out.println(lexicalAnalyzer.getSymbolTable().getToken(lexicalAnalyzer.getLexema()));
			return token;
		}
	}

	@Override
	public String toString() {
		return "Semantical Action 9 - agrega a la tabla de symbolos las cadenas multilinea";
	}
}
