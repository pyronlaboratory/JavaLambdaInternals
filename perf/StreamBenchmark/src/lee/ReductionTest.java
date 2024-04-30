package lee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * is a test class for evaluating the performance of three different methods for
 * summing up the prices of orders in a list, using different programming constructs
 * such as loops, streams, and parallel streams. The test class performs experiments
 * on various lengths of order lists and measures the execution time of each method.
 */
public class ReductionTest {

	/**
	 * runs a test for reduction.
	 * 
	 * @param args 1 or more command line arguments passed to the `main` function when
	 * the program is executed.
	 * 
	 * 	- Length: The `args` array has 0 or more elements, depending on how many command-line
	 * arguments were passed to the program.
	 * 	- Elements: Each element in `args` is a String representing a command-line argument.
	 * 	- Type: The type of each element in `args` is String.
	 */
	public static void main(String[] args) {
		new ReductionTest().doTest();
	}
	/**
	 * generates and sums orders using different loops, streams, and parallel streams,
	 * measuring the time complexity for each approach.
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
	 * generates and processes 10 orders using three different methods: `sumOrderForLoop`,
	 * `sumOrderStream`, and `sumOrderParallelStream`.
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
	 * takes a list of orders as input and creates a map containing the total price of
	 * each order for a specific user.
	 * 
	 * @param orders list of orders that are to be summed and mapped.
	 * 
	 * 	- `List<Order>` represents an ordered list of `Order` objects.
	 * 	- Each `Order` object has the following attributes:
	 * 	+ `UserName`: a string representing the user name associated with the order.
	 * 	+ `Price`: a double value representing the price of the order.
	 * 
	 * @returns a `Map` object containing the total amount spent by each user in a list
	 * of orders.
	 * 
	 * 	- The map contains key-value pairs representing the total cost of orders for each
	 * user.
	 * 	- Each key in the map is a unique user name, and the corresponding value is the
	 * total cost of orders for that user.
	 * 	- If a user has no orders, the value associated with their key is `0`.
	 * 	- The map is a mutable object, meaning it can be modified by adding or updating
	 * values.
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
	 * aggregates the prices of orders based on the user name, and returns a map of user
	 * names to total price amounts.
	 * 
	 * @param orders list of orders that are being summarized by the function.
	 * 
	 * 	- `List<Order>`: A list of `Order` objects representing orders to be summed.
	 * 	- `Order`: An object with attributes `getUserName()` and `getPrice()`, which
	 * return a user name and a double value, respectively.
	 * 
	 * @returns a map of user names to the total price of orders placed by each user.
	 * 
	 * The output is a `Map` containing `String`, `Double` keys and values. This indicates
	 * that the orders are grouped by user name, and the total price of each order is
	 * calculated and stored in the map as a double value.
	 */
	private Map<String, Double> sumOrderStream(List<Order> orders){
		return orders.stream().collect(
				Collectors.groupingBy(Order::getUserName, 
						Collectors.summingDouble(Order::getPrice)));
	}
	/**
	 * takes a list of orders and returns a map with the user name as key and the total
	 * price of orders placed by each user as value, calculated by summing the prices of
	 * the orders for each user in parallel stream.
	 * 
	 * @param orders list of orders to be summed.
	 * 
	 * 	- `List<Order>` - The function takes a list of orders as input. Each order has
	 * multiple attributes like `getUserName`, `getPrice`.
	 * 	- `parallelStream()` - This method applies the `Collectors.groupingBy()` and
	 * `Collectors.summingDouble()` methods in parallel, leveraging the efficiency of
	 * Java 8 stream API.
	 * 
	 * @returns a map of user names to sum of prices for each user.
	 * 
	 * 	- The output is a map data structure where each key is a string representing the
	 * user name of an order, and each value is a double number representing the total
	 * price of all orders placed by that user.
	 * 	- The map is generated using the `collect` method of the `parallelStream` API,
	 * which takes two functions as arguments: `Collectors.groupingBy(Order::getUserName)`
	 * to group the orders by user name, and `Collectors.summingDouble(Order::getPrice)`
	 * to calculate the total price for each user.
	 * 	- The `parallelStream` method is used to execute the stream of orders in parallel,
	 * which improves performance when processing large amounts of data.
	 */
	private Map<String, Double> sumOrderParallelStream(List<Order> orders){
		return orders.parallelStream().collect(
				Collectors.groupingBy(Order::getUserName, 
						Collectors.summingDouble(Order::getPrice)));
	}
}
/**
 * is used to represent an order in a system, with attributes for user name, price,
 * and timestamp. The genOrders method generates a list of orders randomly, with the
 * number of orders per user and the total number of orders controlled by the user.
 * The toString method provides a concise representation of an order as a string.
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
	 * retrieves a user's username.
	 * 
	 * @returns a string representing the user's name.
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * Retrieves the price value.
	 * 
	 * @returns the current price of the item.
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * retrieves the `timestamp` variable, which represents a system-specific timestamp
	 * value.
	 * 
	 * @returns a long value representing the current timestamp.
	 */
	public long getTimestamp() {
		return timestamp;
	}
	/**
	 * generates a list of `Order` objects with varying prices and user names, simulating
	 * orders placed by multiple users.
	 * 
	 * @param listLength total number of orders to be generated in the function, and it
	 * is used to determine the number of users and the size of each user's order list.
	 * 
	 * @returns a list of `Order` objects containing random user names, prices, and timestamps.
	 * 
	 * 	- The list is of type `List<Order>`, indicating that it is a collection of Order
	 * objects.
	 * 	- The list contains `listLength` elements, where `listLength` is the input parameter
	 * passed to the function.
	 * 	- Each element in the list is an instance of Order, representing a single order
	 * generated randomly.
	 * 	- The Order objects contain three attributes: user name, price, and timestamp,
	 * which are generated randomly using `Random` instances.
	 * 	- The user name is a unique identifier generated using `UUID.randomUUID()`,
	 * ensuring that each order has a distinct user name.
	 * 	- The price of each order is generated randomly between 0 and 1000, representing
	 * the random cost of the order.
	 * 	- The timestamp is generated using `System.nanoTime()` and represents the time
	 * at which the order was generated.
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
	 * generates a string representation of an object by combining its user name and price.
	 * 
	 * @returns a concatenation of the user name and price.
	 */
	@Override
	public String toString(){
		return userName + "::" + price;
	}
}
