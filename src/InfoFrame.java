//Chufan Xiao
//Jeremy Little
import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

public class InfoFrame
{
  private JTextArea textArea = null;
  private JPanel panel;
  private JScrollPane scroll;
  private ZombieSurvival field;
  private JFrame frame;
  
  public InfoFrame(ZombieSurvival field_)
  {
    this.field = field_;
    this.frame = new JFrame();
    this.frame.setSize(400, 300);
    this.frame.setLocation(850, 100);
    this.frame.setDefaultCloseOperation(3);
    
    this.textArea = new JTextArea();
    this.textArea.setEditable(false);
    this.textArea.setLineWrap(true);
    this.textArea.setWrapStyleWord(true);
    
    DefaultCaret caret = (DefaultCaret)this.textArea.getCaret();
    caret.setUpdatePolicy(2);
    
    this.panel = new JPanel();
    this.panel.setLayout(new BorderLayout());
    
    this.scroll = new JScrollPane(this.textArea);
    this.panel.add(this.scroll, "Center");
    
    this.frame.getContentPane().add(this.panel);
    
    this.frame.setVisible(true);
  }
  
  public void println(String str)
  {
    this.textArea.append(str + '\n');
  }
  
  public void print(String str)
  {
    this.textArea.append(str);
  }
}
