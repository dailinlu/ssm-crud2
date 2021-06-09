package com.atguigu.crud.service;

import com.atguigu.crud.bean.Employee;
import com.atguigu.crud.bean.EmployeeExample;
import com.atguigu.crud.bean.EmployeeExample.Criteria;
import com.atguigu.crud.dao.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;
    public List<Employee> getAll() {
        return employeeMapper.selectByExampleWithDept(null);
    }
    //员工保存
    public void saveEmp(Employee employee) {
        employeeMapper.insertSelective(employee);
    }
    //校验用户名是否重复
    public boolean checkEmp(String empName) {
        EmployeeExample example = new EmployeeExample();
        Criteria criteria=example.createCriteria();
        criteria.andEmpNameEqualTo(empName);
        long count = employeeMapper.countByExample(example);
        return count==0;
    }
    //根据员工id查找员工数据
    public Employee getEmp(Integer id) {
        Employee employee = employeeMapper.selectByPrimaryKeyWithDept(id);
        return employee;
    }
    //更新员工数据
    public void updateEmp(Employee employee) {
        employeeMapper.updateByPrimaryKeySelective(employee);
    }
    //删除单个员工
    public void deleteEmp(Integer id) {
        employeeMapper.deleteByPrimaryKey(id);
    }

    public void deleteBatch(List<Integer> del_ids) {
        EmployeeExample example = new EmployeeExample();
        Criteria criteria=example.createCriteria();
        criteria.andEmpIdIn(del_ids);
        System.out.println(del_ids);
        employeeMapper.deleteByExample(example);
    }
}
