package mainpackage.Chargement;

import java.util.ArrayList;
import java.util.Stack;

public class ChargingCircleThread extends Thread {
	private GlobalJPanel jp;
	private int dataCount;
	private ChargingCircle cc;
	private Stack<String> data;
	private int currentProgress = 0;
	protected boolean ready = true;
	private ArrayList<ChargingStick> delegate;
	private String name;
	
	//OuterCircle
	public ChargingCircleThread(GlobalJPanel j, ChargingCircle c, String da){
		name = da;
		jp = j;
		cc = c;
		delegate = null;
	}
	
	//Middle circle
	public ChargingCircleThread(GlobalJPanel j, ChargingCircle c, ArrayList<ChargingStick> d, Stack<String> s, String da){
		name = da;
		jp = j;
		cc = c;
		delegate = d;
		data = s;
		dataCount = s.size();
	}
	
	public void run(){
		ArrayList<ChargingCircleThread> cct = null;
		ArrayList<ChargingStickThread> cst = null;
		
		cc.label = name;
		
		if(delegate != null){
			cct = new ArrayList<ChargingCircleThread>();
			cst = new ArrayList<ChargingStickThread>();

			for(ChargingStick c : delegate){
				ChargingCircleThread nico = new ChargingCircleThread(jp, c.out, data.pop());
				ChargingStickThread charge = new ChargingStickThread(jp, c);
			}
			int i=0;
			while(i <= dataCount){
				
				int m = 1;
				while (m==1) {
					for (ChargingCircleThread a : cct) {
						if (a.getState() == State.TERMINATED) {
							m=0;
						}
						if (m==0) {
							break;
						}
					}
				}
				i++;
			}
			cc.updateProgress(i*(100/dataCount));
			jp.repaint();
			try{
				//Récupération des données ici
				Thread.sleep(2000);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}
		
		else {
			for (int i = 0; i <= 100; i++){
				cc.updateProgress(i);
				jp.repaint();
				try{
					Thread.sleep(15);
				} catch (InterruptedException e){
				e.printStackTrace();
				}
			}
		}
		
		//stick1();
		//cc.updateProgress(0);
		
		cc.label = "";
		
	}
	
	public boolean addData(int d){
		cc.updateProgress(d);
		jp.repaint();
		
		if(d >= 100)
			return false;
		else
			return true;
	}
}
