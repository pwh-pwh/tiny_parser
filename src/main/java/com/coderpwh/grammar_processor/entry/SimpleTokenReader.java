package com.coderpwh.grammar_processor.entry;

import java.util.List;
/**
 * 一个简单的Token流。是把一个Token列表进行了封装。
 */
public class SimpleTokenReader implements TokenReader {
    List<Token> tokens = null;
    int pos = 0;

    public SimpleTokenReader(List<Token> tokens) {
        this.tokens = tokens;
    }
//过滤掉注释
    @Override
    public Token read() {
        if (pos < tokens.size()) {
            int t = pos;
            if (tokens.get(t).getType() == TokenType.ZSe) {
                if (t+1<tokens.size()){
                    pos++;
                    return tokens.get(pos++);
                }else {
                    return null;
                }
            }
            return tokens.get(pos++);
        }
        return null;
    }

    @Override
    public Token peek() {
        if (pos < tokens.size()) {
            return tokens.get(pos);
        }
        return null;
    }

    @Override
    public void unread() {
        if (pos > 0) {
            pos--;
        }
    }

    @Override
    public int getPosition() {
        return pos;
    }

    @Override
    public void setPosition(int position) {
        if (position >=0 && position < tokens.size()){
            pos = position;
        }
    }

}

