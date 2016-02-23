/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jomr5bphotos;

/**
 *
 * @author Jared
 */
public class PictureDataHolder
{
    private final String _filename;
    private final String _title;
    private final String _description;
    private final String _longitude;
    private final String _latitude;
    private final String _dateTime;
    
    public String getFileName()
    {
        return _filename;
    }
    
    public String getTitle()
    {
        return _title;
    }
    
    public String getDescription()
    {
        return _description;
    }
    
    public String getLongitude()
    {
        return _longitude;
    }
    
    public String getLatitude()
    {
        return _latitude;
    }
    
    public String getDateTime()
    {
        return _dateTime;
    }
    
    public PictureDataHolder(String date, String description, String filename, String latitude, String longitude, String title)
    {
        _dateTime = date;
        _description = description;
        _filename = filename;
        _latitude = latitude;
        _longitude = longitude;
        _title = title;
    }
}