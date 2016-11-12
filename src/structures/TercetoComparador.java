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
		// TODO Auto-generated method stub
		return null;
	}


	
	
	
	

}
