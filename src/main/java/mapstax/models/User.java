/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapstax.models;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Set;
import java.util.UUID;
import mapstax.lib.AeSimpleSHA1;
import mapstax.stores.LoggedIn;

/**
 *
 * @author Administrator
 */
public class User {

    Cluster cluster;
    private String username, name, surname;
    private Set<String> email;
    private UUID profilePic;
    private User user;

    public User() {
       
    }

    public UUID getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(UUID profilePic, String User) {
        
        Session session = cluster.connect("mapStax");

        PreparedStatement ps = session.prepare("UPDATE userprofiles SET profilepic=? WHERE login =?");
        BoundStatement boundStatement = new BoundStatement(ps);
        boundStatement.bind(profilePic, User);
        session.execute(boundStatement);            
    }

    public Set<String> getEmail() {
        return email;
    }

    public void setEmail(Set<String> email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getUsername() {
        return username;
    }

    public boolean RegisterUser(String name, String surname, Set<String> email, String username, String Password) {
        AeSimpleSHA1 sha1handler = new AeSimpleSHA1();
        String EncodedPassword = null;
        try {
            EncodedPassword = sha1handler.SHA1(Password);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException et) {
            System.out.println("Can't check your password");
            return false;
        }
        Session session = cluster.connect("mapStax");
        PreparedStatement ps = session.prepare("insert into userprofiles (first_name, last_name, email, login, password) Values(?,?,?,?,?)");

        BoundStatement boundStatement = new BoundStatement(ps);
        session.execute(boundStatement.bind(name, surname, email, username, EncodedPassword));
        //We are assuming this always works.  Also a transaction would be good here !        
        return true;
    }
    
    public boolean UpdateUser(String name, String surname, Set<String> email, String Password, String username) {
        AeSimpleSHA1 sha1handler = new AeSimpleSHA1();
        String EncodedPassword = null;
        try {
            EncodedPassword = sha1handler.SHA1(Password);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException et) {
            System.out.println("Can't check your password");
            return false;
        }
        Session session = cluster.connect("mapStax");
        PreparedStatement ps = session.prepare("UPDATE userprofiles SET first_name=?, last_name=?, email=?, password=? WHERE login=?");

        BoundStatement boundStatement = new BoundStatement(ps);
        session.execute(boundStatement.bind(name, surname, email, EncodedPassword, username));
        return true;
    }

    public LoggedIn IsValidUser(String username, String Password) {
        AeSimpleSHA1 sha1handler = new AeSimpleSHA1();
        String EncodedPassword = null;
        try {
            EncodedPassword = sha1handler.SHA1(Password);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException et) {
            System.out.println("Can't check your password");
            return null;
        }
        Session session = cluster.connect("mapStax");
        PreparedStatement ps = session.prepare("select password from userprofiles where login =?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        boundStatement.bind(username);
        rs = session.execute(boundStatement);
        
        if (rs.isExhausted()) {
            System.out.println("No Images returned");
            return null;
        } else {
            for (Row row : rs) {
                String StoredPass = row.getString("password");
                if (StoredPass.compareTo(EncodedPassword) == 0) {
                    LoggedIn lg = new LoggedIn();
                    lg.setLogedin();        
                    user = new User();
                    user = getUserFromDb(username);
                    lg.setUser(user);

                    return lg;
                }
            }
        }

        return null;
    }

    public User getUserFromDb(String username) {
        Session session = cluster.connect("mapStax");
        PreparedStatement ps = session.prepare("select first_name, last_name, email, login, profilepic from userprofiles where login =?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        boundStatement.bind(username);
        rs = session.execute(boundStatement);
        Row aRow = rs.one();
        user.setUsername(aRow.getString("login"));
        user.setName(aRow.getString("first_name"));
        user.setSurname(aRow.getString("last_name"));
        user.setEmail(aRow.getSet("email", String.class));
        user.profilePic = aRow.getUUID("profilepic");

        return user;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

}
