import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Marka
{
    public String id_marki;
    public String nazwa_m;

    public Marka(String id_marki, String nazwa_m) {
        this.id_marki = id_marki;
        this.nazwa_m = nazwa_m;
    }
}

class Perfum
{
    public String id_perfum;
    public String nazwa_p;
    public String id_marki;
    public String rodzina_zapachow;
    public double cena;

    public Perfum(String id_perfum, String nazwa_p, String id_marki, String rodzina_zapachow, double cena) {
        this.id_perfum = id_perfum;
        this.nazwa_p = nazwa_p;
        this.id_marki = id_marki;
        this.rodzina_zapachow = rodzina_zapachow;
        this.cena = cena;
    }
}

class Sklad
{
    public String id_perfum;
    public String nazwa_skladnika;

    public Sklad(String id_perfum, String nazwa_skladnika) {
        this.id_perfum = id_perfum;
        this.nazwa_skladnika = nazwa_skladnika;
    }
}

public class zad6 {
    public static void main(String[] args) throws IOException {
        Scanner sc_marki = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj2019\\Dane\\marki.txt"));
        Scanner sc_perfumy = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj2019\\Dane\\perfumy.txt"));
        Scanner sc_sklad = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj2019\\Dane\\sklad.txt"));

        ArrayList<Marka> marki = new ArrayList<>();
        ArrayList<Perfum> perfumy = new ArrayList<>();
        ArrayList<Sklad> sklad = new ArrayList<>();

        BufferedWriter bw_6 = new BufferedWriter(new FileWriter("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj2019\\Wyniki\\wyniki6.txt"));

        sc_marki.nextLine();
        while(sc_marki.hasNextLine())
        {
            String[] wiersz = sc_marki.nextLine().split("\t");
            marki.add(new Marka(wiersz[0], wiersz[1]));
        }

        sc_perfumy.nextLine();
        while(sc_perfumy.hasNextLine())
        {
            String[] wiersz = sc_perfumy.nextLine().split("\t");
            perfumy.add(new Perfum(wiersz[0], wiersz[1], wiersz[2], wiersz[3], Double.parseDouble(wiersz[4])));
        }

        sc_sklad.nextLine();
        while(sc_sklad.hasNextLine())
        {
            String[] wiersz = sc_sklad.nextLine().split("\t");
            sklad.add(new Sklad(wiersz[0], wiersz[1]));
        }

        //Zadanie 6.1.
        bw_6.write("6.1.\n");
        for(Perfum p : perfumy)
        {
            for(Sklad s : sklad)
            {
                if(p.id_perfum.equals(s.id_perfum) && s.nazwa_skladnika.equals("absolut jasminu"))
                {
                    bw_6.write(String.format("%s\n", p.nazwa_p));
                    break;
                }
            }
        }
        bw_6.newLine();

        //Zadanie 6.2.
        HashSet<String> hs_zapachy = perfumy.stream().map(c -> c.rodzina_zapachow).collect(Collectors.toCollection(HashSet::new));

        for(String rz : hs_zapachy)
        {
            double min_cena = 10000000000000000.0;
            String min_perfum = "";

            for(Perfum p : perfumy)
            {
                if(rz.equals(p.rodzina_zapachow))
                {
                    if(p.cena < min_cena)
                    {
                        min_cena = p.cena;
                        min_perfum = p.nazwa_p;
                    }
                }
            }

            bw_6.write(String.format("%s %f %s\n", rz, min_cena, min_perfum));
        }

        bw_6.newLine();

        //Zadanie 6.3.
        bw_6.write("6.3.\n");
        marki.sort(Comparator.comparing(c -> c.nazwa_m));

        for(Marka m : marki)
        {
            boolean czy_bylo = false;
            for(Perfum p : perfumy)
            {
                for(Sklad s : sklad)
                {
                    if(p.id_marki.equals(m.id_marki) && p.id_perfum.equals(s.id_perfum))
                    {
                        if(s.nazwa_skladnika.contains("paczula"))
                        {
                            czy_bylo = true;
                        }
                    }
                }
            }
            if(!czy_bylo)
            {
                bw_6.write(String.format("%s\n", m.nazwa_m));
            }
        }

        bw_6.newLine();

        //Zadanie 6.4.
        bw_6.write("6.4.\n");
        String mdr = Objects.requireNonNull(marki.stream().filter(c -> c.nazwa_m.equals("Mou De Rosine")).findFirst().orElse(null)).id_marki;
        ArrayList<Perfum> p_obn = perfumy.stream().filter(c -> c.rodzina_zapachow.equals("orientalno-drzewna") && c.id_marki.equals(mdr)).collect(Collectors.toCollection(ArrayList::new));
        for(Perfum p : p_obn)
        {
            p.cena *= 0.85;
        }
       p_obn.sort(Comparator.comparing(c -> c.cena));

        for(Perfum p : p_obn)
        {
            bw_6.write(String.format("%s %f\n", p.nazwa_p, p.cena));
        }

        bw_6.newLine();

        //Zadanie 6.5.
        bw_6.write("6.5.\n");

        for(Marka m : marki)
        {
            HashSet<String> rodziny = new HashSet<>();
            String rodzina = "";
            for(Perfum p : perfumy)
            {
                if(p.id_marki.equals(m.id_marki))
                {
                    rodziny.add(p.rodzina_zapachow);
                    rodzina = p.rodzina_zapachow;
                }
            }

            if(rodziny.size() == 1)
            {
                bw_6.write(String.format("%s %s\n", m.nazwa_m, rodzina));
            }
        }

        bw_6.close();
    }
}
