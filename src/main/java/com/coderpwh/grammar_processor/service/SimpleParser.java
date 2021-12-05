package com.coderpwh.grammar_processor.service;

import com.coderpwh.grammar_processor.entry.*;
import static com.coderpwh.grammar_processor.utils.TokenUtil.*;
/**
 * 一个简单的语法解析器。
 * 能够解析简单的表达式、变量声明和初始化语句、赋值语句。
 * 它支持的语法规则为：
 *
 * programm -> intDeclare | expressionStatement | assignmentStatement
 * intDeclare -> 'int' Id ( = additive) ';'
 * expressionStatement -> addtive ';'
 * addtive -> multiplicative ( (+ | -) multiplicative)*
 * multiplicative -> primary ( (* | /) primary)*
 * primary -> IntLiteral | Id | (additive)
 *
 */

public class SimpleParser {
    public static void main(String[] args) throws Exception {
/*        String script = "int b = 5; int c = 2+3; c = b+a;";
        SimpleParser parser = new SimpleParser();
        SimpleASTNode node = parser.parse(script);
        parser.dumpAST(node,"");*/
        //test read-stmt
/*        String script = "b+10 and b-10";
        SimpleParser simpleParser = new SimpleParser();
        SimpleASTNode simpleASTNode = simpleParser.testExps(script);
        simpleParser.dumpAST(simpleASTNode,"");*/
        String script = "for fact = x downto 1 do fact = fact*x; enddo; write fact;";
        SimpleParser parser = new SimpleParser();
        SimpleASTNode simpleASTNode = parser.parseGrammar(script);
        StringBuffer sb = new StringBuffer();
        parser.dumpAST(simpleASTNode,"",sb);
        System.out.println(sb.toString());
    }
    public SimpleASTNode testSExp(String code) throws Exception {
        SimpleASTNode node = new SimpleASTNode(ASTNodeType.Programm, "pwc");
        SimpleLexer simpleLexer = new SimpleLexer();
        SimpleTokenReader tokens = simpleLexer.tokenize(code);
        while(tokens.peek()!=null) {
            SimpleASTNode child = simpleExp(tokens);
            if (child!=null) {
                node.addChild(child);
            }

        }
        return node;
    }

    public SimpleASTNode testExps(String code) throws Exception {
        SimpleASTNode node = new SimpleASTNode(ASTNodeType.Programm, "pwc");
        SimpleLexer simpleLexer = new SimpleLexer();
        SimpleTokenReader tokens = simpleLexer.tokenize(code);
        while(tokens.peek()!=null) {
            SimpleASTNode child = expS(tokens);
            if (child!=null) {
                node.addChild(child);
            }

        }
        return node;
    }

    public SimpleASTNode testAs(String code) throws Exception {
        SimpleASTNode node = new SimpleASTNode(ASTNodeType.Programm, "pwc");
        SimpleLexer simpleLexer = new SimpleLexer();
        SimpleTokenReader tokens = simpleLexer.tokenize(code);
        while(tokens.peek()!=null) {
            SimpleASTNode child = assignmentStatement(tokens);
            if (child!=null) {
                node.addChild(child);
            }

        }
        return node;
    }

    public SimpleASTNode testExpStmt(String code) throws Exception {
        SimpleASTNode node = new SimpleASTNode(ASTNodeType.Programm, "pwc");
        SimpleLexer simpleLexer = new SimpleLexer();
        SimpleTokenReader tokens = simpleLexer.tokenize(code);
        while(tokens.peek()!=null) {
            SimpleASTNode child = exp(tokens);
            if (child!=null) {
                node.addChild(child);
            }

        }
        return node;
    }

    public SimpleASTNode testDoWhileStmt(String code) throws Exception {
        SimpleASTNode node = new SimpleASTNode(ASTNodeType.Programm, "pwc");
        SimpleLexer simpleLexer = new SimpleLexer();
        SimpleTokenReader tokens = simpleLexer.tokenize(code);
        while(tokens.peek()!=null) {
            SimpleASTNode child = doWhile(tokens);
            if (child!=null) {
                node.addChild(child);
            }

        }
        return node;
    }

