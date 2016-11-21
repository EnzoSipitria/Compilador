/* YACC Declarations */
%{

import lexicalAnalyzer.LexicalAnalyzer;
import structures.Element;
import structures.Terceto;
import structures.TercetoAsignacion;
import structures.TercetoComparador;
import structures.TercetoConversion;
import structures.TercetoLabel;
import structures.TercetoDivision;
import structures.TercetoPrint;
import matrix.AssignMatrix;
import matrix.DivisionMatrix;
import matrix.OperationMatrix;
import matrix.TercetoMatrix;
import structures.TercetoMultiplicacion;
import structures.TercetoDecremento;
import structures.TercetoResta;
import structures.TercetoSuma;
import structures.Token;
import structures.TokenMatrix;
import interfaz.UI2;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Stack;
import structures.TercetoBFalse;
import structures.TercetoBInconditional;
import structures.TercetoSimple;
import structures.TercetoBase;
import structures.TercetoReferencia;
import structures.AuxGenerator;


%}

%token COMENTARIO IDENTIFICADOR PALABRARESERVADA MENORIGUAL MAYORIGUAL DISTINTO ASIGNACION DECREMENTO CADENA FLOAT INTEGER IF ENDIF FOR PRINT CTEINTEGER CTEFLOAT MATRIX ANOT0 ANOT1 ALLOW TO ELSE
%start inicio

/* Grammar follows */
%%

inicio : IDENTIFICADOR programa{ lexAn.getSymbolTable().getToken(((Token) $1.obj).getLexema()).setUse("var");
                                 ((Token) $1.obj).setUse("nombre de programa");};

programa :  sentencia_declarativa bloque_sentencias
          | sentencia_declarativa
		  //|	'{'error'}' {UI2.addText(UI2.txtDebug,"Linea: "+printLine+". DECLARACION EN LUGAR INCORRECTO"+"\n"); errores.add("Linea: "+printLine+" DECLARACION EN LUGAR INCORRECTO.");};	  
		  | bloque_sentencias;

sentencia_declarativa : sentencia_declarativa_datos {estructuras.add(lexAn.getLineNumber()+". sentencia declarativa de datos\n");}
					  | sentencia_declarativa sentencia_declarativa_datos {estructuras.add(lexAn.getLineNumber()+". sentencia declarativa de datos\n");}
					  | sentencia_declarativa sentencia_declarativa_matrix {estructuras.add(printLine+". sentencia declarativa de matrices\n");}
					  | sentencia_declarativa_matrix {estructuras.add(printLine+". sentencia declarativa de matrices\n");}
					  | sentencia_declarativa_conversion {estructuras.add(lexAn.getLineNumber()+". sentencia declarativa de conversion de tipos\n");}
					  | sentencia_declarativa sentencia_declarativa_conversion {estructuras.add(lexAn.getLineNumber()+". sentencia declarativade conversion de tipos\n");};
					  
					  


sentencia_declarativa_conversion : ALLOW tipo TO tipo ';' {convertionMatrix.addConvertion(((Token)$2.obj).getLexema(), ((Token)$4.obj).getLexema());};			  
					  
					  
sentencia_declarativa_matrix : tipo MATRIX IDENTIFICADOR '[' CTEINTEGER ']' '[' CTEINTEGER ']' ';' { String tipoVariable = ((Token) $1.obj).getLexema();
																									((Token) $3.obj).setTypeVariable(tipoVariable);
																									((Token) $3.obj).setUse("mat");
																									Token aux = lexAn.getSymbolTable().getToken(((Token)$3.obj).getLexema());
																									controlRedefVariables(((Token) $3.obj),aux,"mat");
																									annotation = 0;
																									changeTokenMatrix (((Token) $3.obj),annotation,((Element) $5.obj).getValue(),((Element) $8.obj).getValue());
																									//TercetoAnotacion anot = new TercetoAnotacion(0);
																									//tercetos.add(anot);
																									//anot.setPosition(tercetos.size());
																									//TercetoMatrix matrix= new TercetoMatrix (((Token) $3.obj).getLexema(),((Token) $5.obj).getValue(),((Token) $8.obj).getValue());
																									//tercetos.add(matrix);
																									//matrix.setPosition(tercetos.size());
																									}

							  | tipo MATRIX IDENTIFICADOR '[' CTEINTEGER ']' '[' CTEINTEGER ']' ';' anotacion_matrix { String tipoVariable = ((Token) $1.obj).getLexema();
																													  ((Token) $3.obj).setTypeVariable(tipoVariable);
																													  ((Token) $3.obj).setUse("mat");
																													  Token aux = lexAn.getSymbolTable().getToken(((Token)$3.obj).getLexema());
																													  controlRedefVariables(((Token) $3.obj),aux,"mat");
																													  annotation = (Integer)((Token) $11.obj).getValue();
																													  System.out.print("declaracion de matrices: annotation"+annotation);
																													  changeTokenMatrix (((Token) $3.obj),annotation,((Token) $5.obj).getValue(),((Token) $8.obj).getValue());
																													  
																													   }
							  
							  | tipo MATRIX IDENTIFICADOR '[' CTEINTEGER ']' '[' CTEINTEGER ']' inicializacion_matrix { String tipoVariable = ((Token) $1.obj).getLexema();
																													   ((Token) $3.obj).setTypeVariable(tipoVariable);
																														((Token) $3.obj).setUse("mat");
																													   Token aux = lexAn.getSymbolTable().getToken(((Token)$3.obj).getLexema());
																													   controlRedefVariables(((Token) $3.obj),aux,"mat");
																													   //int anot = (Integer)tercetos.get(tercetos.size()-1).getFirst();
																													   //TercetoMatrix matrix= new TercetoMatrix (((Token) $3.obj).getLexema(),((Token) $5.obj).getValue(),((Token) $8.obj).getValue());
																													   //tercetos.add(matrix);
																													  // matrix.setPosition(tercetos.size());
																													  System.out.print("declaracion de matrices: annotation"+annotation);
																													   changeTokenMatrix (((Token) $3.obj),annotation,((Token) $5.obj).getValue(),((Token) $8.obj).getValue());
																													   System.out.print("declaracion de matrices ide: token"+((Token) $3.obj));
																													   initMatrix (((ArrayList<Token>) $10.obj),annotation, ((Token) $3.obj), ((Token) $5.obj).getValue(), ((Token) $8.obj).getValue() );
																													   
																													   }

							  | tipo MATRIX error ';' {UI2.addText(UI2.txtDebug,"Linea: "+printLine+". DECLARACION ERRONEA"+"\n"); errores.add("Linea: "+printLine+" DECLARACION ERRONEA");};
								
								
								
