import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class zad4 {
    public static int NWD_para(int a, int b)
    {
        while(a != 0 && b != 0)
        {
            if(a > b)
            {
                a = a % b;
            }
            else
            {
                b = b % a;
            }
        }
        if(a > 0)
        {
            return a;
        }
        else
        {
            return b;
        }
    }

    public static int NWD_tab(List<Integer> tab)
    {
        int nwd = tab.getFirst();
        for(Integer x : tab)
        {
            nwd = NWD_para(nwd, x);
        }
        return nwd;
    }

    public static int silnia(int n)
    {
        if(n == 0)
        {
            return 1;
        }
        else
        {
            return n * silnia(n - 1);
        }
    }
    public static void main(String[] args) throws IOException {
        Scanner sc_liczby = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj2019\\Dane\\liczby.txt"));
        ArrayList<Integer> dane = new ArrayList<>();

        while(sc_liczby.hasNextInt())
        {
            dane.add(sc_liczby.nextInt());
        }

        BufferedWriter bw_4 = new BufferedWriter(new FileWriter("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj2019\\Wyniki\\wyniki4.txt"));

        //Zadanie 4.1.
        bw_4.write("4.1.\n");
        int ile_pot_3 = 0;
        ArrayList<Integer> pot3 = new ArrayList<>();
        int liczba = 1;
        while(liczba <= 100000)
        {
            pot3.add(liczba);
            liczba *= 3;
        }

        for(Integer x : dane)
        {
            if(pot3.contains(x))
            {
                ile_pot_3++;
            }
        }
        bw_4.write(String.format("%d\n\n", ile_pot_3));

        //Zadanie 4.2.
        bw_4.write("4.2.\n");

        for(Integer x : dane)
        {
            String x_str = x.toString();
            int suma_silni = x_str.chars().map(c-> silnia(c - '0')).sum();
            if(suma_silni == x)
            {
                bw_4.write(String.format("%d\n", x));
            }
        }
        bw_4.newLine();

        //Zadanie 4.3.
        bw_4.write("4.3.\n");
        int max_dl = 0;
        int max_nwd = 1;
        int p_max = 0;
        int k_max = 0;
        for(int p = 0; p < dane.size(); p++)
        {
            for(int k = p; k < dane.size(); k++)
            {
                int nwd = NWD_tab(dane.subList(p, k + 1));
                if(nwd != 1)
                {
                    if(k - p + 1 > max_dl)
                    {
                        max_dl = k - p + 1;
                        max_nwd = nwd;
                        p_max = p;
                        k_max = k;
                    }
                }
            }
        }
        bw_4.write(String.format("pierwsza liczba: %d\ndługość ciągu: %d\nnwd: %d\n\n", dane.get(p_max), max_dl, max_nwd));

        bw_4.close();
    }
}
