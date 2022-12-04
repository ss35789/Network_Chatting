package Object;

public class DivString {
    // "-" => ChatDiv
    // "|" => ListDiv
    // "@" => ChatListDiv
    // "," => RoomDiv
    // " " => RoomListDiv
    public static String regxListDiv = "\\|_#\\$%";  //|_#$%
    public static String regxRoomListDiv ="\\$#@";  //$#@
    public static String regxRoomDiv = ",_#\\$\\^#"; //,_#$^#
    public static String regxChatDiv = "-\\_\\^\\$%#"; //-_^$%#
    public static String regxChatListDiv = "@&%\\^&"; //@&%^&
    public static String ListDiv = "|_#$%";
    public static String RoomListDiv = "$#@";
    public static String RoomDiv = ",_#$^#";
    public static String ChatDiv = "-_^$%#";
    public static String ChatListDiv = "@&%^&";
}
