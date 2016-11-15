package com.quxin.freshfun.controller.goods;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.quxin.freshfun.model.goods.ThemeOut;
import com.quxin.freshfun.model.goods.ThemePOJO;
import com.quxin.freshfun.model.goods.ThemeSortOut;
import com.quxin.freshfun.service.goods.GoodsService;
import com.quxin.freshfun.service.goods.GoodsSortService;
import com.quxin.freshfun.service.goods.GoodsThemeService;
import com.quxin.freshfun.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

/**
 * 专题controller
 * Created by qucheng on 2016/10/28.
 */
@RequestMapping("/goodsTheme")
@Controller
public class GoodsThemeController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private GoodsThemeService goodsThemeService;

    @Autowired
    private GoodsSortService goodsSortService;

    @Autowired
    private GoodsService goodsService;

    /**
     * 保存专题
     *
     * @param param 专题对象
     * @return 返回结果
     */
    @RequestMapping(value = "/addTheme", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveTheme(@RequestBody Map<String, Map<String , Object>> param) {
        Map<String, Object> result = new HashMap<>();
        if (param != null) {
            Map<String , Object> themeParam = param.get("themeParam");
            Integer themeId = (Integer) themeParam.get("themeId");
            String themeName = (String) themeParam.get("themeName");
            String themeImg = (String) themeParam.get("themeImg");
            String themeDes = (String) themeParam.get("themeDes");
            List<Integer> goodsIds;
            try {
                goodsIds = (List<Integer>) themeParam.get("goodsIds");
                if(goodsIds != null && goodsIds.size() != 0){
                    Integer listSize = goodsIds.size() ;
                    Set<Integer> set = new HashSet<>(goodsIds);
                    if(set.size() != listSize){
                        return ResultUtil.fail(1004 , "商品Id重复");
                    }else{
                        for (Integer goodsId : goodsIds) {
                            Long id = Long.valueOf(goodsId);
                            if (goodsService.queryGoodsByGoodsId(id) == null) {
                                return ResultUtil.fail(1004 , "Id为" + goodsId + "的商品不存在");
                            }
                        }
                    }
                }else{
                    return ResultUtil.fail(1004 , "专题下未传入商品Id");
                }
            }catch (ClassCastException e){
                return ResultUtil.fail(1004 , "商品Id传入格式有误");
            }
            ThemePOJO theme = new ThemePOJO();
            theme.setThemeName(themeName);
            theme.setThemeDes(themeDes);
            theme.setThemeImg(themeImg);
            theme.setGoodsIdList(JSON.toJSONString(goodsIds));
            //判断Id是否为空决定是保存还是编辑
            if (themeId == null || themeId == 0) {
                if (goodsThemeService.addTheme(theme)) {
                    Map<String, Object> status = new HashMap<>();
                    status.put("code", 1001);
                    status.put("msg", "请求成功");
                    result.put("status", status);
                } else {
                    result = ResultUtil.fail(1004, "保存商品请求失败");
                }
            } else {
                Long themeIdLong = Long.valueOf(themeId);
                theme.setThemeId(themeIdLong);
                if (goodsThemeService.modifyTheme(theme)) {
                    Map<String, Object> status = new HashMap<>();
                    status.put("code", 1001);
                    status.put("msg", "请求成功");
                    result.put("status", status);
                } else {
                    result = ResultUtil.fail(1004, "保存商品请求失败");
                }
            }
        } else {
            result = ResultUtil.fail(1004, "参数对象为空");
        }
        return result;
    }

    /**
     * 查询专题ById
     *
     * @param themeId 专题Id
     * @return 请求结果
     */
    @RequestMapping(value = "/queryThemeByThemeId", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryThemeByThemeId(Long themeId) {
        Map<String, Object> result;
        if (themeId != null && themeId != 0) {
            ThemePOJO theme = goodsThemeService.queryThemeByThemeId(themeId);
            if (theme != null) {
                ThemeOut themeOut = new ThemeOut();
                themeOut.setThemeId(themeId);
                themeOut.setThemeName(theme.getThemeName());
                themeOut.setThemeImg(theme.getThemeImg());
                themeOut.setThemeDes(theme.getThemeDes());
                themeOut.setGoodsIdList(JSONArray.parseArray(theme.getGoodsIdList(), Long.class));
                themeOut.setIsForbidden(theme.getIsForbidden());
                result = ResultUtil.success(themeOut);
            } else {
                result = ResultUtil.fail(1004, "themeId为:" + themeId + "的专题不存在");
            }
        } else {
            result = ResultUtil.fail(1004, "themeId为空");
        }
        return result;
    }

    /**
     * 查询专题ById是否存储在
     *
     * @param themeId 专题Id
     * @return 请求结果
     */
    @RequestMapping(value = "/isExistThemeByThemeId", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> isExistThemeByThemeId(Long themeId) {
        Map<String, Object> result = new HashMap<>();
        if (themeId != null && themeId != 0) {
            ThemePOJO theme = goodsThemeService.queryThemeByThemeId(themeId);
            Map<String , Object> status = new HashMap<>();
            if (theme != null) {
                status.put("code", 1001);
                status.put("msg", "专题可用");
            } else {
                status.put("code", 1004);
                status.put("msg", "该专题不存在");
            }
            result.put("status" , status);
        } else {
            result = ResultUtil.fail(1004, "themeId为空");
        }
        return result;
    }

    /**
     * 删除专题ById
     *
     * @param themeId 专题Id
     * @return 返回结果
     */
    @RequestMapping(value = "/removeThemeByThemeId", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> removeThemeByThemeId(Long themeId) {
        Map<String, Object> result = new HashMap<>();
        if (themeId != null && themeId != 0) {
            if (goodsThemeService.removeThemeByThemeId(themeId)) {
                Map<String, Object> status = new HashMap<>();
                status.put("code", 1001);
                status.put("msg", "请求成功");
                result.put("status", status);
            } else {
                result = ResultUtil.fail(1004, "themeId为:" + themeId + "的专题不存在");
            }
        } else {
            logger.error("themeId为空");
            result = ResultUtil.fail(1004, "themeId为空");
        }
        return result;
    }

    /**
     * 禁用/启用专题ById
     *
     * @param themeId 专题Id
     * @return 返回结果
     */
    @RequestMapping(value = "/changeThemeStatus", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> changeThemeStatus(Long themeId, Integer themeStatus) {
        Map<String, Object> result = new HashMap<>();
        if (themeId == null || themeId == 0) {
            logger.error("themeId为空");
            return ResultUtil.fail(1004, "themeId为空");
        }
        if (themeStatus == null) {
            logger.error("themeStatus为空");
            return ResultUtil.fail(1004, "themeStatus为空");
        }
        if (goodsThemeService.changeThemeStatus(themeId, themeStatus)) {
            Map<String, Object> status = new HashMap<>();
            status.put("code", 1001);
            status.put("msg", "请求成功");
            result.put("status", status);
        } else {
            result = ResultUtil.fail(1004, "禁用/启用专题失败");
        }
        return result;
    }

    /**
     * 查询专题列表
     *
     * @return 返回请求结果
     */
    @RequestMapping(value = "/queryThemeList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryThemeList() {
        Map<String, Object> result;
        List<ThemePOJO> themePOJOList = goodsThemeService.queryThemeList();
        List<ThemeOut> themeOuts = new ArrayList<>();
        if (themePOJOList != null && themePOJOList.size() > 0) {
            for (ThemePOJO theme : themePOJOList) {
                ThemeOut themeOut = new ThemeOut();
                themeOut.setThemeId(theme.getThemeId());
                themeOut.setThemeName(theme.getThemeName());
                themeOut.setThemeImg(theme.getThemeImg());
                themeOut.setThemeDes(theme.getThemeDes());
                themeOut.setGoodsIdList(JSON.parseArray(theme.getGoodsIdList(), Long.class));
                themeOut.setIsForbidden(theme.getIsForbidden());
                themeOuts.add(themeOut);
            }
        }
        Map<String, Object> data = new HashMap<>();
        data.put("themeList", themeOuts);
        result = ResultUtil.success(data);
        return result;
    }


    /**
     * 保存专题排序
     *
     * @param themeSort 专题排序内容
     * @return 请求结果
     */
    @RequestMapping(value = "addThemeSort", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveThemeSort(@RequestBody Map<String, List<Integer>> themeSort) {
        Map<String, Object> result = new HashMap<>();
        if (themeSort != null && themeSort.size() != 0) {
            try{
                List<Integer> themeSortParam = themeSort.get("themeSort");
                List<Long> themeSortList = new ArrayList<>();
                for(Integer themeId : themeSortParam){
                    Long themeIdLong = Long.valueOf(themeId);
                    themeSortList.add(themeIdLong);
                }
                if (themeSortList.size() > 0) {
                    if (goodsSortService.addThemeSort(themeSortList)) {
                        Map<String, Object> status = new HashMap<>();
                        status.put("code", 1001);
                        status.put("msg", "请求成功");
                        result.put("status", status);
                    } else {
                        result = ResultUtil.fail(1004, "排序对象保存失败");
                    }
                } else {
                    logger.error("排序对象为空");
                    result = ResultUtil.fail(1004, "排序对象为空");
                }
            }catch (ClassCastException e){
                logger.error("专题Id格式有误" , e);
                return ResultUtil.fail(1004 , "专题Id格式有误");
            }
        } else {
            logger.error("排序参数为空");
            result = ResultUtil.fail(1004, "排序参数为空");
        }
        return result;
    }

    /**
     * 查询排序列表,显示专题Id,和专题名称
     *
     * @return 请求结果
     */
    @RequestMapping(value = "/queryThemeSort", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryThemeSort() {
        Map<String, Object> result;
        List<ThemePOJO> themes = goodsSortService.queryThemeSort();
        List<ThemeSortOut> themeSortOuts = new ArrayList<>();
        if (themes != null) {
            for (ThemePOJO theme : themes) {
                ThemeSortOut themeSortOut = new ThemeSortOut();
                themeSortOut.setThemeId(theme.getThemeId());
                themeSortOut.setThemeName(theme.getThemeName());
                themeSortOuts.add(themeSortOut);
            }
            Map<String, Object> data = new HashMap<>();
            data.put("themeSort", themeSortOuts);
            result = ResultUtil.success(data);
        } else {
            result = ResultUtil.fail(1004, "未查询到排序信息");
        }
        return result;
    }

}
