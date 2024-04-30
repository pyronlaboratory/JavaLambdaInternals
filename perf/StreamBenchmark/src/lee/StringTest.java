package lee;

import java.util.ArrayList;
import java.util.Random;

/**
 * is a Java file that tests various methods for comparing and finding the minimum
 * string in an array of strings. The class has a `doTest()` method that runs multiple
 * iterations of each test, with different lengths of input lists. The methods tested
 * include:
 * 
 * 	- `minStringForLoop`: a loop-based method that iterates over the list and compares
 * each element to the previous one, selecting the smallest one.
 * 	- `minStringStream`: a stream-based method that uses the `min()` function to find
 * the minimum string in the list.
 * 	- `minStringParallelStream`: a parallel stream-based method that uses the `min()`
 * function to find the minimum string in the list in parallel.
 * 
 * The class also has a `warmUp()` method that runs multiple iterations of each test
 * to ensure consistent results.
 */
public class StringTest {

	/**
	 * executes a test method named `doTest()` on an object of the `StringTest` class,
	 * which is not shown in the code snippet provided.
	 * 
	 * @param args 1 or more command-line arguments passed to the `main` method when the
	 * program is launched.
	 * 
	 * 	- Length: The `args` array has 0 or more elements.
	 * 	- Element types: Each element in `args` is of type `String`.
	 * 	- Serialized form: `args` represents a serialized input, indicating the beginning
	 * of the program execution.
	 */
	public static void main(String[] args) {
		new StringTest().doTest();
	}
	/**
	 * performs a series of tests to compare the time complexity of three different methods
	 * for finding the minimum value in a list: `minStringForLoop`, `minStringStream`,
	 * and `minStringParallelStream`.
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
	 * iterates over a list of strings 20,000 times, using three different methods to
	 * calculate the minimum string length: `minStringForLoop`, `minStringStream`, and `minStringParallelStream`.
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
	 * iterates over an ArrayList of Strings and returns the smallest string in the list.
	 * 
	 * @param list list of strings to be compared and returned as the minimum string in
	 * the for loop.
	 * 
	 * 	- `list` is an `ArrayList` of type `String`.
	 * 	- The elements in `list` are stored as strings.
	 * 
	 * @returns the shortest string from an ArrayList of strings.
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
	 * @param list collection of strings that are to be compared and returned as the
	 * minimum value.
	 * 
	 * 	- `list`: A list of strings that is processed in the function.
	 * 
	 * @returns the minimum string in the input list.
	 */
	private String minStringStream(ArrayList<String> list){
		return list.stream().min(String::compareTo).get();
	}
	/**
	 * takes an ArrayList of Strings and returns the minimum string in the list after
	 * parallel stream operation using the `min()` method.
	 * 
	 * @param list ArrayList of strings that are to be processed by the parallel stream
	 * and min() method.
	 * 
	 * 	- Type: List<String>
	 * 	+ Element type: String
	 * 	+ Number of elements: Unknown (can be any number)
	 * 	+ Order of elements: Unknown (can be in any order)
	 * 
	 * The function applies a parallel stream operation to the list, using the `min`
	 * method to find the smallest element among all the strings in the list. The `get()`
	 * method is used to retrieve the minimum value from the stream.
	 * 
	 * @returns the minimum string in the `list`.
	 */
	private String minStringParallelStream(ArrayList<String> list){
		return list.stream().parallel().min(String::compareTo).get();
	}
	/**
	 * generates an ArrayList of random strings of a specified length, using a Random
	 * object to determine the characters within each string.
	 * 
	 * @param listLength desired length of the generated string list.
	 * 
	 * @returns a list of `strLength` random strings, each composed of uppercase letters
	 * between 'a' and 'z'.
	 * 
	 * 	- The list returned is an ArrayList of String objects.
	 * 	- Each element in the list is a randomly generated string of length 10 characters.
	 * 	- The strings are generated using a combination of ASCII characters, specifically
	 * the letters 'a' to 'z'.
	 * 	- The order of the elements in the list is randomized.
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
