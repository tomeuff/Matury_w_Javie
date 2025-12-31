import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class zad3 {
    public static int nwd(int a, int b)
    {
        if(b == 0)
        {
            return a;
        }

        while(a != b)
        {
            if(a > b)
            {
                a -= b;
            }
            else
            {
                b -= a;
            }
        }
        return a;
    }

    public static void main(String[] args) throws IOException {
        ArrayList<Para> dane = new ArrayList<>();
        Scanner sc = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj2025\\Dane\\dron.txt"));
        BufferedWriter bw_3 = new BufferedWriter(new FileWriter("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj2025\\Wyniki\\wyniki3.txt"));

        while(sc.hasNextLine())
        {
            String[] wiersz = sc.nextLine().split(" ");
            dane.add(new Para(Integer.parseInt(wiersz[0]), Integer.parseInt(wiersz[1])));
        }

        //Zadanie 3.1.
        long liczba_par_nwd_ponad_1 = dane.stream().filter(c -> nwd(Math.abs(c.a), Math.abs(c.b)) > 1).count();
        bw_3.write("3.1.\n");
        bw_3.write(String.format("%d\n\n", liczba_par_nwd_ponad_1));

        //Zadanie 3.2.
        ArrayList<Para> punkty = new ArrayList<>();
        bw_3.write("3.2.\n");
        punkty.add(new Para(dane.getFirst().a, dane.getFirst().b));

        for(Para p : dane.subList(1, dane.size()))
        {
            punkty.add(new Para(punkty.getLast().a + p.a, punkty.getLast().b + p.b));
        }

        long ile_wew_kw = punkty.stream().filter(c -> c.a > 0 && c.a < 5000 && c.b > 0 && c.b < 5000).count();
        bw_3.write(String.format("a) %d\n", ile_wew_kw));
        for(int i = 0; i < punkty.size(); i++)
        {
            for(int j = i + 1; j < punkty.size(); j++)
            {
                for(int k = j + 1; k < punkty.size(); k++)
                {
                    Para A = new Para(punkty.get(i).a, punkty.get(i).b);
                    Para B = new Para(punkty.get(j).a, punkty.get(j).b);
                    Para C = new Para(punkty.get(k).a, punkty.get(k).b);

                    if((B.a + C.a == 2 * A.a && B.b + C.b == 2 * A.b) ||
                            (A.a + C.a == 2 * B.a && A.b + C.b == 2 * B.b) ||
                            (A.a + B.a == 2 * C.a && A.b + B.b == 2 * C.b))
                    {
                        bw_3.write("b) ");
                        bw_3.write(String.format("(%d, %d), ", A.a, A.b));
                        bw_3.write(String.format("(%d, %d), ", B.a, B.b));
                        bw_3.write(String.format("(%d, %d)\n", C.a, C.b));
                        break;
                    }
                }
            }
        }

        bw_3.close();
    }
}
