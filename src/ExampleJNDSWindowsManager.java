/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */

import ru.develgame.JNDSWindowsManager.Actions.JNDSAction;
import ru.develgame.JNDSWindowsManager.Components.JNDSButton;
import ru.develgame.JNDSWindowsManager.Components.JNDSLabel;
import ru.develgame.JNDSWindowsManager.Forms.JNDSComponentsForm;
import ru.develgame.JNDSWindowsManager.Forms.JNDSDialogForm;
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

        JNDSButton jndsButtonDialogForm = new JNDSButton("Show dialog form", 120, 130);
        JNDSAction jndsActionImplDialogForm = new JNDSAction() {
            public void action() {
                final JNDSDialogForm jndsDialogForm = new JNDSDialogForm();

                JNDSButton jndsButtonClose = new JNDSButton("Close", 10, 130);
                jndsButtonClose.setClickAction(new JNDSAction() {
                    public void action() {
                        jndsDialogForm.setVisible(false);
                    }
                });
                jndsDialogForm.addComponent(jndsButtonClose);

                JNDSLabel jndsLabelDialogForm = new JNDSLabel("This is Dialog Form!", 100, 50);
                jndsDialogForm.addComponent(jndsLabel);

                jndsDialogForm.setVisible(true);
            }
        };
        jndsButtonDialogForm.setClickAction(jndsActionImplDialogForm);

        jNDSComponentsForm.addComponent(jndsButtonDialogForm);

        jNDSComponentsForm.setVisible(true);

        JNDSWindowsManager.instance().run();
    }
}
