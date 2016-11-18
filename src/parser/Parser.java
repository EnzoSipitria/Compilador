//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";



package parser;



//#line 3 "gramaticat.y"

import lexicalAnalyzer.LexicalAnalyzer;
import structures.Element;
import structures.Terceto;
import structures.TercetoAsignacion;
import structures.TercetoComparador;
import structures.TercetoConversion;
import structures.TercetoLabel;
import structures.TercetoDivision;
import structures.TercetoPrint;
import matrix.AssignMatrix;
import matrix.DivisionMatrix;
import matrix.OperationMatrix;
import matrix.TercetoMatrix;
import structures.TercetoMultiplicacion;
import structures.TercetoDecremento;
import structures.TercetoResta;
import structures.TercetoSuma;
import structures.Token;
import structures.TokenMatrix;
import interfaz.UI2;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Stack;
import structures.TercetoBFalse;
import structures.TercetoBInconditional;
import structures.TercetoSimple;
import structures.TercetoBase;
import structures.TercetoReferencia;
import structures.AuxGenerator;


//#line 50 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short COMENTARIO=257;
public final static short IDENTIFICADOR=258;
public final static short PALABRARESERVADA=259;
public final static short MENORIGUAL=260;
public final static short MAYORIGUAL=261;
public final static short DISTINTO=262;
public final static short ASIGNACION=263;
public final static short DECREMENTO=264;
public final static short CADENA=265;
public final static short FLOAT=266;
public final static short INTEGER=267;
public final static short IF=268;
public final static short ENDIF=269;
public final static short FOR=270;
public final static short PRINT=271;
public final static short CTEINTEGER=272;
public final static short CTEFLOAT=273;
public final static short MATRIX=274;
public final static short ANOT0=275;
public final static short ANOT1=276;
public final static short ALLOW=277;
public final static short TO=278;
public final static short ELSE=279;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    1,    2,    2,    2,    2,    2,    2,
    6,    5,    5,    5,    5,    9,    9,    8,    8,   11,
   11,   11,   12,   12,    4,    7,    7,    7,   14,   14,
   14,   18,   15,   15,   19,   17,   20,   21,   17,   22,
   22,   23,   24,   28,   28,   28,   28,   28,   28,   28,
   29,   29,   29,   16,   16,   16,    3,   30,   30,   31,
   31,   26,   26,   26,   32,   32,   32,   32,   33,   33,
   13,   13,   25,   25,   34,   27,   27,   27,   27,   27,
   27,   10,
};
final static short yylen[] = {                            2,
    2,    2,    1,    1,    1,    2,    2,    1,    1,    2,
    5,   10,   11,   10,    4,    5,    6,    1,    1,    2,
    3,    2,    1,    3,    3,    1,    1,    1,    1,    3,
    1,    0,    4,    2,    0,    4,    0,    0,    7,    8,
    4,    4,    4,    1,    1,    1,    3,    5,    3,    5,
    4,    4,    3,    5,    3,    4,    1,    3,    1,    2,
    1,    1,    3,    3,    1,    2,    3,    3,    1,    1,
    1,    1,    1,    1,    7,    1,    1,    1,    1,    1,
    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,   28,    0,   27,   26,    0,    0,    0,    0,
    0,    1,    0,    4,    5,    8,    9,    0,   44,   45,
    0,   59,   46,   57,   74,    0,   34,    0,   32,    0,
    0,    0,    0,    0,    0,    2,    6,    7,   10,   31,
    0,    0,    0,    0,   82,    0,    0,   71,   72,   69,
   70,    0,    0,   65,    0,    0,    0,    0,    0,    0,
   49,    0,    0,   60,   58,    0,    0,    0,   25,    0,
   47,   53,    0,    0,    0,    0,   66,    0,    0,   55,
   78,   79,   80,   76,   77,   81,    0,    0,   33,   41,
    0,    0,    0,    0,    0,   30,   15,    0,   52,   51,
    0,    0,    0,   67,   68,   56,    0,   35,    0,    0,
    0,    0,   50,   48,   11,    0,    0,   54,    0,    0,
   42,    0,    0,    0,   75,   36,    0,    0,   43,    0,
   38,   40,    0,    0,    0,   39,    0,   14,    0,   18,
   19,   13,    0,    0,    0,    0,   23,   22,    0,    0,
   20,    0,    0,   21,   24,   17,
};
final static short yydgoto[] = {                          2,
   12,   13,   14,   15,   16,   17,   18,  142,  138,   47,
  145,  146,   50,   43,   19,   29,   89,   57,  119,  109,
  134,   20,   60,   92,   51,   52,   87,   22,   23,   24,
   35,   53,   54,   25,
};
final static short yysindex[] = {                      -208,
 -104,    0,    0,  -35,    0,    0,  -40,   20,  -25, -139,
 -135,    0, -104,    0,    0,    0,    0, -203,    0,    0,
 -117,    0,    0,    0,    0, -154,    0, -189,    0, -178,
   26, -173, -141, -135,   19,    0,    0,    0,    0,    0,
   56, -143,   89, -154,    0,   97, -179,    0,    0,    0,
    0,    2,  -37,    0,  117,  -19,  -87,  124,  -95, -154,
    0,  128, -139,    0,    0, -124,  111,   81,    0,   62,
    0,    0,   63,   83, -154, -154,    0, -154, -154,    0,
    0,    0,    0,    0,    0,    0, -170,  -93,    0,    0,
 -154,  -83,  -19,  -47,  121,    0,    0,  -90,    0,    0,
 -154,  -37,  -37,    0,    0,    0,  114,    0,  -92,   66,
  -78, -154,    0,    0,    0,   95,    6,    0,  130,  -87,
    0,  149,   67,  100,    0,    0,  -77,  -87,    0,  -79,
    0,    0,  101,  136,  -52,    0, -236,    0,   73,    0,
    0,    0, -175,  138,  -94,   15,    0,    0,  139,   28,
    0, -130, -236,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,  -39,    0,    0,    0,    0,    0,    0,
    0,    0,  199,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   76,    0,    0,    0,    0,    0,    0,
  141,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -32,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -76,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -27,    3,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    1,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   17,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,   25,  189,  191,  192,   27,   53,    0,  -34,
    0,   65,  -14,  142,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   57,   29,  119,   41,    0,    0,
  173,   75,   82,    0,
};
final static int YYTABLESIZE=294;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         28,
   12,   73,   73,   73,   78,   73,  137,   73,   62,   79,
   62,  114,   62,   63,   32,   63,   16,   63,   11,   73,
   73,   73,   73,   75,   91,   76,   62,   62,   62,   62,
  149,   63,   63,   63,   63,   11,   33,   36,  140,  141,
   85,   86,   84,   64,   75,   64,   76,   64,   75,    1,
   76,   34,   40,   73,   41,   26,   56,   21,  152,   30,
   62,   64,   64,   64,   64,   63,   55,   21,    4,   21,
   42,  152,   70,  151,   34,   73,   72,   58,    4,   59,
  144,   88,   48,   49,   61,  106,  154,    4,   93,   95,
   21,   62,   48,   49,   74,   64,   48,   49,  125,   66,
  139,   48,   49,    4,   75,   75,   76,   76,   75,   75,
   76,   76,   67,   21,   68,  107,    3,   48,   49,  110,
   99,  100,    4,   12,  121,  129,    5,    6,  147,  117,
  147,   40,    7,   41,    8,    9,   63,  155,   44,   16,
  123,   48,   49,   65,  127,   45,   46,   69,  111,  102,
  103,    3,  132,    4,  118,   71,   75,   80,   76,  104,
  105,    5,    6,    7,   90,    8,    9,   45,   94,   97,
    4,   98,   10,  101,    4,  108,   21,   48,   49,  115,
    7,  116,    8,    9,   21,  122,  120,  124,  126,  128,
  130,  131,  133,  135,  136,  143,  148,  153,    3,   29,
   61,   37,   37,   38,   39,  156,   64,   96,  113,  150,
   45,  112,    0,    0,    0,   27,   73,    0,    0,    0,
   73,   73,   73,   73,   73,    0,   77,   62,   62,   62,
   31,    0,   63,   63,   63,    0,    0,    0,    0,    0,
   81,   82,   83,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   12,    0,   12,    0,
    0,    0,   64,   64,   64,    0,   12,   12,   12,    0,
   12,   12,   16,    0,   16,    0,    0,   12,    0,    0,
    0,    0,   16,   16,   16,    0,   16,   16,    0,    0,
    0,    0,    0,   16,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   41,   42,   43,   42,   45,   59,   47,   41,   47,
   43,   59,   45,   41,   40,   43,    0,   45,  123,   59,
   60,   61,   62,   43,   59,   45,   59,   60,   61,   62,
  125,   59,   60,   61,   62,  123,   10,   13,  275,  276,
   60,   61,   62,   41,   43,   43,   45,   45,   43,  258,
   45,   11,  256,   93,  258,   91,   28,    1,   44,   40,
   93,   59,   60,   61,   62,   93,  256,   11,  258,   13,
  274,   44,   44,   59,   34,   47,  256,  256,  258,  258,
  256,   57,  272,  273,   59,  256,   59,  258,   60,   63,
   34,  265,  272,  273,   93,   93,  272,  273,   93,   44,
  135,  272,  273,  258,   43,   43,   45,   45,   43,   43,
   45,   45,  256,   57,  258,   87,  256,  272,  273,   91,
   59,   59,  258,  123,   59,   59,  266,  267,  143,  101,
  145,  256,  268,  258,  270,  271,  278,  152,  256,  123,
  112,  272,  273,  125,  120,  263,  264,   59,   92,   75,
   76,  256,  128,  258,   41,   59,   43,   41,   45,   78,
   79,  266,  267,  268,   41,  270,  271,  263,   41,   59,
  258,   91,  277,   91,  258,  269,  120,  272,  273,   59,
  268,  272,  270,  271,  128,  264,  279,   93,   59,   41,
   91,  269,  272,   93,   59,  123,   59,   59,    0,   59,
  125,   13,  279,   13,   13,  153,   34,   66,  256,  145,
  263,   93,   -1,   -1,   -1,  256,  256,   -1,   -1,   -1,
  260,  261,  262,  263,  264,   -1,  264,  260,  261,  262,
  256,   -1,  260,  261,  262,   -1,   -1,   -1,   -1,   -1,
  260,  261,  262,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  256,   -1,  258,   -1,
   -1,   -1,  260,  261,  262,   -1,  266,  267,  268,   -1,
  270,  271,  256,   -1,  258,   -1,   -1,  277,   -1,   -1,
   -1,   -1,  266,  267,  268,   -1,  270,  271,   -1,   -1,
   -1,   -1,   -1,  277,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=279;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,"COMENTARIO","IDENTIFICADOR",
