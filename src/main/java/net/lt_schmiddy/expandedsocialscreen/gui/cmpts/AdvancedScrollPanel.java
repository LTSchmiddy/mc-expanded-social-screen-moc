package net.lt_schmiddy.expandedsocialscreen.gui.cmpts;

import io.github.cottonmc.cotton.gui.widget.WScrollPanel;
import io.github.cottonmc.cotton.gui.widget.WWidget;

public class AdvancedScrollPanel extends WScrollPanel {

    

    public AdvancedScrollPanel(WWidget widget) {
        super(widget);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void layout() {
        super.layout();

        // verticalScrollBar.setValue(verticalScrollBar.getMaxValue());
    }
    
}
