package com.lazada.transcodevideo.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.EncodingAttributes;
import ws.schild.jave.InputFormatException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.VideoAttributes;

@Service
public class TranscodeVideoServiceImpl implements TranscodeVideoService{

	@Value("${transcode.video.output.attribute.codec}")
	private String codec;
	
	@Value("${transcode.video.output.format}")
	private String videoFormat;
	
	/*
	 * encode the input data and returns the encoded data
	 */
	@Override
	public byte[] trancodeVideo(byte[] inputVideoData, String originalFileName) throws IOException, IllegalArgumentException, InputFormatException, EncoderException {
		
		File input=new File(originalFileName);
		FileOutputStream fos=new FileOutputStream(input);
		fos.write(inputVideoData);
		fos.flush();
		fos.close();
		
		MultimediaObject mmo=new MultimediaObject(input);
		
		File output=new File("transcoded_"+originalFileName);
		
		
		VideoAttributes videoAttributes=new VideoAttributes();
		videoAttributes.setCodec(codec);
		
		EncodingAttributes encodingAttributes=new EncodingAttributes();
		encodingAttributes.setVideoAttributes(videoAttributes);
		encodingAttributes.setFormat(videoFormat);
		
		Encoder encoder=new Encoder();
		encoder.encode(mmo, output, encodingAttributes);
		
		
		return Files.readAllBytes(output.toPath());
	}
	
}
