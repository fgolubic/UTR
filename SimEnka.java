package SimEnka;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class SimEnka {

	private static List<String> ulazniNizovi;
	private static Set<String> skupStanja;
	private static Set<String> skupSimbolaAbecede;
	private static Set<String> skupPrihvatljivihStanja;
	private static String pocetnoStanje;
	private static List<String> skupPrethodnihStanja = new ArrayList<String>();
	private static List<String> skupProcitanihSimbola = new ArrayList<String>();
	private static List<String> skupSljedecihStanja = new ArrayList<String>();
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String line;
		
		line = reader.readLine();
		String[] line1 = line.split("\\|");
		ulazniNizovi = new ArrayList<>();
		for (int i = 0; i < line1.length; i++) {
			ulazniNizovi.add(line1[i]);
		}
		
		line=reader.readLine();
		String[] line2 = line.split(",");
		skupStanja = new TreeSet<>();
		for (int i = 0; i < line2.length; i++) {
			skupStanja.add(line2[i]);
		}
		
		line = reader.readLine();
		String[] line3 = line.split(",");
		skupSimbolaAbecede = new TreeSet<>();
		for (int i = 0; i < line3.length; i++) {
			skupSimbolaAbecede.add(line3[i]);
		}
		
		line = reader.readLine();
		String[] line4 = line.split(",");
		skupPrihvatljivihStanja = new TreeSet<>();
		for (int i = 0; i < line4.length; i++) {
			skupPrihvatljivihStanja.add(line4[i]);
		}
		
		line = reader.readLine();
		pocetnoStanje = line;
		
		while ((line = reader.readLine()) != null && !(line.isEmpty())) {
			String [] funkcijaPrijelaza = line.split("->");
			String [] funkcijaPrijelazaArgs = funkcijaPrijelaza[0].split(",");
			skupPrethodnihStanja.add(funkcijaPrijelazaArgs[0]);
			skupProcitanihSimbola.add(funkcijaPrijelazaArgs[1]);
			skupSljedecihStanja.add(funkcijaPrijelaza[1]);
		}
		
		reader.close();
		for(String tmp : ulazniNizovi) {
			String[] ulazniNiz  = tmp.split(",");
			ispisStanja(executeSljedecaStanja(ulazniNiz));
		}
	}
	
	public static void ispisStanja(List<String> stanja) {
		for(int i = 0; i < stanja.size(); i++) {			
			System.out.printf(stanja.get(i));
		}		
		System.out.println();
	}
	
	public static void executeEpsilonPrijelazi(List<String> skupTrenutnihStanja) {
		boolean epsilonPrijelaz = true;
		while(epsilonPrijelaz) {
			epsilonPrijelaz = false;
			for(int i = 0; i < skupTrenutnihStanja.size(); i++) {
				for(int j = 0; j < skupPrethodnihStanja.size(); j++) {
					if(skupProcitanihSimbola.get(j).equals("$") && skupTrenutnihStanja.get(i).equals(skupPrethodnihStanja.get(j))) {
						String [] stanja = skupSljedecihStanja.get(j).split(",");
						for (String tmp : stanja) {
							if (!skupTrenutnihStanja.contains(tmp)){
								skupTrenutnihStanja.add(tmp);
								epsilonPrijelaz = true;											
							}
						}
					}
				}
			}
		}	
	}
	
	public static List<String> executeSljedecaStanja(String[] ulazniNiz) {
		List<String> sviPrijelazi = new ArrayList<String>();
		List<String> trenutnaTrenutnaStanja = new ArrayList<String>();
		List<String> trenutnaSljedecaStanja = new ArrayList<String>(); 

		trenutnaTrenutnaStanja.add(pocetnoStanje);		

		for(int i = 0; i < ulazniNiz.length; i++) {	
			executeEpsilonPrijelazi(trenutnaTrenutnaStanja); 
			if(trenutnaTrenutnaStanja.size() != 1 && trenutnaTrenutnaStanja.contains("#")) trenutnaTrenutnaStanja.remove("#");		
			for(int j = 0; j < trenutnaTrenutnaStanja.size(); j++) {														
				for(int k = 0; k < skupPrethodnihStanja.size(); k++) {																			
					if(skupProcitanihSimbola.get(k).equals(ulazniNiz[i])  && trenutnaTrenutnaStanja.get(j).equals(skupPrethodnihStanja.get(k))) {
						String[] stanje = skupSljedecihStanja.get(k).split(",");
						for (String tmp : stanje) {
							if (!trenutnaSljedecaStanja.contains(tmp)) { 
								trenutnaSljedecaStanja.add(tmp);
							}
						}
					}
				}
			}
			executeEpsilonPrijelazi(trenutnaSljedecaStanja);
			if(trenutnaSljedecaStanja.isEmpty()) {
				trenutnaSljedecaStanja.add("#");
			}
			if(trenutnaSljedecaStanja.size() != 1 && trenutnaSljedecaStanja.contains("#")) {
				trenutnaSljedecaStanja.remove("#");
			}
			Collections.sort(trenutnaTrenutnaStanja);
			
			for(String tmp: trenutnaTrenutnaStanja) {
				if(!sviPrijelazi.contains(tmp)){
					sviPrijelazi.add(tmp);
					sviPrijelazi.add(",");
				}
			}
			
			sviPrijelazi.set(sviPrijelazi.size() - 1, "|");
			trenutnaTrenutnaStanja.clear();
			trenutnaTrenutnaStanja.addAll(trenutnaSljedecaStanja);
			Collections.sort(trenutnaSljedecaStanja);
			
			for(String tmp: trenutnaSljedecaStanja) {
				sviPrijelazi.add(tmp);
				sviPrijelazi.add(",");
			}			
			sviPrijelazi.set(sviPrijelazi.size() - 1, "|");
			trenutnaSljedecaStanja.clear();
		}

		sviPrijelazi.remove(sviPrijelazi.size() - 1);
		return sviPrijelazi;
	}
}
