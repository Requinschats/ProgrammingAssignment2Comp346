import CharStackExceptions.*;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class StackManager {

    private static CharStack stack = new CharStack();
    private static final int NUM_ACQREL = 4; // Number of Producer/Consumer threads
    private static final int NUM_PROBERS = 1; // Number of threads dumping stack
    private static int numberOfSteps = 3; // Number of steps they take

    public static Semaphore mainSemaphore = new Semaphore(0);
    public static Semaphore turnSemaphore = new Semaphore(-1);
    public static int producerRunCount = 0;
    public static String content;

    public static void main(String[] argv) {
        StackManager.printInitialStackStats(stack);

        Consumer ab1 = new Consumer();
        Consumer ab2 = new Consumer();
        Producer rb1 = new Producer();
        Producer rb2 = new Producer();
        CharStackProber csp = new CharStackProber();
        System.out.println("Two consumers, Twos producers and csp created");

        startThreads(ab1, ab2, rb1, rb2, csp);
        closeThreads(ab1, ab2, rb1, rb2, csp);
        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("text.txt"), StandardCharsets.UTF_8))) {
            out.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class Consumer extends BaseThread {
        private char copy;
        public void run() {
            turnSemaphore.Wait();
            mainSemaphore.Wait();
            System.out.println("Consumer thread [TID=" + this.iThreadId + "] starts executing.");
            content += "\nConsumer thread [TID=" + this.iThreadId + "] starts executing.";
            for (int i = 0; i < StackManager.numberOfSteps; i++) {
                // Insert your code in the following:
                try {
                    this.copy = CharStack.pop();
                } catch (CharStackEmptyException e) {
                    e.printStackTrace();
                }
                System.out.println("Consumer thread [TID=" + this.iThreadId + "] pops character =" + this.copy);
                content += "\nConsumer thread [TID=" + this.iThreadId + "] pops character =" + this.copy;
            }
            System.out.println("\nConsumer thread [TID=" + this.iThreadId + "] terminates.");
            content += "\nConsumer thread [TID=" + this.iThreadId + "] terminates.";
            mainSemaphore.Signal();
            turnSemaphore.Signal();
        }

    }


    static class Producer extends BaseThread {
        private char block; // block to be returned

        public void run() {
            mainSemaphore.Wait();
            System.out.println("Producer thread [TID=" + this.iThreadId + "] starts executing.");
            content += "\nProducer thread [TID=" + this.iThreadId + "] starts executing.";
            for (int i = 0; i < StackManager.numberOfSteps; i++) {
                // Insert your code in the following:
                try {
                    char charToPush = (char) (CharStack.pick() + 1);
                    CharStack.push(charToPush);
                    block = charToPush;
                } catch (CharStackFullException e) {
                    e.printStackTrace();
                } catch (CharStackEmptyException e) {
                    e.printStackTrace();
                }
                System.out.println("Producer thread [TID=" + this.iThreadId + "] pushes character =" + this.block);
                content += "\nProducer thread [TID=" + this.iThreadId + "] pushes character =" + this.block;
            }
            System.out.println("Producer thread [TID=" + this.iThreadId + "] terminates.");
            content += "\nProducer thread [TID=" + this.iThreadId + "] terminates.";
            mainSemaphore.Signal();
            producerRunCount++;
            if(producerRunCount == 2){
                turnSemaphore.Signal();
            }
        }
    }

    static class CharStackProber extends BaseThread {
       private void printStackContent(int numberOfSteps) throws CharStackInvalidAceessException, CharStackInvalidAceessException {
           for (int j = 0; j < 6; j++) {
               mainSemaphore.Wait();
               System.out.print("Stack S = (");
               content += "Stack S = (";
               for (int i = 0; i < 10; i++) {
                   // Insert your code in the following.
                   if (String.valueOf(stack.getAt(i)).matches("^[a-zA-Z]*$")) {
                       System.out.print("[" + stack.getAt(i) + "]");
                       content += "[" + stack.getAt(i) + "]";
                   } else {
                       System.out.print("[$]");
                       content += "[$]";
                   }

               }
               System.out.println();
               mainSemaphore.Signal();
           }
        }
        public void run() {
            System.out.println("CharStackProber thread [TID=" + this.iThreadId + "] starts executing.");
            content += "\nCharStackProber thread [TID=" + this.iThreadId + "] starts executing.";
            try {
                printStackContent(StackManager.numberOfSteps);
            } catch (CharStackInvalidAceessException e) {
                e.printStackTrace();
            }
        }
    }

    private static void printInitialStackStats(CharStack stack) {
        try {
            System.out.println("Main thread starts executing.");
            System.out.println("Initial value of top = " + stack.getTop() + ".");
            System.out.println("Initial value of stack top = " + stack.pick() + ".");
            System.out.println("Main thread will now fork several threads.");
        } catch (CharStackEmptyException e) {
            System.out.println("Caught exception: StackCharEmptyException");
            System.out.println("Message : " + e.getMessage());
            System.out.println("Stack Trace : ");
            e.printStackTrace();
        }
    }

    private static void closeThreads(Thread ab1, Thread ab2, Thread rb1, Thread rb2, Thread csp) {
        try {
            ab1.join();
            ab2.join();
            rb1.join();
            rb2.join();
            csp.join();

            System.out.println("System terminates normally.");
            System.out.println("Final value of top = " + stack.getTop() + ".");
            System.out.println("Final value of stack top = " + stack.pick() + ".");
            System.out.println("Final value of stack top-1 = " + stack.getAt(stack.getTop() - 1) + ".");
            // TODO: fix System.out.println("Stack access count = " + stack.getAccessCounter());
        } catch (InterruptedException e) {
            System.out.println("Caught InterruptedException: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.out.println("Caught exception: " + e.getClass().getName());
            System.out.println("Message : " + e.getMessage());
            System.out.println("Stack Trace : ");
            e.printStackTrace();
        }
    }
    private static void startThreads(Thread ab1, Thread ab2, Thread rb1, Thread rb2, Thread csp){
        ab1.start();
        rb1.start();
        ab2.start();
        rb2.start();
        csp.start();
    }
}
