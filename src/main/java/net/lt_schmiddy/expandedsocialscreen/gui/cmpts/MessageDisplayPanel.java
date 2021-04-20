package net.lt_schmiddy.expandedsocialscreen.gui.cmpts;

import java.util.ArrayList;
import java.util.List;

import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import net.lt_schmiddy.expandedsocialscreen.chatsystem.log.ChatLog;
import net.lt_schmiddy.expandedsocialscreen.chatsystem.log.ChatLogEntry;


public class MessageDisplayPanel extends WGridPanel {

    public static int defaultHistorySize = 100;
    public static boolean useLinkedLists = false;


    public int historySize = 100;

    public ChatLog log;
    public List<MessageLabel> msgLabels;
    

    public MessageDisplayPanel() {
        super(4);
        init();
    }

    public MessageDisplayPanel(int gridSize) {
        super(gridSize);
        init();
    }

    protected void init() {
        msgLabels = new ArrayList<MessageLabel>(historySize);

        for (int i = 0; i < historySize; i++) {
            MessageLabel label = new MessageLabel();
            add(label, 0, label.getRecommendedHeight()*i);
            msgLabels.add(label);
        }
    }

    public void redraw(ChatLog log) {

        for (int i = 0; i < msgLabels.size(); i++) {
            ChatLogEntry entry = null;
            if (log != null) {
                int messageIndex = log.messages.size() - msgLabels.size() + i;
                // System.out.println(messageIndex);
                if (messageIndex >= 0 && messageIndex < log.messages.size()) {
                    entry = log.messages.get(messageIndex);
                } 
            }

            // if (entry != null) {
                // System.out.println(labelIndex);
                // System.out.println(entry.message);
            // }

            // msgLabels.get(labelIndex).setMessage(entry);
            msgLabels.get(i).setMessage(entry);
        }

        log.clearUnread();
    }
}
