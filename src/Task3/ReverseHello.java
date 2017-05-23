package Task3;

public class ReverseHello {
    static int threadNumber = 50;

    public static void main(String[] args) throws InterruptedException {

        Run run = new Run();
        Thread thread = new Thread(run);
        thread.start();
    }

    static class Run implements Runnable {

        @Override
        public void run() {
            if (threadNumber > 0) {
                new Thread(this).start();
                }
                System.out.println("Hello from " + threadNumber-- + " thread");
            }
        }
    }
