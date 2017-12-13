import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class Node {
    int[][] board;
    int board_size;
    double cost;
    int color,other_color;
    ArrayList<Node> Neighbours = new ArrayList<Node>();
    Node(int[][] board,int board_size,int color){
        this.board = board;
        this.board_size = board_size;
        this.color = color;
        if(color == 1) other_color =2;
        else other_color = 1;
       // System.setOut(dummyStream);
    }
    double heuristics1(int[][] board,int c, int oc){
        double cost1 =0,cost2=0,cost;
        int[][] weight = {{4,-3,2,2,2,2,-3,4},
                          {-3,-4,-1,-1,-1,-1,-4,-3},
                          {2,-1,1,0,0,1,-1,2},
                          {2,-1,0,1,1,0,-1,2},
                          {2,-1,0,1,1,0,-1,2},
                          {2,-1,1,0,0,1,-1,2},
                          {-3,-4,-1,-1,-1,-1,-4,-3} ,
                          {4,-3,2,2,2,2,-3,4} };
        for(int i=0;i<board_size;i++){
            for(int j=0;j<board_size;j++){
                if(board[i][j] == c) cost1+=weight[i][j]*1;
                else if(board[i][j] == oc) cost2+=weight[i][j]*1;
            }
        }
        cost = cost1 -cost2;
     //   System.out.println("Cost heuristics1 " +cost +" cost1="+cost1+" cost2="+cost2);
        return cost;
    }
    double heuristics2(int board[][],int c,int oc){
        int cp=0,co=0,mp=0,mo=0;
        int w1=10,w2=1;
        double cost;
        if(board[0][0] == c) cp++;
        else if(board[0][0] == oc) co++;
        if(board[0][7] == c) cp++;
        else if(board[0][7] == oc) co++;
        if(board[7][0] == c) cp++;
        else if(board[7][0] == oc) co++;
        if(board[7][7] == c) cp++;
        else if(board[7][7] == oc) co++;
        mp = getMobility(board,c,oc);
    //    System.out.println("change color");
        mo = getMobility(board,oc,c);
    //    System.out.println("cp= "+cp+" co= "+co + " mp= "+mp+" mo= "+mo);
        cost = w1*(cp - co) + (w2*(mp -mo));///(mp + mo);

     //   System.out.println("Cost heuristics2 "+cost);
        return cost;
    }
    double heuristics3(int board[][],int c,int oc){
        double cost,cost1=0,cost2=0;
        for(int i=0;i<board_size;i++){
            for(int j=0;j<board_size;j++){
                if(board[i][j] == c) cost1++;
                else if(board[i][j] == oc) cost2++;
            }
        }
        cost=cost1-cost2;
       // System.out.println("Cost heuristics3 "+cost);
        return cost;
    }
    static void print_board(int[][] board,int board_size){
        System.out.println("-----------------Printing Board----------------");
        for(int i=0;i<board_size;i++){
            for(int j=0;j<board_size;j++){
                if(board[i][j] == 0) System.out.print(" _ ");
                else if(board[i][j] == 1) System.out.printf(" B ");
                else if(board[i][j] == 2) System.out.printf(" W ");
            }
            System.out.println();
        }
    }
    Node copy_node(Node src,Node des){
        for(int i=0;i<board_size;i++){
            for(int j=0;j<board_size;j++){
                des.board[i][j] = src.board[i][j];
            }
        }
        des.cost = src.cost;
        des.color = src.color;
        des.other_color = des.other_color;
        return des;
    }
    int[][] copy_board(int[][] src, int [][] des){
        for(int i=0;i<board_size;i++){
            for(int j=0;j<board_size;j++){
                des[i][j] = src[i][j];
            }
        }
        return des;
    }
    Node callAlligned(int b[][],int i,int j,int di,int dj,char d,boolean l,int color,int other_color) {
      //  System.out.println("Call Aligned i ="+i+" j="+j);
        Node res = null;
        Node n = isAligned(b,i,j,d,l,color,other_color);
        if(n!=null) {
         //   print_board(n.board,board_size);
            res = n;
            n = checkAligned(res.board, di, dj, 'u', false, color, other_color);
            if (n != null) res = n;
            n = checkAligned(res.board, di, dj, 'u', true, color, other_color);
            if (n != null) res = n;
            n = checkAligned(res.board, di, dj, 'l', false, color, other_color);
            if (n != null) res = n;
            n = checkAligned(res.board, di, dj, 'l', true, color, other_color);
            if (n != null) res = n;
            n = checkAligned(res.board, di, dj, 'p', false, color, other_color);
            if (n != null) res = n;
            n = checkAligned(res.board, di, dj, 'p', true, color, other_color);
            if (n != null) res = n;
            n = checkAligned(res.board, di, dj, 's', false, color, other_color);
            if (n != null) res = n;
            n = checkAligned(res.board, di, dj, 's', true, color, other_color);
            if (n != null) res = n;
        }
        return res;
    }

    Node checkAligned(int b[][],int i,int j,char dir,boolean l,int color,int other_color){
       //   System.out.println("checkAligned finc i= "+i +" j= "+j);
        int[][] board = new int[8][8];
        copy_board(b,board);
        if (dir == 'u') {
            if(l){
             //     System.out.println("Case u -");
                int k= i; i--;
                while (i>0){
                    if(board[i][j] == other_color) {i--; continue;}
                    else if(board[i][j] == 0) break;
                    else if(board[i][j] == color) {
                        while (k>=i && k<8) {
                            board[k][j] = color;
                            k--;
                        }
                        Node n = new Node(board,board_size,this.color);
                        //  n.cost = this.heuristics1(color,other_color);
                     //   System.out.println("true");
                    //    print_board(board,board_size);
                        return n;
                        // break;
                    }
                    i--;
                }
            }
            else{
              //    System.out.println("Case u +");
                int k= i; i++;
                while (i<board_size){
                    //     System.out.println("value " + board[i][j] +" i="+i+" j="+j);
                    if(board[i][j] == other_color) {i++; continue;}
                    else if(board[i][j] == 0) break;
                    else if(board[i][j] == color) {
                     //           System.out.println("value" + board[i][j]);
                        while (k<=i) {
                            board[k][j] = color;
                            k++;
                        }
                        Node n = new Node(board,board_size,this.color);
                    //    System.out.println("true");
                   //      print_board(board,board_size);
                        return n;
                        //  break;
                    }
                    // i++;
                }
            }
        }
        else if(dir == 'l'){
            if(l){
              //      System.out.println("Case l -");
                int k= j; j--;
                while (j>0){
                    if(board[i][j] == other_color) {j--;continue;}
                    else if(board[i][j] == 0) break;
                    else if(board[i][j] == color) {
                        while (k>=j) {
                            board[i][k] = color;
                            k--;
                        }
                        Node n = new Node(board,board_size,this.color);
                    //    print_board(board,board_size);
                     //   System.out.println("true");
                        return n;
                        //  break;
                    }
                    //   j--;
                }
            }
            else {
              //     System.out.println("Case l +");
                int k= j; j++;
                while (j<board_size) {
                    if (board[i][j] == other_color) {j++;continue;}
                    else if (board[i][j] == 0) break;
                    else if (board[i][j] == color) {
                        while (k <= j) {
                            board[i][k] = color;
                            k++;
                        }
                        Node n = new Node(board,board_size,this.color);
                     //   print_board(board,board_size);
                        return n;
                        //  break;
                    }
                    //  j++;
                }
            }
        }
        else if(dir == 'p'){
            if(l){
              //     System.out.println("Case p -");
                int k= i,m=j; i--;j--;
                while (i>0 && j>0){
                    if(board[i][j] == other_color) {i--;j--;continue;}
                    else if(board[i][j] == 0) break;
                    else if(board[i][j] == color) {
                        while (k>=i) {
                            board[k][m] = color;
                            k--;m--;
                        }
                        Node n = new Node(board,board_size,this.color);
                     //   print_board(board,board_size);
                        return n;
                        //  break;
                    }
                    //   i--;
                }
            }
            else {
               //     System.out.println("Case p +");
                int k= i,m=j; i++;j++;
                while (i<board_size && (j<board_size)) {
                    //     System.out.println("i="+i);
                    if (board[i][j] == other_color) {i++;j++;continue;}
                    else if (board[i][j] == 0) {
                        ///  System.out.println( "blank i="+i+" j="+j);
                        break;}
                    else if (board[i][j] == color) {
                        while (k <= i) {
                            board[k][m] = color;
                            k++;m++;
                        }
                        Node n = new Node(board,board_size,this.color);
                     //   print_board(board,board_size);
                        return n;
                        //  break;
                    }
                    //  i++;
                }
            }
        }
        else if(dir == 's'){
            if(l){
             //      System.out.println("Case s -");
                int k= i,m=j; i--;j++;
                while (i>0 && j<8) {
                    if (board[i][j] == other_color) {i--;j++;continue;}
                    else if (board[i][j] == 0) break;
                    else if (board[i][j] == color) {
                        while (k >= i) {
                            board[k][m] = color;
                            k--;m++;
                        }
                        Node n = new Node(board,board_size,this.color);
                    //    print_board(board,board_size);
                        return n;
                    }
                    // i--;
                }
            }
            else {
             //      System.out.println("Case s +");
                int k= i,m=j; i++;j--;
                while (i<board_size && j>0) {
                    if (board[i][j] == other_color) {i++;j--;continue;}
                    else if (board[i][j] == 0) break;
                    else if (board[i][j] == color) {
                        while (k <= i) {
                            board[k][m] = color;
                            k++;m--;
                        }
                        Node n = new Node(board,board_size,this.color);
                      //  print_board(board,board_size);
                        return n;
                    }
                    //   i++;
                }
            }
        }
        //   System.out.println("returning null");
        return null;
    }

    Node isAligned(int b[][],int i,int j,char dir,boolean l,int color,int other_color){
      //  System.out.println("isAligned finc");
        int[][] board = new int[8][8];
        copy_board(b,board);
        if (dir == 'u') {
            if(l){
            //    System.out.println("Case u -");
                int k= i+1; i--;
                while (i>0){
                    if(board[i][j] == other_color) {i--; continue;}
                    else if(board[i][j] == 0) break;
                    else if(board[i][j] == color) {
                        while (k>=i) {
                            board[k][j] = color;
                            k--;
                        }
                        Node n = new Node(board,board_size,this.color);
                      //  n.cost = this.heuristics1(color,other_color);
                        return n;
                       // break;
                    }
                    i--;
                }
            }
            else{
            //    System.out.println("Case u +");
                int k= i-1; i++;
                while (i<board_size){
               //     System.out.println("value " + board[i][j] +" i="+i+" j="+j);
                    if(board[i][j] == other_color) {i++; continue;}
                    else if(board[i][j] == 0) break;
                    else if(board[i][j] == color) {
                //        System.out.println("value" + board[i][j]);
                        while (k<=i) {
                            board[k][j] = color;
                            k++;
                        }
                        Node n = new Node(board,board_size,this.color);
                       // print_board(board,board_size);
                        return n;
                      //  break;
                    }
                   // i++;
                }
            }
        }
        else if(dir == 'l'){
            if(l){
             //   System.out.println("Case l -");
                int k= j+1; j--;
                while (j>0){
                    if(board[i][j] == other_color) {j--;continue;}
                    else if(board[i][j] == 0) break;
                    else if(board[i][j] == color) {
                        while (k>=j) {
                            board[i][k] = color;
                            k--;
                        }
                        Node n = new Node(board,board_size,this.color);
                        return n;
                      //  break;
                    }
                 //   j--;
                }
            }
            else {
             //   System.out.println("Case l +");
                int k= j-1; j++;
                while (j<board_size) {
                    if (board[i][j] == other_color) {j++;continue;}
                    else if (board[i][j] == 0) break;
                    else if (board[i][j] == color) {
                        while (k <= j) {
                            board[i][k] = color;
                            k++;
                        }
                        Node n = new Node(board,board_size,this.color);
                        return n;
                      //  break;
                    }
                  //  j++;
                }
            }
        }
        else if(dir == 'p'){
            if(l){
            //    System.out.println("Case p -");
                int k= i+1,m=j+1; i--;j--;
                while (i>0 && j>0){
                    if(board[i][j] == other_color) {i--;j--;continue;}
                    else if(board[i][j] == 0) break;
                    else if(board[i][j] == color) {
                        while (k>=i) {
                            board[k][m] = color;
                            k--;m--;
                        }
                        Node n = new Node(board,board_size,this.color);
                        return n;
                      //  break;
                    }
                 //   i--;
                }
            }
            else {
            //    System.out.println("Case p +");
                int k= i-1,m=j-1; i++;j++;
                while (i<board_size && (j<board_size)) {
               //     System.out.println("i="+i);
                    if (board[i][j] == other_color) {i++;j++;continue;}
                    else if (board[i][j] == 0) {
                      ///  System.out.println( "blank i="+i+" j="+j);
                        break;}
                    else if (board[i][j] == color) {
                        while (k <= i) {
                            board[k][m] = color;
                            k++;m++;
                        }
                        Node n = new Node(board,board_size,this.color);
                        return n;
                      //  break;
                    }
                  //  i++;
                }
            }
        }
        else if(dir == 's'){
            if(l){
             //   System.out.println("Case s -");
                int k= i+1,m=j-1; i--;j++;
                while (i>0 && j<board_size) {
                    if (board[i][j] == other_color) {i--;j++;continue;}
                    else if (board[i][j] == 0) break;
                    else if (board[i][j] == color) {
                        while (k >= i) {
                            board[k][m] = color;
                            k--;m++;
                        }
                        Node n = new Node(board,board_size,this.color);
                        return n;
                    }
                   // i--;
                }
            }
            else {
             //   System.out.println("Case s +");
                int k= i-1,m=j+1; i++;j--;
                while (i<board_size && j>0) {
                    if (board[i][j] == other_color) {i++;j--;continue;}
                    else if (board[i][j] == 0) break;
                    else if (board[i][j] == color) {
                        while (k <= i) {
                            board[k][m] = color;
                            k++;m--;
                        }
                        Node n = new Node(board,board_size,this.color);
                        return n;
                    }
                 //   i++;
                }
            }
        }
     //   System.out.println("returning null");
        return null;
    }
    ArrayList<Node> getSucessor(int i,int j,int c,int oc){
     //   System.out.println("getSuccessor func index: i="+i+" j="+j +" color="+ c);
        ArrayList<Node> successor = new ArrayList<Node>();
        if((i-1) >0 && (j-1)>0) {
            if (board[i - 1][j - 1] == 0) {
                Node n = callAlligned(this.board, i, j,i-1,j-1, 'p', false, c, oc);
                if (n != null) successor.add(n);
            }
        }
        if((i-1)>0) {
            if (board[i - 1][j] == 0 && (i - 1 > 0)) {
                Node n = callAlligned(this.board, i, j, i-1,j,'u', false, c, oc);
                if (n != null) successor.add(n);
            }
        }
        if((i-1)>0 && (j+1)<8) {
            if (board[i - 1][j + 1] == 0) {
                Node n = callAlligned(this.board, i, j,i-1,j+1, 's', true, c, oc);
                if (n != null) successor.add(n);
            }
        }
        if((i<8) && (j-1)>0) {
            if (board[i][j - 1] == 0 && (i < 8) && (j - 1) > 0) {
                Node n = callAlligned(this.board, i, j,i,j-1, 'l', false, c, oc);
                if (n != null) successor.add(n);
            }
        }
        if((j+1)<8) {
            if (board[i][j + 1] == 0 && (j + 1) < 8) {
                Node n = callAlligned(this.board, i, j, i,j+1,'l', true, c, oc);
                if (n != null) successor.add(n);
            }
        }
        if((i+1)<8) {
            if (board[i + 1][j - 1] == 0 && (i + 1) < 8) {
                Node n = callAlligned(this.board, i, j,i+1,j-1, 's', false, c, oc);
                if (n != null) successor.add(n);
            }
        }
        if((i+1)<8) {
            if (board[i + 1][j] == 0 && (i + 1) < 8) {
                Node n = callAlligned(this.board, i, j,i+1,j, 'u', true, c, oc);
                if (n != null) successor.add(n);
            }
        }
        if((i+1)<8 && (j+1)<8) {
            if (board[i + 1][j + 1] == 0 && (i + 1) < 8 && (j + 1) < 8) {
                Node n = callAlligned(this.board, i, j, i+1,j+1,'p', true, c, oc);
                if (n != null) successor.add(n);
            }
        }
        return successor;
    }
    boolean check_valid(int board[][],int i,int j,int c,int oc){
        int k = i,m= j;
        if((i-1) >0 && (j-1)>0) {
            if (board[i - 1][j - 1] == oc) {
                i--; j--;
                while(i>0 && j>0){
                    if(board[i][j] == oc) {i--; j--;continue;}
                  //  if(i == 0) return false;
                    else if(board[i][j] == 0) break;
                    else if(board[i][j] == c) return true;
                    i--; j--;
                }
            }
        }
        i=k;j=m;
        if((i-1)>0) {
            if (board[i - 1][j] == oc) {
                i--;
                while(i>0){
                    if(board[i][j] == oc) {i--;continue;}
                  //  if(i == 0) return false;
                    else if(board[i][j] == 0) break;
                    else if(board[i][j] == c) return true;
                    i--;
                }
             //   return false;
            }
        }
        i=k;j=m;
        if((i-1)>0 && (j+1)<8) {
            if (board[i - 1][j + 1] == oc) {
                i--;j++;
               while(i >0 && j<8){
                  // i--;j++;
                   if(board[i][j] == oc) {i--;j++;continue;}
                   else if(board[i][j] == 0) break;
                   else if(board[i][j] == c) return true;
                   i--;j++;
               }
             //  return false;
            }
        }
        i=k;j=m;
        if((i<8) && (j-1)>0) {
            if (board[i][j - 1] == oc && (i < 8) && (j - 1) > 0) {
                j--;
                while(i >0 && j>0){
                  //  j--;
                    if(board[i][j] == oc) {j--;continue;}
                    else if(board[i][j] == 0) break;
                    else if(board[i][j] == c) return true;
                    j--;
                }
             //   return false;
            }
        }
        i=k;j=m;
        if((j+1)<8) {
            if (board[i][j + 1] == oc && (j + 1) < 8) {
                j++;
                while(i >0 && j<8){
                  //  j++;
                    if(board[i][j] == oc) {j++;continue;}
                    else if(board[i][j] == 0) break;
                    else if(board[i][j] == c) return true;
                    j++;
                }
             //   return false;
            }
        }
        i=k;j=m;
        if((i+1)<8 && (j-1)>0) {
            if (board[i + 1][j - 1] == oc && (i + 1) < 8) {
                i++;j--;
                while(i <8 && j>0){
                 //   System.out.println("s +  i="+ i+" j="+ j+ " "+ board[i][j]);
                   // i++;j--;
                    if(board[i][j] == oc) {i++;j--;continue;}
                    else if(board[i][j] == 0) break;
                    else if(board[i][j] == c) return true;
                    i++;j--;
                }
             //   return false;
            }
        }
        i=k;j=m;
        if((i+1)<8) {
            if (board[i + 1][j] == oc && (i + 1) < 8) {
                i++;
                while(i <8 && j<8){
                  //  i++;
                    if(board[i][j] == oc) {i++;continue;}
                    else if(board[i][j] == 0) break;
                    else if(board[i][j] == c) return true;
                    i++;
                }
             //   return false;
            }
        }
        i=k;j=m;
        if((i+1)<8 && (j+1)<8) {
            if (board[i + 1][j + 1] == oc && (i + 1) < 8 && (j + 1) < 8) {
                i++;j++;
                while(i <8 && j<8){
                   // i++;j++;
                    if(board[i][j] == oc) {i++;j++;continue;}
                    else if(board[i][j] == 0) break;
                    else if(board[i][j] == c) return true;
                    i++;j++;
                }
             //   return false;
            }
        }
        i=k;j=m;
        return false;
    }
    int getMobility(int board[][],int c,int oc){
        int cost =0;
        for(int i=0;i<board_size;i++){
            for(int j=0;j<board_size;j++){
                if(board[i][j] == 0 ){
                 //   System.out.println("Mobility i="+i+" j= "+j);
                    if(check_valid(board,i,j,c,oc)==true) {
                        cost++;
                    //    System.out.println("i= "+i+" j=" +j +" cost="+cost);
                    }
                }
            }
        }
        return cost;
    }

    ArrayList<Node> getNeighbours(int c,int oc){
      //  System.out.println("getNeighbours func");
        for(int i=0;i<board_size;i++){
            for (int j=0;j<board_size;j++){
                if(board[i][j] == oc) this.Neighbours.addAll(getSucessor(i,j,c,oc));
            }
        }
        for(int i=0;i<this.Neighbours.size();i++){
            this.Neighbours.get(i).cost = heuristics2(this.Neighbours.get(i).board,c,oc);
         //   System.out.println("Neighbour: "+i);
         //   print_board(this.Neighbours.get(i).board,this.board_size);
        }
     //   System.setOut(System.out);
        return this.Neighbours;
    }

    int getNeighboursCount(int c,int oc){
        //  System.out.println("getNeighbours func");
        for(int i=0;i<board_size;i++){
            for (int j=0;j<board_size;j++){
                if(board[i][j] == oc) this.Neighbours.addAll(getSucessor(i,j,c,oc));
            }
        }
      /*  for(int i=0;i<this.Neighbours.size();i++){
            this.Neighbours.get(i).cost = heuristics2(this.Neighbours.get(i).board,c,oc);
            //  System.out.println("Neighbour: "+i);
            // print_board(this.Neighbours.get(i).board,this.board_size);
        }  */
        //   System.setOut(System.out);
        return this.Neighbours.size();
    }
}
