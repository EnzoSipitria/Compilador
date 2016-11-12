package structures;

public class TercetoAsignacion extends Terceto{
	
	public TercetoAsignacion(Object first, Object second){
		this.operator = ":=";
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
	public String toString() {
		return  "("+operator+","+((Element) first).getLexema()+","+second+")";
	}

	@Override
	public String getAssembler(){
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String op1 = ((Element) this.first).getOperando();
        String op2 = ((Element) this.second).getOperando();
        System.out.println("=>operando first assembler"+op1);
        System.out.println("=>operando second assembler"+second);
        String ins = this+"\n";
        boolean op2EsVar = (op2.charAt(0) == '_') || (op2.charAt(0) == '@');
        System.out.println("==========================================getAssembler  terceto"+this);
        if (((Element) this.first).getTypeVariable().equals("integer")) {
            if (op2EsVar) {
                ins += "MOV BX, " + op2 + "\n";
                ins += "MOV " + op1 + ", BX\n";
            } else {
                ins = "MOV " + op1 + ", " + op2 + "\n";
            }
        } else {
            ins += "FLD " + op2 + "\n";
            ins += "FSTP " + op1 + "\n";
        }
        return ins;
    }
	
	

}
