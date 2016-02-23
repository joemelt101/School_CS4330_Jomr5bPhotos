/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jomr5bphotos;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author Jared
 */
public class Controller implements Initializable
{
    @FXML
    public Label statusText;
    
    @FXML
    public Label imageFileName;
    
    @FXML
    public Label imageTitle;
    
    @FXML
    public Label description;
    
    @FXML
    public Label latitude;
    
    @FXML
    public Label longitude;
    
    @FXML
    public Label date;
    
    private ObservableList<PictureDataHolder> _pictureDataList = null;
    
    private int _index = 0;
    
    @FXML
    public void rightClicked(ActionEvent event)
    {
        if (this._pictureDataList == null)
            return;
        
        _index = (_index + 1 == this._pictureDataList.size()) ? 0 : _index + 1;
        
        this.loadPicture(_index);
    }
    
    @FXML
    public void leftClicked(ActionEvent event)
    {
        if (this._pictureDataList == null)
            return;
        
        _index = (_index == 0) ? this._pictureDataList.size() - 1 : _index - 1;
        this.loadPicture(_index);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        this.statusText.setText("Loading JSON...");
        
        //Setup model
        Model model = new Model();
        
        try 
        {
            model.LoadPictureJSON();
        } catch (Exception ex) 
        {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        _pictureDataList = model.getPictureData();
        
        loadPicture(0); //load the first picture
        
        this.statusText.setText("Load Complete! Photo Path: " + model.getPhotoPath());
    }    
    
    private void loadPicture(int index)
    {
        if (this._pictureDataList != null)
        {
            PictureDataHolder pdh = _pictureDataList.get(index);
            
            this.date.setText(pdh.getDateTime());
            this.description.setText(pdh.getDescription());
            this.imageFileName.setText(pdh.getFileName());
            this.imageTitle.setText(pdh.getTitle());
            this.latitude.setText(pdh.getLatitude());
            this.longitude.setText(pdh.getLongitude());
        }
    }
}
