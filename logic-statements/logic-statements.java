// Class declaration
class LogicStatements {
  // Main method declaration
  public static void main(String[] argt){
    // AND Statement
    System.out.println("AND statements:");
    // For loop to input all possible combinations
    // Runs 4 times iterating a different if/else-if each time
    for(int i = 1; i < 5; i++){
      // First iteration
      if(i == 1){
        // Declaring booleans
        boolean a = true;
        boolean b = true;
        // Printing the comparison
        System.out.println(a && b);
        // Second iteration
      } else if(i == 2) {
        boolean a = true;
        boolean b = false;
        System.out.println(a && b);
        // Third iteration
      } else if(i == 3){
        boolean a = false;
        boolean b = true;
        System.out.println(a && b);
        // Fourth iteration - don't need an if because it is the last one to run
      } else {
        boolean a = false;
        boolean b = false;
        System.out.println(a && b);
      }
    };
    // All code from here on out is copied from the first FOR loop
    // So read the comments there
    // OR Statement
    System.out.println("OR statements:");
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
    // XOR Statement
    System.out.println("XOR statements:");
    for(int i = 1; i < 5; i++){
      if(i == 1){
        boolean a = true;
        boolean b = true;
        System.out.println(a ^ b);
      } else if(i == 2) {
        boolean a = true;
        boolean b = false;
        System.out.println(a ^ b);
      } else if(i == 3){
        boolean a = false;
        boolean b = true;
        System.out.println(a ^ b);
      } else {
        boolean a = false;
        boolean b = false;
        System.out.println(a ^ b);
      }
    }
  }
}
