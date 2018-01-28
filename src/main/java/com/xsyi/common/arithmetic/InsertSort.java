package com.xsyi.common.arithmetic;

import java.util.Arrays;

/**
 * Created by yixiaoshuang on 2018/1/26.
 * 插入排序
 */
public class InsertSort {

    /**
     * 插入排序
     * @param arr
     */
    public static void sort(int arr[]){
        int tmp;
        for (int i=1,len = arr.length;i<len;i++){
            tmp =arr[i];
            int j = i-1;
            while(j >= 0 && arr[j] > tmp){
                arr[j+1] = arr[j];
                j--;
            }
            arr[j+1]=tmp;
        }
    }

    /**
     * psvm的
     * @param args
     */
    public static void main(String[] args) {
        int arr[]= {78,12,3,5,89,12,33,34};
        sort(arr);
        System.out.println(Arrays.toString(arr));

    }
}
