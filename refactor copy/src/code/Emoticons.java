import java.awt.*;
import javax.swing.JFrame;


public class Emoticons extends Canvas implements PinsetterObserver {

    private String currentImage;

    public Emoticons(int val)
    {
        if (val == 10)
            currentImage = "happy.gif";
        else if (val == 0)
            currentImage = "sad.gif";
    }

    public void paint(Graphics g) {
        Toolkit t = Toolkit.getDefaultToolkit();
        Image i = t.getImage(currentImage);
        g.drawImage(i, 0, 0, getWidth(), getHeight(), this);
    }

    public void show() {
        // MyCanvas m=new MyCanvas();
        JFrame frame = new JFrame();
        frame.add(this);
        frame.setSize(400, 400);
        frame.setVisible(true);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.err.println("Interrupted");
        }
        // this.hide();
        frame.hide();
    }
    public void receivePinsetterEvent(PinsetterEvent pe)
    {
        int count=pe.totalPinsDown();
        if(count==10 || count==0) {
            Emoticons emoji = new Emoticons(count);
            emoji.show();
        }
    }
}