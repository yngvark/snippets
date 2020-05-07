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
        // webPages.outlook()
    }

    @Test
    fun programs() {
        programs.run()
    }

    @Test
    fun clockify() {
        robot.sleep(1000)
        // webPages.clockify()
    }

    @Test
    fun k8s() {
        k8sLogin.run()
    }
}