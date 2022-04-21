package ru.tigran.PlagiarismCalculator;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import ru.tigran.PlagiarismCalculator.Comparators.TextBased;
import ru.tigran.PlagiarismCalculator.Comparators.TokenBased;
import ru.tigran.PlagiarismCalculator.antlr.grammars.cpp.CPP14Lexer;
import ru.tigran.PlagiarismCalculator.antlr.grammars.cpp.CPP14Parser;

import java.io.*;
import java.time.ZonedDateTime;
import java.util.List;

public class CPP14AnalyzeWorker implements Runnable {
    private final List<Token> tokens1, tokens2;
    private final String text1, text2;
    private String name1, name2;
    private long constructorWasted;

    public CPP14AnalyzeWorker(List<Token> tokens1, List<Token> tokens2, String text1, String text2) {
        this.tokens1 = tokens1;
        this.tokens2 = tokens2;
        this.text1 = text1;
        this.text2 = text2;
    }

    @Override
    public void run() {
        long start = ZonedDateTime.now().toInstant().toEpochMilli();

        double
                textBased = TextBased.getPercentage(text1, text2),
                tokenBased = TokenBased.getPercentage(tokens1, tokens2);


        long stop = ZonedDateTime.now().toInstant().toEpochMilli();

        String result =
                "\n" + name1 + " - " + name2 + ":" +
                "\n\t\tText based: " + textBased +
                "\n\t\tToken based: " + tokenBased +
                "\n\tTime wasted: " + (stop - start) + "ms" +
                "\n\tConstructor: " + constructorWasted + "ms";
        System.out.println(result);
    }

    private CPP14AnalyzeWorker setFileNames(String name1, String name2){
        this.name1 = name1;
        this.name2 = name2;
        return this;
    }

    private CPP14AnalyzeWorker setConstructorWasted(long millis){
        this.constructorWasted = millis;
        return this;
    }

    public static CPP14AnalyzeWorker fromFiles(File file1, File file2) throws IOException {
        long constructorStart = ZonedDateTime.now().toInstant().toEpochMilli();

        BufferedReader
                reader1 = new BufferedReader(new FileReader(file1)),
                reader2 = new BufferedReader(new FileReader(file2));
        CPP14Lexer
                lexer1 = new CPP14Lexer(CharStreams.fromReader(reader1)),
                lexer2 = new CPP14Lexer(CharStreams.fromReader(reader2));
        CommonTokenStream
                tokenStream1 = new CommonTokenStream(lexer1),
                tokenStream2 = new CommonTokenStream(lexer2);
        CPP14Parser
                parser1 = new CPP14Parser(tokenStream1),
                parser2 = new CPP14Parser(tokenStream2);
        ParseTree
                parseTree1 = parser1.translationUnit(),
                parseTree2 = parser2.translationUnit();

        String
                sourceCode1 = parseTree1.getChild(0).getText().toLowerCase(),
                sourceCode2 = parseTree2.getChild(0).getText().toLowerCase();
        List<Token>
                tokens1 = tokenStream1.getTokens(),
                tokens2 = tokenStream2.getTokens();

        long constructorStop = ZonedDateTime.now().toInstant().toEpochMilli();

        return new CPP14AnalyzeWorker(tokens1, tokens2, sourceCode1, sourceCode2)
                .setFileNames(file1.getName(), file2.getName())
                .setConstructorWasted(constructorStop - constructorStart);
    }
}
