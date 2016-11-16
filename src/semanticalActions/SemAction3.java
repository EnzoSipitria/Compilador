package semanticalActions;

import lexicalAnalyzer.LexicalAnalyzer;
import structures.Token;

/**
 * 
 *	reconoce los operadores, literales + - = * / 
 *
 * 
 * se controla el caso de que venga un menos, para reconocerlo hay que devolver al buffer el ultimo caracter y seretora el token
 * 
 *
 */

public class SemAction3 extends SemanticalAction{

	@Override
	public Token execute(LexicalAnalyzer lexicalAnalyzer, char character) {
		int lineNumber = lexicalAnalyzer.getLineNumber();
		//System.out.println("ACCION SEMANTICA 3: ");
		//System.out.println("lexema "+lexicalAnalyzer.getLexema());
		if (lexicalAnalyzer.getLexema().equals("-")){
			lexicalAnalyzer.setIndexLine(lexicalAnalyzer.getIndexLine()-1);	
			String lexema = lexicalAnalyzer.getLexema().trim();
			return new Token("LITERAL",lexema, lineNumber);
		}
		if (lexicalAnalyzer.getLexema().equals("<") || lexicalAnalyzer.getLexema().equals(">")){
			//System.out.println("simbolo comparador menor o mayor encontrado");
			lexicalAnalyzer.setIndexLine(lexicalAnalyzer.getIndexLine()-1);	
			String lexema = lexicalAnalyzer.getLexema().trim();
			//System.out.println("lexema"+lexema);
			return new Token("LITERAL",lexema, lineNumber);
		} 
		lexicalAnalyzer.setLexema(character);
		String lexema = lexicalAnalyzer.getLexema().trim();
		//System.out.println("lexema"+lexema);
		return new Token("LITERAL",lexema, lineNumber);
	}

	@Override
	public String toString() {
		return "Semantical Action 3 - reconoce operadores aritmeticos literales ";
	}


}
