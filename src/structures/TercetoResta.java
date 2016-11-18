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
		System.out.println("===terceto resta===");
		System.out.println("====terceto"+this);
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		String Codigo= new String();
		Element op1 = (Element) this.first;
		Element op2 = (Element) this.second;
		System.out.println("operando1 "+op1+"   operando2 "+op2);//		Token aux = new Token("Vacio","");
		if ((op1.getClassType().equals("Terceto")) && (op2.getClassType().equals("Terceto"))){
			System.out.println("MAXI SE QUEMO los tipos son tercetos");
			boolean Arreglo1 = (((Terceto)op1).getOperando().equals(">^"));
			boolean Arreglo2 = (((Terceto)op2).getOperando().equals(">^"));
			if ((Arreglo1) && (Arreglo2)){ //CONTROLO SI LOS TERCETOS SON ARREGLOS
				if( this.typeVariable.equals("integer")){
					Codigo+="MOV "+"EBX" +", " + "dword ptr ["+((Terceto) op1).getAux()+"]"+"\n";
					Codigo +="SUB " + "EBX" + ", " + "dword ptr ["+((Terceto) op2).getAux()+"]"+"\n";
					Codigo+= "MOV " + getAux() + ", EBX" +"\n";}
				else {
					Codigo += "FLD " + "dword ptr ["+((Terceto) op1).getAux()+"]" + "\n";
					Codigo += "FSUB " + "dword ptr ["+((Terceto) op2).getAux()+"]" + "\n";
					Codigo += "FSTP " + getAux() + "\n";//guardo copia  

				}
			}   
			else  if (Arreglo1){
				if( this.typeVariable.equals("integer")){
					Codigo+="MOV "+"EBX" +", " + "dword ptr ["+((Terceto) op1).getAux()+"]"+"\n";
					Codigo +="SUB " + "EBX" + ", " + op2.getOperando() +"\n";
					Codigo+= "MOV " + getAux() + ", EBX" +"\n";}
				else {
					Codigo += "FLD " + "dword ptr ["+((Terceto) op1).getAux()+"]" + "\n";
					Codigo += "FSUB " + op2.getOperando() + "\n";
					Codigo += "FSTP " + getAux() + "\n";//guardo copia  

				}

			}   
			else if (Arreglo2)  { 
				if( this.typeVariable.equals("integer")){
					Codigo +="MOV" + "EBX" + ", " + "dword ptr ["+((Terceto) op2).getAux()+"]"+"\n";
					Codigo +="SUB " + "EBX"+ ", " + op1.getOperando() +"\n";
					Codigo+= "MOV " + getAux() + ", EBX" +"\n";}
				else {
					Codigo += "FLD " + op1.getOperando() + "\n";
					Codigo += "FSUB " + "dword ptr ["+((Terceto) op2).getAux()+"]" + "\n";
					Codigo += "FSTP " + getAux() + "\n";//guardo copia  

				}
			} if( this.typeVariable.equals("integer")){

				Codigo +="MOV " + "BX"+ ", " + op1.getOperando()+"\n";       
				Codigo +="SUB " + "BX" + ", " + op2.getOperando()+"\n";
				Codigo+= "MOV " + getAux() + ", BX" +"\n";
			}
			else 
			{
				Codigo += "FLD " + op1.getOperando() + "\n";
				Codigo += "FSUB " + op2.getOperando() + "\n";
				Codigo += "FSTP " + getAux() + "\n";//guardo copia  
			}
		}
		else 
		{
			System.out.println("FACUNDO ESTUVO POR AQUI"+op1.getOperando()+"      op2"+op2.getOperando());
			if( this.typeVariable.equals("integer")){

				Codigo += "MOV " + "BX"+ ", " + op1.getOperando()+"\n";       
				Codigo += "SUB " + "BX" + ", " + op2.getOperando()+"\n";
				Codigo += "MOV " + getAux() + ", BX" +"\n";
			}
			else 
			{
				Codigo += "FLD " + op1.getOperando() + "\n";
				Codigo += "FSUB " + op2.getOperando() + "\n";
				Codigo += "FSTP " + getAux() + "\n";//guardo copia  
			}
		}
		return Codigo;
	}

}
