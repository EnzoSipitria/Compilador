package structures;

/**
 * 
 * clase para almacenar los tokens simples que son usados enlas sentencias de seleccion e iteracion para guardar el inicio de las mismas
 * 
 * */
public class TercetoSimple extends Terceto{

	public TercetoSimple(Object first){
		this.first = first;
		this.position = (Integer) first;
		this.classType = "Terceto";
		this.operator = "simple";
		this.use = "UNDEFINIED";
	}

	
	@Override
	public String getOperando() {
		// TODO Auto-generated method stub
		return "_"+first;
	}


	@Override
	public String toString(){
		return "(["+((Integer) first).intValue()+"])";
	};
	
	@Override
	public String getLexema() {
		// TODO Auto-generated method stub
		return "["+String.valueOf(this.position)+"]";
	}

	@Override
	public String getAssembler() {
		String codigo = "MOV "+this.aux+", "+this.first+"\n";
		//System.out.println("==========================================getAssembler  terceto"+this);
		return codigo;
	}
	
	public void setUse(String use){
		this.use = use;
	}
	
}
