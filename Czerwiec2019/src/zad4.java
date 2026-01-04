import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class zad4 {
    public static boolean czy_pierwsza(int liczba)
    {
        if(liczba == 1)
        {
            return false;
        }
        for(int d = 2; d <= Math.sqrt(liczba); d++)
        {
            if(liczba % d == 0)
            {
                return false;
            }
        }

        return true;
    }

    public static int suma_cyfr(Integer liczba)
    {
        return liczba.toString().chars().map(c -> c - '0').sum();
    }

    public static int waga(Integer liczba)
    {
        if(liczba < 10)
        {
            return liczba;
        }
        else
        {
            return waga(suma_cyfr(liczba));
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner sc_liczby = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Czerwiec2019\\Dane\\liczby.txt"));
        Scanner sc_pierwsze = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Czerwiec2019\\Dane\\pierwsze.txt"));
        BufferedWriter bw_4_1 = new BufferedWriter(new FileWriter("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Czerwiec2019\\Wyniki\\wyniki4_1.txt"));
        BufferedWriter bw_4_2 = new BufferedWriter(new FileWriter("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Czerwiec2019\\Wyniki\\wyniki4_2.txt"));
        BufferedWriter bw_4_3 = new BufferedWriter(new FileWriter("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Czerwiec2019\\Wyniki\\wyniki4_3.txt"));
        ArrayList<Integer> liczby = new ArrayList<>();
        ArrayList<Integer> pierwsze = new ArrayList<>();

        while(sc_liczby.hasNextInt())
        {
            liczby.add(sc_liczby.nextInt());
        }

        while(sc_pierwsze.hasNextInt())
        {
            pierwsze.add(sc_pierwsze.nextInt());
        }

        //Zadanie 4.1.
        for(Integer liczba : liczby)
        {
            if(czy_pierwsza(liczba) && liczba >= 100 && liczba <= 5000)
            {
                bw_4_1.write(String.format("%d\n", liczba));
            }
        }
        bw_4_1.close();

        //Zadanie 4.2.
        for(Integer pierwsza : pierwsze)
        {
            Integer pierwsza_rev = Integer.parseInt((new StringBuilder(pierwsza.toString())).reverse().toString());
            if(czy_pierwsza(pierwsza_rev))
            {
                bw_4_2.write(String.format("%d\n", pierwsza));
            }
        }
        bw_4_2.close();

        //Zadanie 4.3.
        bw_4_3.write(String.format("%d", (int)pierwsze.stream().filter(c -> waga(c) == 1).count()));
        bw_4_3.close();
    }
}
