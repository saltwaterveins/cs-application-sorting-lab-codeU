/**
 * 
 */
package com.flatironschool.javacs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Provides sorting algorithms.
 *
 */
public class ListSorter<T> {

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void insertionSort(List<T> list, Comparator<T> comparator) {
	
		for (int i=1; i < list.size(); i++) {
			T elt_i = list.get(i);
			int j = i;
			while (j > 0) {
				T elt_j = list.get(j-1);
				if (comparator.compare(elt_i, elt_j) >= 0) {
					break;
				}
				list.set(j, elt_j);
				j--;
			}
			list.set(j, elt_i);
		}
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
		List<T> sorted = mergeSort(list, comparator);
		list.clear();
		list.addAll(sorted);
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * Returns a list that might be new.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public List<T> mergeSort(List<T> list, Comparator<T> comparator) {
        if (list.isEmpty()) return list;
        if (list.size() == 1) return list;
        
        List<T> listFinal = new ArrayList<T>();
        List<T> listOne = new ArrayList<T>();
        List<T> listTwo = new ArrayList<T>();
        for (int i = 0; i < list.size(); i++) {
        	if (i < (list.size() / 2)) {
        		listOne.add(list.get(i));
        	}
        	else {
        		listTwo.add(list.get(i));
        	}	
        }
        insertionSort(listOne, comparator);
        insertionSort(listTwo, comparator);

        int j = 0, k = 0;
        while (j < listOne.size() && k < listTwo.size()) {
        	if (comparator.compare(listOne.get(j), listTwo.get(k)) < 0) {
        		listFinal.add(listOne.get(j));
        		j++;
        	}
        	else {
        		listFinal.add(listTwo.get(k));
        		k++;
        	}
        }
        
        while (j < listOne.size()) {
        	listFinal.add(listOne.get(j));
    		j++;
        }
        
        while (k < listTwo.size()) {
        	listFinal.add(listTwo.get(k));
    		k++;
        }
                
        
        return listFinal;
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void heapSort(List<T> list, Comparator<T> comparator) {
		PriorityQueue<T> priorityQueue = new PriorityQueue<T>(); 
		priorityQueue.addAll(list);
		list.removeAll(list);

		while(!priorityQueue.isEmpty()) {
			list.add(priorityQueue.poll());
		}
		
	}

	
	/**
	 * Returns the largest `k` elements in `list` in ascending order.
	 * 
	 * @param k
	 * @param list
	 * @param comparator
	 * @return 
	 * @return
	 */
	public List<T> topK(int k, List<T> list, Comparator<T> comparator) {
		PriorityQueue<T> priorityQueue = new PriorityQueue<T>();
		/*
    Branch 1: If the heap is not full, add x to the heap.

    Branch 2: If the heap is full, compare x to the smallest 
    element in the heap. If x is smaller, it cannot be one of the largest k elements, 
    so you can discard it.

    Branch 3: If the heap is full and x is greater than the smallest 
    element in the heap, remove the smallest element from the heap and add x.
		 */
		
		for (int i = 0; i < list.size(); i++) {
			if (priorityQueue.size() < k) {
				priorityQueue.offer(list.get(i));
			}
			else {
				if (comparator.compare(list.get(i), priorityQueue.peek()) > 0){
					priorityQueue.poll();
					priorityQueue.offer(list.get(i));
				}
			}
		}
		
		list.removeAll(list);
		for (int j = 0; j < k; j++) {
			list.add(priorityQueue.poll());
		}
        return list;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		
		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer n, Integer m) {
				return n.compareTo(m);
			}
		};
		
		ListSorter<Integer> sorter = new ListSorter<Integer>();
		sorter.insertionSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.mergeSortInPlace(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.heapSort(list, comparator);
		System.out.println(list);
	
		list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
		List<Integer> queue = sorter.topK(4, list, comparator);
		System.out.println(queue);
	}
}
