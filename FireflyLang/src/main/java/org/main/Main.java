package org.main;

import org.ast.Node;
import org.lexer.Lexer;
import org.lexer.Token;
import org.parser.Parser;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String input = new ResManager().getRes("/test.ffl");
        List<Token> tokens = Lexer.tokenize(input);
        Node n = Parser.parse(tokens);
        System.out.println(n.eval());
        //for (Token token : tokens) {
        //    System.out.println(token.toString());
        //}
    }
}