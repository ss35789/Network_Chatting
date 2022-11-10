import javax.swing.*;

public class Chatting extends JFrame{
    private JPanel chattingPanel;
    private JPanel mainPanel;
    private JButton userButton;
    private JPanel sideBarPanel;
    private JButton chattingRoomButton;
    private JLabel myName;
    private String username;
    private String ip_addr;
    private String port_no;
    private void UserButton_Click(JFrame Chatting){
        //chattingPanel·Î ÀÌµ¿

        JFrame cha = new App(username, ip_addr, port_no);
        Chatting.setVisible(false);
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
        mainPanel = new JPanel();

    }
    public Chatting(){}
    public Chatting(String username, String ip_addr,String port_no){
        this.username=username;
        this.ip_addr=ip_addr;
        this.port_no=port_no;
        myName.setText(username);
        userButton.addActionListener(event -> UserButton_Click(this));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.setVisible(true);
    }

}