    public SimpleASTNode testForStmt(String code) throws Exception {
        SimpleASTNode node = new SimpleASTNode(ASTNodeType.Programm, "pwc");
        SimpleLexer simpleLexer = new SimpleLexer();
        SimpleTokenReader tokens = simpleLexer.tokenize(code);
        while(tokens.peek()!=null) {
            SimpleASTNode child = forStmt(tokens);
            if (child!=null) {
                node.addChild(child);
            }

        }
        return node;
    }

    public SimpleASTNode testRepeatStmt(String code) throws Exception {
        SimpleASTNode node = new SimpleASTNode(ASTNodeType.Programm, "pwc");
        SimpleLexer simpleLexer = new SimpleLexer();
        SimpleTokenReader tokens = simpleLexer.tokenize(code);
        while(tokens.peek()!=null) {
            SimpleASTNode child = repeatStmt(tokens);
            if (child!=null) {
                node.addChild(child);
            }

        }
        return node;
    }

    public SimpleASTNode testIfStmt(String code) throws Exception {
        SimpleASTNode node = new SimpleASTNode(ASTNodeType.Programm, "pwc");
        SimpleLexer simpleLexer = new SimpleLexer();
        SimpleTokenReader tokens = simpleLexer.tokenize(code);
//        while(tokens.peek()!=null) {
            SimpleASTNode child = ifStmt(tokens);
            if (child!=null) {
                node.addChild(child);
            }

//        }
        return node;
    }


    public SimpleASTNode testStmt(String code) throws Exception {
        SimpleASTNode node = new SimpleASTNode(ASTNodeType.Programm, "pwc");
        SimpleLexer simpleLexer = new SimpleLexer();
        SimpleTokenReader tokens = simpleLexer.tokenize(code);

        SimpleASTNode child = stmtSequence(tokens);
        if (child != null) {
            node.addChild(child);
        }


        return node;
    }

    public SimpleASTNode testStatement(String code) throws Exception {
        SimpleASTNode node = new SimpleASTNode(ASTNodeType.Programm, "pwc");
        SimpleLexer simpleLexer = new SimpleLexer();
        SimpleTokenReader tokens = simpleLexer.tokenize(code);
        while(tokens.peek()!=null) {
            SimpleASTNode child = statement(tokens);
            if (child!=null) {
                node.addChild(child);
            }

        }
        return node;
    }


    public SimpleASTNode testWriteStmt(String code) throws Exception {
        SimpleASTNode node = new SimpleASTNode(ASTNodeType.Programm, "pwc");
        SimpleLexer simpleLexer = new SimpleLexer();
        SimpleTokenReader tokens = simpleLexer.tokenize(code);
        while(tokens.peek()!=null) {
            SimpleASTNode child = writeStmt(tokens);
            if (child!=null) {
                node.addChild(child);
            }

        }
        return node;
    }

    public SimpleASTNode testReadStmt(String code) {
        SimpleASTNode node = new SimpleASTNode(ASTNodeType.Programm, "pwc");
        SimpleLexer simpleLexer = new SimpleLexer();
        SimpleTokenReader tokens = simpleLexer.tokenize(code);
        while(tokens.peek()!=null) {
            SimpleASTNode child = readStmt(tokens);
            if (child!=null) {
                node.addChild(child);
            }

        }
        return node;
    }
    /**
     * 解析脚本，并返回根节点
     * @param code
     * @return
     * @throws Exception
     */
    public SimpleASTNode parseGrammar(String code) throws Exception {
        SimpleLexer lexer = new SimpleLexer();
        TokenReader tokens = lexer.tokenize(code);
        SimpleASTNode rootNode = progGrammar(tokens);
        return rootNode;
    }
    public SimpleASTNode progGrammar(TokenReader tokens) throws Exception {
        SimpleASTNode node = new SimpleASTNode(ASTNodeType.Programm, "program");
        if (tokens.peek()!=null) {
            SimpleASTNode simpleASTNode = stmtSequence(tokens);
            node.addChild(simpleASTNode);
        }
        return node;
    }

