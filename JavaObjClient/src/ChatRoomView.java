import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
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
    private Integer currentViewChatSize = 0;
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
        //textArea.setSize(textArea.getWidth(), textArea.getPreferredSize().height);//For fixed width
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
                fd.setDirectory(System.getProperty("user.dir") + "\\JavaObjClient\\images");
                fd.setVisible(true);

                // server에게 300 protocol 전송
                Chat chat = new Chat.ChatBuilder().
                        setUid(controller.getUser().getUid()).
                        setMsg("IMG").
                        setDate(controller.DateToString(LocalTime.now())).
                        build();

                isRidNullSet(rid);

                String msg = rid.toString() + DivString.RoomDiv + chat.toString(chat);

                ChatMsg obcm = new ChatMsg(controller.getUser().getUserName(), "300", msg);
                ImageIcon img = new ImageIcon(fd.getDirectory() + fd.getFile());
                obcm.setImg(img);
                controller.SendObject(obcm);

                //채팅창에 이미지 추가
                appendImage(new ImageIcon(fd.getDirectory() + fd.getFile()));
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

        isRidNullSet(this.rid);

        //채팅 시간 기록
        LocalTime time = LocalTime.now();
        //채팅방 뷰에 채팅 추가
        appnedText(controller.getUser().getUid(), ChatInput.getText());
//
//        //TextArea의 위치를 맨 아래로 옮김
//        int len = textArea.getDocument().getLength();
//        textArea.setCaretPosition(len);


        // 서버에게 200 Protocol 전송
        Chat chat = new Chat.ChatBuilder().
                setUid(controller.getUser().getUid()).
                setMsg(ChatInput.getText()).
                setDate(controller.DateToString(time)).
                build();
        String msg = rid + DivString.RoomDiv + chat.toString(chat);
        ChatMsg obcm = new ChatMsg(Integer.toString(controller.getUser().getUid()), "200", msg);
        controller.SendObject(obcm);

        //Client RoomList의 Room의 ChatList에 채팅 추가
        controller.getRoomList().get(rid).getChatList().add(chat);

        //채팅방 뷰 reSetting
        ChatInput.setText("");
        ChatInput.requestFocus();
    }

    private void isRidNullSet(Integer rid) {
        //rid 세팅이 되어있지 않으면 rid를 세팅함
        if (rid == null) {
            setRid(controller.getRidfromRoomName(lblRoomName.getText()));
        }
    }

    public void receiveText(Integer uid, String text) {
        //rid 세팅이 되어있지 않으면 rid를 세팅함
        isRidNullSet(this.rid);

        // 다른 사람이 보낸 msg면 추가
        if (uid != controller.getUser().getUid()) {
            //채팅방 뷰에 채팅 추가
            appnedText(controller.getUserList().get(uid).getUid(), text);
            ChatInput.requestFocus();
//            // 끝으로 이동
//            int len = textArea.getDocument().getLength();
//            textArea.setCaretPosition(len);

            //Client RoomList의 Room의 ChatList에 채팅 추가
            Chat chat = new Chat.ChatBuilder().
                    setUid(uid).
                    setMsg(text).
                    build();

            //controller.getRoomList().get(rid).getChatList().add(chat);
        }
//        //TextArea의 위치를 맨 아래로 옮김
//        int len = textArea.getDocument().getLength();
//        textArea.setCaretPosition(len);
        return;
    }

    public void appnedText(Integer uid, String text) {

        String userName = controller.getUserNameFromUid(uid);
        movetoEndLine();

        // 첫 채팅일시 바로 추가
        if(textArea.getText().length()==0){
            textArea.replaceSelection(userName + "\n");
            movetoEndLine();
            textAreaAppendText(text, 12);
            return;
        }

        //첫채팅이 아닐경우 유저 네임 print 중복 처리
        if(!checkChatUserSame(uid)){
            textArea.replaceSelection(userName + "\n");
            movetoEndLine();
            textAreaAppendText(text, 12);
        }
        else
            textAreaAppendText(text, 12);
    }

    private void textAreaAppendText(String text, int widthlimit) {
        // text 추가 , 긴 text 길이 처리
        if (text.length() > widthlimit) {
            while (text.length() > widthlimit) {
                textArea.replaceSelection(text.substring(0, widthlimit - 1) + "\n");
                text = text.substring(widthlimit);
                textArea.setCaretPosition(textArea.getDocument().getLength());
            }
            textArea.replaceSelection(text + "\n");
        } else
            textArea.replaceSelection(text + "\n");
    }

    /***
     * 추가할 채팅과 그 이전 채팅의 유저가 같은 체크하는 함수
     * @param uid 추가할 채팅의 UserID
     * @return 같으면 true 다르면 false
     */
    public boolean checkChatUserSame(Integer uid) {
        Document doc = textArea.getDocument();
        try {
            String[] str = doc.getText(0,doc.getLength()-1).split("\\n");
            for(int i=str.length-1;i>=0;i--){
                //userName을 찾지 못하면 바로 위의 텍스트로 다시 넘어감
                if(controller.getUidFromUserName(str[i])==999)
                    continue;
                
                else{
                 if((controller.getUidFromUserName(str[i])==uid))
                     return true;
                 else
                     return false;
                }
            }
            return false;
        } catch (BadLocationException e) {
            e.printStackTrace();
            System.out.println("First Chat Append in this ChatRoomView");
            return false;
        }
    }

    public void movetoEndLine() {
        // 끝으로 이동
        int len = textArea.getDocument().getLength();
        textArea.setCaretPosition(len);
    }

    public void appendImage(ImageIcon ori_icon) {
        movetoEndLine();
        Image ori_img = ori_icon.getImage();
        int width, height;
        double ratio;
        width = ori_icon.getIconWidth();
        height = ori_icon.getIconHeight();
        // Image가 너무 크면 최대 가로 또는 세로 200 기준으로 축소시킨다.
        if (width > 200 || height > 200) {
            if (width > height) { // 가로 사진
                ratio = (double) height / width;
                width = 200;
                height = (int) (width * ratio);
            } else { // 세로 사진
                ratio = (double) width / height;
                height = 200;
                width = (int) (height * ratio);
            }
            Image new_img = ori_img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            ImageIcon new_icon = new ImageIcon(new_img);
            textArea.insertIcon(new_icon);
        } else
            textArea.insertIcon(ori_icon);
        movetoEndLine();
        textArea.replaceSelection("\n");
        // ImageViewAction viewaction = new ImageViewAction();
        // new_icon.addActionListener(viewaction); // 내부클래스로 액션 리스너를 상속받은 클래스로
    }
}
