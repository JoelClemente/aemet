import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) {
        ApiREST apiRest = new ApiREST();
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                apiRest.apiRest();
            }
        }, 0, 3600000);
    }
}