"PALABRARESERVADA","MENORIGUAL","MAYORIGUAL","DISTINTO","ASIGNACION",
"DECREMENTO","CADENA","FLOAT","INTEGER","IF","ENDIF","FOR","PRINT","CTEINTEGER",
"CTEFLOAT","MATRIX","ANOT0","ANOT1","ALLOW","TO","ELSE",
};
final static String yyrule[] = {
"$accept : inicio",
"inicio : IDENTIFICADOR programa",
"programa : sentencia_declarativa bloque_sentencias",
"programa : sentencia_declarativa",
"programa : bloque_sentencias",
"sentencia_declarativa : sentencia_declarativa_datos",
"sentencia_declarativa : sentencia_declarativa sentencia_declarativa_datos",
"sentencia_declarativa : sentencia_declarativa sentencia_declarativa_matrix",
"sentencia_declarativa : sentencia_declarativa_matrix",
"sentencia_declarativa : sentencia_declarativa_conversion",
"sentencia_declarativa : sentencia_declarativa sentencia_declarativa_conversion",
"sentencia_declarativa_conversion : ALLOW tipo TO tipo ';'",
"sentencia_declarativa_matrix : tipo MATRIX IDENTIFICADOR '[' CTEINTEGER ']' '[' CTEINTEGER ']' ';'",
"sentencia_declarativa_matrix : tipo MATRIX IDENTIFICADOR '[' CTEINTEGER ']' '[' CTEINTEGER ']' ';' anotacion_matrix",
"sentencia_declarativa_matrix : tipo MATRIX IDENTIFICADOR '[' CTEINTEGER ']' '[' CTEINTEGER ']' inicializacion_matrix",
"sentencia_declarativa_matrix : tipo MATRIX error ';'",
"inicializacion_matrix : operador_asignacion '{' lista_valores_matrix '}' ';'",
"inicializacion_matrix : operador_asignacion '{' lista_valores_matrix '}' ';' anotacion_matrix",
"anotacion_matrix : ANOT0",
"anotacion_matrix : ANOT1",
"lista_valores_matrix : fila_valores_matrix ';'",
"lista_valores_matrix : lista_valores_matrix fila_valores_matrix ';'",
"lista_valores_matrix : error ';'",
"fila_valores_matrix : constante",
"fila_valores_matrix : fila_valores_matrix ',' constante",
"sentencia_declarativa_datos : tipo lista_variables ';'",
"tipo : INTEGER",
"tipo : FLOAT",
"tipo : error",
"lista_variables : IDENTIFICADOR",
"lista_variables : IDENTIFICADOR ',' lista_variables",
"lista_variables : error",
"$$1 :",
"inicio_IF : IF cond $$1 cuerpo_IF",
"inicio_IF : IF error",
"$$2 :",
"cuerpo_IF : bloque_sentencias ENDIF $$2 ';'",
"$$3 :",
"$$4 :",
"cuerpo_IF : bloque_sentencias $$3 ELSE bloque_sentencias ENDIF $$4 ';'",
"inicio_For : FOR '(' asig_for cond_for variable DECREMENTO ')' bloque_sentencias",
"inicio_For : FOR '(' error ')'",
"asig_for : IDENTIFICADOR operador_asignacion expresion ';'",
"cond_for : expresion comparador expresion ';'",
"sentencia_ejecutable : inicio_IF",
"sentencia_ejecutable : inicio_For",
"sentencia_ejecutable : asignacion",
"sentencia_ejecutable : variable DECREMENTO ';'",
"sentencia_ejecutable : PRINT '(' CADENA ')' ';'",
"sentencia_ejecutable : PRINT error ';'",
"sentencia_ejecutable : PRINT '(' CADENA ')' error",
"asignacion : variable operador_asignacion expresion ';'",
"asignacion : variable error expresion ';'",
"asignacion : variable operador_asignacion error",
"cond : '(' expresion comparador expresion ')'",
"cond : '(' error ')'",
"cond : '(' expresion comparador error",
"bloque_sentencias : bloque_sentencias_ejecutable",
"bloque_sentencias_ejecutable : '{' grupo_sentencias '}'",
"bloque_sentencias_ejecutable : sentencia_ejecutable",
"grupo_sentencias : sentencia_ejecutable grupo_sentencias",
"grupo_sentencias : sentencia_ejecutable",
"expresion : termino",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"termino : factor",
"termino : termino DECREMENTO",
"termino : termino '*' factor",
"termino : termino '/' factor",
"factor : constante",
"factor : variable",
"constante : CTEINTEGER",
"constante : CTEFLOAT",
"variable : IDENTIFICADOR",
"variable : valor_matrix",
"valor_matrix : IDENTIFICADOR '[' expresion ']' '[' expresion ']'",
"comparador : '>'",
"comparador : '<'",
"comparador : MENORIGUAL",
"comparador : MAYORIGUAL",
"comparador : DISTINTO",
"comparador : '='",
"operador_asignacion : ASIGNACION",
};

//#line 527 "gramaticat.y"

/**
*
*COMIENZO CODIGO AGREGADO POR NOSOTROS
*
*/
private LexicalAnalyzer lexAn;
private ArrayList<String> errores;
private ArrayList<String> estructuras;
private int printLine=0;
private Stack<Integer> numberLine; 
private ArrayList<Terceto> tercetos;
private Stack<Terceto> stack; 
private AssignMatrix convertionMatrix;
private DivisionMatrix divisionMatrix;
private OperationMatrix operationMatrix;
private static AuxGenerator generator;

private int annotation;


private void yyerror(String string) {
	System.out.println("Error en elparseo de la sentencia:"+string);
	// TODO Auto-generated method stub
	
}
public void run(){
	parse();
}

public void parse(){
	yyparse();
	showTercetos();
}

public LexicalAnalyzer getLexicalAnalizer(){
	return lexAn;
}

public ArrayList<String> getErrores() {
	return errores;
}

public void showTercetos(){
	System.out.println("------TERCETOS------");
	System.out.println(this.tercetos.toString());
}
public ArrayList<String> getEstructuras(){
		return estructuras;
}

