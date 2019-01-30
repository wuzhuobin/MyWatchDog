package wuzhuobin.camera;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

public class CameraWindow extends JPanel {
	
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
	
	public CameraWindow(VideoCapture videoCapture, int fps) {
		this.frame = new JFrame();
		this.frame.add(this);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setVisible(true);
		this.videoCapture = videoCapture;
		if(this.videoCapture.isOpened()) {
			double width = this.videoCapture.get(Videoio.CAP_PROP_FRAME_WIDTH);
			double height = this.videoCapture.get(Videoio.CAP_PROP_FRAME_HEIGHT);
			this.frame.setSize((int)width, (int)height);
			this.image = new Mat();
			this.timer = new Timer(1000 / fps, (ActionEvent e)->this.repaint());
			this.timer.start();
		}
	}
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		if(this.videoCapture.isOpened()) {
			this.videoCapture.read(this.image);
			g.drawImage(matToBufferedImage(this.image), 0, 0, this);
		}
	}
	
	public void addWindowClosingListener(WindowClosingAction action) {
		this.frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub
				super.windowClosing(arg0);
				if(CameraWindow.this.timer.isRunning()) {
					CameraWindow.this.timer.stop();
				}
				action.windowClosing();
			}
		});
	}
	
	public interface WindowClosingAction {
		public void windowClosing();
	}

	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private VideoCapture videoCapture;
	private Mat image;
	private Timer timer;
};