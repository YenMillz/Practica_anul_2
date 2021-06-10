import java.io.*; // Importarea librariilor necesare p-ru utilizare scanner-ului si fisierelor
import java.util.*;

public class Zona
{
    String roci;
    double altitudine;
    int rank;
    static int n, m;
    static Scanner frMunte, frRoci, sc;
    static FileWriter fwMunte, fwRoci;

    static Zona[][] inputData() throws FileNotFoundException // Metoda ce citeste din fisier datele despre zone
    {
        frMunte = new Scanner(new FileReader("Munte.in")); // Initializarea fileredear-ului pentru altitudinile fiecarei zone
        frRoci = new Scanner(new FileReader("Roci.in")); // Initializarea fileredear-ului pentru tipul de roci a fiecarei zone

        n = frMunte.nextInt(); // Initializarea dimensiunii matricei z
        m = frMunte.nextInt();

        Zona [][] z = new Zona[n][m]; // Atribuirea dimensiunilor pentru matricie z

        for (int i = 0; i < n; i++) // Completarea matricei cu valori
        {
            for (int j = 0; j < m; j++)
            {
                z[i][j] = new Zona();
                z[i][j].altitudine = frMunte.nextDouble();
                z[i][j].roci = frRoci.next();
            }
        }

        frMunte.close();
        frRoci.close();
        return z; // Returnarea primului element al matricei
    }

    static void outputData(Zona[][] z, int a, int b, int c, int d) throws IOException // Functie pentru afisarea informatiei din matrice in fisier
    {
        fwMunte = new FileWriter("Munte.in");
        fwRoci = new FileWriter("Roci.in");

        fwMunte.write(b - a + " " + (d - c) + "\n");
        for (int i = a; i < b; i++) // Afisarea in fisier a informatiei despre zone
        {
            for (int j = c; j < d; j++)
            {
                fwMunte.write(z[i][j].altitudine + " ");
                fwRoci.write(z[i][j].roci + " ");
            }
            fwMunte.write("\n");
            fwRoci.write("\n");
        }
        fwMunte.close();
        fwRoci.close();
    }

    static void swapRows(Zona[][] z) throws IOException // Metoda pentru interschimbarea primului cu ultimul rand
    {
        fwMunte = new FileWriter("Munte.in"); // Initializarea fileredear-ului pentru altitudinile fiecarei zone
        fwRoci = new FileWriter("Roci.in"); // Initializarea fileredear-ului pentru tipul de roci a fiecarei zone
        //System.out.println("Dupa interschimbare: ");

            for (int j = 0; j < m; j++)
            {
                Zona temp = z[0][j];
                z[0][j] = z[n - 1][j];
                z[n - 1][j] = temp;
            }

            fwMunte.write(n + " " + m + "\n");
            for (int i = 0; i < n; i++) // Afisarea matricei interschimbate
            {
                for (int j = 0; j < m; j++)
                {
                    fwMunte.write(z[i][j].altitudine + " ");
                    fwRoci.write(z[i][j].roci + " ");

                }
                fwMunte.write("\n");
                fwRoci.write("\n");
            }

        System.out.println("Liniile au fost interschimbate cu succes!");
        fwMunte.close();
        fwRoci.close();

    }

    static void delRowsCols(Zona[][] z) throws IOException // Metoda ce in dependenta de directia indicata sterge randul/coloana
    {
        System.out.print("Precizati pozitia randului/coloanei: ");
        switch (sc.next())
        {
            case "nord":
               outputData(z, 1, n, 0, m);
                break;
            case "sud":
                outputData(z, 0, n - 1, 0, m);
                break;
            case "vest":
                outputData(z, 0, n, 1, m);
                break;
            case "est":
                outputData(z, 0, n, 0, m - 1);
                break;
            default:
                System.out.println("Wrong value!");
        }
        System.out.println("Randul/coloana a fost stearsa cu succes!");

    }

    static void maxLocal(Zona[][] z) // Metoda pentru determinarea maximului local
    {
        int count = 0;
        for (int i = 1; i < n - 1; i++) // Ferificarea succesiva a fiecaru element daca este maxim local
        {
            for (int j = 1; j < m - 1; j++)
                if (z[i][j].altitudine > z[i - 1][j - 1].altitudine && z[i][j].altitudine > z[i - 1][j].altitudine &&
                        z[i][j].altitudine > z[i - 1][j + 1].altitudine && z[i][j].altitudine > z[i][j - 1].altitudine &&
                        z[i][j].altitudine > z[i][j + 1].altitudine && z[i][j].altitudine > z[i + 1][j - 1].altitudine &&
                        z[i][j].altitudine > z[i + 1][j].altitudine && z[i][j].altitudine > z[i + 1][j + 1].altitudine) // Verificam daca elementul curent este mai mare ca cei 8 vecini ai sai
                {
                    System.out.print("[" + (i + 1) + ", " + (j + 1) + "] "); // Afisarea maximului local curent
                    count++;
                }
        }
        if(count == 0) // In caz in care nu exista nici un maxim local afisam mesajul corespunzator
            System.out.println("Nu exista nici un maxim local");
    }

