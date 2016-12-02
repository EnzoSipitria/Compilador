package structures;

public class TercetoPrint extends Terceto {

	
	public TercetoPrint(Object first){
		this.operator = "print";
		this.first    = first;
		//this.second   = second;
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
		
		return "invoke MessageBox, NULL, addr " +getAux()+ ", addr " +getAux()+ ", MB_OK\n";

	}
//        String cadena = ((Token)first).getAssembler();
        
//        String name = cadena.substring(2, cadena.length()-1);
//    	name = name.replaceAll(" ", "");
//    	name = name.substring(0, Math.min(15, name.length()));
    	
    	




}