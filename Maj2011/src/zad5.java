import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Osoba
{
    public Osoba(String id_osoby, String imie, String nazwisko, String nr_telefonu) {
        this.id_osoby = id_osoby;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.nr_telefonu = nr_telefonu;
    }

    String id_osoby;
    String imie;
    String nazwisko;
    String nr_telefonu;
}

class Pies
{
    int id_psa;
    String rasa;
    int wiek;
    String plec;
    int liczba_zdobytych_medali;
    String id_osoby;

    public Pies(int id_psa, String rasa, int wiek, String plec, int liczba_zdobytych_medali, String id_osoby) {
        this.id_psa = id_psa;
        this.rasa = rasa;
        this.wiek = wiek;
        this.plec = plec;
        this.liczba_zdobytych_medali = liczba_zdobytych_medali;
        this.id_osoby = id_osoby;
    }
}

public class zad5 {
    public static void main(String[] args) throws IOException {
        ArrayList<Osoba> osoby = new ArrayList<>();
        ArrayList<Pies> psy = new ArrayList<>();
        Scanner sc_osoby = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj2011\\Dane\\osoby.txt"), "windows-1250");
        Scanner sc_psy = new Scanner(new File("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj2011\\Dane\\psy.txt"), "windows-1250");
        BufferedWriter bw_5 = new BufferedWriter(new FileWriter("C:\\Users\\Admin\\IdeaProjects\\Matury_w_Javie\\Maj2011\\Wyniki\\zadanie5.txt"));

        while(sc_osoby.hasNextLine())
        {
            String[] linia = sc_osoby.nextLine().split(";");
            osoby.add(new Osoba(linia[0], linia[1], linia[2], linia[3]));
        }

        while(sc_psy.hasNextLine())
        {
            String[] linia = sc_psy.nextLine().split(";");
            psy.add(new Pies(Integer.parseInt(linia[0]), linia[1], Integer.parseInt(linia[2]), linia[3], Integer.parseInt(linia[4]), linia[5]));
        }

        //Zadanie a)
        int l_samic = psy.stream().filter(c -> c.plec.equals("samica")).toList().size();
        int l_samcow = psy.stream().filter(c -> c.plec.equals("samiec")).toList().size();
        bw_5.write("a)\n");
        bw_5.write(String.format("Liczba samic: %d\n", l_samic));
        bw_5.write(String.format("Liczba samców: %d\n\n", l_samcow));

        //Zadanie b)
        TreeMap<String, Integer> olp = new TreeMap<>();
        bw_5.write("b)\n");
        for(Osoba o : osoby)
        {
            String klucz = String.format("%s %s %s", o.nazwisko, o.imie, o.id_osoby);
            olp.put(klucz, 0);
            for(Pies p : psy)
            {
                if(p.id_osoby.equals(o.id_osoby))
                {
                    olp.put(klucz, olp.get(klucz) + 1);
                }
            }
        }

        TreeMap<String, Integer> osoby_ponad_8_psow = olp.entrySet().stream().filter(c -> c.getValue() > 8).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, TreeMap::new));

        for(Map.Entry<String, Integer> op : osoby_ponad_8_psow.entrySet())
        {
            bw_5.write(String.format("%s\n", op.getKey().substring(0, op.getKey().length() - 5)));
        }

        bw_5.write("\n");

        //Zadanie c)
        TreeMap<String, Integer> olm = new TreeMap<>();
        bw_5.write("c)\n");
        for(Osoba o : osoby)
        {
            String klucz = String.format("%s %s %s", o.nazwisko, o.imie, o.id_osoby);
            olm.put(klucz, 0);
            for(Pies p : psy)
            {
                if(p.id_osoby.equals(o.id_osoby))
                {
                    olm.put(klucz, olm.get(klucz) + p.liczba_zdobytych_medali);
                }
            }
        }

        Map.Entry<String, Integer> max_olm = olm.entrySet().stream().max(Map.Entry.comparingByValue()).orElseThrow();

        bw_5.write(String.format("%s %d\n\n", max_olm.getKey().substring(0, max_olm.getKey().length() - 5), max_olm.getValue()));

        //Zadanie d)
        HashSet<String> oo = new HashSet<>();
        bw_5.write("d)\n");

        for(Pies p : psy)
        {
            if(p.rasa.contains("owczarek"))
            {
                oo.add(p.id_osoby);
            }
        }
        bw_5.write(Integer.toString(oo.size()));

        bw_5.close();
    }
}
