package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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

            // Step 4: Running the Interpreter
            System.out.println("\nExecuting Program...");
            Interpreter interpreter = new Interpreter();
            interpreter.execute(ast);

            // Step 5: Bytecode Generation
            System.out.println("\nGenerating Bytecode...");
            BytecodeGenerator generator = new BytecodeGenerator();
            generator.generate(ast, "GeneratedProgram");

            // Step 6: Run the compiled Java class and capture the output
            System.out.println("\nRunning Compiled Java Class...");
            Process process = Runtime.getRuntime().exec("java GeneratedProgram");

            // Capture standard output from the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);  // Print output from GeneratedProgram
            }

            // Capture error output (if any)
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = errorReader.readLine()) != null) {
                System.err.println(line);  // Print error output
            }

            process.waitFor();  // Wait for process to complete
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}