inicializacion_matrix : operador_asignacion '{' lista_valores_matrix '}' ';' {$$.obj=new ArrayList<Token>();
																			  ((ArrayList<Token>)($$.obj)).addAll((ArrayList<Token>)$3.obj);
																			  annotation = 0;
																			  }

					   | operador_asignacion '{' lista_valores_matrix '}' ';' anotacion_matrix {$$.obj=new ArrayList<Token>();
																								((ArrayList<Token>)($$.obj)).addAll((ArrayList<Token>)$3.obj);																					
																								annotation = (Integer)((Token)($6.obj)).getValue();
																								System.out.print("lista devalores matriz: annotation"+annotation);
																								};
					   
anotacion_matrix : ANOT0 {$$.obj = ((Token) $1.obj);}| ANOT1 {$$.obj = ((Token) $1.obj);};

lista_valores_matrix : fila_valores_matrix ';' {$$.obj=new ArrayList<Token>();
												((ArrayList<Token>)($$.obj)).addAll((ArrayList<Token>)$1.obj);}

					  | lista_valores_matrix fila_valores_matrix ';' {$$.obj=new ArrayList<Token>();
																	   ((ArrayList<Token>)($$.obj)).addAll((ArrayList<Token>)$1.obj); 
																	  ((ArrayList<Token>)($$.obj)).addAll((ArrayList<Token>)$2.obj);}
					  
					  | error ';'{UI2.addText(UI2.txtDebug,"Linea: "+printLine+". ERROR EN LA LISTA DE VARIABLES"+"\n"); errores.add("Linea: "+printLine+"ERROR EN LA LISTA DE VARIABLES"+"\n");};


fila_valores_matrix : constante {$$.obj=new ArrayList<Token>();
								 ((ArrayList<Token>)($$.obj)).add((Token)$1.obj);} 

					| fila_valores_matrix ',' constante {$$.obj=new ArrayList<Token>();
														 ((ArrayList<Token>)($$.obj)).addAll((ArrayList<Token>)$1.obj); 
														 ((ArrayList<Token>)($$.obj)).add((Token)$3.obj);};


sentencia_declarativa_datos : tipo lista_variables ';'{assignType(((Token)$1.obj).getLexema(),(ArrayList<Token>)$2.obj);
														System.out.println("ejecuto regla de ASSIGN TYPE");};
							

tipo : INTEGER {$$.obj = $1.obj;
                System.out.println("Problando INTEGER: "+((Token)$1.obj).getLexema());} //ANDA
     | FLOAT   {$$.obj = $1.obj;
	            System.out.println("Problando FLOAT: "+((Token)$1.obj).getLexema());}  //ANDA
	 | error{UI2.addText(UI2.txtDebug,"Linea: "+lexAn.getLineNumber()+". Columna: "+lexAn.getIndexLine()+" ERROR DE TIPO DESCONOCIDO "+"\n"); errores.add("Linea: "+lexAn.getLineNumber()+"ERROR DE TIPO DESCONOCIDO");};

lista_variables :IDENTIFICADOR  {$$.obj=new ArrayList<Token>();
								 Token aux=lexAn.getSymbolTable().getToken(((Token) $1.obj).getLexema());
								 ((Token) $1.obj).setUse("var");
								 System.out.println("no se que poner en este mensaje"+((Token) $1.obj).getUse());
                                 System.out.println("no se que poner en este mensaje"+lexAn.getSymbolTable().getToken(((Token) $1.obj).getLexema()));
								 ((ArrayList<Token>)($$.obj)).add((Token)$1.obj);
								 controlRedefVariables(((Token) $1.obj),aux,"var");
				                 System.out.println(((Token) $1.obj));
				                  System.out.println(" Probando IDENTIFICADOR Lista de variables: ");
					              }

				| IDENTIFICADOR ',' lista_variables  {$$.obj=new ArrayList<Token>();
													  Token aux=lexAn.getSymbolTable().getToken(((Token) $1.obj).getLexema());
													  ((Token) $1.obj).setUse("var");
													  ((ArrayList<Token>)($3.obj)).add((Token)$1.obj); 
													  controlRedefVariables(((Token) $1.obj),aux,"var");
                                                      ((ArrayList<Token>)($$.obj)).addAll((ArrayList<Token>)$3.obj);
													  System.out.print("SYSOUT DEL VECTOR "+((ArrayList<Token>)($$.obj)).size());} 
													  
				| error{UI2.addText(UI2.txtDebug,"Linea: "+lexAn.getLineNumber()+". ERROR LISTA DEVARIABLES"+"\n"); errores.add("Linea: "+lexAn.getLineNumber()+"ERROR LISTA DEVARIABLES");};
		  
