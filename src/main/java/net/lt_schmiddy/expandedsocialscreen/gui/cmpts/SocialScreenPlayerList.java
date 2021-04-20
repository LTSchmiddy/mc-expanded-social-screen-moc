package net.lt_schmiddy.expandedsocialscreen.gui.cmpts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

import blue.endless.jankson.annotation.Nullable;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WScrollPanel;
import net.fabricmc.fabric.api.util.TriState;
import net.lt_schmiddy.expandedsocialscreen.chatsystem.ChatSystem;
import net.lt_schmiddy.expandedsocialscreen.chatsystem.log.ChatLog;
import net.lt_schmiddy.expandedsocialscreen.config.ConfigHandler;
// import io.github.cottonmc.cotton.gui.widget.WSprite;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.LiteralText;

public class SocialScreenPlayerList extends WGridPanel {
    // public GuiDescription guiHost;

    public PlayerListFilter playerListFilter = PlayerListFilter.ALL;
    public static MinecraftClient client = MinecraftClient.getInstance();
    
    @Nullable
    public SocialScreenPlayerView playerView;
    // public UUID selected_player_id;
    public static UUID selected_player_id;

    protected WGridPanel scrollPanel;
    protected WScrollPanel scrollView;

    protected ArrayList<WButton> playerButtons = new ArrayList<WButton>(100);;

    // Getters/Setters
    public PlayerListEntry getSelectedPlayer() {
        return selected_player_id == null ? null : client.player.networkHandler.getPlayerListEntry(selected_player_id);
    }

    public ChatLog getSelectedLog() {
        return selected_player_id == null ? null : ChatSystem.chatLogger.getLog(selected_player_id.toString());
    }

    // Constructors:
    public SocialScreenPlayerList(int gridSize) {
        super(gridSize);
        constructListView();
    }

    public SocialScreenPlayerList() {
        super();
        constructListView();
    }

    // UI Building:
    public void constructListView() {
        // Creating scrollView:
        scrollPanel = new WGridPanel();
        scrollView = new WScrollPanel(scrollPanel);

        scrollView.setScrollingHorizontally(TriState.FALSE);
        scrollView.setScrollingVertically(TriState.TRUE);

        add(scrollView, 0, 0, ConfigHandler.config.listSize[0], ConfigHandler.config.listSize[1]);

        constructPlayerList();

        
    }

    public void constructPlayerList() {
        Iterator<UUID> uuidIter = getPlayerUuids().iterator();
        
        int i = 0;
        while(uuidIter.hasNext()) {
        
            UUID uUID = (UUID)uuidIter.next();
            PlayerListEntry playerListEntry = client.player.networkHandler.getPlayerListEntry(uUID);
            if (playerListEntry != null) {
                WButton newButton = constructPlayerButton(playerListEntry);
                scrollPanel.add(newButton, 0, i, ConfigHandler.config.listSize[0], 1);
                playerButtons.add(newButton);

                i++;

            }
        }
    }

    protected WButton constructPlayerButton(PlayerListEntry entry) {
        System.out.println(entry.getProfile().getName());

        WButton button = new WButton(new LiteralText(entry.getProfile().getName()));
        button.setOnClick(() -> {
            // This code runs on the client when you click the button.
            selected_player_id = entry.getProfile().getId();
            System.out.println(entry.getProfile().getName());
            if(playerView != null) {
                playerView.updatePlayer(); 
            }

        });

        return button;
    }

    // Utilities:
    public Collection<UUID> getPlayerUuids() {
        Collection<UUID> retVal;
        switch(playerListFilter) {
            case ALL:
                retVal = client.player.networkHandler.getPlayerUuids();
                break;
            case HIDDEN:
                retVal = client.getSocialInteractionsManager().getHiddenPlayers();
                break;
            default:
                retVal = client.player.networkHandler.getPlayerUuids();
        }
        return retVal;
    }

    // Child Enum:
    public static enum PlayerListFilter {
        ALL,
        HIDDEN,
    }
}
