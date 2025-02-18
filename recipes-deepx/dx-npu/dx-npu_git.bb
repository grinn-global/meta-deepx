SUMMARY = "DX-NPU"
DESCRIPTION = "DX-NPU"
LICENSE = "DEEPX"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=df0ebe3edba67d21cb2e798ef0ee2905"

inherit module
require recipes-deepx/dx.inc

SRC_URI = "git://git@gitlab.grinndev.ovh:/deepx/dx-npu.git;protocol=ssh;branch=master \
           file://0001-Modify-Makefile.patch;patchdir=.. \
           "
SRCREV = "1.3.1"

PV = "${SRCREV}+git${SRCPV}"

S = "${WORKDIR}/git/modules"

PROVIDES:${PN} = "kernel-module-${PN}"

EXTRA_OEMAKE = "DEVICE=${DX_DEVICE} \
                PCIE=${DX_PCIE} \
                KERNEL_DIR=${STAGING_KERNEL_DIR}"

KERNEL_MODULE_AUTOLOAD += "dx_dma" 

do_install:append() {
    install -d ${D}${sysconfdir}/modprobe.d
    install -m 0644 ${S}/dx_dma.conf ${D}${sysconfdir}/modprobe.d/
}
