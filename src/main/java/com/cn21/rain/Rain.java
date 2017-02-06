package com.cn21.rain;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.MemoryImageSource;
import java.util.Random;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * @author cbx 黑客帝国数字雨效果
 */
public class Rain extends JDialog implements ActionListener {

	private Random random = new Random();

	private Dimension screenSize;

	private JPanel jPanel;

	// 行高，列宽
	private final static int gap = 20;

	// 存放雨点顶部的位置信息
	private int[] posArr;

	// 行数和列数
	private int lines;

	private int columns;

	public Rain() {
		init();
	}

	private void init() {
		setLayout( new BorderLayout() );
		jPanel = new MyPanel();
		add( jPanel, BorderLayout.CENTER );
		// 设置光标不可见
		Toolkit defaulToolkit = Toolkit.getDefaultToolkit();
		Image image = defaulToolkit.createImage( new MemoryImageSource( 0, 0, null, 0, 0 ) );
		Cursor invisibleCursor = defaulToolkit.createCustomCursor( image, new Point( 0, 0 ), "cursor" );
		setCursor( invisibleCursor );
		// ESC键退出
		KeyPressListener keyPressListener = new KeyPressListener();
		this.addKeyListener( keyPressListener );
		// 去标题栏
		this.setUndecorated( true );
		// 全屏
		//this.getGraphicsConfiguration().getDevice().setFullScreenWindow( this );
		//this.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
		this.setSize( 200, 200 );
		setVisible( true );

		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		lines = screenSize.height / gap;
		columns = screenSize.width / gap;
		posArr = new int[columns + 1];
		random = new Random();
		for( int i = 0; i < posArr.length; i++ ) {
			posArr[i] = random.nextInt( lines );
		}
		// 每秒10帧
		new Timer( 100, this ).start();
	}

	public static void main( String[] args ) {
		// TODO Auto-generated method stub
		new Rain();
	}

	@Override
	public void actionPerformed( ActionEvent e ) {
		// TODO Auto-generated method stub
		jPanel.repaint();
	}

	private char getChar() {
		return (char)(random.nextInt( 94 ) + 33);
	}

	private class MyPanel extends JPanel {
		@Override
		public void paint( Graphics g ) {
			Graphics2D g2d = (Graphics2D)g;
			g2d.setFont( getFont().deriveFont( Font.BOLD ) );
			g2d.setColor( Color.BLACK );
			g2d.fillRect( 0, 0, screenSize.width, screenSize.height );
			// 当前列
			int currentColumn = 0;
			for( int x = 0; x < screenSize.width; x += gap ) {
				int endPos = posArr[currentColumn];
				g2d.setColor( Color.CYAN );
				g2d.drawString( String.valueOf( getChar() ), x, endPos * gap );
				int cg = 0;
				for( int j = endPos - 15; j < endPos; j++ ) {
					// 颜色渐变
					cg += 20;
					if( cg > 255 ) {
						cg = 255;
					}
					g2d.setColor( new Color( 0, cg, 0 ) );
					g2d.drawString( String.valueOf( getChar() ), x, j * gap );
				}
				// 每放完一帧，当前列上雨点的位置随机下移1~5行
				posArr[currentColumn] += random.nextInt( 5 );
				// 当雨点位置超过屏幕高度时，重新产生一个随机位置
				if( posArr[currentColumn] * gap > getHeight() ) {
					posArr[currentColumn] = random.nextInt( lines );
				}
				currentColumn++;
			}

		}

	}

	private class KeyPressListener extends KeyAdapter {
		@Override
		public void keyPressed( KeyEvent e ) {
			if( e.getKeyCode() == KeyEvent.VK_ESCAPE ) {
				System.exit( 0 );
			}
		}
	}

}