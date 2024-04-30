package lee;

import java.util.ArrayList;
import java.util.Random;

/**
 * is a Java file that tests various methods for finding the minimum string in an
 * array of strings. It includes several different approaches to find the minimum
 * string, including using loops, streams, and parallel streams. The class also
 * includes a warm-up method to ensure consistent results.
 */
public class StringTest {

	/**
	 * calls the `doTest()` method, which is not provided in the code snippet. Therefore,
	 * the functionality of the `main` function cannot be determined.
	 * 
	 * @param args 0 or more command-line arguments passed to the `main` method when the
	 * program is launched.
	 * 
	 * 	- Length: The `main` function receives an array of strings called `args`, which
	 * has a length of 1.
	 * 	- Elements: The `args` array contains only one element, which is a string.
	 */
	public static void main(String[] args) {
		new StringTest().doTest();
	}
	/**
	 * performs benchmarking tests on various methods for finding the minimum string in
	 * a list, including a loop-based approach, a stream-based approach, and a parallel
	 * stream-based approach.
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
	 * iteratively calls three methods on an ArrayList of strings: `minStringForLoop`,
	 * `minStringStream`, and `minStringParallelStream`. Each method performs a different
	 * optimization on the list.
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
	 * iterates through an ArrayList of Strings and returns the minimum string in the list.
	 * 
	 * @param list list of strings to be compared and returned as the minimum string value
	 * in the function execution.
	 * 
	 * 	- It is an ArrayList of Strings, meaning it is a collection of String objects.
	 * 	- Each element in the list is a String object.
	 * 	- The size of the list can vary depending on the input provided.
	 * 
	 * @returns the smallest string from the input list.
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
	 * takes an ArrayList of Strings and returns the minimum String in the list after
	 * streaming the elements and comparing them using the `compareTo()` method.
	 * 
	 * @param list ArrayList of strings to be compared and reduced to the smallest string
	 * using the Stream API.
	 * 
	 * The `list` input is an instance of `ArrayList`. This means that it is a collection
	 * class in Java that can store a list of objects or primitives and provides methods
	 * for common operations such as adding, removing, and accessing elements.
	 * 
	 * @returns the minimum string in the input list.
	 */
	private String minStringStream(ArrayList<String> list){
		return list.stream().min(String::compareTo).get();
	}
	/**
	 * takes an ArrayList of Strings and returns the minimum String in the list after
	 * parallel streaming and using the `min()` method.
	 * 
	 * @param list list of strings to be processed using parallel stream and the minimum
	 * string is returned.
	 * 
	 * 	- `list` is an ArrayList of Strings.
	 * 	- The stream method is called on the list parallel to each other, indicating that
	 * multiple threads are executed simultaneously.
	 * 	- The `min` method is used to find the smallest element in the stream, which is
	 * a String in this case.
	 * 	- The `compareTo` method compares the two elements being compared based on their
	 * string representation.
	 * 
	 * @returns the minimum string value in the input list.
	 */
	private String minStringParallelStream(ArrayList<String> list){
		return list.stream().parallel().min(String::compareTo).get();
	}
	/**
	 * generates an array of random strings of a specified length using a randomized
	 * approach to prevent repetition.
	 * 
	 * @param listLength maximum length of the randomly generated string list, which
	 * determines the capacity of the `ArrayList` and the number of strings generated.
	 * 
	 * @returns a list of randomized strings of length 10 each.
	 * 
	 * 	- The function returns an ArrayList of strings, where each string has a length
	 * of 10 characters.
	 * 	- The ArrayList is created using the `new` keyword and the `ArrayList` class.
	 * 	- A Random object is created using the `new` keyword and the `Random` class. This
	 * object is used to generate random integers for each string in the list.
	 * 	- For each iteration of the loop, a new StringBuffer is created using the `new`
	 * keyword and the `StringBuilder` class. The length of the StringBuffer is set to
	 * 10 characters using the `delete` method.
	 * 	- In the inner loop, a random character is generated using the `nextInt` method
	 * of the Random object, and this character is appended to the StringBuffer using the
	 * `append` method.
	 * 	- Once all the strings are generated, they are added to the ArrayList using the
	 * `add` method.
	 * 	- The function returns the ArrayList of strings.
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
