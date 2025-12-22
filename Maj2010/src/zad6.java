import java.io.*;
import java.util.*;

class Uczen
{
    int idUcznia;
    String nazwisko;
    String imie;
    String ulica;
    String dom;
    String idKlasy;


    public Uczen(int idUcznia, String nazwisko, String imie, String ulica, String dom, String idKlasy) {
        this.idUcznia = idUcznia;
        this.nazwisko = nazwisko;
        this.imie = imie;
        this.ulica = ulica;
        this.dom = dom;
        this.idKlasy = idKlasy;
    }
}

class Ocena
{
    int idUcznia;
    int ocena;
    String data;
    int idPrzedmiotu;

    public Ocena(int idUcznia, int ocena, String data, int idPrzedmiotu) {
        this.idUcznia = idUcznia;
        this.ocena = ocena;
        this.data = data;
        this.idPrzedmiotu = idPrzedmiotu;
    }
}

class Przedmiot
{
    int idPrzedmiotu;
    String nazwaPrzedmiotu;
    String nazwisko_naucz;
    String imie_naucz;

    public Przedmiot(int idPrzedmiotu, String nazwaPrzedmiotu, String nazwisko_naucz, String imie_naucz) {
        this.idPrzedmiotu = idPrzedmiotu;
        this.nazwaPrzedmiotu = nazwaPrzedmiotu;
        this.nazwisko_naucz = nazwisko_naucz;
        this.imie_naucz = imie_naucz;
    }
}

class Para
{
    int lKobiet;
    int lMezczyzn;

    public Para(int lKobiet, int lMezczyzn) {
        this.lKobiet = lKobiet;
        this.lMezczyzn = lMezczyzn;
    }
}

class PrzedmiotSrednia
{
    String przedmiot;
    float srednia;

    public PrzedmiotSrednia(String przedmiot, float srednia) {
        this.przedmiot = przedmiot;
        this.srednia = srednia;
    }
}

