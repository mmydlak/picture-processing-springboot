package com.example.imageprocessing.controller;
import com.example.imageprocessing.exception.PictureNotFoundException;
import com.example.imageprocessing.utils.CallCounter;
import com.example.imageprocessing.utils.PictureUtils;
import com.example.imageprocessing.dto.PictureSize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

@RestController
public class JsonRestController {

    @Autowired
    private PictureUtils pictureUtils;

    @Autowired
    private CallCounter callCounter;


    @RequestMapping(value = "/image/{id}/crop", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getSubimage(@PathVariable("id") String id,
                              @RequestParam(value = "x") int x,
                              @RequestParam(value = "y") int y,
                              @RequestParam(value = "width") int width,
                              @RequestParam(value = "height") int height) throws PictureNotFoundException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(pictureUtils.getSubimage(id, x, y, width, height), "jpg", baos);
        baos.flush();
        byte[] imageInBytes = baos.toByteArray();
        baos.close();
        return imageInBytes;
    }

    @RequestMapping(value = "/image", method = RequestMethod.POST)
    public String addImage(HttpServletRequest requestEntity) throws IOException {
        int id = callCounter.getCounter();
        pictureUtils.addPicture(""+id, ImageIO.read(requestEntity.getInputStream()));
        return "Image was added successfully with id: " + id + ".";
    }

    @RequestMapping(value = "/image/{id}", method = RequestMethod.DELETE)
    public String deletePicture(@PathVariable("id") String id) throws PictureNotFoundException {
        pictureUtils.removePicture(id);
        return "Image with id " + id + " was deleted successfully.";
    }

    @RequestMapping(value = "/image/{id}/size", method = RequestMethod.GET)
    public PictureSize getPictureSize(@PathVariable("id") String id) throws PictureNotFoundException{
        return pictureUtils.getPictureSize(id);
    }

    @RequestMapping(value = "/image/{id}/histogram", method = RequestMethod.GET)
    public Map<Integer, Integer> getPictureHistogram(@PathVariable("id") String id) throws PictureNotFoundException{
        return pictureUtils.getNewPictureHistogram(id);
    }

    @ExceptionHandler
    void handlePictureNotFoundException(PictureNotFoundException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler
    void handleIOException(IOException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler
    void handleException(Exception e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }


}
