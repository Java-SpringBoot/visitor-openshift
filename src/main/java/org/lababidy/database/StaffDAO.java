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
public class StaffDAO {

    public List<Staff> findAll() {
        List<Staff> list = new ArrayList<Staff>();
        Connection c = null;
        String sql = "SELECT * FROM staff ORDER BY sname";
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
    public static List<String> listNames() {
        List<String> list = new ArrayList<>();
        //Staff staff = new Staff();
        Connection c = null;
        String sql = "SELECT sname FROM staff ORDER BY sname";
        try {
            c = ConnectionHelper.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                list.add(rs.getString("sname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.close(c);
        }
        return list;
    }
    
    public List<Staff> findByName(String name) {
        List<Staff> list = new ArrayList<Staff>();
        Connection c = null;
        String sql = "SELECT * FROM staff as e "
                + "WHERE UPPER(sname) LIKE ? "
                + "ORDER BY sname";
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, "%" + name.toUpperCase() + "%");
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

    public static Boolean login(String name, String password) {
        //List<Staff> list = new ArrayList<Staff>();
        boolean bool = false;
        Connection c = null;
        String sql = "SELECT sname,spassword FROM staff "
                + "WHERE sname = ? and spassword = ?";

        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                bool = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.close(c);
        }
        return bool;
    }

    public Staff findById(int id) {
        String sql = "SELECT * FROM staff WHERE sid = ?";
        Staff staff = null;
        Connection c = null;
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                staff = processRow(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
        return staff;
    }

    public Staff save(Staff staff)	{
        return staff.getSid() > 0 ? update(staff) : create(staff);
	}    
    
    public Staff create(Staff staff) {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = ConnectionHelper.getConnection();
            ps = c.prepareStatement("INSERT INTO staff (sname, spassword) VALUES (?, ?)",
                    new String[] { "ID" });
            ps.setString(1, staff.getSname());
            ps.setString(2, staff.getSpassword());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            // Update the id in the returned object. This is important as this value must be returned to the client.
            int id = rs.getInt(1);
            staff.setSid(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
        return staff;
    }

    public Staff update(Staff staff) {
        Connection c = null;
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement("UPDATE staff SET sname=?, spassword=? WHERE sid=?");
            ps.setString(1, staff.getSname());
            ps.setString(2, staff.getSpassword());
            ps.setInt(8, staff.getSid());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
        return staff;
    }

    public boolean remove(int id) {
        Connection c = null;
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement("DELETE FROM staff WHERE sid=?");
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

    protected Staff processRow(ResultSet rs) throws SQLException {
        Staff staff = new Staff();
        staff.setSid(rs.getInt("sid"));
        staff.setSname(rs.getString("sname"));
        staff.setSpassword(rs.getString("spassword"));
        return staff;
    }
    
}
