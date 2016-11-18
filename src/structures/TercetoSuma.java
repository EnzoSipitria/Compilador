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
		System.out.println("===terceto suma===");
		System.out.println("====terceto"+this);
		String Codigo= new String();
		Element op1 = (Element) this.first;
		Element op2 = (Element) this.second;
		if ((op1.getClassType().equals("Terceto")) && (op2.getClassType().equals("Terceto"))){
			boolean Arreglo1 = (((Element)op1).getOperator().equals(">^"));
			boolean Arreglo2 = (((Element)op2).getOperator().equals(">^"));
			if ((Arreglo1) && (Arreglo2)){ //CONTROLO SI LOS TERCETOS SON ARREGLOS
				if( this.typeVariable.equals("integer")){
					Codigo+="MOV "+"EBX" +", " + "dword ptr ["+((Terceto) op1).getAux()+"]"+"\n";
					Codigo +="ADD " + "EBX" + ", " + "dword ptr ["+((Terceto) op2).getAux()+"]"+"\n";
					Codigo+= "MOV " + getAux() + ", EBX" +"\n";}
				else {
					Codigo += "FLD " + "dword ptr ["+((Terceto) op1).getAux()+"]" + "\n";
					Codigo += "FADD " + "dword ptr ["+((Terceto) op2).getAux()+"]" + "\n";
					Codigo += "FST " + getAux() + "\n";//guardo copia  

				}

			}   
			else  if (Arreglo1){

				if( this.typeVariable.equals("integer")){
					Codigo+="MOV "+"EBX" +", " + "dword ptr ["+((Terceto) op1).getAux()+"]"+"\n";
					Codigo +="ADD " + "EBX" + ", " + op2.getOperando() +"\n";
					Codigo+= "MOV " + getAux() + ", EBX" +"\n";}
				else {
					Codigo += "FLD " + "dword ptr ["+((Terceto) op1).getAux()+"]" + "\n";
					Codigo += "FADD " + op2.getOperando() + "\n";
					Codigo += "FST " + getAux() + "\n";//guardo copia  

				}


			}   
			else if (Arreglo2)  { 
				if( this.typeVariable.equals("integer")){
					Codigo +="MOV " + "EBX" + ", " + "dword ptr ["+((Terceto) op2).getAux()+"]"+"\n";
					Codigo +="ADD " + "EBX"+ ", " + op1.getOperando() +"\n";
					Codigo+= "MOV " + getAux() + ", EBX" +"\n";}
				else {
					Codigo += "FLD " + op1.getOperando() + "\n";
					Codigo += "FADD " + "dword ptr ["+((Terceto) op2).getAux()+"]" + "\n";
					Codigo += "FST " + getAux() + "\n";//guardo copia  

				}
			}else 
				if( this.typeVariable.equals("integer")){

					Codigo +="MOV " + "BX"+ ", " + op1.getOperando()+"\n";       
					Codigo +="ADD " + "BX" + ", " + op2.getOperando()+"\n";
					Codigo+= "MOV " + getAux() + ", BX" +"\n";
				}
				else 
				{
					Codigo += "FLD " + op1.getOperando() + "\n";
					Codigo += "FADD " + op2.getOperando() + "\n";
					Codigo += "FST " + getAux() + "\n";//guardo copia 	
				}

		}else 
			//if (op1.getClassType().equals("Terceto")){
			if( this.typeVariable.equals("integer")){

				Codigo +="MOV " + "BX"+ ", " + op1.getOperando()+"\n";       
				Codigo +="ADD " + "BX" + ", " + op2.getOperando()+"\n";
				Codigo+= "MOV " + getAux() + ", BX" +"\n";
			}
			else 
			{
				Codigo += "FLD " + op1.getOperando() + "\n";
				Codigo += "FADD " + op2.getOperando() + "\n";
				Codigo += "FST " + getAux() + "\n";//guardo copia 	
			}

		//	}//cierra if de classType

		return Codigo;



	}


	/*
	 *@Override
	public String getAssembler() {
        String Codigo= new String();
        Element op1 = (Element) this.first;
        Element op2 = (Element) this.second;
        //Token aux = new Token("Vacio","");
        if ((op1.getClassType().equals("Terceto")) && (op2.getClassType().equals("Terceto"))){
            boolean Arreglo1 = (((Terceto)op1).getOperator().equals(">^"));
            boolean Arreglo2 = (((Terceto)op2).getOperator().equals(">^"));
            if ((Arreglo1) && (Arreglo2)){ //CONTROLO SI LOS TERCETOS SON ARREGLOS
                if( this.typeVariable.equals("integer")){
                Codigo+="MOV "+"EBX" +", " + "dword ptr ["+((Terceto) op1).getAux()+"]"+"\n";
                Codigo +="ADD " + "EBX" + ", " + "dword ptr ["+((Terceto) op2).getAux()+"]"+"\n";
                Codigo+= "MOV " + getAux() + ", EBX" +"\n";}
                else {
                    Codigo += "FLD " + "dword ptr ["+((Terceto) op1).getAux()+"]" + "\n";
                     Codigo += "FADD " + "dword ptr ["+((Terceto) op2).getAux()+"]" + "\n";
                     Codigo += "FST " + getAux() + "\n";//guardo copia  

                }

            }   
           else  if (Arreglo1){

            if( this.typeVariable.equals("integer")){
                    Codigo+="MOV "+"EBX" +", " + "dword ptr ["+((Terceto) op1).getAux()+"]"+"\n";
                    Codigo +="ADD " + "EBX" + ", " + op2.getOperando() +"\n";
                    Codigo+= "MOV " + getAux() + ", EBX" +"\n";}
                    else {
                        Codigo += "FLD " + "dword ptr ["+((Terceto) op1).getAux()+"]" + "\n";
                     Codigo += "FADD " + op2.getOperando() + "\n";
                     Codigo += "FST " + getAux() + "\n";//guardo copia  

                    }


                 }   
                 else if (Arreglo2)  { 
                    if( this.typeVariable.equals("integer")){
                        Codigo +="MOV " + "EBX" + ", " + "dword ptr ["+((Terceto) op2).getAux()+"]"+"\n";
                        Codigo +="ADD " + "EBX"+ ", " + op1.getOperando() +"\n";
                         Codigo+= "MOV " + getAux() + ", EBX" +"\n";}
                   else {
                    Codigo += "FLD " + op1.getOperando() + "\n";
                     Codigo += "FADD " + "dword ptr ["+((Terceto) op2).getAux()+"]" + "\n";
                     Codigo += "FST " + getAux() + "\n";//guardo copia  

                   }
                     } 
            }
                else 
                  {
                     if( this.typeVariable.equals("integer")){

                        Codigo +="MOV " + "BX"+ ", " + op1.getOperando()+"\n";       
                        Codigo +="ADD " + "BX" + ", " + op2.getOperando()+"\n";
                        Codigo+= "MOV " + getAux() + ", BX" +"\n";
                    }
                    else 
                    {
                     Codigo += "FLD " + op1.getOperando() + "\n";
                     Codigo += "FADD " + op2.getOperando() + "\n";
                     Codigo += "FST " + getAux() + "\n";//guardo copia 	
                    }
                  }

        return Codigo;
    }*/ 
}

