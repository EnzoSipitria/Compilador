package semanticalActions;

import lexicalAnalyzer.LexicalAnalyzer;
import structures.Token;

/**
 * 
 *cadenas multilinea, tratamiento de fin de linea, casos '/n' se descarta el +, sino se agrega como caracter 
 *
 */
public class SemAction10 extends SemanticalAction {

	@Override
	public Token execute(LexicalAnalyzer lexicalAnalyzer, char character) {
		if ( character == '\n'){
			Integer limitemax=lexicalAnalyzer.getLexema().length();
			//se elimina el ultimo caracter del lexema
			lexicalAnalyzer.setLexema(lexicalAnalyzer.getLexema().substring(0,limitemax-1));
		}
		else {
			lexicalAnalyzer.setLexema(character);
		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		return "Semantical Action 10 - controla el fin de linea de cadenas multilinea";
	}


}
