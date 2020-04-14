package com.yngvark.pc_init.process

import com.yngvark.pc_init.robot.RobotHelper
import com.yngvark.pc_init.secretGetter
import java.awt.event.KeyEvent

class WebPagesProcess(private val robot: RobotHelper) {
    fun run() {
        val password = secretGetter.getPasswordFromWebsite("Oslo Kommune AD byr281592")

        robot.pressAndRelease(KeyEvent.VK_CONTROL, KeyEvent.VK_T).sleep(50)
        robot.type("https://mail.google.com/mail/u/0/#inbox").enter()

        robot.pressAndRelease(KeyEvent.VK_CONTROL, KeyEvent.VK_T).sleep(50)
        robot.type("https://mail.google.com/mail/u/2/#inbox").enter()

        robot.pressAndRelease(KeyEvent.VK_CONTROL, KeyEvent.VK_T).sleep(50)
        robot.type("https://webmail.oslo.kommune.no").enter().sleep(1000)
        robot.type(password).enter().sleep(300)
    }

}
