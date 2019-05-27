package simulation;

public class simulation extends Thread{
	@Override
	public void run() {
		gainsotck();
		while(true) {
			try {
				sleep(10000);
				updatestock();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	void updatestock() {
		
	}
	void gainsotck() {
		
	}
}
