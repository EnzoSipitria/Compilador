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
		String codigo = ";division\n";

		if (this.typeVariable.equals("integer")){
			if ( dividendo.getOperator().equals(">^") && (divisor.getOperator().equals(">^")) ){
//					codigo += "---------------------------------------sentencias assembler para los dos operando de matrices";				
					codigo += "XOR BX, BX \n"; //set eax to 0 
					codigo += "MOV EAX, "+divisor.getOperando()+"\n";
					codigo += "ADD EBX, [EAX]\n";
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
					codigo += "XOR BX, BX \n"; //set eax to 0 
					codigo += "ADD EBX, "+divisor.getOperando()+"\n";
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
						codigo += "XOR BX, BX \n"; //set eax to 0 
						codigo += "MOV EAX, "+divisor.getOperando()+"\n";
						codigo += "ADD EBX, [EAX]\n";
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
                        codigo += "XOR BX, BX \n"; //set eax to 0 
                        codigo += "ADD BX, "+divisor.getOperando()+"\n";
                        codigo += "JZ _division_cero\n";
                        codigo += "MOV DX, 0\n";//Cargo la parte alta con cero, porque todos
                        codigo += "MOV AX, "+dividendo.getOperando()+"\n";//los enteros son de 16 bits no más
                        codigo += "CWD\n";//copia el signo a DX
                        codigo += "MOV EBX, "+divisor.getOperando()+"\n";
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
//		 String Codigo= "";
//	        Element op1 = (Element) this.first;
//	        Element op2 = (Element) this.second;
////	        Token aux = new Token("Vacio","");
//	        if ((op1.getClassType().equals("Terceto")) && (op2.getClassType().equals("Terceto"))){
//	            boolean Arreglo1 = (((Terceto)op1).getOperando().equals(">^"));
//	            boolean Arreglo2 = (((Terceto)op2).getOperando().equals(">^"));
//	            if ((Arreglo1) && (Arreglo2)){ 
//	            //CONTROLO SI LOS TERCETOS SON ARREGLOS
//	                 if( this.typeVariable.equals("integer")){
//	                Codigo += "XOR BX, BX \n"; //set eax to 0 
//	                Codigo += "ADD EBX, " + "dword ptr ["+((Terceto) op2).getAux()+"]"+"\n";
//	                Codigo += "JZ _division_cero\n";
//	                Codigo += "MOV DX, 0\n";//Cargo la parte alta con cero, porque todos
//	                Codigo += "MOV EAX, " + "dword ptr ["+((Terceto) op1).getAux()+"]"+"\n";		;//los enteros son de 16 bits no más
//	                Codigo += "CWD\n";//copia el signo a DX
//	                Codigo += "IDIV " + "dword ptr ["+((Terceto) op2).getAux()+"]"+ "\n";
//	                Codigo += "MOV " + getAux() + ", AX\n";}
//	                else {
//	                    Codigo += "FLD " + "dword ptr ["+((Terceto) op2).getAux()+"]" + "\n";
//	                    Codigo += "FTST\n";
//	                    Codigo += "XOR BX, BX\n"; //set eax to 0 
//	                    Codigo += "FSTSW AX \n";//paso los valores del copro al proc
//	                    Codigo += "SAHF \n";//cargo los valores
//	                    Codigo += "JE _division_cero\n";
//	                    Codigo += "FLD " + "dword ptr ["+((Terceto) op1).getAux()+"]" + "\n";
//	                    Codigo += "FDIVR \n"; // o "FDIV ST, ST(1)\n";
//	                    Codigo += "FSTP " + getAux() + "\n";
//	                }
//	            }    
//	           else  if (Arreglo1){
//	                 if( this.typeVariable.equals("integer")){
//	           	    Codigo += "XOR BX, BX \n"; //set eax to 0 
//	                Codigo += "ADD BX, " + op2 + "\n";
//	                Codigo += "JZ _division_cero\n";
//	                Codigo += "MOV DX, 0\n";//Cargo la parte alta con cero, porque todos
//	                Codigo += "MOV AX, " + "dword ptr ["+((Terceto) op1).getAux()+"]"+"\n";//los enteros son de 16 bits no más
//	                Codigo += "CWD\n";//copia el signo a DX
//	                Codigo += "IDIV " + op2.getOperando() + "\n";
//	                Codigo += "MOV " + getAux() + ", AX\n";}
//	                else {
//	                    Codigo += "FLD " + op2.getOperando() + "\n";
//	                    Codigo += "FTST\n";
//	                    Codigo += "XOR BX, BX\n"; //set eax to 0 
//	                     Codigo += "FSTSW AX \n";//paso los valores del copro al proc
//	                    Codigo += "SAHF \n";//cargo los valores
//	                   Codigo += "JE _division_cero\n";
//	                    Codigo += "FLD " + "dword ptr ["+((Terceto) op1).getAux()+"]" + "\n";
//	                    Codigo += "FDIVR \n"; // o "FDIV ST, ST(1)\n";
//	                    Codigo += "FSTP " + getAux() + "\n";
//
//	                }
//	              
//	                 }   
//	                 else if (Arreglo2)  { 
//	                    if( this.typeVariable.equals("integer")){
//	                 	Codigo += "XOR BX, BX \n"; //set eax to 0 
//	                Codigo += "ADD BX, " +"dword ptr ["+((Terceto) op2).getAux()+"]"+"\n";
//	                Codigo += "JZ _division_cero\n";
//	                Codigo += "MOV DX, 0\n";//Cargo la parte alta con cero, porque todos
//	                Codigo += "MOV AX, " + op1.getOperando()+"\n";//los enteros son de 16 bits no más
//	                Codigo += "CWD\n";//copia el signo a DX
//	                Codigo += "IDIV " +  "dword ptr ["+((Terceto) op2).getAux()+"]"+"\n";
//	                Codigo += "MOV " + getAux() + ", AX\n";}
//
//	                else {
//	                 Codigo += "FLD " + "dword ptr ["+((Terceto) op2).getAux()+"]" + "\n";
//	                    Codigo += "FTST\n";
//	                    Codigo += "XOR BX, BX\n"; //set eax to 0 
//	                     Codigo += "FSTSW AX \n";//paso los valores del copro al proc
//	                    Codigo += "SAHF \n";//cargo los valores
//	                   Codigo += "JE _division_cero\n";
//	                    Codigo += "FLD " + op1.getOperando() + "\n";
//	                    Codigo += "FDIVR \n"; // o "FDIV ST, ST(1)\n";
//	                    Codigo += "FSTP " + getAux() + "\n";
//
//	                }
//	                        } }
//	           // LR.LiberarRegistro(Op2);}
//	                else 
//	                  {
//	                     if( this.typeVariable.equals("integer")){
//
//	                        Codigo += "XOR BX, BX \n"; //set eax to 0 
//	                        Codigo += "ADD BX, " + op2.getOperando() + "\n";
//	                        Codigo += "JZ _division_cero\n";
//	                        Codigo += "MOV DX, 0\n";//Cargo la parte alta con cero, porque todos
//	                        Codigo += "MOV AX, " + op1.getOperando() + "\n";//los enteros son de 16 bits no más
//	                        Codigo += "CWD\n";//copia el signo a DX
//	                        Codigo += "IDIV " + op2.getOperando() + "\n";
//	                        Codigo += "MOV " + getAux() + ", AX\n";
//	                    }
//	                    else 
//	                    {
//	                    Codigo += "FLD " + op2.getOperando() + "\n";
//	                    Codigo += "FTST\n";
//	                    Codigo += "XOR BX, BX\n"; //set eax to 0 
//	                     Codigo += "FSTSW AX \n";//paso los valores del copro al proc
//	                    Codigo += "SAHF \n";//cargo los valores
//	                   Codigo += "JE _division_cero\n";
//	                    Codigo += "FLD " + op1.getOperando() + "\n";
//	                    Codigo += "FDIVR \n"; // o "FDIV ST, ST(1)\n";
//	                    Codigo += "FSTP " + getAux() + "\n"; 	
//	                    }
//	                  }
//	      //  String r = LR.getRegistro(Op1);
//	       // if (r!=null)
//	         //   LR.setRegistro(r, this);
//	        //else LR.AsignarRegistro(this);
//	        return Codigo;
//    }

}
