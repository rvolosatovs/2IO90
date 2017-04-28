import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;

public class Calculator {
  
  /*
   * Reads input file and prints it to console
   * TODO: store in variables
   */
  public static void fileReader() {
    
    try {
      ArrayList<Integer> rectangle_sizes = new ArrayList<Integer>();
      String container_height = "nan";
      String rotations_allowed = "nan";
      int number_of_rectangles = 0;
      
      System.out.print("Enter the file name: "); 
      
      Scanner input = new Scanner(System.in);
      File file = new File(input.nextLine());
      
      input = new Scanner(file);
      
      int i = 0;
      
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
   * TODO: make variable assignments dynamic
   */
  public static void fileBuilder(String container_height, String rotations_allowed, int number_of_rectangles, 
                                 ArrayList<Integer> rectangle_sizes){
    
    try{
      System.out.print("Enter the output file name: "); 
      Scanner input = new Scanner(System.in);
      PrintWriter output_file = new PrintWriter(input.nextLine(), "UTF-8");
      
      /* part 1: requirements */     
      output_file.println("container height: " + container_height);
      output_file.println("rotations allowed: "+ rotations_allowed);
      output_file.println("number of rectangles: " + number_of_rectangles);
      
      /* part 2: rectangle sizes */
      for(int i = 0; i < (2*number_of_rectangles); i = i + 2){ // take two since width + height
        output_file.println(rectangle_sizes.get(i)+ " " + rectangle_sizes.get(i+1));
      }
      
      /* part 3: placement of rectangles */
      output_file.println("placement of rectangles");
      
      /* Arraylist<int> should be build somewhere and used here in same way as rectangle size printing */
      for(int j = 0; j < (2*number_of_rectangles); j = j + 2){
        output_file.println(0 + " " + 0);
      }
      
      output_file.close();
      
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  public static void main(String[] args) {
    fileReader(); 
  }
  
}