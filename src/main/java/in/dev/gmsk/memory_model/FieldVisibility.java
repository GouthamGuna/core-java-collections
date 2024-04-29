package in.dev.gmsk.memory_model;

public class FieldVisibility implements Runnable{

    volatile int x = 0; // volatile change the threading ops.

    protected void writeThread(){
        x = 1;
    }

    protected synchronized void readerThread(String msg){
        int r = x;
        System.out.println("r = " + r +"\t msg : "+msg);
    }

    @Override
    public void run() {
        readerThread("run method invoked...");
    }
}
