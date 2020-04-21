package com.yngvark.pc_init

import org.junit.jupiter.api.Test

internal class TestVariousStuff {
    @Test
    fun test() {
        robot.type("document.evaluate(\"//span[contains(., 'Tokencode')]\", document, null, XPathResult.ANY_TYPE, null ).iterateNext().textContent")
        //document.evaluate('//span[contains(., 'Tokencode')]')
        //document.evaluate("//span[contains(., 'Tokencode')]", document, null, XPathResult.ANY_TYPE, null ).iterateNext().textContent
    }

    @Test
    fun test2() {
        //robot.pressAndRelease(KeyEvent.VK_WINDOWS)
        robot.run("subl")
        //robot.type("ø")
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
        robot.type("https://webmail.oslo.kommune.no/owa/auth/logon.aspx?replaceCurrent=1&url=http%3a%2f%2fwebmail.oslo.kommune.no%2fowa").enter().sleep(3000)
        // https;//webmail.oslo.kommune.no/owa/auth/logon.aspx
    }

    @Test
    fun programs() {
        programs.run()
    }



}