public ArrayList<Terceto> getTercetos() {
	return tercetos;
}

/*
*se asigna el valor de la expresion al identificador indicado 
* leftOp = IDENTIFICADOR rightOp = expresion
*/
public void assignValue (Element leftOp, Element rightOp){
	//agregar control de tipos para realizar la asignacion
	if ( rightOp == null ) {
		System.out.println("error variable no inicializada");
	} else {
			System.out.println("valor asignado"+rightOp.getValue()+" a la variable "+leftOp.getLexema());
			leftOp.setValue(rightOp.getValue());	
    	}
	
}
public void assignType(String type, ArrayList<Token> tokens ) {
		Token tk=null;
		System.out.println("===================asignacion de tipos: type "+type);
		for (Token token : tokens) {
			tk = lexAn.getSymbolTable().getToken(token.getLexema());
			System.out.println("===================TOKEN"+tk.toString());
			if ((tk != null) && (tk.hasTypeVariable())){
				//System.out.println("===================Error Sintactico");
			} else if (tk != null){
					tk.setTypeVariable(type);
					//System.out.println("===================SetType: tipo asignado "+type+" a token "+tk.toString());
				}
			}	
}
 /* metodo para asignar tipos para asignaciones*/
public String assignTypeVariable(Element left,Element right){
	String leftTypeVariable = left.getTypeVariable();
	String rightTypeVariable = right.getTypeVariable();
	String value = convertionMatrix.getTypeOperation(leftTypeVariable, rightTypeVariable);
	return value;
}
public String assignTypeVariable(Element element){
	
	String value = element.getTypeVariable();	
	return value;
}

/*metodos paraasginar tipos en operaciones suma, resta, multiplicacion*/

public String operationTypeVariable(Element left,Element right){
	String leftTypeVariable = left.getTypeVariable();
	String rightTypeVariable = right.getTypeVariable();
	String value = operationMatrix.getTypeOperation(leftTypeVariable, rightTypeVariable);
	return value;
}

public String operationTypeVariable(Element element){
	
	String value = element.getTypeVariable();	
	return value;
}

/*metodos paraasginar tipos en operaciones division*/

public String divisionTypeVariable(Element left,Element right){
	String leftTypeVariable = left.getTypeVariable();
	String rightTypeVariable = right.getTypeVariable();
	String value = divisionMatrix.getTypeOperation(leftTypeVariable, rightTypeVariable);
	return value;
}

public String divisionTypeVariable(Element element){
	
	String value = element.getTypeVariable();	
	return value;
}


public void controlRedefVariables (Token token,Token aux, String prefix){
		String lexema = token.getLexema();
		System.out.println("========================IF estaen la tabla de simbolos");
		//System.out.println("Uso que viene de la tabla de simbolos: "+lexAn.getSymbolTable().getToken(lexema).getUse());
		//System.out.println("Uso que viene del token: "+token.getUse());
		if (token.getLexema().equals(aux.getLexema())){
				if (!token.getUse().equals(aux.getUse())) {
						System.out.println("========================USO DISTINTO, FIJATE LOS NOMBRES");
						token.setLexema(prefix+"@"+lexema);
						lexAn.getSymbolTable().addToken(prefix+"@"+lexema, token);
						System.out.println("AAA token lexema: "+token.getLexema());
						System.out.println("AAAAtoken lexema de la tabla desimbolos:"+lexAn.getSymbolTable().getToken(prefix+"@"+lexema));

						
				}
	}
}


private boolean controlVarNotDeclared (Token token){
	String lexema = token.getLexema();
	if (lexAn.getSymbolTable().containsSymbol(lexema)){
		return (lexAn.getSymbolTable().getToken(lexema).getTypeVariable() == null);
	}
	return true;
}


public void changeTokenMatrix (Token token, Object indexStart,Object rows, Object columns){
	String newType = token.getType();
	String newTypeVariable = token.getTypeVariable();
	String newLexema = token.getLexema();
	int newLineNumber = token.getLineNumber();
	int newIndexStart = (Integer) indexStart;
	int newRows = (Integer) rows;
	int newColumns = (Integer) columns;
	System.out.println("Token matrix cambiado");
	Token newToken = new Token (newType, newLineNumber, newLexema, newIndexStart, newRows, newColumns, null);
	newToken.setTypeVariable(newTypeVariable);
	newToken.setUse("mat");
	lexAn.getSymbolTable().addToken(token.getLexema(), newToken);
}



/**
 * este metodo se encarga de generar los tercetos necesarios para acceder a un elemento de la matriz
 * calculando la posicion en memoria con la formula (rowIndex-indexStart)*shift+(columnIndex-indexStart)
 * 
 * shift = columns - indexStart + 1
 * 
 * indexStart se extrae del token de la tabla de simbolos
 * indexStart = lexAn.getSymbolTable().getToken(ide.getLexema()).getIndexStart();
 * @param ide token de la matriz 
 * @param rowIndex valor de fila que se desea acceder
 * @param columnIndex valor de columna que se desea acceder
 * @param columns limite de columnas de la matriz en cuestion
 */
public void makeMatrix(Token ide ,Object rowIndex, Object columnIndex){ 
															
															int bytes = 2;
															int i1 =(Integer) rowIndex;
															int i2 =(Integer) columnIndex;
															int columns = lexAn.getSymbolTable().getToken(ide.getLexema()).getColumns();
															String typeVariable = lexAn.getSymbolTable().getToken(ide.getLexema()).getTypeVariable();
															System.out.println("type variable :"+typeVariable);
															if (typeVariable.equals("float")){
																bytes = 4;
																}
															System.out.println("================MakeMatrix===========");
															System.out.println("indices de la matriz limite filas:"+i1+" Limite de columnas:"+i2);
															 System.out.println("token ide"+ide);
															 int indexStart = lexAn.getSymbolTable().getToken(ide.getLexema()).getIndexStart();
															 System.out.println("makeMatrix, token de la tabla de simbolos"+lexAn.getSymbolTable().getToken(ide.getLexema()));
															 int shift = columns-indexStart+1;
															System.out.println("indexStart"+indexStart);
															
															  Terceto base=new TercetoBase((Token)ide);
                                                              tercetos.add((Terceto)base);
										                      ((Terceto)base).setPosition(tercetos.size());
															  base.setTypeVariable(typeVariable);
															  
															  Terceto simpleResta = new TercetoSimple(indexStart);
															  simpleResta.setTypeVariable("integer");
															  simpleResta.setUse("SHIFT");
															  tercetos.add((Terceto)simpleResta);
										                      ((Terceto)simpleResta).setPosition(tercetos.size());
															 
															 Terceto simpleI1 = new TercetoSimple(i1);
															  simpleI1.setTypeVariable("integer");
															  simpleI1.setUse("SHIFT");
															  tercetos.add((Terceto)simpleI1);
										                      ((Terceto)simpleI1).setPosition(tercetos.size());
										                     
															 Terceto resta= new TercetoResta(simpleI1,simpleResta);
										                      resta.setTypeVariable("integer");
															  resta.setUse("SHIFT");
										                      tercetos.add((Terceto)resta);
										                      ((Terceto)resta).setPosition(tercetos.size());
															 
															 Terceto simpleMult  = new TercetoSimple(shift);
															  simpleMult.setTypeVariable("integer");
															 simpleMult.setUse("SHIFT");
															  tercetos.add((Terceto)simpleMult);
										                      ((Terceto)simpleMult).setPosition(tercetos.size());
										                     
															 Terceto multi=new TercetoMultiplicacion((Terceto)resta,simpleMult);
										                      multi.setTypeVariable("integer");
															  multi.setUse("SHIFT");
										                      tercetos.add((Terceto)multi);
										                      ((Terceto)multi).setPosition(tercetos.size());
															  //TokenMatrix auxIde = lexAn.getSymbolTable().getToken(ide.getLexema())
															 // int indexStart = lexAn.getSymbolTable().getToken(ide.getLexema()).getIndexStart(); 
										                      
															  Terceto simpleI2 = new TercetoSimple(i2);
															  simpleI2.setTypeVariable("integer");
															  simpleI2.setUse("SHIFT");															  
															  tercetos.add((Terceto)simpleI2);
										                      ((Terceto)simpleI2).setPosition(tercetos.size());
															  
															 Terceto resta1= new TercetoResta(simpleI2,simpleResta);
															 resta1.setTypeVariable("integer");
															 resta1.setUse("SHIFT");
															 tercetos.add((Terceto)resta1);
										                      ((Terceto)resta1).setPosition(tercetos.size());
															  
										                      Terceto suma = new TercetoSuma((Terceto)resta1,(Terceto)multi);
															  suma.setTypeVariable("integer");
															  suma.setUse("SHIFT");
															  tercetos.add((Terceto)suma);
										                      ((Terceto)suma).setPosition(tercetos.size());
															  
															  Terceto simpleBytes = new TercetoSimple(bytes);
															  simpleBytes.setTypeVariable("integer");
															  simpleBytes.setUse("SHIFT");
															  tercetos.add((Terceto)simpleBytes);
										                      ((Terceto)simpleBytes).setPosition(tercetos.size());
															  
										                      Terceto multi1=new TercetoMultiplicacion((Terceto)suma,simpleBytes);										                      
										                      multi1.setTypeVariable("integer");tercetos.add((Terceto)multi1);
															  multi1.setUse("SHIFT");
										                      ((Terceto)multi1).setPosition(tercetos.size());
															  
										                      Terceto suma1= new TercetoSuma(base,(Terceto)multi1);
										                      suma1.setTypeVariable("integer");
															  suma1.setUse("SHIFT");
										                      tercetos.add((Terceto)suma1);
										                      ((Terceto)suma1).setPosition(tercetos.size());
															  
										                      Terceto ref= new TercetoReferencia(suma1,lexAn.getSymbolTable().getToken(ide.getLexema()));
										                      tercetos.add((Terceto)ref);
															  ref.setUse("mat");
															  ref.setTypeVariable(typeVariable);
										                      ((Terceto)ref).setPosition(tercetos.size());
										                      Terceto simple = new TercetoSimple(tercetos.size()+1);
										                      //tercetos.add((Terceto) simple);
										                      }
										
										

