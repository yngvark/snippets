package com.yngvark.pc_init.process

import com.yngvark.pc_init.robot.RobotHelper
import java.awt.event.KeyEvent

class OnePasswordProcess(private val robot: RobotHelper) {
    fun run(password: CharArray) {
        robot.pressAndRelease(KeyEvent.VK_CONTROL, KeyEvent.VK_PERIOD).sleep(500)
        robot.type(password).enter()
    }

}
