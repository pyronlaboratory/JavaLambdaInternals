package lee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * is a JUnit test class that executes multiple iterations of three different methods
 * for summing a list of orders: `sumOrderForLoop`, `sumOrderStream`, and
 * `sumOrderParallelStream`. The methods map the list of orders to a map of user names
 * to total order prices by iterating through the list of orders and calculating the
 * total price of each order based on the user name. The test class provides a
 * high-level summary of the different fields or methods in the ReductionTest class,
 * without providing detailed information on each field or method.
 */
public class ReductionTest {

	/**
	 * calls the `doTest()` method, which performs a reduction test on an object of type
	 * `ReductionTest`.
	 * 
	 * @param args 1 or more command line arguments passed to the program when it is
	 * launched, and they are passed as an array to the `main` method for processing.
	 * 
	 * `String[] args`: This variable is an array of strings containing command-line
	 * arguments passed to the program during execution.
	 * 
	 * The `args` array has various attributes, including its length (the number of
	 * elements in the array), and each element's value (a string representing a command-line
	 * argument).
	 */
	public static void main(String[] args) {
		new ReductionTest().doTest();
	}
	/**
	 * runs a series of benchmarks to compare the performance of different methods for
	 * summing a list of orders. It uses the `Order` class to generate orders and the
	 * `sumOrderForLoop`, `sumOrderStream`, and `sumOrderParallelStream` methods to perform
	 * the sums. The functionality includes generating orders, running the benchmarks,
	 * and printing the results.
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
			System.out.println(String.format("---orders length: %d---", length));
			List<Order> orders = Order.genOrders(length);
			int times = 4;
			Map<String, Double> map1 = null;
			Map<String, Double> map2 = null;
			Map<String, Double> map3 = null;
			
			long startTime;
			
			startTime = System.nanoTime();
			for(int i=0; i<times; i++){
				map1 = sumOrderForLoop(orders);
			}
			TimeUtil.outTimeUs(startTime, "sumOrderForLoop time:", times);
			
			startTime = System.nanoTime();
			for(int i=0; i<times; i++){
				map2 = sumOrderStream(orders);
			}
			TimeUtil.outTimeUs(startTime, "sumOrderStream time:", times);

			startTime = System.nanoTime();
			for(int i=0; i<times; i++){
				map3 = sumOrderParallelStream(orders);	
			}
			TimeUtil.outTimeUs(startTime, "sumOrderParallelStream time:", times);
			
			System.out.println("users=" + map3.size());
		
		}
	}
	/**
	 * generates and processes a list of 10 orders using three different methods:
	 * `sumOrderForLoop`, `sumOrderStream`, and `sumOrderParallelStream`.
	 */
	private void warmUp(){
		List<Order> orders = Order.genOrders(10);
		for(int i=0; i<20000; i++){
			sumOrderForLoop(orders);
			sumOrderStream(orders);
			sumOrderParallelStream(orders);
			
		}
	}
	/**
	 * maps user names to their total orders' prices by iterating through a list of orders
	 * and updating the map with each user's price as either the existing value or the
	 * new price plus the order's price.
	 * 
	 * @param orders list of orders that are being summed up.
	 * 
	 * 	- `List<Order>` is a collection of objects representing orders. Each order has
	 * several attributes such as user name, price, etc.
	 * 	- `Order` is a class that represents an order with attributes like `UserName`,
	 * `Price`, etc.
	 * 	- The function takes a list of `Order` objects as input and returns a map of user
	 * names to their total ordered quantities.
	 * 
	 * @returns a `Map` object containing the sum of the prices of orders for each user.
	 * 
	 * 	- The map contains key-value pairs representing user names and their total order
	 * values.
	 * 	- Each key (user name) is associated with a value (total order value) that is
	 * calculated by adding the price of each order to any previous value associated with
	 * that user name, or initially set to 0 if no previous value exists.
	 * 	- The map is implemented as a `Map` class in Java, which provides efficient lookup
	 * and insertion operations.
	 * 	- The function returns the map, allowing it to be further processed or manipulated
	 * as needed.
	 */
	private Map<String, Double> sumOrderForLoop(List<Order> orders){
		Map<String, Double> map = new HashMap<>();
		for(Order od : orders){
			String userName = od.getUserName();
			Double v; 
			if((v=map.get(userName)) != null){
				map.put(userName, v+od.getPrice());
			}else{
				map.put(userName, od.getPrice());
			}
		}
		return map;
	}
	/**
	 * takes a list of orders and streams them to group by the user name, then sums up
	 * the price of each order within each group.
	 * 
	 * @param orders list of orders to be summed and grouped by user name using the
	 * `stream().collect()` method.
	 * 
	 * 	- `List<Order>` is a collection of objects representing orders.
	 * 	- `Order` has several attributes:
	 * 	+ `getUserName()` retrieves the user name associated with each order.
	 * 	+ `getPrice()` retrieves the price associated with each order.
	 * 	- The `stream().collect()` call groups the orders by user name and calculates the
	 * total price for each user using the `summingDouble()` method.
	 * 
	 * @returns a map containing the sum of the prices of orders for each user.
	 * 
	 * The returned map contains key-value pairs where the keys are user names, and the
	 * values represent the total cost of orders placed by each user. The map is created
	 * by grouping the orders using the `getUserName` method of the `Order` class, followed
	 * by summing the `getPrice` method using the `Collectors.summingDouble` method.
	 * 
	 * The map has a total of `n` key-value pairs, where `n` is the number of unique user
	 * names in the input list of orders. Each value in the map represents the total cost
	 * of orders placed by a particular user. The values are stored as doubles, representing
	 * the total cost in a numerical format.
	 * 
	 * The keys in the map are strings, representing the user names of the customers who
	 * placed orders. The keys are unique and non-null, as the `Collectors.groupingBy`
	 * method ensures that each key is only associated with one value.
	 * 
	 * Overall, the output of the `sumOrderStream` function is a concise and efficient
	 * way to calculate the total cost of orders placed by different users in a list of
	 * orders.
	 */
	private Map<String, Double> sumOrderStream(List<Order> orders){
		return orders.stream().collect(
				Collectors.groupingBy(Order::getUserName, 
						Collectors.summingDouble(Order::getPrice)));
	}
	/**
	 * parses a list of orders and returns a map of user names to sum of prices, calculated
	 * by grouping orders by user name and summing the price of each order using a parallel
	 * stream.
	 * 
	 * @param orders list of orders to be summed parallelly using the `parallelStream()`
	 * method.
	 * 
	 * 	- The type of the list is `List<Order>`.
	 * 	- Each element in the list is an instance of the `Order` class.
	 * 	- The `parallelStream()` method is called on the list to execute the stream in parallel.
	 * 	- The `collect()` method is used to group the elements based on the `getUserName()`
	 * method of the `Order` class and sum the `getPrice()` method of each element.
	 * 
	 * @returns a map of user names to sums of the prices of orders for each user.
	 * 
	 * 	- The output is a map data structure, where the keys are Strings representing
	 * user names and the values are Doubles representing the sum of prices for each user.
	 * 	- The map is generated by using the `parallelStream()` method on the input list
	 * of orders, which allows for efficient processing of large amounts of data in parallel.
	 * 	- The `collect()` method is used to collect the data from the parallel stream
	 * into a single data structure, in this case a map.
	 */
	private Map<String, Double> sumOrderParallelStream(List<Order> orders){
		return orders.parallelStream().collect(
				Collectors.groupingBy(Order::getUserName, 
						Collectors.summingDouble(Order::getPrice)));
	}
}
/**
 * is a simple Java class that represents an order with a unique user name, price,
 * and timestamp. The class provides a `genOrders()` method for generating a list of
 * orders based on user input parameters, and also implements the `ToString` interface
 * for generating a string representation of an object by combining the user name and
 * price.
 */
