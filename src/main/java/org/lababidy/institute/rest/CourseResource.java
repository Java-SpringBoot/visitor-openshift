package org.lababidy.institute.rest;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.lababidy.database.Course;
import org.lababidy.database.CourseDAO;


@Path("/course")
public class CourseResource {

    CourseDAO coursedao = new CourseDAO();
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<Course> findAll() {
		System.out.println("findAll");
            return coursedao.findAll();
	}

	@GET @Path("search/{query}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<Course> findByName(@PathParam("query") String query) {
		System.out.println("findByName: " + query);
            return coursedao.findByName(query);
	}

	@GET @Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Course findById(@PathParam("id") String id) {
		System.out.println("findById " + id);
            return coursedao.findById(Integer.parseInt(id));
	}
    /*
    	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Course create(Course course) {
        System.out.println("creating course");
        return coursedao.create(course);
	}

	@PUT @Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Course update(Course course) {
        System.out.println("Updating course: " + course.getcname());
        coursedao.update(course);
        return course;
	}
	
	@DELETE @Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void remove(@PathParam("id") int id) {
            coursedao.remove(id);
	}
*/
}