inicio_IF: IF cond {Terceto bFalse = new TercetoBFalse(tercetos.get(tercetos.size()-1)); //Terceto comparacion
							System.out.println("Tamaño del ARREGLO TERCETO EN IF CONDICION: "+tercetos.size());
							System.out.println("mostraaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                            tercetos.add(bFalse);
							System.out.println("El tamaño del arreglo TERCETO en IF es: "+tercetos.size());
							bFalse.setPosition((Integer)tercetos.size());
							stack.push(bFalse);}  cuerpo_IF
		 |  IF error {UI2.addText(UI2.txtDebug,"Linea: "+numberLine.peek()+". ERROR EN if"+"\n"); errores.add("Linea: "+numberLine.peek()+"ERROR EN CONDICION");};


					
cuerpo_IF : bloque_sentencias  ENDIF {Terceto bFalse = stack.pop();
									Terceto simple = new TercetoSimple((Integer)tercetos.size()+1);
									tercetos.add(new TercetoLabel((Integer)tercetos.size()+1,(Integer)tercetos.size()+1));
									bFalse.setSecond(simple);} ';'
		  | bloque_sentencias  {	Terceto bInconditional = new TercetoBInconditional();
									tercetos.add(bInconditional); 
									bInconditional.setPosition((Integer)tercetos.size());
									tercetos.add(new TercetoLabel((Integer)tercetos.size()+2,(Integer)tercetos.size()+1));//+2 xq si y creo que anda
									bInconditional.setHasLabel(true);
									Terceto bFalse = stack.pop();
									Terceto simple = new TercetoSimple((Integer)tercetos.size()+1);
									System.out.println("El tamaÃ±o del arreglo TERCETO en CUERPO_IF SIMPLE es: "+tercetos.size());
									stack.push(bInconditional);
									bFalse.setSecond(simple);/*Set linea donde termina el then*/
                                     } ELSE bloque_sentencias ENDIF {Terceto bInconditional = stack.pop();
																System.out.println("El tamaño del arreglo TERCETO en CUERPO_IF FINCONDICIONAL es: "+tercetos.size());
								                               Terceto simple = new TercetoSimple((Integer)tercetos.size()+1);
								                               bInconditional.setFirst(simple);
															   bInconditional.setHasLabel(true);
															   tercetos.add(new TercetoLabel((Integer)tercetos.size()+1,(Integer)tercetos.size()+1));
															   System.out.println("Finalizando el terceto Incondicional");}';';



inicio_For: FOR '(' asig_for cond_for variable DECREMENTO')' bloque_sentencias  {
																		System.out.println("====== INICIO FOR ======= ");
																		estructuras.add(numberLine.pop()+". sentencia ejecutable for\n");
														            	Terceto varUpdate = new TercetoDecremento((Token)$5.obj);
																		tercetos.add(varUpdate);
																		varUpdate.setPosition(tercetos.size());
																		varUpdate.setTypeVariable("integer");
																		
																		Terceto bInconditional = new TercetoBInconditional();
																		tercetos.add(bInconditional);
																		bInconditional.setPosition(tercetos.size());
																		bInconditional.setHasLabel(true);
																		Terceto bFalse = stack.pop();	
																		System.out.println("terceto size "+tercetos.size());
																		tercetos.add(new TercetoLabel((Integer)tercetos.size()+2,(Integer)tercetos.size()+1));
																		Terceto simple = new TercetoSimple(tercetos.size()+1);
																		bFalse.setSecond(simple);
																		simple = stack.pop();
																		//se cambio la linea siguiente como la posicion del simple para que no falle laposicion a  la uqe se debe volver en el, salto incondicional
																		simple.setPosition(simple.getPosition());
																		bInconditional.setFirst(simple);
																					}
			| FOR '(' error ')' { System.out.println("tFOR ERROR");};

asig_for:  IDENTIFICADOR operador_asignacion expresion ';' { assignValue((Element)$1.obj,(Element)$3.obj);
						    Terceto initFor = new TercetoAsignacion(lexAn.getSymbolTable().getToken(((Element)$1.obj).getLexema()),(Element)$3.obj); 
							tercetos.add(initFor);
						    initFor.setPosition(tercetos.size());
							//stack.push(tercetos.get(tercetos.size()-1)); 
							//System.out.println("terceto inicio"+stack.peek().toString());
							
							if ( !lexAn.getSymbolTable().getToken(((Element)val_peek(3).obj).getLexema()).getTypeVariable().equals("integer") ){
								UI2.addText(UI2.txtDebug,"Linea: "+lexAn.getLineNumber()+". ERROR EN LIMITES DE ITERACION FOR: TIPO INCORRECTO. Debe ser integer"+"\n");
								errores.add("Linea: "+lexAn.getLineNumber()+"ERROR EN LIMITES DE ITERACION FOR: TIPO INCORRECTO. Debe ser integer");
							}
							
							};
							
			//| error {System.out.println("==================ASIG FOR====== FOR asignacion");};

cond_for: expresion comparador expresion ';' {
												System.out.println("=== COND FOR ==== ");
												System.out.println("terceto size "+tercetos.size());
												tercetos.add(new TercetoLabel((Integer)tercetos.size()+2,(Integer)tercetos.size()+1));
												if(  !((Element)$1.obj).getTypeVariable().equals(((Element) $3.obj).getTypeVariable()) ){
														String typeResult = operationMatrix.getTypeOperation( ((Element)$1.obj).getTypeVariable() , ((Element) $3.obj).getTypeVariable() );
														Terceto conversion;
														if (typeResult.equals(((Element)$1.obj).getTypeVariable())) {
															conversion = new TercetoConversion(((Element)$3.obj), typeResult);
														} else {
																conversion = new TercetoConversion(((Element)$1.obj), typeResult);
															}
														tercetos.add(conversion);
														(conversion).setPosition(tercetos.size());
												}
												Terceto comp = new TercetoComparador((Token)$2.obj,(Element)$1.obj,(Element) $3.obj); //ANDA
												comp.setTypeVariable(operationTypeVariable((Element)$1.obj,(Element) $3.obj));
												
												tercetos.add(comp);																				
												comp.setPosition(tercetos.size());
												comp.setHasLabel(true);
												stack.push(tercetos.get(tercetos.size()-1));
												//System.out.println("==================COND FOR==========El tamaño del arreglo TERCETO en CONDICION DEL FOR es: "+tercetos.size());
												Terceto bFalse = new TercetoBFalse(tercetos.get(tercetos.size()-1));
												tercetos.add(bFalse);
												bFalse.setPosition(tercetos.size());
												stack.push(bFalse);};
					
		 // | error{System.out.println("==================ASIG FOR====== FOR asignacion");};

					 


		  
sentencia_ejecutable : inicio_IF {estructuras.add(numberLine.pop()+". sentencia ejecutable if\n");}
					 
					 | inicio_For 
					 
					// | FOR '(' error ')'  bloque_sentencias {UI2.addText(UI2.txtDebug,"Linea: "+numberLine.peek()+". ERROR EN SENTENCIA FOR: INICIALIZACION, CONDICION, ACTUALIZACION "+"\n");estructuras.add(numberLine.peek()+". sentencia ejecutable for\n"); errores.add("Linea: "+numberLine.pop()+"ERROR EN SENTENCIA FOR CONDICION BLOQUE INVALIDO");} 

					// | FOR  '(' IDENTIFICADOR operador_asignacion expresion ';' expresion comparador expresion ';' variable DECREMENTO ')' error  {UI2.addText(UI2.txtDebug,"Linea: "+numberLine.peek()+". ERROR EN SENTENCIA FOR  "+"\n"); estructuras.add(numberLine.peek()+". sentencia ejecutable for\n"); errores.add("Linea: "+numberLine.pop()+"ERROR EN SENTENCIA FOR");}
					 
					 | asignacion {estructuras.add(lexAn.getLineNumber()+". asignacion\n");}
		
					 | variable DECREMENTO ';'{estructuras.add(lexAn.getLineNumber()+". variable  decremento\n");}
					 
					 | PRINT '(' CADENA ')' ';' {
												 System.out.println("===CADENA "+ (Element)$3.obj+" palabra reservada "+(Token)$1.obj);
												 Token token = lexAn.getSymbolTable().getToken((((Token)$3.obj).getLexema()));
												 Terceto cadena = new TercetoPrint(token);
												 tercetos.add(cadena);
												 cadena.setPosition(tercetos.size());
												 estructuras.add( printLine+". print\n");
												}

					 | PRINT error ';'{UI2.addText(UI2.txtDebug,"Linea: "+printLine+". ERROR EN PRINT "+"\n"); errores.add("Linea: "+lexAn.getLineNumber()+"ERROR EN PRINT");}  

					 | PRINT '(' CADENA ')' error {UI2.addText(UI2.txtDebug,"Linea: "+printLine+". ERROR EN PRINT FALTA \n"); errores.add("Linea: "+printLine+"ERROR EN PRINT FALTA ;");}; 
					 


asignacion : variable operador_asignacion expresion ';'{System.out.println("==ASIGNACION==");
	/*	String typeResult = ((Element)$1.obj).getTypeVariable();*/
	if(((Element)val_peek(3).obj).getTypeVariable()!=null){
		String typeResult = ((Element)val_peek(3).obj).getTypeVariable();
		String leftType = ((Element)val_peek(3).obj).getTypeVariable();
		String rightType = ((Element)val_peek(1).obj).getTypeVariable();
		System.out.println("la expresion a asignar es"+ (Element)val_peek(1).obj);

//		if ( ((Element)val_peek(1).obj).getUse() != null && ((Element)val_peek(1).obj).getUse().equals("mat"))
//		{
//			Token T1 =lexAn.getSymbolTable().getToken((((Element)val_peek(3).obj).getLexema()));
//			yyval.obj = new TercetoAsignacion(T1,tercetos.get(tercetos.size()-1));
//			/*System.out.print("Terceto que voy a eliminar: "+tercetos.get((tercetos.size()-1)));*/
//			/*tercetos.remove(tercetos.size()-1);*/
//			/*System.out.print("Terceto despues de eliminar: "+tercetos.get(tercetos.size()-1));*/
//			tercetos.add((Terceto)yyval.obj);
//
//			((Terceto)yyval.obj).setPosition((Integer)tercetos.size()); 
//
//		}
		System.out.println("accept Operation"+convertionMatrix.acceptOperation(leftType,rightType));
		if (convertionMatrix.acceptOperation(leftType,rightType)){	
			if(  !((Element)val_peek(3).obj).getTypeVariable().equals(((Element) val_peek(1).obj).getTypeVariable()) ){
				System.out.println("tipos distintos creacion terceto conversion");
				typeResult = convertionMatrix.getTypeOperation( ((Element)val_peek(3).obj).getTypeVariable() , ((Element) val_peek(1).obj).getTypeVariable() );
				Terceto conversion = new TercetoConversion(((Element)val_peek(1).obj), typeResult);
				tercetos.add(conversion);
				System.out.println("Terceto conv"+conversion);
				(conversion).setPosition(tercetos.size());
			}

			Token T1 =lexAn.getSymbolTable().getToken((((Element)val_peek(3).obj).getLexema()));
			if ( ((Element)val_peek(1).obj).getUse() != null && ((Element)val_peek(1).obj).getUse().equals("mat"))
			{
				yyval.obj = new TercetoAsignacion(T1,tercetos.get(tercetos.size()-1));
				/*System.out.print("Terceto que voy a eliminar: "+tercetos.get((tercetos.size()-1)));*/
				/*tercetos.remove(tercetos.size()-1);*/
				/*System.out.print("Terceto despues de eliminar: "+tercetos.get(tercetos.size()-1));*/
				tercetos.add((Terceto)yyval.obj);
				((Terceto) yyval.obj).setTypeVariable(typeResult);
				((Terceto)yyval.obj).setPosition((Integer)tercetos.size()); 

			}else {
				//Token T1 =lexAn.getSymbolTable().getToken((((Element)val_peek(3).obj).getLexema()));

				yyval.obj = new TercetoAsignacion(T1,(Element)val_peek(1).obj);
				System.out.println("terceto asignacion creado $$.obj"+yyval.obj); 
				/*((Element)$3.obj).setTypeVariable(assignTypeVariable(((Element)$1.obj),((Element)$3.obj)));*/
				assignValue(T1,(Element)val_peek(1).obj);
				System.out.println("$$.obj "+yyval.obj);
				((Terceto) yyval.obj).setTypeVariable(typeResult); /*cambiar el tipo*/
				System.out.println("$$.obj seteado type variable "+yyval.obj+" tipo asignado "+((Terceto)yyval.obj).getTypeVariable());
				tercetos.add((Terceto)yyval.obj);
				((Terceto)yyval.obj).setPosition((Integer)tercetos.size());                                                      
				System.out.println("Tipo variableMMMMMM asignacion "+typeResult+"El tipo del TERCETO en ASIGNACION es: "+((Terceto)yyval.obj).getTypeVariable()+":="+((Element)val_peek(1).obj).getTypeVariable());
			}
		} else {
			UI2.addText(UI2.txtDebug,"Linea: "+lexAn.getLineNumber()+". ERROR tipos incompatibles\n"); 
			errores.add("Linea: "+lexAn.getLineNumber()+"ERROR tipos incompatibles\n");			
		}
	}
													}
														 
           | variable error expresion ';' {UI2.addText(UI2.txtDebug,"Linea: "+lexAn.getLineNumber()+". ERROR DE OPERADOR ASIGNACION"+"\n"); errores.add("Linea: "+lexAn.getLineNumber()+"ERROR DE OPERADOR ASIGNACION\n");}
           | variable operador_asignacion error {UI2.addText(UI2.txtDebug,"Linea: "+lexAn.getLineNumber()+". ERROR DE ASIGNACION"+"\n"); errores.add("Linea: "+lexAn.getLineNumber()+"ERROR DE ASIGNACION\n");};
			

cond :  '(' expresion comparador expresion ')' {	if(  !((Element)$2.obj).getTypeVariable().equals(((Element) $4.obj).getTypeVariable()) ){
														String typeResult = operationMatrix.getTypeOperation( ((Element)$2.obj).getTypeVariable() , ((Element) $4.obj).getTypeVariable() );
														Terceto conversion;
														if (typeResult.equals(((Element)$2.obj).getTypeVariable())) {
															conversion = new TercetoConversion(((Element)$4.obj), typeResult);
														} else {
																conversion = new TercetoConversion(((Element)$2.obj), typeResult);
															}
														tercetos.add(conversion);
														(conversion).setPosition(tercetos.size());
													}
													$$.obj = new TercetoComparador((Token)$3.obj,(Element)$4.obj,(Element) $2.obj); //ANDA
													((Element)$$.obj).setTypeVariable(operationTypeVariable((Element)$2.obj,(Element) $4.obj));
                                                    tercetos.add((Terceto)$$.obj);
                                                    ((Terceto)$$.obj).setPosition(tercetos.size());
													((Element)$$.obj).setHasLabel(true);
													System.out.println("El tamaño del arreglo TERCETO en CONDICION es: "+tercetos.size());
                                                    }
	  | '('error')'{UI2.addText(UI2.txtDebug,"Linea: "+lexAn.getLineNumber()+". Columna: "+lexAn.getIndexLine()+" ERROR EN CONDICION"+"\n"); errores.add("Linea: "+lexAn.getLineNumber()+"ERROR EN CONDICION");}

	  |'('expresion comparador  error {UI2.addText(UI2.txtDebug,"Linea: "+lexAn.getLineNumber()+". ERROR EN IF paren2"+"\n"); errores.add("Linea: "+lexAn.getLineNumber()+"ERROR EN CONDICION");};


bloque_sentencias :   bloque_sentencias_ejecutable ;

bloque_sentencias_ejecutable: '{' grupo_sentencias '}'
							 
				| sentencia_ejecutable;

grupo_sentencias: sentencia_ejecutable grupo_sentencias

                  | sentencia_ejecutable;

expresion : termino {$$.obj = $1.obj;}

		  | expresion '+' termino {    			System.out.println("==== expresion + termino ==== ");
												System.out.println("expresion "+ $1.obj+"  termino "+$3.obj);
												if(  !((Element)$1.obj).getTypeVariable().equals(((Element) $3.obj).getTypeVariable()) ){
												String typeResult = operationMatrix.getTypeOperation( ((Element)$1.obj).getTypeVariable() , ((Element) $3.obj).getTypeVariable() );
												Terceto conversion;
												if (typeResult.equals(((Element)$1.obj).getTypeVariable())) {
													conversion = new TercetoConversion(((Element)$3.obj), typeResult);
												} else {
														conversion = new TercetoConversion(((Element)$1.obj), typeResult);
													}
												tercetos.add(conversion);
												(conversion).setPosition(tercetos.size());
											}
											
									   $$.obj = new TercetoSuma((Element)$1.obj,(Element) $3.obj); //ANDA
									   ((Element)$$.obj).setTypeVariable(operationTypeVariable((Element)$1.obj,(Element) $3.obj));
                                        tercetos.add((Terceto)$$.obj);
										((Terceto)$$.obj).setPosition(tercetos.size());
                                        System.out.println("El tamaño del arreglo TERCETO en SUMA es: "+tercetos.size());
										System.out.println("El tipo del TERCETO en SUMA es: "+((Element)$$.obj).getTypeVariable());
                                        /*Estructuras.add("Linea "+analizador.NLineas+": Expresion");*/}
										
		  | expresion '-' termino{     	if(  !((Element)$1.obj).getTypeVariable().equals(((Element) $3.obj).getTypeVariable()) ){
												String typeResult = operationMatrix.getTypeOperation( ((Element)$1.obj).getTypeVariable() , ((Element) $3.obj).getTypeVariable() );
												Terceto conversion;
												if (typeResult.equals(((Element)$1.obj).getTypeVariable())) {
													conversion = new TercetoConversion(((Element)$3.obj), typeResult);
												} else {
														conversion = new TercetoConversion(((Element)$1.obj), typeResult);
													}
												tercetos.add(conversion);
												(conversion).setPosition(tercetos.size());
											}
										$$.obj = new TercetoResta((Element)$1.obj,(Element) $3.obj); //ANDA
		                                ((Element)$$.obj).setTypeVariable(operationTypeVariable((Element)$1.obj,(Element) $3.obj));
                                         tercetos.add((Terceto)$$.obj);
										((Terceto)$$.obj).setPosition(tercetos.size());
										System.out.println("El tamaño del arreglo TERCETO en RESTA es: "+tercetos.size());};
			

termino : factor {$$.obj = $1.obj;} //ANDA
         
		 | termino DECREMENTO {$$.obj = new TercetoDecremento((Element)$1.obj);
								System.out.println("ANTES de setear la posicion en terceto decremento");
                               tercetos.add((Terceto)$$.obj);
							   ((Terceto)$$.obj).setPosition(tercetos.size()); 
							   System.out.println("posicion terceto decremento: "+tercetos.size());}
		 
		 | termino '*' factor { 			if(  !((Element)$1.obj).getTypeVariable().equals(((Element) $3.obj).getTypeVariable()) ){
												String typeResult = operationMatrix.getTypeOperation( ((Element)$1.obj).getTypeVariable() , ((Element) $3.obj).getTypeVariable() );
												Terceto conversion;
												if (typeResult.equals(((Element)$1.obj).getTypeVariable())) {
													conversion = new TercetoConversion(((Element)$3.obj), typeResult);
												} else {
														conversion = new TercetoConversion(((Element)$1.obj), typeResult);
													}
												tercetos.add(conversion);
												(conversion).setPosition(tercetos.size());
											}
		 
								$$.obj = new TercetoMultiplicacion((Element)$1.obj,(Element) $3.obj);
							   ((Element)$$.obj).setTypeVariable(operationTypeVariable((Element)$1.obj,(Element) $3.obj));
							   tercetos.add((Terceto)$$.obj);
                               ((Terceto)$$.obj).setPosition((Integer)tercetos.size()); 
							   System.out.println("posicion terceto ENZO multiplicacion");
							   System.out.println("posicion terceto multi: "+tercetos.size());
							 }
		 
		 | termino '/' factor{
									
											String typeResult = divisionTypeVariable( (Element)$1.obj , (Element) $3.obj );
											Terceto conversion;
											if (!typeResult.equals(((Element)$1.obj).getTypeVariable())) {
												conversion = new TercetoConversion(((Element)$1.obj), typeResult);
												tercetos.add(conversion);
												(conversion).setPosition(tercetos.size());
											} 
											if (!typeResult.equals(((Element)$3.obj).getTypeVariable())) {
													conversion = new TercetoConversion(((Element)$3.obj), typeResult);
													tercetos.add(conversion);
													(conversion).setPosition(tercetos.size());
													
												}
							
							  $$.obj = new TercetoDivision((Element)$1.obj,(Element)$3.obj);
							  ((Element)$$.obj).setTypeVariable(divisionTypeVariable((Element)$1.obj,(Element) $3.obj));
							  tercetos.add((Terceto)$$.obj);
							  ((Terceto)$$.obj).setPosition(tercetos.size());
							  System.out.println(" posicion terceto division: "+tercetos.size());};


factor : constante {$$.obj = $1.obj;} // ANDA
		
		| variable {$$.obj = $1.obj;};//preguntar por este caso como se soluciona.

constante : CTEINTEGER {$$.obj = lexAn.getSymbolTable().getToken(((Token)$1.obj).getLexema()); //ANDA
						System.out.println("Probando CTEINTEGER: "+lexAn.getSymbolTable().getToken(((Token)$1.obj).getLexema()));} 
		
		| CTEFLOAT {$$.obj = lexAn.getSymbolTable().getToken(((Token)$1.obj).getLexema());};

variable : IDENTIFICADOR { 
                           if (controlVarNotDeclared(((Token)$1.obj))){
								UI2.addText(UI2.txtDebug,"Linea: "+lexAn.getLineNumber()+". Variable ["+((Token)$1.obj).getLexema()+"] no declarada");
								errores.add("Linea: "+lexAn.getLineNumber()+"Variable no declarada");
							} else {$$.obj = lexAn.getSymbolTable().getToken(((Token)$1.obj).getLexema()); // ANDA
									System.out.println("Probando IDENTIFICADOR en regla de variable ident: "+lexAn.getSymbolTable().getToken(((Token)$1.obj).getLexema()));}
									}
		 | valor_matrix;

valor_matrix : IDENTIFICADOR '[' expresion ']' '['expresion']' {System.out.println("==== valor matrix ==== ");
																//$$.obj = lexAn.getSymbolTable().getToken(((Token)$1.obj).getLexema()); 
																//makeMatrix(Token ide ,Object rowIndex, Object columnIndex);
	                                                             makeMatrix((Token)$1.obj,((Token)$3.obj).getValue(),((Token)$6.obj).getValue());
																 System.out.println("ultimos tercetos "+tercetos.size()+" agregados"+tercetos.get(tercetos.size()-2));
																 //tercetos.get(tercetos.size()-1).setUse("mat");
																 $$.obj = tercetos.get(tercetos.size()-1);
																 if ( !((Element)$3.obj).getTypeVariable().equals("integer") || !((Element)$6.obj).getTypeVariable().equals("integer") )
																	UI2.addText(UI2.txtDebug,"Linea: "+lexAn.getLineNumber()+". Variable ["+((Token)$1.obj).getLexema()+"] con limites no enteros");
																	errores.add("Linea: "+lexAn.getLineNumber()+"Variable matriz con limites de tipo erroneos");
																}; 		


comparador : '>'
			| '<'
			|MENORIGUAL 
			| MAYORIGUAL 
			| DISTINTO 
			| '=';

			

operador_asignacion : ASIGNACION;

%%

/**
*
*COMIENZO CODIGO AGREGADO POR NOSOTROS
*
*/
private LexicalAnalyzer lexAn;
private ArrayList<String> errores;
private ArrayList<String> estructuras;
private int printLine=0;
private Stack<Integer> numberLine; 
private ArrayList<Terceto> tercetos;
private Stack<Terceto> stack; 
private AssignMatrix convertionMatrix;
private DivisionMatrix divisionMatrix;
private OperationMatrix operationMatrix;
private static AuxGenerator generator;

private int annotation;


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
		System.out.println("===================asignacion de tipos: type "+type);
		for (Token token : tokens) {
			tk = lexAn.getSymbolTable().getToken(token.getLexema());
			System.out.println("===================TOKEN"+tk.toString());
			if ((tk != null) && (tk.hasTypeVariable())){
				//System.out.println("===================Error Sintactico");
			} else if (tk != null){
					tk.setTypeVariable(type);
					//System.out.println("===================SetType: tipo asignado "+type+" a token "+tk.toString());
				}
			}	
}
 /* metodo para asignar tipos para asignaciones*/
