package structures;

public class TercetoBase extends Terceto {

	public TercetoBase(Object first){
		this.operator="^";
		this.first=first;
		this.classType = "Terceto";
	}
	
	
	public String toString(){
		return "("+operator+","+((Element) first).getLexema()+")";
	};
	
	public String getLexema() {
		// TODO Auto-generated method stub
		return "["+String.valueOf(this.position)+"]";
	}


	@Override
	public String getAssembler() {
		String codigo = ";base de matriz\n";
		codigo += "MOV "+this.aux+", "+0+"\n";
		//System.out.println("==========================================getAssembler  terceto"+this);
		return codigo;
	}
}