/**
 * crea los tercetos necesarios para la inicializacion de la matriz usando MakeMatrix para calcular la posicion de memoria de cada elemento de la matriz
 * 
 * al final se elimina un terceto simple usado para guardar la posicion del terceto que contiene la posicion de memoria del elemento de la matriz
 * @param listaValores arreglo generado en la lista de valores de matriz, la cantidad de elementos debera ser igual al producto de los limtes de la matriz
 * @param indexStart es el parametro annotation de la gramatica
 * @param ide token de la matrz
 * @param rowIndex cantidad de filas de la declaracion de la matriz
 * @param columnIndex cantidad de filas de la declaracion de la matriz
 */
public void initMatrix (ArrayList<Token> listaValores,Object indexStart, Token ide, Object rowIndex , Object columnIndex ){
	int index = (Integer) indexStart;
	System.out.println("=================initMatrix========");
	System.out.println("row index value:"+rowIndex);
	int i1 = (Integer) rowIndex;
	int i2 = (Integer) columnIndex;
	int rowi = (Integer) indexStart;
	int columnj = (Integer) indexStart;
	System.out.println("indices de la matriz limite filas:"+i1+" Limite de columnas:"+i2);
	System.out.println("inicio de la matriz filas:"+rowi+" inicio de las columnas:"+columnj);
	if (  (index == 0 && (i1+1*i2+1) == listaValores.size()) || (index == 1 && (i1*i2) == listaValores.size()) ) {
		System.out.println("=======inicio del primer for");
			int i;
			int shift = i2-index+1;
			//System.out.println("============== for por fila, fila numero:"+rowi);
			for (rowi = (Integer) index; rowi <= i1; rowi=rowi+1) {
				System.out.println("============== for por fila, fila numero:"+rowi);
			
				for (columnj = (Integer) index; columnj <= i2; columnj=columnj+1) {
					System.out.println("=====================for por columnas, columna numero:"+columnj);
					i = (rowi-index)*shift+(columnj-index);
					System.out.println("indice del arreglo del elemento a recuperar:"+i+" Elemento recuperado"+listaValores.get(i));
					makeMatrix(ide, rowi, columnj);
					System.out.println("columns en for decolumnas"+columnj);
					
					Terceto simpleAssign  = new TercetoSimple(tercetos.size()-1);
					simpleAssign.setTypeVariable("integer");
					//OLD: simpleAssign, NEW tercetos.get(tercetos.size()-2)aca cambiamos el segundo parametro del token asignacion para que cuando incializamos al matriz aparezca correctamente
					TercetoAsignacion assign = new TercetoAsignacion(tercetos.get(tercetos.size()-2),listaValores.get(i));
					assign.setTypeVariable("integer");
					System.out.println("aall muuundoooooooooo 0000000000000000000000 terceto asignacion creado"+assign);
					//tercetos.remove(tercetos.size()-1);
					//tercetos.add(simpleAssign);
					simpleAssign.setPosition(tercetos.size());
					tercetos.add(assign);
					assign.setPosition(tercetos.size()+1);
					
				}
			}
			
	} else {System.out.println("Error en la cantidad de elementos declarados al inicializar la matriz");}
}



		//yylval = new ParserVal(token);

private int yylex() {
	System.out.println("ejecuto yylex principio del metodo");
	Token token = lexAn.getToken();
	
	if (token != null){
		String type = token.getType();
		yylval = new ParserVal(token);

		if (type.equals("IDENTIFICADOR"))
		{
			//yylval = new ParserVal(token.getLexema());
			return IDENTIFICADOR;
		}
		if (type.equals("CADENA"))
		{
			//yylval = new ParserVal (token.getLineNumber());
			printLine = token.getLineNumber();
			//yylval = new ParserVal(token.getLexema());
			return CADENA;
		}
		if (type.equals("ANOT0")){
				return ANOT0;
			}
		if (type.equals("ANOT1")){
				return ANOT1;
			}

		if (type.equals("PALABRARESERVADA")){
			if (token.getLexema().equals("if")){
				yylval = new ParserVal (token.getLineNumber());
				numberLine.push(yylval.ival);
				return IF;
			}
			if (token.getLexema().equals("else")){
				return ELSE;
			}
			if (token.getLexema().equals("print")){
				yylval = new ParserVal (token.getLineNumber());
				printLine = yylval.ival;
				return PRINT;
			}
			if (token.getLexema().equals("for")){
				yylval = new ParserVal (token.getLineNumber());
				numberLine.push(yylval.ival);
				return FOR;
			}
			if (token.getLexema().equals("endif"))
			{
				return ENDIF;
			}
			if (token.getLexema().equals("integer"))
			{
				yylval = new ParserVal (token);
				return INTEGER;
			}
			if (token.getLexema().equals("float"))
			{
				return FLOAT;
			}
			if (token.getLexema().equals("matrix"))
			{
				yylval = new ParserVal (token.getLineNumber());
				printLine = yylval.ival;
				
				return MATRIX;
			}
			if (token.getLexema().equals("allow"))
			{
				return ALLOW;
			}
			if (token.getLexema().equals("to"))
			{
				return TO;
			}
		}
		if (type.equals("MAYORIGUAL"))
		{
			return MAYORIGUAL;
		}
		if (type.equals("MENORIGUAL"))
		{
			return MENORIGUAL;
		}
		if (type.equals("COMENTARIO"))
		{
			return COMENTARIO;
		}
		if (type.equals("DISTINTO"))
		{
			return DISTINTO;
		}
		if (type.equals("ASIGNACION"))
		{
			return ASIGNACION;
		}
		if (type.equals("DECREMENTO"))
		{
			return DECREMENTO;
		}
		if (type.equals("INTEGER"))
		{
			//yylval = new ParserVal(token.getLexema());
			return CTEINTEGER;
		}
		if (type.equals("FLOAT"))
		{
			//yylval = new ParserVal(token.getLexema());
			return CTEFLOAT;
		}
		if (type.equals("LITERAL")){
			return lexAn.getLexema().charAt(0);
		}
	}
	return 0;
}
	
	
public Parser(String sourcePath) {
	lexAn       = new LexicalAnalyzer(sourcePath);
	errores     = new ArrayList<String>();
	estructuras = new ArrayList<String>();
	tercetos    = new ArrayList<Terceto>();
	stack 		= new Stack<Terceto>();
	numberLine  = new Stack<Integer>();
	convertionMatrix = new AssignMatrix();
	operationMatrix = new OperationMatrix();
	divisionMatrix = new DivisionMatrix();
	generator = new AuxGenerator();

}
	
