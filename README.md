[![](https://jitpack.io/v/xuqiqiang/EasyCamera2-GPUImage.svg)](https://jitpack.io/#xuqiqiang/EasyCamera2-GPUImage)

# EasyCamera2-GPUImage
EasyCamera2的图像处理库，可实现实时滤镜

- 与EasyCamera2其他功能独立
- 支持相机预览滤镜处理
- 支持拍照和录制视频的滤镜处理
- 支持图片的滤镜处理
- 内置几十种滤镜方案

## Gradle dependency

```
allprojects {
        repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
	implementation 'com.github.xuqiqiang:EasyCamera2-GPUImage:1.0.0'
}
```

## Usage


```java
mProperties = new Properties()
        .useGPUImage(true) // 使用GPUImage，启用需要引入此library，可实现实时滤镜。默认不启用。
        .setGPUImageAssetsDir(this, "filter"); // 滤镜纹理资源的目录
```

```java
BaseFilter filter = AllFilters.FILTERS[1];
filter.setIntensity(0.8f);
((SingleCameraModule) mCameraModule).setFilterConfig(filter.getConfig());
```

此项目基于wysaid的android-gpuimage-plus封装实现，感谢wysaid。