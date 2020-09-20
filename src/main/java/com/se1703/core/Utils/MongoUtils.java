package com.se1703.core.Utils;

import com.mongodb.DBObject;
import com.se1703.core.exception.BusinessException;
import org.bson.types.ObjectId;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author leekejin
 * @date 2020/9/14 17:00
 **/
public class MongoUtils {

    public static Date objectId2Date(String objectId){
        if (objectId == null){
            return null;
        }
        long time = Integer.parseInt(objectId.substring(0, 8), 16) * 1000L;
        Date date = new Date(time);
        return date;
    }

    public static ObjectId date2ObjectId(Date date){
        ObjectId objectId = new ObjectId(date);
        return objectId;
    }

}