//#line 890 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 43 "gramaticat.y"
{ lexAn.getSymbolTable().getToken(((Token) val_peek(1).obj).getLexema()).setUse("var");
                                 ((Token) val_peek(1).obj).setUse("nombre de programa");}
break;
case 5:
//#line 51 "gramaticat.y"
{estructuras.add(lexAn.getLineNumber()+". sentencia declarativa de datos\n");}
break;
case 6:
//#line 52 "gramaticat.y"
{estructuras.add(lexAn.getLineNumber()+". sentencia declarativa de datos\n");}
break;
case 7:
//#line 53 "gramaticat.y"
{estructuras.add(printLine+". sentencia declarativa de matrices\n");}
break;
case 8:
//#line 54 "gramaticat.y"
{estructuras.add(printLine+". sentencia declarativa de matrices\n");}
break;
case 9:
//#line 55 "gramaticat.y"
{estructuras.add(lexAn.getLineNumber()+". sentencia declarativa de conversion de tipos\n");}
break;
case 10:
//#line 56 "gramaticat.y"
{estructuras.add(lexAn.getLineNumber()+". sentencia declarativade conversion de tipos\n");}
break;
case 11:
//#line 61 "gramaticat.y"
{convertionMatrix.addConvertion(((Token)val_peek(3).obj).getLexema(), ((Token)val_peek(1).obj).getLexema());}
break;
case 12:
//#line 64 "gramaticat.y"
{ String tipoVariable = ((Token) val_peek(9).obj).getLexema();
																									((Token) val_peek(7).obj).setTypeVariable(tipoVariable);
																									((Token) val_peek(7).obj).setUse("mat");
																									Token aux = lexAn.getSymbolTable().getToken(((Token)val_peek(7).obj).getLexema());
																									controlRedefVariables(((Token) val_peek(7).obj),aux,"mat");
																									annotation = 0;
																									changeTokenMatrix (((Token) val_peek(7).obj),annotation,((Element) val_peek(5).obj).getValue(),((Element) val_peek(2).obj).getValue());
																									/*TercetoAnotacion anot = new TercetoAnotacion(0);*/
																									/*tercetos.add(anot);*/
																									/*anot.setPosition(tercetos.size());*/
																									/*TercetoMatrix matrix= new TercetoMatrix (((Token) $3.obj).getLexema(),((Token) $5.obj).getValue(),((Token) $8.obj).getValue());*/
																									/*tercetos.add(matrix);*/
																									/*matrix.setPosition(tercetos.size());*/
																									}
break;
case 13:
//#line 79 "gramaticat.y"
{ String tipoVariable = ((Token) val_peek(10).obj).getLexema();
																													  ((Token) val_peek(8).obj).setTypeVariable(tipoVariable);
																													  ((Token) val_peek(8).obj).setUse("mat");
																													  Token aux = lexAn.getSymbolTable().getToken(((Token)val_peek(8).obj).getLexema());
																													  controlRedefVariables(((Token) val_peek(8).obj),aux,"mat");
																													  annotation = (Integer)((Token) val_peek(0).obj).getValue();
																													  System.out.print("declaracion de matrices: annotation"+annotation);
																													  changeTokenMatrix (((Token) val_peek(8).obj),annotation,((Token) val_peek(6).obj).getValue(),((Token) val_peek(3).obj).getValue());
																													  
																													   }
break;
case 14:
//#line 90 "gramaticat.y"
{ String tipoVariable = ((Token) val_peek(9).obj).getLexema();
																													   ((Token) val_peek(7).obj).setTypeVariable(tipoVariable);
																														((Token) val_peek(7).obj).setUse("mat");
																													   Token aux = lexAn.getSymbolTable().getToken(((Token)val_peek(7).obj).getLexema());
																													   controlRedefVariables(((Token) val_peek(7).obj),aux,"mat");
																													   /*int anot = (Integer)tercetos.get(tercetos.size()-1).getFirst();*/
																													   /*TercetoMatrix matrix= new TercetoMatrix (((Token) $3.obj).getLexema(),((Token) $5.obj).getValue(),((Token) $8.obj).getValue());*/
																													   /*tercetos.add(matrix);*/
																													  /* matrix.setPosition(tercetos.size());*/
																													  System.out.print("declaracion de matrices: annotation"+annotation);
																													   changeTokenMatrix (((Token) val_peek(7).obj),annotation,((Token) val_peek(5).obj).getValue(),((Token) val_peek(2).obj).getValue());
																													   System.out.print("declaracion de matrices ide: token"+((Token) val_peek(7).obj));
																													   initMatrix (((ArrayList<Token>) val_peek(0).obj),annotation, ((Token) val_peek(7).obj), ((Token) val_peek(5).obj).getValue(), ((Token) val_peek(2).obj).getValue() );
																													   
																													   }
break;
case 15:
//#line 106 "gramaticat.y"
{UI2.addText(UI2.txtDebug,"Linea: "+printLine+". DECLARACION ERRONEA"+"\n"); errores.add("Linea: "+printLine+" DECLARACION ERRONEA");}
break;
case 16:
//#line 110 "gramaticat.y"
{yyval.obj=new ArrayList<Token>();
																			  ((ArrayList<Token>)(yyval.obj)).addAll((ArrayList<Token>)val_peek(2).obj);
																			  annotation = 0;
																			  }
break;
case 17:
//#line 115 "gramaticat.y"
{yyval.obj=new ArrayList<Token>();
																								((ArrayList<Token>)(yyval.obj)).addAll((ArrayList<Token>)val_peek(3).obj);																					
																								annotation = (Integer)((Token)(val_peek(0).obj)).getValue();
																								System.out.print("lista devalores matriz: annotation"+annotation);
																								}
break;
case 18:
//#line 121 "gramaticat.y"
{yyval.obj = ((Token) val_peek(0).obj);}
break;
case 19:
//#line 121 "gramaticat.y"
{yyval.obj = ((Token) val_peek(0).obj);}
break;
case 20:
//#line 123 "gramaticat.y"
{yyval.obj=new ArrayList<Token>();
												((ArrayList<Token>)(yyval.obj)).addAll((ArrayList<Token>)val_peek(1).obj);}
break;
case 21:
//#line 126 "gramaticat.y"
{yyval.obj=new ArrayList<Token>();
																	   ((ArrayList<Token>)(yyval.obj)).addAll((ArrayList<Token>)val_peek(2).obj); 
																	  ((ArrayList<Token>)(yyval.obj)).addAll((ArrayList<Token>)val_peek(1).obj);}
break;
case 22:
//#line 130 "gramaticat.y"
{UI2.addText(UI2.txtDebug,"Linea: "+printLine+". ERROR EN LA LISTA DE VARIABLES"+"\n"); errores.add("Linea: "+printLine+"ERROR EN LA LISTA DE VARIABLES"+"\n");}
break;
case 23:
//#line 133 "gramaticat.y"
{yyval.obj=new ArrayList<Token>();
								 ((ArrayList<Token>)(yyval.obj)).add((Token)val_peek(0).obj);}
break;
case 24:
//#line 136 "gramaticat.y"
{yyval.obj=new ArrayList<Token>();
														 ((ArrayList<Token>)(yyval.obj)).addAll((ArrayList<Token>)val_peek(2).obj); 
														 ((ArrayList<Token>)(yyval.obj)).add((Token)val_peek(0).obj);}
break;
case 25:
//#line 141 "gramaticat.y"
{assignType(((Token)val_peek(2).obj).getLexema(),(ArrayList<Token>)val_peek(1).obj);
														System.out.println("ejecuto regla de ASSIGN TYPE");}
break;
case 26:
//#line 145 "gramaticat.y"
{yyval.obj = val_peek(0).obj;
                System.out.println("Problando INTEGER: "+((Token)val_peek(0).obj).getLexema());}
break;
case 27:
//#line 147 "gramaticat.y"
{yyval.obj = val_peek(0).obj;
	            System.out.println("Problando FLOAT: "+((Token)val_peek(0).obj).getLexema());}
