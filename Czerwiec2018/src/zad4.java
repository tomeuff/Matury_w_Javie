import java.io.*;
import java.util.*;

public class zad4 {
    public static void main(String[] args) throws IOException {
        Scanner sc_d1 = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Czerwiec2018\\Dane\\dane1.txt"));
        Scanner sc_d2 = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Czerwiec2018\\Dane\\dane2.txt"));

        ArrayList<ArrayList<Integer>> dane1 = new ArrayList<>();
        ArrayList<ArrayList<Integer>> dane2 = new ArrayList<>();

        while(sc_d1.hasNextLine())
        {
            String[] wiersz = sc_d1.nextLine().split(" ");
            dane1.add(new ArrayList<>());
            for(int i = 0; i < 10; i++)
            {
                dane1.getLast().add(Integer.parseInt(wiersz[i]));
            }
        }

        while(sc_d2.hasNextLine())
        {
            String[] wiersz = sc_d2.nextLine().split(" ");
            dane2.add(new ArrayList<>());
            for(int i = 0; i < 10; i++)
            {
                dane2.getLast().add(Integer.parseInt(wiersz[i]));
            }
        }

        //Zadanie 4.1.
        BufferedWriter bw_4_1 = new BufferedWriter(new FileWriter("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Czerwiec2018\\Wyniki\\wynik4_1.txt"));
        int wynik4_1 = 0;
        for(int i = 0; i < 1000; i++)
        {
            if(Objects.equals(dane1.get(i).getLast(), dane2.get(i).getLast()))
            {
                wynik4_1++;
            }
        }
        bw_4_1.write(String.format("%d", wynik4_1));
        bw_4_1.close();

        //Zadanie 4.2.
        BufferedWriter bw_4_2 = new BufferedWriter(new FileWriter("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Czerwiec2018\\Wyniki\\wynik4_2.txt"));
        int wynik4_2 = 0;
        for(int i = 0; i < 1000; i++)
        {
            long ile_p_1 = dane1.get(i).stream().filter(c -> c % 2 == 0).count();
            long ile_p_2 = dane2.get(i).stream().filter(c -> c % 2 == 0).count();

            if(ile_p_1 == ile_p_2 && ile_p_1 == 5)
            {
                wynik4_2++;
            }
        }
        bw_4_2.write(String.format("%d", wynik4_2));
        bw_4_2.close();

        //Zadanie 4.3.
        BufferedWriter bw_4_3 = new BufferedWriter(new FileWriter("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Czerwiec2018\\Wyniki\\wynik4_3.txt"));
        int wynik4_3 = 0;
        bw_4_3.write("Numery wierszy:\n");
        for(int i = 0; i < 1000; i++)
        {
            HashSet<Integer> hs1 = new HashSet<>(dane1.get(i));
            HashSet<Integer> hs2 = new HashSet<>(dane2.get(i));

            if(hs1.equals(hs2))
            {
                wynik4_3++;
                bw_4_3.write(String.format("%d\n", i + 1));
            }
        }
        bw_4_3.write(String.format("Liczba par:\n%d", wynik4_3));
        bw_4_3.close();

        //Zadanie 4.4.
        BufferedWriter bw_4_4 = new BufferedWriter(new FileWriter("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Czerwiec2018\\Wyniki\\wynik4_4.txt"));
        for(int i = 0; i < 1000; i++)
        {
            ArrayList<Integer> scalone = new ArrayList<>();
            scalone.addAll(dane1.get(i));
            scalone.addAll(dane2.get(i));
            Collections.sort(scalone);
            for(int j = 0; j < scalone.size(); j++)
                bw_4_4.write(String.format("%d ", scalone.get(j)));
            bw_4_4.newLine();
        }
        bw_4_4.close();
    }
}
