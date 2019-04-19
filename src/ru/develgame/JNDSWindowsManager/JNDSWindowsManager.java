/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 * 
 * Copyright 2019 Ilya Zemskov */

package ru.develgame.JNDSWindowsManager;

import java.util.Iterator;
import java.util.Vector;
import nds.Bios;
import nds.Key;
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
    
    public static int MAX_SCREEN_WIDTH = 256;
    public static int MAX_SCREEN_HEIGHT = 192;
    
    public JNDSWindowsManager() {
        Video.initVideo();
        int videoAddr = Video.BG_BMP_RAM(0);
        
        NDSGraphics g = new NDSGraphics();
		
        NDSFont fnt = new NDSFont("system", 0, 12);
        g.setFont(fnt);
        
        g.setClip(0, 0, MAX_SCREEN_WIDTH, MAX_SCREEN_HEIGHT);                
        
        int keys  = Key.held();
        while ((keys & Key.START) == 0) {
            Key.scan();
            keys = Key.held();
            Bios.swiWaitForVBlank();
            paint();
        }
    }
        
    public void paint() {
        g.setColor(0xFFFFFF);
        g.fillRect(0, 0, MAX_SCREEN_WIDTH, MAX_SCREEN_HEIGHT);
        
        Iterator iterator = ndsForms.iterator();
        while (iterator.hasNext()) {
            JNDSForm form = (JNDSForm) iterator.next();
            form.paint(g);
        }
    }
}
