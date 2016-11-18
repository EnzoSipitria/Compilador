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
	public String getAssembler() {    //no llores por un bou
		 String Codigo= "";
	        Element op1 = (Element) this.first;
	        Element op2 = (Element) this.second;
//	        Token aux = new Token("Vacio","");
	        if ((op1.getClassType().equals("Terceto")) && (op2.getClassType().equals("Terceto"))){
	            boolean Arreglo1 = (((Terceto)op1).getOperando().equals(">^"));
	            boolean Arreglo2 = (((Terceto)op2).getOperando().equals(">^"));
	            if ((Arreglo1) && (Arreglo2)){ 
	            //CONTROLO SI LOS TERCETOS SON ARREGLOS
	                 if( this.typeVariable.equals("integer")){
	                Codigo += "XOR BX, BX \n"; //set eax to 0 
	                Codigo += "ADD EBX, " + "dword ptr ["+((Terceto) op2).getAux()+"]"+"\n";
	                Codigo += "JZ _division_cero\n";
	                Codigo += "MOV DX, 0\n";//Cargo la parte alta con cero, porque todos
	                Codigo += "MOV EAX, " + "dword ptr ["+((Terceto) op1).getAux()+"]"+"\n";		;//los enteros son de 16 bits no m�s
	                Codigo += "CWD\n";//copia el signo a DX
	                Codigo += "IDIV " + "dword ptr ["+((Terceto) op2).getAux()+"]"+ "\n";
	                Codigo += "MOV " + getAux() + ", AX\n";}
	                else {
	                    Codigo += "FLD " + "dword ptr ["+((Terceto) op2).getAux()+"]" + "\n";
	                    Codigo += "FTST\n";
	                    Codigo += "XOR BX, BX\n"; //set eax to 0 
	                    Codigo += "FSTSW AX \n";//paso los valores del copro al proc
	                    Codigo += "SAHF \n";//cargo los valores
	                    Codigo += "JE _division_cero\n";
	                    Codigo += "FLD " + "dword ptr ["+((Terceto) op1).getAux()+"]" + "\n";
	                    Codigo += "FDIVR \n"; // o "FDIV ST, ST(1)\n";
	                    Codigo += "FSTP " + getAux() + "\n";
	                }
	            }    
	           else  if (Arreglo1){
	                 if( this.typeVariable.equals("integer")){
	           	    Codigo += "XOR BX, BX \n"; //set eax to 0 
	                Codigo += "ADD BX, " + op2 + "\n";
	                Codigo += "JZ _division_cero\n";
	                Codigo += "MOV DX, 0\n";//Cargo la parte alta con cero, porque todos
	                Codigo += "MOV AX, " + "dword ptr ["+((Terceto) op1).getAux()+"]"+"\n";//los enteros son de 16 bits no m�s
	                Codigo += "CWD\n";//copia el signo a DX
	                Codigo += "IDIV " + op2.getOperando() + "\n";
	                Codigo += "MOV " + getAux() + ", AX\n";}
	                else {
	                    Codigo += "FLD " + op2.getOperando() + "\n";
	                    Codigo += "FTST\n";
	                    Codigo += "XOR BX, BX\n"; //set eax to 0 
	                     Codigo += "FSTSW AX \n";//paso los valores del copro al proc
	                    Codigo += "SAHF \n";//cargo los valores
	                   Codigo += "JE _division_cero\n";
	                    Codigo += "FLD " + "dword ptr ["+((Terceto) op1).getAux()+"]" + "\n";
	                    Codigo += "FDIVR \n"; // o "FDIV ST, ST(1)\n";
	                    Codigo += "FSTP " + getAux() + "\n";

	                }
	              
	                 }   
	                 else if (Arreglo2)  { 
	                    if( this.typeVariable.equals("integer")){
	                 	Codigo += "XOR BX, BX \n"; //set eax to 0 
	                Codigo += "ADD BX, " +"dword ptr ["+((Terceto) op2).getAux()+"]"+"\n";
	                Codigo += "JZ _division_cero\n";
	                Codigo += "MOV DX, 0\n";//Cargo la parte alta con cero, porque todos
	                Codigo += "MOV AX, " + op1.getOperando()+"\n";//los enteros son de 16 bits no m�s
	                Codigo += "CWD\n";//copia el signo a DX
	                Codigo += "IDIV " +  "dword ptr ["+((Terceto) op2).getAux()+"]"+"\n";
	                Codigo += "MOV " + getAux() + ", AX\n";}

	                else {
	                 Codigo += "FLD " + "dword ptr ["+((Terceto) op2).getAux()+"]" + "\n";
	                    Codigo += "FTST\n";
	                    Codigo += "XOR BX, BX\n"; //set eax to 0 
	                     Codigo += "FSTSW AX \n";//paso los valores del copro al proc
	                    Codigo += "SAHF \n";//cargo los valores
	                   Codigo += "JE _division_cero\n";
	                    Codigo += "FLD " + op1.getOperando() + "\n";
	                    Codigo += "FDIVR \n"; // o "FDIV ST, ST(1)\n";
	                    Codigo += "FSTP " + getAux() + "\n";

	                }
	                        } }
	           // LR.LiberarRegistro(Op2);}
	                else 
	                  {
	                     if( this.typeVariable.equals("integer")){

	                        Codigo += "XOR BX, BX \n"; //set eax to 0 
	                        Codigo += "ADD BX, " + op2.getOperando() + "\n";
	                        Codigo += "JZ _division_cero\n";
	                        Codigo += "MOV DX, 0\n";//Cargo la parte alta con cero, porque todos
	                        Codigo += "MOV AX, " + op1.getOperando() + "\n";//los enteros son de 16 bits no m�s
	                        Codigo += "CWD\n";//copia el signo a DX
	                        Codigo += "IDIV " + op2.getOperando() + "\n";
	                        Codigo += "MOV " + getAux() + ", AX\n";
	                    }
	                    else 
	                    {
	                    Codigo += "FLD " + op2.getOperando() + "\n";
	                    Codigo += "FTST\n";
	                    Codigo += "XOR BX, BX\n"; //set eax to 0 
	                     Codigo += "FSTSW AX \n";//paso los valores del copro al proc
	                    Codigo += "SAHF \n";//cargo los valores
	                   Codigo += "JE _division_cero\n";
	                    Codigo += "FLD " + op1.getOperando() + "\n";
	                    Codigo += "FDIVR \n"; // o "FDIV ST, ST(1)\n";
	                    Codigo += "FSTP " + getAux() + "\n"; 	
	                    }
	                  }
	      //  String r = LR.getRegistro(Op1);
	       // if (r!=null)
	         //   LR.setRegistro(r, this);
	        //else LR.AsignarRegistro(this);
	        return Codigo;
    }

}
