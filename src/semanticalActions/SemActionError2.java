package semanticalActions;

import lexicalAnalyzer.LexicalAnalyzer;
import structures.Token;

/**
 * 
 * token no reconocido
 * 
 *
 */
public class SemActionError2 extends SemanticalAction {

	@Override
	public Token execute(LexicalAnalyzer lexicalAnalyzer, char character) {
		System.out.println("ACCION SEMANTICA error2");
		/*
		 * controla que en el estado inicial avance con la lectura de caracteres
		 */
		if (lexicalAnalyzer.getCurrentState() != 0 ) {
			lexicalAnalyzer.setIndexLine(lexicalAnalyzer.getIndexLine()-1);
		}
		String numberLine = String.valueOf(lexicalAnalyzer.getLineNumber());

		lexicalAnalyzer.addErrors("Linea: "+numberLine+".  ERROR: Token inválido."+lexicalAnalyzer.getLexema()+"\n");
		return null;
	}

	@Override
	public String toString() {
		return "Semantical Action Error 2 - Token Inválido";
	}

}
