package structures;

public class TercetoSuma extends Terceto {

	public TercetoSuma(Object first, Object second){
		this.operator = "+";
		this.first    = first;
		this.second   = second;
		this.position = 0;
	}

	@Override
	public String toString(){
		System.out.println("TO STRING TERCETO SUMA");
		return "("+operator+","+((Element) first).getLexema()+","+((Element) second).getLexema()+")";
	};
	
	@Override
	public String getLexema() {
		// TODO Auto-generated method stub
		return "["+String.valueOf(this.position)+"]";
	}

	@Override
	public String getAssembler() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String op1 = ((Element) this.first).getOperando();
        String op2 = ((Element) this.second).getOperando();
        String operacion = new String();

        if (this.typeVariable.equals("integer")) {
            operacion += "MOV BX, " + op1 + "\n";
            operacion += "ADD BX, " + op2 + "\n";
            operacion += "MOV " + getAux() + ", BX \n";
        } else {
            operacion += "FLD " + op1 + "\n";
            operacion += "FADD " + op2 + "\n";
            operacion += "FST " + getAux() + "\n";//guardo copia
//            operacion += "FABS \n";//Factor Comun trata overflow suma no tener en cuenta lo dejo para entender mas del tema 
//            operacion += "FCOM __MAX\n";//comparo con val inm
//            operacion += "FSTSW AX \n";//paso los valores del copro al proc
//            operacion += "SAHF \n";//cargo los valores
//            operacion += "JA _overflow_suma \n";//si flag G->overflow
//            operacion += "FCOM __MIN\n";
//            operacion += "FSTSW AX\n";
//            operacion += "SAHF\n";
//            operacion += "JB _overflow_suma\n";
        }
        return operacion;
    }

}

