import java.io.*; // Importarea librariilor necesare p-ru utilizare scanner-ului si fisierelor
import java.util.*;
public class Test
{
   static void Menu() throws IOException
   {
       Zona[][] z = Zona.inputData(); // Instantierea matricei z ce va contine informatie despre fiecare zona
       //Afisarea meniului
       System.out.println("=========================================MENU=========================================================");
       System.out.println("1. Interschimbarea in planul terenului a primii cu ultima linie");
       System.out.println("2. Excluderea din plan unui rand / unei coloana marginala: (nord/sud), (vest/est)");
       System.out.println("3. Determinarea coordonatelor zonelor de maxim local propriu al altitudinilor");
       System.out.println("4. Determinarea numarului zonelor cu altitudinea mai mare decat altitudinea medie\n" +
               "   a acelor zone cu tipul de roca specificat");
       System.out.println("5. Afisarea listei rocilor distincte din zonele terenului in ordine descendenta");
       System.out.println("6. Crearea fisierului RociTip.txt cu rocile care depasesc altitudinea maxima");
       System.out.println("7. Gasirea dreptunghiului de arie maximala");
       System.out.println("8. Rezolvarea problemei");
       System.out.println("0. Iesire");
       System.out.print("Alegeti optiunea: ");

       try // Tratarea exceptiei la introducerea valorilor eronate
       {
           switch (Zona.sc.nextInt()) // Alegerea optiunii de executare
           {
               case 1:
                   System.out.println("======================Interschimbarea in planul terenului a primii cu ultima linie======================");
                   Zona.swapRows(z);
                   break;
               case 2:
                   System.out.println("======================Excluderea din plan a unui rand / unei coloana======================");
                   Zona.delRowsCols(z);
                   break;
               case 3:
                   System.out.println("======================Coordonatele zonelor de maxim local propriu al altitudinilor======================");
                   Zona.maxLocal(z);
                   break;
               case 4:
                   System.out.println("======================Numarul zonelor cu altitudinea mai mare decat altitudinea medie a acelor zone din teren======================");
                   Zona.avgAltitude(z);
                   break;
               case 5:
                   System.out.println("======================Afisarea listei rocilor distincte din zonele terenului in ordine descendenta======================");
                   Zona.rockList(z);
                   break;
               case 6:
                   System.out.println("======================Crearea fisierului RociTip.txt cu rocile care depasesc altitudinea maxima======================");
                   Zona.rockType(z);
                   break;
               case 7:
                   System.out.println("======================Gasirea dreptunghiului de arie maximala======================");
                   Zona.maxDreptunhiS(z);
                   break;
               case 8:
                   System.out.println("======================Rezolvarea problemei======================");
                   ProblemaAlpinist.startProblem(z);
                   break;
               case 0:
                   return;
               default:
                   System.out.println("Wrong value!");
                   break;
           }
       }
       catch (InputMismatchException e)
       {
           Zona.sc.next();
           System.out.println("Wrong value!");
       }
       System.out.println("\n" + "Apasati ENTER pentru a va intoarce in menu");
       System.in.read();// Comanda ce asteapta apasarea tastei ENTER pentru a continua executia programului
       Menu(); // Reapelarea recursiva meniului
   }

    public static void main(String[] args) throws IOException
    {
        Zona.sc = new Scanner(System.in); // Initializarea scanner-ului
        Menu(); // Apelarea meniului
        Zona.sc.close(); // Inchiderea scanner-ului

    }


}