break;
case 28:
//#line 149 "gramaticat.y"
{UI2.addText(UI2.txtDebug,"Linea: "+lexAn.getLineNumber()+". Columna: "+lexAn.getIndexLine()+" ERROR DE TIPO DESCONOCIDO "+"\n"); errores.add("Linea: "+lexAn.getLineNumber()+"ERROR DE TIPO DESCONOCIDO");}
break;
case 29:
//#line 151 "gramaticat.y"
{yyval.obj=new ArrayList<Token>();
								 Token aux=lexAn.getSymbolTable().getToken(((Token) val_peek(0).obj).getLexema());
								 ((Token) val_peek(0).obj).setUse("var");
								 System.out.println("no se que poner en este mensaje"+((Token) val_peek(0).obj).getUse());
                                 System.out.println("no se que poner en este mensaje"+lexAn.getSymbolTable().getToken(((Token) val_peek(0).obj).getLexema()));
								 ((ArrayList<Token>)(yyval.obj)).add((Token)val_peek(0).obj);
								 controlRedefVariables(((Token) val_peek(0).obj),aux,"var");
				                 System.out.println(((Token) val_peek(0).obj));
				                  System.out.println(" Probando IDENTIFICADOR Lista de variables: ");
					              }
break;
case 30:
//#line 162 "gramaticat.y"
{yyval.obj=new ArrayList<Token>();
													  Token aux=lexAn.getSymbolTable().getToken(((Token) val_peek(2).obj).getLexema());
													  ((Token) val_peek(2).obj).setUse("var");
													  ((ArrayList<Token>)(val_peek(0).obj)).add((Token)val_peek(2).obj); 
													  controlRedefVariables(((Token) val_peek(2).obj),aux,"var");
                                                      ((ArrayList<Token>)(yyval.obj)).addAll((ArrayList<Token>)val_peek(0).obj);
													  System.out.print("SYSOUT DEL VECTOR "+((ArrayList<Token>)(yyval.obj)).size());}
