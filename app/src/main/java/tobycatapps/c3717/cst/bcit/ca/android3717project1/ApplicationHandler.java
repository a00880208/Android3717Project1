package tobycatapps.c3717.cst.bcit.ca.android3717project1;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;


/**
 * Created by Eric Tsang on 14/09/2014.
 */
public class ApplicationHandler {



    // CLASS VARIABLES
    private static int uniqueMessageID = 0;

    // -------------------------------------------------------------------------
    // DEFINE STATIC CONSTANTS
    // -------------------------------------------------------------------------
    /*
     * Gets the number of available cores
     * (not always the same as the maximum number of cores)
     */
    private static final int NUMBER_OF_CORES =
            Runtime.getRuntime().availableProcessors();

    // Sets the amount of time an idle thread waits before terminating
    private static final int KEEP_ALIVE_TIME = 1;

    // Sets the Time Unit to seconds
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

    // Message types
    /**
     * Message type describing a message that is a runnable, that must be run
     * on some sort of worker thread. Such message types will be assigned to a
     * worker thread of this class to do its thing.
     */
    public static final int START_RUNNABLE_TASK = 0;

    /**
     * Message type describing a message that needs to run on the UI thread;
     * executed on the same thread as this class. Such message types must
     * implement the UpdateUITask interface. The updateUI method will be run on
     * the UI thread.
     * @see ApplicationHandler.UpdateUITask
     */
    public static final int UPDATE_UI_TASK = 1;



    // -------------------------------------------------------------------------
    // INSTANTIATE STATIC SUPPORT OBJECTS
    // -------------------------------------------------------------------------
    // A queue of Runnables instantiated as a LinkedBlockingQueue
    private static final BlockingQueue<Runnable> workQueue =
            new LinkedBlockingQueue<Runnable>();

    /*
     *  Object references; references don't have to be instantiated every
     *  time we need one.
     */
    private static Runnable mTempRunnable;

    // A Handler object that's attached to the UI thread
    public static Handler mHandler;

    // Creates a thread pool manager & instantiate one
    private static final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
            NUMBER_OF_CORES,       // Initial pool size
            NUMBER_OF_CORES,       // Max pool size
            KEEP_ALIVE_TIME,
            KEEP_ALIVE_TIME_UNIT,
            workQueue);

    // Instantiates a single ApplicationHandler
    static { ApplicationHandler applicationHandler = new ApplicationHandler(); }




    // -------------------------------------------------------------------------
    // CONSTRUCTORS
    // -------------------------------------------------------------------------
    /**
     * Constructs the work queues and thread pools used to download
     * and decode images. Because the constructor is marked private,
     * it's unavailable to other classes, even in the same package.
     */
    private ApplicationHandler() {

        // Defines a Handler object that's attached to the UI thread
        mHandler = new Handler(Looper.getMainLooper()) {

            /*
             * handleMessage() defines the operations to perform when
             * the Handler receives a new Message to process.
             */
            public void handleMessage(Message messageIn) {
                switch (messageIn.what) {

                    case ApplicationHandler.START_RUNNABLE_TASK:

                        // Adds task to the thread pool for execution on a
                        // worker thread
                        threadPool.execute((Runnable) messageIn.obj);
                        break;

                    case ApplicationHandler.UPDATE_UI_TASK:

                        // Runs task on the UI thread (this thread).
                        ((UpdateUITask) messageIn.obj).updateUI();
                        break;

                    default:
                        /* Pass along other messages from the UI */
                        super.handleMessage(messageIn);
                        break;
                }
            }
        };
    }




    // -------------------------------------------------------------------------
    // INTERFACE METHODS
    // -------------------------------------------------------------------------
    public static void enqueueGetImageAtURITask(String imageURI,
            ImageGridViewActivity.ImageAdapter imageAdapter) {
        ImageGridViewActivity.getImageAtURITask task =
                new ImageGridViewActivity.getImageAtURITask(
                        imageURI, imageAdapter);
        Message msg = ApplicationHandler.mHandler.obtainMessage(
                ApplicationHandler.START_RUNNABLE_TASK, // Message.what
                task                                    // Message.obj
        );
        msg.sendToTarget();
    }




    // -------------------------------------------------------------------------
    // TASK INTERFACES
    // -------------------------------------------------------------------------
    /**
     * Message type describing a message that needs to run on the UI thread;
     * executed on the same thread as this class. Such message types must
     * implement the UpdateUITask interface. The updateUI method will be run on
     * the UI thread.
     */
    public interface UpdateUITask {
        public void updateUI();
    }
}
