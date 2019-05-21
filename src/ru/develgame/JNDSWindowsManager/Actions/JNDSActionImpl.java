/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */

package ru.develgame.JNDSWindowsManager.Actions;

import ru.develgame.JNDSWindowsManager.Events.JNDSEventsManager;

/**
 *
 * @author Ilya Zemskov
 */
public class JNDSActionImpl implements JNDSAction {
    protected int actionStatus = ru.develgame.JNDSWindowsManager.Actions.JNDSActionQueueHandler.ACTION_PROCESS_STATUS_WAIT;

    public int getActionProcessStatus() {
        synchronized (this) {
            return actionStatus;
        }
    }

    public void startAction() {
        synchronized (this) {
            actionStatus = ru.develgame.JNDSWindowsManager.Actions.JNDSActionQueueHandler.ACTION_PROCESS_STATUS_START;
        }
    }

    public void action() {
        actionStatus = ru.develgame.JNDSWindowsManager.Actions.JNDSActionQueueHandler.ACTION_PROCESS_STATUS_FINISH;
        JNDSEventsManager.instance().sentEvent(
            ru.develgame.JNDSWindowsManager.Events.JNDSEventsManager.BACKGROUND_REPAINT_EVENT
        );
    }
}
