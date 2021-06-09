package com.atguigu.crud.controller;


import com.atguigu.crud.bean.Employee;
import com.atguigu.crud.bean.Msg;
import com.atguigu.crud.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;
    //根据id删除员工
    @ResponseBody
    @RequestMapping(value = "/emp/{ids}",method = RequestMethod.DELETE)
    public Msg deleteEmp(@PathVariable("ids") String ids){
        //contains 检查字符串里是否 有给的字符 boolean   split方法 删除字符串中所有指定的 字符 得到一个新的字符串数组 应该是 指定字符分隔开的
        System.out.println(ids);
        if(ids.contains("-")){
            List<Integer> del_ids = new ArrayList<>();
            String[] str_ids = ids.split("-");
            //组安id的集合
            for(String string : str_ids){
                System.out.println(string);
                del_ids.add(Integer.parseInt(string));
            }
            System.out.println(del_ids);
            employeeService.deleteBatch(del_ids);
        }else {
            Integer id = Integer.parseInt(ids);
            employeeService.deleteEmp(id);

        }
        return Msg.success();
    }
    //更新员工信息
    @ResponseBody
    @RequestMapping(value = "/emp/{empId}",method = RequestMethod.PUT)
    public Msg updateEmp(Employee employee){
        employeeService.updateEmp(employee);
        return Msg.success();
    }
    //根据id查找员工信息
    @ResponseBody
    @RequestMapping(value = "/emp/{id}",method = RequestMethod.GET)
    public Msg getEmp(@PathVariable("id")Integer id){
        Employee employee=employeeService.getEmp(id);
        return Msg.success().add("emp",employee);
    }
    //校验是否员工名重复
    @RequestMapping("/checkemp")
    @ResponseBody
    public Msg checkEmp(@RequestParam("empName") String empName){
        String regx= "(^[a-zA-Z_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5}$)";
        if(!empName.matches(regx)){
            return Msg.fail().add("va_msg","用户名必须是6-16位数字和字母组合,或者是2-5位汉字");
        }
        boolean b = employeeService.checkEmp(empName);
        if (b){
            return Msg.success();
        }else {
            System.out.println("ajaxlail ");
            return Msg.fail().add("va_msg","用户名不可用");
        }
    }
    /**新增员工
     * @Valid:表示要emp对象需要校验
     * BindingResult result:校验失败的提示
     * */
    @RequestMapping(value = "/emp",method = RequestMethod.POST)
    @ResponseBody
    public Msg saveEmp(@Valid Employee employee, BindingResult result){
        if (result.hasErrors()){
            //校验失败,应该返回失败信息,在模态框中显示校验失败的错误信息
            Map<String,Object> map = new HashMap<>();
            //获取所有的错误信息
            List<FieldError> errors = new ArrayList<>();
            for (FieldError error:errors){
                System.out.println("错误的字段名"+error.getField());
                System.out.println("错误的信息"+error.getDefaultMessage());
                map.put(error.getField(),error.getDefaultMessage());
            }
            return Msg.fail().add("errorFields",map);
        }else {
            employeeService.saveEmp(employee);
            return Msg.success();
        }
    }
    //获得分页信息
    @RequestMapping("/emps")
    @ResponseBody
    public Msg getEmpsWhitJson(@RequestParam(value = "pn",defaultValue = "1") Integer pn,
                       Model model){
        //这不是一个分页
        //第几页 和 每一页有多少数据
        PageHelper.startPage(pn,5);
        List<Employee> emps = employeeService.getAll();
        //使用pageInfo包装查询后的结构,只需要将pageInfo交给页面就行了.
        //page中封装了详细的分页信息,包括有我们查询出来的数据,传入连续的页数.
        PageInfo page=new PageInfo(emps,5);
        return Msg.success().add("pageInfo",page);
    }

    //@RequestMapping("/emps")
    public String getEmps(@RequestParam(value = "pn",defaultValue = "1") Integer pn,
        Model model){
        //这不是一个分页
        //第几页 和 每一页有多少数据
        PageHelper.startPage(pn,5);
        List<Employee> emps = employeeService.getAll();
        //使用pageInfo包装查询后的结构,只需要将pageInfo交给页面就行了.
        //page中封装了详细的分页信息,包括有我们查询出来的数据,传入连续的页数.
        PageInfo page=new PageInfo(emps,5);
        model.addAttribute("pageInfo",page);
        return "list";
    }
}
