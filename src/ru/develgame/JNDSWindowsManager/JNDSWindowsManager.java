/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */

package ru.develgame.JNDSWindowsManager;

import ru.develgame.JNDSWindowsManager.Forms.JNDSForm;
import java.util.Enumeration;
import java.util.Vector;
import nds.Bios;
import nds.Key;
import nds.TouchPosition;
import nds.Video;
import nds.pstros.video.NDSFont;
import nds.pstros.video.NDSGraphics;

/**
 *
 * @author Ilya Zemskov
 */
public class JNDSWindowsManager {
    private Vector ndsForms = new Vector();
    private NDSGraphics g;
    private NDSFont fnt;
    private TouchPosition tp;

    public static final int MAX_SCREEN_WIDTH = 256;
    public static final int MAX_SCREEN_HEIGHT = 192;

    public void run() {
        Video.lcdSwap();
        Video.initVideo();
        int videoAddr = Video.BG_BMP_RAM(0);

        g = new NDSGraphics();

        fnt = new NDSFont("system", 0, 12);
        g.setFont(fnt);

        g.setClip(0, 0, MAX_SCREEN_WIDTH, MAX_SCREEN_HEIGHT);

        g.setColor(0xFFFFFF);
        g.fillRect(0, 0, MAX_SCREEN_WIDTH, MAX_SCREEN_HEIGHT);

        tp = new TouchPosition();

        int keys  = Key.held();
        while ((keys & Key.START) == 0) {
            tp.update();

            if (tp.px != 0 && tp.py != 0)
                touchEvents();

            Key.scan();
            keys = Key.held();
            Bios.swiWaitForVBlank();
            paint();
        }
    }

    public void addForm(JNDSForm ndsForm) {
        ndsForms.addElement(ndsForm);
    }

    public void touchEvents() {
        Enumeration elements = ndsForms.elements();
        while (elements.hasMoreElements()) {
            JNDSForm form = (JNDSForm) elements.nextElement();
            form.clickEvent(tp);
        }
    }

    public void paint() {
        Enumeration elements = ndsForms.elements();
        while (elements.hasMoreElements()) {
            JNDSForm form = (JNDSForm) elements.nextElement();
            form.paint(g, fnt);
        }
    }
}
