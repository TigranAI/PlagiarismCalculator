package ru.tigran.PlagiarismCalculator.comparators;

import java.util.ArrayList;
import java.util.Comparator;

public class Levenshtein<T> {
    private final Comparator<T> comparator;

    public Levenshtein(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    // implementation by https://gist.github.com/astromechza/7048328
    public int getDistance(ArrayList<T> a1, ArrayList<T> a2)
    {
        int m = a1.size();
        int n = a2.size();

        int[][] deltaM = new int[m+1][n+1];

        for(int i = 1;i <= m; i++)
        {
            deltaM[i][0] = i;
        }

        for(int j = 1;j <= n; j++)
        {
            deltaM[0][j] = j;
        }

        for(int j=1;j<=n;j++)
        {
            for(int i=1;i<=m;i++)
            {
                if(comparator.compare(a1.get(i-1), a2.get(j-1)) == 0)
                {
                    deltaM[i][j] = deltaM[i-1][j-1];
                }
                else
                {
                    deltaM[i][j] = Math.min(
                            deltaM[i-1][j]+1,
                            Math.min(deltaM[i][j-1]+1, deltaM[i-1][j-1]+1)
                    );
                }
            }
        }

        return deltaM[m][n];
    }
}
