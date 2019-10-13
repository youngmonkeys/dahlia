package com.tvd12.dahlia.core.test;

import java.util.Arrays;

import org.testng.annotations.Test;

public class ArrayTest {

	@Test
	public void insertTest() {
		int size = 5;
		Integer[] array = new Integer[] {1, 2, 3, 4, 5, null, null, null, null};
		System.out.println(Arrays.toString(array));
		insert(array, size, 8);
		++ size;
		System.out.println(Arrays.toString(array));
		insert(array, size, 0);
		++ size;
		System.out.println(Arrays.toString(array));
		insert(array, size, 7);
		++ size;
		System.out.println(Arrays.toString(array));
		insert(array, size, 6);
		++ size;
		System.out.println(Arrays.toString(array));
	}
	
	@Test
	public void insertTest2() {
		int size = 1;
		Integer[] array = new Integer[] {1, null, null, null, null};
		insert(array, size, 0);
		++ size;
		System.out.println(Arrays.toString(array));
		insert(array, size, 7);
		++ size;
		System.out.println(Arrays.toString(array));
		insert(array, size, 6);
		++ size;
		System.out.println(Arrays.toString(array));
	}
	
	private void insert(Integer[] array, int size, int toInsert) {
		for(int i = size ; i > 0 ; i --) {
			int prev = array[i - 1];
			if(prev <= toInsert) {
				array[i] = toInsert;
				return;
			}
			array[i] = prev;
		}
		array[0] = toInsert;
	}
	
	
	@Test
	public void test3() {
		int[] array = new int[] {1, 2, 3, 4, 5};
		moveChildrenToLeft1(array, 0);
		System.out.println(Arrays.toString(array));
		array = new int[] {1, 2, 3, 4, 5};
		moveChildrenToLeft2(array, 1);
		System.out.println(Arrays.toString(array));
	}
	
	private void moveChildrenToLeft1(int[] array, int leftIndex) {
		for(int i = leftIndex ; i < array.length - 1 ; ++i)
			array[i] = array[i + 1];
	}
	
	private void moveChildrenToLeft2(int[] array, int leftIndex) {
		for(int i = leftIndex ; i < array.length ; ++i)
			array[i - 1] = array[i];
	}
}
