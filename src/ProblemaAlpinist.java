public class ProblemaAlpinist
{
    static double max;
    static int Xa, Ya, xMax, yMax;
    // Vectori ce indica posibilitatile de deplasare a alpinistului
    static int[] di = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] dj = {0, 1, 1, 1, 0, -1, -1, -1};
    static int[][] used; // Vector ce memoreaza daca zona a fost vizitata sau nu

    static void printSolution(String[][] path) // Metoda ce afiseaza Hmax si drumul parcurs de alpinist
    {
        System.out.println("DA, EXISTA O SOLUTIE PENTRU REZOLVAREA PROBLEMEI");
        System.out.println("Hmax = " + max);

        for (String[] i : path)
        {
            for (String j : i)
                if (j != null)
                    System.out.print(j);
        }

    }

    static boolean isSafe(Zona[][] maze, int x, int y) // Metoda ce verifica daca pasul curent satisface conditia de deplasare
    {
        return (x >= 0 && x < Zona.n && y >= 0 && y < Zona.m && maze[x][y].altitudine == maze[Xa - 1][Ya - 1].altitudine);
    }

    static void solveMaze(Zona[][] maze) // Metoda ce afiseaza mesajul despre existenta drumului
    {
        String[][] path = new String[1000][1000];

        if (!solveMazeUtil(maze, Xa - 1, Ya - 1, path, 0, 0))
        {
            System.out.print("NU EXISTA NICI O SOLUTIE PENTRU REZOLVAREA PROBLEMEI");
            return;
        }

        printSolution(path);
    }

    static boolean solveMazeUtil(Zona[][] maze, int x, int y, String[][] path, int i_path, int j_path) // Metoda de tip backtracking ce parcurge succesiv fiecare pas
    {
        if (x == xMax && y == yMax && maze[x][y].altitudine == max) // In cazul in care pasul curent e egal cu pasul final atunci se termina executia prog
        {
            path[i_path][j_path] = "[" + (x + 1) + ", " + (y + 1) + "]";
            return true;
        }

        if (isSafe(maze, x, y)) // In caz contrar verificam fiecare pas
        {
            used[x][y] = 1; // memorarea pozitiei
            path[i_path][j_path] = "[" + (x + 1) + ", " + (y + 1) + "]-"; // Salvarea drumului intr-un vector aparte
            for (int k = 0; k < 8; k++) // Iterarea fiecarui pas din vectorul di[] si dj[]
                if (x + di[k] >= 0 && y + dj[k] >= 0)
                    if (used[x + di[k]][y + dj[k]] == 0) // Verificare elementului urmator
                    {
                        if (solveMazeUtil(maze, x + di[k], y + dj[k], path, i_path + 1, j_path + 1)) // Reapelarea recursiva a metodei bk
                            return true;

                    }

            path[i_path][j_path] = null; // La intoarcere atribuim null la pasul efectuat

            return false;
        }

        return false;
    }

    static void findMaxElement(Zona[][] maze) // Metoda ce gaseste Hmax
    {
        max = maze[0][0].altitudine;
        for (int i = 0; i < Zona.n; i++)
            for (int j = 0; j < Zona.m; j++)
                if (max < maze[i][j].altitudine)
                {
                    max = maze[i][j].altitudine;
                    xMax = i;
                    yMax = j;
                }
    }

    static void startProblem(Zona[][] z) // Metoda ce pune la executie intreaga problema
    {
        used = new int [1000][1000]; // Initializarea vectorului ce va memora fiecare pas efectuat
        System.out.println("Introduceti coordonatele de pornire a alpinistului (Xa, Ya): ");
        try // Tratarea exceptiei la introducerea valorilor eronate
        {
            System.out.print("Xa = ");
            Xa = Zona.sc.nextInt();
            System.out.print("Ya = ");
            Ya = Zona.sc.nextInt();
            findMaxElement(z);
            solveMaze(z);
        }
        catch (Exception e)
        {
            System.out.println("Wrong value!");
            Zona.sc.next();
        }

    }
}