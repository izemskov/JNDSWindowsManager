/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */

package ru.develgame.JNDSWindowsManager.Components;

import ru.develgame.JNDSWindowsManager.Forms.JNDSComponentsForm;

/**
 *
 * @author Ilya Zemskov
 */
public abstract class JNDSAbstractComponent implements JNDSComponent {
    protected JNDSComponentsForm parent;
    protected int posX;
    protected int posY;

    public JNDSAbstractComponent(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setParent(JNDSComponentsForm parent) {
        this.parent = parent;
    }
}
