package minDKA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeSet;

public class MinDKA {
	public static TreeSet<String> skupSt;
	public static TreeSet<String> simboliAbc;
	public static TreeSet<String> prihSt;
	public static String pocSt;
	public static LinkedHashMap<String, String> prijelazi;
	public static LinkedHashMap<String,String> oznacenaSt = new LinkedHashMap<String,String>();
	public static LinkedHashMap<String,String> mapIspis = new LinkedHashMap<String,String>();
	
	
	
	public static void main(String args[]) throws IOException{
		BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
		String red;
		String[] pomocni;
		prijelazi=new LinkedHashMap<String, String>();
		skupSt=new TreeSet<String>();
		prihSt = new TreeSet<String>();
		simboliAbc = new TreeSet<String>();
		int i, brReda, brPraznih=0;
		brReda=1;
		while((red=reader.readLine())!=null && brPraznih<2){
			if (brReda==1){
				pomocni=red.split(",");
				brReda++;
				for(i=0;i<pomocni.length;i++)
					skupSt.add(pomocni[i]);
			}
			
			else if (brReda==2){
					pomocni=red.split(",");
					brReda++;
					for(i=0;i<pomocni.length;i++)
						simboliAbc.add(pomocni[i]);
			}
			
			else if(brReda==3){
				brReda++;
				if(red.isEmpty()) brPraznih++;
				else{
					pomocni=red.split(",");
					for(i=0;i<pomocni.length;i++)
						prihSt.add(pomocni[i]);
				}
			}
			
			else if(brReda==4){
				pocSt=red;
				brReda++;
			}
			
			else{
				if(red.isEmpty()) brPraznih++;
				else prijelazi.put(red.split("->")[0], red.split("->")[1]);
				
			}
				
		}
		
		dohvatljivaStanja();
		minAut();
		
		printajTree(skupSt);
		printajTree(simboliAbc);
		printajTree(prihSt);
		
		System.out.println(pocSt);
		
		for (String pom : mapIspis.keySet()) {
			System.out.println(pom + "->" + mapIspis.get(pom));
		}
		
		
	}
	
	private static void dohvatljivaStanja(){
		List<String> dohvSt = new ArrayList<String>();
		dohvSt.add(pocSt);
		LinkedHashMap<String,String> noviPrijelazi= new LinkedHashMap<String,String>();
		TreeSet<String> pomocno= new TreeSet<String>();
		int i;
		for(i=0;i<dohvSt.size();i++)
			for(String pom   : prijelazi.keySet())
				if(pom.split(",")[0].equals(dohvSt.get(i)) && !dohvSt.contains(prijelazi.get(pom)))
					dohvSt.add(prijelazi.get(pom));
				
		TreeSet<String> dohvatljivaSt = new TreeSet<String>();
		dohvatljivaSt.addAll(dohvSt);
		
		for(String pom : prijelazi.keySet())
			if(dohvatljivaSt.contains(pom.split(",")[0]))
				noviPrijelazi.put(pom, prijelazi.get(pom));
		
		prijelazi.clear();
		prijelazi=noviPrijelazi;
		skupSt.clear();
		skupSt=dohvatljivaSt;
		
		for(String pom : dohvatljivaSt)
			if(prihSt.contains(pom))
				pomocno.add(pom);
		
		prihSt.clear();
		prihSt.addAll(pomocno);
		pomocno.clear();
			
		
		
	}
	
	public static void minAut(){
		ArrayList<String> svaStanja=new ArrayList<String>();
		TreeSet<String> kombinacije= new TreeSet<String>();
		TreeSet<String> istovjetnaSt=new TreeSet<String>();
		ArrayList<String> lista1 =new ArrayList<String>();
		ArrayList<String> lista2 =new ArrayList<String>();
		String key;
		String kljuc1, kljuc2, st1, st2;
		svaStanja.addAll(skupSt);
		for(int i=0; i<skupSt.size()-1 ;i++)
			for(int j=i+1; j<skupSt.size(); j++)
				if((!(prihSt.contains(svaStanja.get(i))) && prihSt.contains(svaStanja.get(j))) || ((prihSt.contains(svaStanja.get(i)))&& !(prihSt.contains(svaStanja.get(j))))){
					key= svaStanja.get(i) +"," + svaStanja.get(j);
					oznacenaSt.put(key,"X");
				}
		
		for (int i = 0; i < skupSt.size() - 1; i++){ 
			for (int j = i + 1; j < skupSt.size(); j++) {
				for (String simbol : simboliAbc) {
					kljuc1=svaStanja.get(i) + (",") + simbol;
					kljuc2=svaStanja.get(j) + (",") + simbol;
					st1=prijelazi.get(kljuc1);
					st2=prijelazi.get(kljuc2);
					if(st1.compareTo(st2)> 0){
						key= st2 +"," + st1;
						if(oznacenaSt.containsKey(key)) oznacenaSt.put(svaStanja.get(i)+ "," + svaStanja.get(j), "X");
						else {
							lista1.add(key);
							lista2.add(svaStanja.get(i)+","+svaStanja.get(j));
						}
					}
					else {
						key= st1+"," + st2;
						if(oznacenaSt.containsKey(key)) oznacenaSt.put(svaStanja.get(i)+ "," + svaStanja.get(j), "X");
						else {
							lista1.add(key);
							lista2.add(svaStanja.get(i)+","+svaStanja.get(j));
						}
					}
				}
			}
		}
			
			oznaciListe(lista1,lista2);
		
			kombinacije=brojKombinacijaSt(svaStanja);
			
				for(String parSt : kombinacije)
				if(!oznacenaSt.containsKey(parSt))
					istovjetnaSt.add(parSt);
			
			mapIspis=prijelazi;
			makniVisestrukaSt(mapIspis, istovjetnaSt);
			
			
			
		}
	
	
	private static void makniVisestrukaSt(LinkedHashMap<String,String> tablica, TreeSet<String> istaSt ){
		for(String stanja : istaSt){
			for(String simbol : simboliAbc){
				String pom = stanja.split(",")[1] + "," + simbol;
				tablica.remove(pom);
				skupSt.remove(stanja.split(",")[1]);
				prihSt.remove(stanja.split(",")[1]);
				if(stanja.split(",")[1].equals(pocSt))
					pocSt=stanja.split(",")[0];
				}
		}
		
		for(String pom : tablica.keySet())
			for(String stanja : istaSt){
				if(tablica.get(pom).equals(stanja.split(",")[1]))
					tablica.put(pom, stanja.split(",")[0]);
			}
			
		
	}
	

	private static void printajTree(TreeSet<String> Tree){

	if(Tree.size()==0)
		System.out.println();
		
	else{
		
	String zadnji=Tree.last();
	
		
	for(String pom : Tree){
		System.out.print(pom);
		if(pom.equals(zadnji))
			break;
		System.out.print(",");
	}
	System.out.println();
}
	}

	private static TreeSet<String> brojKombinacijaSt(ArrayList<String> svaSt){
		TreeSet<String> komb= new TreeSet<String>();
		for(int i=0; i<svaSt.size()-1;i++)
			for(int j=i+1; j<svaSt.size();j++)
				komb.add(svaSt.get(i)+ "," + svaSt.get(j));
		return komb;
	}


	private static void oznaciListe(ArrayList<String> prva, ArrayList<String> druga){
		int promjena=0;
		do{
			int i=0;
			for(String stanja : prva){
				if(oznacenaSt.containsKey(stanja)){
					oznacenaSt.put(druga.get(i), "X");
					promjena++;
				}
				i++;
			}
			
		}while(promjena<prva.size()*4 && promjena!=0);
	}





}
