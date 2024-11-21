package org.example;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int position = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public List<ASTNode> parse() {
        List<ASTNode> statements = new ArrayList<>();

        while (!match(TokenType.EOF)) {
            statements.add(parseStatement());
        }

        return statements;
    }

    private ASTNode parseStatement() {
        if (match(TokenType.VAR) && matchNext(TokenType.EQUALS_OPERATOR)) {
            return parseLetStatement();
        } else if (match(TokenType.SHOW)) {
            return parseShowStatement();
        } else {
            throw new RuntimeException("Unexpected token: " + peek().getValue());
        }
    }

    private LetStatement parseLetStatement() {
        String variable = consume(TokenType.VAR).getValue();
        consume(TokenType.EQUALS_OPERATOR);
        int value = Integer.parseInt(consume(TokenType.INT).getValue());
        return new LetStatement(variable, value);
    }

    private ShowStatement parseShowStatement() {
        consume(TokenType.SHOW);
        String value = consume(TokenType.VAR, TokenType.INT).getValue();
        return new ShowStatement(value);
    }

    private boolean match(TokenType type) {
        if (position < tokens.size() && tokens.get(position).getType() == type) {
            return true;
        }
        return false;
    }

    private boolean matchNext(TokenType type) {
        return (position + 1 < tokens.size()) && (tokens.get(position + 1).getType() == type);
    }

    private Token consume(TokenType... expectedTypes) {
        if (position >= tokens.size()) {
            throw new RuntimeException("Unexpected end of input");
        }
        Token token = tokens.get(position);
        for (TokenType type : expectedTypes) {
            if (token.getType() == type) {
                position++;
                return token;
            }
        }
        throw new RuntimeException("Unexpected token: " + token.getValue());
    }

    private Token peek() {
        return tokens.get(position);
    }
}
