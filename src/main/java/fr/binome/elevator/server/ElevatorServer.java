package fr.binome.elevator.server;

import fr.binome.elevator.model.ElevatorResponse;
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
                //Test
                return "Hello World!";
            }
        });

        // /call?atFloor=[0-5]&to=[UP|DOWN]
        get(new Route("/call") {
            @Override
            public Object handle(Request request, Response response) {
                return "call, at floor: " + request.queryParams("atFloor") + ", to: " + request.queryParams("to");
            }
        });

        // /go?floorToGo=[0-5]
        get(new Route("/go") {
            @Override
            public Object handle(Request request, Response response) {
                return "go, floorToGo: " + request.queryParams("floorToGo");
            }
        });

        get(new Route("/userHasEntered") {
            @Override
            public Object handle(Request request, Response response) {
                return "userHasEntered";
            }
        });

        get(new Route("/userHasExited") {
            @Override
            public Object handle(Request request, Response response) {
                response.status(200);
                response.body("");

                return response.raw();
            }
        });

        // /reset?cause=information+message
        get(new Route("/reset") {
            @Override
            public Object handle(Request request, Response response) {
                return "reset";
            }
        });

        get(new Route("/nextCommand") {
            @Override
            public Object handle(Request request, Response response) {
                return ElevatorResponse.NOTHING;
            }
        });

    }

}
