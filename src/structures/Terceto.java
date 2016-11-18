package structures;

public abstract class Terceto extends Element{

	protected Object first;
	protected Object second;
	protected String aux;
	
//	public String toString(){
//		return "("+operator+","+((Element) first).getLexema()+","+((Element) second).getLexema()+")";
//	};

	
	public abstract String getAssembler();

	public String toString(){
	return "("+operator+","+first+","+second+")";
};
	  /**
	   * estos metodos pertenecen a la generacion de codigo assembler
	   * 
	   */
	@Override
	public String getOperando() {
		// TODO Auto-generated method stub
		return aux;
	}
	
	
	
	  public String getAux() {
		return aux;
	}
	  
	  //recibe como parametro el nombre de la variable auxiliar, 
	  //auxGenerator.getname()  
	public void setAux(String aux) {
		this.aux = aux;
	}
	/**
	   * fin metodos generacion de codigo assembler
	   * 
	   */

	public Object getOperator() {
		return operator;
	}
	
	public Object getFirst() {
		return first;
	}
	
	public Object getSecond() {
		return second;
	}
	
	public void setOperator(Object operator) {
		this.operator = operator;
	}
	
	public void setFirst(Object first) {
		this.first = first;
	}
	
	public void setSecond(Object second) {
		this.second = second;
	}
	
	
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
	
	
}
