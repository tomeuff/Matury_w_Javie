import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Tablica
{
    public String ozn;
    public String powiat;
    public String siedziba;
    public String typ;

    public Tablica(String ozn, String powiat, String siedziba, String typ) {
        this.ozn = ozn;
        this.powiat = powiat;
        this.siedziba = siedziba;
        this.typ = typ;
    }
}

class Usluga
{
    public String NIP;
    public String ozn;
    public String nr;
    public String rodzaj_uslugi;
    public float rata;

    public Usluga(String NIP, String ozn, String nr, String rodzaj_uslugi, float rata) {
        this.NIP = NIP;
        this.ozn = ozn;
        this.nr = nr;
        this.rodzaj_uslugi = rodzaj_uslugi;
        this.rata = rata;
    }
}

class Nip_firmy
{
    public String NIP;
    public String firma;

    public Nip_firmy(String NIP, String firma) {
        this.NIP = NIP;
        this.firma = firma;
    }
}

class Para
{
    public String a;
    public String b;

    public Para(String a, String b) {
        this.a = a;
        this.b = b;
    }
}

public class zad6 {
    public static void main(String[] args) throws IOException {
        Scanner sc_tablice = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj2012\\Dane\\tablice.txt"));
        Scanner sc_uslugi = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj2012\\Dane\\uslugi.txt"));
        Scanner sc_nip_firm = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj2012\\Dane\\nip_firm.txt"));

        ArrayList<Tablica> tablice = new ArrayList<>();
        ArrayList<Usluga> uslugi = new ArrayList<>();
        ArrayList<Nip_firmy> nip_firm = new ArrayList<>();

        sc_tablice.nextLine();
        while(sc_tablice.hasNextLine())
        {
            String[] wiersz = sc_tablice.nextLine().split("\t");
            tablice.add(new Tablica(wiersz[0], wiersz[1], wiersz[2], wiersz[3]));
        }

        sc_uslugi.nextLine();
        while(sc_uslugi.hasNextLine())
        {
            String[] wiersz = sc_uslugi.nextLine().split("\t");
            uslugi.add(new Usluga(wiersz[0], wiersz[1], wiersz[2], wiersz[3], Float.parseFloat(wiersz[4])));
        }

        sc_nip_firm.nextLine();
        while(sc_nip_firm.hasNextLine())
        {
            String[] wiersz = sc_nip_firm.nextLine().split("\t");
            nip_firm.add(new Nip_firmy(wiersz[0], wiersz[1]));
        }

        BufferedWriter bw_6 = new BufferedWriter(new FileWriter("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj2012\\Wyniki\\wynik6.txt"));

        //Zadanie a)
        double wynajem = uslugi.stream().filter(c -> Objects.equals(c.rodzaj_uslugi, "W")).mapToDouble(c ->c.rata).sum();
        double leasing = uslugi.stream().filter(c -> Objects.equals(c.rodzaj_uslugi, "L")).mapToDouble(c ->c.rata).sum();

        bw_6.write("a)\n");
        bw_6.write(String.format("wynajem: %.2f\n", wynajem));
        bw_6.write(String.format("wynajem: %.2f\n\n", leasing));

        //Zadanie b)
        String nip_bartex = nip_firm.stream().filter(c -> c.firma.equals("BARTEX")).findFirst().map(c -> c.NIP).orElse(null);
        ArrayList<Para> pary = uslugi.stream().filter(c -> c.NIP.equals(nip_bartex)).map(c -> new Para(c.ozn, c.nr)).collect(Collectors.toCollection(ArrayList::new));
        pary.sort(Comparator.comparing(c -> c.b));
        Collections.reverse(pary);
        bw_6.write("b)\n");

        for(Para p : pary)
        {
            bw_6.write(String.format("%s %s\n", p.a, p.b));
        }
        bw_6.newLine();

        //Zadanie c)
        HashMap<String, Integer> fp = new HashMap<>();

        for(Nip_firmy nf : nip_firm)
        {
            Integer lp = 0;
            for(Usluga u : uslugi)
            {
                if(nf.NIP.equals(u.NIP) && u.rodzaj_uslugi.equals("L"))
                {
                    lp += 1;
                }
            }
            fp.put(nf.firma, lp);
        }

        Map.Entry<String, Integer> max_firma = fp.entrySet().stream().max(Comparator.comparingInt(Map.Entry::getValue)).orElse(null);
        bw_6.write("c)\n");
        assert max_firma != null;
        bw_6.write(String.format("%s %d\n\n", max_firma.getKey(), max_firma.getValue()));

        //Zadanie d)
        bw_6.write("d)\n");
        for(Tablica t : tablice)
        {
            if(t.typ.equals("z"))
            {
                if(!uslugi.stream().filter(c -> c.ozn.equals(t.ozn)).toList().isEmpty())
                {
                    bw_6.write(String.format("%s\n", t.powiat));
                }
            }
        }
        bw_6.newLine();

        //Zadanie e)
        ArrayList<String> ozn_konin = tablice.stream().filter(c -> c.powiat.equals("Konin")).map(c -> c.ozn).collect(Collectors.toCollection(ArrayList::new));
        bw_6.write("e)\n");
        for(Nip_firmy nf : nip_firm)
        {
            float srednia = 0.0f;
            int liczba = 0;
            for(Usluga u : uslugi)
            {
                if(ozn_konin.contains(u.ozn) && u.NIP.equals(nf.NIP))
                {
                    liczba++;
                    srednia += u.rata;
                }
            }

            if(liczba > 0)
            {
                srednia = srednia / liczba;
                bw_6.write(String.format("%s %.2f\n", nf.firma, srednia));
            }
        }

        bw_6.close();
    }
}
