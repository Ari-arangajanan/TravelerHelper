package com.MITProjectService.bot.request;import com.MITProjectCommon.constants.Constants;import lombok.AllArgsConstructor;import lombok.Data;import lombok.NoArgsConstructor;@Data@NoArgsConstructor@AllArgsConstructorpublic class ServiceCategoryRequest {    private int page=(int) Constants.DEFAULT_PAGE;    private int limit= (int) Constants.DEFAULT_LIMIT;    private Long id;    private String categoryName;    private String description;    private int status;}