    /**
     * 解析脚本，并返回根节点
     * @param code
     * @return
     * @throws Exception
     */
    public SimpleASTNode parse(String code) throws Exception {
        SimpleLexer lexer = new SimpleLexer();
        TokenReader tokens = lexer.tokenize(code);
        SimpleASTNode rootNode = prog(tokens);
        return rootNode;
    }
    /**
     * 语法解析：根节点
     * @return
     * @throws Exception
     */
    public SimpleASTNode prog(TokenReader tokens) throws Exception {
        SimpleASTNode node = new SimpleASTNode(ASTNodeType.Programm, "pwc");

        while (tokens.peek() != null) {
            SimpleASTNode child = intDeclare(tokens);

            if (child == null) {
                child = expressionStatement(tokens);
            }

            if (child == null) {
                child = assignmentStatement(tokens);
            }

            if (child != null) {
                node.addChild(child);
            } else {
                throw new Exception("unknown statement");
            }
        }

        return node;

    }

    /**
     * 整型变量声明，如：
     * int a;
     * int b = 2*3;
     *
     * @return
     * @throws Exception
     */
    public SimpleASTNode intDeclare(TokenReader tokens) throws Exception {
        SimpleASTNode node = null;
        Token token = tokens.peek();
        if (token != null && token.getType() == TokenType.Int) {
            token = tokens.read();
            if (tokens.peek().getType() == TokenType.Identifier) {
                token = tokens.read();
                node = new SimpleASTNode(ASTNodeType.IntDeclaration, token.getText());
                token = tokens.peek();
                if (token != null && token.getType() == TokenType.Assignment) {
                    tokens.read();  //取出等号
                    SimpleASTNode child = additive(tokens);
                    if (child == null) {
                        throw new Exception("invalide variable initialization, expecting an expression");
                    }
                    else{
                        node.addChild(child);
                    }
                }
            } else {
                throw new Exception("variable name expected");
            }

            if (node != null) {
                token = tokens.peek();
                if (token != null && token.getType() == TokenType.SemiColon) {
                    tokens.read();
                } else {
                    System.out.println(token);
                    throw new Exception("invalid statement, expecting semicolon");
                }
            }
        }
        return node;
    }

    /**
     * assignmentStatement -> id = additive||id = str
     * @param tokens
     * @return
     */
    public SimpleASTNode assignmentStatement(TokenReader tokens) throws Exception {
        SimpleASTNode node = null;
        Token token = tokens.peek();    //预读，看看下面是不是标识符
        if (token != null && token.getType() == TokenType.Identifier) {
            token = tokens.read();      //读入标识符
            node = new SimpleASTNode(ASTNodeType.AssignmentStmt, token.getText());
            token = tokens.peek();      //预读，看看下面是不是等号
            if (token != null && isAssignment(token.getType())) {
                node.setText(token.getText());
                tokens.read();          //取出等号
                //看看是不是字符串
                token = tokens.peek();
                if(token!=null&&token.getType()==TokenType.StringLiteral) {
                    SimpleASTNode child = new SimpleASTNode(ASTNodeType.Str,token.getText());
                    tokens.read();
                    node.addChild(child);
                }else {


                    SimpleASTNode child = additive(tokens);
                    if (child == null) {    //出错，等号右面没有一个合法的表达式
                        throw new Exception("invalide assignment statement, expecting an expression");
                    }
                    else{
                        node.addChild(child);   //添加子节点
/*                    token = tokens.peek();  //预读，看看后面是不是分号
                    if (token != null && token.getType() == TokenType.SemiColon) {
                        tokens.read();      //消耗掉这个分号

                    } else {                //报错，缺少分号
                        throw new Exception("invalid statement, expecting semicolon");
                    }*/
                    }

                }

            }
            else {
                tokens.unread();            //回溯，吐出之前消化掉的标识符
                node = null;
            }
        }
        return node;
    }

    /**
     * expressionStatement -> addtive ';'
     * @param tokens
     * @return
     */
    public SimpleASTNode expressionStatement(TokenReader tokens) throws Exception {
        int pos = tokens.getPosition();
        SimpleASTNode node = additive(tokens);
        if (node != null) {
            Token token = tokens.peek();
            if (token != null && token.getType() == TokenType.SemiColon) {
                tokens.read();
            } else {
                node = null;
                tokens.setPosition(pos); // 回溯
            }
        }
        return node;  //直接返回子节点，简化了AST。

    }

