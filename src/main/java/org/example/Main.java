package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String input = "X = 10\nY = 20\nSHOW X\nSHOW 30";

        // Step 1: Lexing
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();

        System.out.println("Tokens:");
        System.out.println(tokens);

        // Step 2: Parsing
        Parser parser = new Parser(tokens);
        List<ASTNode> ast = parser.parse();

        System.out.println("\nAST:");

        for (ASTNode node : ast) {
            System.out.println(node);
        }

        // Step 3: Semantic Analysis
        System.out.println("\nPerforming Semantic Analysis...");
        try {
            SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer();
            semanticAnalyzer.analyze(ast);
            System.out.println("Semantic Analysis Passed ✅");
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }
}