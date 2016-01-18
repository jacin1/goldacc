package com.test.j2se.sort;

/**
 * 快速排序:http://blog.csdn.net/wangkuifeng0118/article/details/7286332
 * 1.快速排序可以看做是对冒泡排序的升级版,时间复杂度O(nlogn)
 * 2.基本思想：通过一趟排序将待排序记录分割成独立的两部分,其中一部分比另一部分小,则使用递归分别对这两部分继续进行排序。
 * 
 * @author Nick
 * 
 */
public class TestQuickSort {

	public int partition(int[] a, int first, int end) {
		int i = first;
		int j = end;
		while (i < j) {
			while (i < j && a[i] < a[j])
				j--;
			if (i < j) {
				int temp;
				temp = a[i];
				a[i] = a[j];
				a[j] = temp;
				i++;
			}
			while (i < j && a[i] <= a[j])
				i++;
			if (i < j) {
				int temp;
				temp = a[j];
				a[j] = a[i];
				a[i] = temp;
				j--;
			}

		}
		return i;
	}

	public void quickSort(int[] a, int first, int end) {
		System.out.println("11");
		if (first < end) {
			int pivot=partition(a, first, end);
			quickSort(a, first, pivot-1);
			quickSort(a, pivot+1, end);
		}

	}
	

	public static void main(String[] args) {
		int[] data = new int[]{44,55,33,67,54,65,43,22,66,98,74};  
        TestQuickSort quickSort = new TestQuickSort();  
        quickSort.quickSort(data,0,data.length-1);    
      
        for(int i = 0 ; i<data.length; i++){  
            System.out.print(data[i]+" ");  
        }  
	}

}
