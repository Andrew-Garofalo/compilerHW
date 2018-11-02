public class Parser
{
    public static final int ENDMARKER   =  0;
    public static final int LEXERROR    =  1;

    public static final int PRINT       = 11;
    public static final int INT         = 13;
    public static final int BEGIN       = 30;
    public static final int END         = 31;
    public static final int LPAREN      = 33;
    public static final int RPAREN      = 34;
    public static final int ASSIGN      = 38;
    public static final int PLUS        = 41;
    public static final int MUL         = 43;
    public static final int SEMI        = 47;
    public static final int INT_LIT     = 55;
    public static final int IDENT       = 58;
	public static final int FLOAT       = 59;
	public static final int BOOL        = 60;
	public static final int COMMA       = 61;
	public static final int BOOL_VAL    = 62; //captures true/false
	public static final int FLOAT_LIT   = 63;
	public static final int RECORD      = 64;
	public static final int SIZE        = 65;
	public static final int NEW         = 66;
	public static final int WHILE       = 67;
	public static final int IF          = 68;
	public static final int ELSE        = 69;
	public static final int RETURN      = 70;
	public static final int BREAK       = 71;
	public static final int CONTINUE    = 72;
	public static final int AND         = 73;
	public static final int OR          = 74;
	public static final int NOT         = 75;
	public static final int ISEQ        = 76;
	public static final int NOTEQ       = 77;
	public static final int LT          = 78;
	public static final int GT          = 79;
	public static final int LTE         = 80;
	public static final int GTE         = 81;
	public static final int DIV         = 82;
	public static final int MINUS       = 83;
	public static final int LSQBRACKET  = 84;
	public static final int RSQBRACKET  = 85;
	public static final int AMPERSAND   = 86;
	public static final int PERCENT     = 87;
	public static final int THEN        = 88;

    public class Token {
        public int       type;
        public ParserVal attr;
        public Token(int type, ParserVal attr) {
            this.type = type;
            this.attr = attr;
        }
    }

    public ParserVal yylval;
    Token[]          _tokens;
    int              _lah;
    public Parser(java.io.Reader r) throws java.io.IOException
    {
        this._tokens   = new Token[20];
        this._lah      = 0;

        // read all tokens in advance
        Lexer lex = new Lexer(r, this);
        for(int i=0; i<20; i++)
        {
            int token_type = lex.yylex();
			
            if (token_type == 0 ) { _tokens[i] = new Token(Parser.ENDMARKER, null); break; } // end of input
            if (token_type == -1) { _tokens[i] = new Token(Parser.ENDMARKER, null); break; } // error

            _tokens[i] = new Token(token_type, yylval);
        }
    }

    public void Advance() throws java.io.IOException
    {
        // increase location of lah
        _lah ++;
    }
    public boolean Match(int token_type) throws java.io.IOException
    {
        if (_lah < 0 || _lah >= 20)
            return false;

        boolean match = (token_type == _tokens[_lah].type);
if(match) {
    System.out.println("Token type: " + token_type + ". lah token type: " + _tokens[_lah].type);
}
        _lah ++;

        return match;
    }
    public int GetTokenLocation()
    {
        // get token location for backtracking
        return _lah;
    }
    public void ResetTokenLocation(int loc)
    {
        // restore token location for backtracking
        _lah = loc;
    }

    public int yyparse() throws java.io.IOException, Exception
    {
        parse();
        return 0;
    }

    public void parse() throws java.io.IOException, Exception
    {
        boolean successparse = program();
        if(successparse)
            System.out.println("Success: no syntax error found.");
        else
            System.out.println("Error: there exist syntax errors.");
    }

    public boolean program() throws java.io.IOException, Exception
    {
        int loc = GetTokenLocation();

        // program -> decl_list
        ResetTokenLocation(loc);
        if(decl_list() && Match(ENDMARKER))
            return true;

        return false;
    }
    public boolean decl_list() throws java.io.IOException, Exception
    {
        int loc = GetTokenLocation();

        // decl_list -> decl_list'
        ResetTokenLocation(loc);
        if(decl_list_())
            return true;

        return false;
    }
    public boolean decl_list_() throws java.io.IOException, Exception
    {
        int loc = GetTokenLocation();

        // decl_list'	-> fun_decl decl_list'
        ResetTokenLocation(loc);
        if(fun_decl())
            if(decl_list_())
                return true;

        // decl_list'	-> epsilon
        ResetTokenLocation(loc);
        return true;
    }
    public boolean type_spec() throws java.io.IOException, Exception
    {
        int loc = GetTokenLocation();

        // type_spec	-> "int"
        ResetTokenLocation(loc);
        if(Match(INT))
            return true;

        return false;
    }
    public boolean fun_decl() throws java.io.IOException, Exception
    {
        int loc = GetTokenLocation();

        // fun_decl	-> type_spec IDENT "(" params ")" compound_stmt
        ResetTokenLocation(loc);
        if(type_spec())
            if(Match(IDENT))
                if(Match(LPAREN))
                    if(params())
                        if(Match(RPAREN))
                            if(compound_stmt())
                                return true;

        return false;
    }

    public boolean params() throws java.io.IOException, Exception
    {
        int loc = GetTokenLocation();

        ResetTokenLocation(loc);
        if(param_list())
            return true;

        ResetTokenLocation(loc);
        return true;
    }
	public boolean param_list() throws java.io.IOException, Exception
    {
        int loc = GetTokenLocation();

        ResetTokenLocation(loc);
        if(param())
            if(param_list_())
                return true;
		return false;
    }
	public boolean param() throws java.io.IOException, Exception
    {
        int loc = GetTokenLocation();

        ResetTokenLocation(loc);
        if(type_spec())
            if(Match(IDENT))
                return true;
		return false;
    }
	public boolean stmt_list() throws java.io.IOException, Exception
    {
        int loc = GetTokenLocation();

        ResetTokenLocation(loc);
        if(stmt_list_())
            return true;
		return false;
    }
	public boolean stmt_list_() throws java.io.IOException, Exception
    {
        int loc = GetTokenLocation();

        ResetTokenLocation(loc);
        if(stmt())
            if(stmt_list_())
                return true;

        ResetTokenLocation(loc);
        return true;
    }
	public boolean stmt() throws java.io.IOException, Exception
    {
        int loc = GetTokenLocation();

        ResetTokenLocation(loc);
        if(expr_stmt())
            return true;
		
		ResetTokenLocation(loc);
        if(compound_stmt())
			return true;
		
		ResetTokenLocation(loc);
        if(if_stmt())
			return true;
		
		ResetTokenLocation(loc);
		if(while_stmt())
            return true;
		
		return false;
    }
	public boolean expr_stmt() throws java.io.IOException, Exception
    {
        int loc = GetTokenLocation();

        ResetTokenLocation(loc);
        if(Match(IDENT))
			if(Match(ASSIGN))
				if(expr())
					if(Match(SEMI))
						return true;
		
		ResetTokenLocation(loc);
        if(Match(SEMI))
            return true;
		return false;
    }
	public boolean while_stmt() throws java.io.IOException, Exception
    {
        int loc = GetTokenLocation();

        ResetTokenLocation(loc);
        if(Match(WHILE))
			if(Match(LPAREN))
				if(expr())
					if(Match(RPAREN))
						if(stmt())
							return true;
		return false;
    }
    public boolean compound_stmt() throws java.io.IOException, Exception
    {
        int loc = GetTokenLocation();

        // fun_decl	-> type_spec IDENT "(" params ")" compound_stmt
        ResetTokenLocation(loc);
        if(Match(BEGIN))
            if(local_decls())
                if(stmt_list())
                    if(Match(END))
                        return true;

        return false;
    }
	public boolean local_decls() throws java.io.IOException, Exception
    {
        int loc = GetTokenLocation();

        ResetTokenLocation(loc);
        if(local_decls_())
            return true;

        return false;
    }
	public boolean local_decl() throws java.io.IOException, Exception
    {
        int loc = GetTokenLocation();

        ResetTokenLocation(loc);
        if(type_spec())
            if(Match(IDENT))
                if(Match(SEMI))
                    return true;

        return false;
    }
	public boolean if_stmt() throws java.io.IOException, Exception
    {
        int loc = GetTokenLocation();

        ResetTokenLocation(loc);
            if(Match(IF))
                if(Match(LPAREN))
                    if(expr())
                        if(Match(RPAREN))
                            if(stmt())
								if(Match(ELSE))
									if(stmt())
										return true;

        return false;
    }
	public boolean arg_list() throws java.io.IOException, Exception
    {
        int loc = GetTokenLocation();

        ResetTokenLocation(loc);
        if(expr())
			if(arg_list_())
            return true;

        return false;
    }
	
	public boolean args() throws java.io.IOException, Exception
    {
        int loc = GetTokenLocation();

        ResetTokenLocation(loc);
        if(expr())
            if(arg_list_())
                return true;

        ResetTokenLocation(loc);
        return true;
    }
	public boolean expr() throws java.io.IOException, Exception
    {
        int loc = GetTokenLocation();

        ResetTokenLocation(loc);
        if(term())
			if(expr_())
            return true;

        return false;
    }
	public boolean term() throws java.io.IOException, Exception
    {
        int loc = GetTokenLocation();

        ResetTokenLocation(loc);
        if(factor())
			if(term_())
            return true;

        return false;
    }
	public boolean factor() throws java.io.IOException, Exception
    {
        int loc = GetTokenLocation();

        ResetTokenLocation(loc);
        if(Match(IDENT))
			if(factor_())
            return true;
		ResetTokenLocation(loc);
        if(Match(LPAREN))
			if(expr())
				if(Match(RPAREN))
            return true;
		ResetTokenLocation(loc);
        if(Match(INT_LIT))
            return true;
		return false;
    }
	public boolean param_list_() throws java.io.IOException, Exception
    {
        int loc = GetTokenLocation();

        ResetTokenLocation(loc);
        if(Match(COMMA))
            if(param())
				if(param_list_())
                return true;

        ResetTokenLocation(loc);
        return true;
    }
	public boolean local_decls_() throws java.io.IOException, Exception
    {
        int loc = GetTokenLocation();

        ResetTokenLocation(loc);
        if(local_decl())
            if(local_decls_())
                return true;

        ResetTokenLocation(loc);
        return true;
    }
	public boolean arg_list_() throws java.io.IOException, Exception
    {
        int loc = GetTokenLocation();

        ResetTokenLocation(loc);
        if(Match(COMMA))
            if(expr())
				if(arg_list_())
                return true;

        ResetTokenLocation(loc);
        return true;
    }
	public boolean expr_() throws java.io.IOException, Exception
    {
        int loc = GetTokenLocation();

        ResetTokenLocation(loc);
        if(Match(PLUS))
            if(term())
				if(expr_())
                return true;

        ResetTokenLocation(loc);
        return true;
    }
	public boolean term_() throws java.io.IOException, Exception
    {
        int loc = GetTokenLocation();

        ResetTokenLocation(loc);
        if(Match(ISEQ))
            if(factor())
				if(term_())
                return true;
			
		ResetTokenLocation(loc);
        if(Match(MUL))
            if(factor())
				if(term_())
                return true;

        ResetTokenLocation(loc);
        return true;
    }
	public boolean factor_() throws java.io.IOException, Exception
    {
        int loc = GetTokenLocation();

        ResetTokenLocation(loc);
        if(Match(LPAREN))
            if(args())
				if(Match(RPAREN))
                return true;

        ResetTokenLocation(loc);
        return true;
    }

}
