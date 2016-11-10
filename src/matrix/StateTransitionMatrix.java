package matrix;


public class StateTransitionMatrix extends Matrix {




	public StateTransitionMatrix() {
		matrix = new Integer[rows][columns];
	}


	@Override
	public void inicialize() {

		//carga toda la matriz con el valor FINAL_STATE
		for (int k = 0; k < rows ; k++) {
			for (int j = 0; j < columns; j++) {
				matrix[k][j] = FINAL_STATE;
			}


			for (int i = 0; i < rows ; i++) {
				for (int j = 0; j < columns; j++) {
					if (i == 16 || i == 17 || i == 18) {
						matrix[i][j] = 17;
					}
					if (i == 20 || i == 21) {
						matrix[i][j] = 20;
					}
					if (i == 19) {
						matrix[i][j] = 19;
					}
				}
			}

			matrix[0][0]=1; matrix[0][2]=6;matrix[0][3]=2;matrix[0][4]=FINAL_STATE;matrix[0][5]=FINAL_STATE;
			matrix[0][7]=FINAL_STATE; matrix[0][8]=4;matrix[0][9]=4;matrix[0][10]=5; matrix[0][12]=15;matrix[0][13]=3;
			matrix[0][14]=FINAL_STATE;matrix[0][15]=FINAL_STATE;matrix[0][16]=FINAL_STATE;matrix[0][17]=FINAL_STATE;matrix[0][18]=FINAL_STATE;
			matrix[0][19]=FINAL_STATE;matrix[0][21]=20;matrix[0][22]=1;matrix[0][23]=1;matrix[0][24]=1;matrix[0][25]=FINAL_STATE;			


			matrix[0][28]=0; matrix[1][28]=FINAL_STATE;matrix[2][28]=FINAL_STATE;matrix[3][28]=3;matrix[4][28]=FINAL_STATE;
			matrix[5][28]=5; matrix[6][28]=6; matrix[7][28]=7;matrix[8][28]=8;matrix[9][28]=9; matrix[10][28]=10;matrix[11][28]=FINAL_STATE;
			matrix[12][28]=12;matrix[13][28]=FINAL_STATE;matrix[14][28]=FINAL_STATE;matrix[15][28]=FINAL_STATE;matrix[16][28]=17;
			matrix[17][28]=17;matrix[18][28]=17;matrix[20][28]=20;matrix[21][28]=21;

			matrix[0][1]=0;matrix[0][11]=0;matrix[0][20]=0;matrix[0][26]=FINAL_STATE;matrix[0][27]=0;

			matrix[1][0]=1; matrix[1][1]=1;matrix[1][2]=1;matrix[1][22]=1;matrix[1][23]=1;matrix[1][24]=1;matrix[1][27]=1;
			matrix[3][7]=FINAL_STATE;
			matrix[5][7]=FINAL_STATE; 
			matrix[6][23]=7; matrix[6][24]=12;
			matrix[7][1]=8;matrix[7][3]=9;matrix[7][20]=10;matrix[7][27]=8;
			matrix[8][1]=8;matrix[8][20]=11;matrix[8][27]=8;
			matrix[9][1]=8;matrix[9][20]=10;matrix[9][27]=8;
			matrix[10][1]=11;matrix[10][27]=11;
			matrix[11][1]=11;matrix[11][22]=12;matrix[11][27]=11;
			matrix[12][1]=13;matrix[12][3]=13;matrix[12][4]=13;matrix[12][27]=13;
			matrix[12][14]=FINAL_STATE;
			matrix[13][14]=FINAL_STATE;
			matrix[13][1]=13;matrix[13][27]=13;matrix[13][28]=13;
			matrix[15][12]=16;
			matrix[16][11]=18;matrix[16][14]=0;
			matrix[17][14]=0;
			matrix[18][14]=0;matrix[18][27]=19;
			matrix[19][14]=FINAL_STATE;
			matrix[20][4]=21;matrix[20][14]=FINAL_STATE;matrix[20][21]=FINAL_STATE;
			matrix[21][21]=FINAL_STATE;
		}
	}
}
