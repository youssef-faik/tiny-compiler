package org.example;

public interface ASTVisitor {
    void visit(LetStatement letStatement);

    void visit(ShowStatement showStatement);
}