    /**
     * expS->exp and|or exp | not exp | exp
     * @param tokens
     * @return
     */
    public SimpleASTNode expS(TokenReader tokens) throws Exception {
        Token token = tokens.peek();
        SimpleASTNode node = null;

        if(token!=null&&token.getType()==TokenType.Not) {
            tokens.read();
            node = new SimpleASTNode(ASTNodeType.ExpS,"not exp");
            return node;
        }
        node = exp(tokens);
        token = tokens.peek();
        if(token!=null&&(token.getType()==TokenType.Or||token.getType()==TokenType.And)) {
            SimpleASTNode temp = new SimpleASTNode(ASTNodeType.ExpS,token.getText());
            tokens.read();
            temp.addChild(node);
            temp.addChild(exp(tokens));
            node = temp;
        }
        return node;
    }

    /**
     * exp -> simple-exp comparison-op simple-exp|simple-exp
     * @param tokens
     * @return
     */
    public SimpleASTNode exp(TokenReader tokens) throws Exception {
        SimpleASTNode node = new SimpleASTNode(ASTNodeType.Exp,"exp");
        SimpleASTNode child = simpleExp(tokens);
        node.addChild(child);
        Token token = tokens.peek();
        if (token != null && isComparisonOp(token.getType())) { //如果有比较符号
            node.setText(tokens.peek().getText());
            tokens.read();
            child = simpleExp(tokens);
            node.addChild(child);
        }



        return node;  //直接返回子节点，简化了AST。

    }

    /**
     * simpleExp ->additive(^|%additive)*
     * @param tokens
     * @return
     * @throws Exception
     */
    public SimpleASTNode simpleExp(TokenReader tokens) throws Exception {
        SimpleASTNode node = additive(tokens);
        SimpleASTNode child1 = node;
        if (child1!=null) {
            while(true) {
                Token token = tokens.peek();
                if (token!=null&&(token.getType()==TokenType.QY||token.getType()==TokenType.CF)) {
                    tokens.read();
                    node = new SimpleASTNode(ASTNodeType.Additive,"simple_exp");
                    SimpleASTNode child2 = additive(tokens);
                    node.addChild(child1);
                    node.addChild(child2);
                    child1 = node;

                }else {
                    break;
                }
            }
        }

        return node;  //直接返回子节点，简化了AST。
    }


    /**
     * primary -> IntLiteral | Id | (additive)
     * @param tokens
     * @return
     */
    public SimpleASTNode primary(TokenReader tokens) throws Exception {
        SimpleASTNode node = null;
        Token token = tokens.peek();
        if (token!=null) {
            if(token.getType() == TokenType.IntLiteral) {
                tokens.read();
                node = new SimpleASTNode(ASTNodeType.IntLiteral, token.getText());
            }
            else if(token.getType() == TokenType.Identifier) {
                tokens.read();
                node = new SimpleASTNode(ASTNodeType.Identifier, token.getText());
            } else if(token.getType() == TokenType.LeftParen) {
                tokens.read();
                node = additive(tokens);
                if(node != null) {
                    token = tokens.peek();
                    if (token!=null && token.getType() == TokenType.RightParen) {
                        tokens.read();
                    }else {
                        throw new Exception("expecting right parenthesis");
                    }
                }else {
                    throw new Exception("expecting an additive expression inside parenthesis");
                }
            }
        }
        return node;
    }

    /**
     * Dowhile-stmt->do stmt-sequence while(expS);
     * @param tokens
     * @return
     */
    public SimpleASTNode doWhile(TokenReader tokens) throws Exception {
        Token token = tokens.peek();
        SimpleASTNode node = null;
        if (token!=null&&token.getType() == TokenType.Do) {
            tokens.read();
            node = new SimpleASTNode(ASTNodeType.Dowhile_stem,"dowhile-stmt");
            SimpleASTNode child = stmtSequence(tokens);
            node.addChild(child);
            token = tokens.peek();
            if(token!=null&&token.getType()==TokenType.While) {
                tokens.read();
                child = expS(tokens);
                node.addChild(child);
            }else {
                throw new IllegalArgumentException("缺少while");
            }
        }
        return node;
    }

