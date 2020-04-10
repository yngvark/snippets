package com.yngvark.pc_init.process

import com.yngvark.pc_init.process.common.CHROME_COMMAND
import com.yngvark.pc_init.robot.RobotHelper
import com.yngvark.pc_init.robot.SomewhatSecureString
import java.awt.event.KeyEvent

class LoginToOnePasswordProcess(private val robot: RobotHelper) {
    fun run(password: SomewhatSecureString) {
        robot.run(CHROME_COMMAND) // Open chrome
        robot.sleep(500)
        robot.pressAndRelease(KeyEvent.VK_CONTROL, KeyEvent.VK_PERIOD).sleep(500) // Open 1password
        robot.type(password).enter()
    }

}
