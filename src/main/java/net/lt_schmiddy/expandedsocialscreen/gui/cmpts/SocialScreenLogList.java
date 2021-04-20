package net.lt_schmiddy.expandedsocialscreen.gui.cmpts;

import java.util.Map.Entry;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WScrollPanel;
import net.fabricmc.fabric.api.util.TriState;
import net.lt_schmiddy.expandedsocialscreen.chatsystem.ChatSystem;
import net.lt_schmiddy.expandedsocialscreen.chatsystem.log.ChatLog;
import net.lt_schmiddy.expandedsocialscreen.config.ConfigHandler;
import net.minecraft.text.LiteralText;

public class SocialScreenLogList extends WGridPanel {
    public WGridPanel scrollPanel;
    public WScrollPanel scrollView;

    public ChatLog selected_log;

    public SocialScreenLogList(int gridSize) {
        super(gridSize);
        ConstructListView();
    }

    public SocialScreenLogList() {
        super();
        ConstructListView();
    }

    public void ConstructListView() {
        
        // Creating scrollView:
        scrollPanel = new WGridPanel();
        scrollView = new WScrollPanel(scrollPanel);

        scrollView.setScrollingHorizontally(TriState.FALSE);
        scrollView.setScrollingVertically(TriState.TRUE);

        // scrollPanel.setSize(ConfigHandler.config.listSize[0], ConfigHandler.config.listSize[1]);
        add(scrollView, 0, 0, ConfigHandler.config.listSize[0], ConfigHandler.config.listSize[1]);

        // WLabel messageBtn = new WLabel(new LiteralText("MessageBtn"), 0x000000);
        // scrollPanel.add(messageBtn, 0, 0, 1, 20);

        ConstructLogList();
    }

    public void ConstructLogList() {
        int i = 0;
        for (Entry<String, ChatLog> entry : ChatSystem.chatLogger.logs.entrySet()) {
            WButton button = new WButton(new LiteralText(entry.getKey()));
            button.setOnClick(() -> {
                // This code runs on the client when you click the button.
                selected_log = entry.getValue();
                System.out.println(entry.getKey());
            });

            scrollPanel.add(button, 0, i*2, ConfigHandler.config.listSize[0], 2);
            i++;
        }
    }
}
