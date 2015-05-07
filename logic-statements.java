class LogicStatements {
  public static void main(String[] argt){
    System.out.println("And statements:");
    for(int i = 1; i < 5; i++){
      if(i == 1){
        boolean a = true;
        boolean b = true;
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
    };
    System.out.println("Or statements:");
    for(int i = 1; i < 5; i++){
      if(i == 1){
        boolean a = true;
        boolean b = true;
        System.out.println(a || b);
      } else if(i == 2) {
        boolean a = true;
        boolean b = false;
        System.out.println(a || b);
      } else if(i == 3){
        boolean a = false;
        boolean b = true;
        System.out.println(a || b);
      } else {
        boolean a = false;
        boolean b = false;
        System.out.println(a || b);
      }
    }
  }
}
