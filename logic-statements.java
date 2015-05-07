class LogicStatements {
  public static void main(String[] argt){
    // and statements
    for(int i = 1; i > 4; i++){
      if(i == 1){
        boolean a = true;
        boolean b = false;
        System.out.println(a && b);
      } else if(i == 2) {
        boolean a = true;
        boolean b = false;
        System.out.println(a && b);
      } else if(i == 3){
        boolean a = false;
        boolean b = true;
        System.out.println(a && b);
      } else {
        boolean a = false;
        boolean b = false;
        System.out.println(a && b);
      }
    }
  }
}
