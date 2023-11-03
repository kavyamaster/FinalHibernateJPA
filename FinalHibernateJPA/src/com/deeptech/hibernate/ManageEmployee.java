package com.deeptech.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class ManageEmployee {
	private static SessionFactory sf;
	public static void main(String[] args) {
		try {
			sf=new Configuration().configure().addAnnotatedClass(Employee.class).buildSessionFactory();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		//Creating objects
		ManageEmployee me= new  ManageEmployee();
		Integer  emp=me.addEmployee("Nimalan","S","Developer",25000);
		Integer  emp1=me.addEmployee("Nimal","P","Adminr",25000);
		Integer  emp2=me.addEmployee("Nimala","S","Backend Developer",25000);
		me.updateEmployee(emp1,45000);
		me.listEmployee();
		me.deleteEmployee(emp2);
		
		
	}
	public void deleteEmployee(Integer e1) {
		Session s= sf.openSession();
		Transaction tx=null;
		try {
			tx=s.beginTransaction();
			Employee empdata=s.get(Employee.class, e1);
			
			s.delete(empdata);
			tx.commit();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public void updateEmployee(Integer e1,double sal) {
		Session s= sf.openSession();
		Transaction tx=null;
		try {
			tx=s.beginTransaction();
			Employee emp=s.get(Employee.class, e1);
			emp.setSalary(sal);
			s.update(emp);
			tx.commit();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void listEmployee() {
		Session s= sf.openSession();
		Transaction tx=null;
		try {
			tx=s.beginTransaction();
			Query q=s.createQuery("from Employee");
			List<Employee> e=q.list();
			for(Employee emp:e) {
				System.out.println(emp.getFirstname()+"\t"+emp.getLastname()+"\t"+emp.getDesignation()+"\t"+emp.getSalary());
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public Integer addEmployee(String fname,String lname,String des,double sal)
	{
		Session s= sf.openSession();
		Transaction tx=null;
		Integer employeeID=null;
		try {
			tx=s.beginTransaction();
			Employee emp= new Employee();
			emp.setFirstname(fname);
			emp.setLastname(lname);
			emp.setDesignation(des);
			emp.setSalary(sal);
			employeeID=(Integer)s.save(emp);
			System.out.println("Employee Record is saved");
			tx.commit();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally
		{
			s.close();
			//sf.close();
		}
		return employeeID;
	}
}
