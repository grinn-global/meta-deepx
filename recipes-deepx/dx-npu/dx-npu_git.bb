SUMMARY = "DX-NPU"
DESCRIPTION = "DX-NPU"
LICENSE = "DEEPX"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=df0ebe3edba67d21cb2e798ef0ee2905"

inherit module

SRC_URI = "git://git@gitlab.grinndev.ovh:/deepx/dx-npu.git;protocol=ssh;branch=master \
           file://0001-Modify-Makefile.patch;patchdir=.. \
           "
SRCREV = "217b02ec5a93fec90cf68abf77cae0a55f2e1a89"
PV = "1.0+git${SRCPV}"

S = "${WORKDIR}/git/modules"

PROVIDES_${PN} = "kernel-module-dx-npu"

DX_DEVICE ?= "m1a"
DX_PCIE ?= "deepx"

EXTRA_OEMAKE = "DEVICE=${DX_DEVICE} \
                PCIE=${DX_PCIE} \
                ARCH=${ARCH} \
                CROSS_COMPILE=${TARGET_PREFIX} \
                KERNEL_DIR=${STAGING_KERNEL_DIR}"