public String assignTypeVariable(Element left,Element right){
	String leftTypeVariable = left.getTypeVariable();
	String rightTypeVariable = right.getTypeVariable();
	String value = convertionMatrix.getTypeOperation(leftTypeVariable, rightTypeVariable);
	return value;
}
public String assignTypeVariable(Element element){
	
	String value = element.getTypeVariable();	
	return value;
}

/*metodos paraasginar tipos en operaciones suma, resta, multiplicacion*/

public String operationTypeVariable(Element left,Element right){
	String leftTypeVariable = left.getTypeVariable();
	String rightTypeVariable = right.getTypeVariable();
	String value = operationMatrix.getTypeOperation(leftTypeVariable, rightTypeVariable);
	return value;
}

public String operationTypeVariable(Element element){
	
	String value = element.getTypeVariable();	
	return value;
}

/*metodos paraasginar tipos en operaciones division*/

public String divisionTypeVariable(Element left,Element right){
	String leftTypeVariable = left.getTypeVariable();
	String rightTypeVariable = right.getTypeVariable();
	String value = divisionMatrix.getTypeOperation(leftTypeVariable, rightTypeVariable);
	return value;
}

public String divisionTypeVariable(Element element){
	
	String value = element.getTypeVariable();	
	return value;
}


public void controlRedefVariables (Token token,Token aux, String prefix){
		String lexema = token.getLexema();
		System.out.println("========================IF estaen la tabla de simbolos");
		//System.out.println("Uso que viene de la tabla de simbolos: "+lexAn.getSymbolTable().getToken(lexema).getUse());
		//System.out.println("Uso que viene del token: "+token.getUse());
		if (token.getLexema().equals(aux.getLexema())){
				if (!token.getUse().equals(aux.getUse())) {
						System.out.println("========================USO DISTINTO, FIJATE LOS NOMBRES");
						token.setLexema(prefix+"@"+lexema);
						lexAn.getSymbolTable().addToken(prefix+"@"+lexema, token);
						System.out.println("AAA token lexema: "+token.getLexema());
						System.out.println("AAAAtoken lexema de la tabla desimbolos:"+lexAn.getSymbolTable().getToken(prefix+"@"+lexema));

						
				}
	}
}


