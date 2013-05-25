package edu.bu.cs.cs460.photoshare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A data access object (DAO) to handle picture objects
 *
 * Original author G. Zervas <cs460tf@bu.edu>
 * Modified by Yvette Tsai (ytsai@bu.edu)
 * 
 * int getPicId(Picture picture)- given a pic obj return the pic id
 * 
 * Picture load(int id)- given a pic id obtain all other info in the table
 * 
 * void save(Picture picture)- insert a new data into the table given pic obj
 * 
 * List<Integer> allPicturesIds()- return a list of pid inside the table
 * 
 * boolean deletePid(int picture_id)- delete data with given pic id
 * 
 */
public class PictureDao {
  private static final String LOAD_PICTURE_STMT = "SELECT " +
      "\"caption\", \"imgdata\", \"thumbdata\", \"size\", \"content_type\" FROM \"Pictures\" WHERE \"picture_id\" = ?";

  private static final String SAVE_PICTURE_STMT = "INSERT INTO " +
      "\"Pictures\" (\"caption\", \"imgdata\", \"thumbdata\", \"size\", \"content_type\") VALUES (?, ?, ?, ?, ?)";

  private static final String ALL_PICTURE_IDS_STMT = "SELECT \"picture_id\" FROM \"Pictures\"" +
    " ORDER BY \"picture_id\" DESC";

  private static final String GET_PICTURE_ID_STMT = "SELECT \"picture_id\"" +
      "FROM \"Pictures\" WHERE \"caption\" = ? AND \"imgdata\" = ? AND \"thumbdata\" = ? AND \"size\" = ? AND \"content_type\" = ?";
  
  private static final String DELETE_PID_STMT = "DELETE FROM \"Pictures\" WHERE " +
    "picture_id = ?";
  
  public int getPicId(Picture picture) {
  PreparedStatement stmt = null;
  Connection conn = null;
  ResultSet rs = null;
  try {
   conn = DbConnection.getConnection();
   stmt = conn.prepareStatement(GET_PICTURE_ID_STMT);
    stmt.setString(1, picture.getCaption());
   stmt.setBytes(2, picture.getData());
   stmt.setBytes(3, picture.getThumbdata());
   stmt.setLong(4, picture.getSize());
   stmt.setString(5, picture.getContentType());
   rs = stmt.executeQuery();
   
   if(!rs.next())
   {
     return -1;
   }
   
   return rs.getInt(1);
   
  } catch (SQLException e) {
   e.printStackTrace();
   throw new RuntimeException(e);
  } finally {
   if (stmt != null) {
    try { stmt.close(); } catch (SQLException e) { ; }
    stmt = null;
   }
   if (conn != null) {
    try { conn.close(); } catch (SQLException e) { ; }
    conn = null;
   }
  }
 }
  
  public Picture load(int id) {
  PreparedStatement stmt = null;
  Connection conn = null;
  ResultSet rs = null;
  Picture picture = null;
    try {
   conn = DbConnection.getConnection();
   stmt = conn.prepareStatement(LOAD_PICTURE_STMT);
      stmt.setInt(1, id);
   rs = stmt.executeQuery();
      if (rs.next()) {
        picture = new Picture();
        picture.setId(id);
        picture.setCaption(rs.getString(1));
        picture.setData(rs.getBytes(2));
        picture.setThumbdata(rs.getBytes(3));
        picture.setSize(rs.getLong(4));
        picture.setContentType(rs.getString(5));
      }

   rs.close();
   rs = null;
  
   stmt.close();
   stmt = null;
   
   conn.close();
   conn = null;
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
  } finally {
   if (rs != null) {
    try { rs.close(); } catch (SQLException e) { ; }
    rs = null;
   }
   if (stmt != null) {
    try { stmt.close(); } catch (SQLException e) { ; }
    stmt = null;
   }
   if (conn != null) {
    try { conn.close(); } catch (SQLException e) { ; }
    conn = null;
   }
  }

  return picture;
 }

 public void save(Picture picture) {
  PreparedStatement stmt = null;
  Connection conn = null;
  try {
   conn = DbConnection.getConnection();
   stmt = conn.prepareStatement(SAVE_PICTURE_STMT);
   stmt.setString(1, picture.getCaption());
   stmt.setBytes(2, picture.getData());
   stmt.setBytes(3, picture.getThumbdata());
   stmt.setLong(4, picture.getSize());
   stmt.setString(5, picture.getContentType());
   stmt.executeUpdate();
   
   stmt.close();
   stmt = null;
   
   conn.close();
   conn = null;
  } catch (SQLException e) {
   e.printStackTrace();
   throw new RuntimeException(e);
  } finally {
   if (stmt != null) {
    try { stmt.close(); } catch (SQLException e) { ; }
    stmt = null;
   }
   if (conn != null) {
    try { conn.close(); } catch (SQLException e) { ; }
    conn = null;
   }
  }
 }

 public List<Integer> allPicturesIds() {
  PreparedStatement stmt = null;
  Connection conn = null;
  ResultSet rs = null;
  
  List<Integer> picturesIds = new ArrayList<Integer>();
  try {
   conn = DbConnection.getConnection();
   stmt = conn.prepareStatement(ALL_PICTURE_IDS_STMT);
   rs = stmt.executeQuery();
   while (rs.next()) {
    picturesIds.add(rs.getInt(1));
   }

   rs.close();
   rs = null;

   stmt.close();
   stmt = null;

   conn.close();
   conn = null;
  } catch (SQLException e) {
   e.printStackTrace();
   throw new RuntimeException(e);
  } finally {
   if (rs != null) {
    try { rs.close(); } catch (SQLException e) { ; }
    rs = null;
   }
   if (stmt != null) {
    try { stmt.close(); } catch (SQLException e) { ; }
    stmt = null;
   }
   if (conn != null) {
    try { conn.close(); } catch (SQLException e) { ; }
    conn = null;
   }
  }

  return picturesIds;
 }
 
 public boolean deletePid(int picture_id) {
    PreparedStatement stmt = null;
    Connection conn = null;
    ResultSet rs = null;
    try {
      conn = DbConnection.getConnection();
      
      stmt = conn.prepareStatement(DELETE_PID_STMT);
      stmt.setInt(1, picture_id);
      stmt.executeUpdate();
      
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    } finally {
      if (rs != null) {
        try { rs.close(); }
        catch (SQLException e) { ; }
        rs = null;
      }
      
      if (stmt != null) {
        try { stmt.close(); }
        catch (SQLException e) { ; }
        stmt = null;
      }
      
      if (conn != null) {
        try { conn.close(); }
        catch (SQLException e) { ; }
        conn = null;
      }
    }
  }
}
