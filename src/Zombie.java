//Chufan Xiao cx7ga
//Jeremy Little  jdl2fr
import java.awt.Rectangle;

public class Zombie
{
  private double x;
  private double y;
  private Rectangle hitbox;
  private Human human;
  private double speed;
  private final int HITBOXWIDTH = 30;
  private final int HITBOXHEIGHT = 40;
  
  public boolean collidesWith(Rectangle rect)
  {
    return this.hitbox.intersects(rect);
  }
  
  public void move(float elapsedTime)
  {
    double xMove = this.speed * elapsedTime;
    double yMove = this.speed * elapsedTime;
    if (this.human.getX() < this.x) {
      xMove *= -1.0D;
    }
    if (this.human.getY() < this.y) {
      yMove *= -1.0D;
    }
    this.x += xMove;
    this.y += yMove;
    resetHitbox();
  }
  
  public void resetHitbox()
  {
    this.hitbox.x = ((int)this.x - 15);
    this.hitbox.y = ((int)this.y - 20);
  }
  
  public Zombie(double x, double y, Human human, double speed)
  {
    this.x = x;
    this.y = y;
    this.human = human;
    this.speed = speed;
    this.hitbox = new Rectangle();
    this.hitbox.height = 40;
    this.hitbox.width = 30;
    resetHitbox();
  }
  
  public double getX()
  {
    return this.x;
  }
  
  public void setX(double x)
  {
    this.x = x;
    resetHitbox();
  }
  
  public double getY()
  {
    return this.y;
  }
  
  public void setY(double y)
  {
    this.y = y;
    resetHitbox();
  }
  
  public Human getHuman()
  {
    return this.human;
  }
  
  public void setHuman(Human target)
  {
    this.human = target;
  }
  
  public double getSpeed()
  {
    return this.speed;
  }
  
  public void setSpeed(double speed)
  {
    this.speed = speed;
  }
  
  public Rectangle getHitbox()
  {
    return this.hitbox;
  }
  
  public void setHitbox(Rectangle hitbox)
  {
    this.hitbox = hitbox;
  }
  
  public void avoidCollision(Rectangle o)
  {
    if (this.hitbox.intersects(o))
    {
      Rectangle isection = this.hitbox.intersection(o);
      if (isection.width < isection.height)
      {
        if (isection.getCenterX() < this.x) {
          setX(this.x + isection.width);
        } else {
          setX(this.x - isection.width);
        }
      }
      else if (isection.getCenterY() < this.y) {
        setY(this.y + isection.height);
      } else {
        setY(this.y - isection.height);
      }
      resetHitbox();
    }
  }
}
