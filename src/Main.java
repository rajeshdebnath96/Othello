import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Random;

public class Main{
    public static void main(String[] args) throws FileNotFoundException {
        int board_size = 8;
      //  int[][] board = new int[board_size][board_size];
        int board[][] = {{0,0,0,0,0,0,0,0},
                {0, 0,2,0,0,0,0,0},
                {0,0,2,2,0,0,0,0,},
                {0,1,2,2,2,0,0,0},
                {0,1,1,1,1,1,0,0},
                {0,1,0,1,2,0,0,0},
                {0,0,0,1,0,0,0,0},
                {0,0,0,1,0,0,0,0}};
        Random rand = new Random();
        int r = rand.nextInt(1);
        for(int i=0;i<board_size;i++){
            for (int j=0;j<board_size;j++){
                board[i][j] = 0; // 0 for blank, 1 for black, 2 for white (alphabetically)
            }
        }
        if(r==0){
            board[3][3] = 1;
            board[4][4] = 1;
            board[3][4] = 2;
            board[4][3] = 2;
        }
        else if(r==1){
            board[3][3] = 2;
            board[4][4] = 2;
            board[3][4] = 1;
            board[4][3] = 1;
        }

        PrintStream outStream = new PrintStream(new FileOutputStream("./output.txt"));
       // System.setOut(outStream);
        PrintStream dummyStream = new PrintStream(new OutputStream(){
            public void write(int b) {
                // NO-OP
            }
        });
      //  System.setOut(dummyStream);
        double start = System.currentTimeMillis();
        Node.print_board(board,board_size);
        Othello othello = new Othello(board,board_size);
        othello.solvePuzzle();
     //   Node node = new Node(board,board_size,2);
     //   node.heuristics2(board,1,2);
        double end = System.currentTimeMillis();
        System.out.println("Execution time : " + (end-start)/1000 +"s");
    }
}