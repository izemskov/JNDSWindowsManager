/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */

package ru.develgame.JNDSWindowsManager.Actions;

import java.util.Enumeration;
import java.util.Vector;

/**
 *
 * @author Ilya Zemskov
 */
public class JNDSActionQueueHandler implements Runnable {
    private Vector actionQueue = new Vector();

    private boolean stop = false;

    public static int ACTION_PROCESS_STATUS_WAIT = 0;
    public static int ACTION_PROCESS_STATUS_START = 1;
    public static int ACTION_PROCESS_STATUS_FINISH = 2;

    public Vector getActionQueue() {
        synchronized (this) {
            Vector vector = new Vector();

            Enumeration elements = actionQueue.elements();
            while (elements.hasMoreElements()) {
                JNDSAction action = (JNDSAction) elements.nextElement();
                vector.addElement(action);
            }

            return vector;
        }
    }

    public void addActionToQueue(JNDSAction action) {
        synchronized (this) {
            actionQueue.addElement(action);
        }
    }

    private void removeActionFromQueue(JNDSAction action) {
        synchronized (this) {
            actionQueue.removeElement(action);
        }
    }

    public boolean isStop() {
        synchronized (this) {
            return stop;
        }
    }

    public void setStop(boolean stop) {
        synchronized (this) {
            this.stop = stop;
        }
    }

    public void run() {
        while (!isStop()) {
            Enumeration elements = getActionQueue().elements();
            while (elements.hasMoreElements()) {
                final JNDSAction action = (JNDSAction) elements.nextElement();
                if (action.getActionProcessStatus() == ACTION_PROCESS_STATUS_FINISH) {
                    removeActionFromQueue(action);
                }
                else if (action.getActionProcessStatus() == ACTION_PROCESS_STATUS_WAIT) {
                    action.startAction();
                    new Thread(new Runnable() {
                        public void run() {
                            action.action();
                        }
                    }).start();
                }
            }
        }
    }
}
