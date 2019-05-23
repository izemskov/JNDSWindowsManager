/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */

package ru.develgame.JNDSWindowsManager.Actions;

import ru.develgame.JNDSWindowsManager.JNDSWindowsManager;

/**
 *
 * @author Ilya Zemskov
 */
public class JNDSActionWrapper {
    private JNDSAction action;
    private int actionStatus = ru.develgame.JNDSWindowsManager.Actions.JNDSActionQueueHandler.ACTION_PROCESS_STATUS_WAIT;

    public JNDSActionWrapper(JNDSAction action) {
        this.action = action;
    }

    public int getActionProcessStatus() {
        synchronized (this) {
            return actionStatus;
        }
    }

    public void setActionStatus(int actionStatus) {
        synchronized (this) {
            this.actionStatus = actionStatus;
        }
    }

    public void runAction() {
        //System.out.println("Run Action");
        action.action();

        setActionStatus(ru.develgame.JNDSWindowsManager.Actions.JNDSActionQueueHandler.ACTION_PROCESS_STATUS_FINISH);
        //System.out.println("Need repaint");
        JNDSWindowsManager.instance().repaint();
    }
}
