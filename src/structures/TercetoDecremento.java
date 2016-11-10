package structures;

public class TercetoDecremento extends Terceto{

	public TercetoDecremento (Object first){
		this.operator = "--";
		this.first = first;
	}
	
	@Override
	public String toString(){
		return "("+operator+","+((Element) first).getLexema()+")";
	};
	
	@Override
	public String getLexema() {
		// TODO Auto-generated method stub
		return "["+String.valueOf(this.position)+"]";
	}

}
