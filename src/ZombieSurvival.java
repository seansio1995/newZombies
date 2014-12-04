//Chufan Xiao  cx7ga
//jeremy little  jdl2fr
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
* ZombieSurvival.java
* 
* The ZombieSurvival is the field of play for the game. It passes messages
* between the Human and the Zombies. It also picks up the commands from the
* mouse and does the appropriate action. This is the class that will have the
* main method to start the game.
* 
* @authors
* @compid
* @lab
*/
public class ZombieSurvival
{
  private SimpleCanvas canvas;
  private InfoFrame output; // The InfoFrame that you use for output instead of System.out
  private final int BOARDHEIGHT = 1000;
  private final int BOARDWIDTH = 1000;
  private ArrayList<Zombie> zombieList = new ArrayList();
  private Human player;
  private ArrayList<Rectangle> obstacleList = new ArrayList();
  private boolean gameOver = false;
  private long score = 0L;
  private int startingZombies = 4;
  private Random rand = new Random();
  private boolean bombDropped = false;
  private int bombDropCounter = 0;
  private Clip sound=null;
  
  public ZombieSurvival()
    throws Exception
  {
    this.canvas = new SimpleCanvas(1000, 1000, this);
    this.output = new InfoFrame(this);
    this.player = new Human(250.0D, 250.0D, 250.0D, 250.0D, 20.0D);
    this.output.println("New Player Created!  You start with 3 bombs!");
    for (int i = 0; i < this.startingZombies; i++) {
      this.zombieList.add(new Zombie(this.rand.nextInt(500), this.rand.nextInt(500), 
        this.player, 10.0D));
    }
    loadObstacles("course.csv");
  }
  
  public void loadObstacles(String filename)
    throws Exception
  {
    Scanner file = new Scanner(new File(filename));
    while (file.hasNextLine())
    {
      String[] line = file.nextLine().split(",");
      this.obstacleList.add(new Rectangle(Integer.parseInt(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2]), Integer.parseInt(line[3])));
    }
  }
  
  public void mouseAction(float x, float y, int button)
  {
    if (button == -1)
    {
      this.player.setXMouse(x);
      this.player.setYMouse(y);
    }
    if (button == 1) {
      detonateBomb();
      playSound("bomb.wav");
      
    }
    
    if(button==3)
    {
    	killThemAllBomb();
    	playSound("bomb.wav");
    }
  }
  
  public void killThemAllBomb()
  {
	  if(!this.player.useBomb())
	  {
		  this.output.println("Out of bombs!");
		  return;
	  }
	  zombieList.removeAll(zombieList);
	  this.output.println("You have killed them all!");
	  this.bombDropped=true;
	  gameOver=true;
  }
  
  public void detonateBomb()
  {
    if (!this.player.useBomb())
    {
      this.output.println("Out of bombs!");
      return;
    }
    double humanx = this.player.getX();
    double humany = this.player.getY();
    for (int i = 0; i < this.zombieList.size(); i++)
    {
      double zombiex = ((Zombie)this.zombieList.get(i)).getX();
      double zombiey = ((Zombie)this.zombieList.get(i)).getY();
      if (Math.sqrt((zombiex - humanx) * (zombiex - humanx) + (zombiey - humany) * (zombiey - humany)) < 80.0D)
      {
        this.zombieList.remove(i);
        i--;
      }
    }
    this.output.println("BOOM! You dropped a bomb!  You now have " + this.player.getBombs() + 
      " bombs!");
    this.bombDropped = true;
  }
  
  public void draw(Graphics2D g, float elapsedTime)
  {
    elapsedTime *= 3.0F;
    if (this.gameOver)
    {
      elapsedTime = 0.0F;
      
      this.canvas.drawHuman(g, this.player);
      for (Rectangle o : this.obstacleList) {
        this.canvas.drawObstacle(g, o);
      }
      for (int i = 0; i < this.zombieList.size(); i++)
      {
        Zombie p = (Zombie)this.zombieList.get(i);
        
        this.canvas.drawZombie(g, (Zombie)p);
      }
      return;
    }
    this.score += 1L;
    if (this.score % 5000L == 0L) {
      this.zombieList.add(new Zombie(this.rand.nextInt(500), this.rand.nextInt(500), 
        this.player, 10.0D));
    }
    if (this.score % 50000L == 0L) {
      this.output.println("You've earned another bomb!  You now have " + this.player.addBomb() + 
        "!");
    }
    this.player.move(elapsedTime);
    for (Object p = this.obstacleList.iterator(); ((Iterator)p).hasNext();)
    {
      Rectangle o = (Rectangle)((Iterator)p).next();
      this.canvas.drawObstacle(g, o);
      this.player.avoidCollision(o);
    }
    this.canvas.drawHuman(g, this.player);
    for (int i = 0; i < this.zombieList.size(); i++)
    {
      Zombie p = (Zombie)this.zombieList.get(i);
      p.move(elapsedTime);
      for (int j = 0; j < this.zombieList.size(); j++)
      {
        if (i != j) {
          p.avoidCollision(((Zombie)this.zombieList.get(j)).getHitbox());
        }
        for (Rectangle o : this.obstacleList) {
          p.avoidCollision(o);
        }
      }
      if (p.collidesWith(this.player.getHitbox()))
      {
        this.output.println("XXXXXXXXXXXXXXXXXXXXXXXOOOOOOOOOOOOOOOOOOO");
        this.output.println("");
        this.output.println("Game Over!");
        this.output.println("Your Score: " + this.score / 100L);
        this.gameOver = true;
      }
      this.canvas.drawZombie(g, p);
    }
    if (this.bombDropped)
    {
      this.canvas.drawBoom(g, (int)this.player.getX(), (int)this.player.getY());
      
      this.bombDropCounter += 1;
      if (this.bombDropCounter == 200)
      {
        this.bombDropped = false;
        this.bombDropCounter = 0;
      }
    }
  }
  
  public void playSound(String filename) {
		try {

			sound = AudioSystem.getClip();
			sound.open(AudioSystem.getAudioInputStream(new File(filename)));

			sound.start();
		} catch (Exception exc) {
			System.out.println("Error opening sound file!");
			System.exit(0);
		}
	}
  
  public static void main(String[] args)
    throws Exception
  {
    ZombieSurvival simulator = new ZombieSurvival();
    simulator.play();
  }
  
  public void play()
  {
    this.canvas.setupAndDisplay();
  }
}
