package wuzhuobin.camera;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import org.opencv.core.Mat;

public class CameraWindow {
	
	public static BufferedImage matToBufferedImage(Mat mat) {
		// init
		BufferedImage bufferedImage = null;
		int width = mat.width(), height = mat.height(), channels = mat.channels();
		byte[] sourcePixels = new byte[width * height * channels];
		mat.get(0, 0, sourcePixels);
		
		if (mat.channels() > 1)
		{
			bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		}
		else
		{
			bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		}
		final byte[] targetPixels = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
		System.arraycopy(sourcePixels, 0, targetPixels, 0, sourcePixels.length);
		
		return bufferedImage;
	}
};