package ru.tigran.PlagiarismCalculator;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import ru.tigran.PlagiarismCalculator.Comparators.TextBased;
import ru.tigran.PlagiarismCalculator.Comparators.TokenBased;
import ru.tigran.PlagiarismCalculator.Comparators.TreeBased;
import ru.tigran.PlagiarismCalculator.antlr.grammars.cpp.CPP14Lexer;
import ru.tigran.PlagiarismCalculator.antlr.grammars.cpp.CPP14Parser;

import java.io.*;
import java.time.ZonedDateTime;
import java.util.List;

public class CPP14AnalyzeWorker implements Runnable {
    private List<Token> tokens1, tokens2;
    private String sourceCode1, sourceCode2;
    private ParserRuleContext parserRuleContext1, parserRuleContext2;
    private String fileName1, fileName2;
    private long constructorWasted;

    private CPP14AnalyzeWorker() {
    }

    @Override
    public void run() {
        long start = ZonedDateTime.now().toInstant().toEpochMilli();

        double
            textBased = TextBased.getPercentage(sourceCode1, sourceCode2),
            tokenBased = TokenBased.getPercentage(tokens1, tokens2),
            treeBased = TreeBased.getPercentage(parserRuleContext1, parserRuleContext2);


        long stop = ZonedDateTime.now().toInstant().toEpochMilli();

        String result =
                "\n" + fileName1 + " - " + fileName2 + ":" +
                "\n\t\tText based: " + textBased +
                "\n\t\tToken based: " + tokenBased +
                "\n\t\tTree based: " + treeBased +
                "\n\tTime wasted: " + (stop - start) + "ms" +
                "\n\tConstructor: " + constructorWasted + "ms";
        System.out.println(result);
    }

    private CPP14AnalyzeWorker setTokens(List<Token> tokens1, List<Token> tokens2){
        this.tokens1 = tokens1;
        this.tokens2 = tokens2;
        return this;
    }

    private CPP14AnalyzeWorker setSourceCodes(String sourceCode1, String sourceCode2){
        this.sourceCode1 = sourceCode1;
        this.sourceCode2 = sourceCode2;
        return this;
    }

    private CPP14AnalyzeWorker setParserRuleContexts(ParserRuleContext parserRuleContext1, ParserRuleContext parserRuleContext2){
        this.parserRuleContext1 = parserRuleContext1;
        this.parserRuleContext2 = parserRuleContext2;
        return this;
    }

    private CPP14AnalyzeWorker setFileNames(String fileName1, String fileName2){
        this.fileName1 = fileName1;
        this.fileName2 = fileName2;
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
        ParserRuleContext
                parserRuleContext1 = parser1.translationUnit(),
                parserRuleContext2 = parser2.translationUnit();

        String
                sourceCode1 = parserRuleContext1.getChild(0).getText().toLowerCase(),
                sourceCode2 = parserRuleContext2.getChild(0).getText().toLowerCase();
        List<Token>
                tokens1 = tokenStream1.getTokens(),
                tokens2 = tokenStream2.getTokens();

        long constructorStop = ZonedDateTime.now().toInstant().toEpochMilli();

        return new CPP14AnalyzeWorker()
                .setConstructorWasted(constructorStop - constructorStart)
                .setFileNames(file1.getName(), file2.getName())
                .setSourceCodes(sourceCode1, sourceCode2)
                .setTokens(tokens1, tokens2)
                .setParserRuleContexts(parserRuleContext1, parserRuleContext2);
    }
}
