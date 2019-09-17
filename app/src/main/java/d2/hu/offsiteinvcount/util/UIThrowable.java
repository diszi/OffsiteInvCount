package d2.hu.offsiteinvcount.util;

public class UIThrowable extends Throwable {

    private int messageId;

    public UIThrowable(int messageId) {
        this.messageId = messageId;
    }

    public int getMessageId() {
        return messageId;
    }
}
