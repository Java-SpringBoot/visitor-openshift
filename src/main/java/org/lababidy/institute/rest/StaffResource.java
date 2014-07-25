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
import org.lababidy.database.Staff;
import org.lababidy.database.StaffDAO;


@Path("/staff")
public class StaffResource {

    StaffDAO dao = new StaffDAO();
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<Staff> findAll() {
		System.out.println("findAll");
		return dao.findAll();
	}

	@GET @Path("search/{query}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<Staff> findByName(@PathParam("query") String query) {
		System.out.println("findByName: " + query);
		return dao.findByName(query);
	}

	@GET @Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Staff findById(@PathParam("id") String id) {
		System.out.println("findById " + id);
		return dao.findById(Integer.parseInt(id));
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Staff create(Staff staff) {
        System.out.println("creating staff");
        return dao.create(staff);
	}

	@PUT @Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Staff update(Staff staff) {
        System.out.println("Updating staff: " + staff.getSname());
        dao.update(staff);
        return staff;
	}
	
	@DELETE @Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void remove(@PathParam("id") int id) {
		dao.remove(id);
	}

}
