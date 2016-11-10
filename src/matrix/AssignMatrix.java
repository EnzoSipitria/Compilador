package matrix;

import java.util.HashMap;

public class AssignMatrix extends Matrix {
	
	private int rows = 2;
	private int columns = 2;
	
	
	private HashMap<String,Integer> typesList = new HashMap<String,Integer>();

	public AssignMatrix() {
		matrix = new String[rows][columns];
		inicialize();
	}
	
	
	/**
	 * incializacion con conversiones implicitas de los tipos básicos del lenguaje
	 * 
	 * filas (tipo termino izquierdo de asignacion) 0 = int
	 * 		 										1 = float
	 * 
	 *  columnas tipo termino derecho de asignacion)0 = int
	 *  		 									1 = float
	 */
	@Override
	public void inicialize() {
		typesList.put("integer",0);
		typesList.put("float",1);
		
		for (int k = 0; k < rows ; k++) {
			for (int j = 0; j < columns; j++) {
				matrix[k][j] = null;
			}
		}
		matrix[0][0] = "integer";
		matrix[1][0]= "float";
		matrix[1][1]= "float";
	}
	
	private int findColumn(String type){
		System.out.println("columna de la matriz: "+ typesList.get(type)+" --- "+type);
		return typesList.get(type);
	}
	

	              
	public String getTypeOperation(String left, String right){
		String result = (String) matrix[findColumn(left)][findColumn(right)];
		return (result);
	}
	
	
	public boolean acceptOperation(String left, String right){
		String result = (String) matrix[findColumn(left)][findColumn(right)];
		return (result != null);
	}
	

	/**
	 * proviene de la ejecucion de la sentencia Allow left to right
	 * ALLOW float TO integer = integer
	 * @param left   
	 * @param right
	 */
	public void addConvertion(String left, String right){
		if (matrix[findColumn(left)][findColumn(right)] == null){
			matrix[findColumn(left)][findColumn(right)]=right;
		}			
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
