package structures;

public class TercetoReferencia extends Terceto {

    public TercetoReferencia(Object first, Object second){
    	this.operator= "&";
    	this.first=first;
    	this.second = second;
    }	
    
    //puede ser que haya que cambiar este método en algun caso
    public String toString(){
		System.out.println("Terceto Referencia");
		return "("+operator+","+first+","+second+")";
	};
	
    
    
	public String getLexema() {
		// TODO Auto-generated method stub
		return "["+String.valueOf(this.position)+"]";
	}

	@Override
	public String getAssembler() {
		System.out.println("==========================================getAssembler  terceto"+this);
		return null;
	}

}
