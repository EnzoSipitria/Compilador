/* YACC Declarations */
%{

import lexicalAnalyzer.LexicalAnalyzer;
import structures.Element;
import structures.Terceto;
import structures.TercetoAsignacion;
import structures.TercetoComparador;
import structures.TercetoDivision;
import structures.TercetoMultiplicacion;
import structures.TercetoDecremento;
import structures.TercetoResta;
import structures.TercetoSuma;
import structures.Token;
import interfaz.UI2;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Stack;
import structures.TercetoBFalse;
import structures.TercetoBInconditional;
import structures.TercetoSimple;


%}

%token COMENTARIO IDENTIFICADOR PALABRARESERVADA MENORIGUAL MAYORIGUAL DISTINTO ASIGNACION DECREMENTO CADENA FLOAT INTEGER IF ENDIF FOR PRINT CTEINTEGER CTEFLOAT MATRIX ANOT0 ANOT1 ALLOW TO ELSE
%start programa

/* Grammar follows */
%%


programa : sentencia_declarativa bloque_sentencias
          |sentencia_declarativa 
		  | bloque_sentencias;

sentencia_declarativa : sentencia_declarativa_datos {estructuras.add(lexAn.getLineNumber()+". sentencia declarativa de datos\n");}
					  | sentencia_declarativa sentencia_declarativa_datos {estructuras.add(lexAn.getLineNumber()+". sentencia declarativa de datos\n");}
					  | sentencia_declarativa sentencia_declarativa_matrix {estructuras.add(lexAn.getLineNumber()+". sentencia declarativa de matrices\n");}
					  | sentencia_declarativa_matrix {estructuras.add(lexAn.getLineNumber()+". sentencia declarativa de matrices\n");}
					  | sentencia_declarativa_conversion {estructuras.add(lexAn.getLineNumber()+". sentencia declarativa de conversion de tipos\n");}
					  | sentencia_declarativa sentencia_declarativa_conversion {estructuras.add(lexAn.getLineNumber()+". sentencia declarativade conversion de tipos\n");};
					  


sentencia_declarativa_conversion : ALLOW tipo TO tipo ';';			  
					  
					  
sentencia_declarativa_matrix : tipo MATRIX IDENTIFICADOR '[' CTEINTEGER ']' '[' CTEINTEGER ']' ';'

							  | tipo MATRIX IDENTIFICADOR '[' CTEINTEGER ']' '[' CTEINTEGER ']' ';' anotacion_matrix;
							  
							  | tipo MATRIX IDENTIFICADOR '[' CTEINTEGER ']' '[' CTEINTEGER ']' inicializacion_matrix

							  | tipo MATRIX error ';' {UI2.addText(UI2.txtDebug,"Linea: "+lexAn.getLineNumber()+". Columna: "+lexAn.getIndexLine()+" DECLARACION ERRONEA"+"\n"); errores.add("Linea: "+lexAn.getLineNumber()+" DECLARACION ERRONEA");};
								
								
								
inicializacion_matrix : operador_asignacion '{' lista_valores_matrix '}' ';'
					   | operador_asignacion '{' lista_valores_matrix '}' ';' anotacion_matrix;
					   
anotacion_matrix : ANOT0 {$$.obj = $1;}| ANOT1 {$$.obj = $1;};

lista_valores_matrix : fila_valores_matrix ';' | lista_valores_matrix fila_valores_matrix ';'
					  | error ';'{UI2.addText(UI2.txtDebug,"Linea: "+lexAn.getLineNumber()+". Columna: "+lexAn.getIndexLine()+"ERROR EN LA LISTA DE VARIABLES"+"\n"); errores.add("Linea: "+lexAn.getLineNumber()+"ERROR EN LA LISTA DE VARIABLES"+"\n");};


fila_valores_matrix : constante | fila_valores_matrix ',' constante;


sentencia_declarativa_datos : tipo lista_variables ';'{assignType(((Token)$1.obj).getLexema(),(ArrayList<Token>)$2.obj);
														System.out.println("ejecuto regla de ASSIGN TYPE");};
							

