package mainpackage.Chargement;

import java.util.ArrayList;
import java.util.Stack;

public class ChargingCircleThread extends Thread {
	//Attributs communs
	private GlobalJPanel jp;
	private ChargingCircle cc;
	private int currentProgress = 0;
	private String name = null;
	private int dataDone = 0;
	
	//Attributs pour MiddleCircle
	private Stack<String> data;
	private ArrayList<ChargingStick> delegate;
	
	//Attributs pour OuterCircle
	private ChargingStickThread sT;
	protected boolean ready = true;
	
	//Attributs pour rien
	private int dataCount = 10;
	
	
	//Constructor OuterCircle
	public ChargingCircleThread(GlobalJPanel j, ChargingCircle c, ChargingStickThread t){
		jp = j;
		cc = c;
		delegate = null;
		sT = t;
	}
	
	//Constructor MiddleCircle
	public ChargingCircleThread(GlobalJPanel j, ChargingCircle c, ArrayList<ChargingStick> d, Stack<String> s, String da){
		name = da;
		jp = j;
		cc = c;
		delegate = d;
		data = s;
	}
	
	public void run(){
		ArrayList<ChargingCircleThread> cct = null;
		ArrayList<ChargingStickThread> cst = null;
		
		if(name == null)
			cc.label =  "";
		else
			cc.label = name;
		
		//Si c'est un MiddleCircle
		if(delegate != null){
			cct = new ArrayList<ChargingCircleThread>();

			for(ChargingStick c : delegate){
				cct.add(new ChargingCircleThread(jp, c.out, new ChargingStickThread(jp, c, this)));
			}
			
			for(ChargingCircleThread c : cct){
				c.start();
			}
			
			int i=0;
			while(i <= dataCount){
				if(!data.empty()){
					boolean m = true;
					while (m) {
						for (ChargingCircleThread a : cct) {
							if (a.ready) {
								m=false;
								a.name = data.pop();
								break;
							}
						}
					}
					i++;
				}
			}
		}
		//Si c'est un OuterCircle
		else {
			while(true){
				if(name != null){
					ready = false;
					cc.label = name;
					for (int i = 0; i <= 100; i++){
						cc.updateProgress(i);
						jp.repaint();
						try{
							Thread.sleep(40);
						} catch (InterruptedException e){
							e.printStackTrace();
						}
					}
					sT.start();
					name = null;
					ready = true;
				}
			}
		}
		
		cc.label = "";
	}
	
	public void addStack(String g){
		data.push(g);
	}
	
	public void enterData(){
		dataDone++;
		cc.updateProgress(dataDone*(100/dataCount));
		jp.repaint();
	}
}
