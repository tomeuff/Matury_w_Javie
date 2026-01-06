import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Collectors;

class Para
{
    public int liczba;
    public String slowo;

    public Para(int liczba, String slowo) {
        this.liczba = liczba;
        this.slowo = slowo;
    }
}
public class zad4 {
    public static boolean czy_pierwsza(int liczba)
    {
        if(liczba == 1)
        {
            return false;
        }
        else
        {
            for(int d = 2; d <= Math.sqrt(liczba); d++)
            {
                if(liczba % d == 0)
                {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        Scanner sc_pary = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Czerwiec2020\\Dane\\pary.txt"));
        BufferedWriter bw_4 = new BufferedWriter(new FileWriter("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Czerwiec2020\\Wyniki\\wyniki4.txt"));
        ArrayList<Para> pary = new ArrayList<>();

        while(sc_pary.hasNextLine())
        {
            String[] wiersz = sc_pary.nextLine().split(" ");
            pary.add(new Para(Integer.parseInt(wiersz[0]), wiersz[1]));
        }

        //Zadanie 4.1.
        bw_4.write("4.1.\n");

        for(Para p : pary)
        {
            int liczba = p.liczba;
            if(liczba % 2 == 0)
            {
                for(int a = 2; a <= liczba; a++)
                {
                    if(czy_pierwsza(a) && czy_pierwsza(liczba - a))
                    {
                        bw_4.write(String.format("%d %d %d\n", liczba, a, liczba - a));
                        break;
                    }
                }
            }
        }
        bw_4.newLine();

        //Zadanie 4.2.
        bw_4.write("4.2.\n");
        for(Para p : pary)
        {
            int dl_slowa = p.slowo.length();
            String slowo = p.slowo;
            String max_fragment = "";
            int pozycja = slowo.length();
            for(char z = 'a'; z <= 'z'; z++)
            {
                String fragment = "";
                for(int d = 1; d <= dl_slowa; d++)
                {
                    fragment += z;
                    if(slowo.contains(fragment) && fragment.length() > max_fragment.length())
                    {
                        max_fragment = fragment;
                        pozycja = slowo.indexOf(fragment);
                    }
                    else if(slowo.contains(fragment) && fragment.length() == max_fragment.length() && slowo.indexOf(fragment) < pozycja)
                    {
                        max_fragment = fragment;
                        pozycja = slowo.indexOf(fragment);
                    }
                }
            }
            bw_4.write(String.format("%s %d\n", max_fragment, max_fragment.length()));
        }
        bw_4.newLine();

        //Zadanie 4.3.
        bw_4.write("4.3.\n");
        ArrayList<Para> pary_dl_r_sl = pary.stream().filter(c -> c.liczba == c.slowo.length()).collect(Collectors.toCollection(ArrayList::new));
        pary_dl_r_sl.sort((Para p1, Para p2) -> {if(p1.liczba < p2.liczba) return -1; else if(p1.liczba > p2.liczba) return -1; else return p1.slowo.compareTo(p2.slowo);});
        bw_4.write(String.format("%d %s", pary_dl_r_sl.getFirst().liczba, pary_dl_r_sl.getFirst().slowo));

        bw_4.close();
    }
}
