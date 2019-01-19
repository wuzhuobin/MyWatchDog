package wuzhuobin.camera;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.videoio.VideoWriter;

public class CameraWriter extends VideoWriter implements Runnable{

	public CameraWriter(final String fileName, double fps, Mat oneFrame) {
		// TODO Auto-generated constructor stub
		super(fileName, VideoWriter.fourcc('M', 'J', 'P', 'G'), fps, oneFrame.size());
		this.oneFrame = oneFrame;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.write(this.oneFrame);
	}
	
	private Mat oneFrame;
	
};