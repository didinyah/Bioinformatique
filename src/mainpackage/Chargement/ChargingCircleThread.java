package mainpackage.Chargement;

import java.util.ArrayList;
import java.util.Stack;

public class ChargingCircleThread extends Thread {
	private GlobalJPanel jp;
	private int dataCount;
	private ChargingCircle cc;
	private Stack<Integer> data;
	private int currentProgress = 0;
	protected boolean ready = true;
	private ArrayList<ChargingStick> delegate;
	
	//OuterCircle
	public ChargingCircleThread(GlobalJPanel j, ChargingCircle c){
		jp = j;
		cc = c;
		delegate = null;
	}
	
	//Middle circle
	public ChargingCircleThread(GlobalJPanel j, ChargingCircle c, ArrayList<ChargingStick> d, Stack<Integer> s){
		jp = j;
		cc = c;
		delegate = d;
		data = s;
	}
	
	public void run(){
		ArrayList<ChargingCircleThread> cct = null;
		ArrayList<ChargingStickThread> cst = null;
		if(delegate != null){
			cct = new ArrayList<ChargingCircleThread>();
			cst = new ArrayList<ChargingStickThread>();
			for(ChargingStick c : delegate){
				cct.add(new ChargingCircleThread(jp, c.out));
				cst.add(new ChargingStickThread(jp, c));
			}
			
			while(!data.empty()){
				
			}
		}
		
		
		for (int i = 0; i <= dataCount; i++){
			cc.updateProgress(i*(100/dataCount));
			jp.repaint();
			try{
				//Récupération des données ici
				Thread.sleep(2000);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}
		//stick1();
		//cc.updateProgress(0);
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
