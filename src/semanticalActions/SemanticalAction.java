package semanticalActions;

import lexicalAnalyzer.LexicalAnalyzer;
import structures.Token;

public abstract class SemanticalAction {

	public abstract Token execute (LexicalAnalyzer lexicalAnalyzer, char character);
	public abstract String toString();
	
}
