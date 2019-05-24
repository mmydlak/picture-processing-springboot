package com.example.imageprocessing.utils;

import com.example.imageprocessing.exception.PictureNotFoundException;
import com.example.imageprocessing.dto.PictureSize;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.LinkedHashMap;
import java.util.Map;

public class PictureUtils {

    private Map<String, BufferedImage> pictures;

    public PictureUtils(LinkedHashMap<String, BufferedImage> pictures) {
        this.pictures = pictures;
    }

    public boolean isPictureInCollection(String id) {
        return pictures.containsKey(id);
    }

    public void removePicture(String id) throws PictureNotFoundException{
        if(!isPictureInCollection(id)){
            throw new PictureNotFoundException("There is no such picture in collection.");
        }
        pictures.remove(id);
    }

    public void addPicture(String id, BufferedImage bi){
        pictures.put(id, bi);
    }

    public PictureSize getPictureSize(String id) throws PictureNotFoundException {
        BufferedImage choosen = pictures.get(id);
        if(choosen == null){
            throw new PictureNotFoundException("There is no such picture in collection.");
        }
        return new PictureSize(choosen.getHeight(), choosen.getWidth());
    }

    public BufferedImage getSubimage(String id, int x, int y, int width, int height) throws PictureNotFoundException {
        BufferedImage choosen = pictures.get(id);
        if(choosen == null){
            throw new PictureNotFoundException("There is no such picture in collection.");
        }
        if(x+width>choosen.getWidth()) {
            width = choosen.getWidth() - x;
        }
        if(y+height>choosen.getHeight()) {
            height = choosen.getHeight() - y;
        }
        return choosen.getSubimage(x,y,width,height);
    }

    public int[] getPictureHistogram(String id) throws PictureNotFoundException {
        BufferedImage choosen = pictures.get(id);
        if(choosen == null){
            throw new PictureNotFoundException("There is no such picture in collection.");
        }
        choosen = convertToGrayscale(choosen);
        int[] histogram = new int[256];
        int[][] pixels = getPixels(choosen);

        for (int x = 0; x < pixels.length; x++){
            for (int y = 0; y < pixels[y].length; y++){
                histogram[pixels[x][y]]++;
            }
        }
        return histogram;
    }

    public Map<Integer, Integer> getNewPictureHistogram(String id) throws PictureNotFoundException {
        BufferedImage choosen = pictures.get(id);
        if(choosen == null){
            throw new PictureNotFoundException("There is no such picture in collection.");
        }
        choosen = convertToGrayscale(choosen);
        Map<Integer, Integer> histogram = new LinkedHashMap<>();
        for(int i=0; i<256; i++) {
            histogram.put(i, 0);
        }
        int[][] pixels = getPixels(choosen);

        for (int x = 0; x < pixels.length; x++){
            for (int y = 0; y < pixels[y].length; y++){
                histogram.put(pixels[x][y], histogram.get(pixels[x][y]) + 1);
            }
        }
        return histogram;
    }

    private int[][] getPixels(BufferedImage bi) {
        int width = bi.getWidth();
        int height = bi.getHeight();
        int[][] pixels = new int[width][height];
        DataBufferByte db = (DataBufferByte) bi.getRaster().getDataBuffer();
        byte[] pixelArray = db.getData();

        for (int x = 0; x < width; x++ ) {
            for (int y = 0; y < height; y++ ) {
                pixels[x][y] = pixelArray[x + y * width] &0xFF;
            }
        }
        return pixels;
    }

    private BufferedImage convertToGrayscale(BufferedImage bi) {
        BufferedImage result = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = result.getGraphics();
        g.drawImage(bi, 0, 0, null);
        g.dispose();
        return result;
    }


}
