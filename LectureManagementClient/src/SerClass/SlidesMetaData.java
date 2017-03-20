/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SerClass;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class SlidesMetaData implements Serializable {
    
 private static final long serialVersionUID = -5399605122499999999L;
 
    private File SlidesPath;
    private int numberOfSlides;
    private ArrayList<File> ImageList;

    public int getNumberOfSlides() {
        return numberOfSlides;
    }

    public void setNumberOfSlides(int numberOfSlides) {
        this.numberOfSlides = numberOfSlides;
    }

    public ArrayList<File> getImageList() {
        return ImageList;
    }

    public void setImageList(ArrayList<File> ImageList) {
        this.ImageList = ImageList;
    }

    public File getSlidesPath() {
        return SlidesPath;
    }

    public void setSlidesPath(File SlidesPath) {
        this.SlidesPath = SlidesPath;
    }

 

}
