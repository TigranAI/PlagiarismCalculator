package ru.tigran.PlagiarismCalculator;

import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.tigran.PlagiarismCalculator.antlr.grammars.cpp.CPP14Lexer;
import ru.tigran.PlagiarismCalculator.antlr.grammars.cpp.CPP14Parser;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.util.Arrays;

@SpringBootApplication
public class PlagiarismCalculatorApplication {

    public static void main(String[] args) throws IOException {
        /*SpringApplication.run(PlagiarismCalculatorApplication.class, args);*/

        CPP14Lexer lexer1 = new CPP14Lexer(CharStreams.fromFileName("D:\\Projects\\PlagiarismExample\\first.cpp"));
        CPP14Lexer lexer2 = new CPP14Lexer(CharStreams.fromFileName("D:\\Projects\\PlagiarismExample\\second.cpp"));

        CommonTokenStream tokenStream = new CommonTokenStream(lexer2);
        tokenStream.fill();
        CPP14Parser parser = new CPP14Parser(tokenStream);
        ParseTree tree = parser.translationUnit();

        TreeViewer viewer = new TreeViewer(Arrays.asList(
                parser.getRuleNames()), tree);
        viewer.setScale(1);
        viewer.setAutoscrolls(true);

        JPanel contentPane = new JPanel();
        contentPane.add(viewer);
        contentPane.setSize(new Dimension(10000, 10000));
        JScrollPane scrollPane = new JScrollPane(contentPane);


        JFrame frame = new JFrame("Antlr AST");
        frame.setContentPane(scrollPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

        ParseTreeWalker.DEFAULT.walk(new TreeWalker(), tree);
    }
}
