import java.io.IOException;

import java.util.Timer;
import java.util.TimerTask;

public class Main {

    public static void main(String[] args) {
        Feeder feeder = new Feeder();
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    feeder.feeder();
                } catch (IOException e) {
                    e.getMessage();
                }
            }
        }, 0, 3600000);
    }
}
