package matrix;

import semanticalActions.SemAction1;
import semanticalActions.SemAction10;
//import semanticalActions.SemAction11;
import semanticalActions.SemAction2;
import semanticalActions.SemAction3;
import semanticalActions.SemAction4;
import semanticalActions.SemAction5;
import semanticalActions.SemAction6;
import semanticalActions.SemAction7;
import semanticalActions.SemAction8;
import semanticalActions.SemAction9;
import semanticalActions.SemActionError1;
import semanticalActions.SemActionError2;
import semanticalActions.SemActionError3;
import semanticalActions.SemActionError4;
//import semanticalActions.SemActionError4;
import semanticalActions.SemanticalAction;

public class SemanticalActionMatrix extends Matrix {

	public SemanticalActionMatrix() {
		matrix = new SemanticalAction[rows][columns];
	}

	@Override
	public void inicialize() {
		SemanticalAction semAction1 = new SemAction1();
		SemanticalAction semAction2 = new SemAction2();
		SemanticalAction semAction3  = new SemAction3();
		SemanticalAction semAction4 = new SemAction4();
		SemanticalAction semAction5 = new SemAction5();
		SemanticalAction semAction6 = new SemAction6();
		SemanticalAction semAction7 = new SemAction7();
		SemanticalAction semAction8 = new SemAction8();
		SemanticalAction semAction9 = new SemAction9();
		SemanticalAction semAction10= new SemAction10();
		SemanticalAction semActionError1 = new SemActionError1();
		SemanticalAction semActionError2 = new SemActionError2();
		SemanticalAction semActionError3 = new SemActionError3();
		SemanticalAction semActionError4 = new SemActionError4();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (i == 19 || i == 20) {
					matrix[i][j] = semAction1;
				}
				// inicializamos la fila 17 con as7 antes estaba con as1
				if (i == 16 || i == 18 || i == 17) {
					matrix[i][j] = semAction7;
				}
				if (i == 21 ) {
					matrix[i][j] = semAction10;
				}
				if (i == 7 || i == 8 || i == 9 || i == 10 || i == 12) {
					matrix[i][j] = semActionError3;
				}
				if (i == 3 || i == 5 || i == 6 || i == 15) {
					matrix[i][j] = semActionError2;
				}
				if (i == 2 || i == 4) {
					matrix[i][j] = semAction3;
				}
				if (i == 11 || i == 13) {
					matrix[i][j] = semAction6;
				}
				if ( i == 1 ) {
					matrix[i][j] = semAction2;
				}
			}
		}

		matrix[1][28]=semAction2;matrix[2][28]=semAction3;matrix[3][28]=semAction7;matrix[4][28]=semAction3;
		matrix[5][28]=semAction7; matrix[6][28]=semAction7; matrix[7][28]=semAction7;matrix[8][28]=semAction7;matrix[9][28]=semAction7; matrix[10][28]=semAction7;matrix[11][28]=semAction6;
		matrix[12][28]=semAction7;matrix[13][28]=semAction6;matrix[15][28]=semActionError2;
		matrix[17][28]=semAction1;matrix[18][28]=semAction1;matrix[19][28]=semAction7;matrix[20][28]=semAction1;matrix[21][28]=semAction7;


		matrix[0][0]=semAction1; matrix[0][1]=semActionError2;matrix[0][2]=semAction1;matrix[0][3]=semAction1;matrix[0][4]=semAction3;matrix[0][5]=semAction3;
		matrix[0][6]=semActionError4; matrix[0][7]=semAction3; matrix[0][8]=semAction1;matrix[0][9]=semAction1;matrix[0][10]=semAction1;matrix[0][11]=semActionError2; matrix[0][12]=semAction1;matrix[0][13]=semAction1;
		matrix[0][14]=semAction7;matrix[0][15]=semAction3;matrix[0][16]=semAction3;matrix[0][17]=semAction3;matrix[0][18]=semAction3;
		matrix[0][19]=semAction3;matrix[0][20]=semActionError2;matrix[0][21]=semAction1;matrix[0][22]=semAction1;matrix[0][23]=semAction1;matrix[0][24]=semAction1;matrix[0][25]=semAction3;
		matrix[0][26]=semAction3;matrix[0][27]=semActionError2;matrix[0][28]=semAction7;

		matrix[1][0]=semAction1; matrix[1][1]=semAction1; matrix[1][2]=semAction1; matrix[1][22]=semAction1;matrix[1][23]=semAction1;matrix[1][24]=semAction1;matrix[1][26]=semAction2;matrix[1][27]=semAction1;
		matrix[2][3]=semAction4;matrix[2][6]=semActionError2;
		matrix[3][7]=semAction5;
		matrix[4][7]=semAction5;matrix[4][6]=semActionError2;
		matrix[5][7]=semAction5; 
		matrix[6][23]=semAction1; matrix[6][24]=semAction1;
		matrix[7][1]=semAction1;matrix[7][3]=semAction1;matrix[7][20]=semAction1;matrix[7][27]=semAction1;
		matrix[8][1]=semAction1;matrix[8][20]=semAction1;matrix[8][27]=semAction1;
		matrix[9][1]=semAction1;matrix[9][20]=semAction1;matrix[9][27]=semAction1;
		matrix[10][1]=semAction1;matrix[10][27]=semAction1;
		matrix[11][1]=semAction1;/*matrix[11][6]=semActionError3;*/matrix[11][22]=semAction1;matrix[11][27]=semAction1;
		matrix[12][1]=semAction1;matrix[12][3]=semAction1;matrix[12][4]=semAction1;matrix[12][27]=semAction1;
		matrix[13][1]=semAction1;/*matrix[13][6]=semActionError3;*/matrix[13][26]=semAction6;matrix[13][27]=semAction1;
		matrix[15][12]=semAction1;
		matrix[16][11]=semAction1;matrix[16][14]=semAction8;
		matrix[17][14]=semAction8;
		matrix[18][14]=semAction8;matrix[18][27]=semAction1;
		matrix[19][14]=semAction8;
		matrix[20][14]=semActionError1;matrix[20][21]=semAction9;
		matrix[21][4]=semAction7;matrix[21][21]=semAction9;
	}
}
