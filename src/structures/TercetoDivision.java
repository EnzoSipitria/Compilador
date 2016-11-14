package structures;

public class TercetoDivision extends Terceto {

	public TercetoDivision(Object first, Object second){
		this.operator = "/";
		this.first    = first;
		this.second   = second;
		this.position = 0;
	}
	
	@Override
	public String getLexema() {
		// TODO Auto-generated method stub
		return "["+String.valueOf(this.position)+"]";
	}

	@Override
	public String getAssembler() {    //no llores por un bou
		 String op1 =((Element)this.first).getOperando();
	        String op2 =((Element)this.second).getOperando();
        String operacion = "";
        System.out.println("==========================================getAssembler  terceto"+this);
        if (this.typeVariable.equals("integer")) {
            operacion += "XOR BX, BX \n"; //set eax to 0 
            operacion += "ADD BX, " + op2 + "\n";
            operacion += "JZ _division_cero\n";
            operacion += "MOV DX, 0\n";//Cargo la parte alta con cero, porque todos
            operacion += "MOV AX, " + op1 + "\n";//los enteros son de 16 bits no más
            operacion += "CWD\n";//copia el signo a DX
            operacion += "IDIV " + op2 + "\n";
            operacion += "MOV " + getAux() + ", AX\n";
        } else {
            operacion += "FLD " + op2 + "\n";
            operacion += "FTST\n";
            operacion += "XOR BX, BX\n"; //set eax to 0 
            operacion += "FSTSW AX \n";//paso los valores del copro al proc
            operacion += "SAHF \n";//cargo los valores
            operacion += "JE _division_cero\n";
            operacion += "FLD " + op1 + "\n";
            operacion += "FDIVR \n"; // o "FDIV ST, ST(1)\n";
            operacion += "FSTP " + getAux() + "\n";
        }
        return operacion;
    }

}
