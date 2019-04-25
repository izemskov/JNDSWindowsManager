/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */

package ru.develgame.JNDSWindowsManager.Events;

/**
 *
 * @author Ilya Zemskov
 */
public class JNDSEventListener {
    private String eventName;
    private JNDSEvent event;

    public String getEventName() {
        return eventName;
    }

    public JNDSEvent getEvent() {
        return event;
    }

    public JNDSEventListener(String eventName, JNDSEvent event) {
        this.eventName = eventName;
        this.event = event;
    }
}
