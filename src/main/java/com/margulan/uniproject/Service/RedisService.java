package com.margulan.uniproject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public interface RedisService {

    void saveData(String key, Object data);
    Object getData(String key);
    void deleteData(String key);

}
