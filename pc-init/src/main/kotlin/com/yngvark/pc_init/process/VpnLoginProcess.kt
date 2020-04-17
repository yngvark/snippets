package com.yngvark.pc_init.process

import com.yngvark.pc_init.process.common.CHROME_COMMAND
import com.yngvark.pc_init.robot.Click
import com.yngvark.pc_init.robot.SecretGetter
import com.yngvark.pc_init.robot.RobotHelper
import java.awt.event.KeyEvent
import java.lang.RuntimeException

class VpnLoginProcess(private val robot: RobotHelper, private val secretGetter: SecretGetter) {

    fun run() {
        println("Process: ${javaClass.simpleName}")

        startConnectToVpnAndTriggerGet2faToken()
        robot.sleep(2000) // Wait for SMS token to arrive

        val token = get2FaToken()
        println("Found 2fa token: $token")

        robot.pressAndRelease(KeyEvent.VK_ALT, KeyEvent.VK_TAB).sleep(1500)

        robot.type(token, 10).sleep(300).enter().sleep(100)
    }

    private fun startConnectToVpnAndTriggerGet2faToken() {
        // Get VPN password
        val password = secretGetter.getPasswordFromNote("UVA VPN PIN")

        // Connect to VPN
        robot.click(listOf(
            Click(2410, 14, "Network icon")
            , Click(2322, 170)
            , Click(2173, 168)
        ), pauseAfterClick = 300)

        if (robot.debugMode)
            println("Typing 2fa password into password input box: $password")

        robot.type(password).sleep(10).enter()
    }

    fun get2FaToken(): String {
        robot.run("$CHROME_COMMAND https://messages.google.com/web/conversations")
        robot.sleep(1000) // Wait for Chrome to start, AND SMS token to arrive
        robot.pressAndRelease(KeyEvent.VK_CONTROL, KeyEvent.VK_SHIFT, KeyEvent.VK_J).sleep(2000) // Open Chrome Console
        //robot.pressAndRelease(KeyEvent.VK_TAB).sleep(1000)

        robot.type("var a = document.evaluate(\"//span[contains(., 'Tokencode')]\", document, null, XPathResult.ANY_TYPE, null ).iterateNext().textContent")
        robot.pressAndRelease(KeyEvent.VK_ESCAPE).enter().sleep(1000) // Press ESC to escape chrome auto fill in
        robot.type("copy(a)").enter().sleep(50) // Copies variable to clipboard
        robot.pressAndRelease(KeyEvent.VK_CONTROL, KeyEvent.VK_W).sleep(50) // Close SMS web page

        return parse2faTokenFromSms(robot.getClipboardContents())
    }

    private fun parse2faTokenFromSms(clipboardContents: String):String {
        println("Parsing 2FA token from: $clipboardContents")
        val match = Regex("""[0-9]+""").find(clipboardContents)
            ?: throw RuntimeException("Could not find regex in clipboardContents!")

        return match.value
    }

}