class Order{
	private String userName;
	private double price;
	private long timestamp;
	public Order(String userName, double price, long timestamp) {
		this.userName = userName;
		this.price = price;
		this.timestamp = timestamp;
	}
	/**
	 * retrieves a user's name.
	 * 
	 * @returns a string representing the user's name.
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * retrieves the price value stored in a variable named `price`.
	 * 
	 * @returns the value of the `price` field.
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * retuns a long representing the current timestamp.
	 * 
	 * @returns a long representing the current timestamp.
	 */
	public long getTimestamp() {
		return timestamp;
	}
	/**
	 * generates a list of orders with randomly selected user names and prices, and adds
	 * them to an ArrayList.
	 * 
	 * @param listLength total number of orders to be generated, which is used to determine
	 * the number of users and the size of the list returned by the function.
	 * 
	 * @returns a list of `Order` objects containing random user names, prices, and timestamps.
	 * 
	 * 	- The function returns a list of `Order` objects, which contain information about
	 * orders placed by users.
	 * 	- Each `Order` object has three attributes: user name, price, and creation time,
	 * which are generated randomly using `Random` class.
	 * 	- The list length is determined by the input parameter `listLength`, which is
	 * used to create a fixed number of orders.
	 * 	- The `users` variable is defined as `listLength / 200`, indicating that there
	 * will be approximately 200 orders per user.
	 * 	- The `userNames` array stores unique user names for each order, generated using
	 * `UUID.randomUUID()` method.
	 * 	- The orders are created randomly within the range of 1 to 1000 for the price attribute.
	 */
	public static List<Order> genOrders(int listLength){
		ArrayList<Order> list = new ArrayList<>(listLength);
		Random rand = new Random();
		int users = listLength/200;// 200 orders per user
		users = users==0 ? listLength : users;
		ArrayList<String> userNames = new ArrayList<>(users);
		for(int i=0; i<users; i++){
			userNames.add(UUID.randomUUID().toString());
		}
		for(int i=0; i<listLength; i++){
			double price = rand.nextInt(1000);
			String userName = userNames.get(rand.nextInt(users));
			list.add(new Order(userName, price, System.nanoTime()));
		}
		return list;
	}
	/**
	 * takes a `userName` and a `price` as input and combines them into a single string
	 * representation of the object, using the format `userName::price`.
	 * 
	 * @returns a string consisting of the user name followed by a colon and then the price.
	 */
	@Override
	public String toString(){
		return userName + "::" + price;
	}
}
