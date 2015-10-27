import com.google.gson.Gson;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import static spark.Spark.*;
import spark.*;


/**
 * Created by vladimirsivanovs on 16/10/2015.
 */
public class WebApp {

    private final static Logger LOGGER = LogManager.getLogger("SERVER");

    public static void main(String[] args) {

//        Base64.Encoder encoder = Base64.getEncoder();

        staticFileLocation("/");
        externalStaticFileLocation("/src/main/resources");

        get("/json", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                Gson gson = new Gson();
                String sNum = request.queryParams("num");
                String sDenom = request.queryParams("denom");
                int num = 0;
                int denom = 0;
                try {
                    num = Integer.parseInt(sNum);
                    denom = Integer.parseInt(sDenom);
                } catch (NumberFormatException ex) {
                    Failure failure = new Failure("Server failed");
                    String failureJson = gson.toJson(failure);
                    LOGGER.error("FAILED: cant parse strings! SENDING BACK:" + failureJson );
                    return failureJson;
                }

                LOGGER.info("REQUEST: " + request.queryString());

                NumberWrapper nw = new NumberWrapper(new Number(num, denom), LOGGER);

                String json = gson.toJson(nw.number);
                LOGGER.info("SENDING BACK: " + json);
                return json;
            }
        });

//        get("/encode", (req, res) -> encoder.encodeToString(req.queryParams("string").getBytes()));
//        post("/encode", (req, res) ->  encoder.encodeToString(req.body().getBytes()));
//
//
//
//        get("/params", (req, res) -> req.headers().stream()
//                        .filter((i -> i.contains("Accept")))
//                        .map(i -> i + " : " + req.headers(i))
//                        .collect(Collectors.joining("<br>"))
//                        + "<br><br>" +
//                req.queryParams().stream()
//                        .map(i -> i + " : " + req.queryParams(i))
//                        .collect(Collectors.joining("<br>"))
//        );
    }
}

class Number{
    private int num;
    private int denom;
    private int result;

    public Number(int num, int denom) {
        this.num = num;
        this.denom = denom;
        this.result = num / denom;
    }

    public int getNum() {
        return num;
    }

    public int getDenom() {
        return denom;
    }

    public int getResult() {
        return result;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setDenom(int denom) {
        this.denom = denom;
    }

    public void setResult(int result) {
        this.result = result;
    }
}

class NumberWrapper {
    public Number number;
    public Logger logger;

    public NumberWrapper(Number number, Logger logger) {
        this.number = number;
        this.logger = logger;
        logger.info("CREATING : " + number.getNum() + " : " + number.getDenom() + " = " + number.getResult());
    }
}

class Failure {
    public String msg;

    public Failure(String msg) {
        this.msg = msg;
    }
}