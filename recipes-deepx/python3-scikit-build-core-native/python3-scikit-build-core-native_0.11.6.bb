SUMMARY = "scikit_build_core"
DESCRIPTION = "Build backend for CMake based projects"
HOMEPAGE = "https://github.com/scikit-build/scikit-build-core/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://scikit_build_core-0.11.6.dist-info/licenses/LICENSE;md5=3b4e748e5f102e31c9390dcd6fa66f09"

SRC_URI = "file://scikit_build_core-0.11.6-prebuilt.zip \
           file://0001-builder.py-Check-PYTHON_INCLUDE_DIR.patch \
           file://0002-Find-cmake-from-PATH-instead-of-CMAKE_BIN_DIR.patch \
           "

S = "${WORKDIR}/scikit_build_core-0.11.6-prebuilt"

DEPENDS += "zip-native python3-pip-native"

inherit python3native native

do_compile() {
    cd ${S}
    zip -r scikit_build_core-0.11.6-py3-none-any.whl \
        scikit_build_core \
        scikit_build_core-0.11.6.dist-info
}

do_install() {
    install -d ${D}${PYTHON_SITEPACKAGES_DIR}
    ${PYTHON} -m pip install \
            --ignore-installed \
            --prefix=${D}${prefix} \
        ${S}/scikit_build_core-0.11.6-py3-none-any.whl
}