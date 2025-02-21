# DeepX Yocto Meta Layer

## Adding the meta-deepx layer to your build

To add the meta-deepx layer to your build, run the following command:
```bash
bitbake-layers add-layer meta-deepx
```

## Using the meta-deepx layer

To use the meta-deepx layer, add the following to your local.conf:
```bash
IMAGE_INSTALL:append = " dx-npu dx-rt"
```

To supress the QA warning, add the following to your local.conf:
```bash
LICENSE_PATH += "${TOPDIR}/../meta-deepx/custom-licenses"
```

To change the default configuration, add the following to your local.conf:
```bash
DX_DEVICE = "m1" | "m1a" | "l1" | "l3"
DX_PCIE = "deepx" | "pcie"
DX_USE_ORT = "1" | "0"
DX_USE_PYTHON = "1" | "0"
DX_USE_SERVICE = "1" | "0"
DX_USE_SHARED_DXRT_LIB = "1" | "0"
```

## TODO

* add Kconfig fragments (DMA_ENGINE)
* add python package in dx-rt
* add onnxruntime recipe
* add dx-app
