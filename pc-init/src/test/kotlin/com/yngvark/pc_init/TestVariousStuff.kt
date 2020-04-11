package com.yngvark.pc_init

import com.yngvark.pc_init.robot.RobotHelper
import org.junit.jupiter.api.Test
import java.awt.Robot
import java.awt.event.KeyEvent

internal class TestVariousStuff {
    private val robot = RobotHelper(Robot())

    @Test
    fun test() {
        robot.type("document.evaluate(\"//span[contains(., 'Tokencode')]\", document, null, XPathResult.ANY_TYPE, null ).iterateNext().textContent")
        //document.evaluate('//span[contains(., 'Tokencode')]')
        //document.evaluate("//span[contains(., 'Tokencode')]", document, null, XPathResult.ANY_TYPE, null ).iterateNext().textContent
    }

    @Test
    fun test2() {
        robot.pressAndRelease(KeyEvent.VK_SHIFT, KeyEvent.VK_QUOTE)
        //robot.type("ø")
    }
}