package semanticalActions;


import lexicalAnalyzer.LexicalAnalyzer;
import structures.Token;

/**
 * 
 * devuelve el token && sin considerar el mensaje
 * ultimo cambio, se devuelve el ultimo caracter al buffer, testear este caso... ya que caundo leia ni bien reconocia el token pedia la siguiente linea, en luigar 
 * de leer el caracter salto de linea en el estado inical, ejecutando la AS7 que consume qel caracter salto de linea
 */

public class SemAction8 extends SemanticalAction {

	@Override
	public Token execute(LexicalAnalyzer lexicalAnalyzer, char character) {
		System.out.println("ACCION SEMANTICA 8");
		Token token = null;
		System.out.println("lexema AS8"+lexicalAnalyzer.getLexema());
		lexicalAnalyzer.setIndexLine(lexicalAnalyzer.getIndexLine()-1);
		if(lexicalAnalyzer.getLexema().length()>=4){
				String cadena=lexicalAnalyzer.getLexema().substring(0, 4);
				System.out.println(cadena+".");
				if (cadena.equals("&&@1") ){
					System.out.println("cadena en anotacion 1"+cadena);
					token = new Token("ANOT1", cadena, lexicalAnalyzer.getLineNumber(),1);
					//String.valueOf(cadena.charAt(3)) para retornarel valor del token 0,1
					//lexicalAnalyzer.getSymbolTable().addToken(cadena, token);
					return token;
				}else if (cadena.equals("&&@0")){
					token = new Token("ANOT0", cadena, lexicalAnalyzer.getLineNumber(),0);
					//String.valueOf(cadena.charAt(3)) para retornarel valor del token 0,1
					//lexicalAnalyzer.getSymbolTable().addToken(cadena, token);
					return token;
				}
				System.out.println("no retorno ningun token - AS8");
		}
		
		return null;		
	}

	@Override
	public String toString() {
		return "Semantical Action 8 - reconoce el token && descartando el mensaje";
	}

}
