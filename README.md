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

Note that the dx-npu recipe requires `DMA_ENGINE` option to be enabled in the kernel. Use this command to modify the kernel configuration:
```bash
bitbake -c menuconfig virtual/kernel
```

This layer provides the following configuration options:
* `DX_DEVICE` - choose the device (m1, m1a, l1, l3)
* `DX_PCIE` - choose the PCIe (deepx, xilinx)
* `PACKAGECONFIG[onnxruntime]` - use ONNX Runtime for unsupported operations (requires `onnxruntime` recipe, version `1.12.0`)
* `PACKAGECONFIG[python]` - enable Python bindings
* `PACKAGECONFIG[service]` - enable DX-RT service (support for multiple processes)
* `PACKAGECONFIG[shared_dxrt_lib]` - use shared DX-RT library

The default configuration is equivalent to the following:
```bash
DX_DEVICE = "m1a"
DX_PCIE = "deepx"
PACKAGECONFIG:pn-dx-rt = " python service shared_dxrt_lib"
```
Modify these variables in your `local.conf` to change the default behavior.
