class Semaphore {
    private int value;
    private boolean flag;

    public Semaphore(int value) {
        this.value = value;
    }

    public Semaphore() {
        this(0);
    }

    public synchronized void Wait() {
        this.value--;
        if(value != -1) {
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
        notify();
    }

    public synchronized void P() {}

    public synchronized void V() {
        this.Signal();
    }
}
