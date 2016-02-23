/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jomr5bphotos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author Jared
 */
public class Model
{
    String _photoPath = "";
    ObservableList<PictureDataHolder> _pictureData = FXCollections.observableArrayList();
    
    public String getPhotoPath()
    {
        return _photoPath;
    }
    
    public ObservableList<PictureDataHolder> getPictureData()
    {
        return _pictureData;
    }
    
    public void LoadPictureJSON() throws MalformedURLException, IOException, Exception
    {
        String urlString = "http://dalemusser.net/data/nocaltrip/photos.json";
        
        //initiate url
        URL url;
        try
        {
            url = new URL(urlString);
        }
        catch (MalformedURLException e)
        {
            System.out.println("The URL was malformed...");
            throw e;
        }
        
        //retrieve JSON object
        String json = "";
        
        try(BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream())))
        {
            String line = in.readLine();
            
            while (line != null)
            {
                json += line;
                line = in.readLine();
            }
        }
        catch (IOException e)
        {
            System.out.println("Failed to load JSON document: " + e.getMessage());
            throw e;
        }
        
        parseJSON(json);
    }
    
    private void parseJSON(String json) throws Exception
    {
        JSONObject jsonObj = null;
        
        try
        {
            jsonObj = (JSONObject)JSONValue.parse(json);
        }
        catch (Exception e)
        {
            System.out.println("Failed to parse JSON document.");
            throw e;
        }
        
        //json parsed successfully...
        String status = jsonObj.getOrDefault("status", null).toString();
        
        if (status == null || status.equals("ok") == false)
        {
            System.out.println("Status was not 'OK'");
            throw new Exception();
        }
        
        //status OK, so start loading objects
        JSONArray pictures = null;
        
        try 
        {
            pictures = (JSONArray)jsonObj.get("photos");
        }
        catch (Exception e)
        {
            throw new Exception("Failed to load photos array.");
        }
        
        //load photo path
        try
        {
           _photoPath = (String)jsonObj.get("photosPath");        
        }
        catch (Exception e)
        {
            throw new Exception("Failed to load the photo path.");
        }
        
        System.out.println("Test: ");
        System.out.format("Status: %s\nPath: %s\n", status, _photoPath);
        
        //go through each picture and parse a new object
        for (Object picture : pictures)
        {            
            String date, description, filename, latitude, longitude, title;
            jsonObj = (JSONObject)picture;
            filename = (String)jsonObj.get("image");
            title = (String)jsonObj.get("title");
            description = (String)jsonObj.get("description");
            latitude = ((Double)jsonObj.get("latitude")).toString();
            longitude = ((Double)jsonObj.get("longitude")).toString();
            date = (String)jsonObj.get("date");
            
            System.out.println("==================");
            System.out.format(
                    "Title:  %s\n"
                    + "File Name: %s\n"
                    + "Description: %s\n"
                    + "Date: %s\n"
                    + "Latitude: %s\n"
                    + "Longitude: %s\n", title, filename, description, date, latitude, longitude);
            
            PictureDataHolder holder = new PictureDataHolder(date, description, filename, latitude, longitude, title);
            this._pictureData.add(holder);
        }
    }
}
