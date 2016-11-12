package structures;

public abstract class Element {
	
	protected int position;
	protected Object value;
	protected String typeVariable = null;//TIPO DE VALOR integer float
	protected String use;
	
	public abstract String getLexema(); 
	public abstract int getPosition();
	public abstract String getOperando();
	public abstract String getAssembler();
	
	public Object getValue() {
//		System.out.println("valoren clase token "+ value);
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	public boolean hasTypeVariable() {return (typeVariable!=null);	}
	public String getTypeVariable() {
		return typeVariable;
	}
	public void setTypeVariable(String typeVariable) {
		this.typeVariable = typeVariable;
	}
	public String getUse() {
		return use;
	}
	public void setUse(String use) {
		this.use = use;
	}
}
