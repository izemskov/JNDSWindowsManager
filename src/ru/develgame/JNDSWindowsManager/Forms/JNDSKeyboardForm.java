/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */

package ru.develgame.JNDSWindowsManager.Forms;

import ru.develgame.JNDSWindowsManager.Components.JNDSButton;
import ru.develgame.JNDSWindowsManager.Components.JNDSLabel;

/**
 *
 * @author Ilya Zemskov
 */
public class JNDSKeyboardForm extends JNDSComponentsForm {
    private String text;

    public JNDSKeyboardForm(String text) {
        this.text = text;

        addComponent(new JNDSLabel(text, 10, 20));

        int currentPos = 10;
        for (int i = 1; i <= 9; i++) {
            JNDSButton jndsButton = new JNDSButton(Integer.toString(i), currentPos, 50);

            currentPos += jndsButton.getWidth() + 1;

            addComponent(jndsButton);
        }
    }

    public String getText() {
        return text;
    }
}
