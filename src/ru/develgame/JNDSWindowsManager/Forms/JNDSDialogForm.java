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
        if (visible)
            System.out.println("Start visible");

        super.setVisible(visible);

        while (isVisible()) {
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException ex) {
            }
        }

        if (visible)
            System.out.println("Stop visible");        
    }
}
