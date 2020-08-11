Inspirasjon:
* Hoved: https://medium.com/@chrishantha/encrypting-disks-on-ubuntu-19-04-b50bfc65182a
* Nr2: https://help.ubuntu.com/community/Full_Disk_Encryption_Howto_2019
* Såvidt: https://askubuntu.com/questions/726972/dual-boot-windows-10-and-linux-ubuntu-on-separate-hard-drives

# Forberedelser: cryptsetup

Lag Xubuntu USB bootable. Velg "Try Xubuntu before using" i Grub.

Lag partisjoner med gparted:
* SIZE FILESYSTEM TYPE TYPE 
* 550mb fat32 efi EFI
* 1000mb ext4 boot Boot
* resten ext4 rootfs Root

GParted skal nå se slik ut:
TODO BILDE GPARTED 

```bash
sudo su

lsblk
umount /dev/nvme1n1p3
cryptsetup luksFormat --type=luks1 /dev/nvme1n1p3
* HVIS Device is in use: Åpne File Viewer, høyreklikk på 498GB og trykk Unmount.
cryptsetup open /dev/nvme1n1p3 nvme1n1p3_crypt

pvcreate /dev/mapper/nvme1n1p3_crypt
vgcreate ubuntu-vg /dev/mapper/nvme1n1p3_crypt
lvcreate -l 100%FREE -n root ubuntu-vg
```

# Installer Xubuntu

Velg Something else.
* Velg /dev/nvme1n1p2 -> Change
  * Use as: EFI System Partition
* Velg /dev/nvme1n1p2 -> Change
  * Use as Ext4 journaling file system
  * Format partition
  * Mount point: /boot
* Velg "Device for boot loader installation": /dev/nvme1n1p2
* Trykk på /dev/mapper/ubuntu-vg-root (på submenyen, der det er utfylt Size, used og system) -> Change
  * Use as ext4 journaling system
  * Format partition
  * Mount point: /

TODO BILDE

# VIKTIG: Etter installasjon er ferdig, trykk Continue testing.
Hvis ikke må du installere alt på nytt.

```bash
blkid /dev/nvme1n1p3
> UUID="062898f5-bb72-47e5-a50d-b7f521b590d4" TYPE="crypto_LUKS" PARTLABEL="rootfs" PARTUUID="7cf510eb-bb05-406b-9c42-5ecbe8608ef4"
```

(tips: lsblk -o name,uuid,mountpoint)

Åpne ny terminal her, tror det er greit.

```bash
sudo mount /dev/mapper/ubuntu--vg-root /mnt
sudo mount /dev/nvme1n1p2 /mnt/boot # Sjekk med lsblk at p2 er boot
sudo mount --bind /dev /mnt/dev
sudo chroot /mnt

# Trengs egentlig dette?
mount -t proc proc /proc
mount -t sysfs sys /sys
mount -t devpts devpts /dev/pts
```bash

```bash
nano /etc/crypttab
```bash

Legg til innhold

```bash
# <target name> <source device> <key file> <options>
rootfs UUID=<UUID_ROOTFS> none luks,discard
```

Fjern kommentarlinjen. Resultat:

```
rootfs UUID=062898f5-bb72-47e5-a50d-b7f521b590d4 none luks,discard
```
EVT TODO nvm1n1p3_crypt UUID=062898f5-bb72-47e5-a50d-b7f521b590d4 none luks,discard

Kjør så

```bash
update-initramfs -k all -c
```

HVIS det blir cryptsetup: WARNING: target 'nvme1n1p3_crypt' not found in /etc/crypttab
* Dårlig løsning 1: Så er det noe kødd med hvilken kontekst sudo kjører i. Fiks: CTRL+D, CTRL+D, sudo chroot /mnt igjen. Fiks crypttab filen hvis trengs, og kjøre update-initramfs på nytt.
* Mer info 2: https://unix.stackexchange.com/questions/107810/why-my-encrypted-lvm-volume-luks-device-wont-mount-at-boot-time


