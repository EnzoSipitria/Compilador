package structures;

public class TercetoMultiplicacion extends Terceto {
	
	public TercetoMultiplicacion(Object first, Object second){
		this.operator = "*";
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
	 public String getAssembler(){
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String op1 = ((Element)this.first).getOperando();
        String op2 = ((Element)this.second).getOperando();
        String operacion = this+"\n";
        System.out.println("==========================================getAssembler  op1"+op1);
        if (this.typeVariable.equals("integer")) {
            operacion += "MOV AX, " + op1 + "\n";
            operacion += "MOV DX, 0\n";
            operacion += "IMUL AX, " + op2 + "\n";
            operacion += "MOV " + getAux() + ", AX \n";
        } else {
            operacion += "FLD " + op1 + "\n";
            operacion += "FMUL " + op2 + "\n";
            operacion += "FSTP " + getAux() + "\n";
        }
        return operacion;
    }

}
