package structures;

public class TercetoBase extends Terceto {

	public TercetoBase(Object first){
		this.operator="^";
		this.first=first;
	}
	
	
	public String toString(){
		return "("+operator+","+((Element) first).getLexema()+")";
	};
	
	public String getLexema() {
		// TODO Auto-generated method stub
		return "["+String.valueOf(this.position)+"]";
	}
}
