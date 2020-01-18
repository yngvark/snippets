#!/bin/bash

#src 2: https://medium.com/@chrishantha/encrypting-disks-on-ubuntu-19-04-b50bfc65182a
# Updated install: https://docs.google.com/document/d/1HDTkHozInCFWe5JX72bKJzUjqT1dLu9NJoljUvBLDfc/edit?usp=sharing

# USAGE
# sudo -i
# nano i.sh
# chmod +x i.sh
# sudo DM=nvme0n1 ./install_linux.sh
# SOURCE: https://help.ubuntu.com/community/Full_Disk_Encryption_Howto_2019

if [[ -z "${DM}" ]]; then
  lsblk

  echo
  echo DM environment variable was not set.
  echo
  echo WARNING, CHECK WITH "lsblk" WHAT TO USE FOR DEV VARIABLE
  exit 1;
fi

set -ex

DEV=/dev/$DM
RAM=32G

sgdisk --print $DEV
echo $DM

sgdisk --zap-all $DEV

sgdisk --new=1:0:+768M $DEV
sgdisk --new=2:0:+2M $DEV
sgdisk --new=3:0:+128M $DEV
sgdisk --new=5:0:0 $DEV
sgdisk --typecode=1:8301 --typecode=2:ef02 --typecode=3:ef00 --typecode=5:8301 $DEV
sgdisk --change-name=1:/boot --change-name=2:GRUB --change-name=3:EFI-SP --change-name=5:rootfs $DEV
sgdisk --hybrid 1:2:3 $DEV

# cryptsetup luksFormat -q --type=luks1 ${DEV}p1
cryptsetup luksFormat -q --type=luks1 ${DEV}p5

# cryptsetup open ${DEV}p1 LUKS_BOOT
cryptsetup open ${DEV}p5 ${DM}5_crypt

ls /dev/mapper/

mkfs.ext4 -L boot /dev/mapper/LUKS_BOOT
mkfs.vfat -F 16 -n EFI-SP ${DEV}p3

pvcreate /dev/mapper/${DM}5_crypt
vgcreate ubuntu-vg /dev/mapper/${DM}5_crypt
lvcreate -L $RAM -n swap_1 ubuntu-vg
lvcreate -l 80%FREE -n root ubuntu-vg

echo
echo As soon as you have completed those forms switch to the Terminal to configure GRUB:
echo 'echo "GRUB_ENABLE_CRYPTODISK=y" >> /target/etc/default/grub'
echo
echo This has to be done before the installer reaches the Install Bootloader stage at the end of the installation process.
echo This has to be done before the installer reaches the Install Bootloader stage at the end of the installation process.
echo This has to be done before the installer reaches the Install Bootloader stage at the end of the installation process.
echo
echo Settings:
echo “/dev/mapper/LUKS_BOOT -> Ext4 -> Format -> /boot”
echo “/dev/mapper/ubuntu--vg-root -> Ext4 -> Format -> /”
echo “/dev/mapper/ubuntu--vg-swap_1 -> Swap area”
echo
echo Select raw hard disk as boot device, not a partition or device-mapper node.

echo then follow: https://docs.google.com/document/d/1IbxUmKOtX3peCFJ5Rh8abaq2_Km6K4w7qyNfwAWCE3c/edit?usp=sharing
