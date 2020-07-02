package com.yngvark.pc_init

import com.yngvark.pc_init.process.*
import com.yngvark.pc_init.robot.ConsolePasswordReader
import com.yngvark.pc_init.robot.RobotHelper
import com.yngvark.pc_init.robot.SecretGetter
import com.yngvark.pc_init.robot.SomewhatSecureString
import java.awt.Robot
import java.awt.event.KeyEvent
import java.io.File
import java.lang.RuntimeException
import java.nio.file.Paths
import java.util.concurrent.TimeUnit

val robot = RobotHelper(Robot())
val loginToOnePassword = LoginToOnePasswordProcess(robot)
val secretGetter = SecretGetter(robot)
val vpnLogin = VpnLoginProcess(robot, secretGetter)
val k8sLogin = K8sLoginProcess(robot)
val webPages = WebPagesProcess(robot)
val sshKeys = SshKeysProcess(robot, SecretGetter(robot))
val programs = ProgramsProcess(robot)

fun main(args: Array<String>) {
    println("Version 0.0.10 - Adjust outlook URL")

    if (args.isEmpty()) {
        println("Running all processes")
    } else {
        if (run1Password(args)) {
            println("Running step: " + LoginToOnePasswordProcess::class.simpleName)
        }

        if (runVpn(args)) {
            println("Running step: " + VpnLoginProcess::class.simpleName)
        }
    }

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
        validatePassword(password)
        loginRoutine(password, args)
    }
}

fun validatePassword(password: SomewhatSecureString) {
    val cmd:Array<String> = (
            "/usr/bin/gpg --decrypt --passphrase ${password.asString()} --batch --yes " +
            "encryptedFile.txt.gpg"
            ).split("\\s".toRegex()).toTypedArray()

    val proc = ProcessBuilder(*cmd)
        .directory(File("./src/main/resources"))
//        .redirectOutput(ProcessBuilder.Redirect.INHERIT)
//        .redirectError(ProcessBuilder.Redirect.INHERIT)
        .start()
    proc.waitFor(2, TimeUnit.SECONDS)
    if (proc.exitValue() > 0)
        throw RuntimeException("Invalid password")
}


fun loginRoutine(password: SomewhatSecureString, args: Array<String>) {
    robot.sleep(2000)

    if (args.isEmpty()) {
        runAllProcesses(password)
    } else {
        if (run1Password(args)) {
            println("Running step: " + LoginToOnePasswordProcess::class.simpleName)
            runLoginToOnePasswordProcess(password)
        }

        if (runVpn(args)) {
            println("Running step: " + VpnLoginProcess::class.simpleName)
            runVpnProcess(password)
        }
    }
}

private fun runAllProcesses(password: SomewhatSecureString) {
    println("Running all processes")

    password.use {
        loginToOnePassword.run(password)
        vpnLogin.run()
        robot.pressAndRelease(KeyEvent.VK_ALT, KeyEvent.VK_TAB).sleep(50) // Switch to chrome
        robot.pressAndRelease(KeyEvent.VK_ESCAPE).sleep(50)
    }

    webPages.run()
    sshKeys.run() // TODO: Fix not having to do this.
    programs.run()
    k8sLogin.run()
}

private fun run1Password(args: Array<String>) = args.any { it == "--1p" }

private fun runLoginToOnePasswordProcess(password: SomewhatSecureString) {
    password.use {
        loginToOnePassword.run(password)
    }
}

private fun runVpn(args: Array<String>) = args.any { it == "--vpn" }

private fun runVpnProcess(password: SomewhatSecureString) {
    password.use {
        vpnLogin.run()
    }
}

fun test() {
    // TODO fix outlook
    programs.run()
}
