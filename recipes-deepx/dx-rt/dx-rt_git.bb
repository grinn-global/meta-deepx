SUMMARY = "DX-RT"
DESCRIPTION = "DX-RT"
LICENSE = "DEEPX"
LIC_FILES_CHKSUM = "file://LICENSE;md5=df0ebe3edba67d21cb2e798ef0ee2905"

inherit cmake

SRC_URI = "git://git@gitlab.grinndev.ovh:/deepx/dx-rt.git;protocol=ssh;branch=master"
SRCREV = "2.6.3"
PV = "${SRCREV}+git${SRCPV}"

S = "${WORKDIR}/git"

SOLIBS = ".so"
FILES_SOLIBSDEV = ""

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${B}/bin/* ${D}${bindir}

    install -d ${D}${libdir}
    install -m 0755 ${B}/lib/*.so ${D}${libdir}
}

