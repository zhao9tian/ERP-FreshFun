package com.quxin.freshfun.service.goods.goodsImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.quxin.freshfun.dao.GoodsSortMapper;
import com.quxin.freshfun.model.goods.GoodsPOJO;
import com.quxin.freshfun.model.goods.GoodsSortParam;
import com.quxin.freshfun.model.goods.GoodsSortPOJO;
import com.quxin.freshfun.service.goods.GoodsSortService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 排序Service实现类
 * Created by qucheng on 2016/10/12.
 */
@Service("goodsSortService")
public class GoodsSortServiceImpl implements GoodsSortService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private GoodsSortMapper goodsSortMapper;
    @Override
    public List<GoodsPOJO> querySortGoods() {
        String sortValue = goodsSortMapper.selectPictureWall("pictureWall");
        if(sortValue == null || "".equals(sortValue)){
            return null;
        }
        JSONArray sortArr = JSON.parseArray(sortValue);
        List<Integer> sortList = new ArrayList();
        if(sortArr != null && sortArr.size() > 0){
            for(Object goodsId : sortArr){
                sortList.add((Integer) goodsId);
            }
            return goodsSortMapper.selectSortGoods(sortList);
        }else{
            return null;
        }

    }

    @Override
    public GoodsPOJO querySortGoodsById(Integer goodId) {
        if (goodId == null){
            logger.error("goodsId不能为空");
            return null;
        }
        return goodsSortMapper.selectGoodsPOJOById(goodId);
    }

    @Override
    public Boolean addAllGoodsSort(List allSort) {
        Boolean b = false ;
        if(allSort != null && allSort.size() > 0){
            Integer[] sort = new Integer[allSort.size()];
            if(allSort.size() > 20){
                logger.error("排序对象总数大于20");
            }else{
                for(int i = 0 ; i < allSort.size() ; i++){
                    sort[i] = (Integer) allSort.get(i);
                }
                GoodsSortPOJO goodsSortPOJO = new GoodsSortPOJO();
                goodsSortPOJO.setSortKey("pictureWall");
                goodsSortPOJO.setSortValue(JSON.toJSONString(sort));
                String sortValue = goodsSortMapper.selectPictureWall("pictureWall");
                if(sortValue != null && !"".equals(sortValue)){
                    Integer num =goodsSortMapper.updateGoodsSort(goodsSortPOJO);
                    if(num == 0){
                        logger.error("插入排序对象失败");
                        return false ;
                    }
                }else{
                    Integer num = goodsSortMapper.insertGoodsSort(goodsSortPOJO);
                    if(num == 0){
                        logger.error("插入排序对象失败");
                        return false ;
                    }
                }
                b= true ;
            }
        }else{
            logger.error("排序对象为空");
        }
        return b;
    }

}
