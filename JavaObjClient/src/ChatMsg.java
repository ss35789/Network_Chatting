
// ChatMsg.java 채팅 메시지 ObjectStream 용.
import java.io.Serializable;
import java.util.Map;
import javax.swing.ImageIcon;

class ChatMsg implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String code; // 100:로그인, 400:로그아웃, 200:채팅메시지, 300:Image
	private Object data;
	public ImageIcon img;

	public ChatMsg(String id, String code, Object msg) {
		this.id = id;
		this.code = code;
		this.data = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public String getId() {
		return id;
	}

	public ImageIcon getImg() {return img; }

	public void setId(String id) {
		this.id = id;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void setImg(ImageIcon img) {
		this.img = img;
	}


}