package FilesManagement;

import javax.swing.JFileChooser;




public class FileChooser {
	JFileChooser chooser;
	public FileChooser(){
		chooser = new JFileChooser();
		setFilters();
	}
	private void setFilters(){
		JavaFilter javaFilter = new JavaFilter();
		TxtFilter textFilter = new TxtFilter();
		chooser.setFileFilter(javaFilter);
		chooser.setFileFilter(textFilter);
	}
	public String getOpenFilePath(){
		int returnVal = chooser.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile().getAbsolutePath();
		}else return null;		
	}
	public String getSaveFilePath(){
		int returnVal = chooser.showSaveDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile().getAbsolutePath();
		}else return null;	
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFileChooser chooser = new JFileChooser();
		// Note: source for ExampleFileFilter can be found in FileChooserDemo,
		// under the demo/jfc directory in the Java 2 SDK, Standard Edition.
		JavaFilter filter = new JavaFilter();
		chooser.setFileFilter(filter);
		
		int returnVal = chooser.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
		   System.out.println("You chose to open this file: " +
		        chooser.getSelectedFile().getAbsolutePath());
		}

	}

}
