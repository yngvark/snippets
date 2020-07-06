package com.yngvark.pc_init

import com.yngvark.pc_init.robot.RobotHelper
import org.junit.jupiter.api.Test
import java.io.File
import java.net.URL

internal class TestVariousStuff {
    @Test
    fun testValidatePassword() {
        val encryptedFilePath: URL = RobotHelper::class.java.getClassLoader().getResource("cmd.txt")
        println("encryptedFilePath:")
        println(encryptedFilePath)
        val encryptedFile:File = File(
            RobotHelper::class.java.getProtectionDomain().getCodeSource().getLocation()
                .toURI()
        )
        val path:String = encryptedFile.parentFile.parentFile.parentFile.parentFile.toString() + "/src/main/resources/encryptedFile.txt.gpg"
        print(path)
    }
//    @Test
//    fun testValidatePassword() {
//        validatePassword(SomewhatSecureString("hello".toCharArray()))
//    }
//
//    @Test
//    fun testLotsOfCharactertypes() {
//        robot.type("document.evaluate(\"//span[contains(., 'Tokencode')]\", document, null, XPathResult.ANY_TYPE, null ).iterateNext().textContent")
//        //document.evaluate('//span[contains(., 'Tokencode')]')
//        //document.evaluate("//span[contains(., 'Tokencode')]", document, null, XPathResult.ANY_TYPE, null ).iterateNext().textContent
//    }
//
//    @Test
//    fun testPressAndRelease() {
//        robot.pressAndRelease(KeyEvent.VK_SHIFT, KeyEvent.VK_COLON)
//    }
//
//    @Test
//    fun sshkeys() {
//        sshKeys.run()
//    }
//
//    @Test
//    fun onePassword() {
//        println(loginToOnePassword.run(SomewhatSecureString("somesecretpassword".toCharArray())))
//    }
//
//    @Test
//    fun vpnlogin() {
//        println(vpnLogin.get2FaToken())
//    }
//
//    @Test
//    fun outlook() {
//        // webPages.outlook()
//    }
//
//    @Test
//    fun programs() {
//        programs.run()
//    }
//
//    @Test
//    fun clockify() {
//        robot.sleep(1000)
//        // webPages.clockify()
//    }
//
//    @Test
//    fun k8s() {
//        k8sLogin.run()
//    }
}