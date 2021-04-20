package net.lt_schmiddy.expandedsocialscreen.chatsystem.log;

import java.util.Date;

import com.google.gson.JsonElement;

import net.lt_schmiddy.expandedsocialscreen.chatsystem.DMType;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
// import net.minecraft.text.LiteralText;
// import net.minecraft.text.Text;

public class ChatLogEntry {
    public String senderName = "";
    public DMType type;
    public Date date;
    public JsonElement message; 

    public ChatLogEntry() {}
    
    public ChatLogEntry(String p_senderName, DMType p_type, LiteralText p_message) {
        senderName = p_senderName;
        type = p_type;
        message = Text.Serializer.toJsonTree(p_message);
        date = new Date();
    }

    public LiteralText senderNameAsText() {
        return new LiteralText(senderName);
    }

    public LiteralText dateAsText() {
        return new LiteralText(date.toString());
    }

    public LiteralText getMessageText() {
        return (LiteralText) Text.Serializer.fromJson(message);
    }

    public void setMessageText(LiteralText text) {
        message = Text.Serializer.toJsonTree(text);
    }

    public String getContent() {
        return getMessageText().asString();
    }
}
