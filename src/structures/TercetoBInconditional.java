package structures;


public class TercetoBInconditional extends Terceto {

	
	public TercetoBInconditional() {
		this.operator = "BI";
		this.position=0;
		// TODO Auto-generated constructor stub
	}

	public String toString(){
		System.out.println("TO STRING TERCETO INCONDICIONAL");
		return "("+operator+","+((Element) first).getLexema()+")";
	};
	
	
	@Override
	public String getLexema() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAssembler() {
		System.out.println("==========================================getAssembler  terceto"+this);
		return null;
	}

}
