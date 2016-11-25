package structures;

public class TercetoConversion extends Terceto {

	public TercetoConversion(Object first, String second){
		this.operator = "conv";
		this.first    = first;
		this.second   = second; // second guardara el tipo de la conversion del elemento
		this.position = 0;
		this.classType = "Terceto";
	}

	@Override
	public String getLexema() {
		// TODO Auto-generated method stub
		return "["+String.valueOf(this.position)+"]";
	}

	@Override
	public String getAssembler() {
		Element operando1 =((Element)this.first);
		String instruccion ="";
		System.out.println(operando1+": este fue el operando 1 de la conversion");
		//aca generar sentencias assembler para control de perdida de informacion
		//solamente la conversion, la cuenta en le siguiente paso
        instruccion += "FILD " + operando1.getOperando() + "\n";
        instruccion += "FSTP " + getAux() + "\n";
        return instruccion;
    } 


}
