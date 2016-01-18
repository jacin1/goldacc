package com.test.j2se.sort;

import com.test.j2se.TestOverload;

/**
 * 冒泡排序最终版：减少循环次数
 * @author Administrator
 *
 */
public class TestBubbleSort extends TestOverload{

	public static void main(String[] args) {
		int a[]={44,55,33,67,54,65,43,22,66,98,74};
		sort(a);
		for(int i:a){
			System.out.println(i);
		}
	}
	
	public void testOverrid(String obj){
		System.out.println("child overriding: "+obj);
	}
	
	public static void sort(int[] values){
		int temp;
		boolean sorted=true;
		for(int i=0;i<values.length-1;i++){
			System.out.println("第"+(i+1)+"趟");
			sorted=true;
			for(int j=0;j<values.length-i-1;j++){
				if(values[j] > values[j+1]){
					temp=values[j];
					values[j]=values[j+1];
					values[j+1]=temp;
					sorted=false;
				}
			}
			if(sorted){
				break;
			}
		}
	}

}
