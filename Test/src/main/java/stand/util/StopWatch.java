package stand.util;

public class StopWatch 
{
	 private long startTime;

	 /**
	  * The time elapsed before the current {@link #startTime}.
	  */
	 private long previousElapsedTime;	

	 /**
	  * Whether the stop watch is currently running or not.
	  */
	 private boolean isRunning;

	 /**
	  * Starts or continues the stop watch.
	  *
	  * @see #pause()
	  * @see #reset()
	  */
	 public void start() {
		 synchronized (this) {
			 startTime = System.currentTimeMillis();
			 isRunning = true;
		 }
	 }

	 public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	/**
	  * Pauses the stop watch. It can be continued later from {@link #start()}.
	  *
	  * @see #start()
	  * @see #reset()
	  */
	 public void pause() {
		 synchronized (this) {
			 previousElapsedTime += System.currentTimeMillis() - startTime;
			 isRunning = false;
		 }
	 }

	 /**
	  * Stops and resets the stop watch to zero milliseconds.
	  *
	  * @see #start()
	  * @see #pause()
	  */
	 public void reset() {
		 synchronized (this) {
			 startTime = 0;
			 previousElapsedTime = 0;
			 isRunning = false;
		 }
	 }

	 /**
	  * @return the total elapsed time in milliseconds
	  */
	 public final long getElapsedTime() {
		 synchronized (this) {
			 long currentElapsedTime = 0;
			 if (isRunning) {
				 currentElapsedTime = System.currentTimeMillis() - startTime;
			 }
			 return previousElapsedTime + currentElapsedTime;
		 }
	 }
	    
	    
	 @Override
	 public String toString() {
		 return String.format("%d millis", getElapsedTime());
	 }
}
