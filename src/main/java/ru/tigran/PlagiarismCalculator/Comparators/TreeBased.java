package ru.tigran.PlagiarismCalculator.Comparators;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;

import java.util.*;

public class TreeBased {
    public static double getPercentage(ParserRuleContext first, ParserRuleContext second){
        List<Integer>
            hashTree1 = new LinkedList<>(),
            hashTree2 = new LinkedList<>();

        toHashTree(first, hashTree1);
        toHashTree(second, hashTree2);

        Levenshtein<Integer> levenshtein = new Levenshtein<>(Comparator.comparingInt(Integer::intValue));
        int distance = levenshtein.getDistance(new ArrayList<>(hashTree1), new ArrayList<>(hashTree2));
        int max = Math.max(hashTree1.size(), hashTree2.size());
        return 100 * ((double) (max - distance)) / (double) max;
    }

    private static void toHashTree(ParseTree root, List<Integer> hashList) {
        toHashTree(root, hashList, 0);
    }

    private static int toHashTree(ParseTree node, List<Integer> hashList, int depth){
        if (node instanceof RuleNode){
            RuleNode ruleNode = (RuleNode) node;

            int nodeHash = getHashCode(ruleNode.getRuleContext(), depth);

            for (int i = 0; i < ruleNode.getChildCount(); ++i){
                nodeHash = Objects.hash(
                        nodeHash,
                        toHashTree(ruleNode.getChild(i), hashList,depth + 1)
                );
            }

            hashList.add(nodeHash);
            return nodeHash;
        }
        return 0;
    }

    private static int getHashCode(RuleContext root, int depth){
        return Objects.hash(root.getRuleIndex(), depth);
    }
}
