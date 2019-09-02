package com.wzkj.hzyp.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
public interface CommonService {

    Map getMapByList(PageInfo pageInfo, List list);
}
