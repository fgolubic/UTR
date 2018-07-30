package Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Parser {
	
	public static ArrayList<Character> ulaz =new ArrayList<Character>();
	public static ArrayList<Character> parsirano= new ArrayList<Character>();
	public static char neZnak1='A';
	public static char neZnak2='B';
	public static char neZnak3='C';
	public static char pocNeZnak='S';
	
	public static void main(String[] args) throws IOException{
		BufferedReader citac= new BufferedReader(new InputStreamReader(System.in));
		String linija;
		linija=citac.readLine();
		int jezik;
		
		
		for(int i=0; i<linija.length();i++){
			ulaz.add(linija.charAt(i));
		}
		
		
		jezik=parsiraj(pocNeZnak);
		
		ispisi(jezik);
		
	}
	
	
	
	private static int parsiraj(char znak){
		int provjera=0;
		
		
		if(znak==pocNeZnak) {
				parsirano.add(znak);
				if (ulaz.size() ==0) {
					return 0;
				} else {
				if (ulaz.get(0).equals('b')) {
					ulaz.remove(0);
					provjera = parsiraj(neZnak2);
					if (provjera==1) 
						provjera=parsiraj(neZnak1);			
					return provjera;
					}
				}
				if (ulaz.size() == 0) {
					return 0;
				} else {
				if (ulaz.get(0).equals('a')) {
					ulaz.remove(0);
					provjera=parsiraj(neZnak1);
					if (provjera==1) 
						provjera = parsiraj(neZnak2);
						
					}
				}
			}
			
		else if(znak==neZnak1) {
			parsirano.add(znak);
	
			if (ulaz.size() == 0) {
				return 0;
				} else {
				if (ulaz.get(0).equals('b')) {
					ulaz.remove(0);
					provjera=parsiraj(neZnak3);
					return  provjera;
					}
				}
				if (ulaz.size() == 0) {
					return 0;
				} else {
				if (ulaz.get(0).equals('a')) {
					ulaz.remove(0);
					provjera=1;
					}
				}
			}
			
		else if(znak==neZnak2) {
			parsirano.add(znak);
			if (ulaz.size() == 0) {
				provjera = 1;
				} else {
				provjera = 0;
				if (ulaz.get(0).equals('c')) {
					ulaz.remove(0);
					if (ulaz.get(0).equals('c') && ulaz.size() != 0) {
						ulaz.remove(0);
						int pom = parsiraj(pocNeZnak);
						if (ulaz.get(0).equals('b') && pom==1 && ulaz.size() != 0 ) {
							ulaz.remove(0);
							if (ulaz.get(0).equals('c') && ulaz.size() != 0  ) {
								ulaz.remove(0);
								provjera=1;

								} else {
								provjera=0;
								}


							} else {
							provjera=0;
							}


						} else {
						provjera=0;
						}


					} else {
					provjera=1;
				}
				}
			}
			
			
		else if(znak==neZnak3) {
			parsirano.add(znak);
			provjera = parsiraj(neZnak1);
			if (provjera==1)
				provjera = parsiraj(neZnak1);
		}
		
			
			return provjera;
	}
	
	
	
	
	private static void ispisi(int x){
		
		for(char temp: parsirano)
			System.out.print(temp);
		
		System.out.println();
		
		if(x==0 || (ulaz.size()!=0)){
			System.out.println("NE");
		}
		
		else{
			
			System.out.println("DA");
		}
		
	}
	
}
