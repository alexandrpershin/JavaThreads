package Task2;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class MaxValue extends Thread {
    private int lo;
    private int hi;
    private int[] arr;
    private static int maxInArray = 0;
    private int maxInThread = 0;

    public MaxValue(int[] arr, int lo, int hi) {
        this.lo = lo;
        this.hi = hi;
        this.arr = arr;
    }

    public void run() {
        for(int i = this.lo; i < this.hi; ++i) {
            if(this.arr[i] > this.maxInThread) {
                this.maxInThread = this.arr[i];
            }
        }

        if(this.maxInThread > maxInArray) {
            maxInArray = this.maxInThread;
        }

        System.out.println(currentThread().getName() + ".... Max element in this thread is = " + this.maxInThread);
    }

    public static int getMax(int[] arr) throws InterruptedException {
        int len = arr.length;
        boolean ans = false;
        ExecutorService executor = Executors.newFixedThreadPool(4);

        for(int i = 0; i < 4; ++i) {
            MaxValue worker = new MaxValue(arr, i * len / 4, (i + 1) * len / 4);
            executor.execute(worker);
        }

        executor.shutdown();

        while(!executor.isTerminated()) {
            ;
        }

        return maxInArray;
    }

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        int[] arr = new int[13];

        int max;
        for(max = 0; max < arr.length; ++max) {
            arr[max] = random.nextInt(30);
            System.out.print(arr[max] + "  ");
        }

        System.out.println();
        max = getMax(arr);
        System.out.println("Max element in array = " + max);
    }
}