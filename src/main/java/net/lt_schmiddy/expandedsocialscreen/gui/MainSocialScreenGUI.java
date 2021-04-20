package net.lt_schmiddy.expandedsocialscreen.gui;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
// import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
// import io.github.cottonmc.cotton.gui.widget.WSprite;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;


// import net.lt_schmiddy.expandedsocialscreen.chatsystem.ChatSystem;
// import net.lt_schmiddy.expandedsocialscreen.chatsystem.log.ChatLog;
import net.lt_schmiddy.expandedsocialscreen.config.ConfigHandler;
import net.lt_schmiddy.expandedsocialscreen.gui.cmpts.SocialScreenPlayerList;
import net.lt_schmiddy.expandedsocialscreen.gui.cmpts.SocialScreenPlayerView;

public class MainSocialScreenGUI extends LightweightGuiDescription {
    MinecraftClient client = MinecraftClient.getInstance();

    // UI Root:
    WGridPanel root;
    
    // UI Components
    WLabel headerLabel;

    // Subclass Components:
    SocialScreenPlayerList playerList;
    SocialScreenPlayerView playerView;

    public MainSocialScreenGUI() {

        root = new WGridPanel();
        setRootPanel(root);
        root.setSize(
            ConfigHandler.config.panelSize[0],
            ConfigHandler.config.panelSize[1]
        );
        // root.validate(this);
                
        headerLabel = new WLabel(new LiteralText("Whispers"), 0x000000);
        root.add(headerLabel, 0, 0);

        playerList = new SocialScreenPlayerList();
        root.add(playerList, 0, 1, ConfigHandler.config.listSize[0], ConfigHandler.config.listSize[1]);

        playerView = new SocialScreenPlayerView();
        playerView.init(playerList);
        root.add(playerView, ConfigHandler.config.listSize[0], 1, ConfigHandler.config.viewSize[0], ConfigHandler.config.viewSize[1]);
        // I want to know the size of the widget BEFORE I construct the children components:
        
        root.validate(this);
    }
}