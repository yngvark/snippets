package com.yngvark.pc_init

import com.yngvark.pc_init.process.OutlookWebProcess
import com.yngvark.pc_init.process.LoginToOnePasswordProcess
import com.yngvark.pc_init.process.SshKeysProcess
import com.yngvark.pc_init.process.VpnLoginProcess
import com.yngvark.pc_init.robot.RobotHelper
import com.yngvark.pc_init.robot.SecretGetter
import org.junit.jupiter.api.Test
import java.awt.Robot
import java.awt.event.KeyEvent

internal class TestVariousStuff {
    val robot = RobotHelper(Robot())

    val loginToOnePassword = LoginToOnePasswordProcess(robot)
    val outlook = OutlookWebProcess(robot)
    val sshKeys = SshKeysProcess(robot, SecretGetter(robot))
    val secretGetter = SecretGetter(robot)
    val vpnLogin = VpnLoginProcess(robot, secretGetter)


    @Test
    fun test() {
        robot.type("document.evaluate(\"//span[contains(., 'Tokencode')]\", document, null, XPathResult.ANY_TYPE, null ).iterateNext().textContent")
        //document.evaluate('//span[contains(., 'Tokencode')]')
        //document.evaluate("//span[contains(., 'Tokencode')]", document, null, XPathResult.ANY_TYPE, null ).iterateNext().textContent
    }

    @Test
    fun test2() {
        robot.pressAndRelease(KeyEvent.VK_ALT, KeyEvent.VK_TAB).sleep(1000)
        //robot.type("ø")
    }

    @Test
    fun sshkeys() {
        sshKeys.run()
    }

    @Test
    fun vpnlogin() {
        println(vpnLogin.get2FaToken())
    }

    @Test
    fun outlook() {
        robot.sleep(2000)
        outlook.run()
    }



}