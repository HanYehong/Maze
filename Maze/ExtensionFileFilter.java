import java.io.File;

public class ExtensionFileFilter extends javax.swing.filechooser.FileFilter {
	private String description,extension;
	public ExtensionFileFilter(String description,String extension)
	{
		this.description=description;
		this.extension=extension.toLowerCase();
	}
	public boolean accept(File file)
	{
		return file.getName().toLowerCase().endsWith(this.extension);
	}
	public String getDescription()
	{
		return this.description;
	}

}
