/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */

package ru.develgame.JNDSWindowsManager.Actions;

/**
 *
 * @author Ilya Zemskov
 */
public interface JNDSAction {
    void action();
    int getActionProcessStatus();
    void startAction();
}
