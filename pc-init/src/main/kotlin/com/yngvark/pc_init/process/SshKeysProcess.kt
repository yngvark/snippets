package com.yngvark.pc_init.process

import com.yngvark.pc_init.robot.SecretGetter
import com.yngvark.pc_init.robot.RobotHelper
import java.awt.event.KeyEvent

class SshKeysProcess(private val robot: RobotHelper, private val secretGetter: SecretGetter) {

    fun run() {
        val waitTime:Long = 1000
        val itSshKeyPw = secretGetter.getSecret("SSH-key yngvar.kristiansen@knowit.no")
        val yngvarkSshKeyPw = secretGetter.getSecret("SSH-key yngvark@gmail.com mar20 description_musing_mcnulty")
        robot.pressAndRelease(KeyEvent.VK_F10).sleep(waitTime)
        robot.type("sa 2").enter().sleep(waitTime)
        robot.type(itSshKeyPw).enter().sleep(waitTime)
        robot.type(yngvarkSshKeyPw).enter().sleep(waitTime)

        robot.pressAndRelease(KeyEvent.VK_F10)
    }
}