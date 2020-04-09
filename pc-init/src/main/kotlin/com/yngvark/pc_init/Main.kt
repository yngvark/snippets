package com.yngvark.pc_init

import com.yngvark.pc_init.process.GmailProcess
import com.yngvark.pc_init.process.OnePasswordProcess
import com.yngvark.pc_init.process.VpnLoginProcess
import com.yngvark.pc_init.process.ssh_key.OnePasswordGetter
import com.yngvark.pc_init.process.ssh_key.SshKeysProcess
import com.yngvark.pc_init.robot.RobotHelper
import java.awt.Robot
import java.awt.event.KeyEvent

val robot = RobotHelper(Robot())

fun main(args: Array<String>) {
    //loginRoutine()
    test()
}

fun test() {
    val secretName = "SSH-key yngvark@gmail.com mar20 description_musing_mcnulty"
    val secret = OnePasswordGetter(robot).getSecret("SSH-key yngvark@gmail.com mar20 description_musing_mcnulty")
    println("Secret: $secret")
}

fun test2() {
    val keyEvent:Int = KeyEvent.getExtendedKeyCodeForChar('2'.toInt())
    println(keyEvent)
    println(KeyEvent.VK_SHIFT)
    //robot.pressAndRelease(KeyEvent.VK_SHIFT, KeyEvent.VK_2)@
    //SSH-key yngvark@gmail.com mar20 description_musing_mcnulty
    //SSH-key yngvark@gmail.com mar20 description_musing_mcnulty

}

fun loginRoutine() {
    val vpnLogin = VpnLoginProcess(robot)
    val onePassword = OnePasswordProcess(robot)
    val gmail = GmailProcess(robot)
    val sshKeys = SshKeysProcess(robot, OnePasswordGetter(robot))

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
