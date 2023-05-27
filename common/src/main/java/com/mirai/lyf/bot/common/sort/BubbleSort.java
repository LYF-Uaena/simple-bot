package com.mirai.lyf.bot.common.sort;

/**
 * The type Bubble sort.
 *
 * @author LYF
 * @since 2023年05月27日 22:25
 */
public class BubbleSort {
    /**
     * 冒泡排序
     *
     * @param arr the arr
     */
    private static void bubbleSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    /**
     * 插入排序
     *
     * @param arr the arr
     * @param len the len
     */
    public void insertion_sort(int[] arr, int len) {
        int i, j, key;
        for (i = 1; i < len; i++) {
            key = arr[i];
            j = i - 1;
            while ((j >= 0) && (arr[j] > key)) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    /**
     * 希尔排序
     */
    public void shellSort(int[] arr, int len) {
        int increasement = len;
        int i, j, k;
        do {
            // 确定分组的增量
            increasement = increasement / 3 + 1;
            for (i = 0; i < increasement; i++) {
                for (j = i + increasement; j < len; j += increasement) {
                    if (arr[j] < arr[j - increasement]) {
                        int temp = arr[j];
                        for (k = j - increasement; k >= 0 && temp < arr[k]; k -= increasement) {
                            arr[k + increasement] = arr[k];
                        }
                        arr[k + increasement] = temp;
                    }
                }
            }
        } while (increasement > 1);
    }

}
