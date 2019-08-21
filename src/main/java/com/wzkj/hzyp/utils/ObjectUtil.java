package com.wzkj.hzyp.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
public class ObjectUtil {


    /**
     * 对象转map
     * @param obj
     * @return
     */
    public static Map<String, Object> objToMap(Object obj) {

        Map<String, Object> map = new HashMap<String, Object>();
        Field[] fields = obj.getClass().getDeclaredFields();	// 获取f对象对应类中的所有属性域
        for (int i = 0, len = fields.length; i < len; i++) {
            String varName = fields[i].getName();
            varName = varName.toLowerCase();					// 将key置为小写，默认为对象的属性
            try {
                boolean accessFlag = fields[i].isAccessible();	// 获取原来的访问控制权限
                fields[i].setAccessible(true);					// 修改访问控制权限
                Object o = fields[i].get(obj);					// 获取在对象f中属性fields[i]对应的对象中的变量
                if (o != null){
                    map.put(varName, o.toString());
                }
                fields[i].setAccessible(accessFlag);			// 恢复访问控制权限
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }
        return map;
    }



}
