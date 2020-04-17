package com.yngvark.pc_init.process

import com.yngvark.pc_init.robot.SecretGetter
import com.yngvark.pc_init.robot.RobotHelper
import java.awt.event.KeyEvent

class SshKeysProcess(private val robot: RobotHelper, private val secretGetter: SecretGetter) {

    fun run() {
        println("Process: ${javaClass.simpleName}")

        val itSshKeyPw = secretGetter.getPasswordFromNote("SSH-key yngvar.kristiansen@knowit.no")
        val yngvarkSshKeyPw = secretGetter.getPasswordFromNote("SSH-key yngvark@gmail.com mar20 description_musing_mcnulty")

        robot.pressAndRelease(KeyEvent.VK_F10).sleep(500) // Open Guake Terminal
        robot.pressAndRelease(KeyEvent.VK_CONTROL, KeyEvent.VK_SHIFT, KeyEvent.VK_T).sleep(1500)

        val waitTime:Long = 1000
        robot.type("sa 2").enter().sleep(waitTime)
        robot.type(itSshKeyPw).enter().sleep(waitTime)
        robot.type(yngvarkSshKeyPw).enter().sleep(waitTime)

        robot.pressAndRelease(KeyEvent.VK_CONTROL, KeyEvent.VK_D)
        robot.pressAndRelease(KeyEvent.VK_F10)
    }
}