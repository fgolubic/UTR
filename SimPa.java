package SimPa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Stack;
import java.util.TreeSet;

public class SimPa {
		public static ArrayList<String> ulNizovi =new ArrayList<String>();
		public static TreeSet<String> skupStanja =new TreeSet<String>();
		public static TreeSet<String> skupZnStoga =new TreeSet<String>();
		public static TreeSet<String> ulZnakovi =new TreeSet<String>();
		public static TreeSet<String> prihStanja =new TreeSet<String>();
		public static String pocStanje;
		public static char pocZnStoga;
		public static LinkedHashMap<String,String> prijelazi=new LinkedHashMap<String, String>();
		public static Stack<Character> stog = new Stack<Character>();
		public static String stanje;
		public static char vrhStoga;
		
		
		public static void main(String[] args) throws IOException{
			BufferedReader citac=new BufferedReader(new InputStreamReader(System.in));
			
			definirajPa(citac);
			
			for(String pom : ulNizovi)
				simuliraj(pom);
			
	
			
			
		}
		
	private static	void definirajPa(BufferedReader citac) throws IOException{
		String red;
		String[]  pomocni;
		
		red=citac.readLine();
		pomocni=red.split("\\|");
		for(int i=0;i<pomocni.length;i++)
			ulNizovi.add(pomocni[i]);
		
		red=citac.readLine();
		pomocni=red.split(",");
		for(int i=0;i<pomocni.length;i++)
			skupStanja.add(pomocni[i]);

		red=citac.readLine();
		pomocni=red.split(",");
		for(int i=0;i<pomocni.length;i++)
			ulZnakovi.add(pomocni[i]);
		
		red=citac.readLine();
		pomocni=red.split(",");
		for(int i=0;i<pomocni.length;i++)
			skupZnStoga.add(pomocni[i]);
		
		red=citac.readLine();
		if(!red.isEmpty()){
		pomocni=red.split(",");
		for(int i=0;i<pomocni.length;i++)
			prihStanja.add(pomocni[i]);
		}
		
		
		red=citac.readLine();
		pocStanje=red;
		
		red=citac.readLine();
		pocZnStoga=red.charAt(0);
		
		while(true){
			red=citac.readLine();
			if(red!=null) {
				pomocni=red.split("->");
				prijelazi.put(pomocni[0], pomocni[1]);
				
			}
			else break;
		}
		
		
	}

	private static void simuliraj(String line){
		String[] ulZnakovi=line.split(",");
		stog.add(pocZnStoga);
		System.out.print(pocStanje+"#"+pocZnStoga);
		System.out.print("|");
		vrhStoga=pocZnStoga;
		char[] znak;
		stanje=pocStanje;
		String[] slPrijelaza = new String[3];
		int i=0;
		int provjera1=0, provjera2=1;
		
		while(i!=ulZnakovi.length){
			slPrijelaza[0]=stanje+","+ulZnakovi[i]+","+vrhStoga;
			slPrijelaza[1]=stanje+","+"$"+","+vrhStoga;
			
			if(prijelazi.containsKey(slPrijelaza[0])){
				String vrijednost=prijelazi.get(slPrijelaza[0]);
				stanje=vrijednost.split(",")[0];
				System.out.print(stanje+"#");
				znak=znakStogaObrnuto(vrijednost.split(",")[1]);
				dodajNaStog(znak);
				ispisStoga();
				vrhStoga=stog.peek();
				i++;
				
			}
			
			else if(prijelazi.containsKey(slPrijelaza[1])){
				String vrijednost=prijelazi.get(slPrijelaza[1]);
				stanje=vrijednost.split(",")[0];
				System.out.print(stanje+"#");
				znak=znakStogaObrnuto(vrijednost.split(",")[1]);
				dodajNaStog(znak);
				ispisStoga();
				vrhStoga=stog.peek();
			}
			
			else{
				System.out.println("fail|0");
				provjera1=1;
				break;
			}
			
		}
		
	if(prihStanja.contains(stanje)){
		provjera2=0;
	}
	
	while(provjera2==1){
		provjera2=imaepsilonPrijelaz(stanje,vrhStoga);
		if(prihStanja.contains(stanje))
			provjera2=0;
		
	}
	
	if(provjera1==1)
		provjera1=0;
	else{
		if(prihStanja.contains(stanje)) 
			System.out.println("1");
		else System.out.println("0");
	}
		
	stog.clear();	
			
	}
	
	private static int imaepsilonPrijelaz(String St, char znStog){
		int promjena=0;
		String key=St +","+"$"+","+znStog;
		String pom;
		char[] pom2;
		if(prijelazi.containsKey(key)){
			pom=prijelazi.get(key);
			St=pom.split(",")[0];
			stanje=pom.split(",")[0];
			pom2=znakStogaObrnuto(pom.split(",")[1]);
			dodajNaStog(pom2);
			System.out.print(St+"#");
			ispisStoga();
			promjena=1;
		}
		return promjena;
	}
	
	
	
	private static char[] znakStogaObrnuto(String znak){
		String pomocni=new StringBuilder(znak).reverse().toString();
		char[] obrnut;
		obrnut=pomocni.toCharArray();
		return obrnut;
		
	}
	


	private static void ispisStoga(){
		ArrayList<Character> pomocno=new ArrayList<Character>();
		int i;
		if(!stog.isEmpty()){
			while(!stog.isEmpty()){
				pomocno.add(stog.peek());
				stog.pop();
				
			}
			
			for(i=pomocno.size()-1;i>=0;i--){
				stog.push(pomocno.get(i));
			}
			
			for(i=0;i<pomocno.size();i++){
				System.out.print(pomocno.get(i));
			}
		}
		else 
			System.out.print("$");
		
		System.out.print("|");
			
	}
	
	
	
	private static void dodajNaStog(char[] znak){
		if(!stog.isEmpty())
			stog.pop();
		
		for(char pom:znak)
			stog.push(pom);
		

		if(znak[0]=='$')
			stog.pop();
		
		if(stog.isEmpty())
			stog.push('$');
		
		
		
	}
}
