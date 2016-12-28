package structures;

public class TercetoBase extends Terceto {

	private Element rowIndex;
	private Element columnIndex;


	public TercetoBase(Object first){
		this.operator="^";
		this.first=first;
		this.classType = "Terceto";
	}
	
	
	public String toString(){
		return "("+operator+","+((Element) first).getLexema()+")";
	};
	
	public String getLexema() {
		// TODO Auto-generated method stub
		return "["+String.valueOf(this.position)+"]";
	}


	@Override
	public String getAssembler() {
		 Token matrix = (Token) this.first;
		 int rows = ((Token)matrix).getRows();
	     int columns = ((Token)matrix).getColumns();
	     System.out.println(this.first);
		System.out.println("rowIndex: "+rowIndex+" limite: "+rows+" columnsIndex: "+columnIndex+" limite: "+columns);
		String codigo = ";base de matriz\n";
		
		codigo+= "MOV BX , "+rowIndex.getOperando()+"\n";
        codigo+= "MOV DX, "+rows+"\n"; // guardo limite del arraay
        codigo+= "CMP DX, BX\n"; // comparacion de rangos 
        codigo+= "JGE _Fesmenorigual"+number+"\n";// salto por menor 
        codigo+= " JMP _indexcontrol"+"\n";/// salto por error funcion getindexerror
        codigo+= "_Fesmenorigual"+number+":\n";
        codigo+= "MOV BX, "+columnIndex.getOperando()+"\n";
        codigo+= "MOV DX, "+columns+"\n";// guardo limite del arraay
        codigo+= "CMP DX, BX\n"; // comparacion de rangos 
        codigo+= "JGE _Cesmenorigual"+number+"\n";// salto por menor 
        codigo+= " JMP _indexcontrol"+"\n";/// salto por error funcion getindexerror
        codigo+= "_Cesmenorigual"+number+":\n";
		codigo += "MOV "+this.aux+", "+0+"\n";
		//System.out.println("==========================================getAssembler  terceto"+this);
		return codigo;
	}


	public void setRowColumnIndex(Element rowIndex, Element columnIndex) {
		// TODO Auto-generated method stub
		this.rowIndex=rowIndex;
		this.columnIndex=columnIndex;
	}
}
