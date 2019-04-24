/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */

package ru.develgame.JNDSWindowsManager.Actions;

import nds.TouchPosition;

/**
 *
 * @author Ilya Zemskov
 */
public abstract class JNDSClickAction {
    public abstract void action(TouchPosition tp);
}
