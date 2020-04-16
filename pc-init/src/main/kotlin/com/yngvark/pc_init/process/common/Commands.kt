package com.yngvark.pc_init.process.common

const val CHROME_COMMAND = "/usr/bin/google-chrome-stable --disable-gpu"
const val SLACK_COMMAND = "/usr/bin/flatpak run --branch=stable --arch=x86_64 --command=slack --file-forwarding com.slack.Slack --disable-gpu @@u %U @@"