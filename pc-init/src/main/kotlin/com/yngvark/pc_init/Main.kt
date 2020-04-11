package com.yngvark.pc_init

import com.yngvark.pc_init.process.GmailProcess
import com.yngvark.pc_init.process.LoginToOnePasswordProcess
import com.yngvark.pc_init.process.VpnLoginProcess
import com.yngvark.pc_init.robot.SecretGetter
import com.yngvark.pc_init.process.SshKeysProcess
import com.yngvark.pc_init.robot.ConsolePasswordReader
import com.yngvark.pc_init.robot.RobotHelper
import com.yngvark.pc_init.robot.SomewhatSecureString
import java.awt.Robot

val robot = RobotHelper(Robot())

val loginToOnePassword = LoginToOnePasswordProcess(robot)
val gmail = GmailProcess(robot)
val sshKeys = SshKeysProcess(robot, SecretGetter(robot))
val secretGetter = SecretGetter(robot)
val vpnLogin = VpnLoginProcess(robot, secretGetter)

fun main(args: Array<String>) {
    decideLoginRoutine(args)
//    test()
}

// TODO:
// VPN
// - ssh keys
// chrome
fun test() {
    //robot.debugMode = true
    println(vpnLogin.get2FaToken()) // TODO make private
}


private fun decideLoginRoutine(args: Array<String>) {
    if (args.size == 1) {
        val password = SomewhatSecureString(args[0].toCharArray())
        loginRoutine(password)
    } else {
        val password = ConsolePasswordReader().read()
        loginRoutine(password)
    }
}

fun loginRoutine(password: SomewhatSecureString) {
    password.use {
        //loginToOnePassword.run(password)
        vpnLogin.run()
        //robot.pressAndRelease(KeyEvent.VK_ALT, KeyEvent.VK_TAB).sleep(50) // Switch to chrome
        //robot.pressAndRelease(KeyEvent.VK_ESCAPE).sleep(50)
    }

    //gmail.run()
    //sshKeys.run()
}





// slack

fun test2() {
    val yngvarkSshKeyPw = secretGetter.getSecret("SSH-key yngvark@gmail.com mar20 description_musing_mcnulty")
    println("yngvarkSshKeyPw: $yngvarkSshKeyPw")
}
