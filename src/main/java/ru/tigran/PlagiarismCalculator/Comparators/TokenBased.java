package ru.tigran.PlagiarismCalculator.Comparators;

import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TokenBased {
    public static double getPercentage(List<Token> first, List<Token> second){
        Levenshtein<Token> levenshtein = new Levenshtein<>(Comparator.comparing(Token::getType));
        int distance = levenshtein.getDistance(new ArrayList<>(first), new ArrayList<>(second));
        int max = Math.max(first.size(), second.size());
        return 100 * ((double) (max - distance)) / (double) max;
    }
}
