/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Copyright (C) 2000 Gerwin Klein <lsf@jflex.de>                          *
 * All rights reserved.                                                    *
 *                                                                         *
 * Thanks to Larry Bell and Bob Jamison for suggestions and comments.      *
 *                                                                         *
 * License: BSD                                                            *
 *                                                                         *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

%%

%class Lexer
%byaccj

%{

  public Parser   yyparser;
  public int      lineno;
  public MyTokenAttr myTokenAttr;

  public Lexer(java.io.Reader r, Parser yyparser) {
    this(r);
    this.yyparser = yyparser;
    this.lineno   = 1;
  }
  
  public class MyTokenAttr{
		public int lineNumber;
		public String token;
		
		public MyTokenAttr(String t, int l) {
			this.lineNumber = l;
			this.token = t;
		}
		
	}
	
	
%}

float      = [0-9]+(.[0-9]+)(E[+-]?[0-9]+)?
int        = [0-9]+
identifier = [a-zA-Z_][a-zA-Z0-9_]*
newline    = \n
whitespace = [\t\r ]+
comment    = "//".*
boolval    = (true|false)

%%

"print"                             { yyparser.yylval = new ParserVal(new MyTokenAttr(yytext(), lineno) 		  ); return Parser.PRINT  ; }
"bool"                              { yyparser.yylval = new ParserVal(new MyTokenAttr(yytext(), lineno)           ); return Parser.BOOL  ; }
","									{ yyparser.yylval = new ParserVal(new MyTokenAttr(yytext(), lineno)           ); return Parser.COMMA  ; }
"float"							    { yyparser.yylval = new ParserVal(new MyTokenAttr(yytext(), lineno)           ); return Parser.FLOAT_LIT ; }
"record"						    { yyparser.yylval = new ParserVal(new MyTokenAttr(yytext(), lineno)           ); return Parser.RECORD ; }
"size"								{ yyparser.yylval = new ParserVal(new MyTokenAttr(yytext(), lineno)           ); return Parser.SIZE ; }
"new"								{ yyparser.yylval = new ParserVal(new MyTokenAttr(yytext(), lineno)            ); return Parser.NEW ; }
"while"								{ yyparser.yylval = new ParserVal(new MyTokenAttr(yytext(), lineno)           ); return Parser.WHILE ; }
"if"								{ yyparser.yylval = new ParserVal(new MyTokenAttr(yytext(), lineno)            ); return Parser.IF ; }
"then"								{ yyparser.yylval = new ParserVal(new MyTokenAttr(yytext(), lineno)           ); return Parser.THEN ; }
"else"								{ yyparser.yylval = new ParserVal(new MyTokenAttr(yytext(), lineno)            ); return Parser.ELSE ; }
"return"							{ yyparser.yylval = new ParserVal(new MyTokenAttr(yytext(), lineno)           ); return Parser.RETURN ; }
"break"								{ yyparser.yylval = new ParserVal(new MyTokenAttr(yytext(), lineno)           ); return Parser.BREAK ; }
"continue"							{ yyparser.yylval = new ParserVal(new MyTokenAttr(yytext(), lineno)           ); return Parser.CONTINUE ; }
"and"								{ yyparser.yylval = new ParserVal(new MyTokenAttr(yytext(), lineno)            ); return Parser.AND ; }
"or"								{ yyparser.yylval = new ParserVal(new MyTokenAttr(yytext(), lineno)           ); return Parser.OR ; }
"not"								{ yyparser.yylval = new ParserVal(new MyTokenAttr(yytext(), lineno)            ); return Parser.NOT ; }
"=="								{ yyparser.yylval = new ParserVal(new MyTokenAttr(yytext(), lineno)            ); return Parser.ISEQ ; }
"!="								{ yyparser.yylval = new ParserVal(new MyTokenAttr(yytext(), lineno)            ); return Parser.NOTEQ ; }
"<"									{ yyparser.yylval = new ParserVal(new MyTokenAttr(yytext(), lineno)           ); return Parser.LT ; }
">"									{ yyparser.yylval = new ParserVal(new MyTokenAttr(yytext(), lineno)           ); return Parser.GT ; }
"<="								{ yyparser.yylval = new ParserVal(new MyTokenAttr(yytext(), lineno)           ); return Parser.LTE ; }
">="								{ yyparser.yylval = new ParserVal(new MyTokenAttr(yytext(), lineno)            ); return Parser.GTE ; }
"/"									{ yyparser.yylval = new ParserVal(new MyTokenAttr(yytext(), lineno)           ); return Parser.DIV ; }
"-"									{ yyparser.yylval = new ParserVal(new MyTokenAttr(yytext(), lineno)            ); return Parser.MINUS ; }
"int"                               { yyparser.yylval = new ParserVal(new MyTokenAttr(yytext(), lineno)            ); return Parser.INT    ; }
"{"                                 { yyparser.yylval = new ParserVal(new MyTokenAttr(yytext(), lineno)          ); return Parser.BEGIN  ; }
"}"                                 { yyparser.yylval = new ParserVal(new MyTokenAttr(yytext(), lineno)            ); return Parser.END    ; }
"("                                 { yyparser.yylval = new ParserVal(new MyTokenAttr(yytext(), lineno)           ); return Parser.LPAREN ; }
")"                                 { yyparser.yylval = new ParserVal(new MyTokenAttr(yytext(), lineno)            ); return Parser.RPAREN ; }
"="                                 { yyparser.yylval = new ParserVal(new MyTokenAttr(yytext(), lineno)            ); return Parser.ASSIGN ; }
"+"                                 { yyparser.yylval = new ParserVal(new MyTokenAttr(yytext(), lineno)           ); return Parser.PLUS   ; }
"*"                                 { yyparser.yylval = new ParserVal(new MyTokenAttr(yytext(), lineno)            ); return Parser.MUL    ; }
";"                                 { yyparser.yylval = new ParserVal(new MyTokenAttr(yytext(), lineno)            ); return Parser.SEMI   ; }
"["                                 { yyparser.yylval = new ParserVal(new MyTokenAttr(yytext(), lineno)           ); return Parser.LSQBRACKET   ; }
"]"                                 { yyparser.yylval = new ParserVal(new MyTokenAttr(yytext(), lineno)           ); return Parser.RSQBRACKET   ; }
"&"                                 { yyparser.yylval = new ParserVal(new MyTokenAttr(yytext(), lineno)           ); return Parser.AMPERSAND   ; }
"%"                                 { yyparser.yylval = new ParserVal(new MyTokenAttr(yytext(), lineno)            ); return Parser.PERCENT   ; }
{float}								{ yyparser.yylval = new ParserVal((Object)yytext()); return Parser.FLOAT; }
{int}                               { yyparser.yylval = new ParserVal((Object)yytext()); return Parser.INT_LIT; }
{boolval}                           { yyparser.yylval = new ParserVal((Object)yytext()); return Parser.BOOL_VAL; }
{identifier}                        { yyparser.yylval = new ParserVal((Object)yytext()); return Parser.IDENT  ; }
{comment}                           {  /* skip */ }
{newline}                           { lineno++; /* skip */ }
{whitespace}                        { /* skip */ }


\b     { System.err.println("Sorry, backspace doesn't work"); }

/* error fallback */
[^]    { System.err.println("Error: unexpected character '"+yytext()+"'"); return -1; }
