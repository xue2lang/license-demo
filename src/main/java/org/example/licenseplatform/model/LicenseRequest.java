package org.example.licenseplatform.model;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 客户端请求生成 License 时提交的参数模型（来自前端页面）
 * 用于服务端生成签名后的 License 文件
 */
@Data
public class LicenseRequest {

    /** 项目 ID（用于区分不同的项目或产品线） */
    @NotBlank(message = "项目 ID 不能为空")
    private String projectId;

    /** 客户名称（公司名称或实际使用人） */
    @NotBlank(message = "客户名称不能为空")
    private String customer;

    /** 授权起始时间（单位：毫秒时间戳） */
    @NotNull(message = "起始时间不能为空")
    private Long issueDate;

    /** 授权过期时间（单位：毫秒时间戳） */
    @NotNull(message = "到期时间不能为空")
    private Long expireDate;

    /** 功能模块配置，可为空 */
    private Map<String, Boolean> features;

    /** 授权绑定的机器列表（支持 standalone 或 cluster 模式） */
    @NotNull(message = "机器指纹信息不能为空")
    @Size(min = 1, message = "至少绑定一台机器")
    private List<MachineInfo> boundMachines;

    /** 授权模式：standalone / cluster */
    @NotBlank(message = "授权模式不能为空")
    private String mode;
}