    /**
     * for-stmt->for identifier=simple-exp to simple-exp do stmt-sequence enddo
     * @param tokens
     * @return
     */
    public SimpleASTNode forStmt(TokenReader tokens) throws Exception {
        Token token = tokens.peek();
        SimpleASTNode node = null;
        if(token!=null&&token.getType() == TokenType.For) {
            node = new SimpleASTNode(ASTNodeType.For_stmt,"for-stmt");
            tokens.read();
            token = tokens.peek();
            if(token!=null&&token.getType() == TokenType.Identifier) {
                node.setText(token.getText());
                tokens.read();
                token = tokens.peek();
                if(token!=null&&token.getType() == TokenType.Assignment){
                    tokens.read();
                    SimpleASTNode child = simpleExp(tokens);
                    token = tokens.peek();
                    node.addChild(child);
                    if(token!=null&&(token.getType()==TokenType.To||token.getType()==TokenType.Downto)){
                        tokens.read();
                        child = simpleExp(tokens);
                        node.addChild(child);
                        //读取do
                        token = tokens.peek();
                        if(token!=null&&token.getType()==TokenType.Do) {
                            tokens.read();
                            child = stmtSequence(tokens);
                            node.addChild(child);
                            token = tokens.peek();
                            if(token!=null&&token.getType()==TokenType.EndDo) {
                                tokens.read();
                            }else {
                                throw new IllegalArgumentException("缺少enddo");
                            }
                        }else {
                            throw new IllegalArgumentException("缺少do");
                        }
                    }else {
                        throw new IllegalArgumentException("缺少to");
                    }
                }
            }else {
                throw new IllegalArgumentException("缺少identifier");
            }
        }
        return node;
    }

    /**
     * if-stmt->if expS then stmt-sequence end|if expS then stmt-sequence else stmt-sequence end
     * @param tokens
     * @return
     */
    public SimpleASTNode ifStmt(TokenReader tokens) throws Exception {
        SimpleASTNode node = null;
        Token token = tokens.peek();
        if(token!=null&&token.getType() == TokenType.If) {
            tokens.read();
            node = new SimpleASTNode(ASTNodeType.If_stmt,"if-stmt");
            SimpleASTNode child = expS(tokens);
            node.addChild(child);
            //处理then和stmt-sequence
            token = tokens.peek();
            if(token!=null&&token.getType() == TokenType.Then) {
                tokens.read();
                child = stmtSequence(tokens);
                node.addChild(child);
                //处理end或者else
                token = tokens.peek();
                if(token!=null) {
                    if(token.getType() == TokenType.End) {
                        tokens.read();
                    }else if(token.getType() == TokenType.Else) {
                        tokens.read();
                        child = stmtSequence(tokens);
                        //读取end
                        System.out.println("the child is "+child.getText());
                        node.addChild(child);
                        token = tokens.peek();
                        if(token!=null&&token.getType() == TokenType.End) {
                            tokens.read();
                        }else {
                            System.out.println(token);
                            throw new IllegalArgumentException("缺少end");
                        }
                    }
                }else {
                    throw new IllegalArgumentException("缺少end或者else");
                }
            }

        }
        return node;
    }

    /**
     * repeat-stmt->repeat stmt-sequence until exp
     * @param tokens
     * @return
     * @throws Exception
     */
    public SimpleASTNode repeatStmt(TokenReader tokens) throws Exception {
        Token token = tokens.peek();
        SimpleASTNode node = null;
        if(token!=null&&token.getType() == TokenType.Repeat) {
            tokens.read();
            node = new SimpleASTNode(ASTNodeType.Repeat_stmt,"repeat");
            SimpleASTNode child = stmtSequence(tokens);
            token = tokens.peek();
            node.addChild(child);
            if(token!=null&&token.getType() == TokenType.Until) {
                tokens.read();
                child = expS(tokens);
                node.addChild(child);
            }else {
                throw new IllegalArgumentException("缺少until关键字");
            }
        }
        return node;
    }

    /**
     * stmt->stmt;statement|statement
     * @param tokens
     * @return
     */
    public SimpleASTNode stmtSequence(TokenReader tokens) throws Exception {
        Token token = tokens.peek();
        SimpleASTNode node = null;
        if(token!=null) {
            node = new SimpleASTNode(ASTNodeType.Stmt_sequence,"stmt_sequence");
            SimpleASTNode child = null;
            child = statement(tokens);
            while(child!=null) {
                //消耗;好
                tokens.read();
                node.addChild(child);
                child = statement(tokens);
            }
        }
        return node;
    }

