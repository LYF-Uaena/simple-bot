package com.mirai.lyf.bot.common.sort;

/**
 * @author LYF
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


}
