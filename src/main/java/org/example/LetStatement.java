package org.example;

public class LetStatement implements ASTNode {
    public final String variable;
    public final int value;

    public LetStatement(String variable, int value) {
        this.variable = variable;
        this.value = value;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "LetStatement(variable=" + variable + ", value=" + value + ")";
    }
}
