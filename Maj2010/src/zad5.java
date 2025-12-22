import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class zad5 {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(new File("Maj2010/Dane/pesel.txt"));
        BufferedWriter bw_5 = new BufferedWriter(new FileWriter("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj2010\\wyniki\\odp_5.txt"));
        ArrayList<String> dane = new ArrayList<>();
        while(sc.hasNextLine())
        {
            dane.add(sc.nextLine());
        }

        //Zadanie a)
        int ile_grudzien = 0;
        for(String p : dane)
        {
            if(p.startsWith("12", 2))
            {
                ile_grudzien++;
            }
        }
        bw_5.write("a)\n");
        bw_5.write(String.format("%d\n\n", ile_grudzien));

        //Zadanie b)
        int ile_kobiet = 0;
        for(String p : dane)
        {
            if(Integer.parseInt(p.substring(9, 10)) % 2 == 0)
            {
                ile_kobiet++;
            }
        }
        bw_5.write("b)\n");
        bw_5.write(String.format("%d\n\n", ile_kobiet));

        //Zadanie c)
        HashMap<String, Integer> rok_urodzenia = new HashMap<>();
        for(String p : dane)
        {
            String rok = p.substring(0, 2);
            if(rok_urodzenia.containsKey(rok))
            {
                rok_urodzenia.put(rok, rok_urodzenia.get(rok) + 1);
            }
            else
            {
                rok_urodzenia.put(rok, 1);
            }
        }
        String max_rok = rok_urodzenia.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(null);
        bw_5.write("c)\n");
        bw_5.write(String.format("%s\n\n", "19" + max_rok));

        //Zadanie d)
        int[] wagi = {1, 3, 7, 9, 1, 3, 7, 9, 1, 3};
        ArrayList<String> bledne_pesele = new ArrayList<>();
        for(String p : dane)
        {
            int[] pesel = p.chars().map(c -> c - '0').toArray();
            int cyfra_kontrolna = IntStream.range(0, wagi.length).map(i-> wagi[i] * pesel[i]).sum() % 10;
            cyfra_kontrolna = (cyfra_kontrolna == 0) ? 0 : 10 - cyfra_kontrolna;
            if(cyfra_kontrolna != pesel[pesel.length - 1])
            {
                bledne_pesele.add(Arrays.stream(pesel).mapToObj(String::valueOf).collect(Collectors.joining()));
            }
        }
        Collections.sort(bledne_pesele);

        bw_5.write("d)\n");

        for(String bp : bledne_pesele)
        {
            bw_5.write(String.format("%s\n", bp));
        }
        bw_5.write("\n");

        //Zadanie e)
        Map<Integer, Integer> dziesieciolecie_urodzenia = new TreeMap<>();
        for(String p : dane)
        {
            int dziesieciolecie = ((int)p.charAt(0) - '0') * 10;
            if(dziesieciolecie_urodzenia.containsKey(dziesieciolecie))
            {
                dziesieciolecie_urodzenia.put(dziesieciolecie, dziesieciolecie_urodzenia.get(dziesieciolecie) + 1);
            }
            else
            {
                dziesieciolecie_urodzenia.put(dziesieciolecie, 1);
            }
        }

        bw_5.write("e)\n");

        for(Map.Entry<Integer, Integer> kv : dziesieciolecie_urodzenia.entrySet())
        {
            int dziesieciolecie = kv.getKey();
            int urodzeni = kv.getValue();
            bw_5.write(String.format("%d %d\n", dziesieciolecie, urodzeni));
        }

        bw_5.close();
    }
}
