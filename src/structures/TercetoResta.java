package structures;

public class TercetoResta extends Terceto {
	
	
	public TercetoResta(Object first, Object second){
		this.operator = "-";
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
	 public String getAssembler() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String op1 = ((Element) this.first).getOperando();
        String op2 = ((Element) this.second).getOperando();
        String operacion = new String();

        if (typeVariable.equals("integer")){
            operacion += "MOV BX, " + op1 + "\n";
            operacion += "SUB BX, " + op2 + "\n";
            operacion += "MOV " + getAux() + ", BX \n";
        } else {
            operacion += "FLD " + op1 + "\n";
            operacion += "FSUB " + op2 + "\n";
            operacion += "FSTP " + getAux() + "\n";
        }

        return operacion;
    }
	
}
