import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class zad4 {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(new File("C:\\Users\\TK\\IdeaProjects\\Matury_w_Javie\\Maj2010\\Dane\\anagram.txt"));
        BufferedWriter bw_a = new BufferedWriter(new FileWriter("C:\\Users\\TK\\IdeaProjects\\Matury_w_Javie\\Maj2010\\wyniki\\odp_4a.txt"));
        BufferedWriter bw_b = new BufferedWriter(new FileWriter("C:\\Users\\TK\\IdeaProjects\\Matury_w_Javie\\Maj2010\\wyniki\\odp_4b.txt"));
        ArrayList<ArrayList<String>> dane = new ArrayList<>();
        while(sc.hasNextLine())
        {
            String linia = sc.nextLine();
            dane.add(new ArrayList<>(Arrays.asList(linia.split(" "))));
        }

        //Zadanie a)
        for(ArrayList<String> linia : dane)
        {
            int ile = 0;
            int dl = linia.get(0).length();
            for(String s : linia)
            {
                if(s.length() == dl)
                {
                    ile++;
                }
            }
            if(ile == 5)
            {
                bw_a.write(String.join(" ", linia));
                bw_a.newLine();
            }
        }
        bw_a.close();

        //Zadanie b)
        for(ArrayList<String> linia : dane)
        {
            int ile = 0;
            String pierwsze = linia.get(0).chars().sorted().mapToObj(c -> String.valueOf((char) c)).collect(Collectors.joining());
            for(String s : linia)
            {
                String slowo = s.chars().sorted().mapToObj(c -> String.valueOf((char) c)).collect(Collectors.joining());
                if(slowo.equals(pierwsze))
                {
                    ile++;
                }
            }
            if(ile == 5)
            {
                bw_b.write(String.join(" ", linia));
                bw_b.newLine();
            }
        }
        bw_b.close();
    }
}
