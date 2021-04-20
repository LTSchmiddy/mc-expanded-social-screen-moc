package net.lt_schmiddy.expandedsocialscreen.chatsystem;

// import net.minecraft.client.MinecraftClient;
// import net.minecraft.client.gui.ClientChatListener;
// import net.minecraft.client.gui.hud.ChatHudListener;
import net.minecraft.network.MessageType;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.UUID;

public class MessageHandler {
    
    public static String keyIncomingDM = "commands.message.display.incoming";
    public static String keyOutgoingDM = "commands.message.display.outgoing";

    public void processMessage(MessageType messageType, Text message, UUID senderUuid) {        
        DMType type = isDirectMessage(message);
        System.out.println("Intercepted " + messageType + " message of DMType " + type + " sent by " + senderUuid + ": ");
        // System.out.println("-------------------");
        // System.out.println(message);
        // System.out.println("-------------------");
        
        if (type != DMType.INVALID) {
        // if (message instanceof TranslatableText) {
            ChatSystem.chatLogger.processMessage(messageType, (TranslatableText) message, senderUuid, type);
        }

    }

    public static DMType isDirectMessage(Text message) {
        if (!(message instanceof TranslatableText)) {
            return DMType.INVALID;
        }
        TranslatableText tMsg = (TranslatableText) message;
        if (tMsg.getKey().equals(keyIncomingDM)){
            return DMType.INCOMING;
        } else if (tMsg.getKey().equals(keyOutgoingDM)) {
            return DMType.OUTGOING;
        }
        return DMType.INVALID;
    }
}



        // System.out.println("Intercepted " + messageType + " message of class " + message.getClass() + " sent by " + senderUuid + ": ");
        // System.out.println("-------------------");
        // System.out.println(">> is TranslatableText: " + (message instanceof TranslatableText));
        // System.out.println("-------------------");
        // System.out.println(message);
        // System.out.println("-------------------");
