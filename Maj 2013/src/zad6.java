import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class zad6 {
    public static void main(String[] args) throws IOException {
        Scanner sc_dane = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj 2013\\Dane\\dane.txt"));
        BufferedWriter bw_6 = new BufferedWriter(new FileWriter("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj 2013\\Wyniki\\wyniki6.txt"));
        ArrayList<String> dane = new ArrayList<>();

        while(sc_dane.hasNextLine())
        {
            dane.add(sc_dane.nextLine());
        }

        //Zadanie a)
        bw_6.write("a)\n");
        long ile_p_r_o = dane.stream().filter(c -> c.charAt(0) == c.charAt(c.length() - 1)).count();
        bw_6.write(String.format("%d\n\n", ile_p_r_o));

        //Zadanie b)
        bw_6.write("b)\n");
        long ile_p_r_o_10 = dane.stream().map(c-> Integer.toString(Integer.parseInt(c, 8))).filter(c -> c.charAt(0) == c.charAt(c.length() - 1)).count();
        bw_6.write(String.format("%d\n\n", ile_p_r_o_10));

        //Zadanie c)
        bw_6.write("c)\n");
        ArrayList<String> rosnace = new ArrayList<>();

        for(String n: dane)
        {
            char[] tab = n.toCharArray();
            Arrays.sort(tab);
            String n_sort = new String(tab);
            if(n.equals(n_sort))
            {
                rosnace.add(n);
            }
        }
        rosnace.sort(Comparator.comparing(c -> Integer.parseInt(c, 8)));

        bw_6.write(String.format("%d\n%s\n%s", rosnace.size(), rosnace.getFirst(), rosnace.getLast()));

        bw_6.close();
    }
}
