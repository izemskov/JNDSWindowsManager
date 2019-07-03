/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */

import ru.develgame.JNDSWindowsManager.Actions.JNDSAction;
import ru.develgame.JNDSWindowsManager.Components.JNDSButton;
import ru.develgame.JNDSWindowsManager.Components.JNDSLabel;
import ru.develgame.JNDSWindowsManager.Components.JNDSTextField;
import ru.develgame.JNDSWindowsManager.Forms.JNDSComponentsForm;
import ru.develgame.JNDSWindowsManager.Forms.JNDSDialogForm;
import ru.develgame.JNDSWindowsManager.JNDSWindowsManager;

/**
 *
 * @author Ilya Zemskov
 */
public class ExampleJNDSWindowsManager {
    public static void main(String[] args) {
        JNDSComponentsForm jNDSComponentsForm = new JNDSComponentsForm();

        final JNDSLabel jndsLabel = new JNDSLabel("Simple label", 20, 30);
        jNDSComponentsForm.addComponent(jndsLabel);

        JNDSTextField jndsTextField = new JNDSTextField("Hello world", 20, 58, 150);
        jNDSComponentsForm.addComponent(jndsTextField);

        JNDSButton jndsButtonDialogForm = new JNDSButton("Show dialog form", 20, 90);
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

                JNDSLabel jndsLabelDialogForm = new JNDSLabel("This is Dialog Form!", 70, 80);
                jndsDialogForm.addComponent(jndsLabelDialogForm);

                jndsDialogForm.setVisible(true);
            }
        };
        jndsButtonDialogForm.setClickAction(jndsActionImplDialogForm);

        jNDSComponentsForm.addComponent(jndsButtonDialogForm);

        jNDSComponentsForm.setVisible(true);

        JNDSWindowsManager.instance().run();
    }
}
