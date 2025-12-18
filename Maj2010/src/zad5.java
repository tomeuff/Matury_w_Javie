import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class zad5 {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(new File("Maj2010/Dane/pesel.txt"));
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
        System.out.println(ile_grudzien);

        //Zadanie b)
        int ile_kobiet = 0;
        for(String p : dane)
        {
            if(Integer.parseInt(p.substring(9, 10)) % 2 == 0)
            {
                ile_kobiet++;
            }
        }
        System.out.println(ile_kobiet);

        //Zadanie c)
        HashMap<String, Integer> hm = new HashMap<>();
        for(String p : dane)
        {
            String rok = p.substring(0, 2);
            if(hm.containsKey(rok))
            {
                hm.put(rok, hm.get(rok) + 1);
            }
            else
            {
                hm.put(rok, 1);
            }
        }
        String max_rok = hm.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(null);
        System.out.println("19" + max_rok);

        //Zadanie d)

    }
}
