package com.coderpwh.grammar_processor.utils;

import com.coderpwh.grammar_processor.entry.TokenType;

public class TokenUtil {
    public static boolean isComparisonOp(TokenType tokenType) {
        return tokenType == TokenType.EQ||tokenType == TokenType.LE||tokenType == TokenType.GE||tokenType == TokenType.GT||tokenType==TokenType.NQ||tokenType==TokenType.LT;
    }
    public static boolean isAssignment(TokenType tokenType) {
        return tokenType == TokenType.AE||tokenType == TokenType.JE||tokenType == TokenType.Assignment;
    }
}
