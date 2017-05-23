package Task5;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

public class Main {
    static BookFactory book;


    public static void main(String[] args) {

        Runnable customer = () -> {

            while (true) {
                try {
                    book.buyBook();
                    Thread.sleep(2000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        };

        Runnable creator = () -> {

            while (true) {
                try {
                    book.createBook();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable accountant = () -> {

            while (true) {
                try {
                    Thread.currentThread().join(100);
                    System.out.println("Created books = " + book.getCreatedBookCount());
                    System.out.println("Sold books = " + book.getSoldBookCount());
                    System.out.println("Current books count = " + book.getBookCount());
                    System.out.println("Total price = " + book.getTotalPrice());
                    System.out.println("*************");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                try {
                    TimeUnit.SECONDS.sleep(4);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        };

        book = new BookFactory();
        new Thread(customer).start();
        new Thread(creator).start();
        new Thread(accountant).start();
    }
}


class BookFactory {
    private int bookCount;
    private AtomicInteger price;
    private AtomicInteger totalPrice;
    private int soldBookCount;
    private int createdBookCount;

    public BookFactory() {
        this.bookCount = 0;
        this.price = new AtomicInteger(100);
        this.totalPrice = new AtomicInteger(0);
        this.soldBookCount = 0;
        this.createdBookCount = 0;
    }

    public int getBookCount() {
        return bookCount;
    }

    public int getTotalPrice() {
        return this.totalPrice.get();
    }

    public int getPrice() {
        return price.get();
    }

    public int getSoldBookCount() {
        return soldBookCount;
    }

    public int getCreatedBookCount() {
        return createdBookCount;
    }

    public synchronized void buyBook() throws InterruptedException {
        while (bookCount < 1) {
            wait();
        }
        updateTotalPrice();
        soldBookCount++;
        bookCount--;
        notify();
    }

    private void updateTotalPrice() {
        totalPrice.addAndGet(price.get());
    }

    public synchronized void createBook() throws InterruptedException {

        while (bookCount >= 8) {
            wait();
        }
        bookCount++;
        createdBookCount++;
        notify();

    }
}








