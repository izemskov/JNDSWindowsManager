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
public class JNDSKeyboardForm extends JNDSDialogForm {
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

        // 1 - 9 buttons
        int currentPosX = LEFT_BORDER_X;
        int currentPosY = 50;
        for (int i = 1; i <= 9; i++) {
            JNDSButton jndsButton = new JNDSButton(Integer.toString(i), currentPosX, currentPosY);
            jndsButton.setClickAction(new KeyClickAction(this, Integer.toString(i)));

            currentPosX += jndsButton.getWidth() + 1;

            addComponent(jndsButton);
        }

        // 0 button
        JNDSButton jndsButton = new JNDSButton(Integer.toString(0), currentPosX, currentPosY);
        jndsButton.setClickAction(new KeyClickAction(this, Integer.toString(0)));

        currentPosX += jndsButton.getWidth() + 1;

        addComponent(jndsButton);

        currentPosY += jndsButton.getHeight() + 1;

        // qwerty
        currentPosX = LEFT_BORDER_X;
        currentPosX += addButton("q", currentPosX, currentPosY) + 1;
        currentPosX += addButton("w", currentPosX, currentPosY) + 1;
        currentPosX += addButton("e", currentPosX, currentPosY) + 1;
        currentPosX += addButton("r", currentPosX, currentPosY) + 1;
        currentPosX += addButton("t", currentPosX, currentPosY) + 1;
        currentPosX += addButton("y", currentPosX, currentPosY) + 1;
        currentPosX += addButton("u", currentPosX, currentPosY) + 1;
        currentPosX += addButton("i", currentPosX, currentPosY) + 1;
        currentPosX += addButton("o", currentPosX, currentPosY) + 1;
        currentPosX += addButton("p", currentPosX, currentPosY) + 1;

        currentPosY += jndsButton.getHeight() + 1;

        currentPosX = LEFT_BORDER_X;
        currentPosX += addButton("a", currentPosX, currentPosY) + 1;
        currentPosX += addButton("s", currentPosX, currentPosY) + 1;
        currentPosX += addButton("d", currentPosX, currentPosY) + 1;
        currentPosX += addButton("f", currentPosX, currentPosY) + 1;
        currentPosX += addButton("g", currentPosX, currentPosY) + 1;
        currentPosX += addButton("h", currentPosX, currentPosY) + 1;
        currentPosX += addButton("j", currentPosX, currentPosY) + 1;
        currentPosX += addButton("k", currentPosX, currentPosY) + 1;
        currentPosX += addButton("l", currentPosX, currentPosY) + 1;

        currentPosY += jndsButton.getHeight() + 1;

        currentPosX = LEFT_BORDER_X;
        currentPosX += addButton("z", currentPosX, currentPosY) + 1;
        currentPosX += addButton("x", currentPosX, currentPosY) + 1;
        currentPosX += addButton("c", currentPosX, currentPosY) + 1;
        currentPosX += addButton("v", currentPosX, currentPosY) + 1;
        currentPosX += addButton("b", currentPosX, currentPosY) + 1;
        currentPosX += addButton("n", currentPosX, currentPosY) + 1;
        currentPosX += addButton("m", currentPosX, currentPosY) + 1;

        currentPosY += jndsButton.getHeight() + 1;
        currentPosX = LEFT_BORDER_X;
        JNDSButton jndsButtonOK = new JNDSButton("OK", currentPosX, currentPosY);
        jndsButtonOK.setClickAction(new JNDSClickAction() {
            public void action(TouchPosition tp) {
                answer = true;
                visible = false;
            }
        });
        addComponent(jndsButtonOK);

        currentPosX += jndsButtonOK.getWidth() + 1;
        JNDSButton jndsButtonCancel = new JNDSButton("Cancel", currentPosX, currentPosY);
        jndsButtonCancel.setClickAction(new JNDSClickAction() {
            public void action(TouchPosition tp) {
                answer = false;
                visible = false;
            }
        });
        addComponent(jndsButtonCancel);
    }

    private int addButton(String symbol, int currentPosX, int currentPosY) {
        JNDSButton jndsButton = new JNDSButton(symbol, currentPosX, currentPosY);
        jndsButton.setClickAction(new KeyClickAction(this, symbol));

        currentPosX += jndsButton.getWidth() + 1;

        addComponent(jndsButton);

        return jndsButton.getWidth();
    }

    public String getText() {
        return text[0];
    }

    public void setText(String text) {
        if (text != null) {
            if (text.length() <= MAX_TEXT_LENGTH) {
                this.text[0] = text;
                jNDSLabel.setText(this.text[0]);
                cursorPos = this.text[0].length() + 1;
            }
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
