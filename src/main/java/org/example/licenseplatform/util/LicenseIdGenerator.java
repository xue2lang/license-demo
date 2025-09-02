package org.example.licenseplatform.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * License ID 生成工具类
 * 格式示例：DOCX-TST-202509-001
 */
@Component
public class LicenseIdGenerator {

    private static final String REDIS_KEY_PREFIX = "license:id:";

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 自动生成带序号的 licenseId
     *
     * @param projectId    项目标识（如 docx-platform）
     * @param customerName 客户公司名称（如 测试公司）
     * @return licenseId 如 DOCX-TST-202509-001
     */
    public String generate(String projectId, String customerName) {
        String projectCode = toShortCode(projectId);      // 如：DOCX
        String customerCode = toShortCode(customerName);  // 如：TST
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM")); // 如：202509

        // Redis key: license:id:DOCX:TST:202509
        String redisKey = String.format("%s%s:%s:%s",
                REDIS_KEY_PREFIX, projectCode, customerCode, datePart);

        // 自增序号（从 1 开始）
        Long seq = redisTemplate.opsForValue().increment(redisKey);

        // 序号格式化为 3 位数字（如 001）
        String seqPart = String.format("%03d", seq);

        // 拼接完整 License ID
        return String.join("-", projectCode, customerCode, datePart, seqPart);
    }

    /**
     * 将输入转换为大写简写（保留前缀 4 位）
     *
     * @param input 原始字符串
     * @return 大写简写（最多 4 位）
     */
    private String toShortCode(String input) {
        if (input == null) return "NULL";
        // 只保留字母数字，转为大写
        String clean = input.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
        return clean.length() <= 4 ? clean : clean.substring(0, 4);
    }
}
