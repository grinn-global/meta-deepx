SUMMARY = "DX-NPU"
DESCRIPTION = "DeepX Neural Processing Unit Linux Kernel Drivers"
HOMEPAGE = "https://deepx.ai"
LICENSE = "DEEPX"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=df0ebe3edba67d21cb2e798ef0ee2905"

inherit module

PROVIDES:${PN} = "kernel-module-${PN}"

SRC_URI = "git://github.com/DEEPX-AI/dx_rt_npu_linux_driver.git;protocol=https;branch=main \
           file://0001-Modify-Makefile.patch;patchdir=.."
SRCREV = "9b61de90a03aa9948eacc0709322fbca664a84cf"

S = "${WORKDIR}/git/modules"

EXTRA_OEMAKE = "DEVICE=${DX_DEVICE} \
                PCIE=${DX_PCIE} \
                KERNEL_DIR=${STAGING_KERNEL_DIR}"

DX_DEVICE ?= "m1a"
DX_PCIE ?= "deepx"

do_install:append() {
    install -d ${D}${sysconfdir}/modprobe.d
    install -m 0644 ${S}/dx_dma.conf ${D}${sysconfdir}/modprobe.d/
}
