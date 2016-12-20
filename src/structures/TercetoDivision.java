package structures;

public class TercetoDivision extends Terceto {

	public TercetoDivision(Object first, Object second){
		this.operator = "/";
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
		System.out.println("===TERCETO DIVISION getAssembler() ===");

		Element dividendo = (Element) this.first;
		Element divisor = (Element) this.second;

		System.out.println("terceto "+dividendo+" + "+divisor+" variable auxiliar"+this.getAux());
		String codigo = ";division "+dividendo+" / "+divisor+"\n";

		if (this.typeVariable.equals("integer")){
			if ( dividendo.getOperator().equals(">^") && (divisor.getOperator().equals(">^")) ){
//					codigo += "---------------------------------------sentencias assembler para los dos operando de matrices";				
					codigo += "XOR EBX, EBX \n"; //set eax to 0 
					codigo += "MOV EAX, "+divisor.getOperando()+"\n";
					codigo += "ADD BX, [EAX]\n";
					codigo += "JZ _division_cero\n";
					codigo += "MOV DX, 0\n";//Cargo la parte alta con cero, porque todos
					codigo += "CWD\n";//copia el signo a DX
					codigo += "MOV ECX, "+dividendo.getOperando()+"\n";
					codigo += "MOV EAX, [ECX]\n"; 
					codigo += "MOV ECX, "+divisor.getOperando()+"\n";
					codigo += "MOV EBX, [ECX]\n";
					codigo += "IDIV EBX\n";
	                codigo += "MOV " + getAux() + ", AX\n";
			}
			else
				if (dividendo.getOperator().equals(">^")) {
//					codigo += "sentencias assembler para elementos de matriz cuando el primer operando es una matriz";
					codigo += "XOR EBX, EBX \n"; //set eax to 0 
					codigo += "ADD BX, "+divisor.getOperando()+"\n";
					codigo += "JZ _division_cero\n";
					codigo += "MOV DX, 0\n";//Cargo la parte alta con cero, porque todos
					codigo += "CWD\n";//copia el signo a DX
					codigo += "MOV ECX, "+dividendo.getOperando()+"\n";
					codigo += "MOV EAX, [ECX]\n"; 
					codigo += "MOV EBX, "+divisor.getOperando()+"\n";
					codigo += "IDIV EBX\n";
	                codigo += "MOV " + getAux() + ", AX\n";
				}
				else 
					if  (divisor.getOperator().equals(">^")) {
//						codigo += "------------------------sentencias assembler para elementos de matriz cuando el segundo operando es una matriz";
						codigo += "XOR EBX, EBX \n"; //set eax to 0 
						codigo += "MOV EAX, "+divisor.getOperando()+"\n";
						codigo += "ADD BX, [EAX]\n";
						codigo += "JZ _division_cero\n";
						codigo += "MOV DX, 0\n";//Cargo la parte alta con cero, porque todos
						codigo += "MOV AX, "+dividendo.getOperando()+"\n";
						codigo += "CWD\n";//copia el signo a DX
						codigo += "MOV ECX, "+divisor.getOperando()+"\n";
						codigo += "MOV EBX, [ECX]\n";
						codigo += "IDIV EBX\n";
		                codigo += "MOV " + getAux() + ", AX\n";
					} 
					else{
                        codigo += "XOR EBX, EBX \n"; //set eax to 0 
                        codigo += "ADD BX, "+divisor.getOperando()+"\n";
                        codigo += "JZ _division_cero\n";
                        codigo += "MOV DX, 0\n";//Cargo la parte alta con cero, porque todos
                        codigo += "MOV AX, "+dividendo.getOperando()+"\n";//los enteros son de 16 bits no más
                        codigo += "CWD\n";//copia el signo a DX
                        codigo += "MOV BX, "+divisor.getOperando()+"\n";
    					codigo += "IDIV EBX\n";
                        codigo += "MOV " + getAux() + ", AX\n";


					}
		} 
		else // if this.typeVariable.equals("integer") => rama por float 
			if ( dividendo.getOperator().equals(">^") && (divisor.getOperator().equals(">^")) ){

				codigo += "MOV EDX, dword ptr ["+divisor.getOperando()+"]\n";
				codigo += "MOV EBX, [EDX]\n";
				codigo += "MOV _nourriturre, EBX\n";
				codigo += "FLD _nourriturre\n";
				codigo += "FTST\n";
                codigo += "XOR BX, BX\n"; //set eax to 0 
                codigo += "FSTSW AX \n";//paso los valores del copro al proc
                codigo += "SAHF \n";//cargo los valores
                codigo += "JE _division_cero\n";
				codigo += "MOV EDX, dword ptr ["+dividendo.getOperando()+"]\n";
				codigo += "MOV EBX, [EDX]\n";
				codigo += "MOV _nourriturre, EBX\n";
				codigo += "FLD _nourriturre\n";
				codigo += "FDIVR \n"; // o "FDIV ST, ST(1)\n";
	            codigo += "FSTP " + getAux() + "\n";

			}
			else if (dividendo.getOperator().equals(">^")) {
				codigo += "FLD " + divisor.getOperando() + "\n";
                codigo += "FTST\n";
                codigo += "XOR BX, BX\n"; //set eax to 0 
                codigo += "FSTSW AX \n";//paso los valores del copro al proc
                codigo += "SAHF \n";//cargo los valores
                codigo += "JE _division_cero\n";
                codigo += "MOV EDX, dword ptr ["+dividendo.getOperando()+"]\n";
				codigo += "MOV EBX, [EDX]\n";
				codigo += "MOV _nourriturre, EBX\n";
				codigo += "FLD _nourriturre\n";
				codigo += "FDIVR \n"; // o "FDIV ST, ST(1)\n";
	            codigo += "FSTP " + getAux() + "\n";
			}
			else if  (divisor.getOperator().equals(">^")) {
				codigo += "MOV EDX, dword ptr ["+divisor.getOperando()+"]\n";
				codigo += "MOV EBX, [EDX]\n";
				codigo += "MOV _nourriturre, EBX\n";
				codigo += "FLD _nourriturre\n";
				codigo += "FTST\n";
                codigo += "XOR BX, BX\n"; //set eax to 0 
                codigo += "FSTSW AX \n";//paso los valores del copro al proc
                codigo += "SAHF \n";//cargo los valores
                codigo += "JE _division_cero\n";
                
                codigo += "FLD " + dividendo.getOperando() + "\n";
                codigo += "FDIVR \n"; // o "FDIV ST, ST(1)\n";
	            codigo += "FSTP " + getAux() + "\n";
				
			} 
			else { 
                codigo += "FLD " + divisor.getOperando() + "\n";
                codigo += "FTST\n";
                codigo += "XOR BX, BX\n"; //set eax to 0 
                codigo += "FSTSW AX \n";//paso los valores del copro al proc
                codigo += "SAHF \n";//cargo los valores
                codigo += "JE _division_cero\n";
                codigo += "FLD " + dividendo.getOperando() + "\n";
                codigo += "FDIVR \n"; // o "FDIV ST, ST(1)\n";
                codigo += "FSTP " + getAux() + "\n";

			}

		return codigo;
		//no llores por un bou
	}


}
