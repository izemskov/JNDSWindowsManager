/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 * 
 * Copyright 2019 Ilya Zemskov */

package ru.develgame.JNDSWindowsManager;

import nds.pstros.video.NDSGraphics;

/**
 *
 * @author Ilya Zemskov
 */
public class JNDSDialogForm implements JNDSForm {
    private int posX;
    private int posY;
    private int width;
    private int height;

    public JNDSDialogForm(JNDSForm parent, int posX, int posY, int width, int height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }
    
    public void paint(NDSGraphics g) {
        
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
