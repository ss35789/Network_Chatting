import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;

import Object.Chat;

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
    private Integer rid = null;
    JavaObjClientMainViewController controller = JavaObjClientMainViewController.getInstance();

    //생성자 함수
    public ChatRoomView(String roomName, String userNum) {
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 800);
        setContentPane(mainPanel);
        lblRoomName.setText(roomName);
        lblRoomUserNum.setText(userNum);
        //textArea.setForeground(Color.BLACK);
        textArea.setFont(new Font("Serif", Font.BOLD, 20));
        textArea.setDisabledTextColor(Color.BLACK);

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

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public JLabel getLblRoomName() {
        return lblRoomName;
    }
    // Getters & Setters 끝

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public void sendText() {
        // 채팅이 없으면
        if (ChatInput.getText().equals(""))
            return;
        //채팅 시간 기록
        LocalTime time = LocalTime.now();

        //채팅 화면에 추가 & reSetting
        textArea.append(controller.getUser().getUserName()+"\n"+ChatInput.getText() + "\n");


        //TextArea의 위치를 맨 아래로 옮김
        int len = textArea.getDocument().getLength();
        textArea.setCaretPosition(len);

        if (this.rid == null) {
            setRid(controller.getRidfromRoomName(lblRoomName.getText()));
        }

        // 서버에게 200 Protocol 전송
        Chat chat = new Chat.ChatBuilder().
                setUid(controller.getUser().getUid()).
                setMsg(ChatInput.getText()).
                setDate(controller.DateToString(time)).
                build();
        String msg = rid + DivString.RoomDiv + chat.toString(chat);

        ChatMsg obcm = new ChatMsg(Integer.toString(controller.getUser().getUid()), "200", msg);
        controller.SendObject(obcm);

        //채팅방 뷰 reSetting
        ChatInput.setText("");
        ChatInput.requestFocus();
    }

    public void receiveText(Integer uid, String text) {
        // 다른 사람이 보낸 msg면 추가
        if (uid != controller.getUser().getUid()) {
            textArea.append(controller.getUserList().get(uid).getUserName()+"\n"+text + "\n");
            ChatInput.requestFocus();
        }
        //TextArea의 위치를 맨 아래로 옮김
        int len = textArea.getDocument().getLength();
        textArea.setCaretPosition(len);
        return;
    }

}
