package org.parser;

import org.ast.BinaryNode;
import org.ast.Node;
import org.ast.UnaryNode;
import org.ast.ValueNode;
import org.lexer.Token;
import org.lexer.TokenType;

import java.util.Arrays;
import java.util.List;

public class Parser {

    static List<Token> tokens;
    static int pos = 0;

    public static Node parse(List<Token> ts)
    {
        tokens = ts;
        return getAdditive();
    }

    private static Node getAdditive(){
        Node node1 = getMultiplicative();
        if(match(TokenType.PLUS)){
            return new BinaryNode(getAdditive(), node1, '+');
        }
        else if(match(TokenType.MINUS)){
            return new BinaryNode(getAdditive(), node1, '-');
        }
        else {
            return node1;
        }
    }

    private static Node getMultiplicative(){
        Node node1 = getUnary();
        if(match(TokenType.MUL)){
            return new BinaryNode(node1, getMultiplicative(), '*');
        }
        else if(match(TokenType.DIV)){
            return new BinaryNode(node1, getMultiplicative(), '/');
        }
        else {
            return node1;
        }
    }

    private static Node getUnary(){
        if(match(TokenType.PLUS)){
            return new UnaryNode(getPrimary(), '+');
        }
        else if(match(TokenType.MINUS)){
            return new UnaryNode(getPrimary(), '-');
        }
        else {
            return getPrimary();
        }
    }

    private static Node getPrimary(){
        Token current = tokens.get(pos);
        if(match(TokenType.INT_LITERAL)){
            return new ValueNode(Integer.parseInt(current.getValue()));
        }
        else if(match(TokenType.DOUBLE_LITERAL)){
            return new ValueNode(Double.parseDouble(current.getValue()));
        }
        else if(match(TokenType.CHAR_LITERAL)){
            return new ValueNode(current.getValue().charAt(0));
        }
        else if(match(TokenType.STRING_LITERAL)){
            return new ValueNode(current.getValue());
        }
        else if(match(TokenType.LPAREN)){
            Node node1 = getAdditive();
            consume(TokenType.RPAREN);
            return node1;
        }
        else{
            throw new RuntimeException("Необработанный токен: " + current.toString());
        }
    }

    private static boolean match(TokenType... types){
        Token token = tokens.get(pos);
        if(Arrays.stream(types).toList().contains(token.getTokenType())){
            pos++;
            if(pos >= tokens.size()){
                throw new RuntimeException("Индекс больше велечины массива");
            }
            return true;
        }
        return false;
    }

    private static void consume(TokenType type){
        Token token = tokens.get(pos);
        if(type == token.getTokenType()){
            pos++;
            if(pos >= tokens.size()){
                throw new RuntimeException("Индекс больше велечины массива");
            }
        }
        else{
            throw new RuntimeException("Ожидался токен: " + type +", получили: " + token.toString());
        }
    }

}