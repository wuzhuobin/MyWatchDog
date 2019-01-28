package wuzhuobin.camera;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.videoio.VideoWriter;
import org.opencv.videoio.Videoio;

public class CameraRecorder extends VideoWriter {
	
	public CameraRecorder() {
		super();
		this.executorService = Executors.newSingleThreadScheduledExecutor();
		this.runnable = new CameraRecorderRunnable(this);
	}
	
	public boolean open(final String fileName, double fps) {
		this.fps = fps;
		CameraSingleton2 camera = CameraSingleton2.getInstance();
		if(!camera.isOpened()) {
			if(!camera.open()) {
				return false;
			}
		}
		double height = camera.get(Videoio.CAP_PROP_FRAME_HEIGHT);
		double width = camera.get(Videoio.CAP_PROP_FRAME_WIDTH);
		if(!super.open(fileName, CameraRecorder.fourcc('M', 'J', 'P', 'G'), this.fps, new Size(width, height), true)) {
			return false;
		}
		
		if(!this.executorService.isShutdown()) {
			this.executorService.shutdown();
			try {
				this.executorService.awaitTermination((long) Math.ceil(1000.0 / this.fps), TimeUnit.MILLISECONDS);
			}
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.executorService = Executors.newSingleThreadScheduledExecutor();
		this.executorService.scheduleAtFixedRate(this.runnable, 0, (long) (1000 / this.fps), TimeUnit.MILLISECONDS);
		return true;
	}
	
	// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
	@Override
	@Deprecated
	public boolean open(String filename, int apiPreference, int fourcc, double fps, Size frameSize) {
		// TODO Auto-generated method stub
		return this.open(filename, fps);
	}
	
	@Deprecated
	@Override
	public boolean open(String filename, int fourcc, double fps, Size frameSize) {
		// TODO Auto-generated method stub
		return open(filename, fps);
	}
	
	@Deprecated
	@Override
	public boolean open(String filename, int apiPreference, int fourcc, double fps, Size frameSize, boolean isColor) {
		// TODO Auto-generated method stub
		return open(filename, fps);
	}
	
	@Deprecated
	@Override
	public boolean open(String filename, int fourcc, double fps, Size frameSize, boolean isColor) {
		// TODO Auto-generated method stub
		return open(filename, fps);
	}
	// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
	
	@Override
	public void release() {
		if(!this.executorService.isShutdown()) {
			this.executorService.shutdown();
			try {
				this.executorService.awaitTermination((long) Math.ceil(1000.0 / this.fps), TimeUnit.MILLISECONDS);
			}
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		super.release();
	}

	private ScheduledExecutorService executorService;
	private CameraRecorderRunnable runnable;
	private double fps;
}

class CameraRecorderRunnable implements Runnable {
	
	public CameraRecorderRunnable(CameraRecorder recorder) {
		// TODO Auto-generated constructor stub
		this.cameraRecorder = recorder;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Mat oneFrame = new Mat();
		CameraSingleton2 camera = CameraSingleton2.getInstance();
		camera.read(oneFrame);
		this.cameraRecorder.write(oneFrame);
		oneFrame.release();
	}
	
	public CameraRecorder cameraRecorder;
}
