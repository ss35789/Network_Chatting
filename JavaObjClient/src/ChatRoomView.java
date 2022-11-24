import javax.swing.*;

public class ChatRoomView extends JFrame{
    private JPanel mainPanel;
    private JPanel upperPanel;
    private JPanel lowerPanel;
    private JPanel RoomInfoPanel;
    private JTextArea textArea;
    private JLabel lblRoomName;
    private JLabel lblRoomUserNum;
    private JLabel btnUserList;
    private JLabel btnSubmit;
    private JTextArea textArea1;

    public ChatRoomView(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainPanel);

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

}
