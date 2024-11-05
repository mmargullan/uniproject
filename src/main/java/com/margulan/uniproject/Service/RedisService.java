package com.margulan.uniproject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public interface RedisService {

    public void saveData(String key, Object data);
    public Object getData(String key);
    public void deleteData(String key);

}
