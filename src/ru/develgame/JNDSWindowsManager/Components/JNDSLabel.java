/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */

package ru.develgame.JNDSWindowsManager.Components;

import nds.TouchPosition;
import nds.pstros.video.NDSFont;
import nds.pstros.video.NDSGraphics;

/**
 *
 * @author Ilya Zemskov
 */
public class JNDSLabel extends JNDSAbstractComponent {
    private String text = "";
    private int color = 0x000000;

    public JNDSLabel(String text, int posX, int posY) {
        super(posX, posY);
        if (text != null)
            this.text = text;
    }

    public void paint(NDSGraphics g, NDSFont fnt) {
        g.setColor(color);
        g.drawString(text, posX, posY + fnt.getHeight());
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        if (text != null)
            this.text = text;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isClicked(TouchPosition tp) {
        return false;
    }

    public void clickEvent(TouchPosition tp) {
    }
}
