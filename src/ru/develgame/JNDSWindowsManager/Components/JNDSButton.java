/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */

package ru.develgame.JNDSWindowsManager.Components;

import nds.TouchPosition;
import nds.pstros.video.NDSFont;
import nds.pstros.video.NDSGraphics;
import ru.develgame.JNDSWindowsManager.Actions.JNDSClickAction;

/**
 *
 * @author Ilya Zemskov
 */
public class JNDSButton extends JNDSAbstractComponent {
    private JNDSClickAction clickAction;
    private String text;
    private int color = 0xFFFFFF;
    private int bgColor = 0x326690;

    private int width = 0;
    private int height = 0;

    private static final int PADDING_WIDTH = 5;
    private static final int PADDING_HEIGHT = 3;

    public JNDSButton(String text, int posX, int posY) {
        super(posX, posY);
        this.text = text;
    }

    public void paint(NDSGraphics g, NDSFont fnt) {
        if (width == 0)
            width = PADDING_WIDTH * 2 + fnt.getStringWidth(text);
        if (height == 0)
            height = PADDING_HEIGHT * 2 + fnt.getHeight();

        g.setColor(bgColor);
        g.fillRect(
                posX,
                posY,
                width,
                height
        );

        g.setColor(color);
        g.drawString(text, posX + PADDING_WIDTH, posY + fnt.getHeight());
    }

    public void setClickAction(JNDSClickAction clickAction) {
        this.clickAction = clickAction;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isClicked(TouchPosition tp) {
        return tp.px >= posX && tp.px <= (posX + width) && tp.py >= posY && tp.py <= (posY + height);
    }

    public void clickEvent(TouchPosition tp) {
        if (clickAction != null)
            clickAction.action(tp);
    }
}
