package fr.binome.elevator.server;

import fr.binome.elevator.model.ElevatorResponse;
import fr.binome.elevator.model.context.ElevatorContext;
import spark.Request;
import spark.Response;
import spark.Route;

import static spark.Spark.get;
import static spark.Spark.setPort;

public class ElevatorServer {
    private static final int PORT = new Integer(System.getenv("PORT"));

    public static void main(String[] args) {
        setPort(PORT);

//        final Elevator elevator = new BasicElevator();
        final ElevatorContext elevatorContext = new ElevatorContext();

        get(new Route("/hello") {
            @Override
            public Object handle(Request request, Response response) {
                //Test
                return "Hello world!";
            }
        });

        // /call?atFloor=[0-5]&to=[UP|DOWN]
        get(new Route("/call") {
            @Override
            public Object handle(Request request, Response response) {
                elevatorContext.call(request.queryMap("atFloor").integerValue(), request.queryMap("to").value());

                return sendOkResponse(response);
            }
        });

        // /go?floorToGo=[0-5]
        // /go?cabin=[0|1]&floorToGo=x
        get(new Route("/go") {
            @Override
            public Object handle(Request request, Response response) {
                Integer floorToGo = request.queryMap("floorToGo").integerValue();
                Integer cabin = request.queryMap("cabin").integerValue();

                elevatorContext.go(cabin, floorToGo);

                return sendOkResponse(response);
            }
        });

        // /userHasEntered?cabin=[0|1]
        get(new Route("/userHasEntered") {
            @Override
            public Object handle(Request request, Response response) {
                Integer cabin = request.queryMap("cabin").integerValue();

                elevatorContext.userHasEntered(cabin);

                return sendOkResponse(response);
            }
        });

        // /userHasExited?cabin=[0|1]
        get(new Route("/userHasExited") {
            @Override
            public Object handle(Request request, Response response) {
                Integer cabin = request.queryMap("cabin").integerValue();

                elevatorContext.userHasExited(cabin);

                return sendOkResponse(response);
            }
        });

        // /reset?lowerFloor=-13&higherFloor=27&cabinSize=40&cabinCount=2&cause=NEW+RULES+FTW
        get(new Route("/reset") {
            @Override
            public Object handle(Request request, Response response) {
                Integer lowerFloor = request.queryMap("lowerFloor").integerValue();
                Integer higherFloor = request.queryMap("higherFloor").integerValue();
                Integer cabinSize = request.queryMap("cabinSize").integerValue();
                Integer cabinCount = request.queryMap("cabinCount").integerValue();
                String cause = request.queryMap("cause").toString();

                elevatorContext.reset(lowerFloor, higherFloor, cabinSize, cabinCount, cause);

                return sendOkResponse(response);
            }
        });

        get(new Route("/nextCommands") {
            @Override
            public Object handle(Request request, Response response) {

                return elevatorContext.nextCommands();
            }
        });

    }

    private static Object sendOkResponse(Response response) {
        response.status(200);
        response.body("");

        return response.raw();
    }

}
