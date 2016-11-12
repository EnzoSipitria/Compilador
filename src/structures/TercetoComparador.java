package structures;

public class TercetoComparador extends Terceto{
	
	public TercetoComparador(Object comparator, Object first, Object second){
		this.operator = ((Element) comparator).getLexema();
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
        //return "CMP " + param1.getOperando() + ", " + param2.getOperando() + "\n";
        String op1 =((Element)this.first).getOperando();
        String op2 =((Element)this.second).getOperando();
        String ins=this+"\n";
        System.out.println("==========================================getAssembler  terceto"+this);
        System.out.println("Tipo del terceto comparador: "+this.typeVariable);
        if (this.typeVariable.equals("integer")) {
            ins = "MOV CX, " + op2 + "\n";
            ins += "CMP " + op1 + ", CX\n";
        } else {
            ins = "FLD " + op1 + "\n";
            ins += "FLD " + op2 + "\n";
            ins += "FCOM \n";
            ins += "FSTSW AX \n";//paso los valores del copro al proc
            ins += "SAHF \n";//cargo los valores
        }
        return (ins);
    }


	
	
	
	

}
