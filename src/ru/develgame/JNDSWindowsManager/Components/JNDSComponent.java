/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */

package ru.develgame.JNDSWindowsManager.Components;

import nds.TouchPosition;
import nds.pstros.video.NDSFont;
import nds.pstros.video.NDSGraphics;
import ru.develgame.JNDSWindowsManager.Forms.JNDSComponentsForm;

/**
 *
 * @author Ilya Zemskov
 */
public interface JNDSComponent {
    void paint(NDSGraphics g, NDSFont fnt);
    boolean isClicked(TouchPosition tp);
    void clickEvent(TouchPosition tp);
    void setParent(JNDSComponentsForm parent);
}
