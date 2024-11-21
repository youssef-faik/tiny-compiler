package org.example;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SemanticAnalyzer {
    private final Set<String> declaredVariables = new HashSet<>();

    public void analyze(List<ASTNode> ast) {
        for (ASTNode node : ast) {
            if (node instanceof LetStatement letStatement) {
                declaredVariables.add(letStatement.variable);
            } else if (node instanceof ShowStatement showStatement) {
                if (!declaredVariables.contains(showStatement.variableOrValue) &&
                        !isInteger(showStatement.variableOrValue)) {
                    throw new RuntimeException("Semantic Error: Variable '" + showStatement.variableOrValue + "' is not declared before use.");
                }
            }
        }
    }

    private boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
