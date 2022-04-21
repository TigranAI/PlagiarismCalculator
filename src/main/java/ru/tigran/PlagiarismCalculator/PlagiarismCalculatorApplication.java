package ru.tigran.PlagiarismCalculator;

import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@SpringBootApplication
public class PlagiarismCalculatorApplication {

    public static void main(String[] args) throws IOException, InterruptedException {
        File inputDir = new File("D:\\Projects\\PlagiarismExample");
        File[] files = inputDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".cpp"));

        for (int i = 0; i < files.length - 1; ++i){
            for (int j = i+1; j < files.length; ++j) {
                CPP14AnalyzeWorker analyzeWorker = CPP14AnalyzeWorker.fromFiles(files[i], files[j]);
                new Thread(analyzeWorker).start();
            }
        }

        /*WalkTree(tree);*/
        /*VisualizeTree(tree, parser);*/
    }

    private static void WalkTree(ParseTree tree) {
        ParseTreeWalker.DEFAULT.walk(new TreeWalker(), tree);
    }
    private static void VisualizeTree(ParseTree tree, Parser parser){
        TreeViewer viewer = new TreeViewer(Arrays.asList(parser.getRuleNames()), tree);
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
    }
}
