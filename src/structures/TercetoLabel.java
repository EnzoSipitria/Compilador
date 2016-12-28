package structures;

public class TercetoLabel extends Terceto {

	
	public TercetoLabel(Object first, Object Position) {
		// TODO Auto-generated constructor stub
		this.operator = "label";
		this.first = first;
		this.position = (Integer) Position;
		this.classType = "Terceto";
	}
	
	
	@Override
	public String getAssembler() {
		
		return "label"+this.first+": \n";
	}

	@Override
	public String getLexema() {
		// TODO Auto-generated method stub
		return null;
	}

}
