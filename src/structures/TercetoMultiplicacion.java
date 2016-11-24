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

		System.out.println("terceto "+operando1+" + "+operando2+" variable auxiliar"+this.getAux());
		String codigo = "";

		if (this.typeVariable.equals("integer")){
			if ( operando1.getOperator().equals(">^") && (operando2.getOperator().equals(">^")) ){
				codigo += "sentencias assembler para los dos operando de matrices";
				codigo+="MOV "+"EAX" +", " + "dword ptr ["+((Terceto) operando1).getAux()+"]"+"\n";
				codigo += "MOV DX, 0\n";
				codigo += "IMUL EAX, " + "dword ptr ["+((Terceto) operando2).getAux()+"]"+ "\n";
				codigo+= "MOV" + getAux() + "AX" +"\n";

			}
			else
				if (operando1.getOperator().equals(">^")) {
					codigo += "sentencias assembler para elementos de matriz cuando el primer operando es una matriz";
					codigo+="MOV "+"EAX" +", " + "dword ptr ["+((Terceto) operando1).getAux()+"]"+"\n";
					codigo += "MOV DX, 0\n";
					codigo += "IMUL AX, "+((Terceto) operando2).getAux()+"\n";
					codigo+= "MOV" + getAux() + "AX" +"\n";
				}
				else 
					if  (operando2.getOperator().equals(">^")) {
						codigo += "sentencias assembler para elementos de matriz cuando el segundo operando es una matriz";
						codigo+="MOV "+"AX" +", "+((Terceto) operando1).getAux()+"\n";
						codigo += "MOV DX, 0\n";
						codigo += "IMUL EAX, " + "dword ptr ["+((Terceto) operando2).getAux()+"]"+ "\n";
						codigo+= "MOV" + getAux() + "AX" +"\n";
					
					} 
					else{
						codigo += "MOV AX, " + operando1.getOperando() + "\n";
						codigo += "MOV DX, 0\n";   
						codigo += "IMUL AX, " + operando2.getOperando() + "\n";
						codigo += "MOV " + getAux() + ", AX \n";
					} 
		}
		else if ( operando1.getOperator().equals(">^") && (operando2.getOperator().equals(">^")) ){

			codigo += "sentencias assembler para los dos operando de matrices";

		}
		else 
			if (operando1.getOperator().equals(">^")) {
				codigo += "sentencias assembler para elementos de matriz cuando el primer operando es una matriz";
			}
			else 
				if  (operando2.getOperator().equals(">^")) {
					codigo += "sentencias assembler para elementos de matriz cuando el segundo operando es una matriz";
				} 
				else { 
					codigo += "FLD " + operando1.getOperando() + "\n";
					codigo += "FMUL " + operando2.getOperando() + "\n";
					codigo += "FSTP " + getAux() + "\n";//guardo copia 	
				}

		return codigo;
	}



}
/*@Override
	public String getAssembler(){
		System.out.println("===terceto multiplicacion===");
		System.out.println("====terceto"+this);
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		String Codigo= "";
		Element op1 = (Element) this.first;
		Element op2 = (Element) this.second;
		if ((op1.getClassType().equals("Terceto")) && (op2.getClassType().equals("Terceto"))){
			boolean Arreglo1 = (((Terceto)op1).getOperator().equals(">^"));
			boolean Arreglo2 = (((Terceto)op2).getOperator().equals(">^"));
			if ((Arreglo1) && (Arreglo2)){ //CONTROLO SI LOS TERCETOS SON ARREGLOS
				if( this.typeVariable.equals("integer")){
					Codigo+="MOV "+"EAX" +", " + "dword ptr ["+((Terceto) op1).getAux()+"]"+"\n";
					Codigo += "MOV DX, 0\n";
					Codigo += "IMUL EAX, " + "dword ptr ["+((Terceto) op2).getAux()+"]"+ "\n";
					Codigo+= "MOV" + getAux() + "EAX" +"\n";}

				else {Codigo += "FLD " + "dword ptr ["+((Terceto) op1).getAux()+"]" + "\n";
				Codigo += "FMUL " + "dword ptr ["+((Terceto) op2).getAux()+"]" + "\n";
				Codigo += "FSTP " + getAux() + "\n";

				}
			}    
			else  if (Arreglo1){
				if( this.typeVariable.equals("integer")){
					Codigo+="MOV "+"EAX" +", " + "dword ptr ["+((Terceto) op1).getAux()+"]"+"\n";
					Codigo += "MOV DX, 0\n";
					Codigo += "IMUL EAX, " + op2.getOperando()+ "\n";
					Codigo+= "MOV" + getAux() + "EAX" +"\n";}
				else {
					Codigo += "FLD " + "dword ptr ["+((Terceto) op1).getAux()+"]" + "\n";
					Codigo += "FMUL " + op2.getOperando() + "\n";
					Codigo += "FSTP " + getAux() + "\n";

				}

			}   
			else if (Arreglo2)  { 
				if( this.typeVariable.equals("integer")){
					Codigo+="MOV "+"EAX" +", " + "dword ptr ["+((Terceto) op2).getAux()+"]"+"\n";
					Codigo += "MOV DX, 0\n";
					Codigo += "IMUL EAX, " + op1.getOperando()+ "\n";
					Codigo+= "MOV" + getAux() + ", EAX" +"\n";}
				else {
					Codigo += "FLD " + op1.getOperando() + "\n";
					Codigo += "FMUL " + "dword ptr ["+((Terceto) op2).getAux()+"]" + "\n";
					Codigo += "FSTP " + getAux() + "\n";

				}

			} else 			
				if( this.typeVariable.equals("integer")){

					Codigo += "MOV AX, " + op1.getOperando() + "\n";
					Codigo += "MOV DX, 0\n";   
					Codigo += "IMUL AX, " + ((Terceto) op2).getAux() + "\n";
					Codigo += "MOV " + getAux() + ", AX \n";
				}
				else 
				{
					Codigo += "FLD " + op1.getOperando() + "\n";
					Codigo += "FMUL " + op2.getOperando() + "\n";
					Codigo += "FSTP " + getAux() + "\n";
				}
		}//cierra if type tercetos
		else 
		{
			if( this.typeVariable.equals("integer")){

				Codigo += "MOV AX, " + op1.getOperando() + "\n";
				Codigo += "MOV DX, 0\n";   
				Codigo += "IMUL AX, " + op2.getOperando() + "\n";
				Codigo += "MOV " + getAux() + ", AX \n";
			}
			else 
			{
				Codigo += "FLD " + op1.getOperando() + "\n";
				Codigo += "FMUL " + op2.getOperando() + "\n";
				Codigo += "FSTP " + getAux() + "\n";
			}
		}
		return Codigo;
	}

}
 */
