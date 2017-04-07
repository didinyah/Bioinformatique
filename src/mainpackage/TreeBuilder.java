package mainpackage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import com.google.common.base.Predicates;
import com.google.common.io.Resources;
import com.google.common.util.concurrent.AbstractExecutionThreadService;
import com.google.common.util.concurrent.AbstractService;

import mainpackage.Chargement.Chargement;

public class TreeBuilder extends AbstractExecutionThreadService {
	
	public static enum OrganismType {
		EUKARYOTES,
		PROKARYOTES,
		VIRUSES,
	}

	private OrganismType type;
	private String baseURL;
	private int currentPage;
	private List<Organism> organismList;
	private Chargement charg;
	
	private Retryer<List<Organism>> retryer;
	
	private Callable<List<Organism>> pageCallable = new Callable<List<Organism>>(){
		public List<Organism> call() throws MalformedURLException, IOException{
			return parseCurrentPage();
		}
	};
	
	public TreeBuilder(OrganismType type, Chargement charg){
		this.retryer = RetryerBuilder.<List<Organism>>newBuilder()
				.retryIfExceptionOfType(IOException.class)
				.retryIfRuntimeException()
				.withStopStrategy(StopStrategies.stopAfterAttempt(3))
				.withWaitStrategy(WaitStrategies.fibonacciWait())
				.build();
		
		switch(type){
		case EUKARYOTES:
			this.baseURL = Utils.TREE_EUKARYOTES_URL;
			break;
		case PROKARYOTES:
			this.baseURL = Utils.TREE_PROKARYOTES_URL;
			break;
		case VIRUSES:
			this.baseURL = Utils.TREE_VIRUSES_URL;
		}
		// 1293 �l�ments pour pages entre 1 et 10
		this.charg = charg;
		int nombreOrganismes = 1293;
		this.type = type;
		this.organismList = new ArrayList<Organism>();
	}
	
	public void readAllPages(Chargement charg){
		this.currentPage = 1;
		boolean cont = true;
		
		charg.log("D�but du t�l�chargement des organismes");
		while(cont && currentPage < 3) {
			try{
				List<Organism> result = retryer.call(this.pageCallable);
				if(result == null){
					cont = false;
				} else {
					this.organismList.addAll(result);
				}
			}catch(Exception e){
				e.printStackTrace();
				System.exit(1);
			}
			
			System.out.println(this.type.toString()+ " page : "+ this.currentPage);
			currentPage ++;
		}
		charg.log("Fin du t�l�chargement des organismes");
	}
	
	public List<Organism> parseCurrentPage() throws MalformedURLException, IOException{
		String webPage = new String(Resources.toByteArray(new URL(this.baseURL+this.currentPage)));
		
		if(webPage.split("-->")[1].trim().length() == 0){
			return null;
		}
		
		List<Organism> organismsList = new ArrayList<Organism>();
		
		Document doc = Jsoup.parse("<table>"+webPage+"</table>");
		
		Elements organisms = doc.select(".Odd,.Even");
		
		for(Iterator<Element> it = organisms.iterator(); it.hasNext();){
			Element organism = it.next();
			Elements replicons = organism.select("table");
			
			if(replicons.size() != 0){
				Elements organismTDs = organism.select("td");
				Iterator<Element> tdIterator = organismTDs.iterator();
				String organismName = tdIterator.next().text();
				if(type == OrganismType.PROKARYOTES) {
					tdIterator.next(); // Skip CladeID
				}
				if(type != OrganismType.VIRUSES) {
					tdIterator.next(); // Skip Strain
					tdIterator.next(); // Skip BioSample
				}
				String organismBioProject = tdIterator.next().text();
				String organismGroup = tdIterator.next().text();
				String organismSubGroup = tdIterator.next().text();
				String creationDate =  "";
				String modificationDate = "";
				String buffer = "";
				while(tdIterator.hasNext()){
					creationDate = modificationDate;
					modificationDate = buffer;
					buffer = tdIterator.next().text();
				}
				
				Organism currentOrganism = new Organism(type.name(), organismGroup, organismSubGroup, organismName, creationDate, modificationDate);

				boolean validOrganism = false;
				
				Elements repliconsTDs = replicons.iterator().next().select("td");
				for(Iterator<Element> it2 = repliconsTDs.iterator(); it2.hasNext();) {
					Element replicon = it2.next();
					if(replicon.id().length() != 0) {
						// Skip the "show more button"
						// The show more button is the only one with an id
						continue;
					}
					String repliconName = "";
					if(type != OrganismType.VIRUSES) {
						repliconName = replicon.text().split("/")[0].replace(" ", "_").replace(":", "_");
					}
					String[] repliconIDs = replicon.select("a").text().split(" ");
					String repliconID = "";
					boolean validRepliconFound = false;
					// TODO : Check si on garde MT, CL et CH
					for(String rID : repliconIDs) {
						if(rID.startsWith("NC") /*||
						   rID.startsWith("MT") ||
						   rID.startsWith("CL") ||
						   rID.startsWith("CH")*/){
							repliconID = rID;
							validRepliconFound = true;
							break;
						}
					}
					if(validRepliconFound){
						validOrganism = true;
						if(type ==OrganismType.VIRUSES){
							repliconName = repliconID;
						}
						currentOrganism.addReplicon(repliconName, repliconID);
					}
				}
				if(validOrganism){
					organismsList.add(currentOrganism);
					//System.out.println(currentOrganism.getKingdom());
					//charg.log(currentOrganism.getName());
					charg.send(currentOrganism);
				}
			}
		}
		return organismsList;
	}
	
	public List<Organism> organisms(){
		return this.organismList;
	}
	
	
	@Override
	protected void run() throws Exception {
		this.readAllPages(this.charg);
	}
}
