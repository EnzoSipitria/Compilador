package semanticalActions;

import interfaz.UI2;
import lexicalAnalyzer.LexicalAnalyzer;
import structures.Token;

/*
 * 
 *	Controla si el identificador es una palabra reservada y lo agrega a la tabla de simbolos,
 *
 *
 */
public class SemAction2 extends SemanticalAction{

	@Override
	public Token execute(LexicalAnalyzer lexicalAnalyzer, char character) {
		//System.out.println("ACCION SEMANTICA 2");
		Token token = null;
		//se "devuelve" el ultimo caracter leido, y se procede a controlar si el token es uno válido
		lexicalAnalyzer.setIndexLine(lexicalAnalyzer.getIndexLine()-1);
		String lexema = lexicalAnalyzer.getLexema();
		if ( lexema.length()< LexicalAnalyzer.MAX_LENGTH ){
			if ( lexicalAnalyzer.getReservedWords().contains(lexema) ){
				return new Token("PALABRARESERVADA",lexema,lexicalAnalyzer.getLineNumber());
			}
			else if (lexicalAnalyzer.getSymbolTable().containsSymbol(lexema)) {
				return new Token("IDENTIFICADOR",lexema,lexicalAnalyzer.getLineNumber());
			} else {
				token = new Token("IDENTIFICADOR",lexema,lexicalAnalyzer.getLineNumber());
				//System.out.println("Lexema en as2: "+lexema);
				lexicalAnalyzer.getSymbolTable().addToken(lexema, token);
				return token;
			}
		}else{
			String validLexema = lexema.substring(0,20);
			// mostrarlo pero no agregarlo a la lista de errores, 
			//lexicalAnalyzer.addErrors("Linea: "+lexicalAnalyzer.getIndexLine()+". WARNING: Identificador truncado - "+lexema+" ==> "+validLexema);
			UI2.addText(UI2.txtDebug,"Linea: "+lexicalAnalyzer.getIndexLine()+". WARNING: Identificador truncado - "+lexema+" ==> "+validLexema );
			if (lexicalAnalyzer.getSymbolTable().containsSymbol(validLexema)) {
				return new Token("IDENTIFICADOR", validLexema, lexicalAnalyzer.getLineNumber());
			} else {
				token = new Token("IDENTIFICADOR",validLexema, lexicalAnalyzer.getLineNumber());
				lexicalAnalyzer.getSymbolTable().addToken(validLexema, token);
				return token;
			}

		}
	}

	@Override
	public String toString() {
		return "Semantical Action 2 - agrega identificadores y palabras reservadas a la tabla de simbolos";
	}


}