    /**
     * statement->if-stmt|repeat-stmt|assign-stmt|read-stmt|write-stmt|for-stmt|dowhile-stmt
     * @param tokens
     * @return
     */
    public SimpleASTNode statement(TokenReader tokens) throws Exception {
        Token token = tokens.peek();
        SimpleASTNode node = null;
        if(token!=null) {
            SimpleASTNode child = null;
            child = assignmentStatement(tokens);
            if(child == null) {
                child = ifStmt(tokens);
            }
            if(child == null) {
                child = forStmt(tokens);
            }
            if(child == null) {
                child = doWhile(tokens);
            }
            if(child == null) {
                child = repeatStmt(tokens);
            }
            if (child == null) {
                child = readStmt(tokens);
            }
            if (child == null) {
                child = writeStmt(tokens);
            }
            if(child!=null) {
                node = new SimpleASTNode(ASTNodeType.Statement,"statement");
                node.addChild(child);
            }
        }
        return node;
    }

    /**
     * read-stmt->read identifier
     * @param tokens
     * @return
     */
    public SimpleASTNode readStmt(TokenReader tokens) {
        Token token = tokens.peek();
        SimpleASTNode node = null;
        if(token!=null&&token.getType() == TokenType.READ) {
            tokens.read();
            token = tokens.peek();
            if (token.getType()!=TokenType.Identifier) {
                throw new IllegalArgumentException("不为identifier,不合法");
            }else {
                tokens.read();
                node = new SimpleASTNode(ASTNodeType.Read_stmt,token.getText());
/*                //读取;号
                tokens.read();*/
            }
        }
        return node;
    }

    /**
     * write-stmt->write exp
     * @param tokens
     * @return
     * @throws Exception
     */
    public SimpleASTNode writeStmt(TokenReader tokens) throws Exception {
        Token token = tokens.peek();
        SimpleASTNode node = null;
        if(token!=null&&token.getType() == TokenType.WRITE){
            tokens.read();
            SimpleASTNode simpleASTNode = expS(tokens);
            node = new SimpleASTNode(ASTNodeType.Write_stem,"write");
            node.addChild(simpleASTNode);


        }
        return node;
    }







    /**
     * addtive -> multiplicative ( (+ | -) multiplicative)*
     * @param tokens
     * @return
     */
    public SimpleASTNode additive(TokenReader tokens) throws Exception {
        SimpleASTNode child1 = multiplicative(tokens);
        SimpleASTNode node = child1;
        if (child1!=null) {
            while(true) {
                Token token = tokens.peek();
                if(token!=null && (token.getType() == TokenType.Plus || token.getType() == TokenType.Minus)) {
                    tokens.read();
                    SimpleASTNode child2 = multiplicative(tokens);
                    node = new SimpleASTNode(ASTNodeType.Additive,token.getText());
                    node.addChild(child1);
                    node.addChild(child2);
                    child1 = node;
                }else {
                    break;
                }
            }
        }
        return node;
    }

    /**
     * multiplicative -> primary ( (* | /) primary)*
     * @param tokens
     * @return
     */
    public SimpleASTNode multiplicative(TokenReader tokens) throws Exception {
        SimpleASTNode child1 = primary(tokens);
        SimpleASTNode node = child1;
        Token token = tokens.peek();
        if(child1!=null && token!=null) {
            if(token.getType() == TokenType.Star || token.getType() == TokenType.Slash) {
                tokens.read();
                SimpleASTNode child2 = multiplicative(tokens);
                if(child2!=null){
                    node = new SimpleASTNode(ASTNodeType.Multiplicative,token.getText());
                    node.addChild(child1);
                    node.addChild(child2);
                }else {
                    throw new Exception("invalid multiplicative expression, expecting the right part.");
                }
            }
        }
        return node;
    }

    /**
     * 打印输出AST的树状结构
     * @param node
     * @param indent 缩进字符，由tab组成，每一级多一个tab
     */
    public void dumpAST(ASTNode node, String indent) {
        System.out.println(indent + node.getType() + " " + node.getText());
        for (ASTNode child : node.getChildren()) {
            dumpAST(child, indent + "\t");
        }
    }
    public void dumpAST(ASTNode node,String indent,StringBuffer sb) {
        sb.append(indent + node.getType() + " " + node.getText()+"\n");
        for (ASTNode child : node.getChildren()) {
            dumpAST(child, indent + "\t",sb);
        }
    }
}
