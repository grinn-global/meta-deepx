SUMMARY = "DX-RT"
DESCRIPTION = "DX-RT"
LICENSE = "DEEPX"
LIC_FILES_CHKSUM = "file://LICENSE;md5=df0ebe3edba67d21cb2e798ef0ee2905"

inherit cmake systemd

SRC_URI = "git://git@gitlab.grinndev.ovh:/deepx/dx-rt.git;protocol=ssh;branch=master \
           file://0001-Modify-Service.patch \
           "
SRCREV = "2.6.3"
PV = "${SRCREV}+git${SRCPV}"

S = "${WORKDIR}/git"

SOLIBS = ".so"
FILES_SOLIBSDEV = ""

DX_USE_ORT ?= "1"
DX_USE_PYTHON ?= "1"
DX_USE_SERVICE ?= "1"
DX_USE_SHARED_DXRT_LIB ?= "1"

EXTRA_OECMAKE = "-DUSE_ORT=${DX_USE_ORT} \
                -DUSE_PYTHON=${DX_USE_PYTHON} \
                -DUSE_SERVICE=${DX_USE_SERVICE} \
                -DUSE_SHARED_DXRT_LIB=${DX_USE_SHARED_DXRT_LIB} \
                -Donnxruntime_INCLUDE_DIRS=${STAGING_INCDIR}/onnxruntime \
                -Donnxruntime_LIB_DIRS=${STAGING_LIBDIR}/onnxruntime \
                "

DEPENDS += "${@bb.utils.contains('DX_USE_ORT', '1', 'onnxruntime', '', d)}"
SYSTEMD_SERVICE:${PN} = "${@bb.utils.contains('DX_USE_SERVICE', '1', 'dxrt.service', '', d)}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${B}/bin/* ${D}${bindir}

    if [ "${DX_USE_SHARED_DXRT_LIB}" = "1" ]; then
        install -d ${D}${libdir}
        install -m 0755 ${B}/lib/*.so ${D}${libdir}
    fi

    if [ "${DX_USE_SERVICE}" = "1" ]; then
        install -d ${D}${systemd_unitdir}/system
        install -m 0644 ${S}/service/dxrt.service ${D}${systemd_unitdir}/system
    fi
}
