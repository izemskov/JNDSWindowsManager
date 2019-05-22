/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */

import ru.develgame.JNDSWindowsManager.Actions.JNDSAction;
import ru.develgame.JNDSWindowsManager.Components.JNDSButton;
import ru.develgame.JNDSWindowsManager.Components.JNDSLabel;
import ru.develgame.JNDSWindowsManager.Forms.JNDSComponentsForm;
import ru.develgame.JNDSWindowsManager.Forms.JNDSKeyboardForm;
import ru.develgame.JNDSWindowsManager.JNDSWindowsManager;

/**
 *
 * @author Ilya Zemskov
 */
public class ExampleJNDSWindowsManager {
    public static void main(String[] args) {
        JNDSComponentsForm jNDSComponentsForm = new JNDSComponentsForm();

        final JNDSLabel jndsLabel = new JNDSLabel("Hello world!", 100, 50);
        jNDSComponentsForm.addComponent(jndsLabel);

        JNDSButton jndsButton = new JNDSButton("Test", 120, 100);
        JNDSAction jndsActionImpl = new JNDSAction() {
            public void action() {
                JNDSKeyboardForm jndsKeyboardForm = new JNDSKeyboardForm("Write a text");
                jndsKeyboardForm.setVisible(true);
                if (jndsKeyboardForm.isAnswer()) {
                    jndsLabel.setText(jndsKeyboardForm.getText());
                }
            }
        };
        jndsButton.setClickAction(jndsActionImpl);

        jNDSComponentsForm.addComponent(jndsButton);

        jNDSComponentsForm.setVisible(true);

        JNDSWindowsManager.instance().run();
    }
}
