import javax.swing.*;

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
    public App(String title){
        super(title);
        chattingRoomButton.addActionListener(event -> ChattingButton_Click(this));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.setVisible(true);

    }



    public static void main(String[] args) {
        JFrame frame = new App("NetWork Chatting");

    }
}
