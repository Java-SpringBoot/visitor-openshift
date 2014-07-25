package org.lababidy.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohannad Lababidy (m.lababidy@gmail.com)
 */
public class CourseDAO {

    public  List<Course> findAll() {
        List<Course> list = new ArrayList<Course>();
        Connection c = null;
        String sql = "SELECT * FROM course ORDER BY cid";
        try {
            c = ConnectionHelper.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                list.add(processRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
        return list;
    }
    public static List<String> listCourses() {
        List<String> list = new ArrayList<String>();
        Connection c = null;
        String sql = "SELECT cname FROM course ORDER BY cname";
        try {
            c = ConnectionHelper.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                list.add(rs.getString("cname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.close(c);
        }
        return list;
    }
    
    public List<Course> findByName(String cname) {
        List<Course> list = new ArrayList<Course>();
        Connection c = null;
        String sql = "SELECT * FROM course as e "
                +"WHERE UPPER(name) LIKE ? "	
                + "ORDER BY cname";
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, "%" + cname + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(processRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
        return list;
    }
    
    public Course findById(int id) {
        String sql = "SELECT * FROM course WHERE cid = ?";
        Course course = null;
        Connection c = null;
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                course = processRow(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
        return course;
    }

    public Course save(Course course) {
        return course.getcid() > 0 ? update(course) : create(course);
	}    
    
    public Course create(Course course) {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = ConnectionHelper.getConnection();
            ps = c.prepareStatement("INSERT INTO course (cname,cnotes) VALUES (?, ?)",
                    new String[] { "ID" });
            ps.setString(1, course.getcname());
            ps.setString(2, course.getcnotes());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            // Update the id in the returned object. This is important as this value must be returned to the client.
            int id = rs.getInt(1);
            course.setcid(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
        return course;
    }

    public Course update(Course course) {
        Connection c = null;
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement("UPDATE course SET cname=?,cnotes=? WHERE cid=?");
            ps.setString(1, course.getcname());
            ps.setString(2, course.getcnotes());
            ps.setInt(3, course.getcid());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
        return course;
    }

    public boolean remove(int id) {
        Connection c = null;
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement("DELETE FROM course WHERE cid=?");
            ps.setInt(1, id);
            int count = ps.executeUpdate();
            return count == 1;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
    }

    protected Course processRow(ResultSet rs) throws SQLException {
        Course course = new Course();
        course.setcid(rs.getInt("cid"));
        course.setcname(rs.getString("cname"));
        course.setcnotes(rs.getString("cnotes"));
        return course;
    }
    
}
