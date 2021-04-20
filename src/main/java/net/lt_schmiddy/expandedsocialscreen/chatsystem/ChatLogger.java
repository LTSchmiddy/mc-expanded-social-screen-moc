package net.lt_schmiddy.expandedsocialscreen.chatsystem;
import net.lt_schmiddy.expandedsocialscreen.chatsystem.log.ChatLog;
import net.lt_schmiddy.expandedsocialscreen.config.ConfigHandler;
import net.minecraft.network.MessageType;
import net.minecraft.text.Text;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;
import java.util.Map.Entry;

import com.google.gson.JsonObject;



public class ChatLogger {
    
    public File logDir;
    public HashMap<String, ChatLog> logs;

    public ChatLogger() {
        logs = new HashMap<String, ChatLog>();
        logDir = new File(ConfigHandler.config.logDir);

        if (!logDir.exists()){
            System.out.println("Creating log folder...");
            logDir.mkdirs();
        } else {
            System.out.println("Log folder exists...");
        }
    }

    public String identifyLogForMessage(TranslatableText message, UUID senderUuid) {
        LiteralText header = (LiteralText) message.getArgs()[0];

        // Couldn't figure out how to extract the other player's UUID w/o converting to JSON.
        // I guess this works pretty well, though.
        try {
            JsonObject json_header = Text.Serializer.toJsonTree(header).getAsJsonObject();
            return json_header.get("hoverEvent").getAsJsonObject().get("contents").getAsJsonObject().get("id").getAsString();
        } catch (IllegalStateException e) {
            System.out.println("Cannot identity other player. Using sender id...");
            return senderUuid.toString();
        }
    }
    
    public void processMessage(MessageType messageType, TranslatableText message, UUID senderUuid, DMType type) {
        System.out.println(Text.Serializer.toJson(message));

        String logId = identifyLogForMessage(message, senderUuid);
        
        ChatLog log = getLog(logId);

        log.append(message, type);
    }

    public ChatLog getLog(String logName) {
        // System.out.println("checking for log: " + logName);
        if (logs.containsKey(logName)) {
            return logs.get(logName);
        }

        System.out.println("Loading log for " + logName);
        ChatLog newLog = new ChatLog(logName);
        logs.put(logName, newLog);
        return newLog;
    }

    public void saveAll() {
        for (Entry<String, ChatLog> entry : logs.entrySet()) {
            entry.getValue().save();
        }
    }

}
