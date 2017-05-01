import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;

public class Calculator {
  
  /*
   * Reads input file and prints it to console
   */
  public static void fileReader() {
    
    try {
      ArrayList<Integer> rectangle_sizes = new ArrayList<Integer>();
      String container_height = "nan";
      String rotations_allowed = "nan";
      int number_of_rectangles = 0;
      
      Scanner input = new Scanner(System.in);
      
      int i = 0;
      int j = 0; // counter for rectangles
      
      while (input.hasNextLine()) {
        String line = input.nextLine();
        if(i == 0){ // capture container height
          container_height = line.substring(line.indexOf(':') + 2);
        }
        else if(i == 1){ // capture rotations allowed
          rotations_allowed = line.substring(line.indexOf(':') + 2);
        }
        else if(i == 2){ // capture number of rectangles  
          number_of_rectangles = Integer.parseInt(line.substring(line.indexOf(':') + 2));
        }
        else{ // capture rectangle sizes
          String lengths[] = line.split(" ");
          
          int x_axis = Integer.parseInt(lengths[0].trim());
          int y_axis = Integer.parseInt(lengths[1].trim());
          
          rectangle_sizes.add(x_axis);
          rectangle_sizes.add(y_axis);
          j++;
          if(j == number_of_rectangles){
            break;
          }
        }
        i++;
      }
      
      input.close();
      Calculator.fileBuilder(container_height, rotations_allowed, number_of_rectangles, rectangle_sizes);
      
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  /*
   * Builds txt file using the output rules.
   */
  public static void fileBuilder(String container_height, String rotations_allowed, int number_of_rectangles, 
                                 ArrayList<Integer> rectangle_sizes){
    try{
      
      /* part 1: requirements */     
      System.out.println("container height: " + container_height);
      System.out.println("rotations allowed: "+ rotations_allowed);
      System.out.println("number of rectangles: " + number_of_rectangles);
      
      /* part 2: rectangle sizes */
      for(int i = 0; i < (2*number_of_rectangles); i = i + 2){ // take two since width + height
        System.out.println(rectangle_sizes.get(i)+ " " + rectangle_sizes.get(i+1));
      }
      
      /* part 3: placement of rectangles */
      System.out.println("placement of rectangles");
      
      /* Arraylist<int> should be build somewhere and used here in same way as rectangle size printing */
      for(int j = 0; j < (2*number_of_rectangles); j = j + 2){
        System.out.println(0 + " " + 0);
      }
      
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  public static void main(String[] args) {
    while (true) {
      fileReader();
    }
  } 
}