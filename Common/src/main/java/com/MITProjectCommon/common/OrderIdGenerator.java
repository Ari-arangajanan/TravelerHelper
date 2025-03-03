package com.MITProjectCommon.common;import java.text.SimpleDateFormat;import java.util.Date;import java.util.UUID;public class OrderIdGenerator {    public static String generateOrderId() {        // Generate a timestamp        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());        // Generate a random UUID (shortened)        String uniqueId = UUID.randomUUID().toString().replace("-", "").substring(0, 6);        // Combine timestamp + unique ID        return "ORD-" + timestamp + "-" + uniqueId;    }}