private boolean controlVarNotDeclared (Token token){
	String lexema = token.getLexema();
	if (lexAn.getSymbolTable().containsSymbol(lexema)){
		return (lexAn.getSymbolTable().getToken(lexema).getTypeVariable() == null);
	}
	return true;
}


public void changeTokenMatrix (Token token, Object indexStart,Object rows, Object columns){
	String newType = token.getType();
	String newTypeVariable = token.getTypeVariable();
	String newLexema = token.getLexema();
	int newLineNumber = token.getLineNumber();
	int newIndexStart = (Integer) indexStart;
	int newRows = (Integer) rows;
	int newColumns = (Integer) columns;
	System.out.println("Token matrix cambiado");
	Token newToken = new Token (newType, newLineNumber, newLexema, newIndexStart, newRows, newColumns, null);
	newToken.setTypeVariable(newTypeVariable);
	newToken.setUse("mat");
	lexAn.getSymbolTable().addToken(token.getLexema(), newToken);
}



/**
 * este metodo se encarga de generar los tercetos necesarios para acceder a un elemento de la matriz
 * calculando la posicion en memoria con la formula (rowIndex-indexStart)*shift+(columnIndex-indexStart)
 * 
 * shift = columns - indexStart + 1
 * 
 * indexStart se extrae del token de la tabla de simbolos
 * indexStart = lexAn.getSymbolTable().getToken(ide.getLexema()).getIndexStart();
 * @param ide token de la matriz 
 * @param rowIndex valor de fila que se desea acceder
 * @param columnIndex valor de columna que se desea acceder
 * @param columns limite de columnas de la matriz en cuestion
 */
