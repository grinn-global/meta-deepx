SUMMARY = "pybind11"
DESCRIPTION = "Lightweight header-only library that exposes C++ types in Python"
HOMEPAGE = "https://github.com/pybind/pybind11"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=774f65abd8a7fe3124be2cdf766cd06f"

PV = "2.12.0"
SRC_URI = "git://github.com/pybind/pybind11.git;protocol=https;branch=v2.12"
SRCREV = "2e0815278cb899b20870a67ca8205996ef47e70f"

S = "${WORKDIR}/git"

DEPENDS += "python3-native"

inherit cmake

EXTRA_OECMAKE = "-DPYBIND11_TEST=OFF"

BBCLASSEXTEND = "native"
