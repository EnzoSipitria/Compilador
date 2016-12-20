package structures;

public class TercetoMultiplicacion extends Terceto {

	public TercetoMultiplicacion(Object first, Object second){
		this.operator = "*";
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
		System.out.println("===TERCETO MULTIPLICACION getAssembler() ===");

		Element operando1 = (Element) this.first;
		Element operando2 = (Element) this.second;

		System.out.println("terceto "+operando1+" * "+operando2+" variable auxiliar"+this.getAux());
		String codigo = ";multiplicacion "+operando1+" * "+operando2+"\n";

		if (this.typeVariable.equals("integer")){
			if ( operando1.getOperator().equals(">^") && (operando2.getOperator().equals(">^")) ){
				codigo += ";sentencias assembler para los dos operando de matrices\n";
				codigo += "MOV EBX, "+operando1.getOperando()+"\n";
				codigo += "MOV AX, [EBX]\n";
				codigo += "MOV EBX, "+operando2.getOperando()+"\n";
				codigo += "MOV DX, 0\n";
				codigo += "IMUL AX, [EBX]\n";
				codigo += "MOV "+getAux()+", AX" +"\n";

			}
			else
				if (operando1.getOperator().equals(">^")) {
					codigo += ";elementos de matriz cuando el primer operando es una matriz\n";
					codigo += "MOV EBX, "+operando1.getOperando()+"\n";
					codigo += "MOV AX, [EBX]\n";
					codigo += "MOV DX, 0\n";
					codigo += "IMUL AX, "+operando2.getOperando()+"\n";
					codigo += "MOV "+getAux()+", AX \n";
				}
				else 
					if  (operando2.getOperator().equals(">^")) {
						codigo += ";elementos de matriz cuando el segundo operando es una matriz\n";
						codigo += "MOV EBX, "+operando2.getOperando()+"\n";
						codigo += "MOV AX, [EBX]\n";
						codigo += "MOV DX, 0\n";
						codigo += "IMUL AX, "+operando1.getOperando()+"\n";
						codigo += "MOV "+getAux()+", AX \n";
					} 
					else{
						codigo += "MOV AX, " + operando1.getOperando() + "\n";
						codigo += "MOV DX, 0\n";   
						codigo += "IMUL AX, " + operando2.getOperando() + "\n";
						codigo += "MOV "+getAux()+", AX \n";
					} 
		}
		else if ( operando1.getOperator().equals(">^") && (operando2.getOperator().equals(">^")) ){

			codigo += "MOV EDX, dword ptr ["+operando1.getOperando()+"]\n";
			codigo += "MOV EBX, [EDX]\n";
			codigo += "MOV _nourriturre, EBX\n";
			codigo += "FLD _nourriturre\n";
			codigo += "MOV EDX, dword ptr ["+operando2.getOperando()+"]\n";
			codigo += "MOV EBX, [EDX]\n";
			codigo += "MOV _nourriturre, EBX\n";
			codigo += "FLD _nourriturre\n";
			codigo += "FMUL\n";
			codigo += "FSTP "+getAux()+"\n";

		}
		else 
			if (operando1.getOperator().equals(">^")) {
				codigo += "MOV EDX, dword ptr ["+operando1.getOperando()+"]\n";
				codigo += "MOV EBX, [EDX]\n";
				codigo += "MOV _nourriturre, EBX\n";
				codigo += "FLD _nourriturre\n";
				codigo += "FMUL "+operando2.getOperando()+"\n";
				codigo += "FSTP "+getAux()+"\n";
			}
			else 
				if  (operando2.getOperator().equals(">^")) {
					codigo += "MOV EDX, dword ptr ["+operando2.getOperando()+"]\n";
					codigo += "MOV EBX, [EDX]\n";
					codigo += "MOV _nourriturre, EBX\n";
					codigo += "FLD _nourriturre\n";
					codigo += "FMUL "+operando1.getOperando()+"\n";
					codigo += "FSTP "+getAux()+"\n";
				} 
				else { 
					codigo += "FLD " + operando1.getOperando() + "\n";
					codigo += "FMUL " + operando2.getOperando() + "\n";
					codigo += "FSTP " + getAux() + "\n";//guardo copia 	
				}

		return codigo;
	}



}

