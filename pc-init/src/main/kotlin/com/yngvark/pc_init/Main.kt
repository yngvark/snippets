package com.yngvark.pc_init

import java.awt.Robot

fun main(args: Array<String>) {
    val vpnLogin = VpnLoginProcess(RobotHelper(Robot()))
    vpnLogin.run()
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
