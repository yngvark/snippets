package com.yngvark.pc_init.process

import com.yngvark.pc_init.process.common.SLACK_COMMAND
import com.yngvark.pc_init.robot.RobotHelper
import com.yngvark.pc_init.secretGetter
import java.awt.event.KeyEvent

class SlackProcess(private val robot: RobotHelper) {
    fun run() {
        robot.run(SLACK_COMMAND)
        robot.sleep(100)
    }

}
