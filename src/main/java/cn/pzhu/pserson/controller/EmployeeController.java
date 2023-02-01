package cn.pzhu.pserson.controller;

import cn.pzhu.pserson.domain.Dept;
import cn.pzhu.pserson.domain.Employee;
import cn.pzhu.pserson.domain.Job;
import cn.pzhu.pserson.domain.response.EmployeeResDTO;
import cn.pzhu.pserson.service.EmployeeService;
import cn.pzhu.pserson.service.RainService;
import com.github.pagehelper.PageInfo;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class EmployeeController {

  @Autowired
  private EmployeeService employeeService;

  @Autowired
  @Qualifier("RainService")
  private RainService rainservice;

  // 如果在目录下输入为空，则跳转到指定链接
  @RequestMapping(value = "/employee/")
  public ModelAndView index2(ModelAndView mv) {
    mv.setViewName("employee/list");
    return mv;
  }

  // 如果在目录下输入任何不存在的参数，则跳转到list
  @RequestMapping(value = "/employee/{formName}")
  public String index2(@PathVariable String formName) {
    String blank = "/employee/list";
    return blank;
  }

  @RequestMapping(value = "/employee/list", method = RequestMethod.GET)
  public String index(Model model, String content,int pageNum,int pageSize) {
    PageInfo pageInfo = employeeService.getEmployee(content,pageNum, pageSize);
    model.addAttribute("list", pageInfo.getList());
    model.addAttribute("pageInfo", pageInfo);
    return "employee/list";
  }

  @RequestMapping(value = "/employee/add", method = RequestMethod.GET)
  public String add(Model model, Integer id) {
    if (id != null) {
//      Employee employee = rainservice.get_EmployeeInfo(id);
      Employee employee = employeeService.getEmployee(id);
      model.addAttribute("job", employee);
    }
    Map<String, Object> info = employeeService.getInfo();
    model.addAttribute("job_list", (List<Job>) info.get("jobs"));
    model.addAttribute("dept_list", (List<Dept>) info.get("depts"));
    return "/employee/add";
  }

  @RequestMapping(value = "/dept/maga", method = RequestMethod.GET)
  public String maga(Model model, Integer id) {
    PageInfo pageInfo = employeeService.getEmployee(null,0,7);
    List<EmployeeResDTO> list = (List<EmployeeResDTO>) pageInfo.getList();
    List<EmployeeResDTO> needList = new ArrayList<EmployeeResDTO>();
    for (EmployeeResDTO e : list) {
      Integer jobId = e.getDeptId();
      System.out.println("liiii : " + jobId);
      if (jobId == id) {
        needList.add(e);
      }
    }
    model.addAttribute("list", needList);
    return "/dept/maga";
  }

  @RequestMapping(value = "/dept/maga1", method = RequestMethod.GET)
  public String maga1(Model model, Integer id) {
    PageInfo pageInfo = employeeService.getEmployee(null,0,7);
    List<EmployeeResDTO> list = (List<EmployeeResDTO>) pageInfo.getList();
    List<EmployeeResDTO> needList = new ArrayList<EmployeeResDTO>();
    for (EmployeeResDTO e : list) {
      Integer myId = e.getId();
      System.out.println("li : " + myId);
      if (myId ==id) {
        needList.add(e);
      }
    }
    model.addAttribute("list", needList);

    // List<String> jobList=new ArrayList<String>();
    // for(EmployeeResDTO e : list){
    //   String jobName = e.getJob();
    //   jobList.add(jobName);
    //   System.out.println("job:"+jobName);
    // }

    Map<Integer, String> jobMap = new HashMap<Integer, String>();
    for(EmployeeResDTO e : list){
      Integer jobID = e.getJobId();
      String jobName = e.getJob();
      jobMap.put(jobID, jobName);
    }

    model.addAttribute("jobMap", jobMap);
    return "/dept/maga1";
  }
  
  @RequestMapping(value = "/employee/update", method = RequestMethod.POST)
  public ModelAndView update(ModelAndView mv, @ModelAttribute Employee employee, Integer jobId) {
    Employee emp = employeeService.getEmployee(employee.getId());
    emp.setJobId(jobId);
    employeeService.update(emp);
    // System.out.println("成功：" + jobId.toString());
//     if (employee.getId() != null) {
//       employeeService.update(employee);
// //      rainservice.insert_EmployeeInfo(job);
//     } else {
//       employeeService.insert(employee);
// //      rainservice.update_EmployeeInfo(job);
//     }
    // mv.setViewName("redirect:/employee/list?pageNum=1&pageSize=6");
    return mv;
  }

  @RequestMapping(value = "/employee/add", method = RequestMethod.POST)
  public ModelAndView add(ModelAndView mv, @ModelAttribute Employee job, Integer id) {
    if (job.getId() != null) {
      employeeService.update(job);
//      rainservice.insert_EmployeeInfo(job);
    } else {
      employeeService.insert(job);
//      rainservice.update_EmployeeInfo(job);
    }
    mv.setViewName("redirect:/employee/list?pageNum=1&pageSize=6");
    return mv;
  }

  @RequestMapping(value = "/employee/delete", method = RequestMethod.GET)
  public String delete(Integer id) {
    if (id != null) {
      rainservice.delete_EmployeeInfo(id);
    }
    return "employee/list";
  }
}
