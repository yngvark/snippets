package com.yngvark.pc_init.process.ssh_key

import com.yngvark.pc_init.robot.RobotHelper
import java.awt.event.KeyEvent

class SshKeysProcess(private val robot: RobotHelper, private val onePasswordGetter: OnePasswordGetter) {

    fun run() {
        val pw = onePasswordGetter.getSecret("SSH-key yngvark@gmail.com mar20 description_musing_mcnulty")
        //robot.run()
        robot.pressAndRelease(KeyEvent.VK_F10)
    }
}