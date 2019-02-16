package sample;


import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        int[][] test = {{7,6,2},{5,4,3},{1,0,8}};

        Solver s = new Solver(test);

        if (s.solve() != null) {
            for (int i : s.solve()) System.out.println(i);
        } else {
            System.out.println("Not solvable");
        }

    }

}
