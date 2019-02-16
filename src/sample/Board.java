package sample;

import java.util.ArrayList;

public class Board {
    private int[][] board;
    private int gScore;
    private int size;
    private int blankRow, blankCol;

    private Board parent;


    public Board(int size)
    {
        if(size > 1)
        {
            this.size = size;
            board = new int[size][size];
            gScore = 0;
            blankRow = size - 1;
            blankCol = size-1;
            parent = null;

            int count = 1;


            for (int i = 0; i < size; i++)
            {
                for (int j = 0; j < size; j++)
                {
                    board[i][j] = count % (size*size);
                    count++;
                }
            }
        }
        else
        {
            this.size = 1;

        }
    }

    //Accessor methods
    public int[][] getBoard()
    {
        return board;
    }

    public int getHeuristic()
    {
        int count = 1;
        int heuristic = 0;
       for (int i = 0; i < size; i++)
       {
           for (int j = 0; j < size; j++)
           {
               if (board[i][j] != count%(size*size))
               {
                   heuristic++;
               }
               count++;
           }
       }

       return heuristic;
    }

    public int getgScore()
    {
        return gScore;
    }


    public int getCost()
    {
        return getHeuristic() + gScore;
    }

    public int getSize()
    {
        return size;
    }

    public Board getParent()
    {
        return parent;
    }



    //Mutator methods

    public void setBoard(int[][] newBoard) {
        //Copy the board over
        size = newBoard.length;
        board = new int[size][size];


        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = newBoard[i][j];

                if (newBoard[i][j] == 0) {
                    blankRow = i;
                    blankCol = j;
                }
            }
        }

    }

    public void setParent(Board board)
    {
        parent = board;
    }

    public void setgScore(int gScore) {
        this.gScore = gScore;
    }

    public void incGScore()
    {
        gScore++;
    }




    //Changing the board
    public boolean movable(int number)
    {
        try
        {
            if (board[blankRow+1][blankCol] == number)
            {
                return true;
            }
        }catch(ArrayIndexOutOfBoundsException e) { }

        try
        {
            if (board[blankRow][blankCol+1] == number)
            {
                return true;
            }
        }catch(ArrayIndexOutOfBoundsException e) { }

        try
        {
            if (board[blankRow-1][blankCol] == number)
            {
                return true;
            }
        }catch(ArrayIndexOutOfBoundsException e) { }

        try
        {
            if (board[blankRow][blankCol-1] == number)
            {
                return true;
            }
        }catch(ArrayIndexOutOfBoundsException e) { }

        return false;
    }

    public void move(int number)
    {
        if (movable(number))
        {
            try {
                if (board[blankRow+1][blankCol] == number) {
                    board[blankRow][blankCol] = number;
                    blankRow += 1;
                    board[blankRow][blankCol] = 0;
                    return;
                }
            }catch(ArrayIndexOutOfBoundsException e) { }

            try {
                if (board[blankRow][blankCol+1] == number) {
                    board[blankRow][blankCol] = number;
                    blankCol += 1;
                    board[blankRow][blankCol] = 0;
                    return;
                }
            }catch(ArrayIndexOutOfBoundsException e) { }

            try {
                if (board[blankRow-1][blankCol] == number) {
                    board[blankRow][blankCol] = number;
                    blankRow -= 1;
                    board[blankRow][blankCol] = 0;
                    return;
                }
            }catch(ArrayIndexOutOfBoundsException e) { }

            try {
                if (board[blankRow][blankCol-1] == number) {
                    board[blankRow][blankCol] = number;
                    blankCol -= 1;
                    board[blankRow][blankCol] = 0;
                    return;
                }
            }catch(ArrayIndexOutOfBoundsException e) { }
        }
    }

    public Board getCopy()
    {
        Board copy = new Board(size);
        copy.setBoard(board);
        copy.setParent(parent);
        copy.setgScore(gScore);

        return copy;
    }



    public ArrayList<Board> getSuccessors()
    {
        ArrayList<Board> successors = new ArrayList<>();

        for (int i = 1; i < board.length*board.length; i++)
        {
            if (movable(i))
            {
                    Board copy = getCopy();
                    copy.setParent(getCopy());
                    copy.move(i);
                    copy.incGScore();

                    successors.add(copy);
            }
        }

        return successors;
    }

    //Solvable boolean
    public boolean solvable() {
        int parity = (blankCol + blankRow) % 2;

        int inversions = 0;

        //Copy board
        int[][] tempBoard = new int[size][size];

        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                tempBoard[i][j] = board[i][j];
            }
        }

        //Solve the board by switching tiles in tempBoard, making sure to count the number of swaps necessary

        for (int i = 0; i < size*size; i++)
        {
            if (tempBoard[i/size][i%size] != (i+1)%(size*size))
            {
                for (int j = 0; j < size*size; j++)
                {
                    if (tempBoard[j/size][j%size] == (i+1)%(size*size))
                    {
                        int temp = tempBoard[i/size][i%size];
                        tempBoard[i/size][i%size] = board[j/size][j%size];
                        tempBoard[j/size][j%size] = temp;
                        break;
                    }
                }

                inversions++;
            }
        }


        return inversions%2 == parity;

    }

    public boolean boardEquals(Board b)
    {
        for (int i = 0; i < b.getSize(); i++)
        {
            for (int j = 0; j < b.getSize(); j++)
            {
                if (board[i][j] != b.getBoard()[i][j])
                {
                    return false;
                }
            }
        }

        return true;
    }
}
