package com.yngvark.pc_init.robot

import com.yngvark.pc_init.process.common.CHROME_COMMAND
import com.yngvark.pc_init.robot.RobotHelper
import java.awt.event.KeyEvent

class OnePasswordGetter(private val robot: RobotHelper) {
    fun getSecret(secretName:String):String {
        robot.run(CHROME_COMMAND) // Open chrome
        robot.sleep(500)
        robot.pressAndRelease(KeyEvent.VK_CONTROL, KeyEvent.VK_PERIOD).sleep(500) // Open 1password
        robot.type(secretName, charPause=30)

        robot.pressAndRelease(KeyEvent.VK_RIGHT).enter().sleep(50) // Copy secret to clipboard
        val secret = robot.getClipboardContents()
        robot.clearClipboardContents()

        robot.pressAndRelease(KeyEvent.VK_CONTROL, KeyEvent.VK_W) // Close chrome tab

        return secret
    }
}