/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

/**
 *
 * @author fbo
 */
public class FrameMonitor {

    /**
     * True if the frame should pause execution and wait for "continue" event
     */
    public boolean _pauseExecution = false;

    public boolean isPaused() {
        return _pauseExecution;
    }

}
