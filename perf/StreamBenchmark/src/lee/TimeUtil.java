package lee;

/**
 * in Java provides three methods for calculating and printing time elapsed in
 * milliseconds, microseconds, and average microseconds based on nanosecond resolution.
 * The methods take a start time as input and calculate the time difference with
 * respect to the current system time, printing the result along with a message.
 * Additionally, there is an averaging method that calculates and prints the average
 * execution time for a given number of iterations using high-accuracy nanosecond resolution.
 */
public class TimeUtil {
	/**
	 * takes a starting time and a message as input, calculates the elapsed time in
	 * milliseconds since the start of the function using `System.currentTimeMillis()`,
	 * and prints the elapsed time and message to the console.
	 * 
	 * @param startTime time at which the measurement of milliseconds took place, and it
	 * is used to calculate the difference between the current time and the starting time.
	 * 
	 * @param msg message to be printed along with the elapsed time in milliseconds.
	 */
	public static void outTimeMs(long startTime, String msg){
		long ms = System.currentTimeMillis()-startTime;
		System.out.println(msg + " " + ms + " ms");
		
	}
	/**
	 * calculates and prints the time taken in microseconds (μs) for a given task,
	 * subtracting 500 μs from the system's current time since initialization and dividing
	 * the result by 1000.
	 * 
	 * @param startTime start time of a measurement in milliseconds, which is used to
	 * calculate the elapsed time in microseconds and print it along with the message.
	 * 
	 * @param msg message to be printed along with the elapsed time in milliseconds.
	 */
	public static void outTimeUs(long startTime, String msg){
		long us = (System.nanoTime()-startTime+500)/1000;
		System.out.println(msg + " " + us + " us");
	}
	/**
	 * calculates and prints the average execution time in microseconds for a given number
	 * of iterations, using the `System.nanoTime()` method to measure the time taken.
	 * 
	 * @param startTime time at which the execution of the code began.
	 * 
	 * @param msg message to be printed along with the average execution time in microseconds.
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