    static void avgAltitude(Zona[][] z) // Metoda ce determina altitudinea medie
    {
        sc = new Scanner(System.in);
        try
        {
            System.out.print("Introduceti tipul de roca: ");
            String roca = sc.next();
            double altitudine = 0;
            int count = 0, nr = 0;
            for (int i = 0; i < n; i++)
            {
                for (int j = 0; j < m; j++)
                    if (roca.equals(z[i][j].roci)) // Calcularea numarului de roci si a sumei altitudinilor tuturor valorilor
                    {
                        altitudine += z[i][j].altitudine;
                        count++;
                    }
            }

            for (int i = 0; i < n; i++)
                for (int j = 0; j < m; j++) // Calcularea numarului de zone ce satisfac conditia
                    if (z[i][j].altitudine > altitudine / count)
                        nr++;
            System.out.println("Nr zonelor cu altitudineamai mai mare decat altitudinea medie a acelor zone cu tipul de roca introdus de la tastatrura: " + nr);
        }
        catch (InputMismatchException e)
        {
            Zona.sc.next();
            System.out.println("Wrong value!");
        }
    }

    static void rockList(Zona[][] z) // Metoda ce afiseaza lista rocilor in ordine sortata
    {
        Map<String, Integer> roci = new HashMap<>(); // Crearea mapii pentru pastrarea fiecarei roci si a numarului ei de aparitii

        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++) // Iteram fiecare element
            {
                String tempRoca = z[i][j].roci;
                if(!roci.containsKey(tempRoca)) // Eliminam duplicatele de valori dupa care il salvam in mapa
                    roci.put(tempRoca, 1);
                else
                    roci.put(tempRoca, roci.get(tempRoca) + 1);
            }
        Zona [] rociSort = new Zona[roci.size()]; // Cream vector pentru sortarea rocilor

        int i = 0;
        for (Map.Entry<String, Integer> entry : roci.entrySet()) // Transferul din mapa in vector a datelor
        {
            rociSort[i] = new Zona();
            rociSort[i].roci = entry.getKey();
            rociSort[i].rank = entry.getValue();
            i++;
        }

       for (i = 0; i < rociSort.length - 1; i++) // Sortarea rocilor prin metoda specificata
       {
           int min_indx = i;
           for (int j = i + 1; j < rociSort.length; j++)
               if (rociSort[j].rank > rociSort[min_indx].rank)
               {
                   min_indx = j;
                   Zona aux = rociSort[i];
                   rociSort[i] = rociSort[min_indx];
                   rociSort[min_indx] = aux;
               }
       }

       for (i = 0; i < rociSort.length; i++) // Afisarea informatiei sortate
           System.out.println(rociSort[i].roci + " in " + rociSort[i].rank + " zone ");

    }

    static void rockType(Zona[][] z) throws IOException // Metoda ce afiseaza rocile zonelor altitudinea carora depaseste altitudinea maxima
    {
        fwRoci = new FileWriter("RociTip.txt");
        double avg = 0;
        for (int i = 0; i < n; i++) // Calcularea valorii medii
        {
            for (int j = 0; j < m; j++)
                avg += z[i][j].altitudine;
        }

        System.out.println("Datele au fost inscrise cu succes!");
        for (int i = 0; i < n; i++) // Afisarea rocilor care au altitudinea mai mare ca media rocilor cu tipul; specificat
        {
            for (int j = 0; j < m; j++)
                if(z[i][j].altitudine > avg/(n*m))
                    fwRoci.write(z[i][j].roci + " ");
                fwRoci.write("\n");
        }
        fwRoci.close();
    }

    static boolean perimetruD(Zona [][] z, int i, int j, int k, int l, double max) // Metoda ce verifica daca valoarea curenta satisface conditia problemei
    {
        for (int v = i; v <= k; v++) // Daca elementul de pe prima si ultima linie este diferit mai mic sau egala de cat val maxima atunci returnam false
            if (z[v][j].altitudine <= max || z[v][l].altitudine <= max)
                return false;

        for (int v = j; v <= l; v++)
            if (z[i][v].altitudine <= max || z[k][v].altitudine <= max) // Daca elementul de pe prima si ultima coloana este diferit mai mic sau egala de cat val maxima atunci returnam false
                return false;

        return true;
    }

    static void maxDreptunhiS(Zona[][] z) // Metoda ce determina dreptunghiul de arie maxima
    {
        int maxS = 0, x1 = 0, y1 = 0, x2 = 0, y2 = 0;
        double max = 0;
        for(int i = 0; i < n; i++)
            for(int j = 0; j < m; j++) // Iterarea fiecarui element din matrice
                for(int k = i + 1; k < n; k++) // Cautarea coltului stanga-jos
                    for(int l = j + 1; l < m; l++) // Cautarea coltului dreapta-sus
                    {
                        for (int p = i + 1; p < k; p++)
                            for (int f = j + 1; f < l; f++) // Cautarea elementelor din interiorul dreptunghiului
                                if (z[p][f].altitudine > max) // Determinarea celei mai mari valori din interiorul dreptunghiului
                                    max = z[p][f].altitudine;

                        if (perimetruD(z, i, j, k, l, max) && max != 0) // Verificam daca valoarea curenta e valida
                        {
                            int S = (k - i + 1) * (l - j + 1); // Calculam aria actuala
                            if(S > maxS) // Comparam cu aria maximal
                            {
                                maxS = S;
                                x1 = k;
                                y1 = j;
                                x2 = i;
                                y2 = l;
                            } // Daca este mai mare atribuim la maxS = S si memoram coordonatele acestui dreptunghi
                        }
                        max = 0; // Atribuim max valoarea 0 pentru urmatoarele iteratii
                    }

        if(maxS != 0) // Daca maxS e diferit de 0 afiam mesajul cu maxS si coordonatele dreptunhiului
            System.out.println("Aria maximala: " + maxS + "\nCoordonatele: stanga-jos (" + (x1 + 1) + ", " + (y1 + 1) + ") si dreapta-sus (" + (x2 + 1) + ", " + (y2 + 1) + ")");
        else // In caz contrar afiam ca astfel de dreptunghi nu exista
            System.out.println("Asa dreptunghi nu exista");

    }

}
