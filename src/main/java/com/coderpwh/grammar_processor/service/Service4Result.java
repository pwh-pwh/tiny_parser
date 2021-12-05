package com.coderpwh.grammar_processor.service;

import com.coderpwh.grammar_processor.entry.SimpleASTNode;

public class Service4Result {
    private SimpleParser parser = new SimpleParser();
    public String create(String code) throws Exception {
        System.out.println("code :"+code);
        SimpleASTNode node = parser.parseGrammar(code);
        StringBuffer sb = new StringBuffer();
        parser.dumpAST(node,"",sb);
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        Service4Result service4Result = new Service4Result();
        System.out.println("你金佛");
        String s = service4Result.create("for fact = x downto 1 do fact = fact*x; enddo; write fact;");
        System.out.println(s+"这是我打印的");
    }


}
