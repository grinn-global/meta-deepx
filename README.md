# DeepX Yocto Meta Layer

## Using the meta-deepx layer

To add the meta-deepx layer to your build, run the following command in the build directory:
```bash
bitbake-layers add-layer ../meta-deepx
```

To use the meta-deepx layer, add the following to your `local.conf`:
```bash
IMAGE_INSTALL:append = " dx-rt"
```

To supress the QA warning, add the following to your `local.conf`:
```bash
LICENSE_PATH += "${TOPDIR}/../meta-deepx/custom-licenses"
```

Note that the dx-npu recipe requires `DMA_ENGINE` option to be enabled in the kernel. Use this command to modify the kernel configuration:
```bash
bitbake -c menuconfig virtual/kernel
```

To modify the configuration, change these default values in your `local.conf`:
```bash
# "m1", "m1a", "l1", "l3"
DX_DEVICE = "m1a"

# "deepx", "pcie"
DX_PCIE = "deepx"

# "1", "0"
DX_USE_ORT = "0"
DX_USE_PYTHON = "1"
DX_USE_SERVICE = "1"
DX_USE_SHARED_DXRT_LIB = "1"
DX_ENABLE_DEBUG_INFO = "1"
```

## TODO

* fix USE_SERVICE bug (ftok2 error) (set to 0 to make it work)
* add ctypes dependency
* add dx-app
