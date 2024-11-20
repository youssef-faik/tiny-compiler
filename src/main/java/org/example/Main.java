package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String input = "X = 10\nY = 20\nSHOW X\nSHOW 30";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();

        for (Token token : tokens) {
            System.out.println(token);
        }
    }
}