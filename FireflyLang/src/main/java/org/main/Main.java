package org.main;

import org.lexer.Lexer;
import org.lexer.Token;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String input = new ResManager().getRes("/test.ffl");
        List<Token> tokens = Lexer.tokenize(input);
        for (Token token : tokens) {
            System.out.println(token.toString());
        }
    }
}