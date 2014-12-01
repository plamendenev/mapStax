/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mapstax.stores;

/**
 *
 * @author plamendenev
 */
public class Map {
    
    private String mapName;
    private String mapText;
    private java.util.UUID mapId = null;
    
    public void setMapName(String mapName)
    {
        this.mapName = mapName;
    }
    public String getMapName()
    {
        return mapName;
    }
    public void setUUID(java.util.UUID mapId){
        this.mapId = mapId;
    }
    public java.util.UUID getUUID(){
        return mapId;
    }
    
    public void SetMapText(String mapText)
    {
        this.mapText = mapText;
    }
    
    public String getMapText()
    {
        return mapText;
    }
}
