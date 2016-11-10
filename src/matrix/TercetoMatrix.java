package matrix;

import structures.Terceto;

public class TercetoMatrix extends Terceto {
	
	public TercetoMatrix (Object operator, Object first, Object second){
		this.operator =operator;
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
	public String toString(){
		return "("+operator+","+first+","+second+")";
	};


}
