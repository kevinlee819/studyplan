package com.se1703.core.Utils;

import com.mongodb.DBObject;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * @author leekejin
 * @date 2020/9/14 17:00
 **/
public class MongoUtils {

    /**
     * 转换为ObjectId
     *
     * @param ids
     * @return
     */
    public static ObjectId[] convertObjectId(List<String> ids) {
        if (ids == null) {
            return new ObjectId[0];
        }
        ObjectId[] objectIds = new ObjectId[ids.size()];
        if (ids == null || ids.size() == 0) {
            return objectIds;
        }
        for (int i = 0; i < ids.size(); i++) {
            objectIds[i] = new ObjectId(ids.get(i));
        }
        return objectIds;
    }

    public static ObjectId convertObjectId(String id){
        if (id == null || id.length() == 0) {
            return null;
        } else {
            return new ObjectId(id);
        }
    }

}
