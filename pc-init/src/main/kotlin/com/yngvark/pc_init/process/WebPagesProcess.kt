package com.yngvark.pc_init.process

import com.yngvark.pc_init.robot.RobotHelper
import com.yngvark.pc_init.secretGetter
import java.awt.event.KeyEvent

class WebPagesProcess(private val robot: RobotHelper) {
    fun run() {
        println("Process: ${javaClass.simpleName}")

        val password = secretGetter.getPasswordFromWebsite("Oslo Kommune AD byr281592")

        robot.pressAndRelease(KeyEvent.VK_CONTROL, KeyEvent.VK_T).sleep(50)
        robot.type("https://mail.google.com/mail/u/0/#inbox").enter()

        robot.pressAndRelease(KeyEvent.VK_CONTROL, KeyEvent.VK_T).sleep(50)
        robot.type("https://mail.google.com/mail/u/2/#inbox").enter()

        robot.pressAndRelease(KeyEvent.VK_CONTROL, KeyEvent.VK_T).sleep(50)
        robot.type("https://www.notion.so").enter()

        robot.pressAndRelease(KeyEvent.VK_CONTROL, KeyEvent.VK_T).sleep(50)
        // Super cool URL that doesn't prompt for username and password via a stupid alert box, when Outlook is stuck in that state
        robot.type("https://webmail.oslo.kommune.no/owa/auth/logon.aspx?replaceCurrent=1&url=http%3a%2f%2fwebmail.oslo.kommune.no%2fowa").enter().sleep(3000)
        robot.type(password).enter().sleep(300)
    }

}
