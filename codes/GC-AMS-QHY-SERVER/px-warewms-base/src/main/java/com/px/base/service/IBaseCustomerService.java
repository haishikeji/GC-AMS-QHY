package com.px.base.service;

import java.util.List;
import com.px.base.domain.BaseCustomer;

/**
 * 客户Service接口
 * 
 * @author 品讯科技
 * @date 2024-08
 */
public interface IBaseCustomerService 
{
    /**
     * 查询客户
     * 
     * @param customerId 客户主键
     * @return 客户
     */
     BaseCustomer selectBaseCustomerByCustomerId(String customerId);

    /**
     * 查询客户列表
     * 
     * @param baseCustomer 客户
     * @return 客户集合
     */
     List<BaseCustomer> selectBaseCustomerList(BaseCustomer baseCustomer);

    /**
     * 新增客户
     * 
     * @param baseCustomer 客户
     * @return 结果
     */
     int insertBaseCustomer(BaseCustomer baseCustomer);

    /**
     * 修改客户
     * 
     * @param baseCustomer 客户
     * @return 结果
     */
     int updateBaseCustomer(BaseCustomer baseCustomer);

    /**
     * 批量删除客户
     * 
     * @param customerIds 需要删除的客户主键集合
     * @return 结果
     */
     int deleteBaseCustomerByCustomerIds(String[] customerIds);

    /**
     * 删除客户信息
     * 
     * @param customerId 客户主键
     * @return 结果
     */
     int deleteBaseCustomerByCustomerId(String customerId);
}
