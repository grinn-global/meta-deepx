SUMMARY = "DX-RT"
DESCRIPTION = "DeepX Runtime and Userspace Tools"
HOMEPAGE = "https://deepx.ai"
LICENSE = "DEEPX"
LIC_FILES_CHKSUM = "file://LICENSE;md5=df0ebe3edba67d21cb2e798ef0ee2905"

SRC_URI = "git://github.com/DEEPX-AI/dx_rt.git;protocol=https;branch=main \
           file://0001-Modify-Service.patch \
           file://0002-Modify-CMakeLists.patch \
           file://0003-Remove-install-to-source-directory.patch \
           file://0004-Change-ftok-path.patch \
           file://0005-Disable-get-pybind11-fetch.patch \
           "
SRCREV = "26348329382252873023911675579868053946d4"

S = "${WORKDIR}/git"

DEPENDS += "pybind11"

PACKAGECONFIG ??= "python service shared_dxrt_lib"
PACKAGECONFIG[onnxruntime] = "\
    -DUSE_ORT=ON \
    -Donnxruntime_INCLUDE_DIRS=${STAGING_INCDIR}/onnxruntime \
    -Donnxruntime_LIB_DIRS=${STAGING_LIBDIR}/onnxruntime, \
    -DUSE_ORT=OFF, \
    onnxruntime, \
    onnxruntime"
PACKAGECONFIG[python] = "\
    -DUSE_PYTHON=ON \
    -DPYTHON_INCLUDE_DIRS=${STAGING_INCDIR}/${PYTHON_DIR}, \
    -DUSE_PYTHON=OFF"
PACKAGECONFIG[service] = "\
    -DUSE_SERVICE=ON, \
    -DUSE_SERVICE=OFF"
PACKAGECONFIG[shared_dxrt_lib] = "\
    -DUSE_SHARED_DXRT_LIB=ON, \
    -DUSE_SHARED_DXRT_LIB=OFF"

inherit cmake
inherit ${@bb.utils.contains('PACKAGECONFIG', 'python', 'setuptools3', '', d)}
inherit ${@bb.utils.contains('PACKAGECONFIG', 'service', 'systemd', '', d)}

SETUPTOOLS_SETUP_PATH = "${S}/python_package"
SYSTEMD_SERVICE:${PN} = "dxrt.service"
SOLIBS = ".so"
FILES_SOLIBSDEV = ""
RDEPENDS:${PN} += "dx-npu python3-numpy"

EXTRA_OECMAKE = "${PACKAGECONFIG_CONFARGS} \
                 -DCROSS_COMPILE=TRUE \
                 -DCMAKE_SKIP_RPATH=TRUE \
                 -DCMAKE_BUILD_TYPE=RelWithDebInfo \
                 "

do_configure() {
    cmake_do_configure
    if ${@bb.utils.contains('PACKAGECONFIG', 'python', 'true', 'false', d)}; then
        setuptools3_do_configure
    fi
}

do_compile() {
    cmake_do_compile
    if ${@bb.utils.contains('PACKAGECONFIG', 'python', 'true', 'false', d)}; then
        setuptools3_do_compile
    fi
}

do_install() {
    cmake_do_install

    if ${@bb.utils.contains('PACKAGECONFIG', 'service', 'true', 'false', d)}; then
        install -d ${D}${systemd_unitdir}/system
        install -m 0644 ${S}/service/dxrt.service ${D}${systemd_unitdir}/system
    fi

    if ${@bb.utils.contains('PACKAGECONFIG', 'python', 'true', 'false', d)}; then
        setuptools3_do_install
    fi
}
