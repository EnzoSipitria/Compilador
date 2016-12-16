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
		String codigo ="";
		System.out.println(operando1+": este fue el operando 1 de la conversion");
		if (second.equals("integer")) {
//			codigo+= "MOV AX, "+0+"\n";// cargo 0 xq se que va a haber perdida de informacion
//			codigo+="MOV DX, "+0+"\n";// cargo 0 xq ya seque va ahaber perdida de informacion
//			codigo+= "CMP DX, AX\n"; // comparacion de rangos 
//			codigo+= "JE _hayPerdida"+number+"\n";// salto por menor igual
//			codigo+=" JMP _indexcontrol"+"\n";/// salto por error funcion getindexerror
//			codigo+= "_hayPerdida"+number+": \n";//muestro mensaje de perdida de informacion. sigo con la ejecucion normal 
			
//			codigo += "invoke MessageBox, NULL, addr _conversionCPerdida, addr _conversionCPerdida , MB_OK\n";
			codigo += "invoke MessageBox, NULL, addr _msjCP, addr _msjCP , MB_OK\n";
			// conversiones float a integer
			codigo+= "FLD " + operando1.getOperando() + "\n";
			codigo += "FISTP " + getAux() + "\n";
		} else{
		//aca generar sentencias assembler para control de perdida de informacion
		//solamente la conversion, la cuenta en le siguiente paso
	    if (operando1.getClassType().equals("Token")) {
	    	codigo += "MOV "+ getAux()+ ", " +operando1.getOperando()+ "\n";	
	    	codigo += "FILD " + getAux() + "\n";
	    	codigo += "FSTP " + getAux() + "\n";
			
		} else {
	    	codigo += "FILD "+operando1.getOperando()+ "\n";
	    	codigo += "FSTP " + getAux() + "\n";

		}		
		}
        return codigo;
    } 


}
