package pokemon;

import java.awt.Dimension;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 Date: Nov 26/2021
 Name: Alexander Sotnikov
 Description: This program simulates a battle between two pokemons. On start program shows users pokemon and enemy
  	          pokemon stats in graphical components. User may either heal or battle their pokemon. If heal then 
 			  program restores pokemon to full health, and stores the updated value in a text file. If battle then
              the program shows a randomly generated simulation based on each of the pokemons move accuracy, and 
              move damage.
*/

public class PokemonGame {
  
  public static String enemy = "";
  
  //Main Function
  public static void main (String args[]) {

    PokemonGame pokemonGame = new PokemonGame();
		  
	Scanner in = new Scanner(System.in);
	
	//Initialization of String path and enemyPath which are the file names of the user and enemy
	//pokemon respectively.
	String path = "MyPokemon.txt";
	String enemyPath = "Enemy2.txt";
	
    //Random selection an enemy pokemon to fight:
      if (Math.round(Math.random()) == 0) {
        enemyPath = "Enemy1.txt";  
      } else {
        enemyPath = "Enemy2.txt";
      }
      
    //Declaration and creation of instances of class Pokemon called enemy and pokemon.
    Pokemon enemy = null;
    Pokemon pokemon = null;
    try {
    	pokemon = new Pokemon( path ); 
    	enemy = new Pokemon( enemyPath );
    } catch (IOException ex) {
        System.out.println("Could not load pokemon info; path = " + path + " and enemyPath " + enemyPath );
        System.out.println("Exception trace:");
        ex.printStackTrace();
        System.exit(0);
    }
    
    //Call getPokemonInfo method to receive user pokemon characteristics from the file, 
    //result is assigned to variable loadedPokemon.
    ArrayList<String> loadedPokemon = pokemon.getPokemonInfo();
    
    //Asks if user wants to heal or battle with pokemon.
    System.out.println("Would you like to Heal or battle with the Pokemon?");
    System.out.println("1. Heal");
    System.out.println("2. Battle");
    String battleOrHeal = in.nextLine();
/*   
* Healing or battling:
*_____________________
*/
    //If user chooses to heal the pokemon
    if (battleOrHeal.equalsIgnoreCase("heal") || battleOrHeal.equals("1")) {
      pokemon.setCurrHealth(pokemon.getMaxHealth()); //Updating current health of pokemon with maximum health    
      try {
    	pokemon.savePokemonInfo(); //Saving updated info about pokemon to file.
      } catch (IOException ex) { //In case exception is thrown:
        System.out.println("Could not update pokemon info; path = " + path);
        System.out.println("Exception trace:");
        ex.printStackTrace();
        System.exit(0);
      }
      //Says parting message:
      System.out.println("Pokemon restored to full health. Current health: " + pokemon.getCurrHealth() + "\n");
      System.out.println("Thank you for hopping by, we hope to see you here next time.");
    }
    
    //If user chooses to battle with the pokemon
    else if (battleOrHeal.equalsIgnoreCase("battle") || battleOrHeal.equals("2")) {
     
      
      try { //Calls method battle to simulate the battle between the pokemons.
    	pokemonGame.battle(pokemon, enemy);
      } catch (IOException ex) { //In case exception thrown
        System.out.println("Could not finish battle; enemyPath = " + enemyPath);
        System.out.println("Exception trace:");
        ex.printStackTrace();
        System.exit(0);
      }
    }
  }
  
  //Method which controls the battle simulation:
  public void battle (Pokemon myPokemon, Pokemon enemy) throws IOException{
    
	boolean hopper = false; //Variable used to determine whose turn it is during the battle
	
	// My Pokemon Stats:
	int currHealth = myPokemon.getCurrHealth();
	int maxHealth = enemy.getMaxHealth();
	String name = myPokemon.getPokemonName();
	String moveName = myPokemon.getMoveName();
	int moveDamage = myPokemon.getMoveDamage();
	double moveAccuracy = myPokemon.getMoveAccuracy()/100;
	
	// Enemy   Pokemon Stats:
	int currEnemyHealth = enemy.getCurrHealth();
	int maxEnemyHealth = enemy.getMaxHealth();
	String enemyName = enemy.getPokemonName();
	String enemyMoveName = enemy.getMoveName();
	int enemyMoveDamage = enemy.getMoveDamage();
	double enemyMoveAccuracy = enemy.getMoveAccuracy()/100;
	
	// Decides which pokemon moves first based on pokemon speed.
    if (myPokemon.getSpeed() > enemy.getSpeed()) {
      hopper = true;
    }
    
    // Used to determine when to print out pokemon health
    boolean chooser = true;
    
    // The actual battle simulation:
    while (currHealth > 0 && currEnemyHealth > 0) { //Battle goes on until one of the pokemons health drops below zero.
    	
      //Prints out pokemon name and current health
      if (chooser) {
    	System.out.print("\n");
        System.out.println(name + " " + currHealth + "/" + maxHealth + "\t" + enemyName + " " + currEnemyHealth + "/" + maxEnemyHealth + "\n");
      }
    	
      // Player moves
      if (hopper) {
        double randomN = Math.random(); //Variable used to decide if pokemon missed or not based on its move accuracy.
        if(randomN < moveAccuracy) { //If this condition fullfilled, then pokemon hit.
          currEnemyHealth -= moveDamage;
          System.out.println(name + " hit " + enemyName + " with " + moveName + "."); //Prints out if pokemon successfully hit.
        } 
        else { //If this condition fullfilled, then pokemon missed.
          System.out.println(name + " missed!"); //Prints out which pokemon missed.
        }
        hopper = false; //This assignment allows for the other pokemon to make its move.
      }
      // Enemy moves:
      else {
    	double randomN = Math.random(); //Variable used to decide if pokemon missed or not based on its move accuracy.
    	if(randomN < enemyMoveAccuracy) { //If this condition fullfilled, then pokemon hit.
          currHealth -= enemyMoveDamage;
          System.out.println(enemyName + " hit " + name + " with " + enemyMoveName + "."); //Prints out if pokemon successfully hit.
    	} else { //If this condition fullfilled, then pokemon missed.
    	  System.out.println(enemyName + " missed!"); //Prints out if pokemon missed.
    	}  
    	hopper = true;  //This assignment allows for the other pokemon to make its move.
      }
      //chooser variable decides if it is time to print pokemon health:
      if (chooser) chooser = false;
      else chooser = true;
    }
    
    //Says who won:
	if (currHealth <= 0) {
	  System.out.println();
	  System.out.println(name + " fainted! \n" + enemyName + " won!");
	}
	else {
      System.out.println(enemyName + " fainted! \n" + name + " won!");
	}
	
	//Saves updated health of players pokemon to text file.
	myPokemon.setCurrHealth(currHealth);
    myPokemon.savePokemonInfo();
  }
}