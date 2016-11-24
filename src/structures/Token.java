package structures;

public class Token extends Element{
	
	private String lexema;
	private int lineNumber;
	private String type; // tipo de token IDENTIFICADOR LITERAL ANOTACION etc
	private int indexStart;
	private int rows;
	private int columns;
	

	
	public Token(String type,String lexema, int lineNumber) {
		this.lexema=lexema;
		this.lineNumber=lineNumber;
		this.type=type;
		this.typeVariable=null; 
		this.use=null;
		this.indexStart = 0;
		this.rows = 0;
		this.columns = 0;
		this.classType = "Token";
		this.operator = "T";
		
	}

	public Token(String type,String lexema, int lineNumber,Object value) {
		this.lexema=lexema;
		this.lineNumber=lineNumber;
		this.type=type;
		this.typeVariable=null;
		this.value = value;
		this.use=null;
		this.indexStart = 0;
		this.rows = 0;
		this.columns = 0;
		this.classType = "Token";
		this.operator = "T";
		
	}
	
	public Token(String type,String lexema, int lineNumber,Object value,String typeVariable) {
		this.lexema=lexema;
		this.lineNumber=lineNumber;
		this.type=type;
		this.typeVariable=typeVariable;
		this.value = value;
		this.use=null;
		//System.err.println("constructor de token sin index start");
		this.indexStart = 0;
		this.rows = 0;
		this.columns = 0;
		this.classType = "Token";
		this.operator = "T";
		
	}
	
	public Token(String type, int lineNumber, String lexema, int indexStart, int rows, int columns, Object value) {
		this.lexema=lexema;
		this.lineNumber=lineNumber;
		this.type=type;
		this.value = value;
		//System.out.println("constructor de token"+indexStart);
		this.indexStart = indexStart;
		this.rows = rows;
		this.columns = columns;
		this.classType = "Token";
		this.operator = "T";
		
		
		
	}
	
	
	/**
	 * se agrego el if para controlar que no sean constantes y en ese caso se retorna la constante con un guion bajo delante
	 * 
	 * 
	 */
	public String getAux(){
		return this.aux;
	}
	
	public void setAux(String aux) {
		this.aux=aux;
	}
	
	@Override
	public String getOperando() {
		if (this.getType().equals("INTEGER") || this.getType().equals("FLOAT") ){
		// TODO Auto-generated method stub
		return "_"+String.valueOf(this.value);	
		}
		return "_"+this.lexema;		
	}

	@Override
	public Object getValue() {
		return this.value;
	}

	@Override
	public void setValue(Object value) {
		this.value=value;
	}

	public void setLexema(String lexema) {
		this.lexema = lexema;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	public String getType() {
		return type;
	}
	public String getLexema() {
		return lexema;
	}
	public int getLineNumber() {
		return lineNumber;
	}
		
	@Override
	public String toString() {
		return String.valueOf(lineNumber)+". "+lexema+" - "+type+" - "+typeVariable+" - "+use+" - ope"+operator;
	}

	@Override
	public int getPosition() {
		//Se retorna -1 como valor discernible para indicar que la posicion no tiene nada que ver
		return -1;
	}

	//Esto pertenece los token de matrices
	
	public int getIndexStart() {
		return indexStart;
	}

	public void setIndexStart(int indexStart) {
		this.indexStart = indexStart;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	@Override
	public String getAssembler() {
		System.out.println("==========================================getAssembler  terceto"+this);
        if (this.type.equals("IDENTIFICADOR") || this.type.equals("INTEGER") || this.type.equals("FLOAT") || this.type.equals("CADENA")) {
            if (this.lexema.substring(0, 1).equals("@")) {
                System.out.println("return lexema"+this.lexema);
                return this.lexema;
            }
            System.out.println("return get operando"+getOperando());
            return this.getOperando();
        }
//        if (this.type.equals("integer") || this.type.equals("Float")) {
//            return generadorAssembler.getIdConst(this);
//        }
//        if (this.type.equals("Cadena")) {// no va 
//            return "_" + this.lexema;
//        }
        return "notexistintheproyect";
    }	
	
	
}
