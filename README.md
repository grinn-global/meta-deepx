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
```
