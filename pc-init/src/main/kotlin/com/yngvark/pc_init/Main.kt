package com.yngvark.pc_init

import com.yngvark.pc_init.process.GmailProcess
import com.yngvark.pc_init.process.OnePasswordProcess
import com.yngvark.pc_init.process.VpnLoginProcess
import com.yngvark.pc_init.robot.OnePasswordGetter
import com.yngvark.pc_init.process.SshKeysProcess
import com.yngvark.pc_init.robot.RobotHelper
import java.awt.Robot

val robot = RobotHelper(Robot())
val vpnLogin = VpnLoginProcess(robot)
val onePassword = OnePasswordProcess(robot)
val gmail = GmailProcess(robot)
val sshKeys = SshKeysProcess(robot, OnePasswordGetter(robot))
val onePasswordGetter = OnePasswordGetter(robot)


fun main(args: Array<String>) {
    //loginRoutine()
    test()
}

fun test() {
    val sshKeys = SshKeysProcess(robot, OnePasswordGetter(robot))
    sshKeys.run()
}

fun test2() {
    val yngvarkSshKeyPw = onePasswordGetter.getSecret("SSH-key yngvark@gmail.com mar20 description_musing_mcnulty")
    println("yngvarkSshKeyPw: $yngvarkSshKeyPw")
}

fun loginRoutine() {

//    ConsolePasswordReader().read().use {
//        val pw:CharArray = it.value
//
//        vpnLogin.run()
//        robotHelper.pressAndRelease(KeyEvent.VK_ALT, KeyEvent.VK_TAB).sleep(50) // Switch to chrome
//        onePassword.run(pw)
//        robotHelper.pressAndRelease(KeyEvent.VK_ESCAPE).sleep(50)
//    }

    robot.sleep(1000)
    gmail.run()

    sshKeys.run()
}

// TODO:
// VPN
// - ssh keys
// chrome
// slack













fun testMyRobot() {
    //println(KeyEvent.getExtendedKeyCodeForChar('H'.toInt()))

    val robot = RobotHelper(Robot())
    robot.type("copy(a)").enter()
}

fun testClicks2() {
    // Med keyboard:
    // Start chrome
    // Åpne https://messages.google.com/
    // Sett i gang ~/tmp/ococ

}
