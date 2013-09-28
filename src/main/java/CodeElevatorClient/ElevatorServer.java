package CodeElevatorClient;

import spark.Request;
import spark.Response;
import spark.Route;

import static spark.Spark.get;
import static spark.Spark.setPort;

public class ElevatorServer {
    private static final int PORT = 8080;

    public static void main(String[] args) {
        setPort(PORT);

        get(new Route("/hello") {
            @Override
            public Object handle(Request request, Response response) {
                return "Hello Binôme!";
            }
        });
    }

}
