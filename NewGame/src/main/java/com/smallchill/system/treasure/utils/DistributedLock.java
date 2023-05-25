//package com.smallchill.system.treasure.utils;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//
//@Component
//public class DistributedLock {
//
//    @Resource
//    private RedisTemplate<String, String> redisTemplate;
//
//    public boolean tryLock(String lockName, String holderId, long expireMillis) {
//        String key = "lock:" + lockName;
//        long now = System.currentTimeMillis();
//        long timeout = now + expireMillis;
//
//        Boolean result = redisTemplate.execute((RedisCallback<Boolean>) connection -> {
//            // 设置NX参数，保证同一时间只能有一个线程获取到锁
//            Boolean success = connection.set(key.getBytes(), holderId.getBytes(), Expiration.milliseconds(expireMillis), RedisStringCommands.SetOption.SET_IF_ABSENT);
//            if (success != null && success) {
//                // 锁获取成功，设置过期时间，并返回true
//                connection.pExpire(key.getBytes(), expireMillis);
//                return true;
//            }
//            // 获取锁失败，判断是否已经超时
//            byte[] value = connection.get(key.getBytes());
//            if (value != null && Arrays.equals(value, holderId.getBytes())) {
//                // 当前线程已经持有该锁，重新设置过期时间，并返回true
//                connection.pExpire(key.getBytes(), expireMillis);
//                return true;
//            }
//            return false;
//        });
//
//        return result != null && result && System.currentTimeMillis() < timeout;
//    }
//
//    public void unlock(String lockName, String holderId) {
//        String key = "lock:" + lockName;
//
//        redisTemplate.execute((RedisCallback<Object>) connection -> {
//            byte[] value = connection.get(key.getBytes());
//            if (value != null && Arrays.equals(value, holderId.getBytes())) {
//                // 当前线程持有该锁，删除该键
//                connection.del(key.getBytes());
//            }
//            return null;
//        });
//    }
//}