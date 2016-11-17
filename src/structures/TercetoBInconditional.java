package structures;


public class TercetoBInconditional extends Terceto {

	
	public TercetoBInconditional() {
		this.operator = "BI";
		this.position=0;
		// TODO Auto-generated constructor stub
	}

	public String toString(){
		//System.out.println("TO STRING TERCETO INCONDICIONAL");
		return "("+operator+","+((Element) first)+")";
	};
	
	
	@Override
	public String getLexema() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAssembler() {
		System.out.println("boolean label "+hasLabel+" ");
		//System.out.println("==========================================getAssembler  terceto"+this);
        String label = "label"+((Element)this.first).getPosition();
       // System.out.println("LABEL TERCETO BINCONDICIONAL"+((Element)this.first).getPosition());
        String jump = "JMP"+" "+label+"\n";
		String labelsalto = "";
		if (isHasLabel()){
			int position = this.position+1;
			labelsalto = "label"+position+": ";
		}
		return jump;
//		return jump+labelsalto;
	}

}
