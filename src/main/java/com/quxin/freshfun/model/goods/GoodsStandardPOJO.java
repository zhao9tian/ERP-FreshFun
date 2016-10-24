package com.quxin.freshfun.model.goods;

/**
 * 商品规格
 * Created by qingtian on 2016/10/18.
 */
public class GoodsStandardPOJO {
    private Integer id;
    /**
     * 商品编号
     */
    private Integer goodsId;
    /**
     * 品牌
     */
    private String brand;
    /**
     * 产地
     */
    private String productPlace;
    /**
     * 规格
     */
    private String goodsStandard;
    /**
     * 净含量
     */
    private String netContents;
    /**
     * 保质期
     */
    private String shelfLife;
    /**
     * 存储方式
     */
    private String storageMethod;
    /**
     * 配料表
     */
    private String ingredientList;
    /**
     * 是否含糖  0:未填  1: 是 2:否
     */
    private Integer isSugary;
    /**
     * 是否有机：0:未填  1: 是 2:否
     */
    private Integer isOrganic;
    /**
     * 是否进口：0:未填  1: 是 2:否
     */
    private Integer isImported;
    /**
     * 是否盒装：0:未填  1: 是 2:否
     */
    private  Integer isBoxPacked;
    /**
     * 套餐分量
     */
    private String packageComponent;
    /**
     * 口味
     */
    private String taste;
    /**
     * 功能
     */
    private String facility;
    /**
     * 不适宜人群
     */
    private String unsuitable;
    /**
     * 适宜人群
     */
    private String suitable;
    /**
     * 产品剂型
     */
    private String productForm;
    /**
     * 食品添加剂
     */
    private String foodAdditives;
    /**
     * 套餐周期
     */
    private String setCycle;
    /**
     * 厂名
     */
    private String factoryName;
    /**
     * 厂址
     */
    private String factorySite;
    /**
     * 产品标准号
     */
    private String productStandardNum;
    /**
     * 生鲜储存温度
     */
    private String freshStoreTemp;
    /**
     * 酒精度数
     */
    private String proof;
    /**
     * 适应场景
     */
    private String adaptiveScene;
    /**
     * 包装方式
     */
    private String packingMethod;
    /**
     * 包装种类
     */
    private String packingType;
    /**
     * 葡萄酒种类
     */
    private String wineStyle;
    /**
     * 套装规格
     */
    private String suitSpecification;
    /**
     *  醒酒时间
     */
    private String decanteDuration;
    /**
     * 年份
     */
    private String particularYear;
    /**
     * 香味
     */
    private String smell;
    /**
     * 颜色分类
     */
    private String colourSort;
    /**
     * 风格类型
     */
    private String styleType;
    /**
     * 尺寸
     */
    private String size;

    /**
     * 特产品种
     */
    private String specialty;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProductPlace() {
        return productPlace;
    }

    public void setProductPlace(String productPlace) {
        this.productPlace = productPlace;
    }

    public String getGoodsStandard() {
        return goodsStandard;
    }

    public void setGoodsStandard(String goodsStandard) {
        this.goodsStandard = goodsStandard;
    }

    public String getNetContents() {
        return netContents;
    }

    public void setNetContents(String netContents) {
        this.netContents = netContents;
    }

    public String getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(String shelfLife) {
        this.shelfLife = shelfLife;
    }

    public String getStorageMethod() {
        return storageMethod;
    }

    public void setStorageMethod(String storageMethod) {
        this.storageMethod = storageMethod;
    }

    public String getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(String ingredientList) {
        this.ingredientList = ingredientList;
    }

    public Integer getIsSugary() {
        return isSugary;
    }

    public void setIsSugary(Integer isSugary) {
        this.isSugary = isSugary;
    }

    public Integer getIsOrganic() {
        return isOrganic;
    }

    public void setIsOrganic(Integer isOrganic) {
        this.isOrganic = isOrganic;
    }

    public Integer getIsImported() {
        return isImported;
    }

    public void setIsImported(Integer isImported) {
        this.isImported = isImported;
    }

    public Integer getIsBoxPacked() {
        return isBoxPacked;
    }

    public void setIsBoxPacked(Integer isBoxPacked) {
        this.isBoxPacked = isBoxPacked;
    }

    public String getPackageComponent() {
        return packageComponent;
    }

