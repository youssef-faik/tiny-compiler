package org.example;

public interface ASTNode {
    void accept(ASTVisitor visitor);
}