public void makeMatrix(Token ide ,Object rowIndex, Object columnIndex){ 
															
															int bytes = 2;
															int i1 =(Integer) rowIndex;
															int i2 =(Integer) columnIndex;
															int columns = lexAn.getSymbolTable().getToken(ide.getLexema()).getColumns();
															String typeVariable = lexAn.getSymbolTable().getToken(ide.getLexema()).getTypeVariable();
															System.out.println("type variable :"+typeVariable);
															if (typeVariable.equals("float")){
																bytes = 4;
																}
															System.out.println("================MakeMatrix===========");
															System.out.println("indices de la matriz limite filas:"+i1+" Limite de columnas:"+i2);
															 System.out.println("token ide"+ide);
															 int indexStart = lexAn.getSymbolTable().getToken(ide.getLexema()).getIndexStart();
															 System.out.println("makeMatrix, token de la tabla de simbolos"+lexAn.getSymbolTable().getToken(ide.getLexema()));
															 int shift = columns-indexStart+1;
															System.out.println("indexStart"+indexStart);
															
															  Terceto base=new TercetoBase((Token)ide);
                                                              tercetos.add((Terceto)base);
										                      ((Terceto)base).setPosition(tercetos.size());
															  base.setTypeVariable(typeVariable);
															  
															  Terceto simpleResta = new TercetoSimple(indexStart);
															  simpleResta.setTypeVariable("integer");
															  simpleResta.setUse("SHIFT");
															  tercetos.add((Terceto)simpleResta);
										                      ((Terceto)simpleResta).setPosition(tercetos.size());
															 
															 Terceto simpleI1 = new TercetoSimple(i1);
															  simpleI1.setTypeVariable("integer");
															  simpleI1.setUse("SHIFT");
															  tercetos.add((Terceto)simpleI1);
										                      ((Terceto)simpleI1).setPosition(tercetos.size());
										                     
															 Terceto resta= new TercetoResta(simpleI1,simpleResta);
										                      resta.setTypeVariable("integer");
															  resta.setUse("SHIFT");
										                      tercetos.add((Terceto)resta);
										                      ((Terceto)resta).setPosition(tercetos.size());
															 
															 Terceto simpleMult  = new TercetoSimple(shift);
															  simpleMult.setTypeVariable("integer");
															 simpleMult.setUse("SHIFT");
															  tercetos.add((Terceto)simpleMult);
										                      ((Terceto)simpleMult).setPosition(tercetos.size());
										                     
															 Terceto multi=new TercetoMultiplicacion((Terceto)resta,simpleMult);
										                      multi.setTypeVariable("integer");
															  multi.setUse("SHIFT");
										                      tercetos.add((Terceto)multi);
										                      ((Terceto)multi).setPosition(tercetos.size());
															  //TokenMatrix auxIde = lexAn.getSymbolTable().getToken(ide.getLexema())
															 // int indexStart = lexAn.getSymbolTable().getToken(ide.getLexema()).getIndexStart(); 
										                      
															  Terceto simpleI2 = new TercetoSimple(i2);
															  simpleI2.setTypeVariable("integer");
															  simpleI2.setUse("SHIFT");															  
															  tercetos.add((Terceto)simpleI2);
										                      ((Terceto)simpleI2).setPosition(tercetos.size());
															  
															 Terceto resta1= new TercetoResta(simpleI2,simpleResta);
															 resta1.setTypeVariable("integer");
															 resta1.setUse("SHIFT");
															 tercetos.add((Terceto)resta1);
										                      ((Terceto)resta1).setPosition(tercetos.size());
															  
										                      Terceto suma = new TercetoSuma((Terceto)resta1,(Terceto)multi);
															  suma.setTypeVariable("integer");
															  suma.setUse("SHIFT");
															  tercetos.add((Terceto)suma);
										                      ((Terceto)suma).setPosition(tercetos.size());
															  
															  Terceto simpleBytes = new TercetoSimple(bytes);
															  simpleBytes.setTypeVariable("integer");
															  simpleBytes.setUse("SHIFT");
															  tercetos.add((Terceto)simpleBytes);
										                      ((Terceto)simpleBytes).setPosition(tercetos.size());
															  
										                      Terceto multi1=new TercetoMultiplicacion((Terceto)suma,simpleBytes);										                      
										                      multi1.setTypeVariable("integer");tercetos.add((Terceto)multi1);
															  multi1.setUse("SHIFT");
										                      ((Terceto)multi1).setPosition(tercetos.size());
															  
										                      Terceto suma1= new TercetoSuma(base,(Terceto)multi1);
										                      suma1.setTypeVariable("integer");
															  suma1.setUse("SHIFT");
										                      tercetos.add((Terceto)suma1);
										                      ((Terceto)suma1).setPosition(tercetos.size());
															  
										                      Terceto ref= new TercetoReferencia(suma1,lexAn.getSymbolTable().getToken(ide.getLexema()));
										                      tercetos.add((Terceto)ref);
															  ref.setUse("mat");
															  ref.setTypeVariable(typeVariable);
										                      ((Terceto)ref).setPosition(tercetos.size());
										                      Terceto simple = new TercetoSimple(tercetos.size()+1);
										                      //tercetos.add((Terceto) simple);
										                      }
										
										

