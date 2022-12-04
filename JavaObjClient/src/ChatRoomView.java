import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;

public class ChatRoomView extends JFrame {
    private JPanel mainPanel;
    private JPanel upperPanel;
    private JPanel lowerPanel;
    private JPanel RoomInfoPanel;
    private JTextArea textArea;
    private JLabel lblRoomName;
    private JLabel lblRoomUserNum;
    private JLabel btnUserList;
    private JTextField ChatInput;
    private JLabel btnSubmit;
    private JLabel btnSendImg;
    JavaObjClientMainViewController controller = JavaObjClientMainViewController.getInstance();
    
    //생성자 함수
    public ChatRoomView(String roomName,String userNum){
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 800);
        setContentPane(mainPanel);
        lblRoomName.setText(roomName);
        lblRoomUserNum.setText(userNum);
        
        //이미지 전송 버튼 액션 리스너 설정
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
        //채팅InputField 액션 리스너 설정
        ChatInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendText();
            }
        });

        //전송 버튼 액션 리스너 설정
        btnSubmit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //마우스 이벤트
                setContentPane(mainPanel);
                revalidate();
                repaint();
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                sendText();
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


    // Getters & Setters 시작 
    public void setLblRoomName(String lblRoomName) {
        this.lblRoomName.setText(lblRoomName);
    }

    public void setLblRoomUserNum(String lblRoomUserNum) {
        this.lblRoomUserNum.setText(lblRoomUserNum);
    }

    public JTextArea getTextArea() {
        return textArea;
    }
    // Getters & Setters 끝

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public void sendText() {
        // 채팅이 없으면
        if(ChatInput.getText()=="")
            return;
        getTextArea().append(ChatInput.getText()+"\n");
        ChatInput.setText("");
        ChatInput.setFocusable(true);
    }

}
