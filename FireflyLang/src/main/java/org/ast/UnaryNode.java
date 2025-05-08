package org.ast;

public class UnaryNode implements Node{

    char operator;
    Node node;

    public UnaryNode(Node node, char operator){
        this.operator = operator;
        this.node = node;
    }

    @Override
    public String[] compile() {
        return new String[] {""};
    }

    @Override
    public Object eval() {
        Object val = node.eval();
        if(val instanceof Integer){
            if(operator == '+'){
                return val;
            }
            else if(operator == '-'){
                return -(Integer)val;
            }
            else {
                throw new RuntimeException("Неизвестная унарная операция: " + operator);
            }
        }
        else if(val instanceof Double){
            if(operator == '+'){
                return val;
            }
            else if(operator == '-'){
                return -(Double)val;
            }
            else {
                throw new RuntimeException("Неизвестная унарная операция: " + operator);
            }
        }
        else {
            throw new RuntimeException("Унарный оператор " + operator + " неприменим к типу " + node.getClass().getName());
        }
    }

    @Override
    public String toString() {
        return operator + node.toString();
    }
}
