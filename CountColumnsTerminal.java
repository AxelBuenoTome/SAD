import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

// Main class
public class CountColumnsTerminal {
  // Main driver method
  public static void main(String[] args) throws InterruptedException {
    try {
      // Use Runtime.getRuntime.exec on a separate process
      Process Demo_Process = Runtime.getRuntime().exec("tput cols");

      // Get the output in the console

      String Output_Line;
      InputStreamReader Input_Stream_Reader = new InputStreamReader(Demo_Process.getInputStream());
      BufferedReader Buffered_Reader = new BufferedReader(Input_Stream_Reader);
      while ((Output_Line = Buffered_Reader.readLine()) != null) {
        System.out.println(Output_Line);
      }

      Input_Stream_Reader = new InputStreamReader(Demo_Process.getErrorStream());
      Buffered_Reader = new BufferedReader(Input_Stream_Reader);
      while ((Output_Line = Buffered_Reader.readLine()) != null) {
        System.out.println(Output_Line);
      }
      // Wait for the process to complete
      Demo_Process.waitFor();

    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}