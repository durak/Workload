package workloadstats.ui;

import java.util.Scanner;

/**
 *
 * @author Ilkka
 */
public class TextUi {
    
    private Scanner lukija;
    
    public TextUi() {
        lukija = new Scanner(System.in);        
    }
    
    public String mikaTamaOn(String kysymys) {
        System.out.print("Mikä tämä tapahtuma on: " + kysymys);
        System.out.println("");
        return lukija.nextLine();
    }
    
    public String mihinKurssiinTamaKuuluu(String kysymys) {
        System.out.print("Mihin kurssiin tämä tapahtuma kuuluu: " + kysymys);
        System.out.println("");
        return lukija.nextLine();
    }
    
    public String kysyKayttajaltaKurssilleNimi() {
        System.out.print("Anna kurssille nimi: ");
        System.out.println("");
        return lukija.nextLine();        
    }
}
