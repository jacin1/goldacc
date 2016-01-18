package com.test.j2se.collection.list;

import java.util.LinkedList;

/**
 * 1.实现双向链表
 * 
 * @author Nick
 *
 */
public class SxtLinkedList {
	
	private Node first;
	private Node last;
	private int size;
	
	public void add(Object obj){
		if(null == first){
			Node node=new Node(null,obj,null);
			first=node;
			last=node;
		}else{
			Node node=new Node(last,obj,null);
			last.setNext(node);
			last=node;
		}
		size++;
	}
	
	public int getSize(){
		return size;
	}
	
	
	public static void main(String[] args) {
		SxtLinkedList list=new SxtLinkedList();
		list.add("a");
		list.add("b");
		list.add("c");
		System.out.println(list.size);
//		LinkedList<E>
		
		System.out.println("111="+ (5>>1));
	}
}
