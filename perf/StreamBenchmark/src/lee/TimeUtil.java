package lee;

/**
 * is a utility class that provides three methods for printing the time elapsed in
 * milliseconds (ms), microseconds (us), and average us based on nanoseconds (ns).
 * The methods take a start time as input and print the time difference with respect
 * to the current system time.
 */
public class TimeUtil {
	/**
	 * takes a start time and message as input, calculates the time difference in
	 * milliseconds since the start time, and prints the result to the console along with
	 * the message.
	 * 
	 * @param startTime time at which the measurement of the elapsed time should begin,
	 * which is then used to calculate the elapsed time in milliseconds.
	 * 
	 * @param msg message that will be printed along with the elapsed time in milliseconds.
	 */
	public static void outTimeMs(long startTime, String msg){
		long ms = System.currentTimeMillis()-startTime;
		System.out.println(msg + " " + ms + " ms");
		
	}
	/**
	 * calculates and prints the time taken in microseconds (μs) for a given task, based
	 * on the difference between the current time and the start time, adjusted by 500 μs.
	 * 
	 * @param startTime start time of the measurement in milliseconds, which is subtracted
	 * from the current system time to calculate the elapsed time in milliseconds.
	 * 
	 * @param msg message to be printed along with the elapsed time in milliseconds.
	 */
	public static void outTimeUs(long startTime, String msg){
		long us = (System.nanoTime()-startTime+500)/1000;
		System.out.println(msg + " " + us + " us");
	}
	/**
	 * calculates and prints the average time taken for a given number of iterations,
	 * using nanosecond resolution to provide high accuracy.
	 * 
	 * @param startTime time at which the measurement of execution time began.
	 * 
	 * @param msg message to be printed along with the average execution time calculation.
	 * 
	 * @param times number of measurements to average when calculating the average execution
	 * time for the given task.
	 */
	public static void outTimeUs(long startTime, String msg, int times){
		long ns_all = System.nanoTime()-startTime;
		double us_avg = (ns_all+500.0)/1000/times;
		System.out.println(
				String.format("%s avg of %d = %.2f us", msg, times, us_avg));
	}
}
