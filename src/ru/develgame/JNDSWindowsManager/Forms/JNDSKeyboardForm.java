/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */

package ru.develgame.JNDSWindowsManager.Forms;

import nds.TouchPosition;
import nds.pstros.video.NDSFont;
import nds.pstros.video.NDSGraphics;
import ru.develgame.JNDSWindowsManager.Actions.JNDSClickAction;
import ru.develgame.JNDSWindowsManager.Components.JNDSButton;
import ru.develgame.JNDSWindowsManager.Components.JNDSLabel;

/**
 *
 * @author Ilya Zemskov
 */
public class JNDSKeyboardForm extends JNDSComponentsForm {
    private final String[] text = new String[1];
    private final JNDSLabel jNDSLabel;
    private int cursorPos = 0;

    private static final int LEFT_BORDER_X = 10;
    private static final int TOP_BORDER_Y = 20;
    private static final int CURSOR_HEIGHT = 2;
    private static final int MAX_TEXT_LENGTH = 34;

    public JNDSKeyboardForm(String lText) {
        if (lText != null)
            text[0] = lText;
        else
            text[0] = "";

        cursorPos = text[0].length() + 1;

        jNDSLabel = new JNDSLabel(text[0], LEFT_BORDER_X, TOP_BORDER_Y);
        addComponent(jNDSLabel);

        int currentPos = LEFT_BORDER_X;
        for (int i = 1; i <= 9; i++) {
            JNDSButton jndsButton = new JNDSButton(Integer.toString(i), currentPos, 50);
            jndsButton.setClickAction(new KeyClickAction(this, Integer.toString(i)));

            currentPos += jndsButton.getWidth() + 1;

            addComponent(jndsButton);
        }
    }

    public String getText() {
        return text[0];
    }

    public void setText(String text) {
        if (text != null) {
            this.text[0] = text;
            jNDSLabel.setText(this.text[0]);
            cursorPos = this.text[0].length() + 1;
        }
    }

    public void paint(NDSGraphics g, NDSFont fnt) {
        super.paint(g, fnt);

        String substring = text[0].substring(0, cursorPos - 1);

        g.setColor(0x000000);
        g.fillRect(
                fnt.getStringWidth(substring) + LEFT_BORDER_X,
                TOP_BORDER_Y + fnt.getHeight() - CURSOR_HEIGHT,
                fnt.getStringWidth("A"),
                CURSOR_HEIGHT);
    }

    private static class KeyClickAction extends JNDSClickAction {
        private final String symbol;
        private final JNDSKeyboardForm jNDSKeyboardForm;

        public KeyClickAction(JNDSKeyboardForm jNDSKeyboardForm, String symbol) {
            this.jNDSKeyboardForm = jNDSKeyboardForm;
            this.symbol = symbol;
        }

        public void action(TouchPosition tp) {
            jNDSKeyboardForm.setText(jNDSKeyboardForm.getText() + symbol);
        }
    }
}
