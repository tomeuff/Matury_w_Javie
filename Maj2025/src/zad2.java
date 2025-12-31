import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Para
{
    int a;
    int b;

    public Para(int a, int b) {
        this.a = a;
        this.b = b;
    }
}

public class zad2 {
    public static String na_trojkowy(String napis)
    {
        StringBuilder wynik = new StringBuilder();

        for(int i = 0; i < napis.length(); i++)
        {
            char znak = napis.charAt(i);
            if(znak == 'o')
            {
                wynik.append('0');
            }
            if(znak == '+')
            {
                wynik.append('1');
            }
            if(znak == '*')
            {
                wynik.append('2');
            }
        }
        return wynik.toString();
    }

    public static String na_symbole(int liczba)
    {
        StringBuilder wynik = new StringBuilder();

        while(liczba > 0)
        {
            int reszta = liczba % 3;
            liczba /= 3;
            if(reszta == 0)
            {
                wynik.append('o');
            }
            if(reszta == 1)
            {
                wynik.append('+');
            }
            if(reszta == 2)
            {
                wynik.append('*');
            }
        }
        return wynik.reverse().toString();
    }

    public static void main(String[] args) throws IOException {
        Scanner sc_symbole = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj2025\\Dane\\symbole.txt"));
        ArrayList<String> dane = new ArrayList<>();
        BufferedWriter bw_2 = new BufferedWriter(new FileWriter("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj2025\\Wyniki\\wyniki2.txt"));

        while(sc_symbole.hasNextLine())
        {
            dane.add(sc_symbole.nextLine());
        }

        //Zadanie 2.1.
        bw_2.write("2.1.\n");
        ArrayList<String> palindromy = dane.stream().filter(c -> (new StringBuilder(c)).reverse().toString().equals(c)).collect(Collectors.toCollection(ArrayList::new));
        for(String p : palindromy)
        {
            bw_2.write(p);
            bw_2.newLine();
        }
        bw_2.newLine();

        //Zadanie 2.2.
        bw_2.write("2.2.\n");
        int ile_kw = 0;
        ArrayList<Para> pozycje = new ArrayList<>();

        for(int i = 1; i < dane.size() - 1; i++)
        {
            for(int j = 1; j < dane.getFirst().length() - 1; j++)
            {
                char symbol = dane.get(i).charAt(j);
                int ile_ok = 0;
                for(int w = i - 1; w <= i + 1; w++)
                {
                    for(int k = j - 1; k <= j + 1; k++)
                    {
                        if(dane.get(w).charAt(k) == symbol)
                        {
                            ile_ok++;
                        }
                    }
                }
                if(ile_ok == 9)
                {
                    ile_kw++;
                    pozycje.add(new Para(i + 1, j + 1));
                }
            }
        }

        bw_2.write(String.format("%d ", ile_kw));
        for(Para p : pozycje)
        {
            bw_2.write(String.format("%d %d ", p.a, p.b));
        }
        bw_2.newLine();
        bw_2.newLine();

        //Zadanie 2.3.
        String max_napis = Collections.max(dane, Comparator.comparing(c -> Integer.parseInt(na_trojkowy(c), 3)));
        bw_2.write("2.3.\n");
        bw_2.write(String.format("%d %s\n\n", Integer.parseInt(na_trojkowy(max_napis), 3), max_napis));

        //Zadanie 2.4.
        bw_2.write("2.4.\n");
        int suma_10 = dane.stream().map(c -> Integer.parseInt(na_trojkowy(c), 3)).reduce(0, Integer::sum);
        bw_2.write(String.format("%d %s", suma_10, na_symbole(suma_10)));
        bw_2.close();
    }
}
