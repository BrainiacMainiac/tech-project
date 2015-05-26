public void generateDFS(int rows, int cols) {
    mazeArray = new String[rows][cols];
    for(int i = 0; i < mazeArray.length; i++){
        for(int j = 0; j < mazeArray[i].length; j++){
            mazeArray[i][j] = "/";
        }
    }
}
public void DFS(int x, int y){
    ArrayList li=new ArrayList();
    li.add("left");
    li.add("right");
    li.add("up");
    li.add("down");
    Collections.shuffle(li);
    String first = li.get(0);
    if (first.equals("left"))
}
    