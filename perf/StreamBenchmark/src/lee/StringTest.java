package lee;

import java.util.ArrayList;
import java.util.Random;

/**
 * is designed to perform various optimizations on a list of strings. It contains
 * three methods for comparing and returning the minimum string in the list using
 * different approaches: `minStringForLoop`, `minStringStream`, and `minStringParallelStream`.
 * These methods optimize the comparison process by leveraging the Java Stream API,
 * parallel processing, or a randomized approach to prevent repetition. The class
 * also includes a method for generating an array of random strings of a specified length.
 */
public class StringTest {

	/**
	 * executes the `doTest()` method, which performs some tests on strings.
	 * 
	 * @param args 1 or more command-line arguments passed to the program when it is
	 * executed, and is used by the `main` method to determine the appropriate action to
	 * take based on the input provided.
	 * 
	 * 	- `args`: An array of strings containing the command-line arguments passed to the
	 * program.
	 * 	- Length: The number of elements in the `args` array, which is always equal to
	 * the number of command-line arguments passed to the program.
	 * 	- Elements: Each element in the `args` array represents a separate command-line
	 * argument passed to the program.
	 */
	public static void main(String[] args) {
		new StringTest().doTest();
	}
	/**
	 * performs a series of tests to compare the efficiency of three different methods
	 * for finding the minimum string in a list of strings. The methods are: `minStringForLoop`,
	 * `minStringStream`, and `minStringParallelStream`.
	 */
	public void doTest(){
		warmUp();
		int[] lengths = {
				10000, 
				100000, 
				1000000, 
				10000000, 
				20000000, 
				40000000
			};
		for(int length : lengths){
			System.out.println(String.format("---List length: %d---", length));
			ArrayList<String> list = randomStringList(length);
			int times = 4;
			String min1 = "1";
			String min2 = "2";
			String min3 = "3";
			long startTime;
			
			startTime = System.nanoTime();
			for(int i=0; i<times; i++){
				min1 = minStringForLoop(list);
			}
			TimeUtil.outTimeUs(startTime, "minStringForLoop time:", times);
			
			startTime = System.nanoTime();
			for(int i=0; i<times; i++){
				min2 = minStringStream(list);
			}
			TimeUtil.outTimeUs(startTime, "minStringStream time:", times);

			startTime = System.nanoTime();
			for(int i=0; i<times; i++){
				min3 = minStringParallelStream(list);	
			}
			TimeUtil.outTimeUs(startTime, "minStringParallelStream time:", times);
			
			System.out.println(min1.equals(min2) && min2.equals(min3));
//			System.out.println(min1);
		}
	}
	/**
	 * performs three iterations of mining strings using different methods: `minStringForLoop`,
	 * `minStringStream`, and `minStringParallelStream`.
	 */
	private void warmUp(){
		ArrayList<String> list = randomStringList(10);
		for(int i=0; i<20000; i++){
			minStringForLoop(list);
			minStringStream(list);
			minStringParallelStream(list);
			
		}
	}
	/**
	 * takes an ArrayList of Strings and returns the smallest String in the list after
	 * iterating through it and comparing each String with the previously found minimum
	 * String.
	 * 
	 * @param list list of strings to be compared for minimum length.
	 * 
	 * 	- The `list` variable is of type `ArrayList`, which means it is a collection class
	 * in Java that stores a list of objects in an array.
	 * 	- The elements of the list are represented by the `String` class, which is a
	 * primitive data type in Java used to represent a string of text.
	 * 
	 * The function then iterates through the elements of the list using a for loop and
	 * performs some operations based on the properties of each element.
	 * 
	 * @returns the shortest string from an input list of strings.
	 */
	private String minStringForLoop(ArrayList<String> list){
		String minStr = null;
		boolean first = true;
		for(String str : list){
			if(first){
				first = false;
				minStr = str;
			}
			if(minStr.compareTo(str)>0){
				minStr = str;
			}
		}
		return minStr;
	}
	/**
	 * takes an ArrayList of Strings and returns the smallest String in the list after
	 * performing a comparison using the `compareTo()` method.
	 * 
	 * @param list ArrayList of String that will be sorted and the minimum value returned.
	 * 
	 * 	- The variable list is an instance of the ArrayList class in Java, which is a
	 * collection data structure that stores a collection of elements as a single unit.
	 * 	- The list contains Strings as its elements.
	 * 	- The size of the list can vary based on the input provided.
	 * 
	 * @returns the minimum string in the input list.
	 */
	private String minStringStream(ArrayList<String> list){
		return list.stream().min(String::compareTo).get();
	}
	/**
	 * parallelly streams through an ArrayList of Strings and returns the minimum String
	 * in the list using the ` compareTo()` method.
	 * 
	 * @param list list of strings to be processed using parallel stream and minimum
	 * element is returned.
	 * 
	 * 	- `list`: A list of strings representing a collection of objects that can be
	 * processed in parallel using a stream.
	 * 	- `stream()`: The `stream()` method is called on the `list` object to create a
	 * parallel stream of its elements.
	 * 	- `min(String::compareTo)`: The `min()` method is used to find the smallest string
	 * in the list, and it takes a lambda expression as an argument that compares two
	 * strings using the `compareTo()` method. The lambda expression is defined as
	 * `String::compareTo`, which is a reference to the `compareTo()` method of the
	 * `String` class.
	 * 	- `get()`: The `get()` method is called on the result of the `min()` method to
	 * retrieve the smallest string in the list.
	 * 
	 * @returns the minimum string in the input list.
	 */
	private String minStringParallelStream(ArrayList<String> list){
		return list.stream().parallel().min(String::compareTo).get();
	}
	/**
	 * generates an array list of random strings of a fixed length using a randomly
	 * generated letter for each character.
	 * 
	 * @param listLength length of the ArrayList that will be generated by the function.
	 * 
	 * @returns an ArrayList of randomized strings of fixed length.
	 * 
	 * 1/ The list is an instance of `ArrayList`, which means it is a collection of Strings
	 * that can be accessed and modified using standard Java collection methods.
	 * 2/ The list contains `listLength` number of elements, as specified in the function
	 * declaration.
	 * 3/ Each element in the list is a random string of length `strLength`. The strings
	 * are generated by repeatedly appending random characters to a temporary buffer until
	 * the desired length is reached.
	 * 4/ The characters used to generate the random strings are the 26 uppercase letters
	 * of the Latin alphabet, with each letter having an equal probability of being selected.
	 */
	private ArrayList<String> randomStringList(int listLength){
		ArrayList<String> list = new ArrayList<>(listLength);
		Random rand = new Random();
		int strLength = 10;
		StringBuilder buf = new StringBuilder(strLength);
		for(int i=0; i<listLength; i++){
			buf.delete(0, buf.length());
			for(int j=0; j<strLength; j++){
				buf.append((char)('a'+rand.nextInt(26)));
			}
			list.add(buf.toString());
		}
		return list;
	}
}
