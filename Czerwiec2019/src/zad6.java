import java.io.*;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

class Agent
{
    public int Id_agenta;
    public String Imie;
    public String Nazwisko;

    public Agent(int id_agenta, String imie, String nazwisko) {
        Id_agenta = id_agenta;
        Imie = imie;
        Nazwisko = nazwisko;
    }
}

class Oferta
{
    public String Id_oferty;
    public String Woj;
    public String Status;
    public double Pow;
    public double L_pokoi;
    public double L_laz;
    public double Cena;
    public LocalDate Data_zglosz;
    public int Id_agenta;

    public Oferta(String id_oferty, String woj, String status, double pow, double l_pokoi, double l_laz, double cena, LocalDate data_zglosz, int id_agenta) {
        Id_oferty = id_oferty;
        Woj = woj;
        Status = status;
        Pow = pow;
        L_pokoi = l_pokoi;
        L_laz = l_laz;
        Cena = cena;
        Data_zglosz = data_zglosz;
        Id_agenta = id_agenta;
    }
}

class Klient
{
    public int Id_klienta;
    public String Imie;
    public String Nazwisko;

    public Klient(int id_klienta, String imie, String nazwisko) {
        Id_klienta = id_klienta;
        Imie = imie;
        Nazwisko = nazwisko;
    }
}

class Zainteresowanie
{
    public String Id_oferty;
    public int Id_klienta;

    public Zainteresowanie(String id_oferty, int id_klienta) {
        Id_oferty = id_oferty;
        Id_klienta = id_klienta;
    }
}

public class zad6 {
    public static void main(String[] args) throws IOException {
        Scanner sc_agenci = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Czerwiec2019\\Dane\\agenci.txt"));
        Scanner sc_oferty = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Czerwiec2019\\Dane\\oferty.txt"), java.nio.charset.StandardCharsets.UTF_16);
        Scanner sc_klienci = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Czerwiec2019\\Dane\\klienci.txt"));
        Scanner sc_zainteresowanie = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Czerwiec2019\\Dane\\zainteresowanie.txt"));

        BufferedWriter bw_6 = new BufferedWriter(new FileWriter("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Czerwiec2019\\Wyniki\\wyniki6.txt"));

        ArrayList<Agent> agenci = new ArrayList<>();
        ArrayList<Oferta> oferty = new ArrayList<>();
        ArrayList<Klient> klienci = new ArrayList<>();
        ArrayList<Zainteresowanie> zainteresowanie = new ArrayList<>();

        sc_agenci.nextLine();
        while(sc_agenci.hasNextLine())
        {
            String[] wiersz = sc_agenci.nextLine().split("\t");
            agenci.add(new Agent(Integer.parseInt(wiersz[0]), wiersz[1], wiersz[2]));
        }

        sc_oferty.nextLine();
        while(sc_oferty.hasNextLine())
        {
            String[] wiersz = sc_oferty.nextLine().split("\t");
            oferty.add(new Oferta(wiersz[0], wiersz[1], wiersz[2], Double.parseDouble(wiersz[3]), Double.parseDouble(wiersz[4]), Double.parseDouble(wiersz[5]), Double.parseDouble(wiersz[6]), LocalDate.parse(wiersz[7]), Integer.parseInt(wiersz[8])));
        }

        sc_klienci.nextLine();
        while(sc_klienci.hasNextLine())
        {
            String[] wiersz = sc_klienci.nextLine().split("\t");
            klienci.add(new Klient(Integer.parseInt(wiersz[0]), wiersz[1], wiersz[2]));
        }

        sc_zainteresowanie.nextLine();
        while(sc_zainteresowanie.hasNextLine())
        {
            String[] wiersz = sc_zainteresowanie.nextLine().split("\t");
            zainteresowanie.add(new Zainteresowanie(wiersz[0], Integer.parseInt(wiersz[1])));
        }

        //Zadanie 6.1.
        bw_6.write("6.1.\n");

        String max_oferta = zainteresowanie.stream().max(Comparator.comparingInt(c -> Math.toIntExact(zainteresowanie.stream().filter(d -> d.Id_oferty.equals(c.Id_oferty)).count()))).get().Id_oferty;
        int id_agenta = oferty.stream().filter(c -> c.Id_oferty.equals(max_oferta)).findFirst().get().Id_agenta;
        Agent max_agent = agenci.stream().filter(c -> c.Id_agenta == id_agenta).findFirst().get();
        bw_6.write(String.format("%s %s %s\n\n", max_oferta, max_agent.Imie, max_agent.Nazwisko));

        //Zadanie 6.2.
        bw_6.write("6.2.\n");
        Object TreeSetSet;
        TreeSet<String> wojewodztwa = oferty.stream().map(c -> c.Woj).collect(Collectors.toCollection(TreeSet::new));
        for(String w : wojewodztwa)
        {
            double suma = 0.0;
            int ile = 0;
            for(Oferta o : oferty)
            {
                if(o.Woj.equals(w))
                {
                    ile++;
                    suma += o.Cena;
                }
            }
            bw_6.write(String.format("%s %.2f\n", w, suma / ile));
        }
        bw_6.newLine();

        //Zadanie 6.3.
        bw_6.write("6.3.\n");

        for(Agent a : agenci)
        {
            for(Oferta o : oferty)
            {
                if(o.Id_agenta == a.Id_agenta)
                {
                    if(o.Status.equals("A") && o.Id_oferty.endsWith("MT"))
                    {
                        bw_6.write(String.format("%s %s %s %s %f %f\n", a.Imie, a.Nazwisko, o.Id_oferty, o.Woj, o.Pow, o.Cena));
                    }
                }
            }
        }
        bw_6.newLine();

        //Zadanie 6.4.
        bw_6.write("6.4.\n");
        for(Agent a : agenci)
        {
            int ile = 0;
            for(Oferta o : oferty)
            {
                if(o.Id_agenta == a.Id_agenta)
                {
                    if(o.Data_zglosz.getYear() == 2017)
                    {
                        if(o.Status.equals("S"))
                        {
                            ile++;
                            break;
                        }
                    }
                }
            }
            if(ile == 0)
            {
                bw_6.write(String.format("%s %s\n", a.Imie, a.Nazwisko));
            }
        }
        bw_6.newLine();

        //Zadanie 6.5.
        bw_6.write("6.5.\n");
        for(Oferta o : oferty)
        {
            if(o.Status.equals("A") && o.Pow > 180 && o.L_laz >= 2)
            {
                Agent agent = agenci.stream().filter(c -> c.Id_agenta == o.Id_agenta).findFirst().get();
                bw_6.write(String.format("%s %f %f %f %f %s %s\n", o.Id_oferty, o.Pow, o.L_pokoi, o.L_laz, o.Cena, agent.Imie, agent.Nazwisko));
            }
        }

        bw_6.close();
    }
}