/**
 * crea los tercetos necesarios para la inicializacion de la matriz usando MakeMatrix para calcular la posicion de memoria de cada elemento de la matriz
 * 
 * al final se elimina un terceto simple usado para guardar la posicion del terceto que contiene la posicion de memoria del elemento de la matriz
 * @param listaValores arreglo generado en la lista de valores de matriz, la cantidad de elementos debera ser igual al producto de los limtes de la matriz
 * @param indexStart es el parametro annotation de la gramatica
 * @param ide token de la matrz
 * @param rowIndex cantidad de filas de la declaracion de la matriz
 * @param columnIndex cantidad de filas de la declaracion de la matriz
 */
public void initMatrix (ArrayList<Token> listaValores,Object indexStart, Token ide, Object rowIndex , Object columnIndex ){
	int index = (Integer) indexStart;
	System.out.println("=================initMatrix========");
	System.out.println("row index value:"+rowIndex);
	int i1 = (Integer) rowIndex;
	int i2 = (Integer) columnIndex;
	int rowi = (Integer) indexStart;
	int columnj = (Integer) indexStart;
	System.out.println("indices de la matriz limite filas:"+i1+" Limite de columnas:"+i2);
	System.out.println("inicio de la matriz filas:"+rowi+" inicio de las columnas:"+columnj);
	if (  (index == 0 && (i1+1*i2+1) == listaValores.size()) || (index == 1 && (i1*i2) == listaValores.size()) ) {
		System.out.println("=======inicio del primer for");
			int i;
			int shift = i2-index+1;
			//System.out.println("============== for por fila, fila numero:"+rowi);
			for (rowi = (Integer) index; rowi <= i1; rowi=rowi+1) {
				System.out.println("============== for por fila, fila numero:"+rowi);
			
				for (columnj = (Integer) index; columnj <= i2; columnj=columnj+1) {
					System.out.println("=====================for por columnas, columna numero:"+columnj);
					i = (rowi-index)*shift+(columnj-index);
					System.out.println("indice del arreglo del elemento a recuperar:"+i+" Elemento recuperado"+listaValores.get(i));
					makeMatrix(ide, rowi, columnj);
					System.out.println("columns en for decolumnas"+columnj);
					
					Terceto simpleAssign  = new TercetoSimple(tercetos.size()-1);
					simpleAssign.setTypeVariable("integer");
					//OLD: simpleAssign, NEW tercetos.get(tercetos.size()-2)aca cambiamos el segundo parametro del token asignacion para que cuando incializamos al matriz aparezca correctamente
					
					TercetoAsignacion assign = new TercetoAsignacion(tercetos.get(tercetos.size()-2),listaValores.get(i));
					assign.setTypeVariable("integer");
					System.out.println("aall muuundoooooooooo 0000000000000000000000 terceto asignacion creado"+assign);
					//tercetos.remove(tercetos.size()-1);
					//tercetos.add(simpleAssign);
					simpleAssign.setPosition(tercetos.size());
					tercetos.add(assign);
					assign.setPosition(tercetos.size());
					
				}
			}
			
	} else {System.out.println("Error en la cantidad de elementos declarados al inicializar la matriz");}
}



		//yylval = new ParserVal(token);

