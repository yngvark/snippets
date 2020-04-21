package com.yngvark.pc_init.process

import com.yngvark.pc_init.robot.RobotHelper
import com.yngvark.pc_init.secretGetter
import java.awt.event.KeyEvent

class ProgramsProcess(private val robot: RobotHelper) {
    fun run() {
        println("Process: ${javaClass.simpleName}")

        robot.run("subl")
        robot.sleep(500)

        robot.run("/usr/bin/flatpak run --branch=stable --arch=x86_64 --command=slack --file-forwarding com.slack.Slack --disable-gpu @@u %U @@")
        robot.sleep(500)
    }

}
