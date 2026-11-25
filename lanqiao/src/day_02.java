import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 四平方和
 * url: https://www.lanqiao.cn/problems/122/learning/?page=1&first_category_id=1
 *
 */

public class day_02 {


//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        int n = scanner.nextInt();
//        ArrayList<Integer> arrayList = new ArrayList<>();
//        for (int i = 0; i < Math.sqrt(n); i++) {
//            arrayList.add(i);
//        }
//
//        for (int i = 0; i < arrayList.size(); i++) {
//            for (int j = i; j < arrayList.size(); j++) {
//                for (int k = j; k < arrayList.size(); k++) {
//                    for (int l = k; l < arrayList.size(); l++) {
//                        if (n == i * i + j * j + k * k + l * l) {
//                            System.out.println(i + " " + j + " " + k + " " + l + " ");
//                            System.exit(1);
//                        }
//                    }
//                }
//            }
//        }
//
//        scanner.close();
//    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        int max = (int) Math.sqrt(n);

        // 最多需要存储的二元组数量
        long[] pairs = new long[(max + 1) * (max + 2) / 2];

        int count = 0;

        // 1. 预处理所有 c^2 + d^2
        for (int c = 0; c <= max; c++) {
            for (int d = c; d <= max; d++) {

                int sum = c * c + d * d;

                if (sum > n) {
                    break;
                }

                // 高位存 sum，中间12位存 c，低12位存 d
                pairs[count++] =
                        ((long) sum << 24)
                                | ((long) c << 12)
                                | d;
            }
        }

        pairs = Arrays.copyOf(pairs, count);

        // 2. 排序
        Arrays.sort(pairs);

        // 3. 枚举 a、b
        for (int a = 0; a <= max; a++) {
            for (int b = a; b <= max; b++) {

                int remaining = n - a * a - b * b;

                if (remaining < 0) {
                    break;
                }

                // 4. 二分查找第一个 sum=remaining 且 c>=b 的位置
                long target = ((long) remaining << 24)
                        | ((long) b << 12);

                int left = 0;
                int right = count;

                while (left < right) {
                    int mid = left + (right - left) / 2;

                    if (pairs[mid] < target) {
                        left = mid + 1;
                    } else {
                        right = mid;
                    }
                }

                // 5. 判断是否找到
                if (left < count &&
                        (pairs[left] >>> 24) == remaining) {

                    int c = (int) ((pairs[left] >>> 12) & 0xFFF);
                    int d = (int) (pairs[left] & 0xFFF);

                    System.out.println(a + " " + b + " " + c + " " + d);
                    return;
                }
            }
        }

        scanner.close();
    }
}

