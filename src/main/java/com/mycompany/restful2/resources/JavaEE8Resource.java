package com.mycompany.restful2.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 *
 * @author 
 */
@Path("javaee8")
public class JavaEE8Resource {
    
    @XmlRootElement(name= "persona")
    @XmlType(propOrder = {"id","fullName","age","salario"})
    public static class Person {

        public int id;
        public String fullName;
        public int age;
        public int salario;

        @XmlElement
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @XmlElement
        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        @XmlElement
        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @XmlElement
        public int getSalario() {
            return salario;
        }

        public void setSalario(int salario) {
            this.salario = salario;
        }
    }
    
    
    public static class Service {
        private static Map<Integer, Person> persons = new HashMap<>();
        private static int suma=0, promedio=0;

        static{
            for (int i = 0; i < 10; i++) {
                Person person = new Person();
                int id = i + 1;
                person.setId(id);
                person.setFullName("My wonderful Person " + id);
                person.setAge(new Random().nextInt(40) + 1);
                int edad = person.getAge();
                person.setSalario(1000 * edad / 3);
                suma += person.salario;
                persons.put(id, person);
                promedio = suma/persons.size();
            }
        }
    }

    @GET
    public Response ping(){
        return Response
                .ok("ping")
                .build();
    }
    
    @GET
    @Path("testy/{name}")
    public String ping(@PathParam("name") String name) {
        return "Hello " + name; 
    }
    
    @GET
    @Path("personsJSON")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPersonsJson() {
        List<Person> allPersons = new ArrayList<>(Service.persons.values());
        return Response.ok(allPersons).build();
    }
    
    @GET
    @Path("personsXML")
    @Produces(MediaType.APPLICATION_XML)
    public List<Person> getAllPersonsXml() {
        return new ArrayList <Person>(Service.persons.values());
    }
    
    @GET
    @Path("getPersonByIDJSON/{id}")
    @Produces (MediaType.APPLICATION_JSON)   
    public Person getPersonByIDJson(@PathParam ("id")int id){
        return Service.persons.get(id);
    }
    
    @GET
    @Path("getPersonByIDXML/{id}")
    @Produces (MediaType.APPLICATION_XML)   
    public Person getPersonByIDXml(@PathParam ("id")int id){
        return Service.persons.get(id);
    }
    
    @GET
    @Path("sumaSalariosJSON")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSumaSalarioJson() {
        return Response.ok("Suma de Salarios:" + Service.suma).build();
    }
    
    @GET
    @Path("promedioSalariosJSON")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPromedioSalarioJson() {
        return Response.ok("Promedio de Salarios:" + Service.promedio).build();
    }

    @POST
    @Path("addPerson")
    @Produces (MediaType.APPLICATION_JSON) 
    @Consumes (MediaType.APPLICATION_JSON)
    public Response addPersonJson() {
        int id = Service.persons.size() + 1;
        Person newPerson = new Person();
        newPerson.setId(id);
        newPerson.setFullName("My wonderful Person " + id);
        newPerson.setAge(new Random().nextInt(40) + 1);
        int edad = newPerson.getAge();
        newPerson.setSalario(1000 * edad / 3);
        
        Service.persons.put(id, newPerson);

        Service.suma += newPerson.getSalario();
        Service.promedio = Service.suma / Service.persons.size();

        List<Person> allPersons = new ArrayList<>(Service.persons.values());
        return Response.ok(allPersons).build();
    }
    
}
