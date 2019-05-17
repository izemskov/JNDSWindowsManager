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
import ru.develgame.JNDSWindowsManager.Events.JNDSEvent;
import ru.develgame.JNDSWindowsManager.Events.JNDSEventsManager;

/**
 *
 * @author Ilya Zemskov
 */
public class JNDSWindowsManager {
    private JNDSWindowsManager() {}
    private static final JNDSWindowsManager instance = new JNDSWindowsManager();
    public static JNDSWindowsManager instance() {return instance;}

    private final Vector ndsForms = new Vector();
    private NDSGraphics graphic;
    private final NDSFont fnt = new NDSFont("system", 0, 12);
    private TouchPosition tp;

    private boolean needRepaintBackground = false;

    public static final int MAX_SCREEN_WIDTH = 256;
    public static final int MAX_SCREEN_HEIGHT = 192;

    public NDSGraphics getGraphic() {
        return graphic;
    }

    public TouchPosition getTp() {
        return tp;
    }

    public boolean isNeedRepaintBackground() {
        synchronized (instance) {
            return needRepaintBackground;
        }
    }

    public void setNeedRepaintBackground(boolean needRepaintBackground) {
        synchronized (instance) {
            this.needRepaintBackground = needRepaintBackground;
        }
    }

    public void run() {
        Video.lcdSwap();
        Video.initVideo();
        int videoAddr = Video.BG_BMP_RAM(0);

        graphic = new NDSGraphics();

        graphic.setFont(fnt);

        graphic.setClip(0, 0, MAX_SCREEN_WIDTH, MAX_SCREEN_HEIGHT);

        graphic.setColor(0xFFFFFF);
        graphic.fillRect(0, 0, MAX_SCREEN_WIDTH, MAX_SCREEN_HEIGHT);

        tp = new TouchPosition();

        JNDSEventsManager.instance().subscribeOnEvent(ru.develgame.JNDSWindowsManager.Events.JNDSEventsManager.BACKGROUND_REPAINT_EVENT,
                new JNDSEvent() {
                        public void action() {
                            needRepaintBackground = true;
                        }
                }
        );

        MainLoop mainLoop = new MainLoop();
        Thread thread = new Thread(mainLoop);
        thread.run();
    }

    public void addForm(JNDSForm ndsForm) {
        synchronized (instance) {
            ndsForms.addElement(ndsForm);
        }
    }

    public void removeForm(JNDSForm ndsForm) {
        synchronized (instance) {
            ndsForms.removeElement(ndsForm);
        }
    }

    public Vector getNdsForms() {
        synchronized (instance) {
            Vector vector = new Vector();

            Enumeration elements = ndsForms.elements();
            while (elements.hasMoreElements()) {
                JNDSForm form = (JNDSForm) elements.nextElement();
                vector.addElement(form);
            }

            return vector;
        }
    }

    public NDSFont getFnt() {
        return fnt;
    }

    private static class MainLoop implements Runnable {
        private int lastTPx = 0;
        private int lastTPy = 0;

        public MainLoop() {
        }

        private void touchEvents() {
            Vector ndsForms = JNDSWindowsManager.instance().getNdsForms();
            Enumeration elements = ndsForms.elements();
            while (elements.hasMoreElements()) {
                JNDSForm form = (JNDSForm) elements.nextElement();
                if (lastTPx != JNDSWindowsManager.instance().getTp().px ||
                        lastTPy != JNDSWindowsManager.instance().getTp().py)
                {
                    form.clickEvent(JNDSWindowsManager.instance().getTp());
                }
            }

            lastTPx = JNDSWindowsManager.instance().getTp().px;
            lastTPy = JNDSWindowsManager.instance().getTp().py;
        }

        public void paint() {
            NDSGraphics graphic = JNDSWindowsManager.instance().getGraphic();
            if (JNDSWindowsManager.instance().isNeedRepaintBackground()) {
                graphic.setColor(0xFFFFFF);
                graphic.fillRect(0, 0, MAX_SCREEN_WIDTH, MAX_SCREEN_HEIGHT);
                JNDSWindowsManager.instance().setNeedRepaintBackground(false);
            }

            Vector ndsForms = JNDSWindowsManager.instance().getNdsForms();

            if (!ndsForms.isEmpty()) {
                for (int i = ndsForms.size() - 1; i >= 0; i--) {
                    JNDSForm form = (JNDSForm) ndsForms.elementAt(i);
                    if (form.isVisible()) {
                        form.paint(graphic, JNDSWindowsManager.instance().getFnt());
                        break;
                    }
                }
            }
        }

        public void run() {
            int keys  = Key.held();
            while ((keys & Key.START) == 0) {
                JNDSWindowsManager.instance().getTp().update();

                if (JNDSWindowsManager.instance().getTp().px != 0 &&
                        JNDSWindowsManager.instance().getTp().py != 0)
                {
                    touchEvents();
                }
                else {
                    lastTPx = 0;
                    lastTPy = 0;
                }

                Key.scan();
                keys = Key.held();
                Bios.swiWaitForVBlank();
                paint();
            }
        }
    }
}
