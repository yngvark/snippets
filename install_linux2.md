```txt

Lag Xubuntu USB bootable. Velg "Try Xubuntu before using" i Grub.


Lag partisjoner med gparted:
# SIZE FILESYSTEM TYPE TYPE 
# 550mb fat32 efi EFI
# 1000mb ext4 boot Boot
# resten ext4 rootfs Root

sudo su
lsblk
cryptsetup luksFormat --type=luks1 /dev/nvme1n1p3
# HVIS Device is in use: Åpne File Viewer, høyreklikk på 498GB og trykk Unmount.
cryptsetup open /dev/nvme1n1p3 nvme1n1p3_crypt

pvcreate /dev/mapper/nvme1n1p3_crypt
vgcreate ubuntu-vg /dev/mapper/nvme1n1p3_crypt
lvcreate -l 100%FREE -n root ubuntu-vg

# Installer linux med partisjonssettings
Efi -> efi type
Bios -> ext4 format
Root -> ext4 format

# Fiks

blkid /dev/nvme1n1p3
> UUID="062898f5-bb72-47e5-a50d-b7f521b590d4" TYPE="crypto_LUKS" PARTLABEL="rootfs" PARTUUID="7cf510eb-bb05-406b-9c42-5ecbe8608ef4"

# (tips: lsblk -o name,uuid,mountpoint)
# VURDER EXIT SUDO ENV, CTRL+D her, er noe rart

sudo mount /dev/mapper/ubuntu--vg-root /mnt
sudo mount /dev/nvme1n1p2 /mnt/boot # Sjekk med lsblk at p2 er boot
sudo mount --bind /dev /mnt/dev
sudo chroot /mnt

# Trengs egentlig dette?
mount -t proc proc /proc
mount -t sysfs sys /sys
mount -t devpts devpts /dev/pts

nano /etc/crypttab
# Legg til innhold

# <target name> <source device> <key file> <options>
rootfs UUID=<UUID_ROOTFS> none luks,discard
home UUID=<UUID_HOME> none luks,discard
Mao
# <target name> <source device> <key file> <options>
rootfs UUID=062898f5-bb72-47e5-a50d-b7f521b590d4 none luks,discard

update-initramfs -k all -c

#HVIS det blir cryptsetup: WARNING: target 'nvme1n1p3_crypt' not found in /etc/crypttab
# Så er det noe kødd med hvilken kontekst sudo kjører i. Fiks: CTRL+D, CTRL+D, sudo chroot /mnt igjen. Fiks crypttab filen hvis trengs, og kjøre update-initramfs på nytt.
```
