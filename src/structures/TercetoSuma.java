package structures;

public class TercetoSuma extends Terceto {

	public TercetoSuma(Object first, Object second){
		this.operator = "+";
		this.first    = first;
		this.second   = second;
		this.position = 0;
	}

	@Override
	public String toString(){
		System.out.println("TO STRING TERCETO SUMA");
		return "("+operator+","+((Element) first).getLexema()+","+((Element) second).getLexema()+")";
	};
	
	@Override
	public String getLexema() {
		// TODO Auto-generated method stub
		return "["+String.valueOf(this.position)+"]";
	}
}
