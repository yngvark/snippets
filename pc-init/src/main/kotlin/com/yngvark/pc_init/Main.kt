package com.yngvark.pc_init

import com.yngvark.pc_init.process.*
import com.yngvark.pc_init.robot.SecretGetter
import com.yngvark.pc_init.robot.ConsolePasswordReader
import com.yngvark.pc_init.robot.RobotHelper
import com.yngvark.pc_init.robot.SomewhatSecureString
import java.awt.Robot
import java.awt.event.KeyEvent

val robot = RobotHelper(Robot())
val loginToOnePassword = LoginToOnePasswordProcess(robot)
val secretGetter = SecretGetter(robot)
val vpnLogin = VpnLoginProcess(robot, secretGetter)
val k8sLogin = K8sLoginProcess(robot)
val webPages = WebPagesProcess(robot)
val slack = SlackProcess(robot)
val sshKeys = SshKeysProcess(robot, SecretGetter(robot))

fun main(args: Array<String>) {
    decideLoginRoutine(args)

//    robot.debugMode = true
//    test()
}

private fun decideLoginRoutine(args: Array<String>) {
    if (args.any { it == "-p" }) {
        val password = SomewhatSecureString(args[args.indexOf("-p") + 1].toCharArray())
        loginRoutine(password, args)
    } else {
        val password = ConsolePasswordReader().read()
        loginRoutine(password, args)
    }
}


fun loginRoutine(password: SomewhatSecureString, args: Array<String>) {
    if (args.any { it == "--vpn" }) {
        runVpnProcess(password)
    } else {
        println("runall")
        runAllProcesses(password)
    }
}

private fun runAllProcesses(password: SomewhatSecureString) {
    password.use {
        loginToOnePassword.run(password)
        vpnLogin.run()
        robot.pressAndRelease(KeyEvent.VK_ALT, KeyEvent.VK_TAB).sleep(50) // Switch to chrome
        robot.pressAndRelease(KeyEvent.VK_ESCAPE).sleep(50)
    }

    k8sLogin.run()
    webPages.run()
    slack.run()
    sshKeys.run() // TODO: Fix not having to do this.
}

private fun runVpnProcess(password: SomewhatSecureString) {
    password.use {
        vpnLogin.run()
    }
}

fun test() {
    println(vpnLogin.get2FaToken())
}
