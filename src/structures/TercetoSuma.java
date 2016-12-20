package structures;


public class TercetoSuma extends Terceto {

	public TercetoSuma(Object first, Object second){
		this.operator = "+";
		this.first    = first;
		this.second   = second;
		this.position = 0;
		this.classType = "Terceto";
	}

	@Override
	public String toString(){
		//System.out.println("TO STRING TERCETO SUMA");
		return "("+operator+","+((Element) first).getLexema()+" .,. "+((Element) second).getLexema()+")";
	};

	@Override
	public String getLexema() {
		// TODO Auto-generated method stub
		return "["+String.valueOf(this.position)+"]";
	}

	@Override
	public String getAssembler() {
		System.out.println("===TERCETO SUMA getAssembler() ===");

		Element operando1 = (Element) this.first;
		Element operando2 = (Element) this.second;

		System.out.println("terceto "+operando1+" + "+operando2+" variable auxiliar"+this.getAux());
		String codigo = "; suma "+operando1+" + "+operando2+"\n";

		if (this.typeVariable.equals("integer")){
			if ( operando1.getOperator().equals(">^") && (operando2.getOperator().equals(">^")) ){

//				codigo += "---------------------------------------sentencias assembler para los dos operando de matrices";
				codigo += "MOV EAX, "+operando1.getOperando()+"\n";
				codigo += "MOV BX, [EAX]\n";
				codigo += "MOV EAX, "+operando2.getOperando()+"\n";
				codigo += "ADD BX, [EAX]\n";
				codigo += "MOV "+getAux()+", BX\n";
			}
			else
				if (operando1.getOperator().equals(">^")) {
//					codigo += "sentencias assembler para elementos de matriz cuando el primer operando es una matriz";
					codigo += "MOV EAX, "+operando1.getOperando()+"\n";
					codigo += "MOV BX, [EAX]\n";
					codigo += "ADD BX, "+operando2.getOperando()+"\n";
					codigo += "MOV "+getAux()+", BX\n";
				}
				else 
					if  (operando2.getOperator().equals(">^")) {
//						codigo += "------------------------sentencias assembler para elementos de matriz cuando el segundo operando es una matriz";
						codigo += "MOV EAX, "+operando2.getOperando()+"\n";
						codigo += "MOV BX, [EAX]\n";
						codigo += "ADD BX, "+operando1.getOperando()+"\n";
						codigo += "MOV "+getAux()+", BX\n";
					} 
					else{

						codigo +="MOV " + "BX"+ ", " + operando1.getOperando()+"\n";       
						codigo +="ADD " + "BX" + ", " + operando2.getOperando()+"\n";
						codigo+= "MOV " + this.getAux() + ", BX" +"\n";
					}
		} 
		else // if this.typeVariable.equals("integer") => rama por float 
			if ( operando1.getOperator().equals(">^") && (operando2.getOperator().equals(">^")) ){

				codigo += "MOV EDX, dword ptr ["+operando1.getOperando()+"]\n";
				codigo += "MOV EBX, [EDX]\n";
				codigo += "MOV _nourriturre, EBX\n";
				codigo += "FLD _nourriturre\n";
				codigo += "MOV EDX, dword ptr ["+operando2.getOperando()+"]\n";
				codigo += "MOV EBX, [EDX]\n";
				codigo += "MOV _nourriturre, EBX\n";
				codigo += "FLD _nourriturre\n";
				codigo += "FADD\n";
				codigo += "FSTP "+getAux()+"\n";

			}
			else if (operando1.getOperator().equals(">^")) {
				codigo += "MOV EDX, dword ptr ["+operando1.getOperando()+"]\n";
				codigo += "MOV EBX, [EDX]\n";
				codigo += "MOV _nourriturre, EBX\n";
				codigo += "FLD _nourriturre\n";
				codigo += "FADD "+operando2.getOperando()+"\n";
				codigo += "FSTP "+getAux()+"\n";
			}
			else if  (operando2.getOperator().equals(">^")) {
				codigo += "MOV EDX, dword ptr ["+operando2.getOperando()+"]\n";
				codigo += "MOV EBX, [EDX]\n";
				codigo += "MOV _nourriturre, EBX\n";
				codigo += "FLD _nourriturre\n";
				codigo += "FADD "+operando1.getOperando()+"\n";
				codigo += "FSTP "+getAux()+"\n";
			} 
			else { 
				codigo += "FLD " + operando1.getOperando() + "\n";
				codigo += "FADD " + operando2.getOperando() + "\n";
				codigo += "FST " + this.getAux() + "\n";//guardo copia 	
			}

		return codigo;
	}

}

