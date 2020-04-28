package com.yngvark.pc_init

import org.junit.jupiter.api.Test
import java.awt.event.KeyEvent

internal class TestVariousStuff {
    @Test
    fun testLotsOfCharactertypes() {
        robot.type("document.evaluate(\"//span[contains(., 'Tokencode')]\", document, null, XPathResult.ANY_TYPE, null ).iterateNext().textContent")
        //document.evaluate('//span[contains(., 'Tokencode')]')
        //document.evaluate("//span[contains(., 'Tokencode')]", document, null, XPathResult.ANY_TYPE, null ).iterateNext().textContent
    }

    @Test
    fun testPressAndRelease() {
        robot.pressAndRelease(KeyEvent.VK_SHIFT, KeyEvent.VK_COLON)
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
        robot.sleep(1000)
        robot.pressAndRelease(KeyEvent.VK_CONTROL, KeyEvent.VK_T).sleep(50)
        // Super cool URL that doesn't prompt for username and password via a stupid alert box, when Outlook is stuck in that state
        robot.type("https://webmail.oslo.kommune.no/owa/auth/logon.aspx?replaceCurrent=1&url=http%3a%2f%2fwebmail.oslo.kommune.no%2fowa").enter().sleep(1500)

        // Use 1password login box
        robot
            .pressAndRelease(KeyEvent.VK_DOWN).sleep(50)
            .enter().sleep(50)
            .enter().sleep(50)
    }

    @Test
    fun programs() {
        programs.run()
    }
}