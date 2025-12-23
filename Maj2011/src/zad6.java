import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Collectors;

public class zad6 {
    public static void main(String[] args) throws IOException {
        ArrayList<String> dane = new ArrayList<>();
        Scanner sc = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj2011\\Dane\\liczby.txt"));
        BufferedWriter bw_6 = new BufferedWriter(new FileWriter("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj2011\\Wyniki\\zadanie6.txt"));

        while(sc.hasNextLine())
        {
            dane.add(sc.nextLine());
        }

        //Zadanie a)
        bw_6.write("a)\n");
        bw_6.write(Long.toString(dane.stream().filter(c -> c.charAt(c.length() - 1) == '0').count()));
        bw_6.write("\n\n");

        //Zadanie b)
        bw_6.write("b)\n");
        String max_liczba = dane.stream().max(Comparator.comparingInt(c -> Integer.parseInt(c, 2))).orElse(null);
        bw_6.write(String.format("%s\n%d\n\n", max_liczba, Integer.parseInt(max_liczba, 2)));

        //Zadanie c)
        bw_6.write("c)\n");
        ArrayList<String> liczby_9_cyfrowe = dane.stream().filter(c -> c.length() == 9).collect(Collectors.toCollection(ArrayList::new));
        int ile_9_cyfrowych = liczby_9_cyfrowe.size();
        String suma_9_cyfrowych = Integer.toBinaryString(liczby_9_cyfrowe.stream().mapToInt(c -> Integer.parseInt(c, 2)).sum());

        bw_6.write(String.format("%d\n%s", ile_9_cyfrowych, suma_9_cyfrowych));

        bw_6.close();
    }
}
