package com.yngvark.pc_init

import com.yngvark.pc_init.process.OutlookWebProcess
import com.yngvark.pc_init.process.LoginToOnePasswordProcess
import com.yngvark.pc_init.process.VpnLoginProcess
import com.yngvark.pc_init.robot.SecretGetter
import com.yngvark.pc_init.process.SshKeysProcess
import com.yngvark.pc_init.robot.ConsolePasswordReader
import com.yngvark.pc_init.robot.RobotHelper
import com.yngvark.pc_init.robot.SomewhatSecureString
import java.awt.Robot
import java.awt.event.KeyEvent

val robot = RobotHelper(Robot())
val loginToOnePassword = LoginToOnePasswordProcess(robot)
val secretGetter = SecretGetter(robot)
val vpnLogin = VpnLoginProcess(robot, secretGetter)
val outlookWeb = OutlookWebProcess(robot)
val sshKeys = SshKeysProcess(robot, SecretGetter(robot))

fun main(args: Array<String>) {
    //robot.debugMode = true
    decideLoginRoutine(args)
//    test()
}

private fun decideLoginRoutine(args: Array<String>) {
    if (args.size == 1) {
        val password = SomewhatSecureString(args[0].toCharArray())
        loginRoutine(password)
    } else {
        val password = ConsolePasswordReader().read()
        loginRoutine(password)
    }
}


fun loginRoutine(password: SomewhatSecureString) {
    password.use {
        loginToOnePassword.run(password)
        vpnLogin.run()
        robot.pressAndRelease(KeyEvent.VK_ALT, KeyEvent.VK_TAB).sleep(50) // Switch to chrome
        robot.pressAndRelease(KeyEvent.VK_ESCAPE).sleep(50)
    }

    outlookWeb.run()
    // sshKeys.run()
}

fun test() {
    println(vpnLogin.get2FaToken())
}
