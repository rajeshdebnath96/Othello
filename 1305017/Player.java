import java.util.ArrayList;



public class Player {
    int[][] current_board;
    int board_size,color,other_color;
    static final int DEPTH = 7;
    Player(int[][] board, int board_size,int color){
        current_board = board;
        this.board_size = board_size;
        this.color = color;if(color == 1) other_color =2;
        else other_color = 1;
    }
    static int isGoalState(Node n){
        if(n == null) return -1;
        int cost1=0,cost2=0;
        for(int i=0;i<n.board_size;i++){
            for(int j=0;j<n.board_size;j++){
                if(n.board[i][j] != 0) {
                //    System.out.println("Not Goal State");
                    return 0;
                }
                else if(n.board[i][j] == 1) cost1++;
                else if(n.board[i][j] == 2) cost2++;
            }
        }
        if(cost1 == 0 || cost2==0) return 0;
        if(cost1 > cost2) return 1;
        else return 2;
    }
    Node play(){
        Node n = new Node(current_board,board_size,color);
        Node res = alphaBeta(n,DEPTH,-999999,999999,true);
     //   System.out.printf("Final Node\n");
        if(res != null) Node.print_board(res.board,res.board_size);
        return res;
    }
    Node alphaBeta(Node n,int depth, double alpha, double beta, boolean maximigingPlayer){
    //    System.out.println("depth = "+depth );
      //  Node.print_board(n.board,n.board_size);
        if(depth ==0 || (isGoalState(n) != 0) ){
       //     System.out.println("Return from alpha-beta");
         //   Node.print_board(n.board,n.board_size);
            return n;
        }
        if(maximigingPlayer){
            double value = Integer.MIN_VALUE;
            Node node,op_node = null;
         //   System.out.println("Maximizing player "+depth);
            ArrayList<Node> neighbours= n.getNeighbours(this.color,this.other_color);
            for(int i=0;i<neighbours.size();i++){
                node= alphaBeta(neighbours.get(i),depth-1,alpha,beta,false);
                double cost = Integer.MIN_VALUE;
                if(node == null) return neighbours.get(i);
                if(node != null){cost = node.cost;}
                value = Math.max(cost,value);
                if(value == cost) {
                    if (depth == DEPTH) op_node = neighbours.get(i);
                    else op_node = node;
                }
                alpha = Math.max(value,alpha);
                if(beta <= alpha) break;
            }
            if(op_node != null) {
              //  System.out.println("Return from maximizing player depth= "+depth );
              //  Node.print_board(op_node.board, op_node.board_size);
            }
            return op_node;
        }
        else {
            double value = Integer.MAX_VALUE;
            Node node,op_node = null;
         //   System.out.println("M8nimizing player "+ depth);
            ArrayList<Node> neighbour = n.getNeighbours(this.other_color,this.color);
            for(int i=0;i<neighbour.size();i++){
                node = alphaBeta(neighbour.get(i),depth-1,alpha,beta,true);
                double cost = Integer.MAX_VALUE;
                if(node == null) return neighbour.get(i);
                if(node != null){cost = node.cost;}
                value = Math.min(cost,value);
                if(value == cost){
                    if(depth == DEPTH) op_node = neighbour.get(i);
                    else op_node = node;
                }
                beta = Math.max(beta,value);
                if(beta <= alpha) break;
            }
            if(op_node != null) {
             //   System.out.println("Return from minimizing agent depth= "+depth);
              //  Node.print_board(op_node.board, op_node.board_size);
            }
            return op_node;
        }
    }
}
