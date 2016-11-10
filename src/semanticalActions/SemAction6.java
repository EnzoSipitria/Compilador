package semanticalActions;

import lexicalAnalyzer.LexicalAnalyzer;
import structures.Token;

/**
 * 
 * 
 * recononcer constantes float y enteras con control de rango
 *
 */


public class SemAction6 extends SemanticalAction{

	@Override
	public Token execute(LexicalAnalyzer lexicalAnalyzer, char character) {
		Token token = null;
		
		System.out.println("ACCION SEMANTICA 6");
		lexicalAnalyzer.setIndexLine(lexicalAnalyzer.getIndexLine()-1);

		String prefix = lexicalAnalyzer.getLexema().substring(0,2);
		String number = lexicalAnalyzer.getLexema().substring(2,lexicalAnalyzer.getLexema().length());
		String lexema = null;
		if (prefix.equals("_f")) {
			Float value= Float.parseFloat(number);
			float topLimitFloatPositive = Float.parseFloat("3.40282347E38");
			float infLimitFloatPositive = Float.parseFloat("1.17549435E-38");
			float infLimitFloatNegative = Float.parseFloat("-3.40282347E38");
			float topLimitFloatNegative = Float.parseFloat("-1.17549435E-38");
			float zero = (float) 0.0;
			if ( (value < infLimitFloatNegative ||value > topLimitFloatPositive) 
					&& !value.equals(zero) ){
				lexicalAnalyzer.addErrors("Linea: "+lexicalAnalyzer.getLineNumber()+". ERROR: rango de numero flotante invalido. MaxValue: "+topLimitFloatPositive+" - MinValue: "+infLimitFloatNegative);
				return null;
			}
			else {
				if (value < infLimitFloatPositive && value > topLimitFloatNegative) value = (float) 0.0; 
				lexema = prefix+String.valueOf(value);
				if (lexicalAnalyzer.getSymbolTable().containsSymbol(lexema)){
					return new Token("FLOAT",lexema,lexicalAnalyzer.getLineNumber());
				}
				else {
//					Float valueToken = Float.valueOf(lexicalAnalyzer.getLexema().substring(2));
//					System.out.println("--------------------------value token as6"+valueToken);
					//lexema = prefix+String.valueOf(value);
					token = new Token("FLOAT",lexema,lexicalAnalyzer.getLineNumber(),value,"float");
					lexicalAnalyzer.getSymbolTable().addToken(lexema, token);
					return token;
				}
			} 
		}

		else { 
			
			Integer infLimitInteger = -32768;
			Integer topLimitInteger = 32767;
//			Integer infLimitInteger = Integer.MIN_VALUE;
//			Integer topLimitInteger = Integer.MAX_VALUE;
//			System.out.println("limite inferior enteros"+infLimitInteger);
//			System.out.println("limite superior enteros"+topLimitInteger);
			Integer value = Integer.parseInt(number);

			if  (value < infLimitInteger || value > topLimitInteger ){
				lexicalAnalyzer.addErrors("Linea: "+lexicalAnalyzer.getLineNumber()+". ERROR: rango de numero entero invalido. MaxValue: "+topLimitInteger+" - MinValue: "+infLimitInteger);
				return null;
			}

			else {
				if (lexicalAnalyzer.getSymbolTable().containsSymbol(lexicalAnalyzer.getLexema())){
					Integer valueToken = Integer.valueOf(lexicalAnalyzer.getLexema().substring(2));
					return new Token("INTEGER",lexicalAnalyzer.getLexema(),lexicalAnalyzer.getLineNumber(),valueToken,"integer");
					}
				else {
					Integer valueToken = Integer.valueOf(lexicalAnalyzer.getLexema().substring(2));
//					System.out.println("--------------------------value token as6"+valueToken);
					token = new Token("INTEGER",lexicalAnalyzer.getLexema(),lexicalAnalyzer.getLineNumber(),valueToken,"integer");
					lexicalAnalyzer.getSymbolTable().addToken(lexicalAnalyzer.getLexema(), token);
					return token;
				}
			}
		}
	}

	@Override
	public String toString() {
		return "Semantical Action 6 - agrega a la tabla de símbolos las constantes, controlando rangos";
	}
}


