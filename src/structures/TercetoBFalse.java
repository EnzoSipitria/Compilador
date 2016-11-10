
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

}
