package ru.tigran.PlagiarismCalculator;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.Collections;

public class TreeWalker implements ParseTreeListener {

    @Override
    public void visitTerminal(TerminalNode terminalNode) {

    }

    @Override
    public void visitErrorNode(ErrorNode errorNode) {

    }

    @Override
    public void enterEveryRule(ParserRuleContext parserRuleContext) {
        /*System.out.print(String.join("", Collections.nCopies(parserRuleContext.depth(), "\t")));
        System.out.print(parserRuleContext.getRuleIndex());
        System.out.print("  -  ");
        System.out.println(parserRuleContext.getText());*/
    }

    @Override
    public void exitEveryRule(ParserRuleContext parserRuleContext) {

    }
}