
package structures;

public class TercetoAsignacion extends Terceto{

	public TercetoAsignacion(Object first, Object second){
		this.operator = ":=";
		this.first    = first;
		this.second   = second;
		this.position = 0;
		this.classType = "Terceto";
		System.out.println("terceto asignacion created");
		System.out.println("first"+this.first);
		System.out.println("second"+this.second);
	}



	@Override
	public void setPosition(int position) {
		System.out.println("position seteada en tereceto"+position);
		this.position = position;
	}



	@Override
	public void setUse(String use) {
		// TODO Auto-generated method stub
		super.setUse(use);
	}



	@Override
	public String getLexema() {
		// TODO Auto-generated method stub

		return "["+String.valueOf(this.position)+"]";
	}



	@Override
	public void setTypeVariable(String typeVariable) {
		// TODO Auto-generated method stub
		System.out.println("type variable seteado"+typeVariable);
		this.typeVariable=typeVariable;
	}

	@Override
	public String toString() {
		return  "("+operator+","+((Element) first)+","+((Element)second)+")";
	}

	@Override
	public String getAssembler() {
		System.out.println("=== TERCETO ASIGNACION getAssembler() ===");
		Element variable = (Element) this.first; 
		Element expresion = (Element) this.second;
		System.out.println("terceto "+variable+" := "+expresion+" variable auxiliar must be null:"+typeVariable);
		String codigo = "; asignacion "+variable+" := "+expresion+"\n";
		//		if (this.typeVariable == null) {this.typeVariable="integer"; System.out.println("tipo cambiado");}



		if (this.typeVariable.equals("integer")){
			if ( expresion.getOperator().equals(">^") && (variable.getOperator().equals(">^")) ){

				codigo += "; asignacion entera entre matrices\n";
				codigo += "MOV EAX, "+expresion.getOperando()+"\n";
				codigo += "MOV DX, [EAX]\n";
				codigo += "PrintDec EAX, \"direccion de la"+expresion.getOperando()+" expresion matriz\"\n";
				codigo += "PrintDec DX\n";
				codigo += "MOV EAX, "+variable.getOperando()+"\n";
				codigo += "MOV [EAX], DX\n";
				codigo += "MOV DX, [EAX]\n";
				codigo += "MOV _nourriturre, EDX\n";
				codigo += "PrintDec _nourriturre\n";
//				codigo += "PrintDec "+expresion.getOperando()+", \"direccion de la"+expresion.getOperando()+" expresion matriz\"\n";
//				codigo += "PrintDec "+variable.getOperando()+", \"direccion de la"+variable.getOperando()+" expresion matriz\"\n";
			}else
			if (expresion.getOperator().equals(">^")){

				codigo += "MOV EAX, "+expresion.getOperando()+"\n";
//				codigo += "MOV BX, [EAX]\n";
				codigo += "MOV DX, [EAX]\n";
//				codigo += "MOV EBX, " +  "dword ptr ["+expresion.getOperando()+"]"+"\n"; //o aca habria que cargarlo a BX por ser enteros de 2 bytes
				codigo += "MOV "+variable.getOperando()+", DX\n";//aca puede ser que sea EBX en lugar de BX
			}else 
				if (variable.getOperator().equals(">^")){
//					codigo += "sentencias assembler para elementos de matriz ";
					codigo += "MOV BX, " + expresion.getOperando() + "\n";
					codigo += "MOV EAX, "+variable.getOperando()+"\n";
					codigo += "MOV [EAX], BX\n";
					
				}else{
					codigo += "MOV BX, " + expresion.getOperando() + "\n";
					codigo += "MOV " + variable.getOperando() + ", BX\n";
				}
		}	 
		else{ // if this.typeVariable.equals("integer") => rama por float
			if ( expresion.getOperator().equals(">^") && (variable.getOperator().equals(">^")) ){

				codigo += "MOV EDX, dword ptr ["+expresion.getOperando()+"]\n";
				codigo += "MOV EBX, [EDX]\n";
				codigo += "MOV _nourriturre, EBX\n";
				codigo += "FLD _nourriturre\n";
				codigo += "FSTP dword ptr ["+variable.getOperando()+"]\n";
				codigo += "MOV EDX, dword ptr ["+variable.getOperando()+"]\n";
				codigo += "MOV [EBX], EDX\n";// revisar esta parte de la asignacion

			}else
				if (expresion.getOperator().equals(">^")){
				System.out.println("sentencias float para matrices") ;
				codigo += "MOV EDX, dword ptr ["+expresion.getOperando()+"]\n";
				codigo += "MOV EBX, [EDX]\n";
				codigo += "MOV _nourriturre, EBX\n";
				codigo += "FLD _nourriturre\n";
				codigo += "FSTP "+variable.getOperando()+"\n";
			}else
				if (variable.getOperator().equals(">^")){
					codigo += "FLD "+expresion.getOperando()+"\n";
					codigo += "FSTP dword ptr ["+variable.getOperando()+"]\n";
					codigo += "MOV EDX, dword ptr ["+variable.getOperando()+"]\n";
					codigo += "MOV [EBX], EDX\n";
					
				}
			else{
				codigo += "FLD " + expresion.getOperando() + "\n";
				codigo += "FSTP " + variable.getOperando() + "\n";
			}
		}

		return codigo+"PrintDec "+variable.getOperando()+", \""+variable.getOperando()+"\" \n";
	}



}

/**
 * 
 * TODO: Acomodar IF (terceto) para que quede mas comprensible, yno se repita tanto codigo
 * 
 * posible problema: cuando vengan 2 elementos que sean 1 terceto y 1 token...
 * 
 */
