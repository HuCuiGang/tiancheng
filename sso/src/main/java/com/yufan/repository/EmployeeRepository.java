package com.yufan.repository;

import com.yufan.bean.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//@RepositoryDefinition(domainClass = Employee.class,idClass = Integer.class)
public interface EmployeeRepository extends JpaRepository<Employee,Integer>  //
 {


    public Employee findEmployeeByName(String name);

    public List<Employee> findListEmployeeByName(String name);


    //where name like ?% and age<?
     public List<Employee> findByNameStartingWithAndAgeLessThan(String name,Integer age);

    //where name like %? and age<?
     public List<Employee> findByNameEndingWithAndAgeLessThan(String name,Integer age);

     //where id in(?,?,?,?,?) or age>?
     public List<Employee> findEmployeeByIdInOrAgeGreaterThan(List<Integer> ids,Integer age);

     //where name in(?,?,?,?,?) and age>?
     public List<Employee> findEmployeeByNameInAndAgeGreaterThan(List<String> names,Integer age);


     //Query注解
     @Query("select o from Employee o where id=(select max(id) from Employee t2)")
     public Employee  getEmployeeByMaxId();

     @Query("select o from Employee o where o.name=?1 and o.age=?2")
     public List<Employee> queryParam1( String name,Integer age);


     @Query("select o from Employee o where o.name=:name and o.age=:age")
     public List<Employee> queryParam2( @Param("name") String name, @Param("age") Integer age);


     @Query("select o from Employee o where o.name like  %?1%")
     public List<Employee> queryLike1(String name);


   @Query("select o from Employee o where o.name like  %:name%")
    public List<Employee> queryLike2(@Param("name") String name);

   @Query(nativeQuery = true,value = "select count(*) from employee")
   public long getCount();

   @Modifying
   @Query("update Employee o set o.age=:age where o.id=:id")
   public void update(@Param("age") Integer age,@Param("id") Integer id);


}
