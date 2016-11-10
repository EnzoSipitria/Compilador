package semanticalActions;

import lexicalAnalyzer.LexicalAnalyzer;
import structures.Token;

/**
 * 
 * se controla el error de constantes float y enteras
 *
 */
public class SemActionError3 extends SemanticalAction{

	@Override
	public Token execute(LexicalAnalyzer lexicalAnalyzer, char character) {
		System.out.println("ACCION SEMANTICA error 3");
		lexicalAnalyzer.setIndexLine(lexicalAnalyzer.getIndexLine()-1);
		lexicalAnalyzer.addErrors("Linea: "+lexicalAnalyzer.getLineNumber()+". ERROR: Constante inválida."+"\n");
		return null;
	}

	@Override
	public String toString() {
		return "Semantical Action Error 3 - Errores de constantes de punto flotante y enteras";
	}

}
