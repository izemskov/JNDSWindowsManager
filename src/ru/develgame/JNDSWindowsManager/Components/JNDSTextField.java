/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */

package ru.develgame.JNDSWindowsManager.Components;

import nds.TouchPosition;
import nds.pstros.video.NDSFont;
import nds.pstros.video.NDSGraphics;
import ru.develgame.JNDSWindowsManager.Actions.JNDSAction;
import ru.develgame.JNDSWindowsManager.Forms.JNDSKeyboardForm;

/**
 *
 * @author Ilya Zemskov
 */
public class JNDSTextField extends JNDSAbstractComponent {
    private String text = "";
    private int width;
    private int height;

    private int bgColor = 0x73a6ce;
    private int color = 0x000000;

    private static final int PADDING_WIDTH = 5;
    private static final int PADDING_HEIGHT = 2;

    public JNDSTextField(String text, int posX, int posY, int width) {
        super(posX, posY);

        if (text != null)
            this.text = text;

        this.width = width;
    }

    public void paint(NDSGraphics g, NDSFont fnt) {
        height = fnt.getHeight() + PADDING_HEIGHT * 2;

        g.setColor(bgColor);
        g.drawRect(
                posX,
                posY,
                width,
                height
        );

        g.setColor(color);
        g.drawString(text, posX + PADDING_WIDTH, posY + fnt.getHeight());
    }

    public boolean isClicked(TouchPosition tp) {
        return tp.px >= posX && tp.px <= (posX + width) && tp.py >= posY && tp.py <= (posY + height);
    }

    public void clickEvent(TouchPosition tp) {
        parent.addActionToQueue(new JNDSAction() {
            public void action() {
                JNDSKeyboardForm jndsKeyboardForm = new JNDSKeyboardForm(text);
                jndsKeyboardForm.setVisible(true);
                if (jndsKeyboardForm.isAnswer()) {
                    text = jndsKeyboardForm.getText();
                }
            }
        });
    }
}
