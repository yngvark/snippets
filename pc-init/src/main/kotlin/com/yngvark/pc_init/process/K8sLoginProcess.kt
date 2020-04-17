package com.yngvark.pc_init.process

import com.yngvark.pc_init.robot.RobotHelper
import java.awt.event.KeyEvent

class K8sLoginProcess(private val robot: RobotHelper) {

    fun run() {
        robot.pressAndRelease(KeyEvent.VK_F10).sleep(500) // Open Guake Terminal
        robot.pressAndRelease(KeyEvent.VK_CONTROL, KeyEvent.VK_SHIFT, KeyEvent.VK_T).sleep(1500)
        robot.type("k8").enter().sleep(4000)

        robot.pressAndRelease(KeyEvent.VK_CONTROL, KeyEvent.VK_D)
        robot.pressAndRelease(KeyEvent.VK_F10)
    }
}