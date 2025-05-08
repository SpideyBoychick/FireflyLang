package org.ast;

public class BinaryNode implements Node{

    char operator;
    Node leftNode, rightNode;

    public BinaryNode(Node leftNode, Node rightNode, char operator) {
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        this.operator = operator;
    }

    @Override
    public String[] compile() {
        return new String[0];
    }

    @Override
    public Object eval() {
        Object val1 = leftNode.eval();
        Object val2 = rightNode.eval();
        if(val1 instanceof Double || val2 instanceof Double){
            if(operator == '+'){
                return (Double)val1 + (Double)val2;
            }
            else if(operator == '-'){
                return (Double)val1 - (Double)val2;
            }
            else if(operator == '*'){
                return (Double)val1 * (Double)val2;
            }
            else if(operator == '/'){
                return (Double)val1 / (Double)val2;
            }
            else {
                throw new RuntimeException("Неизвестная бинарная операция: " + operator);
            }
        }
        else if(val1 instanceof Integer || val2 instanceof Integer){
            if(operator == '+'){
                return (Integer)val1 + (Integer)val2;
            }
            else if(operator == '-'){
                return (Integer)val1 - (Integer)val2;
            }
            else if(operator == '*'){
                return (Integer)val1 * (Integer)val2;
            }
            else if(operator == '/'){
                return (Integer)val1 / (Integer)val2;
            }
            else {
                throw new RuntimeException("Неизвестная бинарная операция: " + operator);
            }
        }
        else {
            throw new RuntimeException("Бинарный оператор " + operator + " неприменим к типам " + leftNode.getClass().getName() + " и " + rightNode.getClass().getName());
        }
    }

    @Override
    public String toString() {
        return leftNode.toString() + operator + rightNode.toString() ;
    }
}
