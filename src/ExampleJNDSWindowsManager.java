/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */

import ru.develgame.JNDSWindowsManager.Components.JNDSLabel;
import ru.develgame.JNDSWindowsManager.JNDSDialogForm;
import ru.develgame.JNDSWindowsManager.JNDSWindowsManager;

/**
 *
 * @author Ilya Zemskov
 */
public class ExampleJNDSWindowsManager {
    public static void main(String[] args) {
        JNDSWindowsManager jndsWindowsManager = new JNDSWindowsManager();

        JNDSDialogForm jndsDialogForm = new JNDSDialogForm(null);
        jndsDialogForm.addComponent(new JNDSLabel("Hello world!", 100, 100));

        jndsWindowsManager.addForm(jndsDialogForm);
        jndsWindowsManager.run();
    }
}