tipo : INTEGER {$$.obj = $1.obj;
                System.out.println("Problando INTEGER: "+((Token)$1.obj).getLexema());} //ANDA
     | FLOAT   {$$.obj = $1.obj;
	            System.out.println("Problando FLOAT: "+((Token)$1.obj).getLexema());}  //ANDA
	 | error{UI2.addText(UI2.txtDebug,"Linea: "+lexAn.getLineNumber()+". Columna: "+lexAn.getIndexLine()+" ERROR DE TIPO DESCONOCIDO "+"\n"); errores.add("Linea: "+lexAn.getLineNumber()+"ERROR DE TIPO DESCONOCIDO");};

lista_variables :IDENTIFICADOR  {$$.obj=new ArrayList<Token>(); ((ArrayList<Token>)($$.obj)).add((Token)$1.obj); //ANDA
				                System.out.println(" Probando IDENTIFICADOR Lista de variables: "+lexAn.getSymbolTable().getToken(((Token)$1.obj).getLexema()).getLexema());}

				| IDENTIFICADOR ',' lista_variables  {$$.obj=new ArrayList<Token>(); ((ArrayList<Token>)($3.obj)).add((Token)$1.obj); 
                                                       ((ArrayList<Token>)($$.obj)).addAll((ArrayList<Token>)$3.obj);
													   System.out.print("SYSOUT DEL VECTOR "+((ArrayList<Token>)($$.obj)).size());} 
				| error{UI2.addText(UI2.txtDebug,"Linea: "+lexAn.getLineNumber()+". Columna: "+lexAn.getIndexLine()+" ERROR LISTA DEVARIABLES"+"\n"); errores.add("Linea: "+lexAn.getLineNumber()+"ERROR LISTA DEVARIABLES");};
		  
