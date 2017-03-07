package mainpackage.Chargement;

public class ChargingStickThread extends Thread {
	private GlobalJPanel jp;
	private int dataCount;
	private ChargingStick cs;
	
	public ChargingStickThread(GlobalJPanel j, ChargingStick c){
		jp = j;
		cs = c;
	}
	
	public void run(){
		for (int i = 0; i <= dataCount; i++){
			//cc.updateProgress(i*(100/dataCount));
			jp.repaint();
			try{
				//Récupération des données ici
				Thread.sleep(2000);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}
		//stick1(s);
		//cc.updateProgress(0);
	}
}