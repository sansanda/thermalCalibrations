package fileUtilities;

import factoryCreator.Factory;

import javax.swing.filechooser.FileFilter;

public class FileFilterFactory extends Factory implements FileFilterFactoryInterface {
	public FileFilter createFileFilter(String _fileFilter) throws Exception
	{
		/* Return fileFilter*/
		if (_fileFilter.equals("txt")) {
			return new TXTFileFilter();
		}
		if (_fileFilter.equals("xml")) {
			return new XMLFileFilter();
		}
		return null;
	}
}