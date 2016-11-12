package structures;

public class TercetoReferencia extends Terceto {

    public TercetoReferencia(Object first){
    	this.operator= "&";
    	this.first=first;
    }	
    
    //puede ser que haya que cambiar este método en algun caso
    public String toString(){
		System.out.println("Terceto Referencia");
		return "("+operator+","+first+")";
	};
	
    
    
	public String getLexema() {
		// TODO Auto-generated method stub
		return "["+String.valueOf(this.position)+"]";
	}

	@Override
	public String getAssembler() {
		// TODO Auto-generated method stub
		return null;
	}

}
