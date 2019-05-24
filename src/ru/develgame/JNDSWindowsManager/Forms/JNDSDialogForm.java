/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */

package ru.develgame.JNDSWindowsManager.Forms;

/**
 *
 * @author Ilya Zemskov
 */
public class JNDSDialogForm extends JNDSComponentsForm {
    protected boolean answer = false;

    public boolean isAnswer() {
        return answer;
    }

    public void setVisible(boolean visible) {
        super.setVisible(visible);

        while (isVisible()) {
            synchronized (this) {
                try {
                    wait();
                }
                catch (InterruptedException ex) {
                }
            }
        }
    }
}
