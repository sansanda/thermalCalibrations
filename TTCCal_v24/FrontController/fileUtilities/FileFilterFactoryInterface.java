package fileUtilities;

import javax.swing.filechooser.FileFilter;

public interface FileFilterFactoryInterface {
	public FileFilter createFileFilter(String _fileFilter) throws Exception;
}
