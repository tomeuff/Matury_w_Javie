import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class zad4 {
    public static String szyfruj(String tj, String k)
    {
        int j = 0;
        StringBuilder wynik = new StringBuilder();
        for(int i = 0; i < tj.length(); i++)
        {
            int przesuniecie = (int) k.charAt(j % k.length()) - 64;
            int kod = (int) tj.charAt(i);
            int nowy_kod = kod + przesuniecie;
            if(nowy_kod > 90)
            {
                nowy_kod -= 26;
            }
            wynik.append((char) nowy_kod);
            j++;
        }
        return wynik.toString();
    }

    public static String deszyfruj(String tz, String k)
    {
        int j = 0;
        StringBuilder wynik = new StringBuilder();
        for(int i = 0; i < tz.length(); i++)
        {
            int przesuniecie = (int) k.charAt(j % k.length()) - 64;
            int kod = (int) tz.charAt(i);
            int nowy_kod = kod - przesuniecie;
            if(nowy_kod < 65)
            {
                nowy_kod += 26;
            }
            wynik.append((char) nowy_kod);
            j++;
        }
        return wynik.toString();
    }

    public static void main(String[] args) throws IOException {
        ArrayList<String> tj = new ArrayList<>();
        ArrayList<String> klucze1 = new ArrayList<>();
        ArrayList<String> sz = new ArrayList<>();
        ArrayList<String> klucze2 = new ArrayList<>();
        Scanner tj_sc = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj2012\\Dane\\tj.txt"));
        Scanner klucze1_sc = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj2012\\Dane\\klucze1.txt"));
        BufferedWriter bw_a = new BufferedWriter(new FileWriter("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj2012\\Wyniki\\wynik4a.txt"));
        while(tj_sc.hasNextLine())
        {
            tj.add(tj_sc.nextLine());
        }

        while(klucze1_sc.hasNextLine())
        {
            klucze1.add(klucze1_sc.nextLine());
        }

        Scanner sz_sc = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj2012\\Dane\\sz.txt"));
        Scanner klucze2_sc = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj2012\\Dane\\klucze2.txt"));
        BufferedWriter bw_b = new BufferedWriter(new FileWriter("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj2012\\Wyniki\\wynik4b.txt"));
        while(sz_sc.hasNextLine())
        {
            sz.add(sz_sc.nextLine());
        }

        while(klucze2_sc.hasNextLine())
        {
            klucze2.add(klucze2_sc.nextLine());
        }

        //Zadanie a)
        bw_a.write("a)\n");
        for(int i = 0; i < tj.size(); i++)
        {
            String tekst_jawny = tj.get(i);
            String klucz = klucze1.get(i);
            bw_a.write(String.format("%s\n",szyfruj(tekst_jawny, klucz)));
        }
        bw_a.newLine();
        bw_a.close();

        //Zadanie b)
        bw_b.write("b)\n");
        for(int i = 0; i < sz.size(); i++)
        {
            String szyfrogram = sz.get(i);
            String klucz = klucze2.get(i);
            bw_b.write(String.format("%s\n",deszyfruj(szyfrogram, klucz)));
        }
        bw_b.close();
    }
}
