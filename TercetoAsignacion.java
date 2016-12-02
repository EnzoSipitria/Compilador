
package structures;

public class TercetoAsignacion extends Terceto{

	public TercetoAsignacion(Object first, Object second){
		this.operator = ":=";
		this.first    = first;
		this.second   = second;
		this.position = 0;
		this.classType = "Terceto";
		System.out.println("terceto asignacion created");
		System.out.println("first"+this.first);
		System.out.println("second"+this.second);
	}



	@Override
	public void setPosition(int position) {
		System.out.println("position seteada en tereceto"+position);
		this.position = position;
	}



	@Override
	public void setUse(String use) {
		// TODO Auto-generated method stub
		super.setUse(use);
	}



	@Override
	public String getLexema() {
		// TODO Auto-generated method stub

		return "["+String.valueOf(this.position)+"]";
	}



	@Override
	public void setTypeVariable(String typeVariable) {
		// TODO Auto-generated method stub
		System.out.println("type variable seteado"+typeVariable);
		this.typeVariable=typeVariable;
	}

	@Override
	public String toString() {
		return  "("+operator+","+((Element) first)+","+((Element)second)+")";
	}

	@Override
	public String getAssembler() {
		System.out.println("=== TERCETO ASIGNACION getAssembler() ===");
		Element variable = (Element) this.first; 
		Element expresion = (Element) this.second;
		System.out.println("terceto "+variable+" := "+expresion+" variable auxiliar must be null:"+typeVariable);
		String codigo = "; asignacion\n";
		//		if (this.typeVariable == null) {this.typeVariable="integer"; System.out.println("tipo cambiado");}



		if (this.typeVariable.equals("integer")){
			if ( expresion.getOperator().equals(">^") && (variable.getOperator().equals(">^")) ){

				codigo += "; asignacion entera entre matrices\n";
				codigo += "MOV EAX, "+expresion.getOperando()+"\n";
				codigo += "MOV DX, [EAX]\n";
				codigo += "PrintDec EAX, \"direccion de la"+expresion.getOperando()+" expresion matriz\"\n";
				codigo += "PrintDec DX\n";
				codigo += "MOV EAX, "+variable.getOperando()+"\n";
				codigo += "MOV [EAX], DX\n";
				codigo += "MOV DX, [EAX]\n";
				codigo += "MOV _nourriturre, EDX\n";
				codigo += "PrintDec _nourriturre\n";
				codigo += "PrintDec "+expresion.getOperando()+", \"direccion de la"+expresion.getOperando()+" expresion matriz\"\n";
				codigo += "PrintDec "+variable.getOperando()+", \"direccion de la"+variable.getOperando()+" expresion matriz\"\n";
			}else
			if (expresion.getOperator().equals(">^")){

				codigo += "MOV EAX, "+expresion.getOperando()+"\n";
//				codigo += "MOV BX, [EAX]\n";
				codigo += "MOV DX, [EAX]\n";
//				codigo += "MOV EBX, " +  "dword ptr ["+expresion.getOperando()+"]"+"\n"; //o aca habria que cargarlo a BX por ser enteros de 2 bytes
				codigo += "MOV "+variable.getOperando()+", DX\n";//aca puede ser que sea EBX en lugar de BX
			}else 
				if (variable.getOperator().equals(">^")){
//					codigo += "sentencias assembler para elementos de matriz ";
					codigo += "MOV BX, " + expresion.getOperando() + "\n";
					codigo += "MOV EAX, "+variable.getOperando()+"\n";
					codigo += "MOV [EAX], BX\n";
					
				}else{
					codigo += "MOV BX, " + expresion.getOperando() + "\n";
					codigo += "MOV " + variable.getOperando() + ", BX\n";
				}
		}	 
		else{ // if this.typeVariable.equals("integer") => rama por float
			if ( expresion.getOperator().equals(">^") && (variable.getOperator().equals(">^")) ){

				codigo += "MOV EDX, dword ptr ["+expresion.getOperando()+"]\n";
				codigo += "MOV EBX, [EDX]\n";
				codigo += "MOV _nourriturre, EBX\n";
				codigo += "FLD _nourriturre\n";
				codigo += "FSTP dword ptr ["+variable.getOperando()+"]\n";
				codigo += "MOV EDX, dword ptr ["+variable.getOperando()+"]\n";
				codigo += "MOV [EBX], EDX\n";// revisar esta parte de la asignacion

			}else
				if (expresion.getOperator().equals(">^")){
				System.out.println("sentencias float para matrices") ;
				codigo += "MOV EDX, dword ptr ["+expresion.getOperando()+"]\n";
				codigo += "MOV EBX, [EDX]\n";
				codigo += "MOV _nourriturre, EBX\n";
				codigo += "FLD _nourriturre\n";
				codigo += "FSTP "+variable.getOperando()+"\n";
			}else
				if (variable.getOperator().equals(">^")){
					codigo += "FLD "+expresion.getOperando()+"\n";
					codigo += "FSTP dword ptr ["+variable.getOperando()+"]\n";
					codigo += "MOV EDX, dword ptr ["+variable.getOperando()+"]\n";
					codigo += "MOV [EBX], EDX\n";
					
				}
			else{
				codigo += "FLD " + expresion.getOperando() + "\n";
				codigo += "FSTP " + variable.getOperando() + "\n";
			}
		}

		return codigo+"PrintDec "+variable.getOperando()+", \""+variable.getOperando()+"\" \n";
	}



}

