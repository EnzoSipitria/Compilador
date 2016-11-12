package structures;

public class TercetoAnotacion extends Terceto {

	public TercetoAnotacion (Object first){
		this.operator = "ANOT";
		this.first    = first;
		this.second   = null;
		this.position = 0;
	}
	
	@Override
	public String getLexema() {
		return "["+String.valueOf(this.position)+"]";
	}
	
	@Override
	public String toString() {
		return  "("+operator+","+first+")";
	}

	@Override
	public String getAssembler() {
		System.out.println("==========================================getAssembler  terceto"+this);
		return null;
	}

}
