package structures;

public class TercetoResta extends Terceto {


	public TercetoResta(Object first, Object second){
		this.operator = "-";
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
		System.out.println("===TERCETO RESTA getAssembler() ===");

		Element operando1 = (Element) this.first;
		Element operando2 = (Element) this.second;

		System.out.println("terceto "+operando1+" - "+operando2+" variable auxiliar"+this.getAux());
		String codigo = ";resta "+operando1+" - "+operando2+"\n";

		if (this.typeVariable.equals("integer")){
			if ( operando1.getOperator().equals(">^") && (operando2.getOperator().equals(">^")) ){
				//si son los dos matrices 
				codigo += "MOV EAX, "+operando1.getOperando()+"\n";
				codigo += "MOV BX, [EAX]\n";
				codigo += "MOV EAX, "+operando2.getOperando()+"\n";
				codigo += "SUB BX, [EAX]\n";
				codigo += "MOV "+getAux()+", BX\n";
			}
			else
				if (operando1.getOperator().equals(">^")) {
					// primer operando es matrix 
					codigo += "MOV EAX, "+operando1.getOperando()+"\n";
					codigo += "MOV BX, [EAX]\n";
					codigo += "SUB BX, "+operando2.getOperando()+"\n";
					codigo += "MOV "+getAux()+", BX\n";
				}
				else 
					if  (operando2.getOperator().equals(">^")) {
						//el segundo es un operador matriz 
						codigo += "MOV EAX, "+operando2.getOperando()+"\n";
						codigo += "MOV BX, [EAX]\n";
						codigo += "SUB "+operando1.getOperando()+", BX"+"\n";
						codigo += "MOV DX,"+operando1.getOperando()+"\n";
						codigo += "MOV "+getAux()+", DX\n";
					} 
					else{
						codigo +="MOV " + "BX"+ ", " + operando1.getOperando()+"\n";       
						codigo +="SUB " + "BX" + ", " + operando2.getOperando()+"\n";
						codigo+= "MOV " + this.getAux() + ", BX" +"\n";
					} 
		}
		else   // if this.typeVariable.equals("integer") => rama por float 
			if ( operando1.getOperator().equals(">^") && (operando2.getOperator().equals(">^")) ){
				codigo += "MOV EDX, dword ptr ["+operando1.getOperando()+"]\n";
				codigo += "MOV EBX, [EDX]\n";
				codigo += "MOV _nourriturre, EBX\n";
				codigo += "FLD _nourriturre\n";
				codigo += "MOV EDX, dword ptr ["+operando2.getOperando()+"]\n";
				codigo += "MOV EBX, [EDX]\n";
				codigo += "MOV _nourriturre, EBX\n";
				codigo += "FLD _nourriturre\n";
				codigo += "FSUB\n";
				codigo += "FSTP "+getAux()+"\n";

			}
			else if (operando1.getOperator().equals(">^")) {
				codigo += "MOV EDX, dword ptr ["+operando1.getOperando()+"]\n";
				codigo += "MOV EBX, [EDX]\n";
				codigo += "MOV _nourriturre, EBX\n";
				codigo += "FLD _nourriturre\n";
				codigo += "FSUB "+operando2.getOperando()+"\n";
				codigo += "FSTP "+getAux()+"\n";
			}
			else if  (operando2.getOperator().equals(">^")) {
				codigo += "FLD "+operando1.getOperando()+"\n";
				codigo += "MOV EDX, dword ptr ["+operando2.getOperando()+"]\n";
				codigo += "MOV EBX, [EDX]\n";
				codigo += "MOV _nourriturre, EBX\n";
				codigo += "FLD _nourriturre\n";
				codigo += "FSUB \n";
				codigo += "FSTP "+getAux()+"\n";
			} 
			else{

				codigo += "FLD " + operando1.getOperando() + "\n";
				codigo += "FSUB " + operando2.getOperando() + "\n";
				codigo += "FSTP " + this.getAux() + "\n";//guardo copia 	
			}

		return codigo;
	}



}	


