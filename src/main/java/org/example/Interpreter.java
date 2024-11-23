package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Interpreter {
    private final Map<String, Integer> variables = new HashMap<>();

    public void execute(List<ASTNode> ast) {
        for (ASTNode node : ast) {
            if (node instanceof LetStatement letStatement) {
                variables.put(letStatement.variable, letStatement.value);
            } else if (node instanceof ShowStatement showStatement) {
                String value = showStatement.variableOrValue;
                if (variables.containsKey(value)) {
                    System.out.println(variables.get(value));
                } else {
                    // Print raw number
                    System.out.println(value);
                }
            }
        }
    }
}
