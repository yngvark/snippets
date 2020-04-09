package com.yngvark.pc_init.process

import com.yngvark.pc_init.robot.RobotHelper
import java.awt.event.KeyEvent

class GmailProcess(private val robot: RobotHelper) {
    fun run() {
        robot.pressAndRelease(KeyEvent.VK_CONTROL, KeyEvent.VK_T).sleep(50)
        robot.type("https://mail.google.com/mail/u/0/#inbox").enter()
        robot.pressAndRelease(KeyEvent.VK_CONTROL, KeyEvent.VK_T).sleep(50)
        robot.type("https://mail.google.com/mail/u/2/#inbox").enter()
    }

}
