package assembler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import lexicalAnalyzer.SymbolTable;
import parser.Parser;
import structures.AuxGenerator;
import structures.Terceto;
import structures.Token;

public class Assembler {

    private ArrayList<Terceto> listaTerceto;
    private SymbolTable symbolTable;
    public static HashMap<Token, String> constantes = new HashMap<Token, String>();
    public static int numConst = 0;
    private static AuxGenerator generator = new AuxGenerator();
    public Assembler(ArrayList<Terceto> tercetos, SymbolTable symbolTable) {
    	this.listaTerceto = tercetos;
    	this.symbolTable = symbolTable; 

    }
 
    
    public ArrayList<Terceto> getListaTerceto() {
		return listaTerceto;
	}


    public void initAuxGenerator(){
    	generator.initCounter();
    }
    
	public void setListaTerceto(ArrayList<Terceto> listaTerceto) {
		this.listaTerceto = listaTerceto;
	}


	public static String getIdConst(Token t) {
        String nombre = constantes.get(t);
        if (nombre != null) {
            return nombre;
        }
        nombre = "__cte" + numConst;
        constantes.put(t, nombre);
        numConst++;
        return nombre;
    }

    private String getheadlines() {
        String encabezado = new String();
        encabezado += ".386\n"; //ml /c /Zd /coff hellow.asm"
        encabezado += ".model flat, stdcall\n";//Link /SUBSYSTEM:WINDOWS hellow.obj;
        encabezado += "option casemap :none\n";
        encabezado += "include \\masm32\\include\\windows.inc\n";
        encabezado += "include \\masm32\\include\\kernel32.inc\n";
        encabezado += "include \\masm32\\include\\user32.inc\n";
        encabezado += "includelib \\masm32\\lib\\kernel32.lib\n";
        encabezado += "includelib \\masm32\\lib\\user32.lib\n";

        return encabezado;
    }

    private String getDeclarations() {
        String declaration = new String();
        declaration += ".data\n";
        declaration += "__MIN DD  1.17549435e-38\n";
        declaration += "__MAX DD  3.40282347e38\n";
        declaration += "_msjDC DB \"Error: Division por cero\", 0\n";
        Set<String> keys = symbolTable.getTokenList().keySet();
        int numCad = 0;
        for (String key : keys) {
        		Token token= symbolTable.getToken(key);
        		System.out.println("Tipo del token: "+token);
        		if(token.getTypeVariable() != null){
	                if (token.getTypeVariable().equals("integer")) {
//	                	System.out.println("token en get declarations"+token);
//	                	System.out.println("declaration "+token.getAssembler() + " DW ?\n");
	                    declaration += token.getAssembler() + " DW ?\n"; // entero = 2 bytes
	                } else {
	                    declaration += token.getAssembler() + " DD ?\n"; //  flotantes = 8 bytes
	                } 
        	    	System.out.println("<<LEXEMA TOKEN >>"+token.getType()+","+token);
        		}else{
        			System.out.println("El token sin tipo es: "+token);
        		}
        		
	            if (token.getType().equals("CADENA")) {  
	        	    	String name = token.getLexema().substring(1, token.getLexema().length()-1);
	        	    	name = name.replaceAll(" ", "");
	        	    	System.out.println("<<>TENGO LA CADENA: >>"+name);
//	        	    	token.setLexema(name);
	        	    	declaration += name + " DB " + token.getLexema() + ", 0\n";
	        	    	numCad++;
	        	    
        		}
        }
        return declaration;   
    }

    
   private String getcontrolindex(){
   	return null;
   }

    private String getDivCero() {
        String str = new String();
        str += "_division_cero:\n";
        str += "invoke MessageBox, NULL, addr _msjDC, addr _msjDC, MB_OK\n";
        str += "invoke ExitProcess, 0\n";
        return str;
    }

    public String getCodigo() {
        String Assemblercode = new String();
        Assemblercode += getheadlines();
        String instrucciones = new String();

        instrucciones += ".code\n";
//        instrucciones += getcontrolindex();
        instrucciones += getDivCero();
        instrucciones += "start:\n";
        System.out.println("starttttttttttttttttttt");
        for (Terceto terceto : listaTerceto) {
        	System.out.println("====GetCodigo===="+terceto);
        	terceto.setAux("@"+generator.getName());
        	Token token = new Token("IDENTIFICADOR", terceto.getAux(), 0, 0, terceto.getTypeVariable());
        	symbolTable.addToken(terceto.getAux(), token);
            instrucciones += terceto.getAssembler();
        }
        Assemblercode += getDeclarations(); // Va despues de generar las intrucciones porque se incluyen las @aux# en la TS
        Assemblercode += instrucciones;
        Assemblercode += "invoke ExitProcess, 0\n";
        Assemblercode += "end start";
        
        return Assemblercode;
    }



}
