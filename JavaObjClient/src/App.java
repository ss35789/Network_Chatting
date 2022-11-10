import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;

public class App extends JFrame{

    private JPanel mainPanel;
    private JButton userButton;
    private JPanel sideBarPanel;
    private JButton chattingRoomButton;
    private JLabel myName;

    private void ChattingButton_Click(JFrame App){
       //chattingPanel·Î ÀÌµ¿

        JFrame cha = new Chatting();
        App.setVisible(false);
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
        mainPanel = new JPanel();

    }
    public App(){

        chattingRoomButton.addActionListener(event -> ChattingButton_Click(this));
        chattingRoomButton.setIcon(new ImageIcon("icon1.jpg"));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.setVisible(true);

    }



    public static void main(String[] args) {
        JFrame frame = new App();

    }
}
