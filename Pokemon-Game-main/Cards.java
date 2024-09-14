package pokemon;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Cards extends JPanel{

  //Creates private instance of class pokemon
  private Pokemon pokie; 
  
  public Cards (Pokemon pokie) {
    this.pokie = pokie; 
  }
  
  //Initializes new fonts: 
  Font arial = new Font("Arial", Font.BOLD, 22);
  Font smolArial = new Font("Arial", Font.BOLD, 15);
  Font helevicta = new Font("Arial", Font.BOLD, 30);
  
  public void paintComponent (Graphics g) {
	//Declares and initializes variable of type array list and gives it a value of array list returned from getPokemonInfo method
    ArrayList<String> pokemonInfo = pokie.getPokemonInfo();
    //Creates new image
    Image growlithe = new ImageIcon("C:\\eclipse-workspace\\Pokemon\\src\\pokemon\\" + pokemonInfo.get(0) + ".png").getImage();
    
    //Draw the card:
    g.setColor(Color.RED);
    g.fillRect(0, 0, 310, 465);
    g.setColor(Color.ORANGE);
    g.fillRect(25, 25, 260, 415);
    g.setColor(Color.yellow);
	g.setFont(helevicta);
    g.drawString(pokemonInfo.get(0), 35, 50); //Writes the name of the pokemon
    g.drawImage(growlithe, 45, 60, 155, 218, null); //Draws the pokemon image
    g.setFont(smolArial); 
    
    //Writes out the type of pokemon:
    g.drawString("Type:", 240, 40);
    g.drawString(pokemonInfo.get(1), 245, 55);
    
    //Writes out the stats of the pokemon:
    g.setFont(smolArial);
    g.drawString("HP:" + pokemonInfo.get(3) + "/" + pokemonInfo.get(4), 210, 80);
    g.drawString("Speed: " + pokemonInfo.get(2), 80, 300);
    g.drawString("Move: " + pokemonInfo.get(5), 40, 350);
    g.drawString("Move power: " + pokemonInfo.get(6), 40, 375);
    g.drawString("Move accuracy: " + pokemonInfo.get(7), 40, 400);
  }
}
