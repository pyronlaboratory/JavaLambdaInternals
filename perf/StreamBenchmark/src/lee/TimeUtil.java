package lee;

/**
 * is a utility class that provides methods for printing time elapsed in milliseconds
 * (ms), microseconds (us), and averaged us values. The class includes three methods:
 * outTimeMs, outTimeUs, and outTimeUs(long startTime, String msg, int times). These
 * methods take a start time and a message as inputs and print the time elapsed in
 * the specified unit.
 */
public class TimeUtil {
	/**
	 * takes a start time and message as input, calculates the time difference in
	 * milliseconds since the start time, and prints the result along with the message
	 * to the console.
	 * 
	 * @param startTime time in milliseconds since the Java virtual machine (JVM) started
	 * executing the program.
	 * 
	 * @param msg message to be printed along with the elapsed time in milliseconds.
	 */
	public static void outTimeMs(long startTime, String msg){
		long ms = System.currentTimeMillis()-startTime;
		System.out.println(msg + " " + ms + " ms");
		
	}
	/**
	 * calculates and prints the time elapsed (in microseconds) since a specified start
	 * time, using the `System.nanoTime()` method to measure the time and the `1000`
	 * constant to convert nanoseconds to milliseconds.
	 * 
	 * @param startTime time in milliseconds when the method was called, which is used
	 * to calculate the elapsed time in microseconds.
	 * 
	 * @param msg message that is printed along with the elapsed time in milliseconds.
	 */
	public static void outTimeUs(long startTime, String msg){
		long us = (System.nanoTime()-startTime+500)/1000;
		System.out.println(msg + " " + us + " us");
	}
	/**
	 * calculates and prints the average time taken by a given number of iterations of a
	 * specific operation, with nanosecond precision.
	 * 
	 * @param startTime time at which the measurement of the average time taken to execute
	 * the given number of iterations should begin.
	 * 
	 * @param msg message to be printed along with the average execution time calculation.
	 * 
	 * @param times number of measurements to average when calculating the average time
	 * in microseconds.
	 */
	public static void outTimeUs(long startTime, String msg, int times){
		long ns_all = System.nanoTime()-startTime;
		double us_avg = (ns_all+500.0)/1000/times;
		System.out.println(
				String.format("%s avg of %d = %.2f us", msg, times, us_avg));
	}
}
