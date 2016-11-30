package structures;

import interfaz.UI2;

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
		return "("+operator+","+((Element) first).getLexema()+","+((Token)second).getLexema()+")";
	};
	
    public String getLexema() {
		// TODO Auto-generated method stub
		return "["+String.valueOf(this.position)+"]";
	}

	@Override
	public String getAssembler() {
		System.out.println("===terceto referencia===");
		System.out.println("====terceto"+this);
        String codigo = ";referencia\n";
        Element position = (Element) this.first;/// resultado de la busqueda del desplazamiento (suma)
        Element matrix = (Element) this.second;///  token de la matriz
        System.out.println("position: "+position.getOperando());
        int rows = ((Token)matrix).getRows();
        int columns = ((Token)matrix).getColumns();
        int indexStart = ((Token)matrix).getIndexStart();
        int size = ((rows)*(columns));
        if (indexStart == 0){
        	size = ((indexStart+rows+1)*(indexStart+columns+1));
        }
        size = size*2;
        if (matrix.getTypeVariable().equals("float")){
        	size = size*4;
        }
        System.out.println("size referencia"+size);
        codigo+= "MOV "+"EBX"+", OFFSET "+matrix.getAssembler()+"\n";
        codigo+= "ADD "+"BX"+", "+position.getOperando()+"\n";
        codigo+= "PrintDec "+position.getOperando()+"\n";
        codigo+= "MOV AX, "+position.getOperando()+"\n";
        codigo+= "MOV DX, "+size+"\n";// guardo limite del arraay
        codigo+= "CMP DX, AX\n"; // comparacion de rangos 
        codigo+= "JGE _esmenorigual"+number+"\n";// salto por menor igual
        codigo+= " JMP _indexcontrol"+"\n";/// salto por error funcion getindexerror
        codigo+= "_esmenorigual"+number+": MOV "+getAux()+", EBX\n";//sigo con la ejecucion normal 

//        Element op1 = (Element) this.first;//suma
//        Token op2 = (Token) this.second;//token matrix
//        Codigo+= "MOV "+"EAX"+", OFFSET "+((Token) op2).getAssembler()+"\n";
//        Codigo+= "ADD "+"AX"+", "+((structures.Element) op1).getOperando()+"\n";
//        Codigo+= "MOV "+ getAux() + ", AX"+"\n";
        
        UI2.addText(UI2.txtConsole, codigo);
        return codigo;
	}

}
