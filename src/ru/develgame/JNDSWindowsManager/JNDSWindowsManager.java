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
import ru.develgame.JNDSWindowsManager.Actions.JNDSAction;
import ru.develgame.JNDSWindowsManager.Actions.JNDSActionQueueHandler;

/**
 *
 * @author Ilya Zemskov
 */
public class JNDSWindowsManager {
    private JNDSWindowsManager() {}
    private static final JNDSWindowsManager instance = new JNDSWindowsManager();
    public static JNDSWindowsManager instance() {return instance;}

    private final Vector ndsForms = new Vector();
    private NDSGraphics g;
    private final NDSFont fnt = new NDSFont("system", 0, 12);
    private TouchPosition tp;
    private JNDSActionQueueHandler jndsActionQueueHandler;
    private Thread jndsActionQueueHandlerThread;

    private int lastTPx = 0;
    private int lastTPy = 0;

    private boolean needRepaint = true;

    public static final int MAX_SCREEN_WIDTH = 256;
    public static final int MAX_SCREEN_HEIGHT = 192;

    public void run() {
        Video.lcdSwap();
        Video.initVideo();
        int videoAddr = Video.BG_BMP_RAM(0);

        g = new NDSGraphics();

        g.setFont(fnt);

        g.setClip(0, 0, MAX_SCREEN_WIDTH, MAX_SCREEN_HEIGHT);

        g.setColor(0xFFFFFF);
        g.fillRect(0, 0, MAX_SCREEN_WIDTH, MAX_SCREEN_HEIGHT);

        tp = new TouchPosition();

        jndsActionQueueHandler = new JNDSActionQueueHandler();
        jndsActionQueueHandlerThread = new Thread(jndsActionQueueHandler);
        jndsActionQueueHandlerThread.start();

        int keys  = Key.held();
        while ((keys & Key.START) == 0) {
            tp.update();

            if (tp.px != 0 || tp.py != 0)
                touchEvents();
            else {
                lastTPx = 0;
                lastTPy = 0;
            }

            Key.scan();
            keys = Key.held();
            Bios.swiWaitForVBlank();
            paint();
        }

        jndsActionQueueHandler.setStop(true);
        try {
            jndsActionQueueHandlerThread.join();
        }
        catch (InterruptedException ex) {
            System.out.println("Problem with stop action thread");
        }
    }

    public void addActionToQueue(JNDSAction action) {
        jndsActionQueueHandler.addActionToQueue(action);
    }

    public void addForm(JNDSForm ndsForm) {
        synchronized (this) {
            ndsForms.addElement(ndsForm);
        }
    }

    public void removeForm(JNDSForm ndsForm) {
        synchronized (this) {
            ndsForms.removeElement(ndsForm);
        }
    }

    public Vector getNdsForms() {
        synchronized (this) {
            Vector vector = new Vector();

            Enumeration elements = ndsForms.elements();
            while (elements.hasMoreElements()) {
                JNDSForm action = (JNDSForm) elements.nextElement();
                vector.addElement(action);
            }

            return vector;
        }
    }

    public void touchEvents() {
        if (lastTPx != tp.px || lastTPy != tp.py) {
            Vector ndsFormsCopy = getNdsForms();
            if (!ndsFormsCopy.isEmpty()) {
                for (int i = ndsFormsCopy.size() - 1; i >= 0; i--) {
                    JNDSForm form = (JNDSForm) ndsFormsCopy.elementAt(i);
                    if (form.isVisible()) {
                        form.clickEvent(tp);
                        break;
                    }
                }
            }

            lastTPx = tp.px;
            lastTPy = tp.py;
        }
    }

    public boolean isNeedRepaint() {
        synchronized (this) {
            return needRepaint;
        }
    }

    public void repaint() {
        synchronized (this) {
            needRepaint = true;
            System.out.println("Need repaint");
        }
    }

    public void setNeedRepaint(boolean needRepaint) {
        synchronized (this) {
            this.needRepaint = needRepaint;
        }
    }

    public void paint() {
        if (isNeedRepaint()) {
            System.out.println("Repaint");
            g.setColor(0xFFFFFF);
            g.fillRect(0, 0, MAX_SCREEN_WIDTH, MAX_SCREEN_HEIGHT);

            Vector ndsFormsCopy = getNdsForms();

            if (!ndsFormsCopy.isEmpty()) {
                for (int i = ndsFormsCopy.size() - 1; i >= 0; i--) {
                    JNDSForm form = (JNDSForm) ndsFormsCopy.elementAt(i);
                    if (form.isVisible()) {
                        form.paint(g, fnt);
                        break;
                    }
                }
            }

            setNeedRepaint(false);
        }
    }

    public NDSFont getFnt() {
        return fnt;
    }
}
