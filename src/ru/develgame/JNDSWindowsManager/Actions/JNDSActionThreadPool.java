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
public class JNDSActionThreadPool {
    private Vector threads = new Vector(DEFAULT_THREAD_COUNT);

    public JNDSActionThreadPool() {
        for (int i = 0; i < DEFAULT_THREAD_COUNT; i++) {
            JNDSActionThread jndsActionThread = new JNDSActionThread();
            jndsActionThread.start();
            threads.addElement(jndsActionThread);
        }
    }

    private static final int DEFAULT_THREAD_COUNT = 4;

    public void stopThreads() {
        Enumeration elements = threads.elements();
        while (elements.hasMoreElements()) {
            JNDSActionThread actionThread = (JNDSActionThread) elements.nextElement();
            actionThread.setStop();

            try {
                actionThread.join();
            }
            catch (InterruptedException ex) {
                System.out.println("Problem with stop thread");
            }
        }
    }

    public void runWork(JNDSActionWrapper actionWrapper) {
        boolean find = false;

        Enumeration elements = threads.elements();
        while (elements.hasMoreElements()) {
            JNDSActionThread actionThread = (JNDSActionThread) elements.nextElement();
            if (actionThread.getActionWrapper() == null) {
                System.out.println("Wake up1");
                actionThread.setActionWrapper(actionWrapper);
                find = true;
                break;
            }
        }

        if (!find) {
            JNDSActionThread actionThread = new JNDSActionThread();
            actionThread.start();
            threads.addElement(actionThread);
            System.out.println("Wake up3");
            actionThread.setActionWrapper(actionWrapper);
        }
    }

    public static class JNDSActionThread extends Thread {
        private JNDSActionWrapper actionWrapper = null;
        private boolean stop = false;

        public boolean isStop() {
            synchronized (this) {
                return stop;
            }
        }

        public void setStop() {
            synchronized (this) {
                stop = true;
                notify();
            }
        }

        public void setActionWrapper(JNDSActionWrapper actionWrapper) {
            synchronized (this) {
                this.actionWrapper = actionWrapper;
                if (this.actionWrapper != null)
                    notify();
            }
        }

        public JNDSActionWrapper getActionWrapper() {
            synchronized (this) {
                return actionWrapper;
            }
        }

        public void run() {
            //System.out.println("Start thread from pool");

            while (!isStop()) {
                JNDSActionWrapper action = getActionWrapper();
                if (action != null)
                {
                    if (action.getActionProcessStatus() == ru.develgame.JNDSWindowsManager.Actions.JNDSActionQueueHandler.ACTION_PROCESS_STATUS_START) {
                        action.runAction();
                    }

                    setActionWrapper(null);
                }

                synchronized (this) {
                    System.out.println("Sleep...");
                    try {
                        wait();
                    }
                    catch (InterruptedException ex) {
                    }
                }

                System.out.println("Wake up2");
            }

            System.out.println("Stop thread from pool");
        }
    }
}
