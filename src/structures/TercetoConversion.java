package structures;

public class TercetoConversion extends Terceto {

	public TercetoConversion(Object first, String second){
		this.operator = "conv";
		this.first    = first;
		this.second   = second; // second guardara el tipo de la conversion del elemento
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
		String op1 =((Element)this.first).getOperando();
		String instruccion = new String();
        instruccion += "FILD " + op1 + "\n";
        instruccion += "FSTP " + getAux() + "\n";
        return instruccion;
    } 


}
