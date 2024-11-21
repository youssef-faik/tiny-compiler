package org.example;

public class ShowStatement implements ASTNode {
    public final String variableOrValue;

    public ShowStatement(String variableOrValue) {
        this.variableOrValue = variableOrValue;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "ShowStatement(value=" + variableOrValue + ")";
    }
}