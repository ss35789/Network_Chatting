import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChatRoomView extends JFrame{
    private JPanel mainPanel;
    private JPanel upperPanel;
    private JPanel lowerPanel;
    private JPanel RoomInfoPanel;
    private JTextArea textArea;
    private JLabel lblRoomName;
    private JLabel lblRoomUserNum;
    private JLabel btnUserList;
    private JTextArea ChatInput;
    private JLabel btnSubmit;
    private JLabel btnSendImg;
    
    //생성자 함수
    public ChatRoomView(String roomName,String userNum){
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 800);
        setContentPane(mainPanel);
        lblRoomName.setText(roomName);
        lblRoomUserNum.setText(userNum);


        btnSendImg.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //마우스 이벤트
                setContentPane(mainPanel);
                revalidate();
                repaint();
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        btnSubmit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //마우스 이벤트
                setContentPane(mainPanel);
                revalidate();
                repaint();
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public void setLblRoomName(String lblRoomName) {
        this.lblRoomName.setText(lblRoomName);
    }

    public void setLblRoomUserNum(String lblRoomUserNum) {
        this.lblRoomUserNum.setText(lblRoomUserNum);
    }
}
