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
	public String getAssembler() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