public class zad6 {
    public static void main(String[] args) throws IOException {
        ArrayList<Uczen> uczniowie = new ArrayList<>();
        ArrayList<Ocena> oceny = new ArrayList<>();
        ArrayList<Przedmiot> przedmioty = new ArrayList<>();

        Scanner sc_uczniowie = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj2010\\Dane\\uczniowie.txt"));
        Scanner sc_oceny = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj2010\\Dane\\oceny.txt"));
        Scanner sc_przedmioty = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj2010\\Dane\\przedmioty.txt"));
        BufferedWriter bw_6 = new BufferedWriter(new FileWriter("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj2010\\wyniki\\odp_6.txt"));

        sc_uczniowie.nextLine();
        while(sc_uczniowie.hasNext())
        {
            String[] linia = sc_uczniowie.nextLine().split(";");
            uczniowie.add(new Uczen(Integer.parseInt(linia[0]), linia[1], linia[2], linia[3], linia[4], linia[5]));
        }

        sc_oceny.nextLine();
        while(sc_oceny.hasNext())
        {
            String[] linia = sc_oceny.nextLine().split(";");
            oceny.add(new Ocena(Integer.parseInt(linia[0]), Integer.parseInt(linia[1]), linia[2], Integer.parseInt(linia[3])));
        }

        sc_przedmioty.nextLine();
        while(sc_przedmioty.hasNext())
        {
            String[] linia = sc_przedmioty.nextLine().split(";");
            przedmioty.add(new Przedmiot(Integer.parseInt(linia[0]), linia[1], linia[2], linia[3]));
        }

        //Zadanie a)
        int ile_poza_rejonem = 0;

        for(Uczen u : uczniowie)
        {
            if(u.ulica.equals("Worcella") || u.ulica.equals("Sportowa"))
            {
                ile_poza_rejonem++;
            }
        }

        bw_6.write(String.format("a)\n%d\n\n", ile_poza_rejonem));

        //Zadanie b)
        bw_6.write("b)\n");
        int id_jana_augustyniaka = uczniowie.stream().filter(c -> c.imie.equals("Jan") && c.nazwisko.equals("Augustyniak")).map(c -> c.idUcznia).findFirst().orElse(null);
        int id_jezyka_polskiego = przedmioty.stream().filter(c -> c.nazwaPrzedmiotu.equals("polski")).map(c -> c.idPrzedmiotu).findFirst().orElse(null);
        int[] oceny_ja_jp = oceny.stream().filter(c -> c.idUcznia == id_jana_augustyniaka && c.idPrzedmiotu == id_jezyka_polskiego).mapToInt(c -> c.ocena).toArray();

        for(int o : oceny_ja_jp)
        {
            bw_6.write(String.format("%d\n", o));
        }

        bw_6.write("\n");

        //Zadanie c)
        bw_6.write("c)\n");

        TreeMap<String, Para> kkm = new TreeMap<>();

        for(Uczen u : uczniowie)
        {
            if(kkm.containsKey(u.idKlasy))
            {
                if(u.imie.endsWith("a"))
                {
                    kkm.put(u.idKlasy, new Para(kkm.get(u.idKlasy).lKobiet + 1, kkm.get(u.idKlasy).lMezczyzn));
                }
                else
                {
                    kkm.put(u.idKlasy, new Para(kkm.get(u.idKlasy).lKobiet, kkm.get(u.idKlasy).lMezczyzn + 1));
                }
            }
            else
            {
                if(u.imie.endsWith("a"))
                {
                    kkm.put(u.idKlasy, new Para(1, 0));
                }
                else
                {
                    kkm.put(u.idKlasy, new Para(0, 1));
                }
            }
        }

        for(Map.Entry<String, Para> p : kkm.entrySet())
        {
            bw_6.write(String.format("%s %d %d\n", p.getKey(), p.getValue().lKobiet, p.getValue().lMezczyzn));
        }

        bw_6.write("\n");

        //Zadanie d)
        bw_6.write("d)\n");

        ArrayList<PrzedmiotSrednia> ps = new ArrayList<>();
        for(Przedmiot p : przedmioty)
        {
            String przedmiot = p.nazwaPrzedmiotu;
            int liczba_ocen = 0;
            float suma_ocen = 0.0f;
            for(Uczen u : uczniowie)
            {
                if(u.idKlasy.equals("2a"))
                {
                    for(Ocena o : oceny)
                    {
                        if(o.idUcznia == u.idUcznia && o.idPrzedmiotu == p.idPrzedmiotu)
                        {
                            suma_ocen += o.ocena;
                            liczba_ocen++;
                        }
                    }
                }
            }
            ps.add(new PrzedmiotSrednia(przedmiot, Float.parseFloat(String.format("%.2f", suma_ocen / liczba_ocen).replace(',', '.'))));
        }

        ps.sort(Comparator.comparingDouble(c -> -c.srednia));

        for(PrzedmiotSrednia p : ps)
        {
            bw_6.write(String.format("%s %f\n", p.przedmiot, p.srednia));
        }

        bw_6.write("\n");

        //Zadanie e)
        bw_6.write("e)\n");

        for(Uczen u : uczniowie)
        {
            if(u.idKlasy.equals("2c"))
            {
                for(Przedmiot p : przedmioty)
                {
                    for(Ocena o : oceny)
                    {
                        if(u.idUcznia == o.idUcznia && p.idPrzedmiotu == o.idPrzedmiotu)
                        {
                            if(o.data.startsWith("2009-04") && o.ocena == 1)
                            {
                                bw_6.write(String.format("%s %s %s\n", u.imie, u.nazwisko, p.nazwaPrzedmiotu));
                            }
                        }
                    }
                }
            }
        }
        bw_6.write("\n");

        //Zadanie f)
        bw_6.write("f)\n");

        Float[] srednie = new Float[uczniowie.size()];

        for(Uczen u : uczniowie)
        {
            int liczba_ocen = 0;
            srednie[u.idUcznia - 1] = 0.0f;
            for(Ocena o : oceny)
            {
                if(u.idUcznia == o.idUcznia)
                {
                    liczba_ocen++;
                    srednie[u.idUcznia - 1] += (float)o.ocena;
                }
            }
            srednie[u.idUcznia - 1] = (float)srednie[u.idUcznia - 1] / (float)liczba_ocen;
        }

        float max_srednia = Collections.max(Arrays.asList(srednie));
        int ktory_uczen = Arrays.asList(srednie).indexOf(max_srednia);
        bw_6.write(String.format("%s %s %s %f\n", uczniowie.get(ktory_uczen).imie, uczniowie.get(ktory_uczen).nazwisko, uczniowie.get(ktory_uczen).idKlasy, max_srednia));

        bw_6.close();
    }
}
