import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class EditPlaylist extends JPanel implements ActionListener {
	JButton playlist;
	JButton addSong;
	JButton addDir;
	JButton addToList;
	
	String playlistName = null;
	String songName;
	List<String> songs = new ArrayList<String>();
	
	JFileChooser chooser;
	String choosertitle;

	public EditPlaylist() {
		playlist = new JButton(new AbstractAction("Choose playlist"){

			@Override
			public void actionPerformed(ActionEvent e) {
				selectPlaylist(e);
			}
			
		});
		
		addSong = new JButton(new AbstractAction("Add song"){

			@Override
			public void actionPerformed(ActionEvent e) {
				selectSong(e);
			}
		});
		
		addDir = new JButton(new AbstractAction("Add directory"){

			@Override
			public void actionPerformed(ActionEvent e) {
				selectDir(e);
			}
		});
		
		addToList = new JButton(new AbstractAction("Add to list"){

			@Override
			public void actionPerformed(ActionEvent e) {
				addToPlaylist(e);
			}
		});
		
		playlist.addActionListener(this);
		addSong.addActionListener(this);
		addDir.addActionListener(this);
		addToList.addActionListener(this);
		add(playlist);
		add(addSong);
		add(addDir);
		add(addToList);
	}
	
	public String fileChooser(ActionEvent e){
		String selection = null;
		
		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle(choosertitle);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		chooser.setAcceptAllFileFilterUsed(false);
		
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			selection = chooser.getSelectedFile().getAbsolutePath();
		} else {
			System.out.println("No Selection ");
		}
		
		return selection;
	}
	
	public String fileAndDirChooser(ActionEvent e){
		String selection = null;
		
		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle(choosertitle);
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		chooser.setAcceptAllFileFilterUsed(false);
		
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			selection = chooser.getSelectedFile().getAbsolutePath();
		} else {
			System.out.println("No Selection ");
		}
		
		return selection;
	}

	public void selectPlaylist(ActionEvent e) {
		playlistName = fileChooser(e);
		int len = playlistName.length() - 4;
		
		if(!playlistName.substring(playlistName.length() - 4).equals("m3u8")){
			System.out.println("Not playlist file, choose again");
			playlistName = "";
		}
		
		System.out.println("Playlist: " + playlistName);
	}
	
	public void selectSong(ActionEvent e){
		songName = fileChooser(e).replace('\\', '/').substring(3);
		System.out.println("Song: " + songName);
		songs.add(songName);
	}
	
	public void selectDir(ActionEvent e){
		File file = new File(fileAndDirChooser(e));
		
		File[] list = file.listFiles();
		
		for(File f : list){
			songName = f.getAbsolutePath().replace('\\', '/').substring(3);
			System.out.println("Songs: " + songName);
			songs.add(songName);
		}
	}
	
	public void addToPlaylist(ActionEvent e){
		if(playlistName != null){
			File file = new File(playlistName);
			String prefix = "/<microSD1>/";
			
			try{
				PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file.getAbsolutePath(), true))); 
			  	
				for(String s : songs){
					out.println(prefix + s);
				}
			  	out.close();
			}
			catch(IOException e1){
				System.out.println("Add to playlist error");
			}	
		}else{
			System.out.println("No playlist selected");
		}
	}

	public Dimension getPreferredSize() {
		return new Dimension(150, 200);
	}

	public static void main(String s[]) {
		JFrame frame = new JFrame("");
		EditPlaylist panel = new EditPlaylist();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setLocationRelativeTo(null);
		frame.getContentPane().add(panel, "Center");
		frame.setSize(panel.getPreferredSize());
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}

/*
 * import java.io.BufferedWriter; import java.io.File; import
 * java.io.FileWriter; import java.io.IOException; import java.io.PrintWriter;
 * 
 * 
 * public class EditPlaylist {
 * 
 * public static void main(String[] args) { String filePath =
 * "G:\\Playlists\\15-01-20 - Copy.m3u8"; File file = new File(filePath); String
 * prefix = "/<microSD1>/Music/";
 * 
 * try(PrintWriter out = new PrintWriter(new BufferedWriter(new
 * FileWriter(file.getAbsolutePath(), true)))) { out.println(prefix +
 * "TESTSONG.mp3");
 * 
 * }catch (IOException e) {
 * 
 * } } }
 */