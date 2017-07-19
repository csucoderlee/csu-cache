package cn.edu.csu.domain

/**
 * Created by lixiang on 2017 07 19 上午12:18.
 */
class ProductInfo {

    Long id;
    String name;
    Double price;
    String pictureList;
    String specification;
    String service;
    String color;
    String size;
    Long shopId;
    String modifiedTime;
    Long cityId;
    String cityName;
    Long brandId;
    String brandName;

    @Override
    String toString() {
        return "ProductInfo [id=" + id + ", name=" + name + ", price=" + price
        +", pictureList=" + pictureList + ", specification="
        +specification + ", service=" + service + ", color=" + color
        +", size=" + size + ", shopId=" + shopId + ", modifiedTime="
        +modifiedTime + "]";
    }
}
