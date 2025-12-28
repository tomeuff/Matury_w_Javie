import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class zad5 {
    public static void main(String[] args) throws IOException {
        ArrayList<ArrayList<Integer>> T = new ArrayList<>();
        BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj2012\\Wyniki\\wynik5.txt"));
        for(int i = 0; i < 30; i++)
        {
            T.add(new ArrayList<>());
            T.get(i).add(1);
            for(int j = 1; j < i; j++)
            {
                T.get(i).add(T.get(i - 1).get(j - 1) + T.get(i - 1).get(j));
            }
            if(i > 0)
            {
                T.get(i).add(1);
            }
        }

        //Zadanie a)
        bw.write("a)\n");
        for(int i = 9; i < 30; i += 10)
        {
            bw.write(String.format("%d\n", Collections.max(T.get(i))));
        }
        bw.newLine();

        //Zadanie b)
        bw.write("b)\n");
        for(int i = 0; i < 30; i++)
        {
            ArrayList<Integer> wiersz = T.get(i);
            int liczba_cyfr = wiersz.stream().map(c -> c.toString()).collect(Collectors.joining()).length();
            bw.write(String.format("%d : %d\n", i + 1, liczba_cyfr));
        }
        bw.newLine();

        //Zadanie c)
        bw.write("c)\n");
        for(int i = 0; i < 30; i++)
        {
            ArrayList<Integer> wiersz = T.get(i);
            int liczba_podzielnych_przez_5 = wiersz.stream().filter(c -> c % 5 == 0).toList().size();
            if(liczba_podzielnych_przez_5 == 0)
            {
                bw.write(String.format("%d\n", i + 1));
            }
        }
        bw.newLine();

        //Zadanie d)
        bw.write("d)\n");

        for(int i = 0; i < 30; i++)
        {
            for(int j = 0; j < T.get(i).size(); j++)
            {
                if(T.get(i).get(j) % 3 == 0)
                {
                    bw.write('X');
                }
                else
                {
                    bw.write(' ');
                }
            }
            bw.newLine();
        }

        bw.close();
    }
}
