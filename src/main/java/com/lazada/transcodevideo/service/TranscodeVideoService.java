package com.lazada.transcodevideo.service;

import java.io.IOException;

import ws.schild.jave.EncoderException;
import ws.schild.jave.InputFormatException;

/*
 * Interface for the service to actually encode videos using ffmpeg codecs
 */
public interface TranscodeVideoService {
	public byte[] trancodeVideo(byte[] inputVideoData, String originalFileName) throws IOException, IllegalArgumentException, InputFormatException, EncoderException;
}
