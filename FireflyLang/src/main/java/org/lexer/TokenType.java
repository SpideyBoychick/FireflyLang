package org.lexer;

public enum TokenType {

    //Types keywords
    BYTE,
    SHORT,
    INT,
    LONG,
    STRING,
    FLOAT,
    DOUBLE,
    CHAR,
    BOOL,
    TRUE,
    FALSE,
    VOID,

    //Literals
    INT_LITERAL,
    DOUBLE_LITERAL,
    STRING_LITERAL,
    CHAR_LITERAL,

    //Keywords
    IMPORT,
    CLASS,
    RETURN,
    IF,
    ELSE,
    WHILE,
    FOR,
    REPEAT,
    THROW,
    TRY,
    CATCH,
    GET,
    SET,
    NEW,

    //Symbols
    PLUS,// +
    MINUS,// -
    MUL,// *
    DIV,// /
    EQ,// =
    PLUSPLUS,// ++
    MINUSMINUS,// --
    PLUSEQ,// +=
    MINUSEQ,// -=
    MULEQ,// *=
    DIVEQ,// /=
    ARROW,// ->
    DOUBLE_ARROW,// =>
    LPAREN,// (
    RPAREN,// )
    LFIGURE_PAREN,// {
    RFIGURE_PAREN,// }
    LSQR_PAREN,// [
    RSQR_PAREN,// ]
    LOWER,// <
    LOWEREQ,// <=
    HIGHER,// >
    HIGHEREQ,// >=
    EQEQ,// ==
    NOTEQ,// !=
    NOT,// !
    OROR,// ||
    ANDAND,// &&
    POINT,// .
    COMMA,// ,
    COLON,// :
    SEMICOLON,// ;
    PIPE,// |>

    ID,
    EOF
}