break;
case 31:
//#line 170 "gramaticat.y"
{UI2.addText(UI2.txtDebug,"Linea: "+lexAn.getLineNumber()+". ERROR LISTA DEVARIABLES"+"\n"); errores.add("Linea: "+lexAn.getLineNumber()+"ERROR LISTA DEVARIABLES");}
break;
case 32:
//#line 172 "gramaticat.y"
{Terceto bFalse = new TercetoBFalse(tercetos.get(tercetos.size()-1)); /*Terceto comparacion*/
							System.out.println("Tamaño del ARREGLO TERCETO EN IF CONDICION: "+tercetos.size());
							System.out.println("mostraaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                            tercetos.add(bFalse);
							System.out.println("El tamaño del arreglo TERCETO en IF es: "+tercetos.size());
							bFalse.setPosition((Integer)tercetos.size());
							stack.push(bFalse);}
break;
case 34:
//#line 179 "gramaticat.y"
{UI2.addText(UI2.txtDebug,"Linea: "+numberLine.peek()+". ERROR EN if"+"\n"); errores.add("Linea: "+numberLine.peek()+"ERROR EN CONDICION");}
break;
case 35:
//#line 183 "gramaticat.y"
{Terceto bFalse = stack.pop();
									Terceto simple = new TercetoSimple((Integer)tercetos.size()+1);
									tercetos.add(new TercetoLabel((Integer)tercetos.size()+1,(Integer)tercetos.size()+1));
									bFalse.setSecond(simple);}
break;
case 37:
//#line 187 "gramaticat.y"
{	Terceto bInconditional = new TercetoBInconditional();
									tercetos.add(bInconditional); 
									bInconditional.setPosition((Integer)tercetos.size());
									tercetos.add(new TercetoLabel((Integer)tercetos.size()+2,(Integer)tercetos.size()+1));/*+2 xq si y creo que anda*/
									bInconditional.setHasLabel(true);
									Terceto bFalse = stack.pop();
									Terceto simple = new TercetoSimple((Integer)tercetos.size()+1);
									System.out.println("El tamaÃ±o del arreglo TERCETO en CUERPO_IF SIMPLE es: "+tercetos.size());
									stack.push(bInconditional);
									bFalse.setSecond(simple);/*Set linea donde termina el then*/
                                     }
break;
case 38:
//#line 197 "gramaticat.y"
{Terceto bInconditional = stack.pop();
																System.out.println("El tamaño del arreglo TERCETO en CUERPO_IF FINCONDICIONAL es: "+tercetos.size());
								                               Terceto simple = new TercetoSimple((Integer)tercetos.size()+1);
								                               bInconditional.setFirst(simple);
															   bInconditional.setHasLabel(true);
															   tercetos.add(new TercetoLabel((Integer)tercetos.size()+1,(Integer)tercetos.size()+1));
															   System.out.println("Finalizando el terceto Incondicional");}
break;
case 40:
//#line 207 "gramaticat.y"
{
																		System.out.println("====== INICIO FOR ======= ");
																		estructuras.add(numberLine.pop()+". sentencia ejecutable for\n");
														            	Terceto varUpdate = new TercetoDecremento((Token)val_peek(3).obj);
																		tercetos.add(varUpdate);
																		varUpdate.setPosition(tercetos.size());
																		varUpdate.setTypeVariable("integer");
																		
																		Terceto bInconditional = new TercetoBInconditional();
																		tercetos.add(bInconditional);
																		bInconditional.setPosition(tercetos.size());
																		bInconditional.setHasLabel(true);
																		Terceto bFalse = stack.pop();	
																		System.out.println("terceto size "+tercetos.size());
																		tercetos.add(new TercetoLabel((Integer)tercetos.size()+2,(Integer)tercetos.size()+1));
																		Terceto simple = new TercetoSimple(tercetos.size()+1);
																		bFalse.setSecond(simple);
																		simple = stack.pop();
																		/*se cambio la linea siguiente como la posicion del simple para que no falle laposicion a  la uqe se debe volver en el, salto incondicional*/
																		simple.setPosition(simple.getPosition());
																		bInconditional.setFirst(simple);
																					}
break;
case 41:
//#line 229 "gramaticat.y"
{ System.out.println("tFOR ERROR");}
break;
case 42:
//#line 231 "gramaticat.y"
{ assignValue((Element)val_peek(3).obj,(Element)val_peek(1).obj);
						    Terceto initFor = new TercetoAsignacion(lexAn.getSymbolTable().getToken(((Element)val_peek(3).obj).getLexema()),(Element)val_peek(1).obj); 
							tercetos.add(initFor);
						    initFor.setPosition(tercetos.size());
							/*stack.push(tercetos.get(tercetos.size()-1)); */
							/*System.out.println("terceto inicio"+stack.peek().toString());*/
							
							if ( !lexAn.getSymbolTable().getToken(((Element)val_peek(3).obj).getLexema()).getTypeVariable().equals("integer") ){
								UI2.addText(UI2.txtDebug,"Linea: "+lexAn.getLineNumber()+". ERROR EN LIMITES DE ITERACION FOR: TIPO INCORRECTO. Debe ser integer"+"\n");
								errores.add("Linea: "+lexAn.getLineNumber()+"ERROR EN LIMITES DE ITERACION FOR: TIPO INCORRECTO. Debe ser integer");
							}
							
							}
break;
case 43:
//#line 247 "gramaticat.y"
{
												System.out.println("=== COND FOR ==== ");
												System.out.println("terceto size "+tercetos.size());
												tercetos.add(new TercetoLabel((Integer)tercetos.size()+2,(Integer)tercetos.size()+1));
												if(  !((Element)val_peek(3).obj).getTypeVariable().equals(((Element) val_peek(1).obj).getTypeVariable()) ){
														String typeResult = operationMatrix.getTypeOperation( ((Element)val_peek(3).obj).getTypeVariable() , ((Element) val_peek(1).obj).getTypeVariable() );
														Terceto conversion;
														if (typeResult.equals(((Element)val_peek(3).obj).getTypeVariable())) {
															conversion = new TercetoConversion(((Element)val_peek(1).obj), typeResult);
														} else {
																conversion = new TercetoConversion(((Element)val_peek(3).obj), typeResult);
															}
														tercetos.add(conversion);
														(conversion).setPosition(tercetos.size());
												}
												Terceto comp = new TercetoComparador((Token)val_peek(2).obj,(Element)val_peek(3).obj,(Element) val_peek(1).obj); /*ANDA*/
												comp.setTypeVariable(operationTypeVariable((Element)val_peek(3).obj,(Element) val_peek(1).obj));
												
												tercetos.add(comp);																				
												comp.setPosition(tercetos.size());
												comp.setHasLabel(true);
												stack.push(tercetos.get(tercetos.size()-1));
												/*System.out.println("==================COND FOR==========El tamaño del arreglo TERCETO en CONDICION DEL FOR es: "+tercetos.size());*/
												Terceto bFalse = new TercetoBFalse(tercetos.get(tercetos.size()-1));
												tercetos.add(bFalse);
												bFalse.setPosition(tercetos.size());
												stack.push(bFalse);}
break;
case 44:
//#line 281 "gramaticat.y"
{estructuras.add(numberLine.pop()+". sentencia ejecutable if\n");}
break;
case 46:
//#line 289 "gramaticat.y"
{estructuras.add(lexAn.getLineNumber()+". asignacion\n");}
break;
case 47:
//#line 291 "gramaticat.y"
{estructuras.add(lexAn.getLineNumber()+". variable  decremento\n");}
break;
case 48:
//#line 293 "gramaticat.y"
{
												 System.out.println("===CADENA "+ (Element)val_peek(2).obj+" palabra reservada "+(Token)val_peek(4).obj);
												 Token token = lexAn.getSymbolTable().getToken((((Token)val_peek(2).obj).getLexema()));
												 Terceto cadena = new TercetoPrint(token);
												 tercetos.add(cadena);
												 cadena.setPosition(tercetos.size());
												 estructuras.add( printLine+". print\n");
												}
break;
case 49:
//#line 302 "gramaticat.y"
{UI2.addText(UI2.txtDebug,"Linea: "+printLine+". ERROR EN PRINT "+"\n"); errores.add("Linea: "+lexAn.getLineNumber()+"ERROR EN PRINT");}
break;
case 50:
//#line 304 "gramaticat.y"
{UI2.addText(UI2.txtDebug,"Linea: "+printLine+". ERROR EN PRINT FALTA \n"); errores.add("Linea: "+printLine+"ERROR EN PRINT FALTA ;");}
break;
case 51:
//#line 308 "gramaticat.y"
{ 
														System.out.println("==ASIGNACION==");
													/*	String typeResult = ((Element)$1.obj).getTypeVariable();*/
														if(((Element)val_peek(3).obj).getTypeVariable()!=null){
															String typeResult = ((Element)val_peek(3).obj).getTypeVariable();
															String leftType = ((Element)val_peek(3).obj).getTypeVariable();
															String rightType = ((Element)val_peek(1).obj).getTypeVariable();
															System.out.println("la expresion a asignar es"+ (Element)val_peek(1).obj);
															
															if ( ((Element)val_peek(1).obj).getUse() != null && ((Element)val_peek(1).obj).getUse().equals("mat"))
                                                            {
                                                            	Token T1 =lexAn.getSymbolTable().getToken((((Element)val_peek(3).obj).getLexema()));
																yyval.obj = new TercetoAsignacion(T1,tercetos.get(tercetos.size()-1));
																/*System.out.print("Terceto que voy a eliminar: "+tercetos.get((tercetos.size()-1)));*/
																/*tercetos.remove(tercetos.size()-1);*/
																/*System.out.print("Terceto despues de eliminar: "+tercetos.get(tercetos.size()-1));*/
																tercetos.add((Terceto)yyval.obj);
																
																((Terceto)yyval.obj).setPosition((Integer)tercetos.size()); 

                                                            }
															System.out.println("accept Operation"+convertionMatrix.acceptOperation(leftType,rightType));
															if (convertionMatrix.acceptOperation(leftType,rightType)){	
																if(  !((Element)val_peek(3).obj).getTypeVariable().equals(((Element) val_peek(1).obj).getTypeVariable()) ){
																	System.out.println("tipos distintos creacion terceto conversion");
																	typeResult = convertionMatrix.getTypeOperation( ((Element)val_peek(3).obj).getTypeVariable() , ((Element) val_peek(1).obj).getTypeVariable() );
																	Terceto conversion = new TercetoConversion(((Element)val_peek(1).obj), typeResult);
																	tercetos.add(conversion);
																	System.out.println("Terceto conv"+conversion);
																	(conversion).setPosition(tercetos.size());
																}
																Token T1 =lexAn.getSymbolTable().getToken((((Element)val_peek(3).obj).getLexema()));
																yyval.obj = new TercetoAsignacion(T1,(Element)val_peek(1).obj);
																/*((Element)$3.obj).setTypeVariable(assignTypeVariable(((Element)$1.obj),((Element)$3.obj)));*/
																assignValue(T1,(Element)val_peek(1).obj);
																((Element) yyval.obj).setTypeVariable(typeResult); /*cambiar el tipo*/
																tercetos.add((Terceto)yyval.obj);
																((Terceto)yyval.obj).setPosition((Integer)tercetos.size());                                                      
																System.out.println("Tipo variable asignacion "+typeResult+"El tipo del TERCETO en ASIGNACION es: "+((Element)val_peek(3).obj).getTypeVariable()+":="+((Element)val_peek(1).obj).getTypeVariable());
															} else {
																UI2.addText(UI2.txtDebug,"Linea: "+lexAn.getLineNumber()+". ERROR tipos incompatibles\n"); 
																errores.add("Linea: "+lexAn.getLineNumber()+"ERROR tipos incompatibles\n");			
																}
														}
													}
break;
case 52:
//#line 354 "gramaticat.y"
{UI2.addText(UI2.txtDebug,"Linea: "+lexAn.getLineNumber()+". ERROR DE OPERADOR ASIGNACION"+"\n"); errores.add("Linea: "+lexAn.getLineNumber()+"ERROR DE OPERADOR ASIGNACION\n");}
break;
case 53:
//#line 355 "gramaticat.y"
{UI2.addText(UI2.txtDebug,"Linea: "+lexAn.getLineNumber()+". ERROR DE ASIGNACION"+"\n"); errores.add("Linea: "+lexAn.getLineNumber()+"ERROR DE ASIGNACION\n");}
break;
case 54:
//#line 358 "gramaticat.y"
{	if(  !((Element)val_peek(3).obj).getTypeVariable().equals(((Element) val_peek(1).obj).getTypeVariable()) ){
														String typeResult = operationMatrix.getTypeOperation( ((Element)val_peek(3).obj).getTypeVariable() , ((Element) val_peek(1).obj).getTypeVariable() );
														Terceto conversion;
														if (typeResult.equals(((Element)val_peek(3).obj).getTypeVariable())) {
															conversion = new TercetoConversion(((Element)val_peek(1).obj), typeResult);
														} else {
																conversion = new TercetoConversion(((Element)val_peek(3).obj), typeResult);
															}
														tercetos.add(conversion);
														(conversion).setPosition(tercetos.size());
													}
													yyval.obj = new TercetoComparador((Token)val_peek(2).obj,(Element)val_peek(1).obj,(Element) val_peek(3).obj); /*ANDA*/
													((Element)yyval.obj).setTypeVariable(operationTypeVariable((Element)val_peek(3).obj,(Element) val_peek(1).obj));
                                                    tercetos.add((Terceto)yyval.obj);
                                                    ((Terceto)yyval.obj).setPosition(tercetos.size());
													((Element)yyval.obj).setHasLabel(true);
													System.out.println("El tamaño del arreglo TERCETO en CONDICION es: "+tercetos.size());
                                                    }
break;
case 55:
//#line 376 "gramaticat.y"
{UI2.addText(UI2.txtDebug,"Linea: "+lexAn.getLineNumber()+". Columna: "+lexAn.getIndexLine()+" ERROR EN CONDICION"+"\n"); errores.add("Linea: "+lexAn.getLineNumber()+"ERROR EN CONDICION");}
break;
case 56:
//#line 378 "gramaticat.y"
{UI2.addText(UI2.txtDebug,"Linea: "+lexAn.getLineNumber()+". ERROR EN IF paren2"+"\n"); errores.add("Linea: "+lexAn.getLineNumber()+"ERROR EN CONDICION");}
break;
case 62:
//#line 391 "gramaticat.y"
{yyval.obj = val_peek(0).obj;}
break;
case 63:
//#line 393 "gramaticat.y"
{    			System.out.println("==== expresion + termino ==== ");
												System.out.println("expresion "+ val_peek(2).obj+"  termino "+val_peek(0).obj);
												if(  !((Element)val_peek(2).obj).getTypeVariable().equals(((Element) val_peek(0).obj).getTypeVariable()) ){
												String typeResult = operationMatrix.getTypeOperation( ((Element)val_peek(2).obj).getTypeVariable() , ((Element) val_peek(0).obj).getTypeVariable() );
												Terceto conversion;
												if (typeResult.equals(((Element)val_peek(2).obj).getTypeVariable())) {
													conversion = new TercetoConversion(((Element)val_peek(0).obj), typeResult);
												} else {
														conversion = new TercetoConversion(((Element)val_peek(2).obj), typeResult);
													}
												tercetos.add(conversion);
												(conversion).setPosition(tercetos.size());
											}
											
									   yyval.obj = new TercetoSuma((Element)val_peek(2).obj,(Element) val_peek(0).obj); /*ANDA*/
									   ((Element)yyval.obj).setTypeVariable(operationTypeVariable((Element)val_peek(2).obj,(Element) val_peek(0).obj));
                                        tercetos.add((Terceto)yyval.obj);
										((Terceto)yyval.obj).setPosition(tercetos.size());
                                        System.out.println("El tamaño del arreglo TERCETO en SUMA es: "+tercetos.size());
										System.out.println("El tipo del TERCETO en SUMA es: "+((Element)yyval.obj).getTypeVariable());
                                        /*Estructuras.add("Linea "+analizador.NLineas+": Expresion");*/}
break;
case 64:
//#line 415 "gramaticat.y"
{     	if(  !((Element)val_peek(2).obj).getTypeVariable().equals(((Element) val_peek(0).obj).getTypeVariable()) ){
												String typeResult = operationMatrix.getTypeOperation( ((Element)val_peek(2).obj).getTypeVariable() , ((Element) val_peek(0).obj).getTypeVariable() );
												Terceto conversion;
												if (typeResult.equals(((Element)val_peek(2).obj).getTypeVariable())) {
													conversion = new TercetoConversion(((Element)val_peek(0).obj), typeResult);
												} else {
														conversion = new TercetoConversion(((Element)val_peek(2).obj), typeResult);
													}
												tercetos.add(conversion);
												(conversion).setPosition(tercetos.size());
											}
										yyval.obj = new TercetoResta((Element)val_peek(2).obj,(Element) val_peek(0).obj); /*ANDA*/
		                                ((Element)yyval.obj).setTypeVariable(operationTypeVariable((Element)val_peek(2).obj,(Element) val_peek(0).obj));
                                         tercetos.add((Terceto)yyval.obj);
										((Terceto)yyval.obj).setPosition(tercetos.size());
										System.out.println("El tamaño del arreglo TERCETO en RESTA es: "+tercetos.size());}
break;
case 65:
//#line 433 "gramaticat.y"
{yyval.obj = val_peek(0).obj;}
break;
case 66:
//#line 435 "gramaticat.y"
{yyval.obj = new TercetoDecremento((Element)val_peek(1).obj);
								System.out.println("ANTES de setear la posicion en terceto decremento");
                               tercetos.add((Terceto)yyval.obj);
							   ((Terceto)yyval.obj).setPosition(tercetos.size()); 
							   System.out.println("posicion terceto decremento: "+tercetos.size());}
break;
case 67:
//#line 441 "gramaticat.y"
{ 			if(  !((Element)val_peek(2).obj).getTypeVariable().equals(((Element) val_peek(0).obj).getTypeVariable()) ){
												String typeResult = operationMatrix.getTypeOperation( ((Element)val_peek(2).obj).getTypeVariable() , ((Element) val_peek(0).obj).getTypeVariable() );
												Terceto conversion;
												if (typeResult.equals(((Element)val_peek(2).obj).getTypeVariable())) {
													conversion = new TercetoConversion(((Element)val_peek(0).obj), typeResult);
												} else {
														conversion = new TercetoConversion(((Element)val_peek(2).obj), typeResult);
													}
												tercetos.add(conversion);
												(conversion).setPosition(tercetos.size());
											}
		 
								yyval.obj = new TercetoMultiplicacion((Element)val_peek(2).obj,(Element) val_peek(0).obj);
							   ((Element)yyval.obj).setTypeVariable(operationTypeVariable((Element)val_peek(2).obj,(Element) val_peek(0).obj));
							   tercetos.add((Terceto)yyval.obj);
                               ((Terceto)yyval.obj).setPosition((Integer)tercetos.size()); 
							   System.out.println("posicion terceto ENZO multiplicacion");
							   System.out.println("posicion terceto multi: "+tercetos.size());
							 }
break;
case 68:
//#line 461 "gramaticat.y"
{
									
											String typeResult = divisionTypeVariable( (Element)val_peek(2).obj , (Element) val_peek(0).obj );
											Terceto conversion;
											if (!typeResult.equals(((Element)val_peek(2).obj).getTypeVariable())) {
												conversion = new TercetoConversion(((Element)val_peek(2).obj), typeResult);
												tercetos.add(conversion);
												(conversion).setPosition(tercetos.size());
											} 
											if (!typeResult.equals(((Element)val_peek(0).obj).getTypeVariable())) {
													conversion = new TercetoConversion(((Element)val_peek(0).obj), typeResult);
													tercetos.add(conversion);
													(conversion).setPosition(tercetos.size());
													
												}
							
							  yyval.obj = new TercetoDivision((Element)val_peek(2).obj,(Element)val_peek(0).obj);
							  ((Element)yyval.obj).setTypeVariable(divisionTypeVariable((Element)val_peek(2).obj,(Element) val_peek(0).obj));
							  tercetos.add((Terceto)yyval.obj);
							  ((Terceto)yyval.obj).setPosition(tercetos.size());
							  System.out.println(" posicion terceto division: "+tercetos.size());}
break;
case 69:
//#line 484 "gramaticat.y"
{yyval.obj = val_peek(0).obj;}
break;
case 70:
//#line 486 "gramaticat.y"
{yyval.obj = val_peek(0).obj;}
break;
case 71:
//#line 488 "gramaticat.y"
{yyval.obj = lexAn.getSymbolTable().getToken(((Token)val_peek(0).obj).getLexema()); /*ANDA*/
						System.out.println("Probando CTEINTEGER: "+lexAn.getSymbolTable().getToken(((Token)val_peek(0).obj).getLexema()));}
break;
case 72:
//#line 491 "gramaticat.y"
{yyval.obj = lexAn.getSymbolTable().getToken(((Token)val_peek(0).obj).getLexema());}
break;
case 73:
//#line 493 "gramaticat.y"
{ 
                           if (controlVarNotDeclared(((Token)val_peek(0).obj))){
								UI2.addText(UI2.txtDebug,"Linea: "+lexAn.getLineNumber()+". Variable ["+((Token)val_peek(0).obj).getLexema()+"] no declarada");
								errores.add("Linea: "+lexAn.getLineNumber()+"Variable no declarada");
							} else {yyval.obj = lexAn.getSymbolTable().getToken(((Token)val_peek(0).obj).getLexema()); /* ANDA*/
									System.out.println("Probando IDENTIFICADOR en regla de variable ident: "+lexAn.getSymbolTable().getToken(((Token)val_peek(0).obj).getLexema()));}
									}
break;
case 75:
//#line 502 "gramaticat.y"
{System.out.println("==== valor matrix ==== ");
																/*$$.obj = lexAn.getSymbolTable().getToken(((Token)$1.obj).getLexema()); */
																/*makeMatrix(Token ide ,Object rowIndex, Object columnIndex);*/
	                                                             makeMatrix((Token)val_peek(6).obj,((Token)val_peek(4).obj).getValue(),((Token)val_peek(1).obj).getValue());
																 System.out.println("ultimos tercetos "+tercetos.size()+" agregados"+tercetos.get(tercetos.size()-2));
																 /*tercetos.get(tercetos.size()-1).setUse("mat");*/
																 yyval.obj = tercetos.get(tercetos.size()-1);
																 if ( !((Element)val_peek(4).obj).getTypeVariable().equals("integer") || !((Element)val_peek(1).obj).getTypeVariable().equals("integer") )
																	UI2.addText(UI2.txtDebug,"Linea: "+lexAn.getLineNumber()+". Variable ["+((Token)val_peek(6).obj).getLexema()+"] con limites no enteros");
																	errores.add("Linea: "+lexAn.getLineNumber()+"Variable matriz con limites de tipo erroneos");
																}
break;
//#line 1601 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
//## The -Jnorun option was used ##
//## end of method run() ########################################



//## Constructors ###############################################
//## The -Jnoconstruct option was used ##
//###############################################################



}
//################### END OF CLASS ##############################
