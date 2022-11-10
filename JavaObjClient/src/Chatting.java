import javax.swing.*;

public class Chatting extends JFrame{
    private JPanel chattingPanel;
    private JPanel mainPanel;
    private JButton userButton;
    private JPanel sideBarPanel;
    private JButton chattingRoomButton;
    private JLabel myName;


    private void createUIComponents() {
        // TODO: place custom component creation code here
        mainPanel = new JPanel();

    }
    public Chatting(){

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        JFrame frame = new App("NetWork Chatting");

    }
}
