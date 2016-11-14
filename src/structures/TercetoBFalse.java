
package structures;

/**
 * 
 * Terceto para las bifurcaciones por falso en sentencias IF o FOR
 *
 */
public class TercetoBFalse extends Terceto {

	/* (non-Javadoc)
	 * @see structures.Element#getLexema()
	 */
	
	public TercetoBFalse(Object first){
		this.operator = "BF";
		this.first    = first;
		this.position = 0;
		System.out.println("Ejecutando constructor de BFALSE");
	}
	
	public String toString(){
		System.out.println("TO STRING TERCETO FALSE");
		return "("+operator+","+((Element) first).getLexema()+","+((Element) second).getLexema()+")";
	};
	
	
	@Override
	public String getLexema() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAssembler() {
		System.out.println("==========================================getAssembler  terceto"+this);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String comparator = (String) ((Terceto)this.first).getOperator();
//        String label = ((Element)this.second).getAssembler().replace(":", ""); // ver si esto va 
        String jump = "";
       if(comparator.equals(">="))
                jump = "JL"+"\n";
       if(comparator.equals("<="))
                jump = "JG"+"\n";
       if(comparator.equals(">"))
    	   jump = "JLE"+"\n";
       if(comparator.equals("<"))
                jump = "JGE"+"\n";
       if(comparator.equals("="))
                jump = "JNE"+"\n";
       if(comparator.equals("!=")) //
                jump = "JE"+"\n";
        return jump ;
	}
}
