class Semaphore {
    private int value;
    private int mutex;

    public Semaphore(int value) {
        this.value = value;
        this.mutex = 1;
    }

    public Semaphore() {
        this(0);
    }

    public synchronized void Wait() {
        this.value--;
        while (this.mutex < 1) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Semaphore::Wait() - caught InterruptedException: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public synchronized void Signal() {
        ++this.value;
        ++mutex;
        notify();
    }

    public synchronized void P() {
        this.Wait();
    }

    public synchronized void V() {
        this.Signal();
    }
}