inicio_IF: IF '(' cond ')' {Terceto bFalse = new TercetoBFalse(tercetos.get(tercetos.size()-1));
							System.out.println("Tamaño del ARREGLO TERCETO EN IF CONDICION: "+tercetos.size());
							System.out.println("mostraaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                            tercetos.add(bFalse);
							System.out.println("El tamaño del arreglo TERCETO en IF es: "+tercetos.size());
							bFalse.setPosition((Integer)tercetos.size());
							stack.push(bFalse);} cuerpo_IF

cuerpo_IF : bloque_sentencias  ENDIF {Terceto bFalse = stack.pop();
									Terceto simple = new TercetoSimple((Integer)tercetos.size()+1);
									bFalse.setSecond(simple);} ';'

		  | bloque_sentencias  {	Terceto bInconditional = new TercetoBInconditional();
									tercetos.add(bInconditional); 
									bInconditional.setPosition((Integer)tercetos.size());
									Terceto bFalse = stack.pop();
									Terceto simple = new TercetoSimple((Integer)tercetos.size()+1);
									System.out.println("El tamaño del arreglo TERCETO en CUERPO_IF SIMPLE es: "+tercetos.size());
									stack.push(bInconditional);
									bFalse.setSecond(simple);//Set linea donde termina el then

								}ELSE bloque_sentencias ENDIF {Terceto bInconditional = stack.pop();
																System.out.println("El tamaño del arreglo TERCETO en CUERPO_IF FINCONDICIONAL es: "+tercetos.size());
								                               Terceto simple = new TercetoSimple((Integer)tercetos.size());
								                               bInconditional.setFirst(simple);/*Set linea donde termina el if*/
															   System.out.println("Finalizando el terceto Incondicional");}';';
		  
		  
sentencia_ejecutable : inicio_IF {estructuras.add(lexAn.getLineNumber()+". sentencia ejecutable if\n");}
					 
					 | FOR '(' IDENTIFICADOR operador_asignacion expresion ';'{	assignValue((Element)$3.obj,(Element)$5.obj);
																				Terceto initFor = new TercetoAsignacion((Element)$3.obj,(Element)$5.obj); 
																				tercetos.add(initFor);
																				initFor.setPosition(tercetos.size());
																				stack.push(tercetos.get(tercetos.size()-1)); 
																				System.out.println("terceto inicio"+stack.peek().toString());} 
																				expresion comparador expresion {Terceto comp = new TercetoComparador((Token)$8.obj,(Element)$7.obj,(Element) $9.obj); //ANDA
																												tercetos.add(comp);
																												
																												comp.setPosition(tercetos.size());
																												System.out.println("==================COND FOR==========El tamaño del arreglo TERCETO en CONDICION DEL FOR es: "+tercetos.size());
																												Terceto bFalse = new TercetoBFalse(tercetos.get(tercetos.size()-1));
																												tercetos.add(bFalse);
																												bFalse.setPosition(tercetos.size());
																												stack.push(bFalse);} 
																												';' variable DECREMENTO ')'  bloque_sentencias  {estructuras.add(lexAn.getLineNumber()+". sentencia ejecutable for\n");
																																								Terceto varUpdate = new TercetoDecremento((Token)$11.obj);
																																								tercetos.add(varUpdate);
																																								varUpdate.setPosition(tercetos.size());
																																								Terceto bInconditional = new TercetoBInconditional();
																																								tercetos.add(bInconditional);
																																								bInconditional.setPosition(tercetos.size());
																																								Terceto bFalse = stack.pop();	
																																								Terceto simple = new TercetoSimple(tercetos.size()+1);
																																								bFalse.setSecond(simple);
																																								simple = stack.pop();				
																																								simple.setPosition(simple.getPosition()+1);
																																								bInconditional.setFirst(simple);
																																								}
					 
					 | FOR '(' error ')'  bloque_sentencias {UI2.addText(UI2.txtDebug,"Linea: "+lexAn.getLineNumber()+". Columna: "+lexAn.getIndexLine()+" ERROR EN SENTENCIA FOR: INICIALIZACION, CONDICION, ACTUALIZACION "+"\n"); errores.add("Linea: "+lexAn.getLineNumber()+"ERROR EN SENTENCIA FOR CONDICION BLOQUE INVALIDO");} 

					 | FOR error ';'   {UI2.addText(UI2.txtDebug,"Linea: "+lexAn.getLineNumber()+". Columna: "+lexAn.getIndexLine()+" ERROR EN SENTENCIA FOR  "+"\n"); errores.add("Linea: "+lexAn.getLineNumber()+"ERROR EN SENTENCIA FOR");}  

					 | asignacion {estructuras.add(lexAn.getLineNumber()+". asignacion\n");}
		
					 | variable DECREMENTO ';'{estructuras.add(lexAn.getLineNumber()+". variable  decremento\n");}
					 
					 | PRINT '(' CADENA ')' ';' {estructuras.add(lexAn.getLineNumber()+". print\n");}

					 | PRINT error ';'{UI2.addText(UI2.txtDebug,"Linea: "+lexAn.getLineNumber()+". Columna: "+lexAn.getIndexLine()+" ERROR EN PRINT "+"\n"); errores.add("Linea: "+lexAn.getLineNumber()+"ERROR EN PRINT");}  

					 | PRINT '(' CADENA ')' error {UI2.addText(UI2.txtDebug,"Linea: "+lexAn.getLineNumber()+". Columna: "+lexAn.getIndexLine()+" ERROR EN PRINT FALTA ;"+"\n"); errores.add("Linea: "+lexAn.getLineNumber()+"ERROR EN PRINT FALTA ;");}; 
					 


asignacion : variable operador_asignacion expresion ';'{ Token T1 =lexAn.getSymbolTable().getToken((((Element)$1.obj).getLexema()));
                                                         $$.obj = new TercetoAsignacion(T1,(Element)$3.obj);
														 assignValue(T1,(Element)$3.obj);
                                                         tercetos.add((Terceto)$$.obj);
														((Terceto)$$.obj).setPosition((Integer)tercetos.size());                                                      
														 System.out.println("El tamaño del arreglo TERCETO en ASIGNACION es: "+tercetos.size());} 
           | variable operador_asignacion error ';' {UI2.addText(UI2.txtDebug,"Linea: "+lexAn.getLineNumber()+". Columna: "+lexAn.getIndexLine()+" ERROR DE ASIGNACION"+"\n"); errores.add("Linea: "+lexAn.getLineNumber()+"ERROR DE ASIGNACION\n");};
			

cond :  expresion comparador expresion{$$.obj = new TercetoComparador((Token)$2.obj,(Element)$3.obj,(Element) $1.obj); //ANDA
                                                    tercetos.add((Terceto)$$.obj);
                                                    ((Terceto)$$.obj).setPosition(tercetos.size());
													System.out.println("El tamaño del arreglo TERCETO en CONDICION es: "+tercetos.size());
                                                    /*estructuras.add("Linea "+analizador.NLineas+": Condicion");*/}
	  | error{UI2.addText(UI2.txtDebug,"Linea: "+lexAn.getLineNumber()+". Columna: "+lexAn.getIndexLine()+" ERROR EN CONDICION"+"\n"); errores.add("Linea: "+lexAn.getLineNumber()+"ERROR EN CONDICION");};




bloque_sentencias :   bloque_sentencias_ejecutable ;

bloque_sentencias_ejecutable : '{' bloque_sentencias_ejecutable sentencia_ejecutable '}'
				   | sentencia_ejecutable;


expresion : termino {$$.obj = $1.obj;}

		  | expresion '+' termino {     $$.obj = new TercetoSuma((Element)$1.obj,(Element) $3.obj); //ANDA
                                        tercetos.add((Terceto)$$.obj);
										((Terceto)$$.obj).setPosition(tercetos.size());
                                        System.out.println("El tamaño del arreglo TERCETO en SUMA es: "+tercetos.size());
                                        /*Estructuras.add("Linea "+analizador.NLineas+": Expresion");*/}
										
		  | expresion '-' termino{      $$.obj = new TercetoResta((Element)$1.obj,(Element) $3.obj); //ANDA
                                         tercetos.add((Terceto)$$.obj);
										((Terceto)$$.obj).setPosition(tercetos.size());
										System.out.println("El tamaño del arreglo TERCETO en RESTA es: "+tercetos.size());};
			

termino : factor {$$.obj = $1.obj;} //ANDA
         
		 | termino DECREMENTO {$$.obj = new TercetoDecremento((Element)$1.obj);
								System.out.println("ANTES de setear la posicion en terceto decremento");
                               tercetos.add((Terceto)$$.obj);
							   ((Terceto)$$.obj).setPosition(tercetos.size()); 
							   System.out.println("posicion terceto decremento: "+tercetos.size());}
		 
		 | termino '*' factor {$$.obj = new TercetoMultiplicacion((Element)$1.obj,(Element) $3.obj);
                               tercetos.add((Terceto)$$.obj);
                               ((Terceto)$$.obj).setPosition((Integer)tercetos.size()); 
							   System.out.println("posicion terceto ENZO multiplicacion");
							   System.out.println("posicion terceto multi: "+tercetos.size());}
		 
		 | termino '/' factor{$$.obj = new TercetoDivision((Element)$1.obj,(Element)$3.obj);
							  tercetos.add((Terceto)$$.obj);
							  ((Terceto)$$.obj).setPosition(tercetos.size());
							  System.out.println(" posicion terceto division: "+tercetos.size());};


factor : constante {$$.obj = $1.obj;} // ANDA
		
		| variable {$$.obj = $1.obj;};//preguntar por este caso como se soluciona.

constante : CTEINTEGER {$$.obj = lexAn.getSymbolTable().getToken(((Token)$1.obj).getLexema()); //ANDA
						System.out.println("Probando CTEINTEGER: "+lexAn.getSymbolTable().getToken(((Token)$1.obj).getLexema()));} 
		
		| CTEFLOAT {$$.obj = lexAn.getSymbolTable().getToken(((Token)$1.obj).getLexema());}

variable : IDENTIFICADOR {$$.obj = lexAn.getSymbolTable().getToken(((Token)$1.obj).getLexema()); // ANDA
						  System.out.println("Probando IDENTIFICADOR en regla de variable ident: "+lexAn.getSymbolTable().getToken(((Token)$1.obj).getLexema()));}
		 | valor_matrix;

valor_matrix : IDENTIFICADOR '[' expresion ']' '['expresion']'; 		


comparador : '>'
			| '<'
			|MENORIGUAL 
			| MAYORIGUAL 
			| DISTINTO 
			| '=';

			

operador_asignacion : ASIGNACION{};

%%

/**
*
*COMIENZO CODIGO AGREGADO POR NOSOTROS
*
*/
private LexicalAnalyzer lexAn;
private ArrayList<String> errores;
private ArrayList<String> estructuras;
private ArrayList<Terceto> tercetos;
private Stack<Terceto> stack; 

private void yyerror(String string) {
	System.out.println("Error en elparseo de la sentencia:"+string);
	// TODO Auto-generated method stub
	
}
public void run(){
	parse();
}

public void parse(){
	yyparse();
	showTercetos();
}

public LexicalAnalyzer getLexicalAnalizer(){
	return lexAn;
}

public ArrayList<String> getErrores() {
	return errores;
}

public void showTercetos(){
	System.out.println("------TERCETOS------");
	System.out.println(this.tercetos.toString());
}
public ArrayList<String> getEstructuras(){
		return estructuras;
}

public ArrayList<Terceto> getTercetos() {
	return tercetos;
}

/*
*se asigna el valor de la expresion al identificador indicado 
* leftOp = IDENTIFICADOR rightOp = expresion
*/
public void assignValue (Element leftOp, Element rightOp){
	//agregar control de tipos para realizar la asignacion
	if ( rightOp == null ) {
		System.out.println("error variable no inicializada");
	} else {
			System.out.println("valor asignado"+rightOp.getValue()+" a la variable "+leftOp.getLexema());
			leftOp.setValue(rightOp.getValue());	
    	}
	
}
public void assignType(String type, ArrayList<Token> tokens ) {
		Token tk=null;
		//System.out.println("===================asignacion de tipos: type "+type);
		for (Token token : tokens) {
			tk = lexAn.getSymbolTable().getToken(token.getLexema());
			//System.out.println("===================TOKEN"+tk.toString());
			if ((tk != null) && (tk.hasTypeVariable())){
				//System.out.println("===================Error Sintactico");
			} else if (tk != null){
					tk.setTypeVariable(type);
					//System.out.println("===================SetType: tipo asignado "+type+" a token "+tk.toString());
				}
			}	
}

private int yylex() {
	System.out.println("ejecuto yylex principio del metodo");
	Token token = lexAn.getToken();

	if (token != null){
		String type = token.getType();
		yylval = new ParserVal(token);

		if (type.equals("IDENTIFICADOR"))
		{
			return IDENTIFICADOR;
		}
		if (type.equals("CADENA"))
		{
			return CADENA;
		}
		if (type.equals("ANOT0")){
				return ANOT0;
			}
		if (type.equals("ANOT1")){
			return ANOT1;
			}

		if (type.equals("PALABRARESERVADA")){
			if (token.getLexema().equals("if")){
				return IF;
			}
			if (token.getLexema().equals("else")){
				return ELSE;
			}
			if (token.getLexema().equals("print")){
				return PRINT;
			}
			if (token.getLexema().equals("for")){
				return FOR;
			}
			if (token.getLexema().equals("endif"))
			{
				return ENDIF;
			}
			if (token.getLexema().equals("integer"))
			{
				return INTEGER;
			}
			if (token.getLexema().equals("float"))
			{
				return FLOAT;
			}
			if (token.getLexema().equals("matrix"))
			{
				return MATRIX;
			}
			if (token.getLexema().equals("allow"))
			{
				return ALLOW;
			}
			if (token.getLexema().equals("to"))
			{
				return TO;
			}

		}
		if (type.equals("MAYORIGUAL"))
		{
			return MAYORIGUAL;
		}
		if (type.equals("MENORIGUAL"))
		{
			return MENORIGUAL;
		}
		if (type.equals("COMENTARIO"))
		{
			return COMENTARIO;
		}
		if (type.equals("DISTINTO"))
		{
			return DISTINTO;
		}
		if (type.equals("ASIGNACION"))
		{
			return ASIGNACION;
		}
		if (type.equals("DECREMENTO"))
		{
			return DECREMENTO;
		}
		if (type.equals("INTEGER"))
		{
			//yylval = new ParserVal(token);
			return CTEINTEGER;
		}
		if (type.equals("FLOAT"))
		{
			//yylval = new ParserVal(token);
			return CTEFLOAT;
		}
		if (type.equals("LITERAL")){
			return lexAn.getLexema().charAt(0);
		}

	}
	return 0;
}
	
	
public Parser(String sourcePath) {
	lexAn       = new LexicalAnalyzer(sourcePath);
	errores     = new ArrayList<String>();
	estructuras = new ArrayList<String>();
	tercetos    = new ArrayList<Terceto>();
	stack 		= new Stack<Terceto>();
	

}
	