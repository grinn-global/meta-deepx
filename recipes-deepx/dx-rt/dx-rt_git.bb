DESCRIPTION = "DX-RT - DeepX Runtime and  Userspace Tools"
HOMEPAGE = "https://deepx.ai"
LICENSE = "DEEPX"
LIC_FILES_CHKSUM = "file://LICENSE;md5=df0ebe3edba67d21cb2e798ef0ee2905"

SRC_URI = "git://git@gitlab.grinndev.ovh:/deepx/dx-rt.git;protocol=ssh;branch=master \
           file://0001-Modify-Service.patch \
           file://0001-Modify-CMakeLists.txt.patch \
           "
SRCREV = "c19139fe2a2224492e209853b110376c8fc9a1c1"
PV = "2.6.3+git${SRCPV}"

S = "${WORKDIR}/git"

DX_USE_ORT ?= "0"
DX_USE_PYTHON ?= "1"
DX_USE_SERVICE ?= "1"
DX_USE_SHARED_DXRT_LIB ?= "1"
DX_ENABLE_DEBUG_INFO ?= "1"

inherit cmake
inherit ${@oe.utils.conditional('DX_USE_PYTHON', '1', 'setuptools3', '', d)}
inherit ${@oe.utils.conditional('DX_USE_SERVICE', '1', 'systemd', '', d)}

ONNXRUNTIME_DEP = "${@oe.utils.conditional('DX_USE_ORT', '1', 'onnxruntime', '', d)}"

DEPENDS += "${ONNXRUNTIME_DEP}"
RDEPENDS:${PN} += "dx-npu ${ONNXRUNTIME_DEP}"

SETUPTOOLS_SETUP_PATH = "${S}/python_package"
SYSTEMD_SERVICE:${PN} = "dxrt.service"
SOLIBS = ".so"
FILES_SOLIBSDEV = ""

EXTRA_OECMAKE = "-DUSE_ORT=${DX_USE_ORT} \
                 -DUSE_PYTHON=${DX_USE_PYTHON} \
                 -DUSE_SERVICE=${DX_USE_SERVICE} \
                 -DUSE_SHARED_DXRT_LIB=${DX_USE_SHARED_DXRT_LIB} \
                 -DENABLE_DEBUG_INFO=${DX_ENABLE_DEBUG_INFO} \
                 -Donnxruntime_INCLUDE_DIRS=${STAGING_INCDIR}/onnxruntime \
                 -Donnxruntime_LIB_DIRS=${STAGING_LIBDIR}/onnxruntime \
                 -DPYTHON_INCLUDE_DIRS=${STAGING_INCDIR}/${PYTHON_DIR} \
                 -DCROSS_COMPILE=TRUE \
                 -DCMAKE_BUILD_TYPE=RelWithDebInfo \
                 "

do_configure() {
    cmake_do_configure

    if [ "${DX_USE_PYTHON}" = "1" ]; then
        setuptools3_do_configure
    fi
}

do_compile() {
    cmake_do_compile

    if [ "${DX_USE_PYTHON}" = "1" ]; then
        setuptools3_do_compile
    fi
}

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

    if [ "${DX_USE_PYTHON}" = "1" ]; then
        setuptools3_do_install
    fi
}
