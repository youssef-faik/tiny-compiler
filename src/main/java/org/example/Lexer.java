package org.example;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final String input;
    private int position = 0;

    public Lexer(String input) {
        this.input = input;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        while (position < input.length()) {
            char currentChar = input.charAt(position);

            if (Character.isWhitespace(currentChar)) {
                position++; // Skip whitespace
            } else if (Character.isLetter(currentChar)) {
                tokens.add(readVariableOrKeyword());
            } else if (Character.isDigit(currentChar)) {
                tokens.add(readNumber());
            } else if (currentChar == '=') {
                tokens.add(new Token(TokenType.EQUALS_OPERATOR, "="));
                position++;
            } else {
                throw new RuntimeException("Unexpected character: " + currentChar);
            }
        }

        tokens.add(new Token(TokenType.EOF, "")); // End of file token
        return tokens;
    }

    private Token readVariableOrKeyword() {
        StringBuilder buffer = new StringBuilder();
        while (position < input.length() && Character.isLetter(input.charAt(position))) {
            buffer.append(input.charAt(position));
            position++;
        }
        String text = buffer.toString();
        if (text.equals("SHOW")) {
            return new Token(TokenType.SHOW, text);
        } else {
            return new Token(TokenType.VAR, text);
        }
    }

    private Token readNumber() {
        StringBuilder buffer = new StringBuilder();
        while (position < input.length() && Character.isDigit(input.charAt(position))) {
            buffer.append(input.charAt(position));
            position++;
        }
        return new Token(TokenType.INT, buffer.toString());
    }
}
