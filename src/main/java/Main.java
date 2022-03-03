package main.java;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Scanner;

public class Main {

  private static HttpURLConnection connection;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    System.out.println("Please enter a city name");
    String city = sc.nextLine().toLowerCase();

    String endPoint =("https://api.weatherapi.com/v1/current.json?key=31917b8c4c1e46b5a7d141643220303&q="+city);
    method(endPoint);
  }

  public static void method(String endPoint){

    //Method 2: java.net.http-HttpClient -available by JDK 11 onwards
    //we first create our client
    HttpClient client = HttpClient.newHttpClient();
    //then we built our request
    HttpRequest request = HttpRequest
            .newBuilder()
            .uri(
                    URI
                            .create(endPoint) //using json placeholder url
            )
            .GET() //the HTTP request method
            .build(); //building the request object
    //then we can send this request using our client, and we wanted to send asynchronously
    client.sendAsync(
                    request, // our HttpRequest instance
                    HttpResponse.BodyHandlers.ofString() // the second parameter
                    // tells the server which response body type (as a string) we want to receive
            )
            .thenApply(HttpResponse::body)
            .thenAccept(System.out::println)
            .join();

    /**
     * sendAsync return a completableFuture data type
     * thenApply means once sendAsync is done,
     * we won`t to apply this method (HttpResponse::body) to the previous result CompleteableFuture>HttpResonse<String>>
     *  :: sign means this is a lambda expression that
     *  /HttpResponse::body) return the body as CompletableFuture<String>
     *    .thenAccept means we allow the out print line stream to be applied as lambda expression
     *    .join means combine all the result once it is completed
     */
  }
}
