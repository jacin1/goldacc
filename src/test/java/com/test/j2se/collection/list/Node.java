package com.test.j2se.collection.list;

/**
 * 用来表示一个节点 
 * @author Nick
 *
 */
public class Node {

	 Node previos;
	 Object obj;
	 Node next;
	
	public Node(){}

	public Node(Node previos, Object obj, Node next) {
		super();
		this.previos = previos;
		this.obj = obj;
		this.next = next;
	}

	public Node getPrevios() {
		return previos;
	}

	public void setPrevios(Node previos) {
		this.previos = previos;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}
	
	
}
