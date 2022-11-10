import javax.swing.*;

public class App extends JFrame{

    private JPanel mainPanel;
    private JButton userButton;
    private JPanel sideBarPanel;
    private JButton chattingRoomButton;
    private JLabel myName;


    private void createUIComponents() {
        // TODO: place custom component creation code here
        mainPanel = new JPanel();

    }
    public App(String title){
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

    }

    public static void main(String[] args) {
        JFrame frame = new App("NetWork Chatting");
        frame.setVisible(true);
    }
}
