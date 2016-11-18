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
