package com.yngvark.pc_init

import com.yngvark.pc_init.process.OnePasswordProcess
import com.yngvark.pc_init.process.VpnLoginProcess
import com.yngvark.pc_init.robot.ConsolePasswordReader
import com.yngvark.pc_init.robot.RobotHelper
import java.awt.Robot
import java.awt.event.KeyEvent

fun main(args: Array<String>) {
    val robotHelper = RobotHelper(Robot())
    val vpnLogin = VpnLoginProcess(robotHelper)
    val onePassword = OnePasswordProcess(robotHelper)

    ConsolePasswordReader().read().use {
        val pw:CharArray = it.value

        //vpnLogin.run()
        robotHelper.sleep(1000)

        robotHelper.pressAndRelease(KeyEvent.VK_ALT, KeyEvent.VK_TAB).sleep(50) // Switch to chrome

        onePassword.run(pw)
    }

}














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
// TODO:
// VPN
// - ssh keys
// chrome
// - 1password
// - gmail, private + knowit
// slack
