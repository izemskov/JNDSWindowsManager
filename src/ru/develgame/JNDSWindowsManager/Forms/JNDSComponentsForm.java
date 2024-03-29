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
import ru.develgame.JNDSWindowsManager.Actions.JNDSAction;
import ru.develgame.JNDSWindowsManager.Actions.JNDSActionQueueHandler;
import ru.develgame.JNDSWindowsManager.Components.JNDSComponent;
import ru.develgame.JNDSWindowsManager.JNDSWindowsManager;

/**
 *
 * @author Ilya Zemskov
 */
public class JNDSComponentsForm implements JNDSForm {
    public static final int FORM_TITLE_PANEL_HEIGHT = 22;
    public static final int FORM_TITLE_PANEL_BGCOLOR = 0x326690;

    public static final int FORM_POSX = 2;
    public static final int FORM_POSY = 2;
    public static final int FORM_WIDTH = 250;
    public static final int FORM_HEIGHT = 185;

    private static final int PADDING_TITLE_WIDTH = 5;

    protected final Vector ndsComponents = new Vector();

    protected boolean visible = false;

    private JNDSActionQueueHandler jndsActionQueueHandler;
    private Thread jndsActionQueueHandlerThread;

    protected String title;

    public JNDSComponentsForm() {
        jndsActionQueueHandler = new JNDSActionQueueHandler();
        jndsActionQueueHandlerThread = new Thread(jndsActionQueueHandler);
    }

    public boolean isVisible() {
        synchronized (this) {
            return visible;
        }
    }

    public void setVisible(boolean visible) {
        synchronized (this) {
            this.visible = visible;
            if (visible) {
                jndsActionQueueHandler.setStop(false);
                jndsActionQueueHandlerThread.start();

                JNDSWindowsManager.instance().addForm(this);
                JNDSWindowsManager.instance().repaint();
            }
            else {
                jndsActionQueueHandler.setStop(true);

                JNDSWindowsManager.instance().removeForm(this);
                JNDSWindowsManager.instance().repaint();
                notify();
            }
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addActionToQueue(JNDSAction action) {
        jndsActionQueueHandler.addActionToQueue(action);
    }

    public void addComponent(JNDSComponent ndsComponent) {
        ndsComponent.setParent(this);
        ndsComponents.addElement(ndsComponent);
    }

    public void paint(NDSGraphics g, NDSFont fnt) {
        g.setColor(0x000000);
        g.drawRect(FORM_POSX, FORM_POSY, FORM_WIDTH, FORM_HEIGHT);

        g.setColor(FORM_TITLE_PANEL_BGCOLOR);
        g.fillRect(FORM_POSX + 1, FORM_POSY + 1, FORM_WIDTH - 1, FORM_TITLE_PANEL_HEIGHT);

        if (title != null && title.length() > 0) {
            g.setColor(0xFFFFFF);
            g.drawString(title, FORM_POSX + 1 + PADDING_TITLE_WIDTH, FORM_POSY + 1 + fnt.getHeight());
        }

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
