//package com.margulan.uniproject;
//
//import redis.clients.jedis.Connection;
//import redis.clients.jedis.Jedis;
//
//public class TestRedis {
//    public static void main(String[] args) {
//        Jedis jedis = new Jedis("redis://default:*******@redis-10895.c250.eu-central-1-1.ec2.redns.redis-cloud.com:10895");
//        Connection connection = jedis.getConnection();
//    }
//}

package com.margulan.uniproject;

import redis.clients.jedis.Jedis;

public class TestRedis {
    public static void main(String[] args) {

        Jedis jedis = new Jedis("redis-14832.c293.eu-central-1-1.ec2.redns.redis-cloud.com", 14832);

        jedis.auth("eZExxgbQyEjnHP8XQXEzqpsDUkQ1DnqO");

        System.out.println("Connection successful: " + jedis.ping());

    }
}