    public void setPackageComponent(String packageComponent) {
        this.packageComponent = packageComponent;
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getUnsuitable() {
        return unsuitable;
    }

    public void setUnsuitable(String unsuitable) {
        this.unsuitable = unsuitable;
    }

    public String getSuitable() {
        return suitable;
    }

    public void setSuitable(String suitable) {
        this.suitable = suitable;
    }

    public String getProductForm() {
        return productForm;
    }

    public void setProductForm(String productForm) {
        this.productForm = productForm;
    }

    public String getFoodAdditives() {
        return foodAdditives;
    }

    public void setFoodAdditives(String foodAdditives) {
        this.foodAdditives = foodAdditives;
    }

    public String getSetCycle() {
        return setCycle;
    }

    public void setSetCycle(String setCycle) {
        this.setCycle = setCycle;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getFactorySite() {
        return factorySite;
    }

    public void setFactorySite(String factorySite) {
        this.factorySite = factorySite;
    }

    public String getProductStandardNum() {
        return productStandardNum;
    }

    public void setProductStandardNum(String productStandardNum) {
        this.productStandardNum = productStandardNum;
    }

    public String getFreshStoreTemp() {
        return freshStoreTemp;
    }

    public void setFreshStoreTemp(String freshStoreTemp) {
        this.freshStoreTemp = freshStoreTemp;
    }

    public String getProof() {
        return proof;
    }

    public void setProof(String proof) {
        this.proof = proof;
    }

    public String getAdaptiveScene() {
        return adaptiveScene;
    }

    public void setAdaptiveScene(String adaptiveScene) {
        this.adaptiveScene = adaptiveScene;
    }

    public String getPackingMethod() {
        return packingMethod;
    }

    public void setPackingMethod(String packingMethod) {
        this.packingMethod = packingMethod;
    }

    public String getPackingType() {
        return packingType;
    }

    public void setPackingType(String packingType) {
        this.packingType = packingType;
    }

    public String getWineStyle() {
        return wineStyle;
    }

    public void setWineStyle(String wineStyle) {
        this.wineStyle = wineStyle;
    }

    public String getSuitSpecification() {
        return suitSpecification;
    }

    public void setSuitSpecification(String suitSpecification) {
        this.suitSpecification = suitSpecification;
    }

    public String getDecanteDuration() {
        return decanteDuration;
    }

    public void setDecanteDuration(String decanteDuration) {
        this.decanteDuration = decanteDuration;
    }

    public String getParticularYear() {
        return particularYear;
    }

    public void setParticularYear(String particularYear) {
        this.particularYear = particularYear;
    }

    public String getSmell() {
        return smell;
    }

    public void setSmell(String smell) {
        this.smell = smell;
    }

    public String getColourSort() {
        return colourSort;
    }

    public void setColourSort(String colourSort) {
        this.colourSort = colourSort;
    }

    public String getStyleType() {
        return styleType;
    }

    public void setStyleType(String styleType) {
        this.styleType = styleType;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    @Override
    public String toString() {
        return "GoodsStandardPOJO{" +
                "id=" + id +
                ", goodsId=" + goodsId +
                ", brand='" + brand + '\'' +
                ", productPlace='" + productPlace + '\'' +
                ", goodsStandard='" + goodsStandard + '\'' +
                ", netContents='" + netContents + '\'' +
                ", shelfLife='" + shelfLife + '\'' +
                ", storageMethod='" + storageMethod + '\'' +
                ", ingredientList='" + ingredientList + '\'' +
                ", isSugary=" + isSugary +
                ", isOrganic=" + isOrganic +
                ", isImported=" + isImported +
                ", isBoxPacked=" + isBoxPacked +
                ", packageComponent='" + packageComponent + '\'' +
                ", taste='" + taste + '\'' +
                ", facility='" + facility + '\'' +
                ", unsuitable='" + unsuitable + '\'' +
                ", suitable='" + suitable + '\'' +
                ", productForm='" + productForm + '\'' +
                ", foodAdditives='" + foodAdditives + '\'' +
                ", setCycle='" + setCycle + '\'' +
                ", factoryName='" + factoryName + '\'' +
                ", factorySite='" + factorySite + '\'' +
                ", productStandardNum='" + productStandardNum + '\'' +
                ", freshStoreTemp=" + freshStoreTemp +
                ", proof=" + proof +
                ", adaptiveScene='" + adaptiveScene + '\'' +
                ", packingMethod='" + packingMethod + '\'' +
                ", packingType='" + packingType + '\'' +
                ", wineStyle='" + wineStyle + '\'' +
                ", suitSpecification='" + suitSpecification + '\'' +
                ", decanteDuration='" + decanteDuration + '\'' +
                ", particularYear='" + particularYear + '\'' +
                ", smell='" + smell + '\'' +
                ", colourSort='" + colourSort + '\'' +
                ", styleType='" + styleType + '\'' +
                ", size='" + size + '\'' +
                ", specialty='" + specialty + '\'' +
                '}';
    }
}
