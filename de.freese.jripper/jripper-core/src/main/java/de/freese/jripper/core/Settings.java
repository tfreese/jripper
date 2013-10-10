/**
 * Created: 07.10.2013
 */

package de.freese.jripper.core;

/**
 * Klasse mit der Konfiguation.
 * 
 * @author Thomas Freese
 */
public final class Settings
{
	/**
	 * 
	 */
	private static final Settings INSTANCE = new Settings();

	/**
	 * @return {@link Settings}
	 */
	public static Settings getInstance()
	{
		return INSTANCE;
	}

	/**
	 * Laufwerk
	 */
	private String device = null;

	/**
	 * 
	 */
	private String framesPerSecond = null;

	/**
	 * 
	 */
	private String mp3Bitrate = null;

	/**
	 * Arbeitsverzeichnis
	 */
	private String workDir = null;

	/**
	 * Erstellt ein neues {@link Settings} Object.
	 */
	private Settings()
	{
		super();

		setWorkDir(System.getProperty("java.io.tmpdir"));
		// setWorkDir(System.getProperty("user.dir"));

		setMp3Bitrate("320");
		setDevice(JRipperUtils.detectCDDVD());
		setFramesPerSecond("75");
	}

	/**
	 * @return String
	 */
	public String getDevice()
	{
		return this.device;
	}

	/**
	 * @return String
	 */
	public String getFramesPerSecond()
	{
		return this.framesPerSecond;
	}

	/**
	 * @return String
	 */
	public String getMp3Bitrate()
	{
		return this.mp3Bitrate;
	}

	/**
	 * Liefert das Arbeitsverzeichnis.
	 * 
	 * @return String
	 */
	public String getWorkDir()
	{
		return this.workDir;
	}

	/**
	 * @param device String
	 */
	public void setDevice(final String device)
	{
		this.device = device;
	}

	/**
	 * @param framesPerSecond String
	 */
	public void setFramesPerSecond(final String framesPerSecond)
	{
		this.framesPerSecond = framesPerSecond;
	}

	/**
	 * @param mp3Bitrate String
	 */
	public void setMp3Bitrate(final String mp3Bitrate)
	{
		this.mp3Bitrate = mp3Bitrate;
	}

	/**
	 * Setzt das Arbeitsverzeichnis.
	 * 
	 * @param workDir String
	 */
	public void setWorkDir(final String workDir)
	{
		this.workDir = workDir;
	}
}
