public class Othello {
    int[][] board,current_board;
    int board_size,color=2;
    Othello(int[][] board,int board_size){
        this.board = board;
        this.current_board = board;
        this.board_size = board_size;
    }
    public void solvePuzzle(){
        int i =0;
        Node res = new Node(current_board,board_size,color);
        Node prev;
        while (true){
            System.out.println("Iteration : "+i);
            System.out.println("Player "+((color == 1)?"Black ":"White ") + " move: ");
         //   System.out.println();
            Player p = new Player(res.board,res.board_size,color);
          ///  System.out.println("p "+p==null);
            prev = res;
            res = p.play();
            int w = Player.isGoalState(res);
            if( w!= 0) {
                if(w != -1){
                    System.out.println("Winner is "+((w == 1)?"Black ":"White ")+"Player");
                    Node.print_board(res.board,board_size);
                }
                else {
                    w = Player.isGoalState(prev);
                    System.out.println("No move left \n"+"Winner is "+(((w == 1)?"Black ":"White "))+"Player");
                    Node.print_board(prev.board,board_size);
                }
                break;
            }
            if(color == 1) color =2;
            else if(color == 2) color = 1;
            i++;
         }
    }
}
