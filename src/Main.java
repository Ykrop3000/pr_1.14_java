public class Main {
    public static void main(String[] args) {
        System.out.println("Андреев Григорий Даниилович РИБО-01-21");
        Thread thread1 = new Thread(new MyThread(), "Thread-0");
        Thread thread2 = new Thread(new MyThread(), "Thread-1");
        Thread thread3 = new Thread(new MyThread(), "Thread-2");

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            Thread.sleep(5000); //задержка в 5 секунд
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread1.interrupt();
        thread2.interrupt();
        thread3.interrupt();
    }

    static class MyThread implements Runnable {
        private static final Object lock = new Object();
        private static int currentThread = 0;

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (lock) {
                    while (currentThread != Integer.parseInt(Thread.currentThread().getName().split("-")[1])) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    System.out.println(Thread.currentThread().getName());
                    currentThread = (currentThread + 1) % 3;
                    lock.notifyAll();
                }
                try {
                    Thread.sleep(1000); //задержка в 1 секунду
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
