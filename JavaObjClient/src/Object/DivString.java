package Object;

public class DivString {
    // "-" => ChatDiv
    // "|" => ListDiv
    // "@" => ChatListDiv
    // "," => RoomDiv
    // " " => RoomListDiv
    public static String ListDiv = "\\|#\\$%";  //|#$%
    public static String RoomListDiv ="\\$#@";  //$#@
    public static String RoomDiv = ",#\\$\\^#"; //,#$^#
    public static String ChatDiv = "-\\^\\$%#"; //-^$%#
    public static String ChatListDiv = "@&%\\^&"; //@&%^&
}
