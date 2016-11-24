package structures;

public class TercetoComparador extends Terceto{

	public TercetoComparador(Object comparator, Object first, Object second){
		this.operator = ((Element) comparator).getLexema();
		this.first    = first;
		this.second   = second;
		this.position = 0;
		this.classType = "Terceto";
	}

	@Override
	public String getLexema() {
		// TODO Auto-generated method stub
		return "["+String.valueOf(this.position)+"]";
	}

	@Override
	public String getAssembler() {
		String Codigo= "";
		Element op1 = (Element) this.first;
		Element op2 = (Element) this.second;
		//        Token aux = new Token("Vacio","");
		if ((op1.getClassType().equals("Terceto")) && (op2.getClassType().equals("Terceto"))){
			boolean Arreglo1 = (((Element)op1).getOperando().equals(">^"));
			boolean Arreglo2 = (((Element)op2).getOperando().equals(">^"));
			if ((Arreglo1) && (Arreglo2)){ //CONTROLO SI LOS TERCETOS SON ARREGLOS
				String ope1 =((Element)this.first).getOperando();
				String ope2 =((Element)this.second).getOperando();
				//          StringCodigo="";
				//         System.out.println("==========================================getAssembler  terceto"+this);
				//          System.out.println("Tipo del terceto comparador: "+this.typeVariable);
				if (this.typeVariable.equals("integer")) {
					Codigo = "MOV CX, " + "dword ptr ["+((Terceto) op2).getAux()+"]" + "\n";
					Codigo += "CMP " + "dword ptr ["+((Terceto) op1).getAux()+"]" + ", CX\n";
				} else {
					Codigo = "FLD " + "dword ptr ["+((Terceto) op1).getAux()+"]" + "\n";
					Codigo += "FLD " + "dword ptr ["+((Terceto) op2).getAux()+"]" + "\n";
					Codigo += "FCOM \n";
					Codigo += "FSTSW AX \n";//paso los valores del copro al proc
					Codigo += "SAHF \n";//cargo los valores
				}


			} else  if (Arreglo1){
				String ope1 =((Element)this.first).getOperando();
				String ope2 =((Element)this.second).getOperando();
				//   StringCodigo="";
				System.out.println("==========================================getAssembler  terceto"+this);
				System.out.println("Tipo del terceto comparador: "+this.typeVariable);
				if (this.typeVariable.equals("integer")) {
					Codigo = "MOV CX, " + ope2 + "\n";
					Codigo += "CMP " + "dword ptr ["+((Terceto) op1).getAux()+"]" + ", CX\n";
				} else {
					Codigo = "FLD " + "dword ptr ["+((Terceto) op1).getAux()+"]" + "\n";
					Codigo += "FLD " + ope2 + "\n";
					Codigo += "FCOM \n";
					Codigo += "FSTSW AX \n";//paso los valores del copro al proc
					Codigo += "SAHF \n";//cargo los valores
				}

			}



			else if (Arreglo2)  { 
				String ope1 =((Element)this.first).getOperando();
				String ope2 =((Element)this.second).getOperando();
				// StringCodigo="";
				System.out.println("==========================================getAssembler  terceto"+this);
				System.out.println("Tipo del terceto comparador: "+this.typeVariable);
				if (this.typeVariable.equals("integer")) {
					Codigo = "MOV CX, " + "dword ptr ["+((Terceto) op2).getAux()+"]" + "\n";
					Codigo += "CMP " + ope1 + ", CX\n";
				} else {
					Codigo = "FLD " + ope1 + "\n";
					Codigo += "FLD " + "dword ptr ["+((Terceto) op2).getAux()+"]" + "\n";
					Codigo += "FCOM \n";
					Codigo += "FSTSW AX \n";//paso los valores del copro al proc
					Codigo += "SAHF \n";//cargo los valores
				}

			}

		}else {
			String ope1 =((Element)this.first).getOperando();
			String ope2 =((Element)this.second).getOperando();
			if (this.typeVariable.equals("integer")) {
				Codigo = "MOV CX, " + ope2 + "\n";
				Codigo += "CMP " + ope1 + ", CX\n";
			} else {
				Codigo = "FLD " + ope1 + "\n";
				Codigo += "FLD " + ope2 + "\n";
				Codigo += "FCOM \n";
				Codigo += "FSTSW AX \n";//paso los valores del copro al proc
				Codigo += "SAHF \n";//cargo los valores
			}
		}

		return Codigo;
	}







}
