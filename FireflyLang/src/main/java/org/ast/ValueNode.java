package org.ast;

public class ValueNode implements Node{
    Object value;

    public ValueNode(Object value){
        this.value = value;
    }

    @Override
    public String[] compile(){
        return new String[]{""};
    }

    @Override
    public Object eval() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
