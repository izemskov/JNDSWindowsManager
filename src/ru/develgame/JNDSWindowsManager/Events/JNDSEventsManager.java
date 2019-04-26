/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */

package ru.develgame.JNDSWindowsManager.Events;

import java.util.Enumeration;
import java.util.Vector;

/**
 *
 * @author Ilya Zemskov
 */
public class JNDSEventsManager {
    private JNDSEventsManager() {}
    private static final JNDSEventsManager instance = new JNDSEventsManager();
    public static JNDSEventsManager instance() {return instance;}

    private final Vector listeners = new Vector();

    public static final String BACKGROUND_REPAINT_EVENT = "backgroundRepaintEvent";

    public void subscribeOnEvent(String eventName, JNDSEvent event) {
        listeners.addElement(new JNDSEventListener(eventName, event));
    }

    public void sentEvent(String eventName) {
        Enumeration elements = listeners.elements();
        while (elements.hasMoreElements()) {
            JNDSEventListener listener = (JNDSEventListener) elements.nextElement();
            if (listener.getEventName().equals(eventName))
                listener.getEvent().action();
        }
    }
}
