package lee;

import java.util.Arrays;
import java.util.Random;
/**
 * has a single method, doTest(), which performs a series of tests on an array of
 * integers using various methods for finding the minimum integer in the array. The
 * class also includes a warm-up method that generates random integers and computes
 * the minimum value using different methods. The doTest() method iterates over 20000
 * times to prepare the system for future tasks.
 */
public class IntTest {

	/**
	 * runs a test suite for integers.
	 * 
	 * @param args 0 or more strings that will be passed to the `doTest()` method when
	 * the `main()` method is invoked.
	 * 
	 * 	- It is an array of `String` type.
	 * 	- Its length can be varied based on the command-line arguments passed to the program.
	 * 	- Each element in the array represents a separate command-line argument.
	 */
	public static void main(String[] args) {
		new IntTest().doTest();
	}
	/**
	 * iterates through a series of arrays of increasing sizes and measures the time taken
	 * for three different methods to find the minimum value using `minIntFor`, `minIntStream`,
	 * and `minIntParallelStream`.
	 */
	public void doTest(){
		warmUp();
		int[] lengths = {
				10000, 
				100000, 
				1000000, 
				10000000, 
				100000000, 
				1000000000
			};
		for(int length : lengths){
			System.out.println(String.format("---array length: %d---", length));
			int[] arr = new int[length];
			randomInt(arr);
			
			int times = 4;
			int min1 = 1;
			int min2 = 2;
			int min3 = 3;
			long startTime;
			
			startTime = System.nanoTime();
			for(int i=0; i<times; i++){
				min1 = minIntFor(arr);
			}
			TimeUtil.outTimeUs(startTime, "minIntFor time:", times);
			
			startTime = System.nanoTime();
			for(int i=0; i<times; i++){
				min2 = minIntStream(arr);
			}
			TimeUtil.outTimeUs(startTime, "minIntStream time:", times);
			
			startTime = System.nanoTime();
			for(int i=0; i<times; i++){
				min3 = minIntParallelStream(arr);
			}
			TimeUtil.outTimeUs(startTime, "minIntParallelStream time:", times);
			
			
			System.out.println(min1==min2 && min2==min3);
		}
	}
	/**
	 * performs multiple iterations of min-int calculations using different approaches:
	 * random array creation, `minIntFor`, `minIntStream`, and `minIntParallelStream`.
	 */
	private void warmUp(){
		int[] arr = new int[100];
		randomInt(arr);
		for(int i=0; i<20000; i++){
//			minIntFor(arr);
			minIntStream(arr);
			minIntParallelStream(arr);
			
		}
	}
	/**
	 * calculates and returns the minimum value in an integer array `arr`. It iterates
	 * through the elements of `arr`, comparing each element to the current minimum value,
	 * and updating the minimum value accordingly.
	 * 
	 * @param arr array whose minimum element is to be found and returned by the function.
	 * 
	 * 	- The array `arr` is defined as an array of integers with length `arr.length`.
	 * 	- Each element of the array can take on any integer value between `-2147483648`
	 * and `2147483647`, inclusive.
	 * 	- No additional information about the input array is provided beyond its length
	 * and range of values.
	 * 
	 * @returns the smallest integer value present in the input array.
	 */
	private int minIntFor(int[] arr){
		int min = Integer.MAX_VALUE;
		for(int i=0; i<arr.length; i++){
			if(arr[i]<min)
				min = arr[i];
		}
		return min;
	}
	/**
	 * takes an integer array as input and returns the minimum value in the array using
	 * Java's Stream API.
	 * 
	 * @param arr 1D integer array whose minimum value is returned by the function.
	 * 
	 * 	- It is an array of integers.
	 * 	- Its size can vary based on the input provided.
	 * 	- Each element in the array can take any valid integer value.
	 * 
	 * @returns the minimum integer value in the input array.
	 */
	private int minIntStream(int[] arr){
		return Arrays.stream(arr).min().getAsInt();
	}
	/**
	 * returns the minimum value of an array of integers using parallel stream.
	 * 
	 * @param arr 1D array of integers to be processed parallelly using the `parallel()`
	 * method.
	 * 
	 * 	- The data type of `arr` is `int`.
	 * 	- `arr` is an array, which means it is a collection of elements stored in contiguous
	 * memory locations.
	 * 	- The elements of `arr` can be accessed using their index, where the first element
	 * is located at index 0.
	 * 
	 * @returns the minimum integer value in the input array.
	 */
	private int minIntParallelStream(int[] arr){
		return Arrays.stream(arr).parallel().min().getAsInt();
	}
	/**
	 * generates a random integer array by iterating through an existing array and replacing
	 * each element with a new, randomly generated value using the `Random` class.
	 * 
	 * @param arr 1D integer array that will have its elements generated randomly by the
	 * function.
	 * 
	 * 	- The array `arr` is of type `int`, indicating that it contains a list of integer
	 * values.
	 * 	- The length of the array `arr` is specified using the variable `i`, which ranges
	 * from 0 to `arr.length - 1`.
	 * 	- The function uses a `Random` object named `r` to generate a random integer value
	 * for each index in the array `arr`.
	 */
	private void randomInt(int[] arr){
		Random r = new Random();
		for(int i=0; i<arr.length; i++){
			arr[i] = r.nextInt();
		}
	}
}
