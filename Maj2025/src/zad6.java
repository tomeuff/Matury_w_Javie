import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

class Rekord
{
    public String data;
    public String nazwa_obszaru;
    public double masa;
    public double zawartosc;
    public double wydobycie;
    public double magazyn_przed_wydobyciem;
    public double magazyn_przed_wyslaniem;
    public double magazyn_po_wyslaniu;

    public Rekord(String data, String nazwa_obszaru, double masa, double zawartosc, double wydobycie, double magazyn_przed_wydobyciem, double magazyn_przed_wyslaniem, double magazyn_po_wyslaniu) {
        this.data = data;
        this.nazwa_obszaru = nazwa_obszaru;
        this.masa = masa;
        this.zawartosc = zawartosc;
        this.wydobycie = wydobycie;
        this.magazyn_przed_wydobyciem = magazyn_przed_wydobyciem;
        this.magazyn_przed_wyslaniem = magazyn_przed_wyslaniem;
        this.magazyn_po_wyslaniu = magazyn_po_wyslaniu;
    }
}

public class zad6 {
    public static void main(String[] args) throws IOException {
        ArrayList<Rekord> symulacja = new ArrayList<>();
        Scanner sc = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj2025\\Dane\\martianeum.txt"));
        sc.nextLine();
        while(sc.hasNextLine())
        {
            String[] wiersz = sc.nextLine().split("\t");
            symulacja.add(new Rekord(wiersz[0], wiersz[1], Float.parseFloat(wiersz[2].replace(',', '.')), Float.parseFloat(wiersz[3].replace(',', '.')), 0.0, 0.0, 0.0, 0.0));
        }

        BufferedWriter bw_6 = new BufferedWriter(new FileWriter("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj2025\\Wyniki\\wyniki6.txt"));

        //Zadanie 6.1.
        bw_6.write("6.1.\n");
        double laczna_masa_ladunkow = symulacja.stream().map(c -> c.masa).reduce(0.0, Double::sum);
        double laczna_masa_martianeum = symulacja.stream().filter(c -> c.zawartosc >= 1.0).map(c -> c.masa * c.zawartosc / 100.0).reduce(0.0, Double::sum);
        bw_6.write(String.format("%f kg\n%f kg\n\n", laczna_masa_ladunkow, laczna_masa_martianeum));

        //Zadanie 6.2.
        HashMap<String, Double> os = new HashMap<>();
        HashSet<String> obszary = symulacja.stream().map(c -> c.nazwa_obszaru).collect(Collectors.toCollection(HashSet::new));

        for(String o : obszary)
        {
            int ile = 0;
            double suma = 0.0;
            for(Rekord r : symulacja)
            {
                if(r.nazwa_obszaru.equals(o))
                {
                    ile++;
                    suma += r.masa;
                }
            }

            os.put(o, suma / (double)ile);
        }

        String min_obszar = Collections.min(os.entrySet(), Map.Entry.comparingByValue()).getKey();
        bw_6.write("6.2.\n");
        bw_6.write(String.format("%s\n\n", min_obszar));

        //Zadanie 6.3.
        String max_data = "";
        double max_suma = 0.0;
        for(int p = 0; p < symulacja.size() - 6; p += 7)
        {
            double suma = symulacja.subList(p, p + 7).stream().map(c -> c.masa).reduce(Double::sum).orElse(null);
            if(suma > max_suma)
            {
                max_data = symulacja.get(p).data;
                max_suma = suma;
            }
        }
        bw_6.write("6.3.\n");
        bw_6.write(String.format("%s\n%f\n\n", max_data, max_suma));

        //Zadanie 6.4.
        bw_6.write("6.4.\n");
        for(String o : obszary)
        {
            bw_6.write(o.replace(' ', '_') + " ");
            for(int rok = 2033; rok <= 2038; rok++)
            {
                int ile = 0;
                for(Rekord r : symulacja)
                {
                    if(r.nazwa_obszaru.equals(o) && Integer.parseInt(r.data.substring(0, 4)) == rok)
                    {
                        ile++;
                    }
                }
                bw_6.write(Integer.toString(ile) + " ");
            }
            bw_6.newLine();
        }
        bw_6.newLine();

        //Zadanie 6.5.
        for(int i = 0; i < symulacja.size(); i++)
        {
            Rekord r = symulacja.get(i);
            if(i == 0)
            {
                r.magazyn_przed_wydobyciem = 0;
            }
            else
            {
                r.magazyn_przed_wydobyciem = symulacja.get(i - 1).magazyn_po_wyslaniu;
            }

            if(r.zawartosc >= 1.0)
            {
                r.magazyn_przed_wyslaniem = r.magazyn_przed_wydobyciem + r.masa * r.zawartosc / 100.0;
            }
            else
            {
                r.magazyn_przed_wyslaniem = r.magazyn_przed_wydobyciem;
            }
            if(r.magazyn_przed_wyslaniem >= 100.0)
            {
                r.magazyn_po_wyslaniu = r.magazyn_przed_wyslaniem - 100.0;
            }
            else
            {
                r.magazyn_po_wyslaniu = r.magazyn_przed_wyslaniem;
            }
        }

        bw_6.write("6.5.\n");

        long ile_razy_na_orbite = symulacja.stream().filter(c -> c.magazyn_po_wyslaniu != c.magazyn_przed_wyslaniem).count();
        String pierwszy_transport = symulacja.stream().filter(c -> c.magazyn_po_wyslaniu != c.magazyn_przed_wyslaniem).findFirst().map(c -> c.data).orElse(null);

        String ostatni_transport = "";

        for(Rekord r : symulacja)
        {
            if(r.magazyn_przed_wyslaniem != r.magazyn_po_wyslaniu)
            {
                ostatni_transport = r.data;
            }
        }

        bw_6.write(String.format("%d\n%s\n%s\n", ile_razy_na_orbite, pierwszy_transport, ostatni_transport));


        bw_6.close();
    }
}
