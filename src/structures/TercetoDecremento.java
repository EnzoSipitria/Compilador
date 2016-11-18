package structures;

public class TercetoDecremento extends Terceto{

	public TercetoDecremento (Object first){
		this.operator = "--";
		this.first = first;
		this.classType = "Terceto";
	}
	
	@Override
	public String toString(){
		return "("+operator+","+((Element) first).getLexema()+")";
	};
	
	@Override
	public String getLexema() {
		// TODO Auto-generated method stub
		return "["+String.valueOf(this.position)+"]";
	}

	@Override
	public String getAssembler() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		 String op1 =((Element)this.first).getOperando();
       /// String op2 = param2.getOperando(); no va por que no tenemos segundo operando
        String operacion = "";
       // System.out.println("==========================================getAssembler  terceto"+this);

        if (((Element) this.first).getTypeVariable().equals("integer")) {
            operacion += "MOV BX, " + op1 + "\n";
            operacion += "SUB BX, " + 1 + "\n";
            operacion += "MOV " + getAux() + ", BX \n";
        } else {
            operacion += "FLD " + op1 + "\n";
            operacion += "FSUB " + 1 + "\n";
            operacion += "FSTP " + getAux() + "\n";
        }

        return operacion;
    }

}
