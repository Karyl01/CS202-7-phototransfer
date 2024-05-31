package com.example.controller;

import javax.imageio.ImageIO;

public class ImageIOChecker {
    public static void main(String[] args) {
        String[] formats = ImageIO.getWriterFormatNames();
        System.out.println("Supported formats:");
        for (String format : formats) {
            System.out.println(format);
        }
    }
}
