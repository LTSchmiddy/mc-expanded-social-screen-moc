package net.lt_schmiddy.expandedsocialscreen.gui.cmpts;

import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import net.fabricmc.fabric.api.util.TriState;
import net.lt_schmiddy.expandedsocialscreen.chatsystem.log.ChatLog;
import net.lt_schmiddy.expandedsocialscreen.config.ConfigHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;

public class SocialScreenPlayerView extends WGridPanel { 
    // public GuiDescription guiHost;
    public static int maxMessageLength = 256;
    public MinecraftClient client = MinecraftClient.getInstance();
    public SocialScreenPlayerList playerList;

    protected WLabel playerNameLabel;

    protected MessageDisplayPanel chatPanel;
    // protected WScrollPanel chatView;
    protected AdvancedScrollPanel chatView;
    protected WTextField chatInput;

    // protected int chatDrawPos;
    // protected ArrayList<WLabel> msgRows;


    public SocialScreenPlayerView() {
        super();
    }

    public SocialScreenPlayerView(int gridSize) {
        super(gridSize);
    }

    public SocialScreenPlayerView(SocialScreenPlayerList p_playerList, int gridSize) {
        super(gridSize);
        init(p_playerList);
    }

    public SocialScreenPlayerView(SocialScreenPlayerList p_playerList) {
        super();
        init(p_playerList);
    }

    public void init(SocialScreenPlayerList p_playerList) {
        playerList = p_playerList;
        playerList.playerView = this;

        playerNameLabel = new WLabel("");
        add(playerNameLabel, 0, 0, 5, 2);

        chatPanel = new MessageDisplayPanel();
        chatView = new AdvancedScrollPanel(chatPanel);

        chatView.setScrollingHorizontally(TriState.FALSE);
        chatView.setScrollingVertically(TriState.TRUE);

        add(
            chatView, 
            1, 
            1, 
            ConfigHandler.config.viewSize[0] - 1, 
            ConfigHandler.config.viewSize[1] - 3
        );

        chatInput = new WTextField(new LiteralText("Enter Message...")) {
            public void onKeyPressed(int ch, int key, int modifiers) {

                // System.out.println("Anon... Send " + key);
                if (key == 28) {
                    // System.out.println("Sending");
                    client.player.sendChatMessage("/tell " + playerList.getSelectedPlayer().getProfile().getName() + " " + this.getText());
                    setText("");
                } else {
                    super.onKeyPressed(ch, key, modifiers);
                }
                
            }
        };
        chatInput.setMaxLength(maxMessageLength);
        add(
            chatInput,
            0, 
            ConfigHandler.config.viewSize[1] - 1, 
            ConfigHandler.config.viewSize[0],
            1
        );

        
        // chatInput.onKeyPressed(ch, key, modifiers);

        // msgRows = new ArrayList<WLabel>(100);
        
        if (SocialScreenPlayerList.selected_player_id != null) {
            updatePlayer();
        }
    }


    public void tick() {
        // TODO Auto-generated method stub
        super.tick();

        ChatLog log = playerList.getSelectedLog();

        if (log == null) {
            return;
        }

        if (log.wasUpdated) {
            chatPanel.redraw(log);
        }
    }

    public void updatePlayer() {
        playerNameLabel.setText(new LiteralText("  " + playerList.getSelectedPlayer().getProfile().getName()));
        chatPanel.redraw(playerList.getSelectedLog());
    }
}

    // public void clearLogView() {
    //     for (WLabel i : msgRows) {
    //         chatPanel.remove(i);
    //     }

    //     msgRows.clear();
    //     chatDrawPos = 0;
    // }

    // public void loadLogView(ChatLog log) {
    //     clearLogView();

    //     System.out.println("Loading log for " + log.toString());
    //     chatDrawPos = 0;
    //     for (ChatLogEntry message : log.messages) {
            
    //         drawMessage(message);
    //     }
    //     if (host != null) {
    //         chatPanel.validate(host);
    //     }
    //     System.out.println("Updated textRows");
    // }

    // public void continueLogView(ChatLog log) {
    //     System.out.println("continuing log...");

    //     for (ChatLogEntry message : log.getUnread()) {
    //         drawMessage(message);
    //     }
    //     if (host != null) {
    //         chatPanel.validate(host);
    //     }
    // }

    // public void drawMessage(ChatLogEntry message) {
    //     WLabel nameLabel = new WLabel("--> " + message.senderName + ":");
    //     nameLabel.setColor(message.type == DMType.OUTGOING ? 0x0000ff : 0x007700);
    //     chatPanel.add(nameLabel, 0, chatDrawPos);
    //     msgRows.add(nameLabel);

    //     chatDrawPos += 2;

    //     WLabel msgLabel = new WLabel( message.getContent());
    //     chatPanel.add(msgLabel, 0, chatDrawPos);
    //     msgRows.add(msgLabel);
    //     chatDrawPos += 3;
    // }



