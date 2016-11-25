package assembler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import lexicalAnalyzer.SymbolTable;
import parser.Parser;
import structures.AuxGenerator;
import structures.Element;
import structures.Terceto;
import structures.Token;



/**
 * cosas para hacer:
 *  
 *  Revisar operaciones con flotantes{
 *  									suma
 *  									resta
 *  									multiplicacion
 *  									division
 *  									conversiones
 *  									comparaciones
 *  								}
 *  
 *  cambiar el tipo en la inicializacion de auxiliares (terceto referencia para las direcciones de la matriz)
 *  
 *  control de perdida de informacion
 *  
 *  agregar matrices assembler
 *  
 *  agregar if errores compilacion no generar assembler
 * 
 *  agregar al informe donde estan las palabras reservadas
 *  
 *  revisar informe
 * 
 * 
 *  
 * @author Maxi
 *
 */
public class Assembler {

	private ArrayList<Terceto> listaTerceto;
	private ArrayList<String> operators = new ArrayList<String>();
	private SymbolTable symbolTable;
	public static HashMap<Token, String> constantes = new HashMap<Token, String>();
	public static int numConst = 0;
	private static AuxGenerator generator = new AuxGenerator();
	public Assembler(ArrayList<Terceto> tercetos, SymbolTable symbolTable) {
		this.listaTerceto = tercetos;
		this.symbolTable = symbolTable; 

	}

	/**
	 *  consideraciones:
 cuanto trabajamos con matrices y sus puenteros usar el registro en su maximo EBX por ejemplo
 despues trabajar con bx  solo en todos los tercetos involucrados
 por ejemplo:
  Codigo+="MOV "+"EBX" +", " + "dword ptr ["+Op1.getAux()+"]"+"\n"; // aca siempre se trabaja con registros de 32 se ve q al usar el dword aumenta el tamaño del manejo de direccionnes a 32 
                    Codigo +="ADD " + "BX" + ", " + op2.getOperando() +"\n";// aca siempre q trabajemos con @aux's trabajar con registros de 16 lo hace bien hasta ahora lo q  probe funco 
                     Codigo+= "MOV" + getaux() + "BX" +"\n"
	 * 
	 */

	public ArrayList<Terceto> getListaTerceto() {
		return listaTerceto;
	}


	public void initAuxGenerator(){
		generator.initCounter();
	}

	public void setListaTerceto(ArrayList<Terceto> listaTerceto) {
		this.listaTerceto = listaTerceto;
	}


	public static String getIdConst(Token t) {
		String nombre = constantes.get(t);
		if (nombre != null) {
			return nombre;
		}
		nombre = "__cte" + numConst;
		constantes.put(t, nombre);
		numConst++;
		return nombre;
	}

	private String getheadlines() {
		String encabezado = new String();
		encabezado += ".386\n"; //ml /c /Zd /coff hellow.asm"
		encabezado += ".model flat, stdcall\n";//Link /SUBSYSTEM:WINDOWS hellow.obj;
		encabezado += "option casemap :none\n";
		encabezado +=  "include \\masm32\\include\\debug.inc\n"
				+ "includelib \\masm32\\lib\\debug.lib \n";
		encabezado += "include \\masm32\\include\\windows.inc\n";
		encabezado += "include \\masm32\\include\\kernel32.inc\n";
		encabezado += "include \\masm32\\include\\user32.inc\n";
		encabezado += "includelib \\masm32\\lib\\kernel32.lib\n";
		encabezado += "includelib \\masm32\\lib\\user32.lib\n";

		return encabezado;
	}

	private String getDeclarations() {
		int countCad=0;
		String declaration = new String();
		declaration += ".data\n";
		//    	declaration += "__MIN DD  1.17549435e-38\n";
		//    	declaration += "__MAX DD  3.40282347e38\n";
		declaration += "_msjDC DB \"Error: Division por cero\", 0\n";
		declaration += "_msjIC DB \"Error: Indices fuera de rango\", 0\n";
		Set<String> keys = symbolTable.getTokenList().keySet();
		//int numCad = 0;
		//    	System.out.println("TABLA DE SIMBOLOS");
		//    	System.out.println(symbolTable.toString());
		//    	System.out.println("====GET DECLARATIONS=====");
		int count = 0;
		for (String key : keys) {
			/**
			 * generar codigo para base de matriz faltaria esoooo!!!!!
			 * 
			 * 
			 */

			count++;
			Token token= symbolTable.getToken(key);
			//    		System.out.println("===================== token"+ token);
			if(token.getTypeVariable() != null){
				if (token.getTypeVariable().equals("integer")) {

					if (token.getUse() != null && token.getUse().equals("mat")){
						int size = token.getRows()*token.getColumns();
						declaration += token.getAssembler() + " DW "+size+" DUP(?)\n";
					} else {
						if (token.getType().equals("INTEGER")){
							declaration += token.getAssembler() + " DW "+token.getValue()+"\n";
						} else declaration += token.getAssembler() + " DW ?\n"; // entero = 2 bytes
					}	                	
					//	                	System.out.println("token en get declarations"+token);
					//	                	System.out.println("declaration "+token.getAssembler() + " DW ?\n");
					//declaration += token.getAssembler() + " DW ?\n"; // entero = 2 bytes
				} else {
					if (token.getUse() != null && token.getUse().equals("mat")){
						int size = token.getRows()*token.getColumns();
						declaration += token.getAssembler() + " DD "+size+" DUP(?)\n";
					} else 
						if (token.getType().equals("FLOAT")){
//							token.setAux("_f"+generator.getName());
							declaration += token.getAux() + " DD "+token.getValue()+"\n";
						}else {declaration += token.getAssembler() + " DD ?\n";//  flotantes = 8 bytes
						} 
				} 
				//        	    	System.out.println("<<LEXEMA TOKEN >>"+token.getType()+","+token);
			}else{
				//        			System.out.println("El token sin tipo es: "+token);
			}

			if (token.getType().equals("CADENA")/* && token.getTypeVariable().equals("cadena")*/) {  
				//    			String name = "_cad"+countCad;

				//    			token.setAux(name);
				//	        	    	token.setLexema(name);
				declaration += token.getAux()+" DB " + token.getLexema() + ", 0\n";
				countCad++;

			}
			if (token.getType().equals("DECREMENTO")) {  
				String name = token.getLexema().substring(1, token.getLexema().length()-1);
				name = name.replaceAll(" ", "");
				//        	    	token.setLexema(name);
				declaration += name + " DW " + token.getLexema() + ", 0\n";
				//numCad++;

			}

		}
		System.out.println("cantidad de variables generadas"+count);
		return declaration;   
	}


