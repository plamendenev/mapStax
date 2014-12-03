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
import java.util.Date;
import java.util.UUID;
import mapstax.stores.Map;
import mapstax.lib.Convertors;

/**
 *
 * @author plamendenev
 */
public class MapModel {

    Cluster cluster;

    public java.util.LinkedList<Map> getMapsForUser(String user) {
        java.util.LinkedList<Map> maps = new java.util.LinkedList<>();
        Session session = cluster.connect("mapStax");
        PreparedStatement ps = session.prepare("SELECT * FROM maps WHERE user =?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute(boundStatement.bind(user));

        for (Row row : rs) {
            Map aMap = new Map();
            java.util.UUID UUID = row.getUUID("mapid");
            String mapName = row.getString("mapname");
            String mapText = row.getString("mapinfo");
            Date mapDate = row.getDate("dateadded");
            aMap.setUUID(UUID);
            aMap.setMapName(mapName);
            aMap.SetMapText(mapText);
            aMap.setDate(mapDate);
            maps.add(aMap);
        }
        return maps;
    }

    public java.util.LinkedList<Map> getAllMaps() {
        java.util.LinkedList<Map> maps = new java.util.LinkedList<>();
        Session session = cluster.connect("mapStax");
        PreparedStatement ps = session.prepare("SELECT mapid, mapname, mapinfo FROM maps");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute(boundStatement.bind());

        for (Row row : rs) {
            Map aMap = new Map();
            java.util.UUID UUID = row.getUUID("mapid");
            String mapName = row.getString("mapname");
            String mapText = row.getString("mapinfo");
            aMap.setUUID(UUID);
            aMap.setMapName(mapName);
            aMap.SetMapText(mapText);
            maps.add(aMap);
        }

        return maps;
    }

    public void insertMap(String user, String mapName, String mapStuff) {

        Convertors convertor = new Convertors();
        java.util.UUID mapId = convertor.getTimeUUID();        
        Session session = cluster.connect("mapStax");
        Date dateAdded = new Date();

        PreparedStatement ps = session.prepare("INSERT INTO maps (user, mapid, mapname, mapinfo, dateadded) VALUES(?,?,?,?,?)");
        BoundStatement boundStatement = new BoundStatement(ps);
        boundStatement.bind(user, mapId, mapName, mapStuff, dateAdded);
        session.execute(boundStatement);

        session.close();
    }
    
    public void updateMap(UUID mapId, String mapStuff) {
               
        Session session = cluster.connect("mapStax");

        PreparedStatement ps = session.prepare("UPDATE maps SET mapinfo=? WHERE mapid=?");
        BoundStatement boundStatement = new BoundStatement(ps);
        boundStatement.bind(mapStuff, mapId);
        session.execute(boundStatement);

        session.close();
    }
    
    public void DeleteMap(UUID mapId)
    {
        Session session = cluster.connect("mapStax");
        PreparedStatement ps = session.prepare("DELETE FROM maps WHERE mapid=?");
        BoundStatement bndSt = new BoundStatement(ps);
        bndSt.bind(mapId);
        session.execute(bndSt);
        session.close();
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }
}
