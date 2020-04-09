package com.yngvark.pc_init.process

import com.yngvark.pc_init.robot.Click
import com.yngvark.pc_init.robot.RobotHelper
import java.awt.Robot
import java.awt.event.KeyEvent
import java.lang.RuntimeException

class VpnLoginProcess(private val robot: RobotHelper) {

    fun run() {
        startConnectToVpnAndTriggerGet2faToken()
        val token = get2FaToken()
        robot.pressAndRelease(KeyEvent.VK_ALT, KeyEvent.VK_TAB).sleep(500)
        robot.type(token).sleep(1000).enter()
    }

    private fun getPinCode(): String {
        val envVar = "PC_INIT_VPN_PIN_CODE"
        return System.getenv(envVar) ?: throw RuntimeException("Missing env var: $envVar")
    }

    private fun startConnectToVpnAndTriggerGet2faToken() {
        robot.click(listOf(
            Click(2410, 14, "Network icon")
            , Click(2322, 170)
            , Click(2173, 168)
        ), pauseInbetween = 300)

        robot.type(getPinCode()).enter()
    }

    private fun get2FaToken(): String {
        val robot = RobotHelper(Robot())
        robot.run("/usr/bin/google-chrome-stable --disable-gpu https://messages.google.com/web/conversations")
        robot.sleep(2500)
        robot.pressAndRelease(KeyEvent.VK_CONTROL, KeyEvent.VK_SHIFT, KeyEvent.VK_J).sleep(500)
        robot.pressAndRelease(KeyEvent.VK_TAB).sleep(100)
        robot.type("var a = $(\".snippet-text .ng-star-inserted\").innerHTML").enter().sleep(500)
        robot.type("copy(a)").enter()

        return parse2faTokenFromSms(robot.getClipboardContents())
    }

    private fun parse2faTokenFromSms(clipboardContents: String):String {
        val match = Regex("""[0-9]+""").find(clipboardContents)
            ?: throw RuntimeException("Could not find regex in clipboardContents!")

        return match.value
    }

}