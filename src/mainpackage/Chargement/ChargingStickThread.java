package mainpackage.Chargement;

public class ChargingStickThread extends Thread {
	private GlobalJPanel jp;
	private int dataCount;
	private ChargingStick cs;
	protected ChargingCircleThread parent;
	
	public ChargingStickThread(GlobalJPanel j, ChargingStick c, ChargingCircleThread p){
		jp = j;
		cs = c;
		parent = p;
	}
	
	public void run(){
		for (int i = 0; i <= 100; i++){
			cs.updateProgress(i);
			jp.repaint();
			try{
				Thread.sleep(10);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}
		parent.enterData();
	}
}