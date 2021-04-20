package net.lt_schmiddy.expandedsocialscreen.chatsystem.log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.lt_schmiddy.expandedsocialscreen.chatsystem.DMType;
import net.lt_schmiddy.expandedsocialscreen.config.ConfigHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

public class ChatLog {
    String name;
    File logFile;

    public boolean wasUpdated;

    public static final Gson gson = new GsonBuilder().create();

    public ArrayList<ChatLogEntry> messages;
    public ArrayList<ChatLogEntry> unread_messages;

    public ChatLog(String logName) {

        name = logName;
        messages = new ArrayList<ChatLogEntry>(100);
        unread_messages = new ArrayList<ChatLogEntry>(10);

        logFile = new File(ConfigHandler.config.logDir+"/"+logName+".log");
        try {
            if (!logFile.exists()){
                System.out.println("Creating log for " + name);
                logFile.createNewFile();
            }  

        } catch (IOException e) {
            e.printStackTrace();
        }

        load();
    }

    
    public void append(TranslatableText message, DMType type) {
        String senderName = "Me";
        if (type == DMType.INCOMING) {
            senderName = ((LiteralText)message.getArgs()[0]).asString();
        }
        
        ChatLogEntry entry = new ChatLogEntry(
            senderName,
            type,
            (LiteralText)message.getArgs()[1]
        );
        messages.add(entry);
        unread_messages.add(entry);
        wasUpdated = true;
    }

    public ArrayList<ChatLogEntry> getUnread() {
        ArrayList<ChatLogEntry> retVal = unread_messages;
        unread_messages = new ArrayList<ChatLogEntry>(10);
        wasUpdated = false;
        return retVal;
    }

    public void clearUnread() {
        unread_messages = new ArrayList<ChatLogEntry>(10);
        wasUpdated = false;
    }

    public void load() {
        try {
            Scanner logScanner = new Scanner(logFile);
            
            while (logScanner.hasNextLine()){
                String line = logScanner.nextLine();

                if (!line.startsWith("{")) {continue;}

                ChatLogEntry msg = gson.fromJson(line, ChatLogEntry.class);
                messages.add(msg);
            }
            logScanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            FileWriter writer = new FileWriter(logFile);

            for (ChatLogEntry text : messages) {
                String outJson = gson.toJson(text);
                writer.write(outJson + "\n");
            }
            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
}