	private String getcontrolindex(){
		return null;
	}

	private String getIndexControl() {
		String str = "";
		str += "_indexcontrol:\n";
		str += "invoke MessageBox, NULL, addr _msjIC, addr _msjIC, MB_OK\n";///// hacer un mensajer de error como el dividido en el getheadlines
		str += "invoke ExitProcess, 0\n";
		return str;
	}

	private String getDivCero() {
		String str = "";
		str += "_division_cero:\n";
		str += "invoke MessageBox, NULL, addr _msjDC, addr _msjDC, MB_OK\n";
		str += "invoke ExitProcess, 0\n";
		return str;
	}

	public String getCodigo() {
		System.out.println("LISTA DE TERCETOS");
		for (int i = 0; i < listaTerceto.size(); i++) {
			System.out.println(listaTerceto.get(i));

		}
		String Assemblercode = new String();
		Assemblercode += getheadlines();
		String instrucciones = new String();
		generator.initCounter();
		instrucciones += ".code\n";
		//        instrucciones += getcontrolindex();
		instrucciones += getDivCero();
		instrucciones += getIndexControl();
		instrucciones += "start:\n";
		System.out.println("=====getCOdigo===========");

		generateOperators();
		for (Terceto terceto : listaTerceto) {
//			System.out.println("====================================================>"+terceto);

			//    		System.out.println("get USE");
			System.out.println("CONDICION EVALUADORA: "+terceto+"   typeVariable"+terceto.getTypeVariable());

			if(operators.contains(terceto.getOperator())){
				if (terceto.getOperator().equals("conv")){
					if (((Element)terceto.getFirst()).getClassType().equals("Token")){
					Token token = symbolTable.getToken(((Token) terceto.getFirst()).getLexema());
					String aux = "_"+generator.getName();
					token.setAux(aux);}
				//	terceto.setAux(aux);}
				}
				System.out.println("=TTTTTTTTT=============== aux generada "+generator.control());
				terceto.setAux("@"+generator.getName());
				Token token = new Token("IDENTIFICADOR", terceto.getAux(), 0, 0, terceto.getTypeVariable());
				symbolTable.addToken(terceto.getAux(), token);
				
			}
			
			if (terceto.getOperator().equals("print")){
				Token token = symbolTable.getToken(((Token) terceto.getFirst()).getLexema());
				String aux = "_"+generator.getName();
				token.setAux(aux);
				terceto.setAux(aux);
			}
			if (terceto.getOperator().equals(">^")){
				terceto.setNumber(generator.getCounter());
			}
			//    		if(operators.contains(terceto.getOperator())){
			//    				System.out.println("=TTTTTTTTT=============== aux generada "+generator.control());
			//    				terceto.setAux("@"+generator.getName());
			//    				Token token = new Token("IDENTIFICADOR", terceto.getAux(), 0, 0, terceto.getTypeVariable());
			//    				symbolTable.addToken(terceto.getAux(), token);
			//    		}else if((terceto.getUse() != null) && (terceto.getUse().equals("SHIFT"))){
			//    			  		System.out.println("=TTTTTTTTT=============== aux generada "+generator.control());
			//    			  		terceto.setAux("@"+generator.getName());
			//    			  		Token token = new Token("IDENTIFICADOR", terceto.getAux(), 0, 0, terceto.getTypeVariable());
			//    			  		symbolTable.addToken(terceto.getAux(), token);
			//    			  }else System.out.println("NO GENERO NADAAAAA");

			instrucciones += terceto.getAssembler();
		}
		Assemblercode += getDeclarations(); // Va despues de generar las intrucciones porque se incluyen las @aux# en la TS
		Assemblercode += instrucciones;
		Assemblercode += "invoke ExitProcess, 0\n";
		Assemblercode += "end start";

		return Assemblercode;
	}


	private void generateOperators(){
		operators.add("+");
		operators.add("-");
		operators.add("*");
		operators.add("/");
		//operators.add(":=");
		operators.add("BF");
		operators.add("BI");
		operators.add(">");
		operators.add("<");
		operators.add(">=");
		operators.add("<=");
		operators.add("=");
		operators.add("--");
		operators.add(">^");
		operators.add("conv");
		operators.add("^");
		operators.add("simple");
		//		operators.add("print");

	}



}
