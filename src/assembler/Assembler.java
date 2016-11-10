package assembler;

import java.util.ArrayList;
import java.util.HashMap;

import lexicalAnalyzer.SymbolTable;
import parser.Parser;
import structures.Terceto;
import structures.Token;

public class Assembler {

    private ArrayList<Terceto> listaTerceto;
    private SymbolTable symbolTable;
    public static HashMap<Token, String> constantes = new HashMap<Token, String>();
    public static int numConst = 0;

    public Assembler() {

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

    private String getDeclaraciones() {
        String declaracion = new String();
        declaracion += ".data\n";
        declaracion += "__MIN DD  1.17549435e-38\n";
        declaracion += "__MAX DD  3.40282347e38\n";
        declaracion += "_msjDC DB \"Error: Division por cero\", 0\n";

        int numCad = 0;
        for (Token t : symbolTable.getTS()) {
            if (t.getTipo().equals(Token.Tipo_Token.Identificador)) {
                if (symbolTable.getTipo(t).equals("entero")) {
                    declaracion += t.getAssebler() + " DW ?\n"; // entero = 2 bytes
                } else {
                    declaracion += t.getAssebler() + " DD ?\n"; //  flotantes = 8 bytes
                }
            } else if (t.getTipo().equals(Token.Tipo_Token.Cadena)) { /// ver si lo tenemos en cuenta 
                //Esta bien renombrar las cadenas asi para su identificacion en el assembler? es con DB como el ejemplo de asmhello?
                String nombre = "Cadena" + numCad;
                t.setNomCad(nombre);
                declaracion += t.getAssebler() + " DB " + t.getLexema() + ", 0\n";
                numCad++;
            }
        }
        
        return declaracion;
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
        instrucciones += getcontrolindex();
        instrucciones += getDivCero();
        instrucciones += "start:\n";
        for (Terceto t : listaTerceto) {
            instrucciones += t.getAssebler();
        }

        Assemblercode += getDeclaraciones(); // Va despues de generar las intrucciones porque se incluyen las @aux# en la TS
        Assemblercode += instrucciones;
        Assemblercode += "invoke ExitProcess, 0\n";
        Assemblercode += "end start";
        return codigoAssembler;
    }
}
