package com.quxin.freshfun.constant;

/**
 * 商品常量
 * Created by qucheng on 2016/10/20.
 */
public class GoodsConstant {
    //图墙排序key TODO  更新到goods_property表里面
    public static final String PICTURE_WALL = "pictureWall";
    //保存图墙排序的字段
    public static final String GOODS_SORT_KEY = "goodsSort";
    //精选排序DB里面的key
    public static final String SELECTION_SORT_KEY = "selectionSort";
    public static final String SELECTION_GOODSID_KEY = "goodsId";
    public static final String SELECTION_IMG_KEY = "img";
    //banner排序DB key
    public static final String BANNER_SORT_KEY = "bannerSort";
    //暂时只有专题,页面传过来的参数不需要URL,只需要专题Id
    public static final String BANNER_THEMEID_KEY = "themeId";
    public static final String BANNER_IMG_KEY = "img";
    //需要一个URL生成工具提供给运营,页面只需要给图片和URL就行,就不需要填写专题Id
    public static final String BANNER_URL_KEY = "url";


    //专题首页3个排序DB-key
    public static final String THEME_SORT_KEY = "themeSort";

    //保存分类页图片
    public static final String TYPE_KEY = "typeImg";
    //查询规格key--value
    public static final String GOODS_STANDARD_KEY = "goodsStandard";

}
