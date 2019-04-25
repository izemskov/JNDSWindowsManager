/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */

package ru.develgame.JNDSWindowsManager.Forms;

import java.util.Enumeration;
import java.util.Vector;
import nds.TouchPosition;
import nds.pstros.video.NDSFont;
import nds.pstros.video.NDSGraphics;
import ru.develgame.JNDSWindowsManager.Components.JNDSComponent;

/**
 *
 * @author Ilya Zemskov
 */
public class JNDSDialogForm implements JNDSForm {
    public static final int FORM_TITLE_PANEL_HEIGHT = 20;
    public static final int FORM_TITLE_PANEL_BGCOLOR = 0x326690;

    public static final int FORM_POSX = 2;
    public static final int FORM_POSY = 2;
    public static final int FORM_WIDTH = 250;
    public static final int FORM_HEIGHT = 185;

    private Vector ndsComponents = new Vector();

    public JNDSDialogForm(JNDSForm parent) {
    }

    public void addComponent(JNDSComponent ndsComponent) {
        ndsComponents.addElement(ndsComponent);
    }

    public void paint(NDSGraphics g, NDSFont fnt) {
        g.setColor(0x000000);
        g.drawRect(FORM_POSX, FORM_POSY, FORM_WIDTH, FORM_HEIGHT);

        g.setColor(FORM_TITLE_PANEL_BGCOLOR);
        g.fillRect(FORM_POSX + 1, FORM_POSY + 1, FORM_WIDTH - 1, FORM_TITLE_PANEL_HEIGHT);

        Enumeration elements = ndsComponents.elements();
        while (elements.hasMoreElements()) {
            JNDSComponent component = (JNDSComponent) elements.nextElement();
            component.paint(g, fnt);
        }
    }

    public void clickEvent(TouchPosition tp) {
        Enumeration elements = ndsComponents.elements();
        while (elements.hasMoreElements()) {
            JNDSComponent component = (JNDSComponent) elements.nextElement();
            if (component.isClicked(tp))
                component.clickEvent(tp);
        }
    }
}