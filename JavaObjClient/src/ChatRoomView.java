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
    private JTextPane textArea;
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
        setTitle(controller.getUser().getUserName());
        setSize(500, 800);
        setContentPane(mainPanel);
        lblRoomName.setText(roomName);
        lblRoomUserNum.setText(userNum);
        textArea.setFont(new Font("Serif", Font.BOLD, 20));
        textArea.setDisabledTextColor(Color.BLACK);
        //textArea.setLineWrap(true);

        //이미지 전송 버튼 액션 리스너 설정
        btnSendImg.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                //마우스 이벤트
//                setContentPane(mainPanel);
//                revalidate();
//                repaint();
                JFrame frame = new JFrame();
                FileDialog fd = new FileDialog(frame, "이미지 선택", FileDialog.LOAD);
                fd.setDirectory(System.getProperty("user.dir")+"\\JavaObjClient\\images");
                fd.setVisible(true);

                // server에게 300 protocol 전송
                Chat chat = new Chat.ChatBuilder().
                        setUid(controller.getUser().getUid()).
                        setMsg("IMG").
                        setDate(controller.DateToString(LocalTime.now())).
                        build();

                if (rid == null) {
                    setRid(controller.getRidfromRoomName(lblRoomName.getText()));
                }

                String msg = rid.toString()+DivString.RoomDiv+chat.toString(chat);

                ChatMsg obcm = new ChatMsg(controller.getUser().getUserName(), "300",msg);
                ImageIcon img = new ImageIcon(fd.getDirectory() + fd.getFile());
                obcm.setImg(img);
                controller.SendObject(obcm);

                //채팅창에 이미지 추가
                appendIcon(new ImageIcon(fd.getDirectory()+fd.getFile()));
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

    public JTextPane getTextArea() {
        return textArea;
    }

    public JLabel getLblRoomName() {
        return lblRoomName;
    }
    // Getters & Setters 끝

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
    
    //내가 채팅을 보내면
    public void sendText() {
        // 채팅이 없으면 처리 하지 않음
        if (ChatInput.getText().equals(""))
            return;
        
        //채팅 시간 기록
        LocalTime time = LocalTime.now();

        //채팅 화면에 추가 & reSetting
        //textArea.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
//        int EntextAreaWidth = 70;
//        int KotextAreaWidth = 50;
//        int userNameSize =EntextAreaWidth-controller.getUser().getUserName().length();
//        int textSize = KotextAreaWidth-ChatInput.getText().length();
//        int userNameSize =AreaWidth-controller.getUser().getUserName().length();
//        int textSize = AreaWidth-ChatInput.getText().length();



        appnedText(controller.getUser().getUserName(),ChatInput.getText());


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
            appnedText(controller.getUserList().get(uid).getUserName(),text);
            ChatInput.requestFocus();
        }
        //TextArea의 위치를 맨 아래로 옮김
        int len = textArea.getDocument().getLength();
        textArea.setCaretPosition(len);
        return;
    }

    public void appnedText(String userName,String text) {
        int len = textArea.getDocument().getLength();
        // 끝으로 이동
        textArea.setCaretPosition(len);

        textArea.replaceSelection(userName + "\n");
        textArea.replaceSelection(text + "\n");
    }
    public void appendIcon(ImageIcon icon) {
        int len = textArea.getDocument().getLength();
        // 끝으로 이동
        textArea.setCaretPosition(len);
        textArea.insertIcon(icon);
    }
}
