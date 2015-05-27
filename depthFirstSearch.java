public void generateDFS(int rows, int cols) {
    mazeArray = new String[rows][cols];
    for(int i = 0; i < mazeArray.length; i++){
        for(int j = 0; j < mazeArray[i].length; j++){
            mazeArray[i][j] = "/";
        }
    }
    DFS(1,1);
    mazeArray[1][1]="+";
    mazeArray[rows-2][cols-2]="-";
}
public void DFS(int row, int col) {
    ArrayList li = new ArrayList();
    li.add("left");
    li.add("right");
    li.add("up");
    li.add("down");
    Collections.shuffle(li);
    for (int i=0; i<4; i++) {
    String test = li.get(i);
    if (first.equals("left")) {
        if (col!=1 && mazeArray[row][col].equals("/")) {
            mazeArray[row][col-2]=".";
            mazeArray[row][col-1]=".";
            DFS(row,col-2);
        }
    }
    if (first.equals("right")) {
        if (col!=mazeArray[0].length-2 && mazeArray[row][col].equals("/")) {
            mazeArray[row][col+2]=".";
            mazeArray[row][col+1]=".";
            DFS(row,col+2);
        }
    }
    if (first.equals("up")) {
        if (row!=1 && mazeArray[row][col].equals("/")) {
            mazeArray[row-2][col]=".";
            mazeArray[row-1][col]=".";
            DFS(row-2,col);
        }
    }
    if (first.equals("down")) {
        if (row!=mazeArray.length-2 && mazeArray[row][col].equals("/")) {
            mazeArray[row+2][col]=".";
            mazeArray[row+1][col]=".";
            DFS(row+2,col);
        }
    }
}
}
    
