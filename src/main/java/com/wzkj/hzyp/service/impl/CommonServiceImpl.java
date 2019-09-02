package com.wzkj.hzyp.service.impl;

import com.github.pagehelper.PageInfo;
import com.wzkj.hzyp.service.CommonService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
@Service
public class CommonServiceImpl implements CommonService {
    @Override
    public Map getMapByList(PageInfo pageInfo, List list) {
        Map map = new HashMap();
        Integer total = Math.toIntExact(pageInfo.getTotal());
        map.put("total",total);
        map.put("list",list);
        return map;
    }
}
