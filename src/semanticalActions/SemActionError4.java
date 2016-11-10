package semanticalActions;

import lexicalAnalyzer.LexicalAnalyzer;
import structures.Token;

/**
 * 
 * caracter invalido
 * 
 *
 */
public class SemActionError4 extends SemanticalAction {

	@Override
	public Token execute(LexicalAnalyzer lexicalAnalyzer, char character) {
		/*
		 * controla que en el estado inicial avance con la lectura de caracteres
		 */
		System.out.println("ACCION SEMANTICA error 4");
		System.out.println("CARACTERRRRRRRRRRRRRRRR: "+lexicalAnalyzer.getLexema());
		System.out.println();
		String numberLine = String.valueOf(lexicalAnalyzer.getLineNumber());
		lexicalAnalyzer.addErrors("Linea: "+numberLine+"caracter invalido:"+lexicalAnalyzer.getIndexLine()+".  ERROR: Caracter inválido."+character+"\n");
		return null;
	}

	@Override
	public String toString() {
		return "Semantical Action Error 4 - Caracter Inválido";
	}

}
