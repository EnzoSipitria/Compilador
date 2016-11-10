package matrix;

public abstract class Matrix {

	protected final int FINAL_STATE = -1;
	protected final int DEFAULT = 0;
	
    protected int rows=22;   //Cantidad de filas
    protected int columns=29; //Cantidad de columnas
    protected Object [][] matrix;
    public abstract void inicialize();
    
    
    public Object getElem(int f, int c){
    	return this.matrix [f][c];
    }
    
    public void viewMatrix(){
    	System.out.println("------------------MATRIZ-------------");
    	for (int i = 0; i < rows; i++) {
    		System.out.print(i+" -- ");
			for (int j = 0; j < columns; j++) {
				System.out.print(matrix[i][j]+" | ");
			}
			System.out.println("\n");
		}
    }
}
