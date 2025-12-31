import java.io.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

class Lazik
{
    public int nr_lazika;

    public Lazik(int nr_lazika, String nazwa_lazika, int rok_wyslania, String wsp_ladowania) {
        this.nr_lazika = nr_lazika;
        this.nazwa_lazika = nazwa_lazika;
        this.rok_wyslania = rok_wyslania;
        this.wsp_ladowania = wsp_ladowania;
    }

    public String nazwa_lazika;
    public int rok_wyslania;
    public String wsp_ladowania;
}

class Obszar
{
    public Obszar(String kod_obszaru, String nazwa_obszaru) {
        this.kod_obszaru = kod_obszaru;
        this.nazwa_obszaru = nazwa_obszaru;
    }

    public String kod_obszaru;
    public String nazwa_obszaru;
}

class Pomiar
{
    public int nr_lazika;
    public LocalDate data_pomiaru;

    public Pomiar(int nr_lazika, LocalDate data_pomiaru, String kod_obszaru, String wspolrzedne, double glebokosc, double ilosc) {
        this.nr_lazika = nr_lazika;
        this.data_pomiaru = data_pomiaru;
        this.kod_obszaru = kod_obszaru;
        this.wspolrzedne = wspolrzedne;
        this.glebokosc = glebokosc;
        this.ilosc = ilosc;
    }

    public String kod_obszaru;
    public String wspolrzedne;
    public double glebokosc;
    public double ilosc;
}

public class zad7 {
    public static void main(String[] args) throws IOException {
        Scanner sc_laziki = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj2025\\Dane\\laziki.txt"));
        Scanner sc_obszary = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj2025\\Dane\\obszary.txt"));
        Scanner sc_pomiary = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj2025\\Dane\\pomiary.txt"));

        ArrayList<Lazik> laziki = new ArrayList<>();
        ArrayList<Obszar> obszary = new ArrayList<>();
        ArrayList<Pomiar> pomiary = new ArrayList<>();

        BufferedWriter bw_7 = new BufferedWriter(new FileWriter("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj2025\\Wyniki\\wyniki7.txt"));

        sc_laziki.nextLine();
        while(sc_laziki.hasNextLine())
        {
            String[] wiersz = sc_laziki.nextLine().split("\t");
            laziki.add(new Lazik(Integer.parseInt(wiersz[0]), wiersz[1], Integer.parseInt(wiersz[2]), wiersz[3]));
        }

        sc_obszary.nextLine();
        while(sc_obszary.hasNextLine())
        {
            String[] wiersz = sc_obszary.nextLine().split("\t");
            obszary.add(new Obszar(wiersz[0], wiersz[1]));
        }

        sc_pomiary.nextLine();
        while(sc_pomiary.hasNextLine())
        {
            String[] wiersz = sc_pomiary.nextLine().split("\t");
            pomiary.add(new Pomiar(Integer.parseInt(wiersz[0]), LocalDate.parse(wiersz[1]), wiersz[2], wiersz[3], Double.parseDouble(wiersz[4]), Double.parseDouble(wiersz[5])));
        }

        //Zadanie 7.1.
        String max_obszar = "";
        double max_ilosc = 0.0;

        for(Obszar o : obszary)
        {
            double ilosc = pomiary.stream().filter(c -> c.kod_obszaru.equals(o.kod_obszaru) && c.glebokosc <= 100.0).map(c -> c.ilosc).reduce(0.0, Double::sum);
            if(ilosc > max_ilosc)
            {
                max_obszar = o.nazwa_obszaru;
                max_ilosc = ilosc;
            }
        }

        bw_7.write("7.1.\n");
        bw_7.write(String.format("%s\n\n", max_obszar));

        //Zadanie 7.2.
        String max_lazik = "";
        LocalDate max_start = LocalDate.of(2020, 1, 1);
        LocalDate max_koniec = LocalDate.of(2020, 1, 1);
        long max_dni = 0;
        for(Lazik l : laziki)
        {
            LocalDate koniec = Collections.max(pomiary.stream().filter(c -> c.nr_lazika == l.nr_lazika).collect(Collectors.toCollection(ArrayList::new)), Comparator.comparing(c -> c.data_pomiaru)).data_pomiaru;
            LocalDate start = Collections.min(pomiary.stream().filter(c -> c.nr_lazika == l.nr_lazika).collect(Collectors.toCollection(ArrayList::new)), Comparator.comparing(c -> c.data_pomiaru)).data_pomiaru;

            long czas = ChronoUnit.DAYS.between(start, koniec);

            if(czas > max_dni)
            {
                max_dni = czas;
                max_start = start;
                max_koniec = koniec;
                max_lazik = l.nazwa_lazika;
            }

        }

        bw_7.write("7.2.\n");
        bw_7.write(String.format("%s %s %s\n\n", max_lazik, max_start.toString(), max_koniec.toString()));

        //Zadanie 7.3.
        bw_7.write("7.3.\n");
        for(Obszar o : obszary)
        {
            int ile_bl = 0;
            for(Lazik l : laziki)
            {
                for(Pomiar p : pomiary)
                {
                    if(l.rok_wyslania == p.data_pomiaru.getYear() && l.nr_lazika == p.nr_lazika && Objects.equals(o.kod_obszaru, p.kod_obszaru))
                    {
                        ile_bl++;
                    }
                }
            }
            if(ile_bl == 0)
            {
                bw_7.write(String.format("%s\n", o.nazwa_obszaru));
            }
        }

        bw_7.newLine();

        //Zadanie 7.4.
        bw_7.write("7.4.\n");

        for(Lazik l : laziki)
        {
            int n = 0;
            int s = 0;
            if(l.wsp_ladowania.contains("S"))
            {
                for(Pomiar p : pomiary)
                {
                    if(p.nr_lazika == l.nr_lazika && p.wspolrzedne.contains("N"))
                    {
                        n++;
                    }
                    if(p.nr_lazika == l.nr_lazika && p.wspolrzedne.contains("S"))
                    {
                        s++;
                    }
                }

                if(n > 0 && s > 0)
                {
                    bw_7.write(String.format("%s\n", l.nazwa_lazika));
                }
            }
        }

        bw_7.close();
    }
}
