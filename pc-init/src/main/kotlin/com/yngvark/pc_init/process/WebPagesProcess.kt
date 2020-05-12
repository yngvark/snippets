package com.yngvark.pc_init.process

import com.yngvark.pc_init.robot.RobotHelper
import com.yngvark.pc_init.secretGetter
import java.awt.event.KeyEvent

class WebPagesProcess(private val robot: RobotHelper) {
    fun run() {
        println("Process: ${javaClass.simpleName}")

        robot.pressAndRelease(KeyEvent.VK_CONTROL, KeyEvent.VK_T).sleep(50)
        robot.type("https://www.notion.so").enter()

        clockify()

        robot.pressAndRelease(KeyEvent.VK_CONTROL, KeyEvent.VK_T).sleep(50)
        robot.type("https://mail.google.com/mail/u/0/#inbox").enter()

        robot.pressAndRelease(KeyEvent.VK_CONTROL, KeyEvent.VK_T).sleep(50)
        robot.type("https://mail.google.com/mail/u/1/#inbox").enter()

        outlook()
    }

    private fun outlook() {
        robot.pressAndRelease(KeyEvent.VK_CONTROL, KeyEvent.VK_T).sleep(50)
        // Super cool URL that doesn't prompt for username and password via a stupid alert box, when Outlook is stuck in that state
        // robot.type("https://webmail.oslo.kommune.no/owa/auth/logon.aspx?replaceCurrent=1&url=http%3a%2f%2fwebmail.oslo.kommune.no%2fowa").enter().sleep(2000)
        robot.type("https://webmail.oslo.kommune.no/").enter().sleep(2000)

        // Use 1password login box
        robot
            .pressAndRelease(KeyEvent.VK_DOWN).sleep(50)
            .enter().sleep(50)
            .enter().sleep(50)
    }

    private fun clockify() {
        robot.pressAndRelease(KeyEvent.VK_CONTROL, KeyEvent.VK_T).sleep(50)
        robot.type("https://clockify.me/tracker").enter().sleep(2000)

        // Select project "Vanlig jobb"
        robot
            .pressAndRelease(KeyEvent.VK_TAB)
            .sleep(100)
            .type("jobb")
            .sleep(1000)
            .pressAndRelease(KeyEvent.VK_DOWN).sleep(300)
            .pressAndRelease(KeyEvent.VK_DOWN).sleep(300)
            .enter()

        robot
            .pressAndRelease(KeyEvent.VK_TAB).sleep(100)
            .pressAndRelease(KeyEvent.VK_TAB).sleep(100)
            .pressAndRelease(KeyEvent.VK_TAB).sleep(100)
            .enter()
    }

}
