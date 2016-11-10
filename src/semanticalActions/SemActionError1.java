package semanticalActions;

import lexicalAnalyzer.LexicalAnalyzer;
import structures.Token;

/**
 * 
 *ERROR de cadena 
 *
 */
public class SemActionError1 extends SemanticalAction{

	@Override
	public Token execute(LexicalAnalyzer lexicalAnalyzer, char character) {
		System.out.println("ACCION SEMANTICA Error 1");

		lexicalAnalyzer.addErrors("Linea: "+lexicalAnalyzer.getLineNumber()+". ERROR: Cadena Invalida."+"\n");
		return null;
	}

	@Override
	public String toString() {
		return "Semantical Action Error 1 - Reconoce errores en cadenas multilinea";
	}

}
