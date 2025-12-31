import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

class Film
{
    public String ID_filmu;
    public String Tytul;
    public String Kraj_produkcji;
    public String Gatunek;
    public double Cena_w_zl;

    public Film(String ID_filmu, String tytul, String kraj_produkcji, String gatunek, double cena_w_zl) {
        this.ID_filmu = ID_filmu;
        Tytul = tytul;
        Kraj_produkcji = kraj_produkcji;
        Gatunek = gatunek;
        Cena_w_zl = cena_w_zl;
    }
}

class Klient
{
    public String pesel;
    public String Imie;
    public String Nazwisko;

    public Klient(String pesel, String imie, String nazwisko) {
        this.pesel = pesel;
        Imie = imie;
        Nazwisko = nazwisko;
    }
}

class Wypozyczenie
{
    public int ID_wyp;
    public LocalDate Data_wyp;
    public String ID_filmu;
    public String Pesel;

    public Wypozyczenie(int ID_wyp, LocalDate data_wyp, String ID_filmu, String pesel) {
        this.ID_wyp = ID_wyp;
        Data_wyp = data_wyp;
        this.ID_filmu = ID_filmu;
        Pesel = pesel;
    }
}

public class zad5 {
    public static void main(String[] args) throws IOException {
        Scanner sc_filmy = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj 2013\\Dane\\filmy.txt"));
        Scanner sc_klienci = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj 2013\\Dane\\klienci.txt"));
        Scanner sc_wypozyczenia = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj 2013\\Dane\\wypozyczenia.txt"));

        ArrayList<Film> filmy = new ArrayList<>();
        ArrayList<Klient> klienci = new ArrayList<>();
        ArrayList<Wypozyczenie> wypozyczenia = new ArrayList<>();

        BufferedWriter bw_5 = new BufferedWriter(new FileWriter("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj 2013\\Wyniki\\wyniki5.txt"));

        sc_filmy.nextLine();
        while(sc_filmy.hasNextLine())
        {
            String[] wiersz = sc_filmy.nextLine().split("\t");
            filmy.add(new Film(wiersz[0], wiersz[1], wiersz[2], wiersz[3], Double.parseDouble(wiersz[4])));
        }

        sc_klienci.nextLine();
        while(sc_klienci.hasNextLine())
        {
            String[] wiersz = sc_klienci.nextLine().split("\t");
            klienci.add(new Klient(wiersz[0], wiersz[1], wiersz[2]));
        }

        sc_wypozyczenia.nextLine();
        while(sc_wypozyczenia.hasNextLine())
        {
            String[] wiersz = sc_wypozyczenia.nextLine().split("\t");
            wypozyczenia.add(new Wypozyczenie(Integer.parseInt(wiersz[0]), LocalDate.parse(wiersz[1]), wiersz[2], wiersz[3]));
        }

        //Zadanie a)
        bw_5.write("a)\n");
        ArrayList<Film> filmy_familijne_z_2005 = filmy.stream().filter(c -> c.ID_filmu.endsWith("2005") && c.Gatunek.equals("familijny")).collect(Collectors.toCollection(ArrayList::new));
        filmy_familijne_z_2005.sort(Comparator.comparing(c -> c.Tytul));

        for(Film f : filmy_familijne_z_2005)
        {
            bw_5.write(String.format("%s %s\n", f.ID_filmu, f.Tytul));
        }

        bw_5.newLine();

        //Zadanie b)
        int max_l_zamowien = 0;
        String max_gatunek = "";
        HashSet<String> gatunki = filmy.stream().map(c -> c.Gatunek).collect(Collectors.toCollection(HashSet::new));

        for(String g : gatunki)
        {
            int ile = 0;
            for(Film f : filmy)
            {
                for(Wypozyczenie w : wypozyczenia)
                {
                    if(f.Gatunek.equals(g) && w.ID_filmu.equals(f.ID_filmu))
                    {
                        ile++;
                    }
                }
            }
            if(ile > max_l_zamowien)
            {
                max_gatunek = g;
                max_l_zamowien = ile;
            }
        }

        bw_5.write("b)\n");
        bw_5.write(String.format("%s %d\n\n", max_gatunek, max_l_zamowien));

        //Zadanie c)
        bw_5.write("c)\n");
        for(int m = 6; m <= 12; m++)
        {
            int ile = 0;
            for(Wypozyczenie w : wypozyczenia)
            {
                if(w.Data_wyp.getMonthValue() == m && w.Data_wyp.getYear() == 2011)
                {
                    ile++;
                }
            }
            bw_5.write(String.format("%d %d\n", m, ile));
        }
        bw_5.newLine();

        //Zadanie d)
        bw_5.write("d)\n");
        double max_kwota = 0.0;
        Klient max_klient = new Klient("", "", "");

        for(Klient k : klienci)
        {
            double kwota = 0.0;
            for(Wypozyczenie w : wypozyczenia)
            {
                for(Film f : filmy)
                {
                    if(w.ID_filmu.equals(f.ID_filmu) && w.Pesel.equals(k.pesel))
                    {
                        kwota += f.Cena_w_zl;
                    }
                }
            }
            if(kwota > max_kwota)
            {
                max_kwota = kwota;
                max_klient = k;
            }
        }

        bw_5.write(String.format("%s %s %f\n\n", max_klient.Imie, max_klient.Nazwisko, max_kwota));

        //Zadanie e)
        bw_5.write("e)\n");
        int ile = 0;
        for(Film f : filmy)
        {
            boolean czy_byl = false;
            for(Wypozyczenie w : wypozyczenia)
            {
                if(f.ID_filmu.equals(w.ID_filmu))
                {
                    czy_byl = true;
                    break;
                }
            }
            if(!czy_byl)
            {
                ile++;
                bw_5.write(String.format("%s\n", f.Tytul));
            }
        }
        bw_5.write(String.format("%d", ile));

        bw_5.close();
    }
}
