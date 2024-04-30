package lee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * is a Java file that tests various ways of summing up orders based on their prices.
 * The main method calls the `doTest()` method, which performs the testing. The tester
 * generates different lengths of order lists and runs the summing methods multiple
 * times for each list length. The methods tested include `sumOrderForLoop`,
 * `sumOrderStream`, and `sumOrderParallelStream`. Each method is called on a list
 * of orders and returns a map with the total price of each user's orders. The tester
 * also includes some warm-up code to ensure the methods are properly initialized
 * before running the tests.
 */
public class ReductionTest {

	/**
	 * calls the `doTest()` method, which performs some operation.
	 * 
	 * @param args 1 or more command line arguments passed to the `main` method when the
	 * program is run directly from the command line.
	 * 
	 * 	- The function takes an array of strings as input, denoted by `String[] args`.
	 * 	- The length of the array is not fixed and can vary depending on the invocation
	 * of the program.
	 * 	- Each element in the array represents a command-line argument passed to the
	 * program during execution.
	 */
	public static void main(String[] args) {
		new ReductionTest().doTest();
	}
	/**
	 * performs a series of tests on a list of orders, including generating orders using
	 * different methods, summing them using loops and stream APIs, and measuring the
	 * execution time of each method.
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
	 * executes multiple iterations of three different methods for summing a list of
	 * orders: `sumOrderForLoop`, `sumOrderStream`, and `sumOrderParallelStream`.
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
	 * maps a list of orders to a map of user names to total order prices by iterating
	 * through the list, retrieving and updating the user name to price value in the map
	 * for each order.
	 * 
	 * @param orders list of orders that are being summed and mapped to create a new map.
	 * 
	 * 	- `List<Order>` represents an ordered list of `Order` objects.
	 * 	- Each `Order` object contains attributes such as `getUserName()` for retrieving
	 * the user name and `getPrice()` for retrieving the price.
	 * 
	 * The function performs operations on each `Order` in the list, updating a map with
	 * the user name and the total cost for that user. If the map already contains a value
	 * for the user name, it updates the existing value by adding the price of the current
	 * `Order`. Otherwise, it initializes the map with the user name and its corresponding
	 * total cost. Finally, the function returns the updated map.
	 * 
	 * @returns a map of user names to their total order value.
	 * 
	 * 	- The returned value is a `Map` object containing key-value pairs, where the keys
	 * are user names and the values are the total amounts spent by each user.
	 * 	- The map is constructed using a `for` loop that iterates over the elements in
	 * the `orders` list.
	 * 	- For each element in the `orders` list, the function checks if the corresponding
	 * user name exists in the map. If it does, the function updates the value for that
	 * user name by adding the order's price to the existing total amount. If it doesn't
	 * exist, the function simply adds the order's price to the map with the user name
	 * as the key.
	 * 	- The returned map has a size of `orders.size()` since each element in the list
	 * is accounted for in the map.
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
	 * aggregates the orders by the user name and calculates the total price for each user.
	 * 
	 * @param orders list of orders that are to be summed and grouped by user name.
	 * 
	 * 	- The `List<Order>` type indicates that the function takes a list of order objects
	 * as input.
	 * 	- Each `Order` object in the list has a `getUserName()` method that returns a
	 * string representing the user name of the order.
	 * 	- The `getPrice()` method returns a double value representing the price of the order.
	 * 
	 * The function then uses the `stream().collect()` method to group the orders by user
	 * name and calculate the total price for each user. The `Collectors` class is used
	 * to specify the types of the input streams and the aggregation operation (in this
	 * case, grouping and summing).
	 * 
	 * @returns a map containing the sum of the prices of orders for each user.
	 * 
	 * The output is a map whose key is a `String` representing the user name of each
	 * order, and the value is a `Double` representing the total price of all orders
	 * associated with that user name. The map is constructed by grouping the orders by
	 * user name using `Collectors.groupingBy`, and then summing the prices of all orders
	 * within each group using `Collectors.summingDouble`.
	 */
	private Map<String, Double> sumOrderStream(List<Order> orders){
		return orders.stream().collect(
				Collectors.groupingBy(Order::getUserName, 
						Collectors.summingDouble(Order::getPrice)));
	}
	/**
	 * collects the prices of orders in a list and groups them by the user name, summing
	 * the prices for each user.
	 * 
	 * @param orders list of orders that will be processed in parallel using the
	 * `parallelStream()` method to calculate the total price of each order based on the
	 * user name and sum the results.
	 * 
	 * The `List<Order>` object contains multiple instances of the `Order` class, which
	 * has two attributes - `getUserName()` and `getPrice()`. The `getUserName()` attribute
	 * returns a string representing the user name of the order, while the `getPrice()`
	 * attribute returns a double value representing the price of the order.
	 * 
	 * @returns a map of user names to total prices summed from the parallel stream of orders.
	 * 
	 * 	- The output is a map data structure consisting of key-value pairs, where the
	 * keys are Strings and the values are Double values.
	 * 	- The map contains entries corresponding to each order in the input list, with
	 * the key being the user name of the order and the value being the sum of the prices
	 * of all orders belonging to that user.
	 * 	- The output is generated using the `collect` method of the `parallelStream`
	 * instance, which applies a collector function to the elements of the stream and
	 * aggregates them into a single result. In this case, the collector function is a
	 * custom implementation that groups the orders by user name and sums the prices of
	 * all orders belonging to each user.
	 */
	private Map<String, Double> sumOrderParallelStream(List<Order> orders){
		return orders.parallelStream().collect(
				Collectors.groupingBy(Order::getUserName, 
						Collectors.summingDouble(Order::getPrice)));
	}
}
/**
 * has several methods for generating and manipulating orders. These include:
 * 
 * 	- `genOrders()`: Generates a list of orders with random user names, prices, and
 * timestamps.
 * 	- `toString()`: Returns a string representation of an order in the format "user
 * name::price".
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
	 * returns a string representing the user's name.
	 * 
	 * @returns a string representing the user's name.
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * returns the `price` attribute value.
	 * 
	 * @returns the value of the `price` field.
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * returns the current timestamp value stored in the `timestamp` field.
	 * 
	 * @returns a long value representing the current timestamp.
	 */
	public long getTimestamp() {
		return timestamp;
	}
	/**
	 * generates a list of `Order` objects based on user input parameters. It creates a
	 * random number of users, and for each user, it generates a unique name and adds an
	 * order with a randomly generated price and creation time. The resulting list of
	 * orders is returned.
	 * 
	 * @param listLength total number of orders to be generated in the function, and it
	 * is used to determine the number of users and the size of the list returned by the
	 * function.
	 * 
	 * @returns a list of `Order` objects, each representing an order with a unique user
	 * name, price, and timestamp.
	 * 
	 * 	- `List<Order>`: The function returns a list of orders.
	 * 	- `Order`: Each element in the list is an instance of the `Order` class, representing
	 * a single order.
	 * 	- `String userName`: Each order has a unique user name associated with it.
	 * 	- `double price`: Each order has a randomly generated price between 1 and 1000.
	 * 	- `long timestamp`: Each order has a timestamp in nanoseconds representing when
	 * the order was created.
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
	 * generates a string representation of an object by combining the user name and price.
	 * 
	 * @returns a string consisting of the user name followed by a colon and then the price.
	 * 
	 * 	- `userName`: A string value representing the user's name.
	 * 	- `price`: An integer value representing the price of the item.
	 */
	@Override
	public String toString(){
		return userName + "::" + price;
	}
}
