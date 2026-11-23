import java.util.Arrays;
import java.util.Scanner;

/**
 * 共享单车
 * <p>
 * url：https://www.lanqiao.cn/problems/21592/learning/?page=1
 *
 */

public class day_01 {


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int n = scan.nextInt();
        int m = scan.nextInt();

        int[] a = new int[n];
        int[] b = new int[m];

        for (int i = 0; i < n; i++) {
            a[i] = scan.nextInt();
        }

        // 修复：应该读取 m 个停车点
        for (int j = 0; j < m; j++) {
            b[j] = scan.nextInt();
        }

        Arrays.sort(a);
        Arrays.sort(b);

        System.out.println(dp(a, b));
        scan.close();
    }

    public static long dp(int[] a, int[] b) {
        int n = a.length;
        int m = b.length;

        long INF = Long.MAX_VALUE / 4;

        // dp 保存上一行，初始 dp[0][j] = 0
        long[] dp = new long[m + 1];

        for (int i = 1; i <= n; i++) {
            long[] next = new long[m + 1];
            Arrays.fill(next, INF);

            // j < i 时停车点数量不足
            for (int j = i; j <= m; j++) {

                // 不使用第 j 个停车点
                long skip = next[j - 1];

                // 使用第 j 个停车点
                long distance =
                        Math.abs((long) a[i - 1] - b[j - 1]);

                long use = dp[j - 1] + distance;

                next[j] = Math.min(skip, use);
            }

            dp = next;
        }

        return dp[m];
    }
}

