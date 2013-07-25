package com.anish.chattr;

import java.util.*;

public class TaskQueue<E> {

	private LinkedList<E> list = new LinkedList<E>();

	public void put(E v) {
		list.addFirst(v);
	}

	public E get() {
		return list.removeLast();
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}
}