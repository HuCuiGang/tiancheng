package com.yufan;

import com.yufan.service.EmployeeService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.yufan.bean.Employee;
import com.yufan.repository.EmployeeRepository;

import java.util.Arrays;
import java.util.List;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class TestEmployeeRepository {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void test1(){
        Employee employee=new Employee();
        employee.setName("xiaowang");
        employee.setAge(10);
        employeeRepository.save(employee);
    }


    @Test
    public void test2(){

        Employee employee = employeeRepository.findEmployeeByName("xiaowang");

        System.out.println(employee);
    }
    @Test
    public void test2_1(){

        List<Employee>  list = employeeRepository.findListEmployeeByName("xiaowang");
        for (Employee e: list) {
            System.out.println(e);
        }
    }

    @Test
    public void test3(){

        List<Employee> list = employeeRepository.findByNameStartingWithAndAgeLessThan("李", 25);

        System.out.println(list);
    }

    @Test
    public void test4(){

        List<Employee> list = employeeRepository.findByNameEndingWithAndAgeLessThan("五", 35);
        System.out.println(list);
    }

    //id 2,3  or age >32
    @Test
    public void test5(){

        List<Employee> list = employeeRepository.findEmployeeByIdInOrAgeGreaterThan(Arrays.asList(2, 3), 32);

        System.out.println(list);
    }

    //name xiaowang  and age >5
    @Test
    public void test6(){

        List<Employee> list = employeeRepository.findEmployeeByNameInAndAgeGreaterThan(Arrays.asList("xiaowang"),5);

        System.out.println(list);
    }

    @Test
    public void test7(){

        Employee em = employeeRepository.getEmployeeByMaxId();
        System.out.println(em);
    }

    @Test
    public void test8(){

        List<Employee> list = employeeRepository.queryParam1("xiaowang", 10);

        System.out.println(list);

    }

    @Test
    public void test9(){

        List<Employee> list = employeeRepository.queryParam2("xiaowang", 10);

        System.out.println(list);

    }

    @Test
    public void test10(){

        List<Employee> list = employeeRepository.queryLike2("李");

        System.out.println(list);

    }

    @Test
    public void test11(){
        long count = employeeRepository.getCount();
        System.out.println(count);
    }

    @Test
    public void test12(){
        employeeService.update(80,3);
    }
}
