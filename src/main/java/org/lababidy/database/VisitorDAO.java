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
public class VisitorDAO {

    public List<Visitor> findAll() {
        List<Visitor> list = new ArrayList<Visitor>();
        Connection c = null;
        String sql = "SELECT vid,vcid,vfname,vlname,vmobile,vtelephone,vemail,vnotes FROM visitor ORDER BY vid";
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
    
    public static List<String> listVisitors() {
        List<String> list = new ArrayList<String>();
        Connection c = null;
        String sql = "SELECT vid,vcid,vfname,vlname,vmobile,vtelephone,vemail,vnotes FROM visitor ORDER BY vfname";
        try {
            c = ConnectionHelper.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {

                list.add(rs.getString("vfname") + " " + rs.getString("vlname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.close(c);
        }
        return list;
    }

    public List<Visitor> listVisitorsfl() {
        List<Visitor> list = new ArrayList<Visitor>();
        Connection c = null;
        String sql = "SELECT vfname ,vlname FROM visitor ORDER BY vfname";
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

    
    public List<Visitor> findByName(String vfname) {
        List<Visitor> list = new ArrayList<Visitor>();
        Connection c = null;
        String sql = "SELECT * FROM visitor as e "
                + "WHERE UPPER(vfname) LIKE ? "
                + "ORDER BY vfname";
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, "%" + vfname.toUpperCase() + "%");
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
    
    public Visitor findById(int id) {
        String sql = "SELECT * FROM visitor WHERE vid = ?";
        Visitor visitor = null;
        Connection c = null;
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                visitor = processRow(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
        return visitor;
    }

    public Visitor save(Visitor visitor) {
        return visitor.getVid() > 0 ? update(visitor) : create(visitor);
	}    
    
    public Visitor create(Visitor visitor) {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = ConnectionHelper.getConnection();
            ps = c.prepareStatement("INSERT INTO visitor (vcid,vfname, vlname, vmobile, vtelephone, vemail, vnotes) VALUES (?, ?, ?, ?, ?, ?, ?)",
                    new String[] { "ID" });
            //ps.setInt(1, visitor.getVid());
            ps.setInt(1, visitor.getVcid());
            ps.setString(2, visitor.getVFname());
            ps.setString(3, visitor.getVLname());
            ps.setString(4, visitor.getVMobile());
            ps.setString(5, visitor.getVTelephone());
            ps.setString(6, visitor.getVEmail());
            ps.setString(7, visitor.getNnotes());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            // Update the id in the returned object. This is important as this value must be returned to the client.
            int id = rs.getInt(1);
            visitor.setVid(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
        return visitor;
    }

    public Visitor update(Visitor visitor) {
        Connection c = null;
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement("UPDATE visitor SET vcid=?,vfname=?, vlname=?, vmobile=?, vtelephone=?, vemail=?, vnotes=? WHERE vid=?");
            ps.setInt(8, visitor.getVid());
            ps.setInt(1, visitor.getVcid());
            ps.setString(2, visitor.getVFname());
            ps.setString(3, visitor.getVLname());
            ps.setString(4, visitor.getVMobile());
            ps.setString(5, visitor.getVTelephone());
            ps.setString(6, visitor.getVEmail());
            ps.setString(7, visitor.getNnotes());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
        return visitor;
    }

    public boolean remove(int id) {
        Connection c = null;
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement("DELETE FROM visitor WHERE vid=?");
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

    protected Visitor processRow(ResultSet rs) throws SQLException {
        Visitor visitor = new Visitor();
        visitor.setVid(rs.getInt("vid"));
        visitor.setVcid(rs.getInt("vcid"));
        visitor.setVFname(rs.getString("vfname"));
        visitor.setVLname(rs.getString("vlname"));
        visitor.setVMobile(rs.getString("vmobile"));
        visitor.setVTelephone(rs.getString("vtelephone"));
        visitor.setVEmail(rs.getString("vemail"));
        visitor.setvNotes(rs.getString("vnotes"));
        return visitor;
    }
    
}
