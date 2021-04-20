package net.lt_schmiddy.expandedsocialscreen.gui.cmpts;

import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import net.lt_schmiddy.expandedsocialscreen.chatsystem.DMType;
import net.lt_schmiddy.expandedsocialscreen.chatsystem.log.ChatLogEntry;
import net.minecraft.text.LiteralText;



public class MessageLabel extends WGridPanel {
    public static LiteralText BLANK_TEXT = new LiteralText("");

    protected ChatLogEntry message;

    protected WLabel unameLabel;
    protected WLabel msgLabel;
    protected WLabel timestampLabel;

    public MessageLabel() {
        super(4);
        construct();
    }

    public MessageLabel(int gridSize) {
        super(gridSize);
        construct();
    }

    public void construct() {
        unameLabel = new WLabel(BLANK_TEXT);
        msgLabel = new WLabel(BLANK_TEXT);
        timestampLabel = new WLabel(BLANK_TEXT);

        add(unameLabel, 0, 0);
        add(msgLabel, 0, 3);
        add(timestampLabel, 0, 5);
    }

    public ChatLogEntry getMessage() {
        return message;
    }

    public void setMessage(ChatLogEntry entry) {
        message = entry;
        renderMessage();
    }

    public void renderMessage() {
        if (message == null) {
            unameLabel.setText(BLANK_TEXT);
            msgLabel.setText(BLANK_TEXT);
            timestampLabel.setText(BLANK_TEXT);
            return;
        }
        unameLabel.setText(new LiteralText("--> " + message.senderName + ":"));
        unameLabel.setColor(message.type == DMType.OUTGOING ? 0x0000ff : 0x007700);
        
        msgLabel.setText(message.getMessageText());
        // Message color can be set in-message... I think.
        
        System.out.println(msgLabel.getWidth());
        timestampLabel.setText(message.dateAsText());
        // timestampLabel.setText(new LiteralText("DATE HERE"));
        timestampLabel.setColor(0xbbbbbb);
    }

    public int getRecommendedHeight() {
        return 8;
    }


}