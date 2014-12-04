//Chufan Xiao   cx7ga
//Jeremy little jdl2fr
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class SimpleCanvas
  extends JPanel
  implements MouseListener, MouseMotionListener
{
  private int width;
  private int height;
  private long lastTime;
  private ZombieSurvival simulator;
  private BufferedImage[][] zombieSprites;
  private BufferedImage[][] humanSprites;
  private BufferedImage boomSprite;
  
  public SimpleCanvas(int width_, int height_, ZombieSurvival simulator_)
  {
    this.width = width_;
    this.height = height_;
    this.simulator = simulator_;
    this.lastTime = -1L;
    

    this.humanSprites = loadHumanSprites("sprite.png");
    this.zombieSprites = loadZombieSprites("zombie.png");
    try
    {
      this.boomSprite = ImageIO.read(new File("boom.png"));
    }
    catch (Exception e)
    {
      System.err.println("Cannot load images!");
    }
  }
  
  public BufferedImage[][] loadHumanSprites(String filename)
  {
    BufferedImage[][] spriteArray = new BufferedImage[4][8];
    BufferedImage spriteSheet = null;
    try
    {
      spriteSheet = ImageIO.read(new File(filename));
    }
    catch (Exception e)
    {
      System.err.println("Cannot load images!");
    }
    spriteArray[0][0] = spriteSheet.getSubimage(100, 80, 50, 80);
    
    spriteArray[1][0] = getFlippedImage(spriteSheet.getSubimage(100, 80, 50, 80));
    

    spriteArray[2][0] = spriteSheet.getSubimage(100, 230, 50, 80);
    

    spriteArray[3][0] = spriteSheet.getSubimage(100, 160, 50, 80);
    
    return spriteArray;
  }
  
  public BufferedImage[][] loadZombieSprites(String filename)
  {
    BufferedImage[][] spriteArray = new BufferedImage[4][8];
    BufferedImage spriteSheet = null;
    try
    {
      spriteSheet = ImageIO.read(new File(filename));
    }
    catch (Exception e)
    {
      System.err.println("Cannot load images!");
    }
    spriteArray[0][0] = spriteSheet.getSubimage(3, 100, 75, 100);
    










    spriteArray[1][0] = spriteSheet.getSubimage(3, 300, 75, 100);
    

    spriteArray[2][0] = spriteSheet.getSubimage(3, 3, 75, 100);
    

    spriteArray[3][0] = spriteSheet.getSubimage(3, 200, 75, 100);
    
    return spriteArray;
  }
  
  public void setupAndDisplay()
  {
    JFrame f = new JFrame();
    f.setDefaultCloseOperation(3);
    f.add(new JScrollPane(this));
    f.setSize(this.width, this.height);
    f.setLocation(100, 100);
    f.setVisible(true);
    addMouseListener(this);
    addMouseMotionListener(this);
  }
  
  protected void paintComponent(Graphics g)
  {
    boolean first = this.lastTime == -1L;
    long elapsedTime = System.nanoTime() - this.lastTime;
    this.lastTime = System.nanoTime();
    g.setColor(Color.black);
    g.fillRect(0, 0, this.width, this.height);
    g.setColor(Color.white);
   
    this.simulator.draw((Graphics2D)g, first ? 0.0F : (float)elapsedTime / 1.0E+009F);
    
    repaint();
  }
  
  public void drawDot(Graphics2D g, double x, double y, Color color)
  {
    g.setColor(color);
    g.fillOval((int)(x + 0.5D), (int)(y + 0.5D), 8, 8);
  }
  
  public void drawObstacle(Graphics2D g, Rectangle o)
  {
    g.setColor(Color.white);
    g.fill(o);
  }
  
  public void drawZombie(Graphics2D g, Zombie z)
  {
    if (Math.abs(z.getHuman().getX() - z.getX()) > Math.abs(z.getHuman().getY() - z.getY()))
    {
      if (z.getHuman().getX() > z.getX()) {
        g.drawImage(this.zombieSprites[0][0], (int)z.getX() - 38, (int)z.getY() - 50, null);
      } else {
        g.drawImage(this.zombieSprites[1][0], (int)z.getX() - 38, (int)z.getY() - 50, null);
      }
    }
    else if (z.getHuman().getY() < z.getY()) {
      g.drawImage(this.zombieSprites[2][0], (int)z.getX() - 38, (int)z.getY() - 50, null);
    } else {
      g.drawImage(this.zombieSprites[3][0], (int)z.getX() - 38, (int)z.getY() - 50, null);
    }
  }
  
  public void drawHuman(Graphics2D g, Human h)
  {
    if (Math.abs(h.getXMouse() - h.getX()) > Math.abs(h.getYMouse() - h.getY()))
    {
      if (h.getXMouse() > h.getX()) {
        g.drawImage(this.humanSprites[0][0], (int)h.getX() - 25, (int)h.getY() - 40, null);
      } else {
        g.drawImage(this.humanSprites[1][0], (int)h.getX() - 25, (int)h.getY() - 40, null);
      }
    }
    else if (h.getYMouse() < h.getY()) {
      g.drawImage(this.humanSprites[2][0], (int)h.getX() - 25, (int)h.getY() - 40, null);
    } else {
      g.drawImage(this.humanSprites[3][0], (int)h.getX() - 25, (int)h.getY() - 40, null);
    }
  }
  
  public void drawBoom(Graphics2D g, int x, int y)
  {
    g.drawImage(this.boomSprite, x - 75, y - 75, null);
  }
  
  public BufferedImage getFlippedImage(BufferedImage bi)
  {
    BufferedImage flipped = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
    AffineTransform tran = AffineTransform.getTranslateInstance(bi.getWidth(), 0.0D);
    AffineTransform flip = AffineTransform.getScaleInstance(-1.0D, 1.0D);
    tran.concatenate(flip);
    
    Graphics2D g = flipped.createGraphics();
    g.setTransform(tran);
    g.drawImage(bi, 0, 0, null);
    g.dispose();
    
    return flipped;
  }
  
  public void mouseMoved(MouseEvent e)
  {
    this.simulator.mouseAction(e.getX(), e.getY(), -1);
  }
  
  public void mouseClicked(MouseEvent e)
  {
    this.simulator.mouseAction(e.getX(), e.getY(), e.getButton());
  }
  
  public void mouseEntered(MouseEvent e) {}
  
  public void mouseExited(MouseEvent e) {}
  
  public void mousePressed(MouseEvent e) {}
  
  public void mouseReleased(MouseEvent e) {}
  
  public void mouseDragged(MouseEvent arg0) {}
}
