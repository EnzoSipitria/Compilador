package structures;

import javax.swing.text.Element;

public class TercetoReferencia extends Terceto {

    public TercetoReferencia(Object first, Object second){
    	this.operator= ">^";
    	this.first=first;
    	this.second = second;
    	this.classType = "Terceto";
    }	
    
    //puede ser que haya que cambiar este método en algun caso
    public String toString(){
		//System.out.println("Terceto Referencia");
		return "("+operator+","+((Terceto) first).getLexema()+","+((Token)second).getLexema()+")";
	};
	
    
    
	public String getLexema() {
		// TODO Auto-generated method stub
		return "["+String.valueOf(this.position)+"]";
	}

	@Override
	public String getAssembler() {
		System.out.println("===terceto referencia===");
		System.out.println("====terceto"+this);
        String Codigo = "";
        structures.Element op1 = (structures.Element) this.first;//suma
        Token op2 = (Token) this.second;//token matrix
        Codigo+= "MOV "+"EAX"+", OFFSET "+((Token) op2).getAssembler()+"\n";
        Codigo+= "ADD "+"AX"+", "+((structures.Element) op1).getOperando()+"\n";
        Codigo+= "MOV "+ getAux() + ", AX"+"\n";
        return Codigo;
	}

}