/**
 * 
 * TODO: Acomodar IF (terceto) para que quede mas comprensible, yno se repita tanto codigo
 * 
 * posible problema: cuando vengan 2 elementos que sean 1 terceto y 1 token...
 * 
 */
/*
	@Override
	public String getAssembler(){

		System.out.println("===terceto asignacion===");
		System.out.println("====terceto"+this);
		String Codigo= "";
		Element op1 = (Element) this.first;
		Element op2 = (Element) this.second;
        this.typeVariable=op1.getTypeVariable();
		System.out.println("TERCETOASIGNACION : getAssembler ( primer elemento "+op1+" segundo elemento "+op2);
		System.out.println("terceto "+this.typeVariable);
		if ((op1.getClassType().equals("Terceto")) && (op2.getClassType().equals("Terceto"))){
			boolean arreglo1 = (((Element)op1).getOperator().equals(">^"));
			boolean arreglo2 = (((Element)op2).getOperator().equals(">^"));
			System.out.println("Arreglo 1 en asignacion: "+arreglo1);
			System.out.println("Arreglo 2 en asignacion: "+arreglo2);
			if ((arreglo1) && (arreglo2)){ //CONTROLO SI LOS TERCETOS SON ARREGLOS
				System.out.println("realizo asignacion entre 2 elemntos de matriz");
				String ope1 = ((Element) this.first).getOperando();
				String ope2 = ((Element) this.second).getOperando();
				System.out.println("=>operando first assembler"+op1);
				System.out.println("=>operando second assembler"+second);
				Codigo ="";
				boolean op2EsVar = (ope2.charAt(0) == '_') || (ope2.charAt(0) == '@');
				//				System.out.println("==========================================getAssembler  terceto"+this);
				if (((Element) this.first).getTypeVariable().equals("integer")) {
					if (op2EsVar) {
						Codigo += "MOV EBX, " +  "dword ptr ["+((Terceto) op2).getAux()+"]"+"\n"; 
						Codigo += "MOV " + "dword ptr ["+((Terceto) op1).getAux()+"]" + ", BX\n";
					} else {
						Codigo = "MOV " + "dword ptr ["+((Terceto) op1).getAux()+"]" + ", " + "dword ptr ["+((Terceto) op2).getAux()+"]" + "\n";
					}
				}
				else {
					Codigo += "FLD " + "dword ptr ["+((Terceto) op2).getAux()+"]" + "\n";
					Codigo += "FSTP " + "dword ptr ["+((Terceto) op1).getAux()+"]" + "\n";
				}

			}else  if (arreglo1){
				System.out.println("realizo asignacion entre 1 elemnto de matriz y otra cosa");
				// boolean Arreglo1 = (((Terceto)op1).getOperacion().equals(">^"));
				//  boolean Arreglo2 = (((Terceto)op2).getOperacion().equals(">^"));
				//if ((Arreglo1) && (Arreglo2)){ //CONTROLO SI LOS TERCETOS SON ARREGLOS
				String ope1 = ((Element) this.first).getOperando();
				String ope2 = ((Element) this.second).getOperando();
				System.out.println("=>operando first assembler"+op1);
				System.out.println("=>operando second assembler"+second);
				Codigo ="";
				boolean op2EsVar = (ope2.charAt(0) == '_') || (ope2.charAt(0) == '@');
				//					System.out.println("==========================================getAssembler  terceto"+this);
				if (((Element) this.first).getTypeVariable().equals("integer")) {
					if (op2EsVar) {
						Codigo += "MOV EBX, " +  ope2 +"\n"; 
						Codigo += "MOV " + "dword ptr ["+((Terceto) op1).getAux()+"]" + ", EBX\n";
					} else {
						Codigo = "MOV " + "dword ptr ["+((Terceto) op1).getAux()+"]" + ", " + ope2 + "\n";
					}
				}
				else {
					Codigo += "FLD " + ope2 + "\n";
					Codigo += "FSTP " + "dword ptr ["+((Terceto) op1).getAux()+"]" + "\n";
				}


			}   
			else if (arreglo2)  { 
				System.out.println("realizo asignacion entreotra cosa y 1  elemnto de matriz");
				String ope1 = ((Element) this.first).getOperando();
				String ope2 = ((Element) this.second).getOperando();
				System.out.println("=>operando first assembler"+op1);
				System.out.println("=>operando second assembler"+second);
				Codigo ="";
				boolean op2EsVar = (ope2.charAt(0) == '_') || (ope2.charAt(0) == '@');
				if (((Element) this.first).getTypeVariable().equals("integer")) {
					if (op2EsVar) {
						Codigo += "MOV EBX, " +  "dword ptr ["+((Terceto) op2).getAux()+"]"+"\n"; 
						Codigo += "MOV " + ope1 + ", EBX\n";
					} else {
						Codigo = "MOV " + ope1 + ", " + "dword ptr ["+((Terceto) op2).getAux()+"]" + "\n";
					}
				}
				else {
					Codigo += "FLD " + "dword ptr ["+((Terceto) op2).getAux()+"]" + "\n";
					Codigo += "FSTP " + ope1 + "\n";
				}

			} else 	if (op1.getClassType().equals("Terceto")){
						String ope1 = ((Element) first).getOperando();
						String ope2 = ((Element) second).getOperando();
						if (this.typeVariable.equals("integer")){
							Codigo += "MOV BX, " + ope2 + "\n";
							//OPE1 DEBERIA SER GETAUX???
							Codigo += "MOV " + ope1 + ", BX\n";
							//revisar si va ope2EsVar boolean op2EsVar = (ope2.charAt(0) == '_') || (ope2.charAt(0) == '@');
						} else {
							Codigo += "FLD " + ope2 + "\n";
							Codigo += "FSTP " + ope1 + "\n";
						}// es float

					}
		} else 
			if (op1.getClassType().equals("Terceto")){//miro que sea matriz
				System.out.println("HOLA SOY LA MATRIZ QUE ACABO DE TOCAR EN ARREGLO 1");
				boolean arreglo1 = (((Element)op1).getOperator().equals(">^"));
				if (arreglo1){
					System.out.println("realizo asignacion entre 1 elemnto de matriz y otra cosa");
					// boolean Arreglo1 = (((Terceto)op1).getOperacion().equals(">^"));
					//  boolean Arreglo2 = (((Terceto)op2).getOperacion().equals(">^"));
					//if ((Arreglo1) && (Arreglo2)){ //CONTROLO SI LOS TERCETOS SON ARREGLOS
					String ope1 = ((Element) this.first).getOperando();
					String ope2 = ((Element) this.second).getOperando();
					System.out.println("=>operando first assembler"+op1);
					System.out.println("=>operando second assembler"+second);
					Codigo ="";
					boolean op2EsVar = (ope2.charAt(0) == '_') || (ope2.charAt(0) == '@');
					//					System.out.println("==========================================getAssembler  terceto"+this);
					if (((Element) this.first).getTypeVariable().equals("integer")) {
						if (op2EsVar) {
							Codigo += "MOV EBX, " +  ope2 +"\n"; 
							Codigo += "MOV " + "dword ptr ["+((Terceto) op1).getAux()+"]" + ", EBX\n";
						} else {
							Codigo = "MOV " + "dword ptr ["+((Terceto) op1).getAux()+"]" + ", " + ope2 + "\n";
						}
					}
					else {
						Codigo += "FLD " + ope2 + "\n";
						Codigo += "FSTP " + "dword ptr ["+((Terceto) op1).getAux()+"]" + "\n";
					}


				}   



			}else if(op2.getClassType().equals("Terceto")){
				System.out.println("HOLA SOY LA MATRIZ QUE ACABO DE TOCAR EN ARREGLO 2");
				boolean arreglo2 = (((Element)op2).getOperator().equals(">^"));
				if (arreglo2)  { 
					System.out.println("realizo asignacion entreotra cosa y 1  elemnto de matriz");
					String ope1 = ((Element) this.first).getOperando();
					String ope2 = ((Element) this.second).getOperando();
					System.out.println("=>operando first assembler"+op1);
					System.out.println("=>operando second assembler"+second);
					Codigo ="";
					boolean op2EsVar = (ope2.charAt(0) == '_') || (ope2.charAt(0) == '@');
					if (((Element) this.first).getTypeVariable().equals("integer")) {
						if (op2EsVar) {
							Codigo += "MOV EBX, " +  "dword ptr ["+((Terceto) op2).getAux()+"]"+"\n"; 
							Codigo += "MOV " + ope1 + ", EBX\n";
						} else {
							Codigo = "MOV " + ope1 + ", " + "dword ptr ["+((Terceto) op2).getAux()+"]" + "\n";
						}
					}
					else {
						Codigo += "FLD " + "dword ptr ["+((Terceto) op2).getAux()+"]" + "\n";
						Codigo += "FSTP " + ope1 + "\n";
					}

				}
			}
//				System.out.println("OP1 TERCETO ASIGNACION");
//				String ope1 = ((Element) first).getOperando();
//				String ope2 = ((Element) second).getOperando();
//				if (this.typeVariable.equals("integer")){
//					Codigo += "MOV BX, " + ope2 + "\n";
//					//OPE1 DEBERIA SER GETAUX???
//					Codigo += "MOV " + ope1 + ", BX\n";
//					//revisar si va ope2EsVar boolean op2EsVar = (ope2.charAt(0) == '_') || (ope2.charAt(0) == '@');
//				} else {
//					Codigo += "FLD " + ope2 + "\n";
//					Codigo += "FSTP " + ope1 + "\n";
//				}// es float
//
//			} else
//			{
//				System.out.println("OP2 TERCETO ASIGNACION");
//				String ope1 = ((Element) first).getOperando();
//				String ope2 = ((Element) second).getOperando();
//				if (this.typeVariable.equals("integer")){
//					Codigo += "MOV BX, " + ope2 + "\n";
//					//OPE1 DEBERIA SER GETAUX???
//					Codigo += "MOV " + ope1 + ", BX\n";
//					//revisar si va ope2EsVar boolean op2EsVar = (ope2.charAt(0) == '_') || (ope2.charAt(0) == '@');
//				} else {
//					Codigo += "FLD " + ope2 + "\n";
//					Codigo += "FSTP " + ope1 + "\n";
//				}





//		{
//			System.out.println("realizo asignacion entre 2 DISTINTOS de elemntos de matriz");
//			String ope1 = ((Element) first).getOperando();
//			String ope2 = ((Element) second).getOperando();
//			Codigo = "";
//			boolean op2EsVar = (ope2.charAt(0) == '_') || (ope2.charAt(0) == '@');
//			if (this.typeVariable.equals("integer")) {
//				if (op2EsVar) {
//					Codigo += "MOV BX, " + ope2 + "\n";
//					//OPE1 DEBERIA SER GETAUX???
//					Codigo += "MOV " + ope1 + ", BX\n";
//				} else {
//					Codigo = "MOV " + ope1 + ", " + ope2 + "\n";
//				}
//			} else {
//				Codigo += "FLD " + ope2 + "\n";
//				Codigo += "FSTP " + ope1 + "\n";
//			}
//		}
		return Codigo;
	}
}
 */