package structures;

public class TokenMatrix extends Token {

	private int indexStart;
	private int rows;
	private int columns;
	
	public TokenMatrix(String type, int lineNumber, String lexema, int indexStart, int rows, int columns, Object value) {
		super(type, lexema, lineNumber, value);
		//this.value = value;
		this.indexStart = indexStart;
		this.rows = rows;
		this.columns = columns;
		
	}



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

	
}