private int yylex() {
	System.out.println("ejecuto yylex principio del metodo");
	Token token = lexAn.getToken();
	
	if (token != null){
		String type = token.getType();
		yylval = new ParserVal(token);

		if (type.equals("IDENTIFICADOR"))
		{
			//yylval = new ParserVal(token.getLexema());
			return IDENTIFICADOR;
		}
		if (type.equals("CADENA"))
		{
			//yylval = new ParserVal (token.getLineNumber());
			printLine = token.getLineNumber();
			//yylval = new ParserVal(token.getLexema());
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
				yylval = new ParserVal (token.getLineNumber());
				numberLine.push(yylval.ival);
				return IF;
			}
			if (token.getLexema().equals("else")){
				return ELSE;
			}
			if (token.getLexema().equals("print")){
				yylval = new ParserVal (token.getLineNumber());
				printLine = yylval.ival;
				return PRINT;
			}
			if (token.getLexema().equals("for")){
				yylval = new ParserVal (token.getLineNumber());
				numberLine.push(yylval.ival);
				return FOR;
			}
			if (token.getLexema().equals("endif"))
			{
				return ENDIF;
			}
			if (token.getLexema().equals("integer"))
			{
				yylval = new ParserVal (token);
				return INTEGER;
			}
			if (token.getLexema().equals("float"))
			{
				return FLOAT;
			}
			if (token.getLexema().equals("matrix"))
			{
				yylval = new ParserVal (token.getLineNumber());
				printLine = yylval.ival;
				
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
			//yylval = new ParserVal(token.getLexema());
			return CTEINTEGER;
		}
		if (type.equals("FLOAT"))
		{
			//yylval = new ParserVal(token.getLexema());
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
	numberLine  = new Stack<Integer>();
	convertionMatrix = new AssignMatrix();
	operationMatrix = new OperationMatrix();
	divisionMatrix = new DivisionMatrix();
	generator = new AuxGenerator();

}
	