package pokemon;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;


public class Pokemon extends JFrame {
  
  //Declaring and initializing variables: 
  private String path = ""; //Used to determine which file to get information from
  private ArrayList<String> loadedPokemon; //Arraylist which stores scanned info from text file
  private static final String BASE_PATH = "C:\\eclipse-workspace\\Pokemon\\src\\pokemon\\"; //The root directory of the program
  private static int xPosition = 0; //Variable is used to make sure the two cards don't overlap each other when they are spawned
  
  //Pokemon class constructor reads pokemon info from file, and creates new JFrame showing pokemon stats;
  public Pokemon (String pokemonPath) throws IOException {
	path = pokemonPath;
	loadedPokemon = this.loadPokemonInfo(); //Initializes arrayList loadedPokemon, to equal to ArrayList returned by method loadedPokemonInfo
	this.add(new Cards(this)); //Adds new component to the frame
    this.setTitle("Pokemon Card"); //Sets the title of the frame
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false); //Frames cannot be resized
    this.setVisible(true); //Makes sure frame is visible
    //Makes sure the pop ups are in different places.
    this.setBounds(xPosition, 0 , 200 , 500);
    xPosition += 350;
  }


  //Method reads pokemon info from text files
  private ArrayList<String> loadPokemonInfo() throws IOException {
	  
	// Initializing file reader and then a buffered reader:
	FileReader fileReader = new FileReader(BASE_PATH + path);
	BufferedReader inputReader =  new BufferedReader(fileReader);
	
	//Creating new Array List which stores info read from file
    ArrayList<String> array = new ArrayList<String>(); 
    String line = "";

    //Scans the file
    while ((line = inputReader.readLine()) != null) {
      array.add(line);
    }
    
    //Closes the file and buffered readers
    inputReader.close();
    fileReader.close();
    
    //Returns an array list with pokemon data
	return array;
  }
  
  //Method saves current pokemon data to file:
  public void savePokemonInfo() throws IOException{
	
    // Initializing file reader and then a buffered reader:
	FileWriter fileWriter = new FileWriter(BASE_PATH + path, false);
	BufferedWriter inputWriter = new BufferedWriter(fileWriter);
    
	//Rewrites every line of the existing pokemon file:
	for (int i = 0; i < loadedPokemon.size(); i++) {
      inputWriter.write(loadedPokemon.get(i));
	  inputWriter.newLine();
	}
	
	//Closes the file and buffered readers:
	inputWriter.close();
	fileWriter.close();

	return;
  }
  //Sets current health
  public void setCurrHealth(int currentHealth) {
    loadedPokemon.set( 3, "" + currentHealth );
  }
 
  public ArrayList<String> getPokemonInfo(){ //Method returns array with information about pokemon
	  
	return loadedPokemon;
  }
  
  public String getPokemonName () { //Gets the pokemon's name
	  
    return loadedPokemon.get(0);
  }
  public String getPokemonType () {//Gets the pokemon type
	  
    return loadedPokemon.get(1);
  }
  public int getSpeed (){//Gets the pokemon speed
	  
    return Integer.parseInt(loadedPokemon.get(2));
  }
  public int getCurrHealth () {//Gets the pokemon's current health
	  
    return Integer.parseInt(loadedPokemon.get(3));
  }
  public int getMaxHealth () {//Gets the selected pokemon's maximum health
	  
    return Integer.parseInt(loadedPokemon.get(4));
  }
  public String getMoveName () {//Gets the pokemon's move name
	  
    return loadedPokemon.get(5);
  }
  public int getMoveDamage () {//Gets the pokemon's move damage
	  
    return Integer.parseInt(loadedPokemon.get(6));
  }
  public double getMoveAccuracy () {//Gets the pokemon's move accuracy

    return Double.parseDouble(loadedPokemon.get(7));
  }
}