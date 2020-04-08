package com.yngvark.pc_init

import java.awt.Robot
import java.awt.event.KeyEvent

fun testClicks() {
    // TODO:
    // VPN
    // - ssh keys
    // chrome
    // - 1password
    // - gmail, private + knowit
    // slack
    println("Hello woRLD")

    val robot = RobotHelper(Robot())


    val clicks = listOf(
        Click(2410, 14, "Network icon")
        , Click(2322, 170)
        , Click(2173, 168)
    )

    clicks.forEach {
        robot.click(it)
        println("Clicking: $it")
        Thread.sleep(1000)
    }

    robot.type("SOMECODE").enter()


    getPinCode()
4
    //helper.click(2410, 15)
    //helper.click(2284, 341)

}

fun getPinCode() {
    val robot = RobotHelper(Robot())
    robot.run("/usr/bin/google-chrome-stable --disable-gpu https://messages.google.com/web/conversations")
    robot.sleep(2500)
    robot.pressAndRelease(KeyEvent.VK_CONTROL, KeyEvent.VK_SHIFT, KeyEvent.VK_J)
    robot.sleep(500)
    robot.type("var a = $(\".snippet-text .ng-star-inserted\").innerHTML")
}


fun main(args: Array<String>) {
    //testClicks()
    getPinCode()
    //print4ln("var a = $(\".snippet-text .ng-star-inserted\").innerHTML")


    /*val keyEvent = KeyEvent.getExtendedKeyCodeForChar('$'.toInt())
    println(keyEvent)
    println(KeyEvent.VK_DOLLAR)

    Robot().keyPress(KeyEvent.VK_SHIFT)
    Robot().keyPress(KeyEvent.VK_4)

    Robot().keyRelease(KeyEvent.VK_SHIFT)
    Robot().keyRelease(KeyEvent.VK_4)*/

}

fun testClicks2() {
    // Med keyboard:
    // Start chrome
    // Åpne https://messages.google.com/
    // Sett i gang ~/tmp/ococ

}
