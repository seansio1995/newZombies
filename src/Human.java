//Chufan Xiao
//Jeremy Little
import java.awt.Rectangle;

public class Human
{
  private double x;
  private double y;
  private Rectangle hitbox;
  private double xMouse;
  private double yMouse;
  private double speed;
  private int bombs;
  private int frame;
  private final int HITBOXWIDTH = 30;
  private final int HITBOXHEIGHT = 40;
  
  public Human(double x, double y, double xMouse, double yMouse, double speed)
  {
    this.x = x;
    this.y = y;
    this.xMouse = xMouse;
    this.yMouse = yMouse;
    this.speed = speed;
    this.bombs = 3;
    this.frame = 0;
    this.hitbox = new Rectangle();
    this.hitbox.height = 40;
    this.hitbox.width = 30;
    resetHitbox();
  }
  
  public void move(float elapsedTime)
  {
    double xMove = this.speed * elapsedTime;
    double yMove = this.speed * elapsedTime;
    if ((int)this.x == (int)this.xMouse) {
      xMove = 0.0D;
    }
    if ((int)this.y == (int)this.yMouse) {
      yMove = 0.0D;
    }
    if (this.xMouse < this.x) {
      xMove *= -1.0D;
    }
    if (this.yMouse < this.y) {
      yMove *= -1.0D;
    }
    this.x += xMove;
    this.y += yMove;
    resetHitbox();
    this.frame += 1;
    if (this.frame == 7) {
      this.frame = 0;
    }
  }
  
  public void resetHitbox()
  {
    this.hitbox.x = ((int)this.x - 15);
    this.hitbox.y = ((int)this.y - 20);
  }
  
  public boolean collidesWith(Rectangle rect)
  {
    return this.hitbox.intersects(rect);
  }
  
  public boolean useBomb()
  {
    if (this.bombs <= 0) {
      return false;
    }
    this.bombs -= 1;
    return true;
  }
  
  public int addBomb()
  {
    this.bombs += 1;
    return this.bombs;
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
  
  public Rectangle getHitbox()
  {
    return this.hitbox;
  }
  
  public void setHitbox(Rectangle hitbox)
  {
    this.hitbox = hitbox;
  }
  
  public double getXMouse()
  {
    return this.xMouse;
  }
  
  public void setXMouse(double x)
  {
    this.xMouse = x;
  }
  
  public double getYMouse()
  {
    return this.yMouse;
  }
  
  public void setYMouse(double y)
  {
    this.yMouse = y;
  }
  
  public double getSpeed()
  {
    return this.speed;
  }
  
  public void setSpeed(double speed)
  {
    this.speed = speed;
  }
  
  public int getBombs()
  {
    return this.bombs;
  }
  
  public void setBombs(int bombs)
  {
    this.bombs = bombs;
  }
  
  public int getFrame()
  {
    return this.frame;
  }
  
  public void setFrame(int frame)
  {
    this.frame = frame;
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
