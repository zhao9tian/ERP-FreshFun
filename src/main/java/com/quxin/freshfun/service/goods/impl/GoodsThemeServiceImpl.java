package com.quxin.freshfun.service.goods.impl;

import com.alibaba.fastjson.JSON;
import com.quxin.freshfun.dao.GoodsThemeMapper;
import com.quxin.freshfun.model.goods.ThemePOJO;
import com.quxin.freshfun.service.goods.GoodsService;
import com.quxin.freshfun.service.goods.GoodsThemeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 商品专题service实现类
 * Created by qucheng on 2016/10/28.
 */
@Service("goodsThemeService")
public class GoodsThemeServiceImpl implements GoodsThemeService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private GoodsThemeMapper goodsThemeMapper;

    @Autowired
    private GoodsService goodsService;

    @Override
    public Boolean addTheme(ThemePOJO themePOJO) {
        if (validateTheme(themePOJO)) {
            if (!isExistThemeName(themePOJO.getThemeName())) {
                if (themePOJO.getGoodsIdList() != null && !"".equals(themePOJO.getGoodsIdList())) {
                    try {
                        List<Long> goodsIds = JSON.parseArray(themePOJO.getGoodsIdList(), Long.class);
                        if (!checkGoodsIdRepeatAndExist(goodsIds)) {
                            themePOJO.setIsForbidden(0);
                            themePOJO.setCreated(System.currentTimeMillis() / 1000);
                            themePOJO.setUpdated(System.currentTimeMillis() / 1000);
                            Integer record = goodsThemeMapper.insertTheme(themePOJO);
                            if (record != null && record == 1) {
                                return true;
                            } else {
                                logger.error("新增专题失败");
                            }
                        }
                    } catch (Exception e) {
                        logger.error("传入商品Id格式有误", e);
                        return false ;
                    }
                }else{
                    logger.error("专题下面没有商品Id");
                }
            }else{
                logger.error("专题名称重复");
            }
        } else {
            logger.error("专题参数校验出错");
        }
        return false;
    }

    @Override
    public ThemePOJO queryThemeByThemeId(Long themeId) {
        if (themeId != null && themeId != 0) {
            ThemePOJO themePOJO = goodsThemeMapper.selectThemeByThemeId(themeId);
            if (themePOJO != null) {
                return themePOJO;
            } else {
                logger.error("Id为" + themeId + "的专题为空或已删除");
            }
        } else {
            logger.error("专题Id为空");
        }
        return null;
    }

    @Override
    public Boolean modifyTheme(ThemePOJO themePOJO) {
        if (themePOJO != null) {
            if (themePOJO.getThemeId() != null && themePOJO.getThemeId() != 0) {
                if (themePOJO.getGoodsIdList() != null && !"".equals(themePOJO.getGoodsIdList())) {
                    try {
                        List<Long> goodsIds = JSON.parseArray(themePOJO.getGoodsIdList(), Long.class);
                        if (!checkGoodsIdRepeatAndExist(goodsIds)) {
                            themePOJO.setUpdated(System.currentTimeMillis() / 1000);
                            Integer record = goodsThemeMapper.updateTheme(themePOJO);
                            if (record != null && record == 1) {
                                return true;
                            } else {
                                logger.error("编辑专题失败");
                            }
                        }
                    } catch (Exception e) {
                        logger.error("传入商品Id格式有误", e);
                        return false;
                    }
                }else{
                    logger.error("专题下面没有商品Id");
                }
            } else {
                logger.error("专题Id为空");
            }
        } else {
            logger.error("专题对象为空");
        }
        return false;
    }

    @Override
    public Boolean removeThemeByThemeId(Long themeId) {
        if (themeId != null && themeId != 0) {
            Integer record = goodsThemeMapper.deleteThemeByThemeId(themeId);
            if (record != null && record == 1) {
                return true;
            }
        } else {
            logger.error("专题Id不能为空");
        }
        return false;
    }

    @Override
    public Boolean changeThemeStatus(Long themeId, Integer themeStatus) {
        if (themeId != null && themeId != 0) {
            ThemePOJO themePOJO = new ThemePOJO();
            themePOJO.setThemeId(themeId);
            themePOJO.setUpdated(System.currentTimeMillis() / 1000);
            if (0 == themeStatus) {
                themePOJO.setIsForbidden(0);
            } else if (1 == themeStatus) {
                themePOJO.setIsForbidden(1);
            } else {
                logger.error("禁用/启用状态为空");
                return false;
            }
            Integer record = goodsThemeMapper.updateTheme(themePOJO);
            if (1 == record) {
                return true;
            }
        } else {
            logger.error("专题Id不能为空");
        }
        return false;
    }

    @Override
    public List<ThemePOJO> queryThemeList() {
        List<ThemePOJO> themeList = goodsThemeMapper.selectThemeList();
        if (themeList == null || themeList.size() == 0) {
            logger.error("没有专题列表信息");
        }
        return themeList;
    }

    @Override
    public Boolean isExistThemeName(String themeName) {
        Integer record = goodsThemeMapper.selectCountByThemeName(themeName);
        if (record != null && record >= 1) {
            logger.error("专题名称已经存在");
            return true;
        }
        return false;
    }


    /**
     * 校验专题对象
     *
     * @param themePOJO 专题
     * @return 检验是否成功
     */
    private Boolean validateTheme(ThemePOJO themePOJO) {
        if (themePOJO != null) {
            if (themePOJO.getThemeName() == null || "".equals(themePOJO.getThemeName().trim())) {
                logger.warn("专题名称为空");
                return false;
            }
            if (themePOJO.getThemeDes() == null || "".equals(themePOJO.getThemeDes().trim())) {
                logger.warn("专题描述为空");
                return false;
            }
            if (themePOJO.getThemeImg() == null || "".equals(themePOJO.getThemeImg().trim())) {
                logger.warn("专题图片地址为空");
                return false;
            }
            if (themePOJO.getGoodsIdList() == null || "".equals(themePOJO.getGoodsIdList().trim())) {
                logger.warn("商品id列表为空");
                return false;
            }
        } else {
            logger.warn("专题对象为空");
            return false;
        }
        return true;
    }

    /**
     * 校验商品ID是否重复、存在
     *
     * @param goodsIds 商品Id列表
     * @return true:商品ID重复,或者已经存在  false:没有重复的或者已经存在的商品ID
     */
    private Boolean checkGoodsIdRepeatAndExist(List<Long> goodsIds) {
        Set<Long> set = new HashSet<>(goodsIds);
        if (set.size() != goodsIds.size()) {
            logger.error("专题下的商品Id重复");
            return true;
        } else {
            for (Object goodsId : goodsIds) {
                if (goodsService.queryGoodsByGoodsId((Long) goodsId) == null) {
                    logger.error("Id为" + goodsId + "的商品不存在");
                    return true;
                }
            }
        }
        return false;
    }

}
