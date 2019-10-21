class BaseThread extends Thread {
        public static int iNextThreadId = 1;
        protected int iThreadId;
        public BaseThread() {
              this.iThreadId = iNextThreadId;
              iNextThreadId++;
        }
       public BaseThread(int piTID)
       {
             this.iThreadId = piTID;
       }
}
