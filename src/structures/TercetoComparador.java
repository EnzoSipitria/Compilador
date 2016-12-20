package structures;

public class TercetoComparador extends Terceto{

	public TercetoComparador(Object comparator, Object first, Object second){
		this.operator = ((Element) comparator).getLexema();
		this.first    = first;
		this.second   = second;
		this.position = 0;
		this.classType = "Terceto";
	}

	@Override
	public String getLexema() {
		// TODO Auto-generated method stub
		return "["+String.valueOf(this.position)+"]";
	}

	@Override
	public String getAssembler() {
		System.out.println("=== TERCETO comparador getAssembler() ===");
		Element operando1 = (Element) this.first; 
		Element operando2 = (Element) this.second;
		System.out.println("terceto "+operando1+" := "+operando2+" variable auxiliar must be null:"+typeVariable);
		String codigo = "; comparacion "+operando1+" "+this.getOperator()+" "+operando2+"\n";
		//		if (this.typeVariable == null) {this.typeVariable="integer"; System.out.println("tipo cambiado");}



		if (this.typeVariable.equals("integer")){
			if ( operando1.getOperator().equals(">^") && (operando2.getOperator().equals(">^")) ){
				codigo += ";comparacion entre elementos enteros de matrices\n";
				codigo += "MOV EAX, "+operando1.getOperando()+"\n";
				codigo += "MOV BX, [EAX]\n";
				codigo += "MOV CX, BX\n";
				codigo += "MOV EAX, "+operando2.getOperando()+"\n";
				codigo += "MOV BX, [EAX]\n";
				codigo += "CMP CX, BX\n";
				
			}else
			if (operando2.getOperator().equals(">^")){
				codigo += "MOV CX, "+operando1.getOperando()+"\n";
				codigo += "MOV EAX, "+operando2.getOperando()+"\n";
				codigo += "MOV BX, [EAX]\n";
				codigo += "CMP CX, BX\n";
				

			}else 
				if (operando1.getOperator().equals(">^")){
					codigo += "MOV EAX, "+operando1.getOperando()+"\n";
					codigo += "MOV BX, [EAX]\n";
					codigo += "MOV CX, BX\n";
					codigo += "CMP CX, "+operando2.getOperando()+"\n";
					
				}else{
					codigo = "MOV CX, "+operando1.getOperando()+"\n";
					codigo += "CMP CX ," + operando2.getOperando()+ "\n"; /// cambie esto al reves por q no me funcionaba una condicion del for ver si lo dejo 
				}
		}	 
		else{ // if this.typeVariable.equals("integer") => rama por float
			if ( operando1.getOperator().equals(">^") && (operando2.getOperator().equals(">^")) ){
				codigo += "MOV EDX, dword ptr ["+operando1.getOperando()+"]\n";
				codigo += "MOV EBX, [EDX]\n";
				codigo += "MOV _nourriturre, EBX\n";
				codigo += "FLD _nourriturre\n";
				codigo += "MOV EDX, dword ptr ["+operando2.getOperando()+"]\n";
				codigo += "MOV EBX, [EDX]\n";
				codigo += "MOV _nourriturre, EBX\n";
				codigo += "FLD _nourriturre\n";
				codigo += "FCOMPP \n";
				codigo += "FSTSW AX \n";
				codigo += "SAHF \n";
			}else
			if (operando2.getOperator().equals(">^")){
				// aca hay que ver si esta bien el orden de los operandos
				System.out.println("es AUX en comparador"+operando2.getOperando());
				codigo += "MOV EDX, dword ptr ["+operando2.getOperando()+"]\n";
				codigo += "MOV EBX, [EDX]\n";
				codigo += "MOV _nourriturre, EBX\n";
				codigo += "FLD _nourriturre\n";
				codigo += "FLD "+operando1.getOperando()+"\n";
				codigo += "FCOMPP \n";
				codigo += "FSTSW AX \n";
				codigo += "SAHF \n";
			}else
				if (operando1.getOperator().equals(">^")){
					System.out.println("es AUX en comparador"+operando1.getOperando());
					codigo += "MOV EDX, dword ptr ["+operando1.getOperando()+"]\n";
					codigo += "MOV EBX, [EDX]\n";
					codigo += "MOV _nourriturre, EBX\n";
					codigo += "FLD _nourriturre\n";
					codigo += "FLD "+operando2.getOperando()+"\n";
					codigo += "FCOMPP \n";
					codigo += "FSTSW AX \n";
					codigo += "SAHF \n";
				}
			else{
				codigo = "FLD "+operando1.getOperando()+"\n";
				codigo += "FLD "+operando2.getOperando()+"\n";
				codigo += "FCOMPP \n";
				codigo += "FSTSW AX \n";//paso los valores del copro al proc
				codigo += "SAHF \n";//cargo los valores
			}
		}

		return codigo;

	}
}


