package org.lexer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lexer {

    static int pos = 0;
    static int line = 1;
    static String input = "";

    static Map<String, TokenType> keywords = new HashMap<>();
    static Map<String, TokenType> symbols = new HashMap<>();
    static String allSymbols = "+-*/=<>|.,;:(){}[]";
    static {
        keywords.put("import", TokenType.IMPORT);
        keywords.put("class", TokenType.CLASS);
        keywords.put("return", TokenType.RETURN);
        keywords.put("if", TokenType.IF);
        keywords.put("else", TokenType.ELSE);
        keywords.put("while", TokenType.WHILE);
        keywords.put("for", TokenType.FOR);
        keywords.put("repeat", TokenType.REPEAT);
        keywords.put("throw", TokenType.THROW);
        keywords.put("try", TokenType.TRY);
        keywords.put("catch", TokenType.CATCH);
        keywords.put("get", TokenType.GET);
        keywords.put("set", TokenType.SET);
        keywords.put("byte", TokenType.BYTE);
        keywords.put("short", TokenType.SHORT);
        keywords.put("int", TokenType.INT);
        keywords.put("long", TokenType.LONG);
        keywords.put("char", TokenType.CHAR);
        keywords.put("bool", TokenType.BOOL);
        keywords.put("string", TokenType.STRING);
        keywords.put("true", TokenType.TRUE);
        keywords.put("false", TokenType.FALSE);
        keywords.put("void", TokenType.VOID);
        keywords.put("new", TokenType.NEW);
        keywords.put("float", TokenType.FLOAT);
        keywords.put("double", TokenType.DOUBLE);
        symbols.put("+", TokenType.PLUS);
        symbols.put("-", TokenType.MINUS);
        symbols.put("*", TokenType.MUL);
        symbols.put("/", TokenType.DIV);
        symbols.put("=", TokenType.EQ);
        symbols.put("++", TokenType.PLUSPLUS);
        symbols.put("--", TokenType.MINUSMINUS);
        symbols.put("+=", TokenType.PLUSEQ);
        symbols.put("-=", TokenType.MINUSEQ);
        symbols.put("*=", TokenType.MULEQ);
        symbols.put("/=", TokenType.DIVEQ);
        symbols.put("->", TokenType.ARROW);
        symbols.put("=>", TokenType.DOUBLE_ARROW);
        symbols.put("(", TokenType.LPAREN);
        symbols.put(")", TokenType.RPAREN);
        symbols.put("{", TokenType.LFIGURE_PAREN);
        symbols.put("}", TokenType.RFIGURE_PAREN);
        symbols.put("[", TokenType.LSQR_PAREN);
        symbols.put("]", TokenType.RSQR_PAREN);
        symbols.put("<", TokenType.LOWER);
        symbols.put("<=", TokenType.LOWEREQ);
        symbols.put(">", TokenType.HIGHER);
        symbols.put(">=", TokenType.HIGHEREQ);
        symbols.put("==", TokenType.EQEQ);
        symbols.put("!=", TokenType.NOTEQ);
        symbols.put("!", TokenType.NOT);
        symbols.put("||", TokenType.OROR);
        symbols.put("&&", TokenType.ANDAND);
        symbols.put(".", TokenType.POINT);
        symbols.put(",", TokenType.COMMA);
        symbols.put(":", TokenType.COLON);
        symbols.put(";", TokenType.SEMICOLON);
        symbols.put("|>", TokenType.PIPE);
    }


    public static List<Token> tokenize(String inp) {
        pos = 0;
        line = 1;
        input = inp;
        List<Token> res = new ArrayList<>();

        while(get(0) != '\0'){
            if(get(0) == '\n'){
                line++;
                pos++;
            }
            else if(Character.isWhitespace(get(0))) {
                pos++;
            }
            else if((get(0) == '/' && get(1) == '/') || (get(0) == '/' && get(1) == '*')){
                skipComments();
            }
            else if(Character.isDigit(get(0))){
                res.add(tokenizeNumber());
            }
            else if(get(0) == '"'){
                res.add(tokenizeString());
            }
            else if(get(0) == '\''){
                res.add(tokenizeChar());
            }
            else if(Character.isAlphabetic(get(0))){
                res.add(tokenizeWord());
            }
            else if(allSymbols.indexOf(get(0)) != -1){
                res.add(tokenizeSymbol());
            }
            else {
                throw new RuntimeException("Ошибка в символе: " + get(0) + " на строке " + line);
            }
        }
        res.add(new Token(TokenType.EOF, "\0", line));
        return res;
    }

    private static void skipComments(){
        pos++;
        if(get(0) == '/'){
            while (true) {
                pos++;
                if(get(0) == '\n'){
                    line++;
                    pos++;
                    break;
                }
            }
        }
        else{
            while(true){
                pos++;
                if(get(0) == '\n'){
                    line++;
                }
                if(get(0) == '*' && get(1) == '/'){
                    pos += 2;
                    break;
                }
            }
        }
    }

    private static Token tokenizeNumber() {
        StringBuilder sb = new StringBuilder();
        TokenType t = TokenType.INT_LITERAL;

        while(Character.isDigit(get(0))){
            sb.append(get(0));
            pos++;
            if(get(0) == '.'){
                if(t == TokenType.DOUBLE_LITERAL){
                    throw new RuntimeException("В double ожидается только одна точка" + get(0));
                }
                else{
                    sb.append('.');
                    pos++;
                    t = TokenType.DOUBLE_LITERAL;
                }
            }
        }

        return new Token(t, sb.toString(), line);
    }
    private static Token tokenizeString() {
        StringBuilder sb = new StringBuilder();
        pos++;
        while(get(0) != '"'){
            sb.append(get(0));
            pos++;
            if(get(0) == '\n'){
                line++;
            }
            else if(get(0) == '\\'){
                pos++;
                switch (get(0)){
                    case 'n'->sb.append('\n');
                    case 'r'->sb.append('\r');
                    case 't'->sb.append('\t');
                    case '\\'->sb.append('\\');
                    case '\''->sb.append('\'');
                    case '\"'->sb.append('\"');
                    default->throw new RuntimeException("Неизвестная эскейп-последовательность!");
                }
            }
            else if(get(0) == '\0'){
                throw new RuntimeException("Не закрыта строка!");
            }
        }
        pos++;
        return new Token(TokenType.STRING_LITERAL, sb.toString(), line);
    }
    private static Token tokenizeChar()  {
        pos++;
        char c = get(0);
        if(c == '\\'){
            pos++;
            switch (get(0)){
                case 'n'->c = '\n';
                case 'r'->c = '\r';
                case 't'->c = '\t';
                case '\\'->c = '\\';
                case '\''->c = '\'';
                case '\"'->c = '\"';
                default -> throw new RuntimeException("Неизвестная эскейп-последовательность!");
            }
        }
        pos++;
        if(get(0) != '\''){
            new Exception("Незакрытый символ!").fillInStackTrace();
        }
        return new Token(TokenType.CHAR_LITERAL, String.valueOf(c), line);
    }
    private static Token tokenizeWord() {
        StringBuilder sb = new StringBuilder();
        while(Character.isLetterOrDigit(get(0))){
            sb.append(get(0));
            pos++;
        }
        return new Token(keywords.getOrDefault(sb.toString(), TokenType.ID), sb.toString(), line);
    }
    private static Token tokenizeSymbol() {
        if(allSymbols.indexOf(get(1)) != -1){
            String s = Character.toString(get(0)) + Character.toString(get(1));
            if(symbols.containsKey(s)){
                pos += 2;
                return new Token(symbols.get(s), s, line);
            }
            else {
                String s2 = Character.toString(s.charAt(0));
                pos++;
                return new Token(symbols.get(s2), s2, line);
            }
        }
        else{
            String s = Character.toString(get(0));
            pos++;
            return new Token(symbols.get(s), s, line);
        }
    }
    private static char get(int offset){
        int n = pos + offset;
        if(n < input.length()){
            return input.charAt(n);
        }
        else {
            return '\0';
        }
    }
}