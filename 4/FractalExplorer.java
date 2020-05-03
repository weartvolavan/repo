import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.geom.Rectangle2D;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.*;
import javax.swing.*;

public class FractalExplorer {

    private int _displaySize;
    private JImageDisplay _image;
    private JComboBox<String> _fractalChooser;
    private JButton _saveButton; // сохраняет картинку
	private JButton _resetButton; // перезапускает
	private FractalGenerator _gen;
	private Rectangle2D.Double _range;
	private int _rowsRemaining;

    /**
     * Отключает кнопки во время рисования
     */
    private void enableUI(boolean val) {
		_fractalChooser.setEnabled(val);

		_saveButton.setEnabled(val);
		_resetButton.setEnabled(val);
    }


    private class FractalWorker extends SwingWorker<Object, Object> {

		private int _y; // y-value of row that will be computed

		private int[] _RGBVals; // Hold each row's RGB values

		public FractalWorker(int y) {
		    _y = y;
		}
		
		public Object doInBackground() {
		    _RGBVals = new int[_displaySize];

		    double yCoord = FractalGenerator.getCoord(_range.y, _range.y + _range.height,
								       _displaySize, _y);

		    for (int x = 0; x < _displaySize; x++) {

				double xCoord = FractalGenerator.getCoord(_range.x, _range.x + _range.width,
									       _displaySize, x);
				int numIters;
				int rgbColor = 0;
				float hue;
			      
				numIters = _gen.numIterations(xCoord, yCoord);
				if (numIters >= 0) {
				    hue = 0.7f + numIters / 200f;
				    rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
				}
				
					_RGBVals[x] = rgbColor;
		    }
		    
		    return null;
		}

		public void done() {
		    for (int x = 0; x < _displaySize; x++) {
				_image.drawPixel(x, _y, _RGBVals[x]);
		    }

		    _image.repaint(0, 0, _y, _displaySize, 1);

		    if (_rowsRemaining-- < 1) {
				enableUI(true);
		    }
		}
    }
    
    /**
     * Реагирует на нажатие кнопки
     */ 
    private class FractalHandler implements ActionListener {
	
	public void actionPerformed(ActionEvent e) {
	    String cmd = e.getActionCommand();
	 
	    if (e.getSource() == _fractalChooser) {
			String selectedItem = _fractalChooser.getSelectedItem().toString();

		if (selectedItem.equals(Mandelbrot.getString()))  {
			_gen = new Mandelbrot();
		}
                else {
		    JOptionPane.showMessageDialog(null, "Error: Couldn't recognize choice");
		    return;
		}
                
                _range = new Rectangle2D.Double();
                _gen.getInitialRange(_range);
                
                drawFractal();
	    }
	    else if (cmd.equals("reset")) {
		_range = new Rectangle2D.Double();
		_gen.getInitialRange(_range);
		
		drawFractal();
	    }
	    else if (cmd.equals("save")) {
		JFileChooser chooser = new JFileChooser();

		FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG Images", "png");
		chooser.setFileFilter(filter);
		chooser.setAcceptAllFileFilterUsed(false);

		if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
		    try {
			File f = chooser.getSelectedFile();
			String filePath = f.getPath();
			if (!filePath.toLowerCase().endsWith(".png")) {
			    f = new File(filePath + ".png");
			}

			ImageIO.write(_image.getImage(), "png", f);
		    }
		    catch (IOException exc) {
			JOptionPane.showMessageDialog(null, "Error: Couldn't save image ( "
						      + exc.getMessage() + " )");
		    }
		}							  
	    }
	    else {
		JOptionPane.showMessageDialog(null, "Error: Couldn't recognize action");
	    }
	}
    }


    /** 
     * По щелчку мыши увеличивает изображение
     */
    private class MouseHandler extends MouseAdapter {
	
	public void mouseClicked(MouseEvent e) {

	    if (_rowsRemaining > 0) {
		return;
	    }

	    double xCoord = FractalGenerator.getCoord(_range.x, _range.x + _range.width,
						      _displaySize, e.getX());

	    double yCoord = FractalGenerator.getCoord(_range.y, _range.y + _range.height,
						      _displaySize, e.getY());

	    _gen.recenterAndZoomRange(_range, xCoord, yCoord, 0.5);

	    drawFractal();
	}
    }

    public FractalExplorer(int size) {
	_displaySize = size;

	_gen = new Mandelbrot();

	_range = new Rectangle2D.Double();
	_gen.getInitialRange(_range);
    }

    public void createAndShowGUI() {
		JFrame frame  = new JFrame("Fractal Explorer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout( new BorderLayout());
		FractalHandler handler = new FractalHandler();
		JPanel fractalPanel = new JPanel();

		JLabel label = new JLabel("Fractal: ");
		fractalPanel.add(label);

		_fractalChooser = new JComboBox<String>();
		_fractalChooser.addItem(Mandelbrot.getString());
			_fractalChooser.addActionListener(handler);
		fractalPanel.add(_fractalChooser);
		frame.getContentPane().add(fractalPanel, BorderLayout.NORTH);
		// картинка
		_image = new JImageDisplay(_displaySize, _displaySize);
		frame.getContentPane().add(_image, BorderLayout.CENTER);
		// кнопки
		JPanel buttonsPanel = new JPanel();
		// сохранение
		_saveButton = new JButton("Save Image");
			_saveButton.setActionCommand("save");
			_saveButton.addActionListener(handler);
			buttonsPanel.add(_saveButton);
		// ресет
		_resetButton = new JButton("Reset Display");
		_resetButton.setActionCommand("reset");
		_resetButton.addActionListener(handler);
		buttonsPanel.add(_resetButton);
		frame.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
		frame.getContentPane().addMouseListener(new MouseHandler());
		frame.pack();
			frame.setVisible(true);
			frame.setResizable(false);
    }
	
    public void drawFractal() {
	enableUI(false);

	for (int y = 0; y < _displaySize; y++) {
	    FractalWorker worker = new FractalWorker(y);
	    worker.execute();
	}

	_image.repaint();
    }

    /**
     * Запуск
     */
    
    public static void main(String[] args) {
	FractalExplorer explorer = new FractalExplorer(800);
	explorer.createAndShowGUI();
	explorer.drawFractal();
    }

}