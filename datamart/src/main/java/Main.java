import Control.Controller;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    controller.controller();
                } catch (IOException e) {
                    e.getMessage();
                }
            }
        }, 0, 3600000);
    }
}
