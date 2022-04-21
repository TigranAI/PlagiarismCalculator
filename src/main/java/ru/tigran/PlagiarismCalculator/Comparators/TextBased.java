package ru.tigran.PlagiarismCalculator.Comparators;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class TextBased {
    public static double getPercentage(String first, String second) {
        Levenshtein<Character> levenshtein = new Levenshtein<>(Comparator.comparingInt(item -> item));
        int distance = levenshtein.getDistance(ToArray(first), ToArray(second));
        int max = Math.max(first.length(), second.length());
        return 100 * ((double) (max - distance)) / (double) max;
    }

    private static ArrayList<Character> ToArray(String str) {
        return str
            .chars()
            .mapToObj((i) -> (char) i)
            .collect(Collectors.toCollection(ArrayList::new));
    }
}